package hu.webuni.hr.gyuri96.service;

import static hu.webuni.hr.gyuri96.model.RequiredEducationLevel.HIGH_SCHOOL;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.hr.gyuri96.dto.CompanyDto;
import hu.webuni.hr.gyuri96.dto.EmployeeDto;
import hu.webuni.hr.gyuri96.mapper.PositionMapper;
import hu.webuni.hr.gyuri96.model.Company;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.model.LegalEntityType;
import hu.webuni.hr.gyuri96.model.Position;
import hu.webuni.hr.gyuri96.model.PositionDetailsByCompany;
import hu.webuni.hr.gyuri96.repository.CompanyRepository;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;
import hu.webuni.hr.gyuri96.repository.LegalEntityTypeRepository;
import hu.webuni.hr.gyuri96.repository.PositionDetailsByCompanyRepository;
import hu.webuni.hr.gyuri96.repository.PositionRepository;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyServiceIT {

	@Autowired
	private LegalEntityTypeRepository legalEntityTypeRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;

	@Autowired
	private PositionMapper positionMapper;


	private static final String BASE_URI = "/api/company/";

	private static long companyId;

	@Autowired
	WebTestClient webTestClient;

	@BeforeEach
	void setDb() {
		employeeRepository.deleteAll();
		companyRepository.deleteAll();
		positionRepository.deleteAll();
		legalEntityTypeRepository.deleteAll();

		LegalEntityType kft = new LegalEntityType(0L, "Kft.");
		legalEntityTypeRepository.save(kft);

		List<Position> torleyPositions = new ArrayList<>();
		Position torleyFactoryWorker = new Position(0L, "Factory worker", HIGH_SCHOOL, null);
		torleyPositions.add(torleyFactoryWorker);
		positionRepository.saveAll(torleyPositions);

		Company torley = new Company(0L, "01-09-883786", "Törley Pezsgőpincészet", "1222 Budapest, Háros u. 2-6.", kft, null);
		companyRepository.save(torley);

		companyId = torley.getId();

		Employee papaiMarika = new Employee(0L, "Pápai Mária", 1890, LocalDate.of(2016, 7, 18), null, null);
		torley.addEmployee(papaiMarika);
		torleyFactoryWorker.addEmployee(papaiMarika);

		Employee vargaTamas = new Employee(0L, "Varga Tamás", 1280, LocalDate.of(2018, 4, 2), null, null);
		torley.addEmployee(vargaTamas);
		torleyFactoryWorker.addEmployee(vargaTamas);

		employeeRepository.saveAll(torley.getEmployees());

		positionDetailsByCompanyRepository.save(new PositionDetailsByCompany(0L, 1250, torley, torleyFactoryWorker));
	}

	@Test
	void createNewEmployee_newEmployeeIsListed() {

		boolean full = true;

		CompanyDto company = findCompanyById(companyId, full);
//		CompanyDto company = new CompanyDto();
//		company.setId(1L);
		EmployeeDto newEmployee = createNewEmployee(company);

		CompanyDto companyWithNewEmployee = addNewEmployee(companyId, newEmployee);

		EmployeeDto newEmployeeInCompany = companyWithNewEmployee.getEmployees().get(companyWithNewEmployee.getEmployees().size() - 1);

		assertThat(newEmployee).usingRecursiveComparison().ignoringFields("id")
				.isEqualTo(newEmployeeInCompany);
	}

	EmployeeDto createNewEmployee(CompanyDto company){
		Position position = positionRepository.getById(1L);
		return new EmployeeDto(0L, "Bíró János", positionMapper.toPositionDto(position), 1250, LocalDate.of(2021, 11, 15), company);
	}

	private CompanyDto findCompanyById(long companyId, Boolean full){
		return webTestClient.get()
				.uri(uriBuilder ->
						uriBuilder.path(BASE_URI + companyId)
								.queryParam("full", full)
								.build()
				)
				.exchange()
				.expectStatus().isOk()
				.expectBody(CompanyDto.class)
				.returnResult()
				.getResponseBody();
	}

	private CompanyDto addNewEmployee(long companyId, EmployeeDto employeeDto) {
		return webTestClient.post()
				.uri(BASE_URI + companyId + "/employees")
				.bodyValue(employeeDto)
				.exchange()
				.expectStatus().isOk()
				.expectBody(CompanyDto.class)
				.returnResult()
				.getResponseBody();
	}

}
