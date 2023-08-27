package ar.edu.unlp.estacionamiento.services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unlp.estacionamiento.models.AccountModel;
import ar.edu.unlp.estacionamiento.models.MovementModel;
import ar.edu.unlp.estacionamiento.models.UserModel;
import ar.edu.unlp.estacionamiento.models.VehicleModel;
import ar.edu.unlp.estacionamiento.repositories.IUserRepository;
import ar.edu.unlp.estacionamiento.repositories.IVehicleRepository;
import io.jsonwebtoken.lang.Arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
	

	
	@InjectMocks
    private UserService userService;

	@Mock
	private VehicleService vehicleService;
    
    @Mock
    private AccountService accountService;

    @Mock
    private IUserRepository userRepository;
    
    @Mock
    private IVehicleRepository vehicleRepository;
    
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
	void getAllUser() {
		List<UserModel> usuarios = new ArrayList<>();
	
		when(userRepository.findAll()).thenReturn(usuarios);
		
		List<UserModel> listResult = userService.getAllUsers();
		
		assertNotNull(listResult);
		assertEquals(usuarios,listResult);
	
	}
	
	@Test
	void getUserByPhoneNumber() {
		
		UserModel user= new UserModel();
		user.setPhoneNumber("1234567890");
		
		when(userRepository.findByPhoneNumber("1234567890")).thenReturn(user);
		
		UserModel result = userService.getUserByPhoneNumber("1234567890");
		
		 assertNotNull(result);
	     assertEquals(user, result);		
	}
	
	@Test
    public void testGetUserVehicles() {
       
        UserModel user = new UserModel();
        user.setPhoneNumber("1234567890");
        
        List<VehicleModel> someList = List.of(
                new VehicleModel(),
                new VehicleModel()
        );
        user.setVehiculos(someList);

      
        when(userRepository.findByPhoneNumber("1234567890")).thenReturn(user);

        List<VehicleModel> result = userService.getUserVehicles("1234567890");

        assertNotNull(result); 
        assertEquals(someList, result);
        assertEquals(2, result.size()); 
    }
	
	 @Test
	 public void testAddVehicleToUser() {
	        UserModel user = new UserModel();
	        user.setPhoneNumber("123456789");
	        user.setVehiculos(new ArrayList<>());
	        VehicleModel vehicle = new VehicleModel();
	        vehicle.setPatente("ABC123");
	        
	        when(userRepository.findByPhoneNumber("123456789")).thenReturn(user);
	        when(vehicleService.existePatente("ABC123")).thenReturn(false);
	        when(userRepository.save(user)).thenReturn(user);
	        

	        boolean ok = false;
	        StringBuilder msg = new StringBuilder();
	        UserModel result = userService.addVehicleToUser("123456789", vehicle, ok, msg);

	        assertEquals(1, result.getVehiculos().size());
	        assertEquals(user, vehicle.getUser());

	        verify(userRepository, times(1)).findByPhoneNumber("123456789");
	        verify(vehicleService, times(1)).existePatente("ABC123");
	        verify(userRepository, times(1)).save(user);
	    }

	@Test
	public void testAgregarCuentaCorriente() {
	    String phoneNumber = "1234567890";
	    UserModel user = new UserModel();
	    user.setPhoneNumber(phoneNumber);
	
	    AccountModel cuentaCorriente = new AccountModel();
	
	    when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(user);
	
	    UserModel result = userService.agregarCuentaCorriente(phoneNumber, cuentaCorriente);
	
	    assertNotNull(result);
	    assertEquals(cuentaCorriente, result.getAccount());
	    verify(userRepository).save(user);
	
	    // Test case: User not found
	    when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(null);
	
	    UserModel failureResult = userService.agregarCuentaCorriente(phoneNumber, cuentaCorriente);
	
	    assertNull(failureResult);
	   
}
	
	@Test
	public void testCrearUsuario() {
	    UserModel user = new UserModel();
	    user.setPhoneNumber("1234567898");
	    user.setPassword("password123");

	    AccountModel mockCtaCte = new AccountModel();
	    when(accountService.createCtaCte()).thenReturn(mockCtaCte);

	    when(userRepository.save(user)).thenReturn(user);

	    UserModel result = userService.crearUsuario(user);

	    assertNotNull(result);
	    assertEquals(mockCtaCte, result.getAccount());
	    assertEquals(user.getPassword(), result.getPassword());
	    verify(userRepository).save(user);
	}
		
	@Test
	void testGetUserAccount() {
	    String existingPhoneNumber = "1234567890";
	    String nonExistingPhoneNumber = "9876543210";
	    UserModel existingUser = new UserModel();
	    AccountModel mockAccount = new AccountModel();
	    
	    when(userRepository.findByPhoneNumber(existingPhoneNumber)).thenReturn(existingUser);
	    when(userRepository.findByPhoneNumber(nonExistingPhoneNumber)).thenReturn(null);
	    
	    existingUser.setCuentaCorriente(mockAccount);

	    AccountModel existingResult = userService.getUserAccount(existingPhoneNumber);
	    AccountModel nonExistingResult = userService.getUserAccount(nonExistingPhoneNumber);

	    assertEquals(mockAccount, existingResult);
	    assertNull(nonExistingResult);
	}
    // ...
}