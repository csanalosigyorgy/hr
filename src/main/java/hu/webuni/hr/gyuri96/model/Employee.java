package hu.webuni.hr.gyuri96.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

	private long id;
	private String name;
	private String rank;
	private int salary;
	private LocalDateTime dateOfEntry;
}
