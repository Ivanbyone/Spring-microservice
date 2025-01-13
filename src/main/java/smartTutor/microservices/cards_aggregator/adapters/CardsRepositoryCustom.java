package smartTutor.microservices.cards_aggregator.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import smartTutor.microservices.cards_aggregator.adapters.common.CardMapped;

import java.util.Map;

@Component
public class CardsRepositoryCustom {

    @Autowired
    private MongoTemplate template;

    public void updateCard(String customCardId, Map<String, Object> fields) {
        Query query = new Query();
        query.addCriteria(Criteria.where("customCardId").is(customCardId));

        Update update = new Update();

        fields.forEach(update::set);

        template.updateFirst(query, update, CardMapped.class);
    }
}
