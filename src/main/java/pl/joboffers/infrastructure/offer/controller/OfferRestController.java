package pl.joboffers.infrastructure.offer.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.dto.FindByIdRequestDto;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
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

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponseDto> offers(@PathVariable Long id) {
        FindByIdRequestDto requestDto = new FindByIdRequestDto(id);
        OfferResponseDto offer = facade.findOfferById(requestDto);
        return ResponseEntity.ok(offer);

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OfferResponseDto> offers(@RequestBody @Valid OfferRequestDto requestDto) {
        OfferResponseDto responseDto = facade.saveOffer(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
