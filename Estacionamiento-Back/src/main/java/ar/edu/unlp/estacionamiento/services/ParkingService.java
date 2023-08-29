package ar.edu.unlp.estacionamiento.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ar.edu.unlp.estacionamiento.models.AccountModel;
import ar.edu.unlp.estacionamiento.models.HolidayModel;
import ar.edu.unlp.estacionamiento.models.ParkingModel;
import ar.edu.unlp.estacionamiento.models.UserModel;
import ar.edu.unlp.estacionamiento.models.VehicleModel;
import ar.edu.unlp.estacionamiento.repositories.IParkingRepository;
import ar.edu.unlp.estacionamiento.repositories.IUserRepository;
import ar.edu.unlp.estacionamiento.security.dto.ApiResponse;
import ch.qos.logback.core.util.Duration;

@Service
public class ParkingService {

	    @Autowired
	    private IParkingRepository estacionamientoRepository;

	    @Autowired
	    private IUserRepository userRepository;
	    
		@Autowired
		private AccountService ctacteService;
		
		@Autowired
		private HolidayService holidayService;
		
		@Autowired
		MessageSource msg;
		
		public ParkingModel iniciarEstacionamiento(String phoneNumber, String patente, LocalDateTime currentDateTime, StringBuilder response) {
			 
			
	    	ParkingModel estacionamient = estacionamientoRepository.findByUserPhoneNumber(phoneNumber);  
	    	
	    	UserModel user = userRepository.findByPhoneNumber(phoneNumber);
	    	user.getAccount().getSaldo();
	    	
	    	boolean diaFeriado= false;
	    	LocalDateTime fecha = currentDateTime;
	    	DayOfWeek dayOfWeek = fecha.getDayOfWeek();
	    	          
	    	//SE FIJA QUE ESTE EN EL HORARIO PERMITIDO PARA PODER OPERAR Y DIAS PERMITIDOS
	    	//&& (DayOfWeek.SATURDAY != dayOfWeek && DayOfWeek.SUNDAY != dayOfWeek))
	    	if ((currentDateTime.getHour() >= 8 && currentDateTime.getHour() <=24 )  && (estacionamient == null)&& (DayOfWeek.SATURDAY != dayOfWeek && DayOfWeek.SUNDAY != dayOfWeek)) {
	    		    		
	    		String fechaParaComparar = fecha.toLocalDate().toString();
		    	  
	            if(!esDiaFeriado(fechaParaComparar)) {
	            	//SE FIJA QUE EL SALDO DISPONIBLE PUEDA CANCELAR AUNQUESEA 1 H
		    		if(user.getAccount().getSaldo()>=1000000) {
		    			
			    		// CREA NUEVO ESTACIONAMIENTO
				        ParkingModel estacionamiento = new ParkingModel();
				        estacionamiento.setUser(user);
				        estacionamiento.setPatente(patente);
				        estacionamiento.setHoraInicio(fecha);
				        estacionamiento.setActivo(true);
				        		    
				        return estacionamientoRepository.save(estacionamiento);
		            
		    		}else {
		     			
						response.append("parking.notValid.balance");

		    		}
	            }else {
	            	response.append("parking.notValid.workingDays");

	            }  			    		
	    	}else {

	    		response.append("parking.notValid");
	    	}

	    	return null;    
	    }

	    
	    public ParkingModel finalizarEstacionamiento(String phoneNumber, LocalDateTime currentDateTime) {
	    	
	    	//BUSCA SI EL USUARIO CORRESPONDIENTE A PHONE NUMBER TIENE ALGUN ESTACIONAMIENTO ACTIVO
	    	ParkingModel estacionamiento = estacionamientoRepository.findByUserPhoneNumber(phoneNumber);  	
	
			if(estacionamiento!=null) {
			
				if(estacionamiento.isActivo()) {
				
			        LocalDateTime horaInicio = estacionamiento.getHoraInicio();
			        LocalDateTime horaFin = currentDateTime.plusSeconds(0);
		
			        // CALCULA HORAS
			        double duracion = ChronoUnit.MINUTES.between(horaInicio, horaFin);
			        duracion = Math.ceil(duracion / 15);	       
		       
			        UserModel user = estacionamiento.getUser();
			        AccountModel cta = user.getAccount();
			      
			        //  COSTO POR HORA
			        //  double costoPorHora = 10.0; 
			        
			        // COSTO POR 15 MINUTOS
			        double costoPorCuartoDeHora= 2.50;
			              
			        // CALCULA GASTO 
			        double importeTotal = costoPorCuartoDeHora * duracion;
		
			        //REGISTRA EN LA CUENTA CORRIENTE Y ESTA REGISTRA EL MOVIMIENTO
			        ctacteService.cargarCuentaCorriente(user.getAccount().getId(), (0-importeTotal),"Egreso");
			       
			        //FIN ESTACIONAMIENTO
			        estacionamiento.setHoraFin(horaFin);
			        estacionamiento.setActivo(false);
			        estacionamiento.setImporte(importeTotal);
		
			        estacionamientoRepository.save(estacionamiento);	           	
			        return estacionamientoRepository.save(estacionamiento);
	        }
			}else {
				return null;
			}
			return null;
	 }


		public ParkingModel getUserParkingA(String phoneNumber) {
			 return estacionamientoRepository.findByUserPhoneNumber(phoneNumber);
			
		}

		public static ParkingModel getUserAccount(Long phoneNumbe) {
			// TODO Auto-generated method stub
			return null;
		} 
		
		//CAMBIAR  , es solo para probar si funciona
		public boolean esDiaFeriado(String fechaParaComparar) {
			List<HolidayModel> feriados = holidayService.getAllHolidays();
        
		    for (HolidayModel feriado : feriados) {
		        if (feriado.getDate().equals(fechaParaComparar)) {
		            
		            return true;
		        }
		    }
		    
		    return false;
		}
	
}
