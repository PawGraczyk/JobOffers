package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.OfferDto;

public class OfferMapper {

    public static OfferDto mapToOfferDto(Offer offer){
        return OfferDto.builder()
                .id(offer.id())
                .title(offer.title())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }
}
