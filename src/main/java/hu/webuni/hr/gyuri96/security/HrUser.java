package hu.webuni.hr.gyuri96.security;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import hu.webuni.hr.gyuri96.model.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HrUser extends User {

	private Employee employee;


	public HrUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Employee employee) {
		super(username, password, authorities);
		this.employee = employee;
	}
}
