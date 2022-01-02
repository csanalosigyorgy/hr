package hu.webuni.hr.gyuri96.security;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import hu.webuni.hr.gyuri96.configuration.HrConfigurationProperties;
import hu.webuni.hr.gyuri96.model.Employee;

@Service
public class JwtService {

	private String auth;
	private String issuer;
	private Algorithm alg;
	private Duration expireDuration;

	private static final String EMPLOYEE_ID = "employee_id";
	private static final String EMPLOYEE_FULL_NAME = "full_name";
	public static final String MANAGER = "manager";
	public static final String MANAGER_ID = "id";
	public static final String MANAGER_USERNAME = "username";
	public static final String MANAGED_EMPLOYEE_IDS = "managed_employee_ids";
	public static final String MANAGED_EMPLOYEE_USERNAMES = "managed_employee_usernames";

	@Autowired
	private HrConfigurationProperties config;

	@PostConstruct
	public void init(){
		this.auth = config.getJwt().getAuth();
		this.issuer = config.getJwt().getIssuer();
		try {
			this.alg = (Algorithm) Algorithm.class.getMethod(config.getJwt().getAlg(), String.class).invoke(Algorithm.class, config.getJwt().getSecret());
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		this.expireDuration = config.getJwt().getDuration();
	}

	public String createJwtToken(UserDetails principal) {
		JWTCreator.Builder jwtBuilder = JWT.create()
				.withSubject(principal.getUsername())
				.withArrayClaim(auth, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));

		Employee employee = ((HrUser) principal).getEmployee();
		Employee manager = employee.getManager();
		Set<Employee> managedEmployees = employee.getManagedEmployees();

		jwtBuilder.withClaim(EMPLOYEE_ID, employee.getId());
		jwtBuilder.withClaim(EMPLOYEE_FULL_NAME, employee.getName());

		if(Objects.nonNull(manager)){
			jwtBuilder.withClaim(MANAGER, Map.of(
					MANAGER_ID, manager.getId(),
					MANAGER_USERNAME, manager.getUsername()));
		}

		if(Objects.nonNull(managedEmployees) && !managedEmployees.isEmpty()){
			jwtBuilder.withArrayClaim(MANAGED_EMPLOYEE_IDS, managedEmployees.stream().map(Employee::getId).toArray(Long[]::new));
			jwtBuilder.withArrayClaim(MANAGED_EMPLOYEE_USERNAMES, managedEmployees.stream().map(Employee::getUsername).toArray(String[]::new));
		}

		return jwtBuilder
				.withExpiresAt(new Date(System.currentTimeMillis() + expireDuration.toMillis()))
				.withIssuer(issuer)
				.sign(alg);
	}

	public UserDetails parseJwt(String jwtToken) {
		DecodedJWT decodedJwt = JWT.require(alg)
				.withIssuer(issuer)
				.build()
				.verify(jwtToken);

		Employee employee = new Employee();
		employee.setId(decodedJwt.getClaim(EMPLOYEE_ID).asLong());
		employee.setName(decodedJwt.getClaim(EMPLOYEE_FULL_NAME).asString());
		employee.setUsername(decodedJwt.getSubject());

		employee.setManager(getManagerFromDecodedJwt(decodedJwt));
		employee.setManagedEmployees(getManagedEmployeesFromDecodedJwt(decodedJwt));

		return new HrUser(
				decodedJwt.getSubject(),
				"dummy",
				decodedJwt.getClaim(auth).asList(String.class).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
				employee);
	}

	private Employee getManagerFromDecodedJwt(DecodedJWT decodedJwt){
		Employee manager = new Employee();
		Claim managerClaim = decodedJwt.getClaim(MANAGER);
		if(Objects.nonNull(managerClaim)) {
			Map<String, Object> managerData = managerClaim.asMap();
			if (Objects.nonNull(managerData)) {
				manager.setId(((Integer) managerData.get(MANAGER_ID)).longValue());
				manager.setUsername(managerData.get(MANAGER_USERNAME).toString());
			}
		}
		return manager;
	}

	private Set<Employee> getManagedEmployeesFromDecodedJwt(DecodedJWT decodedJwt){
		Set<Employee> managedEmployees = new HashSet<>();
		Claim managedEmployeesUsernameClaim = decodedJwt.getClaim(MANAGED_EMPLOYEE_USERNAMES);
		if(Objects.nonNull(managedEmployeesUsernameClaim)){
			List<String> managedEmployeeUsernames = managedEmployeesUsernameClaim.asList(String.class);
			if(Objects.nonNull(managedEmployeeUsernames) && !managedEmployeeUsernames.isEmpty()){
				List<Long> managedEmployeeIds = decodedJwt.getClaim(MANAGED_EMPLOYEE_IDS).asList(Long.class);
				for(int i = 0; i < managedEmployeeUsernames.size(); i++){
					Employee managedEmployee = new Employee();
					managedEmployee.setId(managedEmployeeIds.get(i));
					managedEmployee.setUsername(managedEmployeeUsernames.get(i));
					managedEmployees.add(managedEmployee);
				}
			}
		}
		return managedEmployees;
	}
}
