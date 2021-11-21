package hu.webuni.hr.gyuri96.dto;

import hu.webuni.hr.gyuri96.model.RequiredEducationLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDto {

	private long id;

	private String name;

	private RequiredEducationLevel educationLevel;
}
