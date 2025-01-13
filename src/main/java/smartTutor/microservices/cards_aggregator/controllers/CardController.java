package smartTutor.microservices.cards_aggregator.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.CreateNewCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.DeleteCardByIdDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.input.UpdateCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.GetCardByIdDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.CreatedCardOutput;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.DeletedCardDto;
import smartTutor.microservices.cards_aggregator.application.common.dto.output.UpdateCardOutput;
import smartTutor.microservices.cards_aggregator.application.common.services.ICardsService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {
    private final ICardsService cardsService;

    public CardController(@Autowired ICardsService cardsService) {
        this.cardsService = cardsService;
    }

    @GetMapping
    public CompletableFuture<List<GetCardByIdDto>> getAllCards() {
        return this.cardsService.getAllCardsWithSort();
    }

    @GetMapping("/{id}")
    public CompletableFuture<GetCardByIdDto> getCard(@PathVariable("id") String customCardId) {
        return this.cardsService.getCardById(customCardId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<CreatedCardOutput> createCard(@Valid @RequestBody CreateNewCardDto dto) {
        return this.cardsService.createNewCard(dto);
    }

    @PatchMapping("/{id}")
    public CompletableFuture<UpdateCardOutput> updateCard(@PathVariable("id") String customCardId,
                                                          @RequestBody List<UpdateCardDto> dto) {
        return this.cardsService.updateCard(dto, customCardId);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<DeletedCardDto> deleteCard(@PathVariable("id") String customCardId,
                                                        @RequestBody DeleteCardByIdDto userId) {
        return this.cardsService.deleteCard(customCardId, userId);
    }
}
