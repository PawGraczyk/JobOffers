package pl.joboffers.infrastructure.offersfetcher.httpclient;


import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.joboffers.domain.offersfetcher.OfferFetchable;

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
    public OfferFetchable offersFetcherRemoteClient(RestTemplate restTemplate) {
        return new OffersFetcherRestTemplate(restTemplate, properties.uri(), properties.port(), properties.service());
    }
}
