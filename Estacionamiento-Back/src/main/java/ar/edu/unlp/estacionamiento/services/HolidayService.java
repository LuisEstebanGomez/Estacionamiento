package ar.edu.unlp.estacionamiento.services;

import ar.edu.unlp.estacionamiento.models.HolidayModel;
import ar.edu.unlp.estacionamiento.repositories.HolidayRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    @Autowired
    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public List<HolidayModel> getAllHolidays() {
        return holidayRepository.findAll();
    }

    public Optional<HolidayModel> getHolidayById(Long id) {
        return holidayRepository.findById(id);
    }

    public HolidayModel createHoliday(HolidayModel holiday) {
        return holidayRepository.save(holiday);
    }

    public void deleteHoliday(Long id) {
        holidayRepository.deleteById(id);
    }

  

}