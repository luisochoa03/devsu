package org.banco.cuentas.service.strategy;

import java.math.BigDecimal;

public interface Operacion {
    BigDecimal ejecutar(BigDecimal saldo, BigDecimal valor);
}