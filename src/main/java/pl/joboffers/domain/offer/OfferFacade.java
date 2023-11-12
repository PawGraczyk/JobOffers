package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.dto.FindByIdRequestDto;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import pl.joboffers.domain.offersfetcher.OffersFetcherFacade;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OfferFacade {

    private final static String OFFER_NOT_FOUND_MESSAGE = "Offer not found";
    private final OfferRepository repository;
    private final OffersFetcherFacade fetcherFacade;

    public List<OfferResponseDto> findAllOffers() {
        return repository.findAll()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public OfferResponseDto findOfferById(FindByIdRequestDto requestDto) {
        String requestString = requestDto.id();
        return repository.findById(requestString)
                .map(OfferMapper::mapFromOfferToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException(OFFER_NOT_FOUND_MESSAGE));
    }

    public OfferResponseDto saveOffer(OfferRequestDto offerRequestDto) {
        Offer offerDocument = OfferMapper.mapFromOfferDtoToOffer(offerRequestDto);
        Offer save = repository.save(offerDocument);
        return OfferMapper.mapFromOfferToOfferDto(save);
    }

    public List<OfferResponseDto> fetchRemoteOffersAndSaveIfNotExists() {
        List<RemoteJobOfferDto> fetchedRemoteJobOffers = fetcherFacade.fetchRemoteJobOffers();
        return fetchedRemoteJobOffers.stream()
                .filter(remoteJobOffer -> remoteJobOffer.offerUrl() != null)
                .filter(remoteJobOffer -> !repository.existsByOfferUrl(remoteJobOffer.offerUrl()))
                .map(OfferMapper::mapFromRemoteJobOfferToOffer)
                .map(repository::save)
                .map(OfferMapper::mapFromOfferToOfferDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
