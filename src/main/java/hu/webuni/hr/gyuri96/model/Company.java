package hu.webuni.hr.gyuri96.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedEntityGraph(
		name = "Company.summary",
		attributeNodes = @NamedAttributeNode(value = "legalEntityType")
)
@NamedEntityGraph(
		name = "Company.withEmployees",
		attributeNodes = {
				@NamedAttributeNode(value = "legalEntityType"),
				@NamedAttributeNode(value = "employees", subgraph = "subgraph.employees")
		},
		subgraphs = {
				@NamedSubgraph(name = "subgraph.employees",
								attributeNodes = @NamedAttributeNode(value = "position")
				)}
)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	private String corporateRegistrationNumber;

	private String name;

	private String address;

	@ManyToOne
	private LegalEntityType legalEntityType;

	@OneToMany(mappedBy = "company")
	private List<Employee> employees;


	public void addEmployee(Employee employee) {
		if(Objects.isNull(this.employees)) {
			this.employees = new ArrayList<>();
		}
		this.employees.add(employee);
		employee.setCompany(this);
	}

	public void removeEmployee(Employee employee){
		this.employees.remove(employee);
		employee.setCompany(null);
	}
}