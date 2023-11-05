package pl.joboffers.infrastructure.offersfetcher.httpclient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.joboffers.domain.offersfetcher.OfferFetchable;

import java.time.Duration;

@Configuration
public class OffersFetcherRestClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler())
                .setConnectTimeout(Duration.ofMillis(1000))
                .setReadTimeout(Duration.ofMillis(1000))
                .build();
    }

    @Bean
    public OfferFetchable offersFetcherRemoteClient(RestTemplate restTemplate,
                                                    @Value("${joboffers.offers-fetcher.http.client.config.uri}") String uri,
                                                    @Value("${joboffers.offers-fetcher.http.client.config.port}") int port,
                                                    @Value("${joboffers.offers-fetcher.http.client.config.service}") String service) {
        return new OffersFetcherRestTemplate(restTemplate, uri, port, service);
    }
}
