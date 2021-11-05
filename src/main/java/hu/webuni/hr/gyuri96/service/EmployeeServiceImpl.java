package hu.webuni.hr.gyuri96.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import hu.webuni.hr.gyuri96.configuration.DateTimeFormatConfigurationProperties;
import hu.webuni.hr.gyuri96.configuration.HrConfogurationProperties;
import hu.webuni.hr.gyuri96.model.Company;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.repository.CompanyRepository;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;

public abstract class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	protected HrConfogurationProperties hrConfog;

	@Autowired
	protected DateTimeFormatConfigurationProperties dateTimeFormatterConfig;

	@Autowired
	protected EmployeeRepository employeeRepository;

	@Autowired
	protected CompanyRepository companyRepository;


	@Override
	public List<Employee> findAll(){
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> findById(long id) {
		return employeeRepository.findById(id);
	}

	@Transactional
	@Override
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Transactional
	@Override
	public Employee update(Employee employee) {
		Employee persistedEmployee = employeeRepository.findById(employee.getId()).orElseThrow(NoSuchElementException::new);
		setCompanyToEmployee(employee, persistedEmployee);
		return employeeRepository.save(employee);
	}

	@Override
	public void delete(long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public List<Employee> findByAboveSalaryLimit(int limit){
		return employeeRepository.findBySalaryGreaterThan(limit);
	}

	@Override
	public List<Employee> findByJobTitle(String jobTitle) {
		return employeeRepository.findByJobTitle(jobTitle);
	}

	@Override
	public List<Employee> findByNameStartingWithIgnoreCase(String name) {
		return employeeRepository.findByNameStartingWithIgnoreCase(name);
	}

	@Override
	public List<Employee> findByDateOfEntryBetween(String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormatterConfig.getMvc().getFormat().getDate());
		LocalDate parsedFrom = LocalDate.parse(from, formatter);
		LocalDate parsedTo = LocalDate.parse(to, formatter);

		return employeeRepository.findByDateOfEntryBetween(parsedFrom, parsedTo);
	}

	private void setCompanyToEmployee(Employee employee, Employee persistedEmployee) {
		if(Objects.isNull(employee.getCompany())) {
			employee.setCompany(persistedEmployee.getCompany());
		} else {
			Company company = companyRepository.findById(employee.getCompany().getId()).orElseThrow(NoSuchElementException::new);
			employee.setCompany(company);
		}
	}
}
