package hu.webuni.hr.gyuri96.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
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
public class Company {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	private String corporateRegistrationNumber;

	private String name;

	private String address;

	@OneToMany(mappedBy = "company",
			fetch = LAZY,
			cascade = CascadeType.ALL)
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