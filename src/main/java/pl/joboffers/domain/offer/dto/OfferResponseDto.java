package pl.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder

public record OfferResponseDto(
        Long id,
        String company,
        String title,
        String salary,
        String offerUrl
) {
}
