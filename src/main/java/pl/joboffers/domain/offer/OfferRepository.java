package pl.joboffers.domain.offer;

import java.util.List;
import java.util.Optional;


public interface OfferRepository {
    List<Offer> findAll();

    Optional<Offer> findById(String inputId);

    Offer save(Offer offer);

}
