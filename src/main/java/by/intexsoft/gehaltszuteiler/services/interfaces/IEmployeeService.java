package by.intexsoft.gehaltszuteiler.services.interfaces;

import by.intexsoft.gehaltszuteiler.dtos.EmployeeWorkDuration;

import java.util.Set;

public interface IEmployeeService {

    /**
     * Gets work duration of each employee in a company of the period, that is passed as an argument.
     *
     * @param period period of statistics on employee time spent at work.
     * @return set of representations of employees time spent at work during the given period.
     * @see EmployeeWorkDuration
     */
    Set<EmployeeWorkDuration> getEmployeeWorkDurations(String period);

    /**
     * Gets work duration of employees of the period, that is passed as an argument.
     * Employees identifiers are for finding necessary employees.
     *
     * @param period period of statistics on employee time spent at work.
     * @param employeeIds set of employees' identifiers
     * @return set of representations of employees time spent at work during the given period.
     * @see EmployeeWorkDuration
     */
    Set<EmployeeWorkDuration> getEmployeeWorkDurations(String period, Set<String> employeeIds);
}
