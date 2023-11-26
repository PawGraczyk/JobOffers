package pl.joboffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.joboffers.domain.offer.OfferRepository;
import pl.joboffers.infrastructure.offersfetcher.httpclient.OffersFetcherHttpClientConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OffersFetcherHttpClientConfigurationProperties.class)
@EnableMongoRepositories(basePackageClasses = OfferRepository.class)
public class JobOffersSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobOffersSpringBootApplication.class, args);
    }

}
