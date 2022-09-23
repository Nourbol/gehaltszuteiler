package by.intexsoft.gehaltszuteiler.testutils;

import by.intexsoft.gehaltszuteiler.dtos.Employee;
import by.intexsoft.gehaltszuteiler.dtos.EmployeeWorkDuration;
import org.jeasy.random.EasyRandom;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class EmployeeWorkDurationTestBuilder {

    private UUID mitarbeiterId;
    private LocalTime beginn;
    private LocalTime ende;
    private Integer dauer;

    private EmployeeWorkDurationTestBuilder() {}

    public static EmployeeWorkDurationTestBuilder builder() {
        return new EmployeeWorkDurationTestBuilder();
    }

    public static Stream<EmployeeWorkDuration> buildEmployeeWorkDurations(Supplier<EmployeeWorkDurationTestBuilder> buildPattern,
                                                                          long streamSize) {
        return Stream
                .generate(buildPattern)
                .map(EmployeeWorkDurationTestBuilder::build)
                .limit(streamSize);
    }

    public static EmployeeWorkDurationTestBuilder withRandomData(EasyRandom generator) {
        return builder()
                .withRandomMitarbeiterId()
                .withCustomBeginn(generator.nextObject(LocalTime.class))
                .withCustomEnde(generator.nextObject(LocalTime.class))
                .withCustomDauer(generator.nextObject(Integer.class));
    }

    public EmployeeWorkDurationTestBuilder withRandomMitarbeiterId() {
        this.mitarbeiterId = UUID.randomUUID();
        return this;
    }

    public EmployeeWorkDurationTestBuilder withCustomMitarbeiterId(UUID mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
        return this;
    }

    public EmployeeWorkDurationTestBuilder withCustomBeginn(LocalTime beginn) {
        this.beginn = beginn;
        return this;
    }

    public EmployeeWorkDurationTestBuilder withCustomEnde(LocalTime ende) {
        this.ende = ende;
        return this;
    }

    public EmployeeWorkDurationTestBuilder withCustomDauer(Integer dauer) {
        this.dauer = dauer;
        return this;
    }

    public EmployeeWorkDurationTestBuilder withValidDauer() {
        this.dauer = new Random().nextInt(1440);
        return this;
    }

    public EmployeeWorkDuration build() {
        return EmployeeWorkDuration
                .builder()
                .mitarbeiterId(mitarbeiterId)
                .beginn(beginn)
                .ende(ende)
                .dauer(dauer)
                .build();
    }
}
