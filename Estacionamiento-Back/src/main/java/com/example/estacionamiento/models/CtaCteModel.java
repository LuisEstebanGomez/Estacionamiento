package com.example.estacionamiento.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ctacte")
public class CtaCteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double saldo;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user")
    private UserModel user;
    @OneToMany(mappedBy = "ctaCte", cascade = CascadeType.ALL)
    private List<MovimentModel> movimientos;

    // Constructor, getters y setters

    public CtaCteModel() {
        this.movimientos = new ArrayList<>();
    }

    public CtaCteModel(UserModel user) {
        this.user = user;
        this.movimientos = new ArrayList<>();
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double nuevoSaldo) {
        this.saldo = nuevoSaldo;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public List<MovimentModel> getMovimientos() {
        return movimientos;
    }

    public void agregarMovimiento(MovimentModel movimiento) {
        this.movimientos.add(movimiento);
        movimiento.setCtaCte(this);
    }
}
