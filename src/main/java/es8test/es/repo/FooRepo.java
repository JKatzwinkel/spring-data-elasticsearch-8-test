package es8test.es.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import es8test.es.model.FooEntity;

public interface FooRepo extends ElasticsearchRepository<FooEntity, String> {
    
}
