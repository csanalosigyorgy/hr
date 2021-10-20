package hu.webuni.hr.gyuri96.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.service.EmployeeService;

@RestController
@RequestMapping("/salary")
public class SalaryController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping
	public String getRaiseValue(@RequestBody Employee employee) {
		return String.format("Az megadott alkalmazotthoz tartozó emelés mértéke: %.2f%%.",
				(double) employeeService.getPayRisePercent(employee));
	}

}
