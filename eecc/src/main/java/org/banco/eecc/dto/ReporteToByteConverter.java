package org.banco.eecc.dto;


import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

public class ReporteToByteConverter {

    private ReporteToByteConverter() {
    }

    public static byte[] convert(Reporte reporte) {
        StringJoiner sj = new StringJoiner("\n---------------------------------------------------------------------------------------\n");

        sj.add("ESTADOS DE CUENTAS BANCO DEMO");
        sj.add("Id del cliente = " + reporte.getClienteId());

        sj.add("Numero Operacion | Operacion | Importe |  Saldo actual |  fecha de Operacion");

        for (CuentaReporte cuenta : reporte.getCuentas()) {
            sj.add("Cuenta: " + cuenta.getNumero());

            for (MovimientoReporte movimiento : cuenta.getMovimientos()) {
                sj.add(String.format("\t%d\t|\t%s\t|\t%s\t|\t%s\t|\t%s",
                        movimiento.getId(),
                        movimiento.getTipo(),
                        movimiento.getImporte(),
                        movimiento.getSaldo(),
                        movimiento.getFecha()));
            }
        }

        return sj.toString().getBytes(StandardCharsets.UTF_8);
    }
}