package ar.edu.unlp.estacionamiento.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.estacionamiento.models.AccountModel;
import ar.edu.unlp.estacionamiento.models.MovementModel;
import ar.edu.unlp.estacionamiento.repositories.AccountRepository;



@Service
public class AccountService {
		
	@Autowired
	private AccountRepository ctaCteRepository;
	
    public AccountModel createCtaCte() {
	        AccountModel ctaCte = new AccountModel();
	        ctaCte.setSaldo(10000.0); // SALDO INICIAL 10.000
	        return ctaCteRepository.save(ctaCte);
    }

    public void guardarCuentaCorriente(AccountModel cuentaCorriente) {
    	 ctaCteRepository.save(cuentaCorriente);		
	}

    public AccountModel cargarCuentaCorriente(Long id, double monto, String tipo) {
    	AccountModel cuentaCorriente =  ctaCteRepository.findById(id).orElseThrow();

        double saldoActual = cuentaCorriente.getSaldo();
        double nuevoSaldo = saldoActual + monto;
        cuentaCorriente.setSaldo(nuevoSaldo);
              
        // REGISTRAR MOVIMIENTO
        MovementModel movimiento = new MovementModel(monto, nuevoSaldo, tipo);
        cuentaCorriente.agregarMovimiento(movimiento);

        return  ctaCteRepository.save(cuentaCorriente);
    }
    
    

	public List<MovementModel> getMoviments(Long id) {
		AccountModel cuentaCorriente =  ctaCteRepository.findById(id).orElseThrow();
		if (cuentaCorriente != null) {
            return cuentaCorriente.getMovimientos();
        } else {
            return Collections.emptyList();
        }		
	}

	public List<MovementModel> getMovimientosByCtaCteId(Long ctaCteId) {
        AccountModel ctaCte = ctaCteRepository.findById(ctaCteId).orElse(null);
        if (ctaCte != null) {
            return ctaCte.getMovimientos();
        } else {
            
            return null;
        }
    } 
}
