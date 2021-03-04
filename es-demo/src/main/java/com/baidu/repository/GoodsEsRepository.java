package com.baidu.repository;

import com.baidu.entity.GoodsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface GoodsEsRepository extends ElasticsearchRepository<GoodsEntity,Long> {

    //spring data 提供的功能
    //只要按照一定的规则去写方法名就可以实现
    //JPA 也有类似的操作
    List<GoodsEntity> findAllByTitle(String title);
    List<GoodsEntity> findByPriceBetween(Double startPrice,Double endPrice);
}
