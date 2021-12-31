package hu.webuni.hr.gyuri96.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gyuri96.model.AverageSalaryByPosition;
import hu.webuni.hr.gyuri96.model.Company;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.model.Position;
import hu.webuni.hr.gyuri96.model.PositionDetailsByCompany;
import hu.webuni.hr.gyuri96.repository.CompanyRepository;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;
import hu.webuni.hr.gyuri96.repository.PositionDetailsByCompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;

	@Autowired
	private EmployeeService employeeService;

	@Override
	public List<Company> findAll(){
		return companyRepository.findAll();
	}

	@Override
	public List<Company> findAllWithEmployees(){
		return companyRepository.findAllWithEmployees();
	}

	@Transactional
	@Override
	public Optional<Company> findById(long id){
		return companyRepository.findById(id);
	}

	@Override
	public Optional<Company> findByIdWithEmployees(long id) {
		return companyRepository.findByIdWithEmployees(id);
	}

	@Transactional
	@Override
	public Company save(Company company){
		if(Objects.nonNull(company.getEmployees())){
			setCompanyToEmployees(company, company.getEmployees());
		}
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
		PositionDetailsByCompany positionDetails = positionDetailsByCompanyRepository.findByCompanyIdAndPositionName(companyId, employee.getPosition().getName())
				.orElseThrow(NoSuchElementException::new);
		System.out.println("PositionDetails");

		Company company = positionDetails.getCompany();
		Position position = positionDetails.getPosition();

		//employee.setCompany(company);
		employee.setPosition(position);
		Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
		System.out.println("Employee");
		employee = optionalEmployee.orElse(employeeService.save(employee));

		company.addEmployee(employee);
		return company;
	}

	@Transactional
	@Override
	public Company replaceAllEmployees(long companyId, List<Employee> employees) {
		// TODO -> El kell menteni az új employee-kat
		// TODO -> csekkolni kell, hogy van-e ilyen munkakör!
		// TODO -> le kell cserélni az összes employeet!

		Company company = getCompanyOrThrowException(companyId);
		company.getEmployees().forEach(employee -> employee.setCompany(null));
		company.setEmployees(new ArrayList<>());

		employees.forEach(e -> {
			e.setCompany(company);
			employeeService.save(e);
			company.addEmployee(e);
		});
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

	@Override
	public List<Company> findByHaveEmployeeEarnMoreThen(int limit) {
		return companyRepository.findByEmployeeEarnMoreThen(limit);
	}

	@Override
	public List<Company> findByNumberOfEmployeesGreaterThan(int limit) {
		return companyRepository.findByNumberOfEmployeesGreaterThan(limit);
	}

	@Override
	public List<AverageSalaryByPosition> getSalarySummaryByJobTitle(long companyId) {
		return companyRepository.createSalarySummaryByCompanyId(companyId);
	}

//	@Override
//	public Map<String, Double> getSalarySummaryByJobTitle(long companyId) {
//		List<Object[]> rawData = null; //companyRepository.createSalarySummaryById(1);
//
//		Map<String, Double> salarySummary = new LinkedHashMap<>();
//		for(Object[] o : rawData){
//			salarySummary.put(o[0].toString(), Double.valueOf(o[1].toString()));
//		}
//		return salarySummary;
//	}


	private Company getCompanyOrThrowException(Long companyId) {
		Optional<Company> optionalCompany = companyRepository.findByIdWithEmployees(companyId);
		return optionalCompany.orElseThrow(NoSuchElementException::new);
	}

	private void setCompanyToEmployees(Company company, List<Employee> employees){
		if(!employees.isEmpty()){
			employees.forEach(employee -> employee.setCompany(company));
		}
	}

}
