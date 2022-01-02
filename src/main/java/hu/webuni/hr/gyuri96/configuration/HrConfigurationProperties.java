package hu.webuni.hr.gyuri96.configuration;

import java.time.Duration;
import java.util.TreeMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "hr")
@Data
public class HrConfigurationProperties {

	private Salary salary = new Salary();
	private JwtData jwt = new JwtData();

	@Data
	public static class Salary {
		private Default aDefault = new Default();
		private Smart aSmart = new Smart();
	}

	@Data
	public static class Default {
		private int percentage;
	}

	@Data
	public static class Smart {
		private TreeMap<Double, Integer> limits;
	}

	@Data
	public static class JwtData {
		private String issuer;
		private String secret;
		private String alg;
		private Duration duration;
		private String auth;
	}
}
