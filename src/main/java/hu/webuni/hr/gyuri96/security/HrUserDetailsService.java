package hu.webuni.hr.gyuri96.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;


// ITT CSAK AZ ADATOK BETÖLTÉSE ZAJLIK!
@Slf4j
@Service
public class HrUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository
				.findByUsernameWithManagerAndManagedEmployees(username).orElseThrow(() -> new UsernameNotFoundException(username));
		log.info("User was login");
		return new HrUser(username,
				employee.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority("USER")), employee);
	}
}
