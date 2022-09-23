package by.intexsoft.gehaltszuteiler.services.interfaces;

import by.intexsoft.gehaltszuteiler.dtos.Employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ISalaryManager {

    /**
     * Distributes total salary among employees of a team.
     * Team is found by a set of employee identifiers.
     * Method distributes the salary by employee's time spent at work during the given period.
     *
     * @param employeeIds a set of employees' identifiers
     * @param totalSalary salary which has to be distributed among team members
     * @param period period of statistics on employee time spent at work.
     * @return a list of representations of employees.
     */
    List<Employee> manageSalariesInTeam(Set<String> employeeIds, BigDecimal totalSalary, String period);
}
