package team3.secondhand.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;


@Table("items")
public class ItemEntity {
        @Id
        Long id;
        String name;
        Double price;
        String description;
        String condition;
        LocalDate postedDay;
        String category;
        Boolean onSale;

        public ItemEntity(String name, Double price, String description, String condition, LocalDate postedDay, String category, Boolean onSale) {
                this.name = name;
                this.price = price;
                this.description = description;
                this.condition = condition;
                this.postedDay = postedDay;
                this.category = category;
                this.onSale = onSale;
        }

        public Long getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public Double getPrice() {
                return price;
        }

        public String getDescription() {
                return description;
        }

        public String getCondition() {
                return condition;
        }

        public LocalDate getPostedDay() {
                return postedDay;
        }

        public String getCategory() {
                return category;
        }

        public Boolean getOnSale() {
                return onSale;
        }


}
