package pl.joboffers.domain.offer;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Getter
@Document
class Offer {
    @Transient
    public static final String SEQUENCE_NAME = "offers_database_sequence";
    @Id
    private Long id;
    @Field("company")
    private String company;
    @Field("title")
    private String title;
    @Field("salary")
    private String salary;
    @Field("url")
    @Indexed(name = "offer_url_idx", unique = true)
    private String offerUrl;

    public void setId(Long id) {
        this.id = id;
    }
}