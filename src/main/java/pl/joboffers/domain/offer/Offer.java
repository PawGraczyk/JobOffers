package pl.joboffers.domain.offer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Document
record Offer(
        @Id()
        String id,
        @Field("company")
        String company,
        @Field("title")
        String title,
        @Field("salary")
        String salary,
        @Field("url")
        @Indexed(unique = true)
        String offerUrl
) {
}
