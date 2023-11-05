package pl.joboffers.infrastructure.offer.scheduler;

import lombok.AllArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.offersfetcher.OffersFetcherFacade;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class JobOffersScheduler {

    private final OffersFetcherFacade offersFetcherFacade;

    @Scheduled(cron = "*/10 * * * * *")
    public void fetchJobOffers() {
        List<RemoteJobOfferDto> fetchedRemoteJobOffers = offersFetcherFacade.fetchRemoteJobOffers();
    }
}
