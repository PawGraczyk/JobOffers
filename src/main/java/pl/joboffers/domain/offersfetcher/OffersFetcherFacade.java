package pl.joboffers.domain.offersfetcher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

import java.util.List;

@AllArgsConstructor
@Component
public class OffersFetcherFacade {

    private final OffersFetchable fetcher;

    public List<RemoteJobOfferDto> fetchRemoteJobOffers() {
        return fetcher.fetchOffers();
    }
}
