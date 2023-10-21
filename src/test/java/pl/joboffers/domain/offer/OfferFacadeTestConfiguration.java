package pl.joboffers.domain.offer;

import pl.joboffers.domain.offersfetcher.OffersFetcherFacade;

import java.util.Map;

class OfferFacadeTestConfiguration {

    public OfferFacadeTestConfiguration() {
    }

    public OfferFacade getOfferFacadeForTest(Map<String, Offer> testData) {
        OfferRepository repository = new OfferRepositoryTestImplementation(testData);
        OfferService offerService = new OfferService(repository);
        OffersFetcherFacade fetcherFacade = new OffersFetcherFacade(new OffersFetcherInOfferFacadeTestImplementation());
        return new OfferFacade(repository, fetcherFacade, offerService);
    }
}
