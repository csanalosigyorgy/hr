package hu.webuni.hr.gyuri96.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public List<EmployeeDto> getEmployees(@RequestParam(required = false) Integer limit) {
		Stream<EmployeeDto> stream = employeeMapper.toDtos(employeeService.findAll()).stream();
		if(limit != null){
			stream = stream.filter(e -> e.getSalary() > limit);
		}
		return stream.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public EmployeeDto getEmployeeById(@PathVariable Long id) {
		return employeeMapper.toDto(employeeService.findById(id));
	}

	@PostMapping
	public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
		Employee savedEmployee = employeeService.save(employeeMapper.toEmployee(employeeDto));
		return employeeMapper.toDto(savedEmployee);
	}

	@PutMapping("/{id}")
	public EmployeeDto modifyEmployeeById(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto) {
		employeeDto.setId(id);
		Employee updateEmployee = employeeService.update(employeeMapper.toEmployee(employeeDto));
		return employeeMapper.toDto(updateEmployee);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployeeById(@PathVariable Long id) {
		employeeService.delete(id);
	}

	@GetMapping("/salary")
	public double getSalaryRaisePercent(@RequestBody EmployeeDto employeeDto) {
		return employeeService.getPayRisePercent(employeeMapper.toEmployee(employeeDto));
	}
}
