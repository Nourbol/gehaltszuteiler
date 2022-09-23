package by.intexsoft.gehaltszuteiler.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record Employee(UUID mitarbeiterId,
                       BigDecimal salary) {
}
