package pl.joboffers.features;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.SampleHttpResponse;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import pl.joboffers.infrastructure.offer.scheduler.JobOffersScheduler;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioUserWantToGetAndCheckOffersIntegrationTest extends BaseIntegrationTest {

    @Autowired
    OfferFacade offerFacade;
    @Autowired
    SampleHttpResponse httpResponse;
    @Autowired
    JobOffersScheduler jobOffersScheduler;

    @Test
    public void user_has_to_be_authenticated_and_api_should_have_offers() throws Exception {
        //# typical path: user want to see offers but have to be logged in and external server should have some offers
        //step 0: external service returns job offers (http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com:5057/offers)
        //step 1: there are no offers in external HTTP server
        // given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(httpResponse.emptyResponse())

                ));

        // step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        // given & when
        List<OfferResponseDto> addedOffers = jobOffersScheduler.fetchRemoteJobOffers();
        // then
        assertThat(addedOffers).isEmpty();

        // step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        // step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        // step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        // step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        // step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        //given && when && then
        MvcResult performGetResultAllOffers = mockMvc.perform(get("/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = performGetResultAllOffers.getResponse().getContentAsString();
        List<OfferResponseDto> offers = objectMapper.readValue(json, new TypeReference<>() {
        });
        assertThat(offers).isEmpty();

        // step 8: there are 2 new offers in external HTTP server
        // step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        // step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000
        // step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        // given && when
        ResultActions performGetResultWithNotExistingId = mockMvc.perform(get("/offers/9999"));
        // then
        performGetResultWithNotExistingId.andExpect(status().isNotFound())
                .andExpect(content().json(
                        """
                                 {
                                 "message": "Offer not found for id: 9999",
                                 "status": "NOT_FOUND"
                                 }                                   
                                """.trim()
                ));

        // step 12: user made GET /offers/1000 and system returned OK(200) with offer
        // step 13: there are 2 new offers in external HTTP server
        // step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        // step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
        // step 16: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and offer and system returned CREATED(201) with saved offer
        //given && when
        ResultActions performPostNewOffer = mockMvc.perform(post("/offers").
                content(
                        """
                                {
                                "company": "test",
                                "title": "Junior Java Developer",
                                "salary": "5000.00 PLN",
                                "offerUrl": "www.example.com"
                                }
                                """.trim()
                ).contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult = performPostNewOffer.andExpect(status().isCreated()).andReturn();
        String offerJson = mvcResult.getResponse().getContentAsString();
        OfferResponseDto responseDto = objectMapper.readValue(offerJson, OfferResponseDto.class);
        assertAll(
                () -> assertThat(responseDto.id() != null && !responseDto.id().isEmpty()),
                () -> assertThat(responseDto.title().equals("Junior Java Developer")),
                () -> assertThat(responseDto.company().equals("test")),
                () -> assertThat(responseDto.salary().equals("5000.00 PLN")),
                () -> assertThat(responseDto.offerUrl().equals("www.example.com"))
        );
        // step 17: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 1 offer
        //given && when
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(httpResponse.responseWithOneObject())));
        ResultActions performGetWithOneOffer = mockMvc.perform(get("/offers"));

        //then
        MvcResult mvcResultWithOneOffer = performGetWithOneOffer.andExpect(status().isOk()).andReturn();
        String jsonWithOneOffer = mvcResultWithOneOffer.getResponse().getContentAsString();
        List<OfferResponseDto> responseWithOneOffer = objectMapper.readValue(jsonWithOneOffer, new TypeReference<>() {
        });
        assertThat(responseWithOneOffer).hasSize(1);
    }
}
