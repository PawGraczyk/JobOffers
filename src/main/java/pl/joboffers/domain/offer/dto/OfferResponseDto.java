package pl.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(
        String id,
        String company,
        String title,
        String salary,
        String offerUrl
) {
}
