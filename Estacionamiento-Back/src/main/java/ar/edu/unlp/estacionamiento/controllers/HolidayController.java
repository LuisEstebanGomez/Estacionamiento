package ar.edu.unlp.estacionamiento.controllers;

import ar.edu.unlp.estacionamiento.models.HolidayModel;
import ar.edu.unlp.estacionamiento.repositories.HolidayRepository;
import ar.edu.unlp.estacionamiento.security.dto.ApiResponse;

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

  
    @GetMapping
    public ResponseEntity<List<HolidayModel>> getAllHolidays() {
        List<HolidayModel> holidays = holidayRepository.findAll();
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHolidayById(@PathVariable Long id) {
        HolidayModel holiday = holidayRepository.findById(id).orElse(null);
        if (holiday == null) {
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("holiday.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(holiday, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createHoliday(@RequestBody HolidayModel holiday) {
    	// Verificar si el feriado ya existe en la base de datos
        if (holidayRepository.existsByDate(holiday.getDate())) {
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("holiday.exist", null, LocaleContextHolder.getLocale())), HttpStatus.BAD_REQUEST);
        }
        
        HolidayModel newHoliday = holidayRepository.save(holiday);
        return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("holiday.create", null, LocaleContextHolder.getLocale())), HttpStatus.CREATED);
    }
   

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateHoliday(@PathVariable Long id, @RequestBody HolidayModel holidayDetails) {
        HolidayModel holiday = holidayRepository.findById(id).orElse(null);
        if (holiday == null) {
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("holiday.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
        holiday.setDate(holidayDetails.getDate());

        HolidayModel updatedHoliday = holidayRepository.save(holiday);
        return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("holiday.update", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteHoliday(@PathVariable Long id) {
        HolidayModel holiday = holidayRepository.findById(id).orElse(null);
        if (holiday == null) {
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("holiday.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
        holidayRepository.delete(holiday);
        return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("holiday.delete", null, LocaleContextHolder.getLocale())), HttpStatus.NO_CONTENT);
    }

}