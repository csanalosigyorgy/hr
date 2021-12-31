package hu.webuni.hr.gyuri96.service;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gyuri96.model.Employee;

@Service
public class DefaultEmployeeService extends EmployeeServiceImpl {

	@Override
	public int getPayRisePercent(Employee employee) {
		return hrConfig.getSalary().getADefault().getPercentage();
	}
}
