package hu.webuni.hr.gyuri96.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

	@Override
	@EntityGraph("Employee.full")
	List<Employee> findAll();

	@Override
	@EntityGraph("Employee.full")
	List<Employee> findAll(Specification<Employee> spec);

	@Override
	@EntityGraph("Employee.full")
	Optional<Employee> findById(Long id);

	@EntityGraph(attributePaths = {"company", "position", "holidayRequests", "manager"})
	@Query("select e from Employee e where e.id = ?1")
	Optional<Employee> findByIdDetailed(Long id);


	@EntityGraph(attributePaths = "holidayRequests")
	@Query("select e from Employee e where e.id = ?1")
	Optional<Employee> findByIdWithHolidayRequest(Long id);

	@EntityGraph("Employee.full")
	Optional<Employee> findByIdAndCompanyId(Long companyId, Long employeeId);

	@Modifying
	@Transactional
	@Query("UPDATE Employee e SET e.salary = :minSalary WHERE e.id IN (" +
			"SELECT e2.id FROM Employee e2 " +
			"WHERE e2.position.name = :positionName AND e2.company.id = :companyId And e2.salary < :minSalary)")
	void updateSalaries(long companyId, String positionName, int minSalary);

	@Query("SELECT e FROM Employee e WHERE e.username = :username")
	Optional<Employee> findByUsername(String username);

	@EntityGraph(attributePaths = {"manager", "managedEmployees"})
	@Query("SELECT e FROM Employee e WHERE e.username = :username")
	Optional<Employee> findByUsernameWithManagerAndManagedEmployees(String username);
}
