package pl.joboffers.infrastructure.offer.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import java.util.List;


@RestController
@Log4j2
public class OfferRestController {

    private final OfferFacade facade;

    public OfferRestController(OfferFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/offers")
    public List <OfferResponseDto> offers(){
        return facade.findAllOffers();
    }
}
