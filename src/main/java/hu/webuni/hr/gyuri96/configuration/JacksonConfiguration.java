package hu.webuni.hr.gyuri96.configuration;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class JacksonConfiguration {

	@Autowired
	private DateTimeFormatConfigurationProperties formatConfig;

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> {
			builder.simpleDateFormat(formatConfig.getMvc().getFormat().getDateTime());
			builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(formatConfig.getMvc().getFormat().getDate())));
			builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(formatConfig.getMvc().getFormat().getDateTime())));

			builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(formatConfig.getMvc().getFormat().getDate())));
			builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(formatConfig.getMvc().getFormat().getDateTime())));
		};
	}
}
