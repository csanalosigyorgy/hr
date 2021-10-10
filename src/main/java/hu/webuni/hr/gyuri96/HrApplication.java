package hu.webuni.hr.gyuri96;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Autowired
	SalaryService salaryService;

	@Override
	public void run(String... args) throws Exception {

		Employee employee = new Employee(1,
				"Fekete Andrea",
				"beosztott",
				300,
				LocalDateTime.of(2009, 3, 1, 0,0 ,0));

		int salaryBeforeRise = employee.getSalary();
		salaryService.setRaisedSalary(employee);

		System.out.println(String.format("Original salaray: %s, Raised salary: %s",
				salaryBeforeRise,
				employee.getSalary()));
	}
}
