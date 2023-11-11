package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
class OfferService {

    private final OfferRepository repository;


    public List<OfferResponseDto> saveUniqueOffers(List<RemoteJobOfferDto> fetchedOffers) {
        List<OfferResponseDto> savedOffers = new ArrayList<>();
        for (var remoteJobOffer : fetchedOffers) {
            try {
                Offer offerToBeSaved = OfferMapper.mapFromRemoteJobOfferToOffer(remoteJobOffer);
                Offer savedOffer = repository.save(offerToBeSaved);
                OfferResponseDto offerResponseDto = OfferMapper.mapFromOfferToOfferDto(savedOffer);
                savedOffers.add(offerResponseDto);
            } catch (OfferUniqueConstraintViolationException ex) {
                // do nothing in this case, when exception is thrown it means it`s a duplicate;
            }
        }
        return savedOffers;
    }
}
