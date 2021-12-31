package hu.webuni.hr.gyuri96.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayRequestDto {

	private long id;
	private LocalDateTime issuingDate;
	@NotNull
	private Long issuerId;
	private Long approverId;
	private Boolean isApproved;
	private LocalDateTime approvedAt;
	@NotNull
	private LocalDate startingDate;
	@NotNull
	private LocalDate endingDate;
}
