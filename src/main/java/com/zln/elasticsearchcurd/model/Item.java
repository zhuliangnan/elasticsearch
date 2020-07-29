package com.zln.elasticsearchcurd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName = "estest",type = "fish")
public class Item {

    @Field(type = FieldType.Long)
    @Id
    private Long id ;
    /**标题*/
    @Field(type = FieldType.text)
    private String title;
    /**分类*/
    @Field(type = FieldType.text)
    private String category;
    /**品牌*/
    @Field(type = FieldType.keyword)
    private String brand;
    /**价格*/
    @Field(type = FieldType.Double)
    private Double price;
    /**图片地址*/
    @Field(type = FieldType.keyword,index = false)
    private String images;

}
