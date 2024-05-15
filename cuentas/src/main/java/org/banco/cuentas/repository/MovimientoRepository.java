package org.banco.cuentas.repository;


import org.banco.cuentas.model.Cuenta;
import org.banco.cuentas.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaInAndFechaBetween(List<Cuenta> cuentas, OffsetDateTime fechaInicio, OffsetDateTime fechaFin);

}