package com.example.estacionamiento.repositories;

import com.example.estacionamiento.models.MovimentModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovimentRepository extends JpaRepository<MovimentModel, Long> {
 
}