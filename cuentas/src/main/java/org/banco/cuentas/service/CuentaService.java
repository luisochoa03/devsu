package org.banco.cuentas.service;


import org.banco.cuentas.model.Cuenta;

import java.util.List;

public interface CuentaService {
    List<Cuenta> getAllCuentas();
    Cuenta createCuenta(Cuenta cuenta);
    Cuenta updateCuenta(Long id, Cuenta cuentaDetails);
    void deleteCuenta(Long id);
}