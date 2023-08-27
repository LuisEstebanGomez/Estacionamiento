package ar.edu.unlp.estacionamiento.services;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unlp.estacionamiento.models.AccountModel;
import ar.edu.unlp.estacionamiento.models.HolidayModel;
import ar.edu.unlp.estacionamiento.models.ParkingModel;
import ar.edu.unlp.estacionamiento.models.UserModel;
import ar.edu.unlp.estacionamiento.repositories.IParkingRepository;
import ar.edu.unlp.estacionamiento.repositories.IUserRepository;
import ar.edu.unlp.estacionamiento.services.ParkingService;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingServiceTest {

    @InjectMocks
    private ParkingService parkingService;

    @Mock
    private IParkingRepository estacionamientoRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private AccountService ctacteService;  
    
    @Mock
    private HolidayService holidayService;

    @Mock
    private LocalDateTime currentDateTime;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
       
    }

    @Test
    public void testIniciarEstacionamiento() {
    	UserModel user = new UserModel();
        AccountModel cuenta = new AccountModel();
        cuenta.setSaldo(500);
        user.setAccount(cuenta);
      
        //Feriado
        List<HolidayModel> feriados = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaFeriado = LocalDate.parse("2023-12-25", formatter);
        feriados.add(new HolidayModel(fechaFeriado.toString()));

        when(userRepository.findByPhoneNumber(anyString())).thenReturn(user);
        when(estacionamientoRepository.findByUserPhoneNumber(anyString())).thenReturn(null);
        when(currentDateTime.getDayOfWeek()).thenReturn(DayOfWeek.MONDAY);
        when(currentDateTime.toLocalDate()).thenReturn(LocalDate.parse("2023-12-22"));
        when(currentDateTime.getHour()).thenReturn(12); //hora  rango
        when(holidayService.getAllHolidays()).thenReturn(feriados);
        when(estacionamientoRepository.save(any(ParkingModel.class))).thenReturn(new ParkingModel());
        StringBuilder msg = new StringBuilder();
        //inicia un estacionamiento con todos los valores correctos
        ParkingModel result = parkingService.iniciarEstacionamiento("1234567890", "ABC123", currentDateTime,msg);

        assertNotNull(result);// Verifica que se haya iniciado el estacionamiento
    }
    
    @Test
    public void testIniciarEstacionamientoNoTieneSaldo() {
    	UserModel user = new UserModel();
        AccountModel cuenta = new AccountModel();
        cuenta.setSaldo(0); //CON UN SALDO INFERIOR PARA QUE NO PUEDA INICIAR
        user.setAccount(cuenta);
      
        //Feriados
        List<HolidayModel> feriados = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaFeriado = LocalDate.parse("2023-12-25", formatter);
        feriados.add(new HolidayModel(fechaFeriado.toString()));

        when(userRepository.findByPhoneNumber(anyString())).thenReturn(user);
        when(estacionamientoRepository.findByUserPhoneNumber(anyString())).thenReturn(null);
        when(currentDateTime.getDayOfWeek()).thenReturn(DayOfWeek.MONDAY);
        when(currentDateTime.toLocalDate()).thenReturn(LocalDate.parse("2023-12-22"));
        when(currentDateTime.getHour()).thenReturn(12);
        when(holidayService.getAllHolidays()).thenReturn(feriados);
        when(estacionamientoRepository.save(any(ParkingModel.class))).thenReturn(new ParkingModel());
        StringBuilder msg = new StringBuilder();
        ParkingModel result = parkingService.iniciarEstacionamiento("1234567890", "ABC123", currentDateTime,msg);
        assertNull(result); // Verifica que no se haya iniciado el estacionamiento

    }

    @Test
    public void testIniciarEstacionamientoDiaFeriado() {
      
    	UserModel user = new UserModel();
        AccountModel cuenta = new AccountModel();
        cuenta.setSaldo(500);
        user.setAccount(cuenta);
      
        //Feriado
        List<HolidayModel> feriados = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaFeriado = LocalDate.parse("2023-12-25", formatter);
        feriados.add(new HolidayModel(fechaFeriado.toString()));

        when(userRepository.findByPhoneNumber(anyString())).thenReturn(user);
        when(estacionamientoRepository.findByUserPhoneNumber(anyString())).thenReturn(null);
        when(currentDateTime.getDayOfWeek()).thenReturn(DayOfWeek.MONDAY);
        when(currentDateTime.toLocalDate()).thenReturn(LocalDate.parse("2023-12-25")); // SE PRUEBA CON UNA FECHA QUE ES FERIADO AGREGADO ARRIBA
        when(currentDateTime.getHour()).thenReturn(12);
        when(holidayService.getAllHolidays()).thenReturn(feriados);
        when(estacionamientoRepository.save(any(ParkingModel.class))).thenReturn(new ParkingModel());
        StringBuilder msg = new StringBuilder();
        
        ParkingModel result = parkingService.iniciarEstacionamiento("1234567890", "ABC123", currentDateTime,msg);

        assertNull(result); // Verifica que no se haya iniciado el estacionamiento
        
    }

    @Test
    public void testIniciarEstacionamientoFueraDelRangoHorario() {
 
    	UserModel user = new UserModel();
        AccountModel cuenta = new AccountModel();
        cuenta.setSaldo(500);
        user.setAccount(cuenta);
      
        //FERIADOS
        List<HolidayModel> feriados = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaFeriado = LocalDate.parse("2023-12-25", formatter);
        feriados.add(new HolidayModel(fechaFeriado.toString()));

        when(userRepository.findByPhoneNumber(anyString())).thenReturn(user);
        when(estacionamientoRepository.findByUserPhoneNumber(anyString())).thenReturn(null);
        when(currentDateTime.getDayOfWeek()).thenReturn(DayOfWeek.MONDAY);
        when(currentDateTime.toLocalDate()).thenReturn(LocalDate.parse("2023-12-22"));
        when(holidayService.getAllHolidays()).thenReturn(feriados);
        when(estacionamientoRepository.save(any(ParkingModel.class))).thenReturn(new ParkingModel());
        when(currentDateTime.getHour()).thenReturn(1); //UNA HORA QUE ESTE FUERA DEL RANGO
        StringBuilder msg = new StringBuilder();
        ParkingModel result = parkingService.iniciarEstacionamiento("1234567890", "ABC123", currentDateTime,msg);
      
        assertNull(result); // Verifica que no se haya iniciado el estacionamiento
       
    }
   


    @Test
	public void testFinalizarEstacionamientoEstacionamientoNoActivo() {
	
	    ParkingModel estacionamiento = new ParkingModel();
	    estacionamiento.setActivo(false); // NO TIENE ESTACIONAMIENTO ACTIVO
	    when(estacionamientoRepository.findByUserPhoneNumber(anyString())).thenReturn(estacionamiento);
	    when(estacionamientoRepository.save(any(ParkingModel.class))).thenReturn(new ParkingModel());
	    ParkingModel result = parkingService.finalizarEstacionamiento("1234567890", currentDateTime);
	
	    assertNull(result); 
	}
	
    @Test
    public void testFinalizarEstacionamientoEstacionamientoActivo() {
        UserModel user = new UserModel();
        AccountModel cuenta = new AccountModel();
        cuenta.setSaldo(500); // Establece el saldo inicial del usuario
        user.setAccount(cuenta);

        ParkingModel estacionamiento = new ParkingModel();
        estacionamiento.setActivo(true);
        estacionamiento.setHoraInicio(currentDateTime);
        estacionamiento.setUser(user); // Asigna el usuario al estacionamiento

        when(estacionamientoRepository.findByUserPhoneNumber(anyString())).thenReturn(estacionamiento);
        when(estacionamientoRepository.save(any(ParkingModel.class))).thenReturn(new ParkingModel());
        when(ctacteService.cargarCuentaCorriente(anyLong(), anyDouble(), anyString())).thenReturn(cuenta);
        when(currentDateTime.getDayOfWeek()).thenReturn(DayOfWeek.MONDAY);
        when(currentDateTime.toLocalDate()).thenReturn(LocalDate.parse("2023-12-22"));

        ParkingModel result = parkingService.finalizarEstacionamiento("1234567890", currentDateTime);

        assertNotNull(result); // Verifica que se haya finalizado el estacionamiento
        assertFalse(result.isActivo());// verifica que ya este inactivo

        // Verifica que se haya llamado a ctacteService para cargar la cuenta corriente con el importe esperado
        verify(ctacteService).cargarCuentaCorriente(eq(cuenta.getId()), anyDouble(), eq("Egreso"));
    }
	
	
	}
