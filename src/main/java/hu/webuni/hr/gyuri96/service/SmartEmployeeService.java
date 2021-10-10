package hu.webuni.hr.gyuri96.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gyuri96.configuration.HrConfogurationProperties;
import hu.webuni.hr.gyuri96.model.Employee;

@Service
public class SmartEmployeeService implements EmployeeService {

	@Autowired
	private HrConfogurationProperties hrConfogurationProperties;

	@Override
	public int getPayRisePercent(Employee employee) {

		int numberOfMonthAtTheCompany = getNumberOfMonthAtTheCompany(employee.getDateOfEntry());
		int percentOfRaise;

		if(numberOfMonthAtTheCompany > 120) {
			percentOfRaise = hrConfogurationProperties.getRise().getTen();
		} else if(numberOfMonthAtTheCompany >  60) {
			percentOfRaise = hrConfogurationProperties.getRise().getFive();
		} else if (numberOfMonthAtTheCompany >  30) {
			percentOfRaise = hrConfogurationProperties.getRise().getTwoAndHalf();
		} else {
			percentOfRaise = 0;
		}

		return percentOfRaise;
	}


	private int getNumberOfYearsAtTheCompany(LocalDateTime dateOfEntry){
		return LocalDateTime.now().getYear() - dateOfEntry.getYear();
	}

	private int getNumberOfMonthAtTheCompany(LocalDateTime dateOfEntry){

		int numberOfYearsAtTheCompany = getNumberOfYearsAtTheCompany(dateOfEntry);
		int numberOfMonthThisYearAtTheCompany = LocalDateTime.now().getMonthValue() - dateOfEntry.getMonthValue();

		return numberOfYearsAtTheCompany * 12 + numberOfMonthThisYearAtTheCompany;
	}
}
