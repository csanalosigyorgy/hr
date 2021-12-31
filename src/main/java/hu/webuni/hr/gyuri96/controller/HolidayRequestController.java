package hu.webuni.hr.gyuri96.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.gyuri96.dto.HolidayRequestDto;
import hu.webuni.hr.gyuri96.dto.HolidayRequestFilterDto;
import hu.webuni.hr.gyuri96.service.HolidayRequestService;

@RestController
@RequestMapping("/api/holiday-request")
public class HolidayRequestController {

	@Autowired
	private HolidayRequestService holidayRequestService;

	@GetMapping
	public List<HolidayRequestDto> getHolidayRequests(){
		return holidayRequestService.findAll();
	}

	@GetMapping("/{id}")
	public HolidayRequestDto getHolidayRequestById(@PathVariable long id){
		return holidayRequestService.findById(id);
	}

	@PostMapping("/search")
	public List<HolidayRequestDto> getHolidayRequestByExample(@RequestBody HolidayRequestFilterDto holidayRequestFilterDto, Pageable pageable){
		return holidayRequestService.findHolidayRequestsByExample(holidayRequestFilterDto, pageable);
	}

	@PostMapping
	@PreAuthorize("#holidayRequestDto.issuerId == authentication.principal.employee.id")
	public HolidayRequestDto createHolidayRequest(@RequestBody @Valid HolidayRequestDto holidayRequestDto){
		return holidayRequestService.saveHolidayRequest(holidayRequestDto);
	}

	@PutMapping("/{id}")
	@PreAuthorize("#holidayRequestDto.issuerId == authentication.principal.employee.id")
	public HolidayRequestDto modifyHolidayRequest(@PathVariable Long id, @RequestBody HolidayRequestDto holidayRequestDto) {
		return holidayRequestService.modifyHolidayRequest(id, holidayRequestDto);
	}

	@DeleteMapping("/{issuerId}/{id}")
	@PreAuthorize("#issuerId == authentication.principal.employee.id")
	public void deleteHolidayRequest(@PathVariable long id){
		holidayRequestService.deleteHolidayRequest(id);
	}

	@PutMapping(value = "/{id}/process", params = {"approverId", "status"})
	//@PreAuthorize("#approverId == authentication.principal.employee.id") // TODO managerId kell majd itt!
	public HolidayRequestDto processHolidayRequest(@PathVariable long id, @RequestParam long approverId, @RequestParam boolean status){
		return holidayRequestService.processHolidayRequest(id, approverId, status);
	}
}
