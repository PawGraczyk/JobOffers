package pl.joboffers.infrastructure.offer.controller.error;


import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.joboffers.domain.offer.OfferNotFoundException;
import pl.joboffers.infrastructure.offer.controller.OfferRestController;

@ControllerAdvice(basePackageClasses = OfferRestController.class)
@Log4j2
public class OfferControllerErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public OfferErrorResponse handleOfferByIdNotFound(OfferNotFoundException ex) {
        final String message = ex.getMessage();
        log.error(message);
        return new OfferErrorResponse(message, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public OfferErrorResponse handleDuplicateOffer() {
        final String message = "Offer with given offer url already exists.";
        log.error(message);
        return new OfferErrorResponse(message, HttpStatus.CONFLICT);
    }

}
