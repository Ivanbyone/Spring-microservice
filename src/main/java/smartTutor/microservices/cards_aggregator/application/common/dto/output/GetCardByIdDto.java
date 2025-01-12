package smartTutor.microservices.cards_aggregator.application.common.dto.output;

import java.time.Instant;
import java.util.ArrayList;

public record GetCardByIdDto(String customCardId, String userId, Integer priority,
                             String title, String fio, String description, Integer pricePerHour,
                             ArrayList<String> tags, Instant createdAt) {
}
