package hu.webuni.hr.gyuri96.configuration;

import java.util.TreeMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "hr")
@Data
public class HrConfogurationProperties {

	private Salary salary = new Salary();

	@Data
	public static class Salary {
		private Default aDefault = new Default();
		private Smart aSmart = new Smart();
	}

	@Data
	public static class Default {
		private int percent;
	}

	@Data
	public static class Smart {
		private TreeMap<Double, Integer> limits;
	}
}
