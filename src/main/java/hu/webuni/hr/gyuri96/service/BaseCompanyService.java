package hu.webuni.hr.gyuri96.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.gyuri96.model.Company;
import hu.webuni.hr.gyuri96.model.Employee;

@Service
public class BaseCompanyService implements CompanyService {

	private Map<Long, Company> companies;

	{
		companies = new HashMap<>();
		List<Employee> employees1 = new ArrayList<>();

		employees1.add(new Employee(1, "Kiss János", "employee", 1000, LocalDateTime.of(2020, 10, 5, 0, 0)));
		employees1.add(new Employee(2, "Varga Piroska", "team leader", 1625, LocalDateTime.of(2016, 4, 18, 0, 0)));
		companies.put(1L, new Company(1L, "01-09-562739", "Kereskedelem Bt.", "Budapest, Bocskai út 5-15, 1114", employees1));

		List<Employee> employees2 = new ArrayList<>();
		employees2.add(new Employee(1, "Fekete Márk", "employee", 1150, LocalDateTime.of(2019, 12, 2, 0, 0)));
		companies.put(2L, new Company(2L, "04-50-982647", "Termelő Kft.", "Budapest, Váci út 20-26, 1132", employees2));
	}

	@Override
	public List<Company> findAll(){
		return new ArrayList<>(companies.values());
	}

	private List<Company> findAllWithoutEmployees(){
		return companies.values().stream()
				.map(this::getCompanyWithoutEmployees)
				.collect(Collectors.toList());
	}

	@Override
	public Company findById(long id){
		throwExceptonIfNotExists(id);
		return companies.get(id);
	}

	private Company findByIdWithoutEmployees(long id){
		return getCompanyWithoutEmployees(companies.get(id));
	}

	private Company getCompanyWithoutEmployees(Company c) {
		return new Company(c.getId(), c.getCorporateRegistrationNumber(), c.getName(), c.getAddress(), null);
	}

	private void throwExceptonIfNotExists(Long companyId) {
		if(!companies.containsKey(companyId)){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	private void throwExceptonIfExists(Long companyId) {
		if(companies.containsKey(companyId)){
			throw new ResponseStatusException(HttpStatus.FOUND);
		}
	}

	@Override
	public Company save(Company company){
		throwExceptonIfExists(company.getId());
		companies.put(company.getId(), company);
		return company;
	}

	@Override
	public Company update(Company company) {
		throwExceptonIfNotExists(company.getId());
		companies.put(company.getId(), company);
		return company;
	}

	@Override
	public void delete(long id) {
		throwExceptonIfNotExists(id);
		companies.remove(id);
	}

	@Override
	public Company addEmployee(long companyId, Employee employee) {
		Company company = getCompanyOrThrowException(companyId);
		company.getEmployees().add(employee);
		return company;
	}

	@Override
	public Company replaceAllEmployees(long companyId, List<Employee> employees) {
		Company company = getCompanyOrThrowException(companyId);
		company.setEmployees(employees);
		return company;
	}

	@Override
	public Company deleteEmployee(long companyId, long employeeId) {
		Company company = getCompanyOrThrowException(companyId);
		company.getEmployees().removeIf(e -> e.getId() == employeeId);
		return company;
	}

	private Company getCompanyOrThrowException(Long companyId) {
		if(!companies.containsKey(companyId)){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return companies.get(companyId);
	}

}
