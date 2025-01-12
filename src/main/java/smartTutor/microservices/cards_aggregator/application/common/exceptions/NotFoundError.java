package smartTutor.microservices.cards_aggregator.application.common.exceptions;

public class NotFoundError extends RuntimeException {
    public NotFoundError(String message) {
        super(message);
    }
}
