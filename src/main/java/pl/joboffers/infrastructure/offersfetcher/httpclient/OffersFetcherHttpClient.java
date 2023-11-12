package pl.joboffers.infrastructure.offersfetcher.httpclient;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.joboffers.domain.offersfetcher.OffersFetchable;
import pl.joboffers.domain.offersfetcher.dto.RemoteJobOfferDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@AllArgsConstructor
@Log4j2
public class OffersFetcherHttpClient implements OffersFetchable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;
    private final String service;


    @Override
    public List<RemoteJobOfferDto> fetchOffers() {
        log.info(logFetchInfo.FETCH_STARTED);
        String urlForService = getUrlForService();
        HttpHeaders httpHeaders = prepareHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .toUriString();
        try {
            return makeRequest(url, requestEntity);
        } catch (ResourceAccessException e) {
            log.error(logFetchInfo.ERROR_WHILE_FETCHING);
            return Collections.emptyList();
        }
    }

    private List<RemoteJobOfferDto> makeRequest(String url, HttpEntity<HttpHeaders> requestEntity) {
        List<RemoteJobOfferDto> remoteJobOfferDtos;
        ResponseEntity<List<RemoteJobOfferDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        remoteJobOfferDtos = response.getBody();
        if (remoteJobOfferDtos != null && remoteJobOfferDtos.isEmpty()) {
            log.info(logFetchInfo.FETCHED_EMPTY_BODY);
            return Collections.emptyList();
        }
        log.info(logFetchInfo.FETCHED_SUCCESSFULLY.toString(), remoteJobOfferDtos.size());
        return remoteJobOfferDtos;
    }

    private HttpHeaders prepareHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }


    private String getUrlForService() {
        return uri + ":" + port + service;
    }

    private enum logFetchInfo {
        FETCH_STARTED("Fetching data started."),
        FETCHED_EMPTY_BODY("Response is empty. No data fetched."),
        FETCHED_SUCCESSFULLY("Data fetched successfully. Fetched {} objects."),
        ERROR_WHILE_FETCHING("An error occurred while fetching!");

        private final String description;

        logFetchInfo(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

}
