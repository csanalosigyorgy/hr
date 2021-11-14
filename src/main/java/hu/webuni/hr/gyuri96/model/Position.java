package hu.webuni.hr.gyuri96.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	private String name;

	private RequiredEducationLevel educationLevel;

	@OneToMany(mappedBy = "position")
	private List<Employee> employees;

}
