package pl.joboffers.domain.offer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OfferRequestDto(
        @NotEmpty(message = "{company.not.empty}") @NotNull(message = "{company.not.null}") String company,
        @NotEmpty(message = "{title.not.empty}") @NotNull(message = "{title.not.null}") String title,
        @NotEmpty(message = "{salary.not.empty}") @NotNull(message = "{salary.not.null}") String salary,
        @NotEmpty(message = "{url.not.empty}") @NotNull(message = "{url.not.null}") String offerUrl) {


}

