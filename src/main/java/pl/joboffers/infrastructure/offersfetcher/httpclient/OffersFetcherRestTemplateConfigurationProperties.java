package pl.joboffers.infrastructure.offersfetcher.httpclient;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "joboffers.http.client.config")
public record OffersFetcherRestTemplateConfigurationProperties(String uri,
                                                               int port,
                                                               String service,
                                                               int connectTimeout,
                                                               int readTimeout) {
}
