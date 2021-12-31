package hu.webuni.hr.gyuri96.dto;

import java.util.ArrayList;
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

	private String legalEntityName;

	private List<EmployeeDto> employees = new ArrayList<>();
}
