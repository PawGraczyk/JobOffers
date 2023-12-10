package pl.joboffers.http.offersfetcher;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.joboffers.domain.offersfetcher.OffersFetchable;

import static com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OffersFetcherHttpClientErrorsIntegrationTest {

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();
    OffersFetchable httpClientForTest = new OffersFetcherHttpClientIntegrationTestConfig().offersFetcherHttpClient(wireMockServer.getPort(), 1000, 1000);


    @Test
    void should_throw_500exception_when_fault_connection_reset_by_peer() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(SC_OK).withHeader("Content-Type", "Application/json").withFault(Fault.CONNECTION_RESET_BY_PEER)));

        //when
        Throwable throwable = catchThrowable(() -> httpClientForTest.fetchOffers());


        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");

    }

    @Test
    void should_throw_500exception_when_response_delay_is_5000ms_and_client_has_1500ms_read_timeout() {
        //given
        final int readTimeout = 1500;
        final int connectionTimeout = 2000;
        final int serverResponseTime = 5000;
        OffersFetchable httpClientForTest = new OffersFetcherHttpClientIntegrationTestConfig().offersFetcherHttpClient(wireMockServer.getPort(), connectionTimeout, readTimeout);
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(SC_OK).withHeader("Content-type", "application/json").withBody("""
                [{"title":"Junior Java Developer","company":"BlueSoft Sp. z o.o.","salary":"7 000 - 9 000 PLN","offerUrl":"https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"},
                {"title":"Java (CMS) Developer","company":"Efigence SA","salary":"16 000 - 18 000 PLN","offerUrl":"https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"}]""".trim()).withFixedDelay(serverResponseTime)));
        //when
        Throwable throwable = catchThrowable(httpClientForTest::fetchOffers);

        // then
        assertAll(() -> assertThat(throwable).isInstanceOf(ResponseStatusException.class), () -> assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR"));
    }

    @Test
    void should_throw_204exception_when_status_204_no_content() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(SC_NO_CONTENT).withHeader("Content-Type", "Application/json").withBody("""
                [{"title":"Junior Java Developer","company":"BlueSoft Sp. z o.o.","salary":"7 000 - 9 000 PLN","offerUrl":"https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"},
                {"title":"Java (CMS) Developer","company":"Efigence SA","salary":"16 000 - 18 000 PLN","offerUrl":"https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"}]""".trim())));

        //when
        Throwable throwable = catchThrowable(() -> httpClientForTest.fetchOffers());

        //then
        assertAll(() -> assertThat(throwable).isInstanceOf(ResponseStatusException.class), () -> assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT"));

    }

    @Test
    void should_throw_401exception_when_401_unauthorized() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(SC_UNAUTHORIZED).withHeader("Content-Type", "Application/json")));

        //when
        Throwable throwable = catchThrowable(() -> httpClientForTest.fetchOffers());

        //then
        assertAll(() -> assertThat(throwable).isInstanceOf(ResponseStatusException.class), () -> assertThat(throwable.getMessage()).isEqualTo("401 UNAUTHORIZED"));
    }

    @Test
    void should_throw_500exception_when_fault_malformed_chunk() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(SC_OK).withHeader("Content-Type", "Application/json").withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        //when
        Throwable throwable = catchThrowable(() -> httpClientForTest.fetchOffers());

        // then
        assertAll(() -> assertThat(throwable).isInstanceOf(ResponseStatusException.class), () -> assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR"));
    }

    @Test
    void should_throw_500exception_when_random_data_then_close() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(SC_OK).withHeader("Content-Type", "Application/json").withFault(Fault.RANDOM_DATA_THEN_CLOSE)));
        //when
        Throwable throwable = catchThrowable(() -> httpClientForTest.fetchOffers());

        // then
        assertAll(() -> assertThat(throwable).isInstanceOf(ResponseStatusException.class), () -> assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR"));
    }


}



