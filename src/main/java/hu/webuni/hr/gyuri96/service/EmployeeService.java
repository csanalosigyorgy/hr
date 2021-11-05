package hu.webuni.hr.gyuri96.service;

import java.util.List;
import java.util.Optional;

import hu.webuni.hr.gyuri96.model.Employee;

public interface EmployeeService {

	int getPayRisePercent(Employee employee);

	List<Employee> findAll();

	Optional<Employee> findById(long id);

	Employee save(Employee employee);

	Employee update(Employee employee);

	void delete(long id);

	List<Employee> findByAboveSalaryLimit(int limit);

	List<Employee> findByJobTitle(String jobTitle);

	List<Employee> findByNameStartingWithIgnoreCase(String name);

	List<Employee> findByDateOfEntryBetween(String from, String to);
}
