package hu.webuni.hr.gyuri96;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.hr.gyuri96.configuration.HrConfogurationProperties;
import hu.webuni.hr.gyuri96.configuration.HrConfogurationProperties.Smart;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.repository.CompanyRepository;
import hu.webuni.hr.gyuri96.service.CompanyService;
import hu.webuni.hr.gyuri96.service.InitDBService;
import hu.webuni.hr.gyuri96.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	SalaryService salaryService;

	@Autowired
	HrConfogurationProperties hrConfig;

	@Autowired
	InitDBService initDBService;

	@Autowired
	CompanyRepository companyRepository;

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) {

		//initDBService.clearDB();
		//initDBService.insertTestData();


//		Smart smartConfiguration = hrConfig.getSalary().getASmart();
//		for (Double limit : smartConfiguration.getLimits().keySet()) {
//
//			int originalSalary = 1000;
//			System.out.printf("Az eredeti fizetés %d%n", originalSalary);
//
//			LocalDate limitDay = LocalDate.now().minusDays((long) (limit * 365));
//
//			Employee employee = new Employee(1L, "Füredi Anita", "employee", originalSalary, limitDay.plusDays(1), null);
//			salaryService.setNewSalary(employee);
//			System.out.printf("1 nappal a %.2f éves határ előtt az új fizetés %d%n", limit, employee.getSalary());
//
//			employee.setSalary(originalSalary);
//			employee.setDateOfEntry(limitDay.minusDays(1));
//			salaryService.setNewSalary(employee);
//			System.out.printf("1 nappal a %.2f éves határ után az új fizetés %d%n%n", limit, employee.getSalary());
//		}
	}
}
