package org.banco.cuentas.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.banco.cuentas.model.Cuenta;
import org.banco.cuentas.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }

    @PostMapping
    public Cuenta createCuenta(@RequestBody Cuenta cuenta, HttpServletRequest request) {
        String usuarioAlta = request.getHeader("user");
        cuenta.setUsuarioAlta(usuarioAlta);
        return cuentaService.createCuenta(cuenta);
    }

    @PutMapping("/{id}")
    public Cuenta updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta, HttpServletRequest request) {
        String usuarioAlta = request.getHeader("user");
        cuenta.setUsuarioAlta(usuarioAlta);
        return cuentaService.updateCuenta(id, cuenta);
    }

    @DeleteMapping("/{id}")
    public void deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
    }
}