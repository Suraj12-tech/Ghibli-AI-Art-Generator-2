package in.bushansirgur.ghbliapi.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Ye class Feign Client ki configuration ke liye hai.
 */
@Configuration
public class FeignConfig {

	@Bean
	public Encoder feignFormEncoder() {

		return new SpringFormEncoder(
				new SpringEncoder(
						() -> new HttpMessageConverters()
				)
		);
	}
}