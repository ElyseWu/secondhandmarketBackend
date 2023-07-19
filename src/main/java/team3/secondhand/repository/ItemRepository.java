package team3.secondhand.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.ItemEntity;

import java.time.LocalDate;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {
    ItemEntity getItemEntityById(Long itemId);

}