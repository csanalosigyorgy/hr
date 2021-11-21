package hu.webuni.hr.gyuri96.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
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

import hu.webuni.hr.gyuri96.dto.EmployeeDto;
import hu.webuni.hr.gyuri96.mapper.EmployeeMapper;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeMapper employeeMapper;

	@GetMapping
	public List<EmployeeDto> getEmployees(@RequestParam(required = false) Boolean full, @RequestBody(required = false) Employee example){
		List<Employee> employees = employeeService.findAll(example);

		return (Objects.isNull(full) || !full) ?
				employeeMapper.toEmployeeDtosIgnoreCompany(employees) :
				employeeMapper.toEmployeeDtos(employees);
	}

	@GetMapping("/{id}")
	public EmployeeDto getEmployeeById(@PathVariable Long id, @RequestParam(required = false) Boolean full) {
		Employee employee = employeeService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return (Objects.isNull(full) || !full) ?
				employeeMapper.toEmployeeDtoIgnoreCompany(employee) :
				employeeMapper.toEmployeeDto(employee);
	}

	@GetMapping("/salary")
	public List<EmployeeDto> getEmployeesAboveSalaryLimit(@RequestParam Integer limit, Pageable page) {
		return employeeMapper.toEmployeeDtosIgnoreCompany(employeeService.findByAboveSalaryLimit(limit, page));
	}

	@GetMapping("/job-title")
	public List<EmployeeDto> getEmployeesFilterJobTitle(@RequestParam String filter) {
		return employeeMapper.toEmployeeDtosIgnoreCompany(employeeService.findByJobTitle(filter));
	}

	@GetMapping("/name-like")
	public List<EmployeeDto> getEmployeesFilterNameLike(@RequestParam String filter) {
		return employeeMapper.toEmployeeDtosIgnoreCompany(employeeService.findByNameStartingWithIgnoreCase(filter));
	}

	@GetMapping("/entry-date")
	public List<EmployeeDto> getEmployeesFilterEntryDate(@RequestParam LocalDate from, @RequestParam LocalDate to) {
		return employeeMapper.toEmployeeDtosIgnoreCompany(employeeService.findByDateOfEntryBetween(from, to));
	}

	@PostMapping
	public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
		Employee savedEmployee = employeeService.save(employeeMapper.toEmployee(employeeDto));
		return employeeMapper.toEmployeeDto(savedEmployee);
	}

	@PutMapping("/{id}")
	public EmployeeDto modifyEmployeeById(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto) {
		employeeDto.setId(id);
		Employee updateEmployee = employeeService.update(employeeMapper.toEmployee(employeeDto));
		return employeeMapper.toEmployeeDto(updateEmployee);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployeeById(@PathVariable Long id) {
		employeeService.delete(id);
	}

	@GetMapping("/salary/raise")
	public double getSalaryRaisePercent(@RequestBody EmployeeDto employeeDto) {
		return employeeService.getPayRisePercent(employeeMapper.toEmployee(employeeDto));
	}

}
