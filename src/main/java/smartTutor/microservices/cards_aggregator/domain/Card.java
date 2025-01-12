package smartTutor.microservices.cards_aggregator.domain;

import smartTutor.microservices.cards_aggregator.domain.common.ICard;
import smartTutor.microservices.cards_aggregator.domain.exceptions.DomainError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Card implements ICard {
    private String customCardId;
    private String userId;
    private Integer priority;
    private String title;
    private String fio;
    private String description;
    private Integer pricePerHour;
    private ArrayList<String> tags;
    private LocalDateTime createdAt;

    public Card(String userId, String title, String fio, String description, Integer pricePerHour,
                ArrayList<String> tags) {
        this.customCardId = UUID.randomUUID().toString();
        this.userId = notNullUserId(userId);
        this.priority = 10;
        this.title = notNullTitle(title);
        this.fio = notNullFio(fio);
        this.description = description;
        this.pricePerHour = notNullPricePerHour(pricePerHour);
        this.tags = tags;
        this.createdAt = LocalDateTime.now();
    }

    public Card() { }

    public boolean isEmpty() {
        return this.createdAt == null;
    }

    private String notNullUserId(String userId) throws DomainError {
        if (userId == null) { throw new DomainError("Null userId value"); }
        return userId;
    }

    private String notNullTitle(String title) {
        if (title == null) { throw new DomainError("Null title value"); }
        return title;
    }

    private String notNullFio(String fio) {
        if (fio == null) { throw new DomainError("Null fio value"); }
        return fio;
    }

    private Integer notNullPricePerHour(Integer pricePerHour) {
        if (pricePerHour == null) { throw new DomainError("Null fio value"); }
        return pricePerHour;
    }

    public String getCustomCardId() {
        return customCardId;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public String getFio() {
        return fio;
    }

    public Integer getPricePerHour() {
        return pricePerHour;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
