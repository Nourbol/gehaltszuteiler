package by.intexsoft.gehaltszuteiler.services;

import by.intexsoft.gehaltszuteiler.dtos.Employee;
import by.intexsoft.gehaltszuteiler.dtos.EmployeeWorkDuration;
import by.intexsoft.gehaltszuteiler.services.interfaces.IEmployeeService;
import by.intexsoft.gehaltszuteiler.services.interfaces.ISalaryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryManager implements ISalaryManager {

    private final IEmployeeService employeeService;

    public List<Employee> manageSalariesInTeam(Set<String> members,
                                               BigDecimal totalSalary,
                                               String period) {
        log.debug("Distributing total salary: {} among team members with ids: {} during this period: {}",
                totalSalary, members, period);
        Supplier<Stream<UUID>> employeeIds = () -> members.stream().map(UUID::fromString);
        Supplier<Set<EmployeeWorkDuration>> employeeWorkDurations =
                () -> employeeService.getEmployeeWorkDurations(period, members);

        Map<UUID, Long> employeeTotalMinutes = getEmployeesWithTotalMinutes(employeeIds.get(), employeeWorkDurations);
        Long totalMinutes = employeeWorkDurations.get()
                .stream()
                .map(EmployeeWorkDuration::dauer)
                .mapToLong(minutes -> minutes)
                .sum();

        return employeeIds.get()
                .map(employeeId -> {
                    BigDecimal workPercent =
                            BigDecimal.valueOf(employeeTotalMinutes.get(employeeId).doubleValue() / totalMinutes.doubleValue());
                    BigDecimal salary = totalSalary.multiply(workPercent);
                    return Employee.builder()
                            .mitarbeiterId(employeeId)
                            .salary(salary)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private Map<UUID, Long> getEmployeesWithTotalMinutes(Stream<UUID> employeeIds, Supplier<Set<EmployeeWorkDuration>> employeeWorkDurations) {
        Map<UUID, Long> employeeTotalMinutes = new HashMap<>();
        employeeIds.forEach(employeeId ->
                employeeTotalMinutes.put(employeeId, getEmployeeTotalMinutes(employeeId, employeeWorkDurations.get())));
        return employeeTotalMinutes;
    }

    private Long getEmployeeTotalMinutes(UUID employeeId, Set<EmployeeWorkDuration> employeeWorkDurations) {
        return employeeWorkDurations.stream()
                .filter(employeeWorkDuration -> employeeWorkDuration.mitarbeiterId().equals(employeeId))
                .mapToLong(EmployeeWorkDuration::dauer)
                .sum();
    }
}
