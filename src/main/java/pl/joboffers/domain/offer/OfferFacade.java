package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.dto.FindByIdRequestDto;
import pl.joboffers.domain.offer.dto.OfferDto;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OfferFacade {

       private final static String OFFER_NOT_FOUND_MESSAGE = "Offer not found";
       private final OfferRepository repository;

       public Set<OfferDto> getAllOffers(){
              return repository.findAllOffers()
                      .stream()
                      .map(OfferMapper::mapToOfferDto)
                      .collect(Collectors.toSet());
       }

       public OfferDto getOfferById(FindByIdRequestDto requestDto){
              String requestString = requestDto.id();
              return repository.findOfferById(requestString)
                      .map(OfferMapper::mapToOfferDto)
                      .orElseThrow(() -> new OfferNotFoundException(OFFER_NOT_FOUND_MESSAGE));
       }

}
