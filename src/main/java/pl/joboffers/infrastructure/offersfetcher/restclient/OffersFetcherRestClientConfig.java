package pl.joboffers.infrastructure.offersfetcher.restclient;


import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.joboffers.domain.offersfetcher.OffersFetchable;

import java.time.Duration;

@Configuration
@AllArgsConstructor
public class OffersFetcherRestClientConfig {

    private final OffersFetcherRestTemplateConfigurationProperties properties;
    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler())
                .setConnectTimeout(Duration.ofMillis(properties.connectTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                .build();
    }

    @Bean
    public OffersFetchable offersFetcherRemoteClient(RestTemplate restTemplate) {
        return new OffersFetcherRestClient(restTemplate, properties.uri(), properties.port(), properties.service());
    }
}
