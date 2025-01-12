package smartTutor.microservices.cards_aggregator.domain.common;

import smartTutor.microservices.cards_aggregator.application.common.dto.input.CreateNewCardDto;
import smartTutor.microservices.cards_aggregator.domain.exceptions.DomainError;

public interface ICardFactory {
    ICard createNewCard(CreateNewCardDto dto) throws DomainError;
}
