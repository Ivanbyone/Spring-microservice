package smartTutor.microservices.cards_aggregator.module_tests.adapters.mappers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import smartTutor.microservices.cards_aggregator.adapters.common.CardMapped;
import smartTutor.microservices.cards_aggregator.adapters.mapper.CardMapper;
import smartTutor.microservices.cards_aggregator.domain.Card;
import smartTutor.microservices.cards_aggregator.domain.common.ICard;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestCardMapper {
    private static CardMapper mapper;

    @BeforeAll
    public static void init() {
        mapper = new CardMapper();
    }

    @Test
    @DisplayName("Тестирование метода создания документа при помощи объекта Маппера")
    public void testToCardDocument() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Математика");
        list.add("ЕГЭ");

        ICard card = new Card("12345", "Репетитор математика", "Иван",
                "- тестовая карточка\n- test card", 2300, list);

        CardMapped expectedOutput = new CardMapped(card);
        CardMapped result = mapper.toCardDocument(card);

        assertEquals(expectedOutput, result);
    }

    @Test
    @DisplayName("Тест на возврат null значения, если карточка пустая")
    public void testNullCardEntityGiven() {
        ICard card = new Card();

        CardMapped result = mapper.toCardDocument(card);

        assertNull(result);
    }
}
