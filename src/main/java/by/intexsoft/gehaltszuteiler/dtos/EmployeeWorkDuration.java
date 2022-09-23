package by.intexsoft.gehaltszuteiler.dtos;

import lombok.Builder;

import java.time.LocalTime;
import java.util.UUID;

@Builder
public record EmployeeWorkDuration(UUID mitarbeiterId,
                                   LocalTime beginn,
                                   LocalTime ende,
                                   Integer dauer) {
}
