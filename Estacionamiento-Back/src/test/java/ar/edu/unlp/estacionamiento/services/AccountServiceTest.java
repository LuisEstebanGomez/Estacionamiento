package ar.edu.unlp.estacionamiento.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.unlp.estacionamiento.models.AccountModel;
import ar.edu.unlp.estacionamiento.models.MovementModel;
import ar.edu.unlp.estacionamiento.repositories.AccountRepository;

public class AccountServiceTest {

	@Mock
	AccountRepository ctaCteRepository;
	
	@InjectMocks
	private AccountService accountService;
	
	@BeforeEach
	void setUp() {
		//ver que son las anotaciones de Mockito
		MockitoAnnotations.openMocks(this);
	}
	
	//CHEQUEA QUE SE CREE Y QUE EL SALDO CON EL QUE SE CREO SEA $10.000
	@Test
	void testCreateCtaCte() {
		when(ctaCteRepository.save(any(AccountModel.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
	 AccountModel createdCtaCte = accountService.createCtaCte();
	 assertNotNull(createdCtaCte);
     assertEquals(10000.0, createdCtaCte.getSaldo());
 
	}
	
	 // Verifica que el repositorio haya sido llamado para guardar la cuenta 
	@Test
    void testGuardarCuentaCorriente() {
     
        AccountModel cuentaCorriente = new AccountModel();

        accountService.guardarCuentaCorriente(cuentaCorriente);
    
        verify(ctaCteRepository, times(1)).save(cuentaCorriente);
    }
	
	@Test
	void testCargarCuentaCorriente() {
		
        AccountModel cuentaCorriente = new AccountModel();
       
        cuentaCorriente.setId(1L);
        cuentaCorriente.setSaldo(10000);
        
        when(ctaCteRepository.findById(1L)).thenReturn(Optional.of(cuentaCorriente));
        when(ctaCteRepository.save(any(AccountModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AccountModel CuentaCorriente2 = accountService.cargarCuentaCorriente(1L, 300.0, "CARGA");

        assertNotNull(CuentaCorriente2);
        assertEquals(10300.0, CuentaCorriente2.getSaldo());
       
        verify(ctaCteRepository, times(1)).save(CuentaCorriente2);
	}

	
	@Test
    void testGetMovimientosByCtaCteId() {
        // Crea una cuenta simulada
        AccountModel account = new AccountModel();
        account.setId(1L);

        // Define una lista simulada de movimientos
        List<MovementModel> someListOfMovements = List.of(
                new MovementModel(100.0, 1100.0, "CARGA"),
                new MovementModel(-50.0, 1050.0, "RETIRO")
        );

        account.setMovements(someListOfMovements);
        
        // Simula el comportamiento del repositorio
        when(ctaCteRepository.findById(1L)).thenReturn(Optional.of(account));


        // Llama al método de prueba
        List<MovementModel> movimientos = accountService.getMovimientosByCtaCteId(1L);

        // Verifica el resultado
        assertEquals(someListOfMovements, movimientos);
    }

	
}