package hu.webuni.hr.gyuri96.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.gyuri96.dto.HolidayRequestDto;
import hu.webuni.hr.gyuri96.model.HolidayRequest;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface HolidayRequestMapper {

	@Named("toHolidayRequestDto")
	@Mapping(target = "issuerId", source = "issuer.id")
	@Mapping(target = "approverId", source = "approver.id")
	@Mapping(target = "approvedAt", source = "approvingTime")
	HolidayRequestDto toHolidayRequestDto(HolidayRequest holidayRequest);

	@IterableMapping(qualifiedByName = "toHolidayRequestDto")
	List<HolidayRequestDto> toHolidayRequestDtos(List<HolidayRequest> holidayRequests);

	@InheritInverseConfiguration(name = "toHolidayRequestDto")
	HolidayRequest toHolidayRequest(HolidayRequestDto holidayRequestDto);
}
