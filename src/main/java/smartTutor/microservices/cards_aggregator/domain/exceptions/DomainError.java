package smartTutor.microservices.cards_aggregator.domain.exceptions;

public class DomainError extends RuntimeException {
    public DomainError(String message) {
        super(message);
    }
}
