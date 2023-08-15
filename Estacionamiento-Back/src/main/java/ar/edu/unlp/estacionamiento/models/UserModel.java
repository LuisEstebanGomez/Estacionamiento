package ar.edu.unlp.estacionamiento.models;

import java.util.Collection;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class UserModel  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String phoneNumber;
	private String password;
	private String email;

	//Relación One-to-One con CuentaCorriente
	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AccountModel account;
    //Relación One-to-Many con Vehículo
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<VehicleModel> vehiculos;
    
    // GETTERS AND SETTERS
    
	public void setPassword(String password) {
		this.password = password;
	}

	public void setVehiculos(List<VehicleModel> vehiculos) {
		this.vehiculos = vehiculos;
	}
    
  	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<VehicleModel>  getVehiculos() {
		return this.vehiculos;
	}



	public AccountModel getAccount() {
		return this.account;
	}

    public void setCuentaCorriente(AccountModel accountAux) {
	    this.account = accountAux;
	    accountAux.setUser(this);
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	public void setAccount(AccountModel account) {
		this.account = account;
	    account.setUser(this);
		
	}


	
}
