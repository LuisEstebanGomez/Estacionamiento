package ar.edu.unlp.estacionamiento.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.unlp.estacionamiento.models.HolidayModel;
import ar.edu.unlp.estacionamiento.repositories.HolidayRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HolidayServiceTest {

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private HolidayService holidayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllHolidays() {
       
        List<HolidayModel> holidays = new ArrayList<>();
        when(holidayRepository.findAll()).thenReturn(holidays);

        List<HolidayModel> result = holidayService.getAllHolidays();

        assertNotNull(result);
        assertEquals(holidays, result);
        
    }

    @Test
    void testGetHolidayById() {
        
        HolidayModel holiday = new HolidayModel();
        holiday.setId(1L);

        when(holidayRepository.findById(1L)).thenReturn(Optional.of(holiday));

        Optional<HolidayModel> result = holidayService.getHolidayById(1L);

        assertTrue(result.isPresent());
        assertEquals(holiday, result.get());
    }

    @Test
    void testCreateHoliday() {
        
    	HolidayModel holiday = new HolidayModel();

        when(holidayRepository.save(holiday)).thenReturn(holiday);

        HolidayModel result = holidayService.createHoliday(holiday);

        assertNotNull(result);
        assertEquals(holiday, result);
    }

    @Test
    void testDeleteHoliday() {
        
        doNothing().when(holidayRepository).deleteById(1L);

        assertDoesNotThrow(() -> holidayService.deleteHoliday(1L));
    }
}