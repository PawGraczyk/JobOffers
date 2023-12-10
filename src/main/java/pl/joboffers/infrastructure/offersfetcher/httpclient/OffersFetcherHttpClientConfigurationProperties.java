package pl.joboffers.infrastructure.offersfetcher.httpclient;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "joboffers.http.client.config")
public record OffersFetcherHttpClientConfigurationProperties(String uri, int port, String service, int connectTimeout,
                                                             int readTimeout) {
}
