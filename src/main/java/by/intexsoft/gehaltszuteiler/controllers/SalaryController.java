package by.intexsoft.gehaltszuteiler.controllers;

import by.intexsoft.gehaltszuteiler.dtos.Employee;
import by.intexsoft.gehaltszuteiler.services.interfaces.ISalaryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class SalaryController {

    private final ISalaryManager salaryManager;

    @GetMapping("/period/{period}")
    public List<Employee> getEmployees(@RequestParam("period") String period,
                                       @RequestParam("mitarbeiterIds") Set<String> mitarbeiterIds,
                                       @RequestParam("totalSalary") BigDecimal totalSalary) {
        return salaryManager.manageSalariesInTeam(mitarbeiterIds, totalSalary, period);
    }
}
