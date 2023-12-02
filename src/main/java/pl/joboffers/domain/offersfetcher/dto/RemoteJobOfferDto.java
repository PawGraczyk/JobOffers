package pl.joboffers.domain.offersfetcher.dto;


import lombok.Getter;

public record RemoteJobOfferDto
        (String company,
         String title,
         String salary,
         String offerUrl) {
}
