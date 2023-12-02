package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

@AllArgsConstructor
public class OfferMapper {

    public static OfferResponseDto mapFromOfferToOfferDto(Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.getId())
                .company(offer.getCompany())
                .title(offer.getTitle())
                .salary(offer.getSalary())
                .offerUrl(offer.getOfferUrl())
                .build();
    }

    public static Offer mapFromOfferDtoToOffer(OfferRequestDto offerRequestDto, Long newId) {
        return Offer.builder()
                .id(newId)
                .company(offerRequestDto.company())
                .title(offerRequestDto.title())
                .salary(offerRequestDto.salary())
                .offerUrl(offerRequestDto.offerUrl())
                .build();
    }

    public static Offer mapFromRemoteJobOfferToOffer(RemoteJobOfferDto jobOfferDto, Long newId) {
        return Offer.builder()
                .id(newId)
                .company(jobOfferDto.company())
                .title(jobOfferDto.title())
                .salary(jobOfferDto.salary())
                .offerUrl(jobOfferDto.offerUrl())
                .build();
    }
}
