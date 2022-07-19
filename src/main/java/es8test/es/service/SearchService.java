package es8test.es.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import es8test.es.model.FooEntity;

@Service
public class SearchService {
    
    @Autowired
    private ElasticsearchOperations operations;


    public SearchHits<FooEntity> search(String searchterms) {
        SearchHits<FooEntity> results = operations.search(
            NativeQuery.builder().withQuery(
                q -> q.match(
                    m -> m.field("text").query(searchterms)
                )
            ).build(),
            FooEntity.class
        );
        return results;
    }


    public List<FooEntity> searchResults(String searchterms) {
        SearchHits<FooEntity> results = search(searchterms);
        return results.getSearchHits().stream().map(
            hit -> hit.getContent()
        ).toList();
    }

}
