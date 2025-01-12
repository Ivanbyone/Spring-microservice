package smartTutor.microservices.cards_aggregator.domain.factory;

import org.springframework.stereotype.Component;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.CreateNewCardDto;
import smartTutor.microservices.cards_aggregator.domain.Card;
import smartTutor.microservices.cards_aggregator.domain.common.ICard;
import smartTutor.microservices.cards_aggregator.domain.common.ICardFactory;
import smartTutor.microservices.cards_aggregator.domain.exceptions.DomainError;

@Component
public class CardFactory implements ICardFactory {

    public ICard createNewCard(CreateNewCardDto dto) throws DomainError {
        return new Card(dto.getUserId(),
                        dto.getTitle(),
                        dto.getFio(),
                        dto.getDescription(),
                        dto.getPricePerHour(),
                        dto.getTags());
    }
}
