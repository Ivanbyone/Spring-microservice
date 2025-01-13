package smartTutor.microservices.cards_aggregator.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import smartTutor.microservices.cards_aggregator.adapters.CardsRepository;
import smartTutor.microservices.cards_aggregator.adapters.CardsRepositoryCustom;
import smartTutor.microservices.cards_aggregator.adapters.common.CardMapped;
import smartTutor.microservices.cards_aggregator.adapters.mapper.CardMapper;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.CreateNewCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.DeleteCardByIdDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.UpdateCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.CreatedCardOutput;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.DeletedCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.GetCardByIdDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.UpdateCardOutput;
import smartTutor.microservices.cards_aggregator.application.common.exceptions.NotFoundError;
import smartTutor.microservices.cards_aggregator.application.common.exceptions.ValidationError;
import smartTutor.microservices.cards_aggregator.application.common.services.ICardsService;
import smartTutor.microservices.cards_aggregator.domain.common.ICard;
import smartTutor.microservices.cards_aggregator.domain.common.ICardFactory;
import smartTutor.microservices.cards_aggregator.domain.exceptions.DomainError;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class CardsService implements ICardsService {
    private static final Logger logger = LoggerFactory.getLogger(CardsService.class);
    private final ICardFactory factory;
    private final CardsRepository repository;
    private final CardsRepositoryCustom updateRepo;
    private final CardMapper mapper;

    public CardsService(@Autowired ICardFactory factory, CardsRepository repository, CardMapper mapper,
                        CardsRepositoryCustom updateRepo) {
        this.factory = factory;
        this.repository = repository;
        this.updateRepo = updateRepo;
        this.mapper = mapper;
    }

    @Async
    @Override
    public CompletableFuture<List<GetCardByIdDto>> getAllCardsWithSort() {
        // Получение всех карточек
        List<CardMapped> mappedCards = this.repository.findAll();

        List<GetCardByIdDto> cards = this.mapper.toListCards(mappedCards);

        // Сортировка по приоритету и дате создания
        cards.sort(Comparator.
                comparing(GetCardByIdDto::priority).
                thenComparing(GetCardByIdDto::createdAt).
                reversed());

        return CompletableFuture.completedFuture(cards);
    }

    @Async
    @Override
    public CompletableFuture<GetCardByIdDto> getCardById(String customCardId) throws NotFoundError {
        // Поиск карточки в коллекции
        CardMapped card = this.repository.getCardByCustomCardId(customCardId);

        // Если карточка с указанным айди отсутствует, то выбрасывается исключение
        if (card == null) {
            logger.error("Card wasn't found in collection");
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
    public CompletableFuture<UpdateCardOutput> updateCard(List<UpdateCardDto> dto, String customCardId) {
        Map<String, Object> fieldsValues = new HashMap<>();

        // Преобразование дто в объект типа {field: value}
        for (UpdateCardDto updateCardDto : dto) {
            fieldsValues.put(updateCardDto.field(), updateCardDto.value());
        }

        // Update в базе данных
        this.updateRepo.updateCard(customCardId, fieldsValues);
        return CompletableFuture.completedFuture(new UpdateCardOutput("OK", customCardId));
    }

    @Async
    @Override
    public CompletableFuture<DeletedCardDto> deleteCard(String customCardId, DeleteCardByIdDto userId)
            throws NotFoundError {
        // Возвращает количество удаленных карточек (либо 0, либо 1)
        long deleted = this.repository.deleteByCustomCardIdAndUserId(customCardId, userId.userId());

        // Если 0, то пробрасывается исключение, что карточка не найдена
        if (deleted != 1) {
            logger.error("Card wasn't found");
            throw new NotFoundError("Card wasn't found");
        }

        // Ответ об успешном удалении карточки
        return CompletableFuture.completedFuture(new DeletedCardDto("Deleted", customCardId));
    }
}
