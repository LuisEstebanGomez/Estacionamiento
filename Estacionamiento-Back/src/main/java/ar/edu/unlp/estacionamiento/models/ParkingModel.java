package ar.edu.unlp.estacionamiento.models;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="parking")
public class ParkingModel {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 private LocalDateTime horaInicio;
	 private LocalDateTime horaFin;
     private double importe;
     private boolean activo;
     @ManyToOne
     @JoinColumn(name = "user")
     private UserModel user;
     private String patente;
     
     // GETTERS AND SETTER
          
	 public Long getId() {
		return id;
	}
	 
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}
	
	public void setHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public LocalDateTime getHoraFin() {
		return horaFin;
	}
	
	public void setHoraFin(LocalDateTime horaFin) {
		this.horaFin = horaFin;
	}
	
	public void setActivo(boolean b) {
		this.activo=b;	
	}
	
	public double getImporte() {
		return importe;
	}
	
	public boolean isActivo() {
		return activo;
	}
	
	public void setImporte(double importeTotal) {
		this.importe=importeTotal;
		
	}
	
	public void setUser(UserModel automovilista) {
		this.user=automovilista;
	}
	
	public String getPatente() {
		return patente;
	}

	public UserModel getUser() {
		return this.user;
	}
	
	public void setPatente(String patente) {
		this.patente=patente;		
	}
	
}
