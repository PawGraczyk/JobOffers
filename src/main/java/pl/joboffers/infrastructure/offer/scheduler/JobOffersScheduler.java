package pl.joboffers.infrastructure.offer.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

@Component
@AllArgsConstructor
public class JobOffersScheduler {

    private final OfferFacade offerFacade;

    @Scheduled(fixedDelayString = "${joboffers.scheduler.fetchOccurrence}")
    public void fetchRemoteJobOffers() {
        final List<OfferResponseDto> fetchedRemoteJobOffers = offerFacade.fetchRemoteOffersAndSaveIfNotExists();
    }
}
