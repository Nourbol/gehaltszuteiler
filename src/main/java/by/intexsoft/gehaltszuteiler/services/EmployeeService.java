package by.intexsoft.gehaltszuteiler.services;

import by.intexsoft.gehaltszuteiler.dtos.EmployeeWorkDuration;
import by.intexsoft.gehaltszuteiler.exceptions.HttpResponseReturnValueException;
import by.intexsoft.gehaltszuteiler.services.interfaces.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService implements IEmployeeService {

    private final RestOperations restTemplate;

    public Set<EmployeeWorkDuration> getEmployeeWorkDurations(String period) {
        String url = String.format("http://some-service/period/%s", period);
        log.debug("Getting employee work durations by GET request: {}", url);
        try {
            EmployeeWorkDuration[] employeeWorkDurations = restTemplate.getForObject(url, EmployeeWorkDuration[].class);
            if (employeeWorkDurations == null) {
                String errorMessage = String.format("GET request %s returned null", url);
                log.error(errorMessage);
                throw new HttpResponseReturnValueException(errorMessage);
            }
            return Arrays.stream(employeeWorkDurations).collect(Collectors.toSet());
        } catch (HttpStatusCodeException hsce) {
            int statusCode = hsce.getStatusCode().value();
            String errorMessage = String.format("Request returned %d status code", statusCode);
            log.error(errorMessage, hsce);
            throw new HttpResponseReturnValueException(errorMessage, hsce);
        }
    }

    public Set<EmployeeWorkDuration> getEmployeeWorkDurations(String period, Set<String> employeeIds) {
        Set<EmployeeWorkDuration> employeeWorkDurations = getEmployeeWorkDurations(period);
        return employeeWorkDurations.stream()
                .filter(employeeWorkDuration -> employeeIds.contains(employeeWorkDuration.mitarbeiterId().toString()))
                .collect(Collectors.toSet());
    }
}
