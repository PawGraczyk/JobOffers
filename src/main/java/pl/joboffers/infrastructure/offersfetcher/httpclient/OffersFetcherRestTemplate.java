package pl.joboffers.infrastructure.offersfetcher.httpclient;


import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.joboffers.domain.offersfetcher.OfferFetchable;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

import java.util.List;

@AllArgsConstructor
public class OffersFetcherRestTemplate implements OfferFetchable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<RemoteJobOfferDto> fetchOffers() {
        List<RemoteJobOfferDto> remoteJobOfferDtos;
        String urlForService = getUrlForService("/offers");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .toUriString();
        ResponseEntity<List<RemoteJobOfferDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {

                });

        System.out.println(response.getBody());
        remoteJobOfferDtos = response.getBody();
        return remoteJobOfferDtos;
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
