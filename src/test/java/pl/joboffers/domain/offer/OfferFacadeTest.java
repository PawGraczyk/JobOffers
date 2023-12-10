package pl.joboffers.domain.offer;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import pl.joboffers.domain.offer.dto.FindByIdRequestDto;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class OfferFacadeTest {

    @Test
    public void find_all_offers_should_not_return_null() {
        //given
        final Map<String, Offer> testData = new ConcurrentHashMap<>();
        var testOfferFacade = new OfferFacadeTestConfiguration().getOfferFacadeForTest(testData);
        //when
        List<OfferResponseDto> allOffers = testOfferFacade.findAllOffers();
        // then
        assertThat(allOffers).isNotNull();
    }

    @Test
    public void should_throw_exception_when_offer_id_not_found() {
        // given
        final Map<String, Offer> testData = new ConcurrentHashMap<>();
        var testOfferFacade = new OfferFacadeTestConfiguration().getOfferFacadeForTest(testData);
        //when
        FindByIdRequestDto requestedId = new FindByIdRequestDto(5L);
        //then
        assertThrows(OfferNotFoundException.class, () -> testOfferFacade
                .findOfferById(requestedId), "Offer not found");
    }

    @Test
    public void should_save_new_offer() {
        final Map<String, Offer> testData = new ConcurrentHashMap<>();
        var testOfferFacade = new OfferFacadeTestConfiguration().getOfferFacadeForTest(testData);
        //given
        OfferRequestDto offerRequestDto = OfferRequestDto.builder().company("TestCompany").title("Title").salary("5000 - 10000 PLN").offerUrl("www.example.com.pl").build();
        //when
        OfferResponseDto offerResponseDto = testOfferFacade.saveOffer(offerRequestDto);

        //then
        assertThat(offerResponseDto).isEqualTo(OfferResponseDto.builder()
                .id(offerResponseDto.id())
                .company(offerResponseDto.company())
                .title(offerResponseDto.title())
                .salary(offerResponseDto.salary())
                .offerUrl("www.example.com.pl")
                .build());
    }

    @Test
    public void should_throw_exception_when_offer_url_already_exists() {
        //given
        final Map<String, Offer> testData = new ConcurrentHashMap<>();
        var testOfferFacade = new OfferFacadeTestConfiguration().getOfferFacadeForTest(testData);
        OfferRequestDto offerRequestDto = OfferRequestDto.builder().company("TestCompany").title("Title").salary("5000 - 10000 PLN").offerUrl("www.example.com.pl").build();
        // when
        testOfferFacade.saveOffer(offerRequestDto);
        //then
        assertThrows(DuplicateKeyException.class,
                () -> testOfferFacade.saveOffer(offerRequestDto), String.format("Offer with offerUrl %s already exists in the database", offerRequestDto.offerUrl())
        );
    }

    @Test
    public void should_save_2_fetched_offers_that_not_exist_in_repository() {
        //given
        final Map<String, Offer> testData = new ConcurrentHashMap<>();
        testData.put("TestUrl1", new Offer(1L, "TestCompany1", "TestTitle1", "TestSalary1", "TestUrl1"));
        testData.put("TestUrl2", new Offer(2L, "TestCompany2", "TestTitle2", "TestSalary2", "TestUrl2"));
        testData.put("TestUrl3", new Offer(3L, "TestCompany3", "TestTitle3", "TestSalary3", "TestUrl3"));
        testData.put("TestUrl4", new Offer(4L, "TestCompany4", "TestTitle4", "TestSalary4", "TestUrl4"));
        testData.put("TestUrl5", new Offer(5L, "TestCompany5", "TestTitle5", "TestSalary5", "TestUrl5"));
        var testOfferFacade = new OfferFacadeTestConfiguration().getOfferFacadeForTest(testData);
        // when
        List<OfferResponseDto> offerResponseDtos = testOfferFacade.fetchRemoteOffersAndSaveIfNotExists();
        //then
        assertThat(offerResponseDtos).containsExactlyInAnyOrder(new OfferResponseDto(6L, "TestCompany6", "TestTitle6", "TestSalary6", "UniqueUrl1"),
                new OfferResponseDto(7L, "TestCompany7", "TestTitle7", "TestSalary7", "UniqueUrl2"));
        assertThat(offerResponseDtos.size()).isEqualTo(2);
    }

    @Test
    public void should_save_4_fetched_offers_that_not_exist_in_repository() {
        //given
        final Map<String, Offer> testData = new ConcurrentHashMap<>();
        testData.put("TestUrl1", new Offer(1L, "TestCompany1", "TestTitle1", "TestSalary1", "TestUrl1"));
        testData.put("TestUrl2", new Offer(2L, "TestCompany2", "TestTitle2", "TestSalary2", "TestUrl2"));
        testData.put("TestUrl3", new Offer(3L, "TestCompany3", "TestTitle3", "TestSalary3", "TestUrl3"));
        var testOfferFacade = new OfferFacadeTestConfiguration().getOfferFacadeForTest(testData);
        // when
        List<OfferResponseDto> offerResponseDtos = testOfferFacade.fetchRemoteOffersAndSaveIfNotExists();
        //then
        assertThat(offerResponseDtos).containsExactlyInAnyOrder(new OfferResponseDto(4L, "TestCompany4", "TestTitle4", "TestSalary4", "TestUrl4"),
                new OfferResponseDto(5L, "TestCompany5", "TestTitle5", "TestSalary5", "TestUrl5"),
                new OfferResponseDto(6L, "TestCompany6", "TestTitle6", "TestSalary6", "UniqueUrl1"),
                new OfferResponseDto(7L, "TestCompany7", "TestTitle7", "TestSalary7", "UniqueUrl2"));
        assertThat(offerResponseDtos.size()).isEqualTo(4);
    }

    @Test
    public void should_save_1_offer_with_numerical_id() {
        //given
        final Map<String, Offer> testData = new ConcurrentHashMap<>();
        var testOfferFacadeWithoutData = new OfferFacadeTestConfiguration().getOfferFacadeForTest(testData);
        OfferRequestDto offerRequestDto = OfferRequestDto.builder()
                .company("TestCompany")
                .title("Title")
                .salary("5000 - 10000 PLN")
                .offerUrl("www.example.com.pl")
                .build();
        //when
        OfferResponseDto offerResponseDto = testOfferFacadeWithoutData.saveOffer(offerRequestDto);

        //then
        assertEquals(offerResponseDto.id(), 1L);

    }

    @Test
    public void should_save_2_offers_with_incrementing_numerical_ids() {
        //given
        final Map<String, Offer> testData = new ConcurrentHashMap<>();
        var testOfferFacadeWithoutData = new OfferFacadeTestConfiguration().getOfferFacadeForTest(testData);
        OfferRequestDto firstOfferRequestDto = OfferRequestDto.builder().company("TestCompany1").title("Title1").salary("5000 - 10000 PLN").offerUrl("www.example1.com.pl").build();
        OfferRequestDto secondOfferRequestDto = OfferRequestDto.builder().company("TestCompany2").title("Title2").salary("5000 - 10000 PLN").offerUrl("www.example2.com.pl").build();
        //when
        OfferResponseDto firstOfferResponseDto = testOfferFacadeWithoutData.saveOffer(firstOfferRequestDto);
        OfferResponseDto secondOfferResponseDto = testOfferFacadeWithoutData.saveOffer(secondOfferRequestDto);

        //then
        assertAll(() -> assertEquals(1L, firstOfferResponseDto.id()), () -> assertEquals(2L, secondOfferResponseDto.id()));

    }
}


