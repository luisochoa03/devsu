package org.banco.cuentas.service.strategy;

import java.math.BigDecimal;

public class OperacionCredito implements Operacion {
    @Override
    public BigDecimal ejecutar(BigDecimal saldo, BigDecimal valor) {
        return saldo.add(valor);
    }
}