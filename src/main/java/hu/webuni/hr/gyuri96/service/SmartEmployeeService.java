package hu.webuni.hr.gyuri96.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import hu.webuni.hr.gyuri96.model.Employee;

@Service
public class SmartEmployeeService extends EmployeeServiceImpl {

	@Override
	public int getPayRisePercent(Employee employee) {
		TreeMap<Double, Integer> limits = hrConfog.getSalary().getASmart().getLimits();
		double yearsWorked = ChronoUnit.DAYS.between(employee.getDateOfEntry(), LocalDateTime.now()) / 365.0;
		Optional<Double> optionalMax = limits.keySet().stream().filter(l -> yearsWorked >= l).max(Double::compare);
		return optionalMax.isEmpty() ? 0 : limits.get(optionalMax.get());
	}

}
