package hu.webuni.hr.gyuri96.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonFilter("employeeFilter")
public class CompanyDTO {

	private long id;
	private String corporateRegistrationNumber;
	private String name;
	private String address;
	private List<EmployeeDTO> employees;
}
