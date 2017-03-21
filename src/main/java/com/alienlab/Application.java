package com.alienlab;

import com.alienlab.wa17.WaImageProp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class Application {
	@Bean
	@ConfigurationProperties(prefix = "17wa.image")
	public WaImageProp waImageProp(){
		return new WaImageProp();

	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
