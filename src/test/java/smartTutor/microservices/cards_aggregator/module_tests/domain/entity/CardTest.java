package smartTutor.microservices.cards_aggregator.module_tests.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import smartTutor.microservices.cards_aggregator.domain.Card;
import smartTutor.microservices.cards_aggregator.domain.common.ICard;
import smartTutor.microservices.cards_aggregator.domain.exceptions.DomainError;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    @DisplayName("Правильное создание новой карточки из полного набора данных, отправленных юзером")
    @Timeout(1)
    public void rightCreatingCard() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Математика");
        list.add("ЕГЭ");

        ICard card = new Card("12345", "Репетитор математика", "Иван",
                "- тестовая карточка\n- test card", 2300, list);

        // Проверка типов в сущности
        assertInstanceOf(String.class, card.getCustomCardId());
        assertInstanceOf(String.class, card.getUserId());
        assertInstanceOf(Integer.class, card.getPriority());
        assertInstanceOf(String.class, card.getTitle());
        assertInstanceOf(String.class, card.getDescription());
        assertInstanceOf(String.class, card.getFio());
        assertInstanceOf(Integer.class, card.getPricePerHour());
        assertInstanceOf(ArrayList.class, card.getTags());
        assertInstanceOf(LocalDateTime.class, card.getCreatedAt());

        // Проверка правильности записи переданных данных
        assertEquals("12345", card.getUserId());
        assertEquals(10, card.getPriority());
        assertEquals("Репетитор математика", card.getTitle());
        assertEquals("Иван", card.getFio());
        assertEquals("- тестовая карточка\n- test card", card.getDescription());
        assertEquals(2300, card.getPricePerHour());
        assertEquals(list, card.getTags());
    }

    @Test
    @DisplayName("Возможность создания пустой сущности Card")
    @Timeout(1)
    public void createEmptyCard() {
        ICard card = new Card();
        assertTrue(card.isEmpty());
    }

    @Test
    @DisplayName("Создание сущности, если есть разрешенные null значения")
    @Timeout(1)
    public void createCardWithNullValues() {
        ICard card = new Card("12345", "Репетитор математика", "Иван", null,
                2300, null);

        assertNull(card.getTags());
        assertNull(card.getDescription());
    }

    @Test
    @DisplayName("Сущность с неразрешенными null значениями не создается")
    @Timeout(1)
    public void notCreatedCardWithNulls() {
        assertThrows(DomainError.class, () -> new Card(null, "Репетитор математика", "Иван",
                null,2300,null));
        assertThrows(DomainError.class, () -> new Card("12345", null, "Иван",
                null,2300,null));
        assertThrows(DomainError.class, () -> new Card("12345", "Репетитор", null,
                null,2300,null));
        assertThrows(DomainError.class, () -> new Card("12345", "Репетитор", "Иван",
                null,null,null));
    }

    @Test
    @DisplayName("Сущность со всеми передаваемыми null значениями не создается")
    @Timeout(1)
    public void notCreatedCardWithAllNulls() {
        assertThrows(DomainError.class, () -> new Card(null, null, null,
                null,null,null));
    }
}
