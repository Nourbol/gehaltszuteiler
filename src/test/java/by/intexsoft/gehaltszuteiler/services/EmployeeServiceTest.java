package by.intexsoft.gehaltszuteiler.services;

import by.intexsoft.gehaltszuteiler.dtos.EmployeeWorkDuration;
import by.intexsoft.gehaltszuteiler.exceptions.HttpResponseReturnValueException;
import by.intexsoft.gehaltszuteiler.testutils.EmployeeWorkDurationTestBuilder;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock private RestOperations restTemplate;
    @InjectMocks private EmployeeService underTest;
    private static final EasyRandom generator = new EasyRandom();

    @Test
    void givenPeriod_whenGetEmployeeWorkDurations_thenReturn() {
        //given
        String period = "2022-07";
        EmployeeWorkDuration[] employeeWorkDurations = new EmployeeWorkDuration[]{
                EmployeeWorkDurationTestBuilder.withRandomData(generator).withValidDauer().build(),
                EmployeeWorkDurationTestBuilder.withRandomData(generator).withValidDauer().build()
        };
        String url = "http://some-service/period/2022-07";

        //when
        when(restTemplate.getForObject(anyString(), eq(EmployeeWorkDuration[].class))).thenReturn(employeeWorkDurations);
        underTest.getEmployeeWorkDurations(period);

        //then
        verify(restTemplate).getForObject(url, EmployeeWorkDuration[].class);
    }

    @Test
    void givenPeriodAndRequestReturningNull_whenGetEmployeeWorkDurations_thenThrowException() {
        //given
        String period = "2022-07";

        //when
        //then
        when(restTemplate.getForObject(anyString(), eq(EmployeeWorkDuration[].class))).thenReturn(null);
        assertThatThrownBy(() -> underTest.getEmployeeWorkDurations(period))
                .isInstanceOf(HttpResponseReturnValueException.class);
    }

    @Test
    void givenPeriodAndRequestReturningError_whenGetEmployeeWorkDurations_thenThrowException() {
        //given
        String period = "2022-07";

        //when
        //then
        when(restTemplate.getForObject(anyString(), eq(EmployeeWorkDuration[].class)))
                .thenThrow(new HttpStatusCodeException(HttpStatus.BAD_REQUEST) {});
        assertThatThrownBy(() -> underTest.getEmployeeWorkDurations(period))
                .isInstanceOf(HttpResponseReturnValueException.class);
    }

    @Test
    void givenPeriodAndEmployeeIds_whenGetEmployeeWorkDurations_thenReturnEmployeeWorkDurationSet() {
        //given
        String period = "2022-07";
        List<String> employeeIds = generator.objects(UUID.class, 2)
                .map(UUID::toString)
                .toList();
        EmployeeWorkDuration[] employeeWorkDurations = new EmployeeWorkDuration[]{
                EmployeeWorkDurationTestBuilder.withRandomData(generator)
                        .withValidDauer()
                        .withCustomMitarbeiterId(UUID.fromString(employeeIds.get(0)))
                        .build(),
                EmployeeWorkDurationTestBuilder.withRandomData(generator)
                        .withValidDauer()
                        .withCustomMitarbeiterId(UUID.fromString(employeeIds.get(1)))
                        .build(),
                EmployeeWorkDurationTestBuilder.withRandomData(generator)
                        .withValidDauer()
                        .withCustomMitarbeiterId(UUID.fromString(employeeIds.get(1)))
                        .build()
        };
        String url = "http://some-service/period/2022-07";

        //when
        when(restTemplate.getForObject(anyString(), eq(EmployeeWorkDuration[].class))).thenReturn(employeeWorkDurations);
        underTest.getEmployeeWorkDurations(period, new HashSet<>(employeeIds));

        //then
        verify(restTemplate).getForObject(url, EmployeeWorkDuration[].class);
    }
}