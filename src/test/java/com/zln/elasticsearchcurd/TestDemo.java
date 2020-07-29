package com.zln.elasticsearchcurd;


import com.zln.elasticsearchcurd.es.CrudEs;
import com.zln.elasticsearchcurd.model.Item;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {
    @Autowired
    private CrudEs crudEs;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Test
    public void testCreate(){
        //创建索引 ，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(Item.class);
        // 配置映射 根据Item类中的id 等字段自动完成映射
        elasticsearchTemplate.putMapping(Item.class);
    }
    /**
    * 创建索引
    * */
    @Test
    public void createIndex(){
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L,"first","first","first",234.123,"first"));
        list.add(new Item(2L,"second","second","second",234.123,"second"));
        list.add(new Item(3L,"third","third","third",234.122,"third"));
        list.add(new Item(4L,"fourth","fourth","fourth",234.123,"fourth"));
        crudEs.saveAll(list);
    }
    /**
     * 查看所有索引
     * */
    @Test
    public void findAllByIndex(){
        Iterable<Item> all = crudEs.findAll();
        for (Item item : all){
            System.out.println("Item: "+ item.toString());
        }
    }

    /**
     * select id,name from t_user where name like  xxx orderby id desc limit 0,10
     * GET shopping/employee/_search
     {
     "query": {
     "bool": {
     "must": [
     {"match_all": {}}
     ],
     "filter": {
     "range": {
     "age": {
     "gte": 10,
     * @throws Exception
     */
    @Test
    public void complextFunction(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //过滤
        //queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","title","price"},null));
        //添加查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "first"));
        //排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        //分页
        //queryBuilder.withPageable(PageRequest.of(1,2));
        //查询
        Page<Item> search = crudEs.search(queryBuilder.build());
        System.out.println("total="+search.getTotalElements());
        System.out.println("total="+search.getTotalPages());
        List<Item> list = search.getContent();
        for (Item item : list){
            System.out.println("item = " + item.toString());
        }
    }

    //dsl es查询语言,以json体的方式来写查询条件

    /**
     * select id,name from t_user where name like  xxx orderby id desc limit 0,10
     * GET shopping/employee/_search
     {
     "query": {
     "bool": {
     "must": [
     {"match_all": {}}
     ],
     "filter": {
     "range": {
     "age": {
     "gte": 10,
     * @throws Exception
     */
/*    @Test
    public void testDsl()throws Exception{

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery(); //query bool
        List<QueryBuilder> must = boolQuery.must();
        must.add(QueryBuilders.matchAllQuery());    //query bool must matchall
        List<QueryBuilder> filter = boolQuery.filter();
        filter.add(QueryBuilders.rangeQuery("age").gte(20).lte(40)); // //query bool filter range
        //查询
        builder.withQuery(boolQuery);

        //排序
        builder.withSort(SortBuilders.fieldSort("age").order(SortOrder.ASC)); //升序
        //分页
        builder.withPageable(PageRequest.of(2,10)); //page当前页 size没有显示多少条

        //截取字段
        builder.withSourceFilter(new FetchSourceFilter(new String[]{"id","name","age"},null));

        //封装结果
        NativeSearchQuery query = builder.build();
        Page<Employee> page = employeeRepository.search(query);
        System.out.println(page.getTotalElements());
        Iterator<Employee> iterator = page.iterator();
        while (iterator.hasNext()){
            Employee employee = iterator.next();
            System.out.println(employee);
        }

    }*/



}
