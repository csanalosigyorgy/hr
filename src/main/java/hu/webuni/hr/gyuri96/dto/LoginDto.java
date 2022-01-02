package hu.webuni.hr.gyuri96.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {

	private String username;
	private String password;
}
