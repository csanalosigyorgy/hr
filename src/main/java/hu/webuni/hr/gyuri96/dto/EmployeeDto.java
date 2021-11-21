package hu.webuni.hr.gyuri96.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	private long id;

	@NotEmpty
	private String name;

	private PositionDto position;

	@Min(1)
	private int salary;

	@Past
	private LocalDate entryDate;

	private CompanyDto company;
}
