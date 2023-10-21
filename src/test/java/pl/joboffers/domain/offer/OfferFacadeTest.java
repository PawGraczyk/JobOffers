package pl.joboffers.domain.offer;

import org.junit.jupiter.api.Test;
import pl.joboffers.domain.offer.dto.FindByIdRequestDto;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import pl.joboffers.domain.offersfetcher.OffersFetcherFacade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OfferFacadeTest {

    private final OfferRepository repository = new OfferRepositoryTestImplementation();
    private final OfferFacade testOfferFacadeWithoutPreConfig = new OfferFacade(
            repository,
            new OffersFetcherFacade(
                    new OffersFetcherInOfferFacadeTestImplementation()
            ),
            new OfferService(repository)
    );

    @Test
    public void find_all_offers_should_not_return_null() {
        //given
        //when
        List<OfferResponseDto> allOffers = testOfferFacadeWithoutPreConfig.findAllOffers();
        // then
        assertThat(allOffers).isNotNull();
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
                () -> testOfferFacadeWithoutPreConfig.findOfferById(requestedId),
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
        OfferResponseDto offerResponseDto = testOfferFacadeWithoutPreConfig.saveOffer(offerRequestDto);

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
        testOfferFacadeWithoutPreConfig.saveOffer(offerRequestDto);
        //then
        assertThrows(OfferUniqueConstraintViolationException.class,
                () -> testOfferFacadeWithoutPreConfig.saveOffer(offerRequestDto),
                "Entity already exists in the database");
    }

    @Test
    public void should_save_2_fetched_offers_that_not_exist_in_repository() {
        //given
        final Map<String, Offer> testData = new HashMap<>();
        testData.put("TestUrl1", new Offer(null, "TestCompany1", "TestTitle1", "TestSalary1", "TestUrl1"));
        testData.put("TestUrl2", new Offer(null, "TestCompany2", "TestTitle2", "TestSalary2", "TestUrl2"));
        testData.put("TestUrl3", new Offer(null, "TestCompany3", "TestTitle3", "TestSalary3", "TestUrl3"));
        testData.put("TestUrl4", new Offer(null, "TestCompany4", "TestTitle4", "TestSalary4", "TestUrl4"));
        testData.put("TestUrl5", new Offer(null, "TestCompany5", "TestTitle5", "TestSalary5", "TestUrl5"));
        var offerFacadeForTest = new OfferFacadeTestConfiguration().getOfferFacadeForTest(testData);
        // when
        List<OfferResponseDto> offerResponseDtos = offerFacadeForTest.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(offerResponseDtos).containsExactlyInAnyOrder(
                new OfferResponseDto(null, "TestCompany6", "TestTitle6", "TestSalary6", "UniqueUrl1"),
                new OfferResponseDto(null, "TestCompany7", "TestTitle7", "TestSalary7", "UniqueUrl2")
        );
        assertThat(offerResponseDtos.size()).isEqualTo(2);
    }

    @Test
    public void should_save_4_fetched_offers_that_not_exist_in_repository() {
        //given
        final Map<String, Offer> testData = new HashMap<>();
        testData.put("TestUrl1", new Offer(null, "TestCompany1", "TestTitle1", "TestSalary1", "TestUrl1"));
        testData.put("TestUrl2", new Offer(null, "TestCompany2", "TestTitle2", "TestSalary2", "TestUrl2"));
        testData.put("TestUrl3", new Offer(null, "TestCompany3", "TestTitle3", "TestSalary3", "TestUrl3"));
        var offerFacadeForTest = new OfferFacadeTestConfiguration().getOfferFacadeForTest(testData);
        // when
        List<OfferResponseDto> offerResponseDtos = offerFacadeForTest.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(offerResponseDtos).containsExactlyInAnyOrder(
                new OfferResponseDto(null, "TestCompany4", "TestTitle4", "TestSalary4", "TestUrl4"),
                new OfferResponseDto(null, "TestCompany5", "TestTitle5", "TestSalary5", "TestUrl5"),
                new OfferResponseDto(null, "TestCompany6", "TestTitle6", "TestSalary6", "UniqueUrl1"),
                new OfferResponseDto(null, "TestCompany7", "TestTitle7", "TestSalary7", "UniqueUrl2")
        );
        assertThat(offerResponseDtos.size()).isEqualTo(4);
    }

}


