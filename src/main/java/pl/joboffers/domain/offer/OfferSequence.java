package pl.joboffers.domain.offer;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "offer_sequence")
record OfferSequence(@Id String id, long sequenceCurVal) {
}

