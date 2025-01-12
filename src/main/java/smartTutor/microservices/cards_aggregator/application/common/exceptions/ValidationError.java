package smartTutor.microservices.cards_aggregator.application.common.exceptions;

public class ValidationError extends RuntimeException {
    public ValidationError(String message) {
        super(message);
    }
}
