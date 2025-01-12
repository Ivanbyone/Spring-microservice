package smartTutor.microservices.cards_aggregator.adapters.common;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import smartTutor.microservices.cards_aggregator.domain.common.ICard;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

@Document(collection = "cards")
public class CardMapped {
    @Id
    private String id;

    @Indexed(name = "customCardId_idx", unique = true)
    private String customCardId;

    @Indexed(name = "userId_idx")
    private String userId;

    @Indexed(name = "priority_idx")
    private Integer priority;

    @Indexed(name = "title_idx")
    private String title;

    private String fio;
    private String description;

    @Indexed(name = "pricePerHour_idx")
    private Integer pricePerHour;

    @Indexed(name = "tags_idx")
    private ArrayList<String> tags;

    @Version
    private Integer version;

    @Indexed(name = "createdAt_idx")
    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Transient
    private String _class;

    public CardMapped(ICard entity) {
        this.customCardId = entity.getCustomCardId();
        this.title = entity.getTitle();
        this.userId = entity.getUserId();
        this.description = entity.getDescription();
        this.fio = entity.getFio();
        this.pricePerHour = entity.getPricePerHour();
        this.priority = entity.getPriority();
        this.tags = entity.getTags();
    }

    public CardMapped() { }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        CardMapped o = (CardMapped) obj;

        return Objects.equals(userId, o.userId) &&
               Objects.equals(priority, o.priority) &&
               Objects.equals(title, o.title) &&
               Objects.equals(description, o.description) &&
               Objects.equals(fio, o.fio) &&
               Objects.equals(pricePerHour, o.pricePerHour) &&
               Objects.equals(tags, o.tags);
    }

    public String getCustomCardId() {
        return customCardId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
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

    public Instant getCreatedAt() {
        return createdAt;
    }
}
