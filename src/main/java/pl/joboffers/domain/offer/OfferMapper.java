package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

public class OfferMapper {

    public static OfferResponseDto mapFromOfferToOfferDto(Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .company(offer.company())
                .title(offer.title())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }

    public static Offer mapFromOfferDtoToOffer(OfferRequestDto offerRequestDto) {
        return Offer.builder()
                .company(offerRequestDto.company())
                .title(offerRequestDto.title())
                .salary(offerRequestDto.salary())
                .offerUrl(offerRequestDto.offerUrl())
                .build();
    }

    public static Offer mapFromRemoteJobOfferToOffer(RemoteJobOfferDto jobOfferDto){
        return Offer.builder()
                .company(jobOfferDto.company())
                .title(jobOfferDto.title())
                .salary(jobOfferDto.salary())
                .offerUrl(jobOfferDto.offerUrl())
                .build();
    }
}
