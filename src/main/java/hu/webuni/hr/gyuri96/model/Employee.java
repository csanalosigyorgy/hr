package hu.webuni.hr.gyuri96.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	private long id;
	private String name;
	private String rank;
	private int salary;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime dateOfEntry;
}