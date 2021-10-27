package hu.webuni.hr.gyuri96.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

	private long id;

	private String corporateRegistrationNumber;

	private String name;

	private String address;

	private List<EmployeeDto> employees;
}
