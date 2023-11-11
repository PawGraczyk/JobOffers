package pl.joboffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.joboffers.infrastructure.offersfetcher.restclient.OffersFetcherHttpClientConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OffersFetcherHttpClientConfigurationProperties.class)
public class JobOffersSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobOffersSpringBootApplication.class, args);
    }

}
