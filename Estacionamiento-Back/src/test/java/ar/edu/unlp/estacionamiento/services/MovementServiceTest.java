
package ar.edu.unlp.estacionamiento.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.unlp.estacionamiento.models.MovementModel;
import ar.edu.unlp.estacionamiento.repositories.IMovementRepository;

public class MovementServiceTest {
	
	@Mock
	private IMovementRepository movementRepository;
	
	@InjectMocks
	private MovementService movementService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCrearMovimiento() {
		MovementModel movimiento = new MovementModel();
		movimiento.setId(1L);
		
		when(movementRepository.save(movimiento)).thenReturn(movimiento);
		MovementModel result = movementService.crearMovimiento(movimiento);
		
		assertNotNull(result);
		assertEquals(movimiento, result);
		verify(movementRepository, times(1)).save(movimiento);
	
	}
	
	@Test
	void testGetMovimientosById() {
		MovementModel movimiento = new MovementModel();
		movimiento.setId(1L);
		
		when(movementRepository.findById(1L)).thenReturn(Optional.of(movimiento));
		
		MovementModel result = movementService.getMovimientoById(1L);
		
		assertNotNull(result);
		assertEquals(movimiento, result);
		
	}
	
	
	
	
	
}
