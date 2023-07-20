package team3.secondhand.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

// change some file to set up upstream for local branch "user_feature_branch"
@Table("users")
public record UserEntity(
        @Id
        String username,
        @JsonIgnore
        String password,
        String location,
        @JsonIgnore
        Boolean enabled
) {
}
