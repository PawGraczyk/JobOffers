package pl.joboffers.http.offersfetcher;

import org.springframework.web.client.RestTemplate;
import pl.joboffers.domain.offersfetcher.OffersFetchable;
import pl.joboffers.infrastructure.offersfetcher.httpclient.OffersFetcherHttpClientConfig;
import pl.joboffers.infrastructure.offersfetcher.httpclient.OffersFetcherHttpClientConfigurationProperties;

public class OffersFetcherHttpClientIntegrationTestConfig extends OffersFetcherHttpClientConfig {

    public OffersFetchable offersFetcherHttpClient(int port, int connectionTimeout, int readTimeout) {
        final String uri = "http://localhost";
        final String service = "/offers";
        var properties = OffersFetcherHttpClientConfigurationProperties.builder()
                .connectTimeout(connectionTimeout)
                .readTimeout(readTimeout)
                .uri(uri)
                .service(service)
                .port(port)
                .build();
        RestTemplate restTemplate = restTemplate(properties);
        return offersFetcherHttpClient(restTemplate, properties);

    }

}
