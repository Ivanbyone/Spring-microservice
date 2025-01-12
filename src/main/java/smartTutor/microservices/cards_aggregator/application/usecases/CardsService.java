package smartTutor.microservices.cards_aggregator.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import smartTutor.microservices.cards_aggregator.adapters.CardsRepository;
import smartTutor.microservices.cards_aggregator.adapters.common.CardMapped;
import smartTutor.microservices.cards_aggregator.adapters.mapper.CardMapper;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.CreateNewCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.DeleteCardByIdDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.CreatedCardOutput;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.DeletedCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.GetCardByIdDto;
import smartTutor.microservices.cards_aggregator.application.common.exceptions.NotFoundError;
import smartTutor.microservices.cards_aggregator.application.common.exceptions.ValidationError;
import smartTutor.microservices.cards_aggregator.application.common.services.ICardsService;
import smartTutor.microservices.cards_aggregator.domain.common.ICard;
import smartTutor.microservices.cards_aggregator.domain.common.ICardFactory;
import smartTutor.microservices.cards_aggregator.domain.exceptions.DomainError;

import java.util.concurrent.CompletableFuture;

@Service
public class CardsService implements ICardsService {
    private static final Logger logger = LoggerFactory.getLogger(CardsService.class);
    private final ICardFactory factory;
    private final CardsRepository repository;
    private final CardMapper mapper;

    public CardsService(@Autowired ICardFactory factory, CardsRepository repository, CardMapper mapper) {
        this.factory = factory;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Async
    @Override
    public CompletableFuture<GetCardByIdDto> getCardById(String customCardId) throws NotFoundError {
        logger.info("Thread: {}", Thread.currentThread().getName());

        // Поиск карточки в коллекции
        CardMapped card = this.repository.getCardByCustomCardId(customCardId);

        // Если карточка с указанным айди отсутствует, то выбрасывается исключение
        if (card == null) {
            throw new NotFoundError("Card was not found");
        }

        // Вывод существующей карточки
        return CompletableFuture.completedFuture(this.mapper.toCardDto(card));
    }

    /**
     * UseCase создания новой карточки репетитора.
     * @param dto - CreateNewCardDto (Отправленные пользователем данные для создания карточки).
     * @return Если создание карточки успешно, то {message: "OK"}, иначе - пробрасывается ошибка.
     */
    @Async
    @Override
    public CompletableFuture<CreatedCardOutput> createNewCard(CreateNewCardDto dto) throws ValidationError {
        ICard card;

        // Создание новой карточки при помощи фабрики сущностей
        logger.info("createNewCard Thread: {}", Thread.currentThread().getName());
        try {
            card = this.factory.createNewCard(dto);
        } catch (DomainError error) {
            throw new ValidationError(error.getMessage());
        }

        // Маппинг карточки репетитора в Mongo документ
        CardMapped mapped = this.mapper.toCardDocument(card);

        // Сохранение сущности в БД
        this.repository.save(mapped);

        // Возврат сообщения об успешном создании карточки
        return CompletableFuture.completedFuture(new CreatedCardOutput("OK"));
    }

    @Async
    @Override
    public String updateCard() {
        return "OK";
    }

    @Async
    @Override
    public CompletableFuture<DeletedCardDto> deleteCard(String customCardId, DeleteCardByIdDto userId)
            throws NotFoundError {
        logger.info("Current Thread: {}", Thread.currentThread().getName());

        // Возвращает количество удаленных карточек (либо 0, либо 1)
        long deleted = this.repository.deleteByCustomCardIdAndUserId(customCardId, userId.userId());

        // Если 0, то пробрасывается исключение, что карточка не найдена
        if (deleted != 1) {
            throw new NotFoundError("Bad request");
        }

        // Ответ об успешном удалении карточки
        return CompletableFuture.completedFuture(new DeletedCardDto("Deleted", customCardId));
    }
}