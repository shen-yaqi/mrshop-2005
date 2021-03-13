package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.entity.SkuEntity;
import com.baidu.shop.entity.SpuDetailEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName GoodsService
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2021/1/5
 * @Version V1.0
 **/
@Api(tags = "商品接口")
public interface GoodsService {

    @ApiOperation(value = "查询spu信息")
    @GetMapping(value = "/goods/getSpuInfo")
    Result<List<SpuDTO>> getSpuInfo(@SpringQueryMap SpuDTO spuDTO);

    @ApiOperation(value = "新增商品")
    @PostMapping(value = "/goods/save")
    Result<JSONObject> saveGoods(@RequestBody SpuDTO spuDTO);

    @ApiOperation(value = "修改商品")
    @PutMapping(value = "/goods/save")
    Result<JSONObject> editGoods(@RequestBody SpuDTO spuDTO);

    @ApiOperation(value = "通过spuId查询spudetail信息")
    @GetMapping(value = "/goods/getSpuDetailBySpuId")
    Result<SpuDetailEntity> getSpuDetailBySpuId(@RequestParam Integer spuId);

    @ApiOperation(value = "通过spuId查询sku信息")
    @GetMapping(value = "/goods/getSkusBySpuId")
    Result<List<SkuDTO>> getSkusBySpuId(@RequestParam Integer spuId);

    @ApiOperation(value = "删除商品")
    @DeleteMapping(value = "/goods/delete")
    Result<JSONObject> deleteGoods(Integer spuId);

    @ApiOperation(value = "通过skuId查询sku信息")
    @GetMapping(value = "/goods/getSkuById")
    Result<SkuEntity> getSkuById(@RequestParam Long skuId);
}
