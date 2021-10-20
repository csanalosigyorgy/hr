package hu.webuni.hr.gyuri96.controller;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import hu.webuni.hr.gyuri96.dto.CompanyDTO;
import hu.webuni.hr.gyuri96.dto.EmployeeDTO;

@RestController
@RequestMapping("/company")
public class CompanyController {

	Map<Long, CompanyDTO> companies;

	{
		companies = new HashMap<>();
		List<EmployeeDTO> employees1 = new ArrayList<>();

		employees1.add(new EmployeeDTO(1, "Kiss János", "employee", 1000, LocalDateTime.of(2020, 10, 5, 0, 0)));
		employees1.add(new EmployeeDTO(2, "Varga Piroska", "team leader", 1625, LocalDateTime.of(2016, 4, 18, 0, 0)));
		companies.put(1L, new CompanyDTO(1L, "01-09-562739", "Kereskedelem Bt.", "Budapest, Bocskai út 5-15, 1114", employees1));

		List<EmployeeDTO> employees2 = new ArrayList<>();
		employees2.add(new EmployeeDTO(1, "Fekete Márk", "employee", 1150, LocalDateTime.of(2019, 12, 2, 0, 0)));
		companies.put(2L, new CompanyDTO(2L, "04-50-982647", "Termelő Kft.", "Budapest, Váci út 20-26, 1132", employees2));
	}

	// TODO a filter miatt más végpontok nem műkdönek!
//	@GetMapping()
//	public MappingJacksonValue getCompanies(@RequestParam(required = false) Boolean full){
//		SimpleBeanPropertyFilter filter;
//		if(full == null || !full) {
//			filter = SimpleBeanPropertyFilter.serializeAllExcept("employees");
//		} else {
//			filter = SimpleBeanPropertyFilter.serializeAll();
//		}
//		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("employeeFilter", filter);
//		List<CompanyDTO> companiesAsList = new ArrayList<>(companies.values());
//
//		MappingJacksonValue response = new MappingJacksonValue(companiesAsList);
//		response.setFilters(filterProvider);
//
//		return response;
//	}

	@GetMapping
	public List<CompanyDTO> getAll(@RequestParam(required = false) Boolean full){
		return new ArrayList<>(companies.values());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDTO> getById(@PathVariable Long id){
		ResponseEntity<CompanyDTO> response;
		if(companies.containsKey(id)){
			response = ResponseEntity.ok(companies.get(id));
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return response;
	}

	@PostMapping()
	public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO company){
		ResponseEntity<CompanyDTO> response;
		if(!companies.containsKey(company.getId())){
			companies.put(company.getId(), company);
			response = ResponseEntity.ok(company);
		} else {
			response = ResponseEntity.status(HttpStatus.FOUND).build();
		}
		return response;
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyDTO>modify(@PathVariable Long id, @RequestBody CompanyDTO company){
		ResponseEntity<CompanyDTO> response;
		company.setId(id);
		if(companies.containsKey(company.getId())){
			companies.put(company.getId(), company);
			response = ResponseEntity.ok(company);
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return response;
	}

	@DeleteMapping
	public ResponseEntity delete(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId) {
		ResponseEntity response;
		if (companies.containsKey(companyId)) {
			CompanyDTO company = companies.get(companyId);
			if (employeeId != null) {
				List<EmployeeDTO> employees = company.getEmployees();
				Optional<EmployeeDTO> optionalResult = employees.stream().filter(e -> e.getId() == employeeId).findAny();
				optionalResult.ifPresent(employees::remove);

			} else {
				companies.remove(company.getId());
			}
			response = ResponseEntity.status(HttpStatus.OK).build();
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return response;
	}

	@PostMapping("/addEmployee/{companyId}")
	public ResponseEntity<CompanyDTO> addEmployeToCompany(@PathVariable long companyId, @RequestBody EmployeeDTO newEmployee){
		ResponseEntity<CompanyDTO> response;
		if(companies.containsKey(companyId)){
			CompanyDTO company = companies.get(companyId);
			List<EmployeeDTO> employees = company.getEmployees();
			employees.add(newEmployee);
			response = ResponseEntity.ok(company);
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return response;
	}

	@PutMapping("/replaceEmployees/{companyId}")
	public ResponseEntity<List<EmployeeDTO>> replaceEmployeesAtCompany(@PathVariable long companyId, @RequestBody List<EmployeeDTO> newEmployees){
		ResponseEntity<List<EmployeeDTO>> response;
		if(companies.containsKey(companyId)){
			CompanyDTO company = companies.get(companyId);
			company.setEmployees(newEmployees);
			response = ResponseEntity.ok(newEmployees);
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return response;
	}
}
