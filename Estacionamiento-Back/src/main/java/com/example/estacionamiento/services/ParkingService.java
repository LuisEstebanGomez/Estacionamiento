package com.example.estacionamiento.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.estacionamiento.models.CtaCteModel;
import com.example.estacionamiento.models.HolidayModel;
import com.example.estacionamiento.models.ParkingModel;
import com.example.estacionamiento.models.UserModel;
import com.example.estacionamiento.models.VehicleModel;
import com.example.estacionamiento.repositories.IParkingRepository;
import com.example.estacionamiento.repositories.IUserRepository;

import ch.qos.logback.core.util.Duration;

@Service
public class ParkingService {

	    @Autowired
	    private IParkingRepository estacionamientoRepository;

	    @Autowired
	    private IUserRepository userRepository;
	    
		@Autowired
		private CtaCteService ctacteService;
		
		@Autowired
		private HolidayService holidayService;
		
		
		public ParkingModel iniciarEstacionamiento(String phoneNumber, String patente) {
	    	
	    	ParkingModel estacionamient = estacionamientoRepository.findByUserPhoneNumber(phoneNumber);  
	    	
	    	UserModel user = userRepository.findByPhoneNumber(phoneNumber);
	    	user.getCuentaCorriente().getSaldo();
	    	
	    	boolean diaFeriado= false;
	    	LocalDateTime fecha = LocalDateTime.now();
	    	DayOfWeek dayOfWeek = fecha.getDayOfWeek();
	    	          
	    	//SE FIJA QUE ESTE EN EL HORARIO PERMITIDO PARA PODER OPERAR Y DIAS PERMITIDOS
	    	//&& (DayOfWeek.SATURDAY != dayOfWeek && DayOfWeek.SUNDAY != dayOfWeek))
	    	if ((fecha.getHour() >= 0 && fecha.getHour() <=24 )  && (estacionamient == null)&& (DayOfWeek.SATURDAY != dayOfWeek && DayOfWeek.SUNDAY != dayOfWeek)) {
	    		    		
	    		String fechaParaComparar = fecha.toLocalDate().toString();
		    	  
	            if(!esDiaFeriado(fechaParaComparar)) {
	            	//SE FIJA QUE EL SALDO DISPONIBLE PUEDA CANCELAR AUNQUESEA 1 H
		    		if(user.getCuentaCorriente().getSaldo()>=10) {
		    			
			    		// CREA NUEVO ESTACIONAMIENTO
				        ParkingModel estacionamiento = new ParkingModel();
				        estacionamiento.setUser(user);
				        estacionamiento.setPatente(patente);
				        estacionamiento.setHoraInicio(fecha);
				        estacionamiento.setActivo(true);
			    
				        return estacionamientoRepository.save(estacionamiento);
		            
		    		}else {
		    			System.out.print("No tiene saldo suficiente para operar");
		    		}
	            }else {
	            	System.out.print("No se puede operar un dia feriado");         	
	            }
	    			    		
	    	}else {
	    		System.out.print("Esta fuera del rango de horarios operable");
	    		
	    	}
	    		
	    	return null;    
	    }

	    
	    public ParkingModel finalizarEstacionamiento(String phoneNumber) {
	    	
	    	//BUSCA SI EL USUARIO CORRESPONDIENTE A PHONE NUMBER TIENE ALGUN ESTACIONAMIENTO ACTIVO
	    	ParkingModel estacionamiento = estacionamientoRepository.findByUserPhoneNumber(phoneNumber);  	
	
			if(estacionamiento!=null) {
				if(estacionamiento.isActivo()) {
				
			        LocalDateTime horaInicio = estacionamiento.getHoraInicio();
			        LocalDateTime horaFin = LocalDateTime.now().plusSeconds(0);
		
			        // CALCULA HORAS
			        double duracion = ChronoUnit.MINUTES.between(horaInicio, horaFin);
			        duracion = Math.ceil(duracion / 15);	       
		       
			        UserModel user = estacionamiento.getUser();
			        CtaCteModel cta = user.getCuentaCorriente();
			      
			        //  COSTO POR HORA
			        //  double costoPorHora = 10.0; 
			        
			        // COSTO POR 15 MINUTOS
			        double costoPorCuartoDeHora= 2.50;
			              
			        // CALCULA GASTO 
			        double importeTotal = costoPorCuartoDeHora * duracion;
		
			        //REGISTRA EN LA CUENTA CORRIENTE Y ESTA REGISTRA EL MOVIMIENTO
			        ctacteService.cargarCuentaCorriente(user.getCuentaCorriente().getId(), (0-importeTotal),"Egreso");
			       
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
			return estacionamiento;
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
