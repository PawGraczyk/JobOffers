package pl.joboffers.domain.offer;

import org.junit.jupiter.api.Test;
import pl.joboffers.domain.offer.dto.FindByIdRequestDto;

import static org.junit.jupiter.api.Assertions.assertThrows;


class OfferFacadeTest {

   private final OfferFacade offerFacade = new OfferFacade(
           new OfferRepositoryTestImplementation()
   );

    @Test
    public void should_throw_offer_not_found_exception_when_offer_id_not_found() {
        // given
        FindByIdRequestDto requestedId = FindByIdRequestDto.builder()
                .id("12344UED")
                .build();
        //when
        //then
        assertThrows(OfferNotFoundException.class,
                () -> offerFacade.getOfferById(requestedId),
                "Offer not found");

    }


}