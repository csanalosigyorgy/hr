package hu.webuni.hr.gyuri96.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hu.webuni.hr.gyuri96.dto.HolidayRequestDto;
import hu.webuni.hr.gyuri96.dto.HolidayRequestFilterDto;
import hu.webuni.hr.gyuri96.mapper.HolidayRequestMapper;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.model.HolidayRequest;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;
import hu.webuni.hr.gyuri96.repository.HolidayRequestRepository;
import hu.webuni.hr.gyuri96.specification.HolidayRequestSpecification;

@Service
public class HolidayRequestService {

	@Autowired
	private HolidayRequestMapper holidayRequestMapper;

	@Autowired
	private HolidayRequestRepository holidayRequestRepository;

	@Autowired
	private EmployeeRepository employeeRepository;


	public List<HolidayRequestDto> findAll() {
		return holidayRequestMapper.toHolidayRequestDtos(holidayRequestRepository.findAll());
	}

	public HolidayRequestDto findById(Long id) {
		return holidayRequestMapper.toHolidayRequestDto(holidayRequestRepository.findById(id).orElseThrow(NoSuchElementException::new));
	}

	public List<HolidayRequestDto> findHolidayRequestsByExample(HolidayRequestFilterDto example, Pageable pageable) {
		LocalDateTime creationDateTimeStart = example.getCreationDateTimeStart();
		LocalDateTime createDateTimeEnd = example.getCreationDateTimeEnd();
		String issuerName = example.getIssuerName();
		String approvalName = example.getApproverName();
		Boolean isApproved = example.getIsApproved();
		LocalDate startOfHolidayRequest = example.getStartDate();
		LocalDate endOfHolidayRequest = example.getEndDate();

		Specification<HolidayRequest> spec = Specification.where(null);

		if (isApproved != null)
			spec = spec.and(HolidayRequestSpecification.hasApproved(isApproved));
		if (creationDateTimeStart != null && createDateTimeEnd != null)
			spec = spec.and(HolidayRequestSpecification.createDateIsBetween(creationDateTimeStart, createDateTimeEnd));
		if (StringUtils.hasText(issuerName))
			spec = spec.and(HolidayRequestSpecification.hasIssuerName(issuerName));
		if (StringUtils.hasText(approvalName))
			spec = spec.and(HolidayRequestSpecification.hasApprovalName(approvalName));
		if (startOfHolidayRequest != null)
			spec = spec.and(HolidayRequestSpecification.isEndDateGreaterThan(startOfHolidayRequest));
		if (endOfHolidayRequest != null)
			spec = spec.and(HolidayRequestSpecification.isStartDateLessThan(endOfHolidayRequest));

		return holidayRequestMapper.toHolidayRequestDtos(holidayRequestRepository.findAll(spec, pageable).getContent());
	}

	@Transactional
	public HolidayRequestDto saveHolidayRequest(HolidayRequestDto holidayRequestDto){
		HolidayRequest holidayRequest = holidayRequestMapper.toHolidayRequest(holidayRequestDto);
		Employee employee = employeeRepository.findByIdDetailed(holidayRequestDto.getIssuerId()).orElseThrow(NoSuchElementException::new);
		holidayRequest.setIssuingDate(LocalDateTime.now());
		employee.addHolidayRequest(holidayRequest);
		return holidayRequestMapper.toHolidayRequestDto(holidayRequestRepository.save(holidayRequest));
	}

	@Transactional
	public HolidayRequestDto processHolidayRequest(long id, long approverId, boolean status){
		HolidayRequest holidayRequest = holidayRequestRepository.findById(id).orElseThrow(NoSuchElementException::new);
		Employee approver = employeeRepository.findById(approverId).orElseThrow(NoSuchElementException::new);

		holidayRequest.setApprover(approver);
		holidayRequest.setIsApproved(status);
		holidayRequest.setApprovingTime(LocalDateTime.now());
		return holidayRequestMapper.toHolidayRequestDto(holidayRequest);
	}

	@Transactional
	public HolidayRequestDto modifyHolidayRequest(long id, HolidayRequestDto modifiedHolidayRequest){
		HolidayRequest holidayRequest = holidayRequestRepository.findById(id).orElseThrow(NoSuchElementException::new);
		if(Objects.nonNull(holidayRequest.getIsApproved()))
			throw new IllegalStateException();

		holidayRequest.setStartingDate(modifiedHolidayRequest.getStartingDate());
		holidayRequest.setEndingDate(modifiedHolidayRequest.getEndingDate());
		holidayRequest.setIssuingDate(LocalDateTime.now());
		return holidayRequestMapper.toHolidayRequestDto(holidayRequest);
	}

	@Transactional
	public void deleteHolidayRequest(long id){
		HolidayRequest holidayRequest = holidayRequestRepository.findById(id).orElseThrow(NoSuchElementException::new);
		if(Objects.nonNull(holidayRequest.getIsApproved()))
			throw new IllegalStateException();
		holidayRequest.getIssuer().removeHolidayRequest(holidayRequest);
		holidayRequestRepository.delete(holidayRequest);
	}
}
