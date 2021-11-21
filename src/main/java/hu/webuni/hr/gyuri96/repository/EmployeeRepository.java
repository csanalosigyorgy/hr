package hu.webuni.hr.gyuri96.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.gyuri96.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

	List<Employee> findBySalaryGreaterThan(Integer minSalary);

	Page<Employee> findBySalaryGreaterThan(Integer minSalary, Pageable page);

	List<Employee> findByPositionName(String positionName);

	List<Employee> findByNameStartingWithIgnoreCase(String name);

	List<Employee> findByDateOfEntryBetween(LocalDate from, LocalDate to);

	@EntityGraph("Employee.full")
	@Query("SELECT e FROM Employee e")
	List<Employee> findAll();

	@EntityGraph("Employee.full")
	@Query("SELECT e FROM Employee e WHERE e.id = :id")
	Optional<Employee> findById(long id);

	@Modifying
	@Transactional
	@Query("UPDATE Employee e SET e.salary = :minSalary WHERE e.id IN (" +
			"SELECT e2.id FROM Employee e2 " +
			"WHERE e2.position.name = :positionName AND e2.company.id = :companyId And e2.salary < :minSalary)")
	void updateSalaries(long companyId, String positionName, int minSalary);

}
