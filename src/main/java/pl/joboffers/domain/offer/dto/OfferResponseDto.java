package pl.joboffers.domain.offer.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder

public record OfferResponseDto(Long id, String company, String title, String salary, String offerUrl

) implements Serializable {
}
