package ar.edu.unlp.estacionamiento.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.unlp.estacionamiento.models.AccountModel;
import ar.edu.unlp.estacionamiento.models.MovementModel;
import ar.edu.unlp.estacionamiento.models.ParkingModel;
import ar.edu.unlp.estacionamiento.models.UserModel;
import ar.edu.unlp.estacionamiento.models.VehicleModel;
import ar.edu.unlp.estacionamiento.security.dto.ApiResponse;
import ar.edu.unlp.estacionamiento.services.AccountService;
import ar.edu.unlp.estacionamiento.services.ParkingService;
import ar.edu.unlp.estacionamiento.services.UserService;
import ar.edu.unlp.estacionamiento.services.VehicleService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import jakarta.annotation.security.PermitAll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    private static final Marker APP = MarkerManager.getMarker("APP");

    @Autowired
    private UserService userService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private AccountService ctacteService;
    @Autowired
    private ParkingService parkingService;
    @Autowired
    MessageSource msg;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        logger.info(APP, "Iniciando sesión para el usuario con número de teléfono: {}", user.getPhoneNumber());

        UserModel aux = userService.getUserByPhoneNumber(user.getPhoneNumber());
        if (aux != null && aux.getPassword().equals(user.getPassword())) {
            logger.info(APP, "Inicio de sesión exitoso para el usuario: {}", user.getPhoneNumber());
            return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("user.validLogin", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
        } else {
            logger.warn(APP, "Inicio de sesión no válido para el usuario: {}", user.getPhoneNumber());
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("user.notValidLogin", null, LocaleContextHolder.getLocale())), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/add")
    @PermitAll
    public ResponseEntity<ApiResponse> crearUsuario(@RequestBody UserModel user) {
        logger.info(APP, "Creando nuevo usuario");
        UserModel usuarioAux = userService.getUserByPhoneNumber(user.getPhoneNumber());
        if (usuarioAux == null) {
            UserModel createdUser = userService.crearUsuario(user);
            if (createdUser != null) {
                logger.info(APP, "Usuario creado con éxito");
                return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("user.create", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
            } else {
                logger.warn(APP, "No se pudo crear el usuario");
                return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("user.notCreate", null, LocaleContextHolder.getLocale())), HttpStatus.BAD_REQUEST);
            }
        } else {
            logger.warn(APP, "No se pudo crear el usuario debido a que ya existe");
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("user.existPhone", null, LocaleContextHolder.getLocale())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        logger.info(APP, "Obteniendo todos los usuarios");
        List<UserModel> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.info(APP, "No se encontraron usuarios");
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("user.notValidList", null, LocaleContextHolder.getLocale())), HttpStatus.NO_CONTENT);
        } else {
            logger.info(APP, "Usuarios obtenidos con éxito");
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<?> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        logger.info(APP, "Obteniendo usuario por número de teléfono: {}", phoneNumber);

        UserModel user = userService.getUserByPhoneNumber(phoneNumber);
        if (user == null) {
            logger.warn(APP, "Usuario no encontrado para número de teléfono: {}", phoneNumber);
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("user.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        } else {
            logger.info(APP, "Usuario encontrado para número de teléfono: {}", phoneNumber);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping("/{phoneNumber}/add")
    public ResponseEntity<?> agregarVehiculo(@PathVariable("phoneNumber") String phoneNumber, @RequestBody VehicleModel vehiculo) {
        logger.info(APP, "Agregando vehículo para el usuario con número de teléfono: {}", phoneNumber);

        StringBuilder response = new StringBuilder();
        boolean ok = false;
        UserModel usuario = userService.getUserByPhoneNumber(phoneNumber);
        if (usuario != null) {
            userService.addVehicleToUser(phoneNumber, vehiculo, ok, response);
            if (ok) {
                logger.info(APP, "Vehículo agregado con éxito para el usuario: {}", phoneNumber);
                return new ResponseEntity<>(new ApiResponse(true, msg.getMessage(response.toString(), null, LocaleContextHolder.getLocale())), HttpStatus.OK);
            } else {
                logger.warn(APP, "No se pudo agregar el vehículo para el usuario: {}", phoneNumber , response.toString());
                return new ResponseEntity<>(new ApiResponse(false, msg.getMessage(response.toString(), null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn(APP, "Usuario no encontrado para agregar vehículo: {}", phoneNumber);
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage(response.toString(), null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{phoneNumber}/ListVehicles")
    public ResponseEntity<?> getUserVehicles(@PathVariable String phoneNumber) {
        logger.info(APP, "Obteniendo vehículos del usuario con número de teléfono: {}", phoneNumber);

        List<VehicleModel> vehicles = userService.getUserVehicles(phoneNumber);
        if (vehicles.isEmpty()) {
            logger.warn(APP, "No se encontraron vehículos para el usuario: {}", phoneNumber);
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("patent.notList", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        } else {
            logger.info(APP, "Vehículos obtenidos con éxito para el usuario: {}", phoneNumber);
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        }
    }
    
    @GetMapping("/{phoneNumbe}/movimientos")
    public ResponseEntity<?> getUserAccountMoviment(@PathVariable String phoneNumbe) {
        logger.info(APP, "Obteniendo movimientos de cuenta para el usuario con número de teléfono: {}", phoneNumbe);

        AccountModel cuentaCorriente = userService.getUserAccount(phoneNumbe);
        if (cuentaCorriente != null) {
            logger.info(APP, "Cuenta corriente encontrada para el usuario: {}", phoneNumbe);
            List<MovementModel> movimientos = ctacteService.getMoviments(cuentaCorriente.getId());
            if (movimientos.isEmpty()) {
                logger.info(APP, "No se encontraron movimientos de cuenta para el usuario: {}", phoneNumbe);
                return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("movement.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
            } else {
                logger.info(APP, "Movimientos de cuenta obtenidos con éxito para el usuario: {}", phoneNumbe);
                return new ResponseEntity<>(movimientos, HttpStatus.OK);
            }
        } else {
            logger.warn(APP, "No se encontró la cuenta corriente para obtener movimientos de cuenta del usuario: {}", phoneNumbe);
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("Account.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{phoneNumbe}/cta")
    public ResponseEntity<?> getUserAccount(@PathVariable String phoneNumbe) {
        logger.info(APP, "Obteniendo cuenta corriente del usuario con número de teléfono: {}", phoneNumbe);

        AccountModel cuentaCorriente = userService.getUserAccount(phoneNumbe);
        if (cuentaCorriente != null) {
            logger.info(APP, "Cuenta corriente obtenida con éxito para el usuario: {}", phoneNumbe);
            return new ResponseEntity<>(cuentaCorriente, HttpStatus.OK);
        } else {
            logger.warn(APP, "No se encontró la cuenta corriente para el usuario: {}", phoneNumbe);
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("Account.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{phoneNumbe}/saldo")
    public ResponseEntity<?> getUserAccountBalance(@PathVariable String phoneNumbe) {
        logger.info(APP, "Obteniendo saldo de la cuenta corriente del usuario con número de teléfono: {}", phoneNumbe);

        AccountModel cuentaCorriente = userService.getUserAccount(phoneNumbe);
        if (cuentaCorriente != null) {
            logger.info(APP, "Saldo de la cuenta corriente obtenido con éxito para el usuario: {}", phoneNumbe);
            return new ResponseEntity<>(cuentaCorriente.getSaldo(), HttpStatus.OK);
        } else {
            logger.warn(APP, "No se encontró la cuenta corriente para obtener el saldo del usuario: {}", phoneNumbe);
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("Account.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{phoneNumber}/sumar")
    public ResponseEntity<ApiResponse> sumarSaldoCuentaCorriente(@PathVariable("phoneNumber") String phoneNumber, @RequestBody double monto) {
        logger.info(APP, "Sumando saldo a la cuenta corriente del usuario con número de teléfono: {}", phoneNumber);

        UserModel usuario = userService.getUserByPhoneNumber(phoneNumber);
        if ((usuario == null) || (monto < 100)) {
            logger.warn(APP, "No se pudo sumar saldo a la cuenta corriente debido a un usuario no válido o un monto insuficiente: {}", phoneNumber);
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("Account.error", null, LocaleContextHolder.getLocale())), HttpStatus.BAD_REQUEST);
        }

        ctacteService.cargarCuentaCorriente(usuario.getAccount().getId(), monto, "Ingreso");
        logger.info(APP, "Saldo sumado con éxito a la cuenta corriente del usuario: {}", phoneNumber);

        return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("Account.balance.update", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
    }

    @PostMapping("/{phoneNumber}/restar")
    public ResponseEntity<ApiResponse> restarCuentaCorriente(@PathVariable("phoneNumber") String phoneNumber, @RequestBody double monto) {
        logger.info(APP, "Restando saldo de la cuenta corriente del usuario con número de teléfono: {}", phoneNumber);

        UserModel usuario = userService.getUserByPhoneNumber(phoneNumber);
        if (usuario == null) {
            logger.warn(APP, "No se pudo restar saldo de la cuenta corriente debido a un usuario no encontrado: {}", phoneNumber);
            return new ResponseEntity<>(new ApiResponse(false, "Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }

        ctacteService.cargarCuentaCorriente(usuario.getAccount().getId(), -monto, "Egreso");
        logger.info(APP, "Saldo restado con éxito de la cuenta corriente del usuario: {}", phoneNumber);

        return new ResponseEntity<>(new ApiResponse(true, "Egreso realizado exitosamente"), HttpStatus.OK);
    }

    @GetMapping("/{phoneNumbe}/estacionamientoActivo")
    public ResponseEntity<?> getParking(@PathVariable String phoneNumbe) {
        logger.info(APP, "Obteniendo estacionamiento activo del usuario con número de teléfono: {}", phoneNumbe);

        ParkingModel estacionamiento = parkingService.getUserParkingA(phoneNumbe);
        if (estacionamiento != null) {
            logger.info(APP, "Estacionamiento activo obtenido con éxito para el usuario: {}", phoneNumbe);
            return new ResponseEntity<>(estacionamiento, HttpStatus.OK);
        } else {
            logger.warn(APP, "No se encontró un estacionamiento activo para el usuario: {}", phoneNumbe);
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("parking.notValid.active", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
    }
}