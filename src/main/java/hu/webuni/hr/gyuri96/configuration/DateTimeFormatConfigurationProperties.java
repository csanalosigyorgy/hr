package hu.webuni.hr.gyuri96.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "spring")
@Data
public class DateTimeFormatConfigurationProperties {

	private Mvc mvc = new Mvc();

	@Data
	public static class Mvc {
		private Format format = new Format();
	}

	@Data
	public static class Format {
		private String date;
		private String dateTime;
	}

}
