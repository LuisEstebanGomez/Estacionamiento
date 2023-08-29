package ar.edu.unlp.estacionamiento.controllers;

import ar.edu.unlp.estacionamiento.models.HolidayModel;
import ar.edu.unlp.estacionamiento.repositories.HolidayRepository;
import ar.edu.unlp.estacionamiento.security.dto.ApiResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holidays")
public class HolidayController {
	
	    private final HolidayRepository holidayRepository;
	
	    @Autowired
	    public HolidayController(HolidayRepository holidayRepository) {
	        this.holidayRepository = holidayRepository;
	    }
	    
	    @Autowired
		MessageSource msg;
	    
	     private static final Logger logger = LogManager.getLogger(AccountController.class);
		 public static final Marker APP = MarkerManager.getMarker("APP");
	
	     @GetMapping
	     public ResponseEntity<List<HolidayModel>> getAllHolidays() {
		        logger.info(APP, "Obteniendo todos los feriados");
		        List<HolidayModel> holidays = holidayRepository.findAll();
		        return new ResponseEntity<>(holidays, HttpStatus.OK);
		    }
	     
        @GetMapping("/{id}")
	    public ResponseEntity<?> getHolidayById(@PathVariable Long id) {
	        logger.info(APP, "Obteniendo feriado por ID: {}", id);
	        HolidayModel holiday = holidayRepository.findById(id).orElse(null);
	        if (holiday == null) {
	            logger.warn(APP, "Feriado no encontrado para ID: {}", id);
	            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("holiday.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(holiday, HttpStatus.OK);
	    }

	    @PostMapping
	    public ResponseEntity<ApiResponse> createHoliday(@RequestBody HolidayModel holiday) {
	        logger.info(APP, "Creando nuevo feriado");
	        if (holidayRepository.existsByDate(holiday.getDate())) {
	            logger.warn(APP, "El feriado ya existe para la fecha: {}", holiday.getDate());
	            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("holiday.exist", null, LocaleContextHolder.getLocale())), HttpStatus.BAD_REQUEST);
	        }
	        
	        HolidayModel newHoliday = holidayRepository.save(holiday);
	        logger.info(APP, "Feriado creado con éxito para la fecha: {}", newHoliday.getDate());
	        return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("holiday.create", null, LocaleContextHolder.getLocale())), HttpStatus.CREATED);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<ApiResponse> updateHoliday(@PathVariable Long id, @RequestBody HolidayModel holidayDetails) {
	        logger.info(APP, "Actualizando feriado por ID: {}", id);
	        HolidayModel holiday = holidayRepository.findById(id).orElse(null);
	        if (holiday == null) {
	            logger.warn(APP, "Feriado no encontrado para ID: {}", id);
	            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("holiday.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
	        }
	        holiday.setDate(holidayDetails.getDate());

	        HolidayModel updatedHoliday = holidayRepository.save(holiday);
	        logger.info(APP, "Feriado actualizado con éxito para ID: {}", id);
	        return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("holiday.update", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse> deleteHoliday(@PathVariable Long id) {
	        logger.info(APP, "Eliminando feriado por ID: {}", id);
	        HolidayModel holiday = holidayRepository.findById(id).orElse(null);
	        if (holiday == null) {
	            logger.warn(APP, "Feriado no encontrado para ID: {}", id);
	            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("holiday.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
	        }
	        holidayRepository.delete(holiday);
	        logger.info(APP, "Feriado eliminado con éxito para ID: {}", id);
	        return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("holiday.delete", null, LocaleContextHolder.getLocale())), HttpStatus.NO_CONTENT);
	    }   
     	    
}