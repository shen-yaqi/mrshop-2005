package com.baidu.test;

import com.baidu.RunTestEsApplication;
import com.baidu.entity.GoodsEntity;
import com.baidu.repository.GoodsEsRepository;
import com.baidu.utils.HighlightUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName TestES
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2021/3/3
 * @Version V1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RunTestEsApplication.class})
public class TestES {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private GoodsEsRepository goodsEsRepository;

    @Test
    public void testAggs(){

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.addAggregation(
                AggregationBuilders.terms("agg_brand")
                        .field("brand")
                        .subAggregation(
                                AggregationBuilders.max("max_price")
                                        .field("price")
                        )
        );

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), GoodsEntity.class);
        Aggregations aggregations = search.getAggregations();

        //Terms aaa = aggregations.get("aaa");
        //用 terms的好处是不需要知道key的类型

        //抽象工厂!!!
        Terms agg_brand = aggregations.get("agg_brand");
        List<? extends Terms.Bucket> buckets = agg_brand.getBuckets();

        buckets.stream().forEach(bucket -> {
            System.out.println(bucket.getKeyAsString() + " : " + bucket.getDocCount());

            Aggregations aggregations1 = bucket.getAggregations();

            Map<String, Aggregation> stringAggregationMap = aggregations1.asMap();
            stringAggregationMap.forEach((key,value) -> {
                Max v = (Max)value;//大类型转小类型 --> 强转
                System.out.println(key + " : " + v.getValue());
            });

          /*  Max max_price = aggregations1.get("max_price");
            System.out.println("最大的价格为:" + max_price.getValue());*/

        });

        System.out.println(search);
    }


    @Test
    public void firstCharUpper(){

        String str = "title";
        char[] chars = str.toCharArray();
        //chars[0] = chars[0] - 32;
        chars[0] -= 32;
        System.out.println(String.valueOf(chars));
    }

    @Test
    public void highlightSearch(){

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //条件查询

        nativeSearchQueryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title","华为手机"))
                        .must(QueryBuilders.matchQuery("brand","华为"))
                        .must(QueryBuilders.rangeQuery("price").gte(3000D).lte(4000D))
        );

        /*HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field field = new HighlightBuilder.Field("title");
        field.preTags("<span style='color:red'>");
        field.postTags("</span>");
        highlightBuilder.field(field);*/

        //创建HighlightBuilder
        /*HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");//设置需要高亮的字段
        highlightBuilder.preTags("<span style='color:red'>");//前置标签
        highlightBuilder.postTags("</span>");*///后置标签
        //设置高亮字段
        nativeSearchQueryBuilder.withHighlightBuilder(HighlightUtil.getHighlightBuilder("title", "brand"));

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), GoodsEntity.class);

        List<SearchHit<GoodsEntity>> searchHits = search.getSearchHits();
        List<GoodsEntity> goodsList = HighlightUtil.getHighlightList(searchHits);

        System.out.println(goodsList);

    }

    @Test
    public void customSearch(){

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //条件查询
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title","华为手机"));
        nativeSearchQueryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title","华为手机"))
                        .must(QueryBuilders.matchQuery("category","手机"))
                        .must(QueryBuilders.rangeQuery("price").gte(3000D).lte(4000D))
                        .mustNot(QueryBuilders.matchQuery("brand","三星"))
        );
        //分页 起始页是从0开始的 当前页 - 1
        //nativeSearchQueryBuilder.withPageable(PageRequest.of(0,2)); //4,2,3
        //排序
        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));

        //NativeSearchQuery query = nativeSearchQueryBuilder.build();
        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), GoodsEntity.class);

        List<SearchHit<GoodsEntity>> searchHits = search.getSearchHits();
        searchHits.stream().forEach(searchHit -> {
            System.out.println(searchHit.getContent());
        });
        //System.out.println(search);
    }

    @Test
    public void conditionSearch(){

        //List<GoodsEntity> huawei = goodsEsRepository.findAllByTitle("华为");
        List<GoodsEntity> byPriceBetween = goodsEsRepository.findByPriceBetween(3000D, 5000D);
        System.out.println(byPriceBetween);
        //System.out.println(huawei);
    }

    @Test
    public void findAll(){
        Iterable<GoodsEntity> all = goodsEsRepository.findAll();

        all.forEach(goods -> {
            System.out.println(goods);
        });
        /*while(all.iterator().hasNext()){
            System.out.println(all.iterator().next());
        }*/
        //System.out.println(all);
    }

    @Test
    public void saveGoods(){

        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setId(1L);
        goodsEntity.setBrand("小米");
        goodsEntity.setCategory("手机");
        goodsEntity.setImages("xiaomi2.jpg");
        goodsEntity.setPrice(1000D);
        goodsEntity.setTitle("小米3");

        goodsEsRepository.save(goodsEntity);
        System.out.println("新增成功");
    }

    @Test
    public void saveAllData(){

        GoodsEntity entity = new GoodsEntity();
        entity.setId(2L);
        entity.setBrand("苹果");
        entity.setCategory("手机");
        entity.setImages("pingguo.jpg");
        entity.setPrice(5000D);
        entity.setTitle("iphone11手机");

        GoodsEntity entity2 = new GoodsEntity();
        entity2.setId(3L);
        entity2.setBrand("三星");
        entity2.setCategory("手机");
        entity2.setImages("sanxing.jpg");
        entity2.setPrice(3000D);
        entity2.setTitle("w2019手机");

        GoodsEntity entity3 = new GoodsEntity();
        entity3.setId(4L);
        entity3.setBrand("华为");
        entity3.setCategory("手机");
        entity3.setImages("huawei.jpg");
        entity3.setPrice(4000D);
        entity3.setTitle("华为mate30手机");

        goodsEsRepository.saveAll(Arrays.asList(entity,entity2,entity3));

        System.out.println("批量新增成功");
    }

    @Test
    public void deleteGoods(){

        //goodsEsRepository.deleteById(1L);

        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setId(1L);
        goodsEsRepository.delete(goodsEntity);

    }



    // 创建索引
    @Test
    public void createIndex(){

        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(GoodsEntity.class);
        indexOperations.create();

        System.out.println(indexOperations.exists() ? "索引创建成功" : "索引创建失败");
    }

    @Test
    public void createMapping(){

        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(GoodsEntity.class);
//        indexOperations.create();
//        indexOperations.createMapping();//创建映射,不调用此函数也可以创建映射,这就是高版本的强大之处
        System.out.println("映射创建成功" + indexOperations.getMapping());
    }

    @Test
    public void deleteIndex(){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(GoodsEntity.class);
        indexOperations.delete();
        System.out.println("索引删除成功" + indexOperations.exists());
    }

}
