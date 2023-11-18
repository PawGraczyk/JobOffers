package pl.joboffers.infrastructure.offer.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;


@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/offers")
public class OfferRestController {

    private final OfferFacade facade;

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> offers() {
        List<OfferResponseDto> allOffers = facade.findAllOffers();
        return ResponseEntity.ok(allOffers);
    }
}
