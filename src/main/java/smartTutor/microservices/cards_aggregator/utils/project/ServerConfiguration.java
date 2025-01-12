package smartTutor.microservices.cards_aggregator.utils.project;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "server")
@ConfigurationPropertiesScan
public class ServerConfiguration {
    private int port;
}
