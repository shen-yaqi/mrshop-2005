package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.UserDTO;
import com.baidu.shop.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName UserService
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2021/3/10
 * @Version V1.0
 **/
@Api(tags = "用户接口")
public interface UserService {

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "user/register")
    Result<JSONObject> register(UserDTO userDTO);

    @ApiOperation(value = "给手机发送验证码")
    @PostMapping(value = "user/send")
    Result<JSONObject> send(@RequestBody UserDTO userDTO);

    @ApiOperation(value = "验证用户名或者手机号")
    @GetMapping(value = "user/check/{value}/{type}")
    Result<List<UserEntity>> checkAccountAndPhone(@PathVariable(value = "value") String value
            , @PathVariable(value = "type") Integer type);
}
