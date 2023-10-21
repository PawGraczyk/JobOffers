package pl.joboffers.domain.offer;

import pl.joboffers.domain.offersfetcher.OfferFetchable;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

import java.util.List;

class OffersFetcherInOfferFacadeTestImplementation implements OfferFetchable {

    private final List<RemoteJobOfferDto> fetchedOffers = List.of(
            new RemoteJobOfferDto("TestCompany1", "TestTitle1", "TestSalary1", "TestUrl1"),
            new RemoteJobOfferDto("TestCompany2", "TestTitle2", "TestSalary2", "TestUrl2"),
            new RemoteJobOfferDto("TestCompany3", "TestTitle3", "TestSalary3", "TestUrl3"),
            new RemoteJobOfferDto("TestCompany4", "TestTitle4", "TestSalary4", "TestUrl4"),
            new RemoteJobOfferDto("TestCompany5", "TestTitle5", "TestSalary5", "TestUrl5"),
            new RemoteJobOfferDto("TestCompany6", "TestTitle6", "TestSalary6", "UniqueUrl1"),
            new RemoteJobOfferDto("TestCompany7", "TestTitle7", "TestSalary7", "UniqueUrl2")
    );

    @Override
    public List<RemoteJobOfferDto> fetchOffers() {
        return fetchedOffers;
    }


}
