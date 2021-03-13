package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.config.JwtConfig;
import com.baidu.shop.dto.Car;
import com.baidu.shop.dto.UserInfo;
import com.baidu.shop.entity.SkuEntity;
import com.baidu.shop.feign.GoodsFeign;
import com.baidu.shop.redis.repository.RedisRepository;
import com.baidu.shop.service.CarService;
import com.baidu.shop.utils.JSONUtil;
import com.baidu.shop.utils.JwtUtils;
import com.baidu.shop.utils.ObjectUtil;
import com.baidu.shop.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CarServiceImpl
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2021/3/13
 * @Version V1.0
 **/
@RestController
@Slf4j
public class CarServiceImpl extends BaseApiService implements CarService {

    private final String GOODS_CAR_PRE = "goods-car-";

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private GoodsFeign goodsFeign;

    @Override
    public Result<JSONObject> addCar(Car car,String token) {
        //redis Hash Map<userId, Map<skuId, goods>> map = new HashMap<>();
        //获取当前登录用户信息
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());
            //通过用户id和skuId查询redis数据
            Car redisCar = redisRepository.getHash(GOODS_CAR_PRE + userInfo.getId(), car.getSkuId() + "", Car.class);
            log.info("通过用户id : {} ,skuId : {} 从redis中获取到的数据为 : {}",userInfo.getId(),car.getSkuId(),redisCar);
            if(ObjectUtil.isNotNull(redisCar)){
                //如果能查询出来数据 : num + num
                redisCar.setNum(redisCar.getNum() + car.getNum());
                redisRepository.setHash(GOODS_CAR_PRE + userInfo.getId(),car.getSkuId() + "", JSONUtil.toJsonString(redisCar));
                log.info("redis中有当前商品 , 重新设置redis中该商品的数量 : {}",redisCar.getNum());
            }else{

                Result<SkuEntity> skuResult = goodsFeign.getSkuById(car.getSkuId());
                if (skuResult.isSuccess()) {
                    //如果不能查询出来数据
                    //通过skuId查询sku信息
                    //往redis中新增一个商品
                    SkuEntity skuEntity = skuResult.getData();
                    car.setTitle(skuEntity.getTitle());
                    car.setImage(StringUtils.isEmpty(skuEntity.getImages()) ? "" : skuEntity.getImages().split(",")[0]);
                    car.setPrice(skuEntity.getPrice().longValue());
                    //skuEntity.getOwnSpec() 转换成map --> 遍历map --> 通过key查询spec_param表中的name值 --> 重新拼一个map集合 --> 将map集合转string字符串
                    car.setOwnSpec(skuEntity.getOwnSpec());

                    redisRepository.setHash(GOODS_CAR_PRE + userInfo.getId(),car.getSkuId() + "",JSONUtil.toJsonString(car));
                    log.info("redis中没有当前商品 , 新增商品到购物车中 userId : {} , skuId : {} ,商品数据 : {}",userInfo.getId(),car.getSkuId(),JSONUtil.toJsonString(car));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.setResultSuccess();
    }
}
