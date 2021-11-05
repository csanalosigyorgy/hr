package hu.webuni.hr.gyuri96.service;

import java.util.List;
import java.util.Optional;

import hu.webuni.hr.gyuri96.model.Company;
import hu.webuni.hr.gyuri96.model.Employee;

public interface CompanyService {

	List<Company> findAll();

	Optional<Company> findById(long id);

	Company save(Company company);

	Company update(Company company);

	void delete(long id);

	Company addEmployee(long companyId, Employee employee);

	Company replaceAllEmployees(long companyId, List<Employee> employees);

	Company deleteEmployee(long companyId, long employeeId);
}
