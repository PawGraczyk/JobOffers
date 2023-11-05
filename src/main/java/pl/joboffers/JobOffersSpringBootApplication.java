package pl.joboffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.joboffers.infrastructure.offersfetcher.httpclient.OffersFetcherRestTemplateConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OffersFetcherRestTemplateConfigurationProperties.class)
public class JobOffersSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobOffersSpringBootApplication.class, args);
    }

}
