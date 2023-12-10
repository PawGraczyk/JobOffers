package pl.joboffers.domain.offersfetcher;

import org.junit.jupiter.api.Test;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class OffersFetcherFacadeTest {

    @Test
    public void should_return_data_when_fetchable_offers_exist() {
        //given
        var testFacade = new OffersFetcherTestConfiguration().getFetcherForTest();
        //when
        List<RemoteJobOfferDto> fetchedRemoteJobOffers = testFacade.fetchRemoteJobOffers();
        //then
        assertThat(fetchedRemoteJobOffers.size()).isGreaterThan(0);

    }

    @Test
    public void should_not_return_null_when_no_offers_fetched() {
        //given
        var testFacade = new OffersFetcherFacade(new OffersFetcherTestImplementation());
        //when
        List<RemoteJobOfferDto> remoteJobOffers = testFacade.fetchRemoteJobOffers();
        //then
        assertThat(remoteJobOffers).isNotNull();
    }
}
