package pl.joboffers.infrastructure.offer.scheduler;

import lombok.AllArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.dto.OfferResponseDto;


import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class JobOffersScheduler {

    private final OfferFacade offerFacade;

    @Scheduled(cron = "${joboffers.scheduler.fetchOccurrence}")
    public void fetchJobOffers() {
        List<OfferResponseDto> fetchedRemoteJobOffers = offerFacade.fetchRemoteOffersAndSaveIfNotExists();
    }
}
