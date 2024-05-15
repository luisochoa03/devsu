package org.banco.cuentas.util;


import java.time.OffsetDateTime;
import java.util.Date;


public record DateRange(OffsetDateTime startDate, OffsetDateTime endDate) {
}