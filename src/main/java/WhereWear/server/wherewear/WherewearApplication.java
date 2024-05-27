package WhereWear.server.wherewear;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WherewearApplication {

	public static void main(String[] args) {

		SpringApplication.run(WherewearApplication.class, args);

	}

	@Bean
	public ApplicationRunner runner(DataSource dataSource){
		return args->{
			Connection connection = dataSource.getConnection();
		};
	}

}
