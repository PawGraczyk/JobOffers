package pl.joboffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.joboffers.domain.offersfetcher.OffersFetcherFacade;

@Configuration
public class OfferFacadeConfiguration {

    @Bean
    OfferFacade offerFacade(OfferRepository repository, OffersFetcherFacade fetcherFacade, OfferSequenceGenerator generator) {
        return new OfferFacade(repository, fetcherFacade, generator);
    }
}
