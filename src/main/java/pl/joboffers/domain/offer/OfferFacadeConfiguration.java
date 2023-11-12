package pl.joboffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.joboffers.domain.offersfetcher.OffersFetcherFacade;

import java.util.List;
import java.util.Optional;

@Configuration
public class OfferFacadeConfiguration {

    @Bean
    OfferFacade offerFacade(OfferRepository repository, OffersFetcherFacade fetcherFacade) {
        return new OfferFacade(repository, fetcherFacade);
    }
}
