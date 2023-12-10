package pl.joboffers.domain.offersfetcher.dto;


public record RemoteJobOfferDto
        (String company, String title, String salary, String offerUrl) {
}
