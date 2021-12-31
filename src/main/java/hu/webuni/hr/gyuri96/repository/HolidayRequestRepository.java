package hu.webuni.hr.gyuri96.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.gyuri96.model.HolidayRequest;

public interface HolidayRequestRepository extends JpaRepository<HolidayRequest, Long>, JpaSpecificationExecutor<HolidayRequest> {

	@EntityGraph(attributePaths = {"issuer", "approver"})
	@Query("select h from HolidayRequest h")
	List<HolidayRequest> findAll();

	@EntityGraph(attributePaths = {"issuer", "approver"})
	@Query("select h from HolidayRequest h where h.id = :id")
	Optional<HolidayRequest> findById(Long id);

	@EntityGraph(attributePaths = {"issuer", "approver"})
	@Query("select h from HolidayRequest h where h.issuer.id = :issuerId")
	List<HolidayRequest> findByIssuerId(Long issuerId);
}
