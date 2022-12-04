package com.promantus.Assessment.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private final String releaseVersion = "1.5.0";
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	LocalDateTime now = LocalDateTime.now();

	public Docket assessmentapi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.promantus.Assessment")).build()
				.globalOperationParameters(operationParameters()).apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Pro-Assessment - Spring Boot REST API")
				.description("\"An Internal Assessment Web Application Of Promantus Private Limited, chennai.\"" + now)
				.version(releaseVersion).build();

	}

	private List<Parameter> operationParameters() {

		List<Parameter> headers = new ArrayList<Parameter>();

		headers.add(new ParameterBuilder().name("PRO-API-KEY").description("Security token to be sent.")
				.modelRef(new ModelRef("string")).parameterType("header").required(true).build());
		return headers;
	}
	
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
