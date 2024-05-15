package org.banco.cuentas.service;

import org.banco.cuentas.model.Cuenta;
import org.banco.cuentas.model.CustomException;
import org.banco.cuentas.model.ErrorCode;
import org.banco.cuentas.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public List<Cuenta> getAllCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        if (cuentas.isEmpty()) {
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return cuentas;
    }
    @Override
    public Cuenta createCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Cuenta updateCuenta(Long id, Cuenta cuentaDetails) {
        Cuenta existingCuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        cuentaDetails.setNumeroCuenta(existingCuenta.getNumeroCuenta());
        cuentaDetails.setFechaAlta(existingCuenta.getFechaAlta());
        cuentaDetails.setUsuarioAlta(existingCuenta.getUsuarioAlta());
        cuentaDetails.setClienteId(existingCuenta.getClienteId());
        cuentaDetails.setUsuarioModificacion(cuentaDetails.getUsuarioAlta());
        return cuentaRepository.save(cuentaDetails);
    }

    @Override
    public void deleteCuenta(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        cuentaRepository.deleteById(id);
    }
}