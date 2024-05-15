package org.banco.cuentas;

import org.banco.cuentas.model.Cuenta;
import org.banco.cuentas.repository.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CuentasApplicationTests {

	@Autowired
	private CuentaRepository cuentaRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testCreateCuenta() {
		// Crear una cuenta
		Cuenta cuenta = new Cuenta();
		cuenta.setNumeroCuenta(Long.valueOf("123456"));
		cuenta.setSaldo(new BigDecimal("1000"));
		cuenta.setClienteId(100000000L);
		cuenta.setUsuarioAlta("admin");
		cuenta.setTipoCuenta("AHORRO");
		cuenta.setEstado(1);

		Cuenta savedCuenta = cuentaRepository.save(cuenta);

		Cuenta foundCuenta = cuentaRepository.findById(savedCuenta.getNumeroCuenta()).orElse(null);

		assertEquals(foundCuenta.getNumeroCuenta(),savedCuenta.getNumeroCuenta());

	}
}