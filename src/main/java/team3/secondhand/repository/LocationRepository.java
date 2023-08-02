package team3.secondhand.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.Location;

@Repository
public interface LocationRepository extends ElasticsearchRepository<Location, Long>, UserLocationRepository {
}
