package ar.edu.unlp.estacionamiento.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="vehicle")
public class VehicleModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String patente;
	// Relación Many-to-One con usuario
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user")
    private UserModel user;
	
    // GETTERS AND SETTERS
       
 	public UserModel getUser() {
		return user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente = patente;
	}
	
	public void setUser(UserModel automovilista) {
		this.user=automovilista;
		
	}
	   
}
