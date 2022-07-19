package es8test.es.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "foo")
public class FooEntity {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    
    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Text)
    private String text;

}
