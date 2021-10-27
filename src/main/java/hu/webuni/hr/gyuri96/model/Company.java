package hu.webuni.hr.gyuri96.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

	private long id;
	private String corporateRegistrationNumber;
	private String name;
	private String address;
	private List<Employee> employees;
}