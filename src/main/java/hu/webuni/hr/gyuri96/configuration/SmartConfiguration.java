package hu.webuni.hr.gyuri96.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.gyuri96.service.EmployeeService;
import hu.webuni.hr.gyuri96.service.SmartEmployeeService;

@Configuration
@Profile("smart")
public class SmartConfiguration {

	@Bean
	public EmployeeService employeeService(){
		return new SmartEmployeeService();
	}
}
