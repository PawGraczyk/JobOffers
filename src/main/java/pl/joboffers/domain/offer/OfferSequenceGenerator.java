package pl.joboffers.domain.offer;

interface OfferSequenceGenerator {
    Long generateSequence(String sequenceName);
}
