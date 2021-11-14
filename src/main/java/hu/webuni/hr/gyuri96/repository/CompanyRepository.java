package hu.webuni.hr.gyuri96.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.gyuri96.model.AverageSalaryByPosition;
import hu.webuni.hr.gyuri96.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Query("SELECT DISTINCT c FROM Company c JOIN Employee e WHERE e.salary > :minSalary")
	List<Company> findByEmployeeEarnMoreThen(int minSalary);

	@Query("SELECT c FROM Company c WHERE c.employees.size > :minEmployeeHeadCount")
	List<Company> findByNumberOfEmployeesGreaterThan(int minEmployeeHeadCount);

	@Query("SELECT e.position.name AS position, AVG(e.salary) AS averageSalary " +
			"FROM Company c JOIN c.employees e WHERE c.id = :companyId " +
			"GROUP BY e.position.name ORDER BY averageSalary DESC")
	List<AverageSalaryByPosition> createSalarySummaryByCompanyId(long companyId);
}
