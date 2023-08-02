package team3.secondhand.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.FavoriteItemEntity;

import java.util.List;

@Repository
public interface FavoriteItemRepository extends CrudRepository<FavoriteItemEntity, Long> {
    List<FavoriteItemEntity> getFavoriteItemEntitiesByUsername(String username);

    @Modifying
    @Query("INSERT INTO favorite_items (item_id, username) VALUES(:itemId, :username)")
    void insert(Long itemId, String username);

    @Modifying
    @Query("DELETE FROM favorite_items WHERE item_id = :itemId AND username = :username")
    void deleteByItemIdAndUsername(Long itemId, String username);
}
