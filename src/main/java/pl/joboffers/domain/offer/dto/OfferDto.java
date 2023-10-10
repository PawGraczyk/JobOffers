package pl.joboffers.domain.offer.dto;

import lombok.Builder;

import java.net.URL;

@Builder
public record OfferDto(
        String id,
        String title,
        String salary,
        URL offerUrl
) {
}
