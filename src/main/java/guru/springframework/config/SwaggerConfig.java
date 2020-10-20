package guru.springframework.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig { // extends WebMvcConfigurationSupport {
	
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build()
			.pathMapping("/")
			.apiInfo(metaData());
	}

	// if you swagger-ui having problem not show, enable code below for manual configuration
//	@Override
//	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//		
//		registry.addResourceHandler("swagger-ui.html")
//			.addResourceLocations("classpath:/META-INF/resources/");
//		
//		registry.addResourceHandler("webjars/**")
//			.addResourceLocations("classpath:/META-INF/resources/webjars/");
//	}
	
	
	private ApiInfo metaData() {
		
		Contact contact = new Contact("Nickolas Jonathan", "https://nikojona.id/about/", 
				"nicko@nikojona.id");
		
		return new ApiInfo(
				"Spring framework Guru",
				"Spring framework 5: Beginner to Guru",
				"1.0",
				"Terms of Service: blah blah",
				contact,
				"Apache License Version 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<>());
			
	}
}
