package team3.secondhand.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,String> {
    @Modifying
    @Query("INSERT INTO users VALUES(:username, :password, :location, :enabled)")
    void add(String username, String password, String location, Boolean enabled);

    UserEntity findByUsername(String username);

}
