package com.example.estacionamiento.controllers;

import com.example.estacionamiento.models.HolidayModel;
import com.example.estacionamiento.repositories.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    
    @GetMapping
    public ResponseEntity<List<HolidayModel>> getAllHolidays() {
        List<HolidayModel> holidays = holidayRepository.findAll();
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HolidayModel> getHolidayById(@PathVariable Long id) {
        HolidayModel holiday = holidayRepository.findById(id).orElse(null);
        if (holiday == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(holiday, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HolidayModel> createHoliday(@RequestBody HolidayModel holiday) {
        HolidayModel newHoliday = holidayRepository.save(holiday);
        return new ResponseEntity<>(newHoliday, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HolidayModel> updateHoliday(@PathVariable Long id, @RequestBody HolidayModel holidayDetails) {
        HolidayModel holiday = holidayRepository.findById(id).orElse(null);
        if (holiday == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        holiday.setDate(holidayDetails.getDate());

        HolidayModel updatedHoliday = holidayRepository.save(holiday);
        return new ResponseEntity<>(updatedHoliday, HttpStatus.OK);
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        HolidayModel holiday = holidayRepository.findById(id).orElse(null);
        if (holiday == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        holidayRepository.delete(holiday);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}