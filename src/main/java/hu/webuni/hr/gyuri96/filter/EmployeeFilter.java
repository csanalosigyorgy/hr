package hu.webuni.hr.gyuri96.filter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;
import hu.webuni.hr.gyuri96.specification.EmployeeSpecification;

@Service
public class EmployeeFilter {

	@Autowired
	private EmployeeRepository employeeRepository;

	private static final double LOWER_LIMIT = 0.9;
	private static final double UPPER_LIMIT = 1.1;

	public List<Employee> findAllByFilter(Employee filterEmployee) {
		Specification<Employee> specification = Specification.where(null);

		long id = filterEmployee.getId();
		if (id > 0)
			specification = specification.and(EmployeeSpecification.hasId(id));

		String name = filterEmployee.getName();
		if (StringUtils.hasText(name)) {
			specification = specification.and(EmployeeSpecification.hasName(name));
		}

		if (Objects.nonNull(filterEmployee.getPosition())) {
			String positionName = filterEmployee.getPosition().getName();
			if (StringUtils.hasText(positionName)) {
				specification = specification.and(EmployeeSpecification.hasPositionName(positionName));
			}
		}

		int salary = filterEmployee.getSalary();
		if (salary > 0) {
			specification = specification.and(EmployeeSpecification.hasSalary(salary, LOWER_LIMIT, UPPER_LIMIT));
		}

		LocalDate dateOfEntry = filterEmployee.getDateOfEntry();
		if (Objects.nonNull(dateOfEntry)) {
			specification = specification.and(EmployeeSpecification.hasDateOfEntry(dateOfEntry));
		}

		if (Objects.nonNull(filterEmployee.getCompany())) {
			String companyName = filterEmployee.getCompany().getName();
			if (StringUtils.hasText(companyName)) {
				specification = specification.and(EmployeeSpecification.hasCompanyName(companyName));
			}
		}

		return employeeRepository.findAll(specification);
	}
}
