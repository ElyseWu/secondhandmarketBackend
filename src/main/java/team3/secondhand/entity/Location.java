package team3.secondhand.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "loc")
public record Location(
        @Id
        @Field(type = FieldType.Long)
        Long id,

        @GeoPointField
        GeoPoint geoPoint

) {
}
