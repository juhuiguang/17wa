package com.alienlab.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Springfox Swagger configuration.
 *
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
 * case, you can use a specific Spring profile for this class, so that only front-end developers
 * have access to the Swagger view.
 */
@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    public static final String DEFAULT_INCLUDE_PATTERN = "/test/.*,/api/.*";

    @Value("${17wa.swagger.contactName}")
    String contactName;
    @Value("${17wa.swagger.contactUrl}")
    String contactUrl;
    @Value("${17wa.swagger.contactEmail}")
    String contactEmail;
    @Value("${17wa.swagger.title}")
    String title;
    @Value("${17wa.swagger.description}")
    String description;
    @Value("${17wa.swagger.version}")
    String version;
    @Value("${17wa.swagger.termsOfServiceUrl}")
    String termsOfServiceUrl;
    @Value("${17wa.swagger.license}")
    String license;
    @Value("${17wa.swagger.licenseUrl}")
    String licenseUrl;
    
    @Bean
    public Docket swaggerSpringfoxDocket() {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                contactName,
                contactUrl,
                contactEmail);

        ApiInfo apiInfo = new ApiInfo(
                title,
                description,
                version,
                termsOfServiceUrl,
            contact,
                license,
                licenseUrl);

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .forCodeGeneration(true)
            .genericModelSubstitutes(ResponseEntity.class)
            .select()
            .paths(regex(DEFAULT_INCLUDE_PATTERN))
            .build();
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }
}
