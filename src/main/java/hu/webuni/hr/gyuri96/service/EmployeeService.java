package hu.webuni.hr.gyuri96.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hu.webuni.hr.gyuri96.model.Employee;

public interface EmployeeService {

	int getPayRisePercent(Employee employee);

	List<Employee> findAll();

	Optional<Employee> findById(long id);

	Employee save(Employee employee);

	Employee update(Employee employee);

	void delete(long id);

	List<Employee> findByAboveSalaryLimit(int limit, Pageable page);

	List<Employee> findByJobTitle(String jobTitle);

	List<Employee> findByNameStartingWithIgnoreCase(String name);

	List<Employee> findByDateOfEntryBetween(LocalDate from, LocalDate to);
}
