package hu.webuni.hr.gyuri96.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayRequestFilterDto {

	private LocalDateTime creationDateTimeStart;
	private LocalDateTime creationDateTimeEnd;
	private String issuerName;
	private String approverName;
	private Boolean isApproved;
	private LocalDate startDate;
	private LocalDate endDate;
}
