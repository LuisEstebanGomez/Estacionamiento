package com.example.estacionamiento.repositories;

import com.example.estacionamiento.models.HolidayModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayModel, Long> {
    List<HolidayModel> findByDate(String date);
}