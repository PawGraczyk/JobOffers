package pl.joboffers.infrastructure.offer.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class JobOffersScheduler {

    private final OfferFacade offerFacade;
    private final static String SCHEDULER_START_MESSAGE = "Scheduler started at {}.";
    private final static String ADDED_OBJECTS_COUNT = "Added {} unique objects to database.";
    private final static String SCHEDULER_END_MESSAGE = "Scheduler stopped ar {}";

    @Scheduled(fixedDelayString = "${joboffers.scheduler.fetchOccurrence}")
    public List<OfferResponseDto> fetchRemoteJobOffers() {
        log.info(SCHEDULER_START_MESSAGE, LocalDateTime.now());
        final List<OfferResponseDto> addedObjects = offerFacade.fetchRemoteOffersAndSaveIfNotExists();
        log.info(ADDED_OBJECTS_COUNT, addedObjects.size());
        log.info(SCHEDULER_END_MESSAGE, LocalDateTime.now());
        return addedObjects;

    }
}
