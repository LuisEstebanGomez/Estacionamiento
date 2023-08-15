package ar.edu.unlp.estacionamiento.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.estacionamiento.models.HolidayModel;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayModel, Long> {
    boolean existsByDate(String string);
}