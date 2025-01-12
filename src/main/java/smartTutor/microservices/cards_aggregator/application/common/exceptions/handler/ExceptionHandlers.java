package smartTutor.microservices.cards_aggregator.application.common.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smartTutor.microservices.cards_aggregator.application.common.exceptions.NotFoundError;
import smartTutor.microservices.cards_aggregator.application.common.exceptions.ValidationError;

@RestControllerAdvice
public class ExceptionHandlers {

    @ResponseBody
    @ExceptionHandler(ValidationError.class)
    public ResponseEntity<String> handleInvalidRequest(ValidationError error) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotFoundError.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundError error) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage());
    }
}
