package pl.joboffers.infrastructure.offersfetcher.restclient;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "joboffers.http.client.config")
public record OffersFetcherHttpClientConfigurationProperties(String uri,
                                                             int port,
                                                             String service,
                                                             int connectTimeout,
                                                             int readTimeout) {
}
