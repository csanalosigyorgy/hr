package hu.webuni.hr.gyuri96.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import hu.webuni.hr.gyuri96.configuration.HrConfogurationProperties;
import hu.webuni.hr.gyuri96.model.Employee;

public abstract class BaseEmployeeService implements EmployeeService {

	@Autowired
	protected HrConfogurationProperties hrConfogurationProperties;

	private Map<Long, Employee> employees = new HashMap<>();

	{
		employees.put(1L, new Employee(1, "Kiss János", "employee", 1000, LocalDateTime.of(2020, 10, 5, 0, 0)));
		employees.put(2L, new Employee(2, "Varga Piroska", "team leader", 1625, LocalDateTime.of(2016, 4, 18, 0, 0)));
		employees.put(3L, new Employee(3, "Fekete Márk", "employee", 1150, LocalDateTime.of(2019, 12, 2, 0, 0)));
	}

	@Override
	public List<Employee> findAll(){
		return new ArrayList<>(employees.values());
	}

	@Override
	public Employee findById(long id) {
		return employees.get(id);
	}

	@Override
	public Employee save(Employee employee) {
		employees.put(employee.getId(), employee);
		return employee;
	}

	@Override
	public Employee update(Employee employee) {
		employees.put(employee.getId(), employee);
		return employee;
	}

	@Override
	public void delete(long id) {
		employees.remove(id);
	}
}
