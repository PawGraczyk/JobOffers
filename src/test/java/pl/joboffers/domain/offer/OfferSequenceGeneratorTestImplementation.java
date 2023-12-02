package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class OfferSequenceGeneratorTestImplementation implements OfferSequenceGenerator {
    private final Map<String, Offer> data;
    @Override
    public Long generateSequence(String sequenceName) {
        return (long)(data.size() + 1);
    }
}
