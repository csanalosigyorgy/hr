package hu.webuni.hr.gyuri96.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


import hu.webuni.hr.gyuri96.dto.PositionDto;
import hu.webuni.hr.gyuri96.model.Position;

@Mapper(componentModel = "spring")
public interface PositionMapper {

	@Named("toPositionDto")
	PositionDto toPositionDto(Position position);

	@InheritInverseConfiguration(name = "toPositionDto")
	Position toPositon(PositionDto positionDto);
}
