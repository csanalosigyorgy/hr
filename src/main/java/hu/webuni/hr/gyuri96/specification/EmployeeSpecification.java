package hu.webuni.hr.gyuri96.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.gyuri96.model.Company_;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.model.Employee_;
import hu.webuni.hr.gyuri96.model.Position_;

public class EmployeeSpecification {

	public static Specification<Employee> hasId(long id) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
	}

	public static Specification<Employee> hasName(String name) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), name.toLowerCase() + "%");
	}

	public static Specification<Employee> hasPositionName(String positionName) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.position).get(Position_.name), positionName);
	}

	public static Specification<Employee> hasSalary(int salary, double lowerLimit, double upperLimit) {
		int lowerSalary = (int) (lowerLimit * salary);
		int upperSalary = (int) (upperLimit * salary);
		return (root, cq, cb) -> cb.between(root.get(Employee_.salary), lowerSalary, upperSalary);
	}

	public static Specification<Employee> hasDateOfEntry(LocalDate dateOfEntry) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.dateOfEntry), dateOfEntry);
	}

	public static Specification<Employee> hasCompanyName(String companyName) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.company).get(Company_.name), companyName);
	}
}
