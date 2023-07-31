package com.example.estacionamiento.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.estacionamiento.models.CtaCteModel;
import com.example.estacionamiento.models.MovimentModel;
import com.example.estacionamiento.repositories.CtaCteRepository;
@Service
public class CtaCteService {
		
	@Autowired
	private CtaCteRepository ctaCteRepository;
	
    public CtaCteModel createCtaCte() {
	        CtaCteModel ctaCte = new CtaCteModel();
	        ctaCte.setSaldo(10000.0); // SALDO INICIAL 10.000
	        return ctaCteRepository.save(ctaCte);
    }

    public void guardarCuentaCorriente(CtaCteModel cuentaCorriente) {
    	 ctaCteRepository.save(cuentaCorriente);		
	}

    public CtaCteModel cargarCuentaCorriente(Long id, double monto, String tipo) {
    	CtaCteModel cuentaCorriente =  ctaCteRepository.findById(id).orElseThrow();

        double saldoActual = cuentaCorriente.getSaldo();
        double nuevoSaldo = saldoActual + monto;
        cuentaCorriente.setSaldo(nuevoSaldo);
              
        // REGISTRAR MOVIMIENTO
        MovimentModel movimiento = new MovimentModel(monto, nuevoSaldo, tipo);
        cuentaCorriente.agregarMovimiento(movimiento);

        return  ctaCteRepository.save(cuentaCorriente);
    }

	public List<MovimentModel> getMoviments(Long id) {
		CtaCteModel cuentaCorriente =  ctaCteRepository.findById(id).orElseThrow();
		if (cuentaCorriente != null) {
            return cuentaCorriente.getMovimientos();
        } else {
            return Collections.emptyList();
        }		
	}

	public List<MovimentModel> getMovimientosByCtaCteId(Long ctaCteId) {
        CtaCteModel ctaCte = ctaCteRepository.findById(ctaCteId).orElse(null);
        if (ctaCte != null) {
            return ctaCte.getMovimientos();
        } else {
            
            return null;
        }
    }
    
}
