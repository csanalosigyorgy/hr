package hu.webuni.hr.gyuri96.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NamedEntityGraph(
		name = "positionByDetails.full",
		attributeNodes = {
				@NamedAttributeNode(value = "company"),
				@NamedAttributeNode(value = "position")
		}
)
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
