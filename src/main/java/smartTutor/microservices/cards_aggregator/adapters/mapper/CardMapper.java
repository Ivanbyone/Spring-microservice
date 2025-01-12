package smartTutor.microservices.cards_aggregator.adapters.mapper;

import org.springframework.stereotype.Component;
import smartTutor.microservices.cards_aggregator.adapters.common.CardMapped;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.GetCardByIdDto;
import smartTutor.microservices.cards_aggregator.domain.common.ICard;

@Component
public class CardMapper {

    public CardMapped toCardDocument(ICard card) {
        if (card == null || card.isEmpty()) {
            return null;
        }

        return new CardMapped(card);
    }

    public GetCardByIdDto toCardDto(CardMapped doc) {
        return new GetCardByIdDto(
                doc.getCustomCardId(),
                doc.getUserId(),
                doc.getPriority(),
                doc.getTitle(),
                doc.getFio(),
                doc.getDescription(),
                doc.getPricePerHour(),
                doc.getTags(),
                doc.getCreatedAt());
    }
}
