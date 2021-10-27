package hu.webuni.hr.gyuri96.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	private long id;
	private String name;
	private String jobTitle;
	private int salary;
	private LocalDateTime dateOfEntry;
}
