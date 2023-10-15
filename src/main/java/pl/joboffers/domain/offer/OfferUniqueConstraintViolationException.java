package pl.joboffers.domain.offer;

public class OfferUniqueConstraintViolationException extends RuntimeException {
    public OfferUniqueConstraintViolationException(String message) {
        super(message);
    }
}
