package hu.webuni.hr.gyuri96.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PositionDetailsByCompany {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	private int minSalary;

	@ManyToOne
	private Company company;

	@ManyToOne
	private Position position;
}
