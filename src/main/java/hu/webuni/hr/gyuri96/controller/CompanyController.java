package hu.webuni.hr.gyuri96.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.gyuri96.dto.CompanyDto;

import hu.webuni.hr.gyuri96.dto.EmployeeDto;
import hu.webuni.hr.gyuri96.mapper.CompanyMapper;
import hu.webuni.hr.gyuri96.mapper.EmployeeMapper;
import hu.webuni.hr.gyuri96.model.Company;
import hu.webuni.hr.gyuri96.service.CompanyService;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private EmployeeMapper employeeMapper;

	@GetMapping
	public List<CompanyDto> getCompanies(@RequestParam(required = false) Boolean full){
		List<Company> companies = companyService.findAll();
		List<CompanyDto> result;
		if(Objects.isNull(full) || !full){
			result = companyMapper.toCompanyDtosIgnoreEmployees(companies);
		} else {
			result = companyMapper.toCompanyDtos(companies);
		}
		return result;
	}

	@GetMapping("/{companyId}")
	public CompanyDto getCompanyById(@RequestParam(required = false) Boolean full, @PathVariable Long companyId){
		Optional<Company> optionalCompany = companyService.findById(companyId);
		Company company = optionalCompany
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		CompanyDto result;
		if(Objects.isNull(full) || !full){
			result = companyMapper.toCompanyDtoIgnoreEmployees(company);
		} else {
			result = companyMapper.toCompanyDto(company);
		}
		return result;
	}

	@PostMapping()
	public CompanyDto createCompany(@RequestBody CompanyDto company){
		Company savedCompany = companyService.save(companyMapper.toCompany(company));
		return companyMapper.toCompanyDto(savedCompany);
	}

	@PutMapping("/{companyId}")
	public CompanyDto modifyCompany(@PathVariable Long companyId, @RequestBody CompanyDto companyDto){
		companyDto.setId(companyId);
		Company updateCompany = companyService.update(companyMapper.toCompany(companyDto));
		return companyMapper.toCompanyDto(updateCompany);
	}

	@DeleteMapping("/{companyId}")
	public void deleteCompany(@PathVariable Long companyId) {
		companyService.delete(companyId);
	}

	@PostMapping("/{companyId}/employees")
	public CompanyDto addEmployee(@PathVariable Long companyId, @RequestBody EmployeeDto newEmployee){
		Company company = companyService.addEmployee(companyId, employeeMapper.toEmployee(newEmployee));
		return companyMapper.toCompanyDto(company);
	}

	@PutMapping("/{companyId}/employees")
	public CompanyDto replaceEmployees(@PathVariable Long companyId, @RequestBody List<EmployeeDto> newEmployees){
		return companyMapper.toCompanyDto(companyService.replaceAllEmployees(companyId, employeeMapper.toEmployees(newEmployees)));
	}

	@DeleteMapping("/{companyId}/employees/{employeeId}")
	public CompanyDto deleteEmployee(@PathVariable Long companyId, @PathVariable Long employeeId){
		return companyMapper.toCompanyDto(companyService.deleteEmployee(companyId, employeeId));
	}

}
