package org.banco.cuentas.util;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

public class DateUtil {

    private DateUtil() {
    }

    public static DateRange convertStringsToDates(String startDateInString, String endDateInString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Convert Strings to LocalDate
        LocalDate startDate = LocalDate.parse(startDateInString, formatter);
        LocalDate endDate = LocalDate.parse(endDateInString, formatter);

        OffsetDateTime startDateTime = startDate.atStartOfDay().toInstant(ZoneOffset.UTC).atOffset(ZoneOffset.UTC);
        OffsetDateTime endDateTime = endDate.atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC).atOffset(ZoneOffset.UTC);


        return new DateRange(startDateTime, endDateTime);
    }
}