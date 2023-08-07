package team3.secondhand.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import team3.secondhand.entity.DescriptionEntity;

import java.util.List;

// use this elastic repository to help us do keywords search
// when user enter keyword in frontend and click search, searchService will pass keyword to this repository
// and call this repo's method: findByDescriptionContaining
// -> and it will return list of valid DescriptionEntity of corresponding item
// which its description has our search keywords

public interface DescriptionRepository extends ElasticsearchRepository<DescriptionEntity, Long> {

    List<DescriptionEntity> findByDescriptionContaining(String keyword);
}
