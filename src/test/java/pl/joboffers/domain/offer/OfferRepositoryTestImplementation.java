package pl.joboffers.domain.offer;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OfferRepositoryTestImplementation implements OfferRepository {

    private final Map<String, Offer> inMemoryOfferDatabase = new ConcurrentHashMap<>();


    @Override
    public Set<Offer> findAllOffers() {
        return null;
    }

    @Override
    public Optional<Offer> findOfferById(String inputId) {
        return Optional.ofNullable(inMemoryOfferDatabase.get(inputId));
    }

    @Override
    public Offer saveOffer() {
        return null;
    }
}
