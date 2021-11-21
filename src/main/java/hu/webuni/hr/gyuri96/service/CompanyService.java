package hu.webuni.hr.gyuri96.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import hu.webuni.hr.gyuri96.model.AverageSalaryByPosition;
import hu.webuni.hr.gyuri96.model.Company;
import hu.webuni.hr.gyuri96.model.Employee;

public interface CompanyService {

	List<Company> findAll();

	List<Company> findAllWithEmployees();

	Optional<Company> findById(long id);

	Company save(Company company);

	Company update(Company company);

	void delete(long id);

	Company addEmployee(long companyId, Employee employee);

	Company replaceAllEmployees(long companyId, List<Employee> employees);

	Company deleteEmployee(long companyId, long employeeId);

	List<Company> findByHaveEmployeeEarnMoreThen(int limit);

	List<Company> findByNumberOfEmployeesGreaterThan(int limit);

//	Map<String, Double> getSalarySummaryByJobTitle(long companyId);

	List<AverageSalaryByPosition> getSalarySummaryByJobTitle(long companyId);
}
