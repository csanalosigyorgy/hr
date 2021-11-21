package hu.webuni.hr.gyuri96.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.gyuri96.model.AverageSalaryByPosition;
import hu.webuni.hr.gyuri96.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Override
	@EntityGraph("Company.full")
	List<Company> findAll();

	@EntityGraph("Company.full")
	Optional<Company>findById(long id);

	@EntityGraph("Company.full")
	@Query("SELECT c FROM Company c")
	List<Company> findAllWithEmployees();

	@EntityGraph("Company.full")
	@Query("SELECT c FROM Company c WHERE c.id = :id")
	Optional<Company> findByIdWithEmployees(long id);

	@Query("SELECT DISTINCT c FROM Company c JOIN Employee e WHERE e.salary > :minSalary")
	List<Company> findByEmployeeEarnMoreThen(int minSalary);

	@Query("SELECT c FROM Company c WHERE size(c.employees) > :minEmployeeHeadCount")
	List<Company> findByNumberOfEmployeesGreaterThan(int minEmployeeHeadCount);

	@Query("SELECT e.position.name AS position, AVG(e.salary) AS averageSalary " +
			"FROM Company c JOIN c.employees e WHERE c.id = :companyId " +
			"GROUP BY e.position.name ORDER BY averageSalary DESC")
	List<AverageSalaryByPosition> createSalarySummaryByCompanyId(long companyId);
}
