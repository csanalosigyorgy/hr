package hu.webuni.hr.gyuri96.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.MethodArgumentNotValidException;

import hu.webuni.hr.gyuri96.dto.EmployeeDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceIT {

	private static final String URI = "/api/employee";
	private static final String URI_ID = "/api/employee/{id}";

	private static final int SELECTED_ID = 1;
	private static final int NEW_SALARY = 1250;

	@Autowired
	WebTestClient webTestClient;

	@Test
	void createEmployee_newEmployeeIsListed() {
		List<EmployeeDto> employeesBefore = getAllEmployees();
		EmployeeDto newEmployee = new EmployeeDto(4L, "Varga Mária", "employee", 1280, LocalDateTime.of(2018, 4, 2, 0,0));

		saveEmployee(newEmployee);
		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter.subList(0, employeesBefore.size()))
				.usingRecursiveFieldByFieldElementComparator()
				.containsExactlyElementsOf(employeesBefore);

		assertThat(employeesAfter.get(employeesAfter.size() - 1))
				.usingRecursiveComparison()
				.isEqualTo(newEmployee);
	}

	// TODO!
//	@Test
//	void createEmployeeWithFutureEntryDate_getValidationException() {
//		try {
//			EmployeeDto newEmployee = new EmployeeDto(4L, "Varga Mária", "employee", 1280, LocalDateTime.of(2044, 4, 2, 0, 0));
//			saveEmployee(newEmployee);
//		} catch (Exception e){
//			assertThat(e).isInstanceOf(MethodArgumentNotValidException.class);
//		}
//	}

	@Test
	void updateEmployee_updatedEmployeeIsListedProperly() {
		EmployeeDto selectedEmployeeBefore = getSelectedEmployee(SELECTED_ID);
		selectedEmployeeBefore.setSalary(NEW_SALARY);

		updateEmployee(selectedEmployeeBefore);
		EmployeeDto selectedEmployeeAfter = getSelectedEmployee(SELECTED_ID);

		assertThat(selectedEmployeeAfter.getSalary())
				.isEqualTo(NEW_SALARY);
	}

	private EmployeeDto getSelectedEmployee(int id){
		List<EmployeeDto> employees = getAllEmployees();
		return employees.get(id - 1);
	}

	private void updateEmployee(EmployeeDto selectedEmployee) {
		webTestClient
				.put()
				.uri(uriBuilder -> uriBuilder
						.path(URI_ID)
						.build(selectedEmployee.getId()))
				.bodyValue(selectedEmployee)
				.exchange()
				.expectStatus().isOk();
	}

	private List<EmployeeDto> getAllEmployees() {
		List<EmployeeDto> response = webTestClient
				.get()
				.uri(URI)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(EmployeeDto.class)
				.returnResult()
				.getResponseBody();

		Collections.sort(response, Comparator.comparingLong(EmployeeDto::getId));
		return response;
	}

	private void saveEmployee(EmployeeDto newEmployee) {
		webTestClient
				.post()
				.uri(URI)
				.bodyValue(newEmployee)
				.exchange()
				.expectStatus().isOk();
	}
}
