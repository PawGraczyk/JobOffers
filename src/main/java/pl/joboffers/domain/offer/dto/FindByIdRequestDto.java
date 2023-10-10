package pl.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record FindByIdRequestDto(
        String id
) {
}
