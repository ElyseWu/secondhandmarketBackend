package team3.secondhand.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "descriptionindex" ,createIndex = false)
public record DescriptionEntity(
        @Id
        @Field(type = FieldType.Long)
        Long id,

        @Field(type = FieldType.Text)
        String description
) {
}
