package pl.joboffers.domain.offer;

import java.util.Optional;
import java.util.Set;

public interface OfferRepository {
    Set<Offer> findAllOffers();
    Optional<Offer> findOfferById(String inputId);
    Offer saveOffer();

}
