package org.banco.cuentas.service.strategy;


import org.banco.cuentas.model.CustomException;
import org.banco.cuentas.model.ErrorCode;

import java.math.BigDecimal;

public class OperacionDebito implements Operacion {
    @Override
    public BigDecimal ejecutar(BigDecimal saldo, BigDecimal valor) {
        if (saldo.compareTo(valor) < 0) {
            throw new CustomException(ErrorCode.INSUFFICIENT_BALANCE);
        }
        return saldo.subtract(valor);
    }
}