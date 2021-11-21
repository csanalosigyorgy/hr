package hu.webuni.hr.gyuri96.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NamedEntityGraph(
		name = "Employee.full",
		attributeNodes = {
				@NamedAttributeNode("position"),
				@NamedAttributeNode("company")
		})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	private String name;

	private int salary;

	private LocalDate dateOfEntry;

	@ManyToOne
	private Position position;

	@ManyToOne
	private Company company;
}
