package pl.joboffers.domain.offer.dto;

import lombok.Builder;
import lombok.Getter;

@Builder

public record OfferResponseDto(
        String id,
        String company,
        String title,
        String salary,
        String offerUrl
) {
}
