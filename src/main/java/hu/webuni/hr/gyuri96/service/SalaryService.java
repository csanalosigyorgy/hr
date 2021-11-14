package hu.webuni.hr.gyuri96.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;
import hu.webuni.hr.gyuri96.repository.PositionDetailsByCompanyRepository;
import hu.webuni.hr.gyuri96.repository.PositionRepository;

@Service
public class SalaryService {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PositionRepository positionRepository;

	@Autowired
	PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;


	public void setNewSalary(Employee employee) {
		int currentSalary = employee.getSalary();
		int payRisePercent = employeeService.getPayRisePercent(employee);
		employee.setSalary((int) (currentSalary * (1 + (payRisePercent / 100.0))));
	}

	@Transactional
	public void raiseMinSalary(long companyId, String positionName, int minSalary){
		positionDetailsByCompanyRepository.findByCompanyIdAndPositionName(companyId, positionName)
				.forEach(pd -> pd.setMinSalary(minSalary));

		employeeRepository.updateSalaries(companyId, positionName, minSalary);
	}
}
