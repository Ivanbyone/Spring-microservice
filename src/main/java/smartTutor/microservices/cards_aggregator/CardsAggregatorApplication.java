package smartTutor.microservices.cards_aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ConfigurationPropertiesScan("smartTutor.microservices.cards_aggregator.utils")
@EnableAsync
public class CardsAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsAggregatorApplication.class, args);
	}

}
