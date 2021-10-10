package hu.webuni.hr.gyuri96.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.gyuri96.service.DefaultEmployeeService;
import hu.webuni.hr.gyuri96.service.EmployeeService;
import lombok.Data;

@Configuration
@Profile("!smart")
@Data
public class DefaultConfiguration {

	@Bean
	public EmployeeService employeeService(){
		return new DefaultEmployeeService();
	}
}
