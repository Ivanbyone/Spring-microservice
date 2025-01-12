package smartTutor.microservices.cards_aggregator.application.common.dto.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;

public class CreateNewCardDto {
    @NotBlank
    private String userId;

    @NotBlank
    private String title;

    @NotBlank
    private String fio;

    private String description;

    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer pricePerHour;

    private ArrayList<String> tags;

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getFio() {
        return fio;
    }

    public Integer getPricePerHour() {
        return pricePerHour;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setPricePerHour(Integer pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
