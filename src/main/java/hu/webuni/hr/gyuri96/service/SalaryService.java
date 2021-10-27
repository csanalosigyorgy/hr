package hu.webuni.hr.gyuri96.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gyuri96.model.Employee;

@Service
public class SalaryService {

	@Autowired
	EmployeeService employeeService;

	public void setNewSalary(Employee employee) {
		int currentSalary = employee.getSalary();
		int payRisePercent = employeeService.getPayRisePercent(employee);
		employee.setSalary((int) (currentSalary * (1 + (payRisePercent / 100.0))));
	}
}
