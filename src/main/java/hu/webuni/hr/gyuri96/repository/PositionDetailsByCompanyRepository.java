package hu.webuni.hr.gyuri96.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.gyuri96.model.PositionDetailsByCompany;

public interface PositionDetailsByCompanyRepository extends JpaRepository<PositionDetailsByCompany, Long> {

	@EntityGraph("positionByDetails.full")
	Optional<PositionDetailsByCompany> findByCompanyIdAndPositionName(long companyId, String positionName);

	@EntityGraph("positionByDetails.full")
	Optional<PositionDetailsByCompany> findByCompanyIdAndPositionId(long companyId, long positionId);
}
