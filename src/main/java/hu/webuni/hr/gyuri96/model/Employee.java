package hu.webuni.hr.gyuri96.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NamedEntityGraph(
		name = "Employee.full",
		attributeNodes = {
				@NamedAttributeNode("position"),
				@NamedAttributeNode(value = "company", subgraph = "subgraph.company")
		},
		subgraphs = {@NamedSubgraph(name = "subgraph.company", attributeNodes = {
				@NamedAttributeNode("legalEntityType")
		})
		})
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	private String name;

	private int salary;

	private LocalDate dateOfEntry;

	@ManyToOne(fetch = FetchType.LAZY)
	private Position position;

	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;

	@OneToMany(mappedBy = "issuer")
	private Set<HolidayRequest> holidayRequests;

	@ManyToOne(fetch = FetchType.LAZY)
	private Employee manager;

	@OneToMany(mappedBy = "manager")
	private Set<Employee> managedEmployees;

	private String username;

	private String password;

	public Employee(String name, int salary, LocalDate dateOfEntry, Position position, Company company) {
		this.name = name;
		this.salary = salary;
		this.dateOfEntry = dateOfEntry;
		this.position = position;
		this.company = company;
	}

	public void addHolidayRequest(HolidayRequest holidayRequest) {
		if(Objects.isNull(this.holidayRequests)) {
			this.holidayRequests = new HashSet<>();
		}
		this.holidayRequests.add(holidayRequest);
		holidayRequest.setIssuer(this);
	}

	public void removeHolidayRequest(HolidayRequest holidayRequest){
		this.holidayRequests.remove(holidayRequest);
		holidayRequest.setIssuer(null);
	}
}
