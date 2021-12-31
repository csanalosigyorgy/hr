package hu.webuni.hr.gyuri96.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import hu.webuni.hr.gyuri96.configuration.DateTimeFormatConfigurationProperties;
import hu.webuni.hr.gyuri96.configuration.HrConfigurationProperties;
import hu.webuni.hr.gyuri96.filter.EmployeeFilter;
import hu.webuni.hr.gyuri96.model.Company;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.repository.CompanyRepository;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;

public abstract class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	protected HrConfigurationProperties hrConfig;

	@Autowired
	protected DateTimeFormatConfigurationProperties dateTimeFormatterConfig;

	@Autowired
	protected EmployeeRepository employeeRepository;

	@Autowired
	protected EmployeeFilter employeeFilter;

	@Autowired
	protected CompanyRepository companyRepository;

	@Autowired
	protected PositionService positionService;

	@Override
	public List<Employee> findAll(Employee example){
		return (Objects.nonNull(example)) ?
				employeeFilter.findAllByFilter(example) :
				employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> findById(long id) {
		return employeeRepository.findById(id);
	}

	@Transactional
	@Override
	public Employee save(Employee employee) {
		positionService.setPositionForEmployee(employee);
		return employeeRepository.save(employee);
	}

	@Transactional
	@Override
	public Employee update(Employee employee) {
		Employee persistedEmployee = employeeRepository.findById(employee.getId()).orElseThrow(NoSuchElementException::new);
		setCompanyToEmployee(employee, persistedEmployee);
		positionService.setPositionForEmployee(employee);
		return employeeRepository.save(employee);
	}

	@Override
	public void delete(long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public List<Employee> findByAboveSalaryLimit(int limit, Pageable page){
		return employeeRepository.findBySalaryGreaterThan(limit, page).getContent();
	}

	@Override
	public List<Employee> findByJobTitle(String positionName) {
		return employeeRepository.findByPositionName(positionName);
	}

	@Override
	public List<Employee> findByNameStartingWithIgnoreCase(String name) {
		return employeeRepository.findByNameStartingWithIgnoreCase(name);
	}

	@Override
	public List<Employee> findByDateOfEntryBetween(LocalDate from, LocalDate to) {
		return employeeRepository.findByDateOfEntryBetween(from, to);
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
