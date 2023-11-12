package pl.joboffers.domain.offer;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


public class OfferRepositoryTestImplementation implements OfferRepository {

    private final Map<String, Offer> inMemoryOfferDatabase;

    public OfferRepositoryTestImplementation() {
        inMemoryOfferDatabase = new ConcurrentHashMap<>();
    }

    public OfferRepositoryTestImplementation(Map<String, Offer> inMemoryOfferDatabase) {
        this.inMemoryOfferDatabase = inMemoryOfferDatabase;
    }

    @Override
    public <S extends Offer> S save(S entity) {
        boolean offerAlreadyExists = inMemoryOfferDatabase.values().stream().anyMatch(offerInDatabase -> offerInDatabase.offerUrl().equals(entity.offerUrl()));
        if (offerAlreadyExists) {
            throw new OfferUniqueConstraintViolationException("Entity already exists in the database");
        }
        inMemoryOfferDatabase.put(entity.offerUrl(), entity);
        return entity;
    }

    @Override
    public boolean existsByOfferUrl(String url) {
        return inMemoryOfferDatabase.containsKey(url);
    }

    @Override
    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(inMemoryOfferDatabase.get(id));
    }

    @Override
    public <S extends Offer> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<Offer> findAll() {
        return new ArrayList<>(inMemoryOfferDatabase.values());
    }

    @Override
    public List<Offer> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Offer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Offer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public <S extends Offer> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Offer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Offer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Offer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Offer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Offer> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Offer> findAll(Pageable pageable) {
        return null;
    }

}

