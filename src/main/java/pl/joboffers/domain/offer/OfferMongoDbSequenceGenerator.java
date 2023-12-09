package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@AllArgsConstructor
@Service
class OfferMongoDbSequenceGenerator implements OfferSequenceGenerator {

    private final static String SEQUENCE_FIELD_NAME = "sequenceCurVal";
    private final MongoOperations mongoOperations;

    @Override
    public Long generateSequence(String sequenceName) {
        OfferSequence counter = mongoOperations
                .findAndModify(query(where("_id")
                                .is(sequenceName)),
                        new Update().inc(SEQUENCE_FIELD_NAME, 1),
                        options().returnNew(true).upsert(true),
                        OfferSequence.class);
        return !Objects.isNull(counter) ? counter.sequenceCurVal() : 1L;
    }

}
