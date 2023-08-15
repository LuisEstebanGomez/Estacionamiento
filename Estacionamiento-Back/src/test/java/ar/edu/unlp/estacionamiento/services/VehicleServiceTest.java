package ar.edu.unlp.estacionamiento.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.unlp.estacionamiento.models.HolidayModel;
import ar.edu.unlp.estacionamiento.models.VehicleModel;
import ar.edu.unlp.estacionamiento.repositories.IVehicleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {

	@Mock
	private IVehicleRepository vehicleRepository;
	
	@InjectMocks
	private VehicleService vehicleService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testCrearVehiculo(){
		
		VehicleModel vehicle = new VehicleModel();
		vehicle.setId(1L);
		
		when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
		
		VehicleModel result = vehicleService.crearVehiculo(vehicle);
		

        assertNotNull(result);
		assertEquals(vehicle, result);
		
		
	}

	@Test
	void testgetVehicles() {
		
		List<VehicleModel> vehicles = new ArrayList<>();
		when(vehicleRepository.findAll()).thenReturn(vehicles);
		
		List<VehicleModel> result = vehicleService.getVehicles();
		
		assertNotNull(result);
		assertEquals(vehicles, result);
		
		
	}

	
	  @Test
	    void testGetHolidayById() {
	        
	        VehicleModel vehicle = new VehicleModel();
	        vehicle.setId(1L);

	        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

	        Optional<VehicleModel> result = vehicleService.getVehicleById(1L);

	        assertTrue(result.isPresent());
	        assertEquals(vehicle, result.get());
	    }


	    @Test
	    void testExistePatente() {       

		  	String patente = "ABC123";
	        when(vehicleRepository.existsByPatente(patente)).thenReturn(true);

	        boolean resultado = vehicleService.existePatente(patente);

	        assertTrue(resultado);
	        verify(vehicleRepository, times(1)).existsByPatente(patente);
	    }






}
