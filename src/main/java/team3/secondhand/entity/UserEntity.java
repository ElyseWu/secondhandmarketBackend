package team3.secondhand.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class UserEntity {
    @Id
    private String username;
    private String password;
    private String location;
    private boolean enabled;

    public UserEntity(){}
    public UserEntity(String username, String password, String location, boolean enabled) {
        this.username = username;
        this.password = password;
        this.location = location;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLocation() {
        return location;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getPassword() {
        return password;
    }
}
