package ar.edu.unlp.estacionamiento.models;

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
@Table(name = "Account")
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double saldo;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user")
    private UserModel user;
    @OneToMany(mappedBy = "ctaCte", cascade = CascadeType.ALL)
    private List<MovementModel> movements;

    // Constructor, getters y setters

    public AccountModel() {
        this.movements = new ArrayList<>();
    }

    public AccountModel(UserModel user) {
        this.user = user;
        this.movements = new ArrayList<>();
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

    public List<MovementModel> getMovements() {
		return movements;
	}

	public void setMovements(List<MovementModel> movements) {
		this.movements = movements;
	}

	public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public List<MovementModel> getMovimientos() {
        return movements;
    }

    public void agregarMovimiento(MovementModel movementsAux) {
        this.movements.add(movementsAux);
        movementsAux.setCtaCte(this);
    }
}
