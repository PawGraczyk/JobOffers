package pl.joboffers.domain.offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class OfferRepositoryTestImplementation implements OfferRepository {

    private final Map<String, Offer> inMemoryOfferDatabase;

    public OfferRepositoryTestImplementation() {
        inMemoryOfferDatabase = new ConcurrentHashMap<>();
    }

    public OfferRepositoryTestImplementation(Map<String, Offer> inMemoryOfferDatabase) {
        this.inMemoryOfferDatabase = inMemoryOfferDatabase;
    }

    @Override
    public List<Offer> findAll() {
        return new ArrayList<>(inMemoryOfferDatabase.values());
    }

    @Override
    public Optional<Offer> findById(String inputId) {
        return Optional.ofNullable(inMemoryOfferDatabase.get(inputId));
    }

    @Override
    public Offer save(Offer offer) {
        boolean offerAlreadyExists = inMemoryOfferDatabase.values().stream().anyMatch(offerInDatabase -> offerInDatabase.offerUrl().equals(offer.offerUrl()));
        if (offerAlreadyExists) {
            throw new OfferUniqueConstraintViolationException("Entity already exists in the database");
        }
        inMemoryOfferDatabase.put(offer.offerUrl(), offer);
        return offer;
    }
}
