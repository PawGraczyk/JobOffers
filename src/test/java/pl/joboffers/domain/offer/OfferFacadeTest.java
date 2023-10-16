package pl.joboffers.domain.offer;

import org.junit.jupiter.api.Test;
import pl.joboffers.domain.offer.dto.FindByIdRequestDto;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import pl.joboffers.domain.offersfetcher.OffersFetcherFacade;
import pl.joboffers.domain.offersfetcher.OffersFetcherTestImplementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OfferFacadeTest {

    private final OfferFacade offerFacade = new OfferFacade(
            new OfferRepositoryTestImplementation(),
            new OffersFetcherFacade(
                    new OffersFetcherTestImplementation())
    );

    @Test
    public void shouldReturnCollectionOfAllOffersFromDatabase() {
        //given
        //when
        List<OfferResponseDto> allOffers = offerFacade.findAllOffers();
        // then
        assertThat(allOffers).isInstanceOf(Collection.class);
    }

    @Test
    public void should_throw_exception_when_offer_id_not_found() {
        // given
        FindByIdRequestDto requestedId = FindByIdRequestDto.builder()
                .id("12344UED")
                .build();
        //when
        //then
        assertThrows(OfferNotFoundException.class,
                () -> offerFacade.findOfferById(requestedId),
                "Offer not found");
    }

    @Test
    public void should_save_new_offer() {
        //given
        OfferRequestDto offerRequestDto = OfferRequestDto
                .builder()
                .company("TestCompany")
                .title("Title")
                .salary("5000 - 10000 PLN")
                .offerUrl("www.example.com.pl")
                .build();
        //when
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(offerRequestDto);

        //then
        assertThat(offerResponseDto).isEqualTo(
                OfferResponseDto.builder()
                        .id(offerResponseDto.id())
                        .company(offerResponseDto.company())
                        .title(offerResponseDto.title())
                        .salary(offerResponseDto.salary())
                        .offerUrl("www.example.com.pl")
                        .build()
        );
    }

    @Test
    public void should_throw_exception_when_offer_url_already_exists() {
        //given
        OfferRequestDto offerRequestDto = OfferRequestDto
                .builder()
                .company("TestCompany")
                .title("Title")
                .salary("5000 - 10000 PLN")
                .offerUrl("www.example.com.pl")
                .build();
        // when
        offerFacade.saveOffer(offerRequestDto);
        //then
        assertThrows(OfferUniqueConstraintViolationException.class,
                () -> offerFacade.saveOffer(offerRequestDto),
                "Entity already exists in the database");
    }

}