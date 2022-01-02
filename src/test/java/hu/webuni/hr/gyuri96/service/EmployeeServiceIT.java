package hu.webuni.hr.gyuri96.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.gyuri96.dto.EmployeeDto;
import hu.webuni.hr.gyuri96.dto.LoginDto;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class EmployeeServiceIT {

	private static final String URI = "/api/employee";
	private static final String URI_ID = "/api/employee/{id}";

	private static final int SELECTED_ID = 1;
	private static final int NEW_SALARY = 1250;
	private String jwt;

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmployeeRepository employeeRepository;

	String username = "user";
	String pass = "pass";

	@BeforeEach
	public void init(){
		if(employeeRepository.findByUsername(username).isEmpty()){
			Employee employee = new Employee();
			employee.setUsername(username);
			employee.setPassword(passwordEncoder.encode(pass));
			employeeRepository.save(employee);
		}

		jwt = webTestClient
				.post()
				.uri("/api/login")
				.bodyValue(new LoginDto(username, pass))
				.exchange()
				.expectBody(String.class)
				.returnResult()
				.getResponseBody();
	}

	@Test
	void createEmployee_newEmployeeIsListed() {
		System.out.println(jwt);
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

		assertThat(selectedEmployeeBefore)
				.isEqualTo(selectedEmployeeAfter);
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
				//.headers(headers -> headers.setBasicAuth(username, pass))
				.headers(headers -> headers.setBearerAuth(jwt))
				.bodyValue(selectedEmployee)
				.exchange();
	}

	private List<EmployeeDto> getAllEmployees() {
		List<EmployeeDto> response = webTestClient
				.get()
				.uri(URI)
				//.headers(headers -> headers.setBasicAuth(username, pass))
				.headers(headers -> headers.setBearerAuth(jwt))
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
				//.headers(headers -> headers.setBasicAuth(username, pass))
				.headers(headers -> headers.setBearerAuth(jwt))
				.bodyValue(newEmployee)
				.exchange();
	}

	private EmployeeDto getValidEmployee(){
		return new EmployeeDto(0L, "Varga Mária", "Factory worker", 1280, LocalDate.of(2018, 4, 2), null);
	}

	private EmployeeDto getEmployeeWithMissingRank(){
		return new EmployeeDto(0L, "Horváth Miklós", null, 1350, LocalDate.of(2019, 10, 4), null);
	}


}
