package smartTutor.microservices.cards_aggregator.application.common.services;

import smartTutor.microservices.cards_aggregator.application.common.dto.input.CreateNewCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.DeleteCardByIdDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.UpdateCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.CreatedCardOutput;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.DeletedCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.GetCardByIdDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.UpdateCardOutput;
import smartTutor.microservices.cards_aggregator.application.common.exceptions.NotFoundError;
import smartTutor.microservices.cards_aggregator.application.common.exceptions.ValidationError;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICardsService {
    CompletableFuture<List<GetCardByIdDto>> getAllCardsWithSort();
    CompletableFuture<GetCardByIdDto> getCardById(String customCardId) throws NotFoundError;
    CompletableFuture<CreatedCardOutput> createNewCard(CreateNewCardDto dto) throws ValidationError;
    CompletableFuture<UpdateCardOutput> updateCard(List<UpdateCardDto> dto, String customCardId);
    CompletableFuture<DeletedCardDto> deleteCard(String customCardId, DeleteCardByIdDto userId) throws NotFoundError;
}
