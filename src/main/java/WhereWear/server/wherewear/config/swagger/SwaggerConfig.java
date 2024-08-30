package WhereWear.server.wherewear.config.swagger;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // 보안 스키마 이름 정의
        String securitySchemeName = "JWT";

        // SecurityRequirement를 생성하여 JWT 스키마 이름을 추가합니다.
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securitySchemeName);

        // Components를 생성하고 보안 스키마를 추가합니다.
        Components components = new Components().addSecuritySchemes(securitySchemeName,
                new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT")
        );

        // OpenAPI 인스턴스를 생성하고 정보 및 보안 설정을 추가합니다.
        return new OpenAPI()
                .components(components)
                .info(apiInfo())
                .addSecurityItem(securityRequirement);
    }

    // API 정보 설정 메서드
    private Info apiInfo() {
        return new Info()
                .title("WhereWear API") // API의 제목
                .description("WhereWear의 api입니다.") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }
}
