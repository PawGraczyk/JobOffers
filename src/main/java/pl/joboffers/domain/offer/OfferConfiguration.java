package pl.joboffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.joboffers.domain.offersfetcher.OffersFetcherFacade;

import java.util.List;
import java.util.Optional;


@Configuration
public class OfferConfiguration {

    @Bean
    OfferRepository offerRepository() {
        return new OfferRepository() {
            @Override
            public List<Offer> findAll() {
                return null;
            }

            @Override
            public Optional<Offer> findById(String inputId) {
                return Optional.empty();
            }

            @Override
            public Offer save(Offer offer) {
                return null;
            }
        };
    }

    @Bean
    OfferService offerService() {
        return new OfferService(offerRepository());
    }

    @Bean
    OfferFacade offerFacade(OfferRepository repository, OffersFetcherFacade fetcherFacade, OfferService service) {
        return new OfferFacade(repository, fetcherFacade, service);
    }
}
