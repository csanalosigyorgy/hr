package hu.webuni.hr.gyuri96.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties("hr")
@Data
public class HrConfogurationProperties {

	private Rise rise = new Rise();

	@Data
	public static class Rise {
		private int ten;
		private int five;
		private int twoAndHalf;
	}
}
