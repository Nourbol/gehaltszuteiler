package by.intexsoft.gehaltszuteiler.services;

import by.intexsoft.gehaltszuteiler.dtos.Employee;
import by.intexsoft.gehaltszuteiler.dtos.EmployeeWorkDuration;
import by.intexsoft.gehaltszuteiler.services.interfaces.IEmployeeService;
import by.intexsoft.gehaltszuteiler.testutils.EmployeeWorkDurationTestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class SalaryManagerTest {

    @Mock private IEmployeeService employeeService;
    @InjectMocks private SalaryManager underTest;
    private static final EasyRandom generator = new EasyRandom();

    @Test
    void givenMembersAndPeriodAndSalary_whenManageSalariesInTeam_thenReturnEmployeeList() {
        //given
        String period = "2022-7";
        List<String> employeeIds = generator.objects(UUID.class, 4)
                .map(UUID::toString)
                .toList();
        Set<EmployeeWorkDuration> employeeWorkDurations = EmployeeWorkDurationTestBuilder
                .buildEmployeeWorkDurations(() ->
                        EmployeeWorkDurationTestBuilder.withRandomData(generator)
                                .withValidDauer()
                                .withCustomMitarbeiterId(UUID.fromString(employeeIds.get(new Random().nextInt(4)))),
                4).collect(Collectors.toSet());
        BigDecimal totalSalary = BigDecimal.valueOf(1000000);

        //when
        when(employeeService.getEmployeeWorkDurations(anyString(), anySet()))
                .thenReturn(employeeWorkDurations);
        List<Employee> employees = underTest.manageSalariesInTeam(new HashSet<>(employeeIds), totalSalary, period);

        //then
        verify(employeeService, times(5)).getEmployeeWorkDurations(period, new HashSet<>(employeeIds));
        log.info("{}", employees);
    }
}