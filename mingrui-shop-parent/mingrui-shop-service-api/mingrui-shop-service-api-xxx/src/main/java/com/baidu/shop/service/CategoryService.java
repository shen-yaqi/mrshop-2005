package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ClassName CategoryService
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/12/22
 * @Version V1.0
 **/
@Api(tags = "商品分类接口") // 声明接口
public interface CategoryService {

    @ApiOperation(value = "通过pid查询商品分类")
    @GetMapping(value = "category/list")
    Result<List<CategoryEntity>> getCategoryByPid(Integer pid);

    @ApiOperation(value = "通过id删除分类")
    @DeleteMapping(value = "/category/delete")
    Result<JsonObject> deleteCategoryById(Integer id);

    //@RequestBody 接收前台传递过来的参数(参数必须得是string类型的字符串[json]) "{}" {}
    @ApiOperation(value = "更新")
    @PutMapping(value = "/category/edit")
    Result<JsonObject> editCategoryById(@RequestBody CategoryEntity categoryEntity);
}
