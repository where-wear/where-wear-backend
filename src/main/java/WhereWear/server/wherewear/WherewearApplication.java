package WhereWear.server.wherewear;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.sql.Connection;
@EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan
public class WherewearApplication {

	public static void main(String[] args) {

		SpringApplication.run(WherewearApplication.class, args);

	}
	@Bean
	public Storage storage() {
		return StorageOptions.getDefaultInstance().getService();
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

	@Bean
	public ApplicationRunner runner(DataSource dataSource){
		return args->{
			Connection connection = dataSource.getConnection();
		};
	}

}
