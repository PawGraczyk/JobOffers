package pl.joboffers.domain.offersfetcher;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

import java.util.List;

@AllArgsConstructor
public class OffersFetcherFacade {

    private final OfferFetchable fetcher;

    public List<RemoteJobOfferDto> fetchRemoteJobOffers() {
        return fetcher.fetchOffers();
    }
}
