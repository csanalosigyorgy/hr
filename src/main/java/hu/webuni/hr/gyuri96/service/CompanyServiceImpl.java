package hu.webuni.hr.gyuri96.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.webuni.hr.gyuri96.model.Company;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.repository.CompanyRepository;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Company> findAll(){
		return companyRepository.findAll();
	}

	@Override
	public Optional<Company> findById(long id){
		getCompanyOrThrowException(id);
		return companyRepository.findById(id);
	}

	@Transactional
	@Override
	public Company save(Company company){
		setCompanyToEmployees(company, company.getEmployees());
		return companyRepository.save(company);
	}

	@Transactional
	@Override
	public Company update(Company company) {
		getCompanyOrThrowException(company.getId());
		setCompanyToEmployees(company, company.getEmployees());
		return companyRepository.save(company);
	}

	@Override
	public void delete(long id) {
		getCompanyOrThrowException(id);
		companyRepository.deleteById(id);
	}

	@Transactional
	@Override
	public Company addEmployee(long companyId, Employee employee) {
		Company company = getCompanyOrThrowException(companyId);

		employee.setCompany(company);
		Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
		employee = optionalEmployee.orElse(employeeRepository.save(employee));

		company.addEmployee(employee);
		return company;
	}

	@Transactional
	@Override
	public Company replaceAllEmployees(long companyId, List<Employee> employees) {
		Company company = getCompanyOrThrowException(companyId);
		company.getEmployees().forEach(employee -> employee.setCompany(null));

		setCompanyToEmployees(company, employees);
		employees.forEach(company::addEmployee);
		return company;
	}

	@Transactional
	@Override
	public Company deleteEmployee(long companyId, long employeeId) {
		Company company = getCompanyOrThrowException(companyId);
		Employee employee = company.getEmployees().stream().filter(e -> e.getId() == employeeId).findAny().orElseThrow(NoSuchElementException::new);

		company.removeEmployee(employee);
		return company;
	}

	private Company getCompanyOrThrowException(Long companyId) {
		Optional<Company> optionalCompany = companyRepository.findById(companyId);
		return optionalCompany.orElseThrow(NoSuchElementException::new);
	}

	private void setCompanyToEmployees(Company company, List<Employee> employees){
		if(!employees.isEmpty()){
			employees.forEach(employee -> employee.setCompany(company));
		}
	}

}