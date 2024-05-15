package org.banco.cuentas.controller;

import org.banco.cuentas.model.Movimiento;
import org.banco.cuentas.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> getAllMovimientos() {
        return movimientoService.getAllMovimientos();
    }

    @PostMapping
    public Movimiento createMovimiento(@RequestBody Movimiento movimiento) {
        return movimientoService.createMovimiento(movimiento);
    }

    @PutMapping("/{id}")
    public Movimiento updateMovimiento(@PathVariable Long id, @RequestBody Movimiento movimientoDetails) {
        return movimientoService.updateMovimiento(id, movimientoDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteMovimiento(@PathVariable Long id) {
        movimientoService.deleteMovimiento(id);
    }
}