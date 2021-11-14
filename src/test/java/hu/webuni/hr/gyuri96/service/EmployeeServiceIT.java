package hu.webuni.hr.gyuri96.service;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.gyuri96.dto.EmployeeDto;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceIT {

	private static final String URI = "/api/employee";
	private static final String URI_ID = "/api/employee/{id}";

	private static final int SELECTED_ID = 1;
	private static final int NEW_SALARY = 1250;

	@Autowired
	WebTestClient webTestClient;

	// TODO már DB-val, így az id-vel vannak gondok.
	@Test
	void createEmployee_newEmployeeIsListed() {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = getValidEmployee();
		saveEmployee(newEmployee).expectStatus().isOk();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter.subList(0, employeesBefore.size()))
				.usingRecursiveFieldByFieldElementComparator()
				.containsExactlyElementsOf(employeesBefore);

		assertThat(employeesAfter.get(employeesAfter.size() - 1))
				.usingRecursiveComparison().ignoringFields("id")
				.isEqualTo(newEmployee);
	}

	@Test
	void createEmployeeWithMissingRank_getBadRequestResponse(){
		List<EmployeeDto> employeesBefore = getAllEmployees();
		EmployeeDto newEmployee = getEmployeeWithMissingRank();

		saveEmployee(newEmployee)
				.expectStatus()
				.isBadRequest();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
	}

	@Test
	void updateEmployee_updatedEmployeeIsListedProperly() {
		EmployeeDto selectedEmployeeBefore = getSelectedEmployee(SELECTED_ID);
		selectedEmployeeBefore.setSalary(NEW_SALARY);

		updateEmployee(selectedEmployeeBefore).expectStatus().isOk();
		EmployeeDto selectedEmployeeAfter = getSelectedEmployee(SELECTED_ID);

		assertThat(selectedEmployeeAfter.getSalary())
				.isEqualTo(NEW_SALARY);
	}

	@Test
	void updateEmployeeWithMissingRank_getBadRequestResponse() {
		EmployeeDto newEmployee = getValidEmployee();
		EmployeeDto savedEmployee = saveEmployee(newEmployee).expectStatus().isOk()
				.expectBody(EmployeeDto.class)
				.returnResult().getResponseBody();

		List<EmployeeDto> employeesBefore = getAllEmployees();
		EmployeeDto invalidEmployee = getEmployeeWithMissingRank();
		invalidEmployee.setId(savedEmployee.getId());
		updateEmployee(invalidEmployee).expectStatus().isBadRequest();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
		assertThat(employeesAfter.get(employeesAfter.size()-1))
				.usingRecursiveComparison()
				.isEqualTo(savedEmployee);
	}

	private EmployeeDto getSelectedEmployee(int id){
		List<EmployeeDto> employees = getAllEmployees();
		return employees.get(id - 1);
	}

	private ResponseSpec updateEmployee(EmployeeDto selectedEmployee) {
		return webTestClient
				.put()
				.uri(uriBuilder -> uriBuilder
						.path(URI_ID)
						.build(selectedEmployee.getId()))
				.bodyValue(selectedEmployee)
				.exchange();
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

		Objects.requireNonNull(response).sort(Comparator.comparingLong(EmployeeDto::getId));
		return response;
	}

	private ResponseSpec saveEmployee(EmployeeDto newEmployee) {
		return webTestClient
				.post()
				.uri(URI)
				.bodyValue(newEmployee)
				.exchange();
	}

	private EmployeeDto getValidEmployee(){
		return new EmployeeDto(0L, "Varga Mária", "employee", 1280, LocalDate.of(2018, 4, 2), null);
	}

	private EmployeeDto getEmployeeWithMissingRank(){
		return new EmployeeDto(0L, "Horváth Miklós", null, 1350, LocalDate.of(2019, 10, 4), null);
	}


}
