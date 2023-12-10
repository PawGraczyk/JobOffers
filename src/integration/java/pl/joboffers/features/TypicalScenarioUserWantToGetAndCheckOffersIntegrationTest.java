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
import pl.joboffers.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.joboffers.infrastructure.offer.scheduler.JobOffersScheduler;

import java.util.List;
import java.util.regex.Pattern;

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
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value()).withHeader("Content-Type", "application/json").withBody(httpResponse.emptyResponse())

        ));

        // step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        // given & when
        List<OfferResponseDto> addedOffers = jobOffersScheduler.fetchRemoteJobOffers();
        // then
        assertThat(addedOffers).isEmpty();

        // step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        //given && when
        ResultActions failedLoginRequest = mockMvc.perform(post("/token").content("""
                {
                "username": "someUser",
                "password": "somePassword"
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON));

        // then
        failedLoginRequest.andExpect(status().isUnauthorized()).andExpect(content().json("""
                {
                   "message": "Bad Credentials",
                   "status": "UNAUTHORIZED"
                }
                """));


        // step 4: user made GET /offers with no jwt token and system returned FORBIDDEN(403)
        //given && when
        ResultActions getWithoutToken = mockMvc.perform(get("/offers").contentType(MediaType.APPLICATION_JSON));

        //then
        getWithoutToken.andExpect(status().isForbidden());

        // step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status CREATED(200)
        // given && when
        ResultActions performRegistration = mockMvc.perform(post("/register").content("""
                {
                "username": "someUser",
                "password": "somePassword"
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON));
        //then
        performRegistration.andExpect(status().isCreated());

        // step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        // given && then
        ResultActions performGetToken = mockMvc.perform(post("/token").content("""
                {
                "username": "someUser",
                "password": "somePassword"
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResultWithToken = performGetToken.andExpect(status().isOk()).andReturn();
        String jsonWithToken = mvcResultWithToken.getResponse().getContentAsString();
        JwtResponseDto jwtResponseDto = objectMapper.readValue(jsonWithToken, JwtResponseDto.class);
        String token = jwtResponseDto.token();
        assertAll(() -> assertThat(jwtResponseDto.username().equals("someUser")), () -> assertThat(jwtResponseDto.token().matches(String.valueOf(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$")))

        ));
        // step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        //given && when && then
        MvcResult performGetResultAllOffers = mockMvc.perform(get("/offers").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andReturn();
        String json = performGetResultAllOffers.getResponse().getContentAsString();
        List<OfferResponseDto> offers = objectMapper.readValue(json, new TypeReference<>() {
        });
        assertThat(offers).isEmpty();

        // step 8: there are 2 new offers in external HTTP server
        // given && when && then
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value()).withHeader("Content-Type", "application/json").withBody(httpResponse.responseWithTwoObjects())));
        // step 9: Scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1 and 2 to the database
        // given && when
        List<OfferResponseDto> twoNewOffers = jobOffersScheduler.fetchRemoteJobOffers();

        // then
        assertThat(twoNewOffers).hasSize(2);

        // step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids 1 i 2
        //given && when
        MvcResult mvcResultWithTwoOffers = mockMvc.perform(get("/offers").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andReturn();
        String jsonWithTwoOffers = mvcResultWithTwoOffers.getResponse().getContentAsString();
        List<OfferResponseDto> responseWithTwoOffers = objectMapper.readValue(jsonWithTwoOffers, new TypeReference<>() {
        });
        OfferResponseDto expectedFirstOffer = responseWithTwoOffers.get(0);
        OfferResponseDto expectedSecondOffer = responseWithTwoOffers.get(1);

        //then
        assertAll(() -> assertThat(responseWithTwoOffers).hasSize(2), () -> assertThat(responseWithTwoOffers).containsExactlyInAnyOrder(new OfferResponseDto(expectedFirstOffer.id(), expectedFirstOffer.company(), expectedFirstOffer.title(), expectedFirstOffer.salary(), expectedFirstOffer.offerUrl()), new OfferResponseDto(expectedSecondOffer.id(), expectedSecondOffer.company(), expectedSecondOffer.title(), expectedSecondOffer.salary(), expectedSecondOffer.offerUrl())));

        // step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        // given && when
        MvcResult failedResult = mockMvc.perform(get("/offers/9999")).andExpect(status().isNotFound()).andReturn();
        String failedResponse = failedResult.getResponse().getContentAsString();

        // then
        assertThat(failedResponse).isEqualTo("""
                {"message":"Offer not found for id: 9999","status":"NOT_FOUND"}
                """.trim());

        // step 12: user made GET /offers/1 and system returned OK(200) with offer
        //given
        Long offerIdAddedToTheDatabase = expectedFirstOffer.id();
        String offerTitleAddedToTheDatabase = expectedFirstOffer.title();
        String offerCompanyAddedToTheDatabase = expectedFirstOffer.company();
        String offerSalaryAddedToTheDatabase = expectedFirstOffer.salary();
        String offerUrlAddedToTheDatabase = expectedFirstOffer.offerUrl();

        //when
        ResultActions getSingleofferById = mockMvc.perform(get("/offers/" + offerIdAddedToTheDatabase.toString()));

        //then
        String successfulGetResultJson = getSingleofferById.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        OfferResponseDto offerResponseDto = objectMapper.readValue(successfulGetResultJson, OfferResponseDto.class);
        assertAll(() -> assertThat(offerResponseDto.id().equals(offerIdAddedToTheDatabase)), () -> assertThat(offerResponseDto.title().equals(offerTitleAddedToTheDatabase)), () -> assertThat(offerResponseDto.company().equals(offerCompanyAddedToTheDatabase)), () -> assertThat(offerResponseDto.salary().equals(offerSalaryAddedToTheDatabase)), () -> assertThat(offerResponseDto.offerUrl().equals(offerUrlAddedToTheDatabase)));

        // step 13: there are 2 new offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withHeader("Content-Type", "Application/json").withStatus(HttpStatus.OK.value()).withBody(httpResponse.responseWithFourObjects())));


        // step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3 and 4 to database
        // given && when
        List<OfferResponseDto> nextTwoUniqueOffers = jobOffersScheduler.fetchRemoteJobOffers();
        OfferResponseDto firstNewFetchedOffer = nextTwoUniqueOffers.get(0);
        OfferResponseDto secondNewFetchedOffer = nextTwoUniqueOffers.get(1);

        // then
        assertAll(() -> assertThat(nextTwoUniqueOffers).hasSize(2), () -> assertThat(nextTwoUniqueOffers).containsExactlyInAnyOrder(new OfferResponseDto(firstNewFetchedOffer.id(), firstNewFetchedOffer.company(), firstNewFetchedOffer.title(), firstNewFetchedOffer.salary(), firstNewFetchedOffer.offerUrl()), new OfferResponseDto(secondNewFetchedOffer.id(), secondNewFetchedOffer.company(), secondNewFetchedOffer.title(), secondNewFetchedOffer.salary(), secondNewFetchedOffer.offerUrl())));

        // step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1, 2, 3, 4
        // given && when
        ResultActions performWithFourOffers = mockMvc.perform(get("/offers").header("Authorization", "Bearer " + token));

        //then
        String jsonWithFourOffers = performWithFourOffers.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<OfferResponseDto> responseWithFourOffers = objectMapper.readValue(jsonWithFourOffers, new TypeReference<>() {
        });
        assertAll(() -> assertThat(responseWithFourOffers).hasSize(4), () -> assertThat(responseWithFourOffers).contains(new OfferResponseDto(firstNewFetchedOffer.id(), firstNewFetchedOffer.company(), firstNewFetchedOffer.title(), firstNewFetchedOffer.salary(), firstNewFetchedOffer.offerUrl()), new OfferResponseDto(secondNewFetchedOffer.id(), secondNewFetchedOffer.company(), secondNewFetchedOffer.title(), secondNewFetchedOffer.salary(), secondNewFetchedOffer.offerUrl()))

        );
        // step 16: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and offer and system returned CREATED(201) with saved offer
        //given && when
        ResultActions performPostNewOffer = mockMvc.perform(post("/offers").content("""
                {
                "company": "test",
                "title": "Junior Java Developer",
                "salary": "5000.00 PLN",
                "offerUrl": "www.example.com"
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token));

        //then
        MvcResult mvcResult = performPostNewOffer.andExpect(status().isCreated()).andReturn();
        String offerJson = mvcResult.getResponse().getContentAsString();
        OfferResponseDto responseDto = objectMapper.readValue(offerJson, OfferResponseDto.class);
        assertAll(() -> assertThat(responseDto.id() != null), () -> assertThat(responseDto.title().equals("Junior Java Developer")), () -> assertThat(responseDto.company().equals("test")), () -> assertThat(responseDto.salary().equals("5000.00 PLN")), () -> assertThat(responseDto.offerUrl().equals("www.example.com")));
        // step 17: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 5 offers

        //given && when
        ResultActions performGetWithOneOffer = mockMvc.perform(get("/offers").header("Authorization", "Bearer " + token));

        //then
        MvcResult mvcResultWithFiveOffers = performGetWithOneOffer.andExpect(status().isOk()).andReturn();
        String jsonWithOneOffer = mvcResultWithFiveOffers.getResponse().getContentAsString();
        List<OfferResponseDto> responseWithFiveOffers = objectMapper.readValue(jsonWithOneOffer, new TypeReference<>() {
        });
        assertThat(responseWithFiveOffers).hasSize(5);
    }
}
