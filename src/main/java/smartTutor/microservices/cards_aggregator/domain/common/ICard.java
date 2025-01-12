package smartTutor.microservices.cards_aggregator.domain.common;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ICard {
    String getCustomCardId();
    String getUserId();
    Integer getPriority();
    String getTitle();
    String getDescription();
    String getFio();
    Integer getPricePerHour();
    ArrayList<String> getTags();
    LocalDateTime getCreatedAt();
    boolean isEmpty();
}
