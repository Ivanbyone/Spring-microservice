package smartTutor.microservices.cards_aggregator.module_tests.domain.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import smartTutor.microservices.cards_aggregator.application.common.dto.input.CreateNewCardDto;
import smartTutor.microservices.cards_aggregator.domain.common.ICard;
import smartTutor.microservices.cards_aggregator.domain.common.ICardFactory;
import smartTutor.microservices.cards_aggregator.domain.exceptions.DomainError;
import smartTutor.microservices.cards_aggregator.domain.factory.CardFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestCardFactory {
    private static CreateNewCardDto dto;
    private static CreateNewCardDto invalidDto;
    private ICardFactory factory;

    @BeforeAll
    public static void setupTestCases() {
        dto = new CreateNewCardDto();
        dto.setUserId("123456");
        dto.setTitle("Репетитор по математике");
        dto.setFio("Иван");
        dto.setDescription("описание");
        dto.setPricePerHour(3200);

        invalidDto = new CreateNewCardDto();
        invalidDto.setUserId(null);
        invalidDto.setTitle("Репетитор по математике");
        invalidDto.setFio("Иван");
        invalidDto.setDescription("описание");
        invalidDto.setPricePerHour(3200);
    }

    @BeforeEach
    public void setupFactory() {
        this.factory = new CardFactory();
    }

    @Test
    @DisplayName("Создание сущности фабрикой")
    public void factoryCreateCard() {
        ICard card = factory.createNewCard(dto);

        // Карточка не пустая
        assertNotNull(card);

        // Проверка правильной записи данных (в данном тест кейсе tags равняется null)
        assertEquals("123456", card.getUserId());
        assertEquals("Репетитор по математике", card.getTitle());
        assertEquals("Иван", card.getFio());
        assertEquals("описание", card.getDescription());
        assertEquals(3200, card.getPricePerHour());
    }

    @Test
    @DisplayName("Проверка на вывод ошибки при создании сущности c невалидным полем userId")
    public void factoryNotCreateCard() {
        assertThrows(DomainError.class, () -> factory.createNewCard(invalidDto));
    }
}
