package team3.secondhand.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemBody(
        String name,
        Double price,
        String description,
        String condition,
        @JsonProperty("posted_day") String postedDay,
        String category,
        @JsonProperty("on_sale") Boolean onSale
        ) {
}
