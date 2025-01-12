package smartTutor.microservices.cards_aggregator.adapters;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import smartTutor.microservices.cards_aggregator.adapters.common.CardMapped;

public interface CardsRepository extends MongoRepository<CardMapped, String> {

    @Query(value = "{ 'customCardId': ?0, 'userId': ?1 }", delete = true)
    long deleteByCustomCardIdAndUserId(String customCardId, String userId);

    @Query(value = "{ 'customCardId': ?0 }")
    CardMapped getCardByCustomCardId(String customCardId);
}
