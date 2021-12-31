package hu.webuni.hr.gyuri96.specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.model.Employee_;
import hu.webuni.hr.gyuri96.model.HolidayRequest;
import hu.webuni.hr.gyuri96.model.HolidayRequest_;

public class HolidayRequestSpecification {

	public static Specification<HolidayRequest> hasApproved(boolean isApproved) {
		return (root, cq, cb) -> cb.equal(root.get(HolidayRequest_.isApproved), isApproved);
	}

	public static Specification<HolidayRequest> createDateIsBetween(LocalDateTime createDateTimeStart, LocalDateTime createDateTimeEnd) {
		return (root, cq, cb) -> cb.between(root.get(HolidayRequest_.issuingDate), createDateTimeStart, createDateTimeEnd);
	}

	public static Specification<HolidayRequest> hasIssuerName(String name) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(HolidayRequest_.issuer).get(Employee_.name)), name.toLowerCase() + "%");
	}

	public static Specification<HolidayRequest> hasApprovalName(String approvalName) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(HolidayRequest_.approver).get(Employee_.name)),
				(approvalName + "%").toLowerCase());
	}

	public static Specification<HolidayRequest> isStartDateLessThan(LocalDate startOfHolidayRequest) {
		return (root, cq, cb) -> cb.lessThan(root.get(HolidayRequest_.startingDate), startOfHolidayRequest);
	}

	public static Specification<HolidayRequest> isEndDateGreaterThan(LocalDate endOfHolidayRequest) {
		return (root, cq, cb) -> cb.greaterThan(root.get(HolidayRequest_.endingDate), endOfHolidayRequest);
	}
}
