package hu.webuni.hr.gyuri96.service;

import java.util.List;

import hu.webuni.hr.gyuri96.model.Employee;

public interface EmployeeService {

	int getPayRisePercent(Employee employee);

	List<Employee> findAll();

	Employee findById(long id);

	Employee save(Employee employee);

	Employee update(Employee employee);

	void delete(long id);
}
