package pl.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferRequestDto(
        String company,
        String title,
        String salary,
        String offerUrl
) {
}

