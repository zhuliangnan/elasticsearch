package com.zln.elasticsearchcurd.es;

import com.zln.elasticsearchcurd.model.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CrudEs extends ElasticsearchRepository<Item,Long> {
}
