package zerobase.demo.common.config;

import java.util.function.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("zerobase.demo"))
			.paths(PathSelectors.any())
			.paths(Predicate.not(PathSelectors.ant("/error/**")))
			.paths(Predicate.not(PathSelectors.ant("/login/**")))
			.build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("ZOGIYO 배달 대행 시스템 API 명세서")
			.description("ZOGIYO 배달 대행 시스템을 사용하기 위한 API 명세서 입니다.")
			.version("1.0")
			.build();
	}
}
