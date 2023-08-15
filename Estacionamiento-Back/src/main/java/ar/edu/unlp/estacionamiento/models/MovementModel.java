package ar.edu.unlp.estacionamiento.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "moviment")
public class MovementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fecha;
    private double monto;
    private String tipo;
 	private double saldoResultante;
	@JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ctacte")
    private AccountModel ctaCte;

    // Constructor, getters y setters

    public MovementModel() {
        this.fecha = new Date();
    }

    public MovementModel(double monto, double saldoResultante, String tipo) {
        this.fecha = new Date();
        this.monto = monto;
        this.tipo=tipo;
        this.saldoResultante = saldoResultante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getSaldoResultante() {
        return saldoResultante;
    }

    public void setSaldoResultante(double saldoResultante) {
        this.saldoResultante = saldoResultante;
    }

    public AccountModel getCtaCte() {
        return ctaCte;
    }

    public void setCtaCte(AccountModel ctaCte) {
        this.ctaCte = ctaCte;
    }
     
    public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}