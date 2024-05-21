package WhereWear.server.wherewear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WherewearApplication {

	public static void main(String[] args) {
		SpringApplication.run(WherewearApplication.class, args);
	}

}
