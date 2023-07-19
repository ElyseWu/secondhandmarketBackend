package team3.secondhand.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.UserEntity;
@Repository
public interface UserRepository extends CrudRepository<UserEntity,String> {
}
