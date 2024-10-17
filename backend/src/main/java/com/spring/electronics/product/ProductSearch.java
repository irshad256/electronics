package com.spring.electronics.product;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Set;

@Document(indexName = "products")
@Getter
@Setter
@Builder
public class ProductSearch {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String code;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword)
    private Set<String> categoryNames;

    @Field(type = FieldType.Boolean)
    private boolean active;

    @Field(type = FieldType.Text)
    private String imgUrl;

    @Field(type = FieldType.Search_As_You_Type)
    private String nameAutocomplete;
}
