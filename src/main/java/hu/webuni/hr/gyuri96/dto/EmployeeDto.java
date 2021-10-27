package hu.webuni.hr.gyuri96.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	private long id;

	@NotNull
	private String name;

	@NotNull
	private String rank;

	@Min(1)
	private int salary;

	@Past
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime entryDate;
}
