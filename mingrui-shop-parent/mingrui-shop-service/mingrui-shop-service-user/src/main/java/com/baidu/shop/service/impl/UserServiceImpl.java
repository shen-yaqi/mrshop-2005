package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.UserDTO;
import com.baidu.shop.entity.UserEntity;
import com.baidu.shop.mapper.UserMapper;
import com.baidu.shop.service.UserService;
import com.baidu.shop.utils.BCryptUtil;
import com.baidu.shop.utils.BaiduBeanUtil;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2021/3/10
 * @Version V1.0
 **/
@RestController
public class UserServiceImpl extends BaseApiService implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Result<JSONObject> send(UserDTO userDTO) {
        //验证码
        String code= (int)((Math.random() * 9 + 1) * 100000) + "";
        //手机号
        //发送验证码
        return this.setResultSuccess();
    }

    @Override
    public Result<List<UserEntity>> checkAccountAndPhone(String value, Integer type) {

        Example example = new Example(UserEntity.class);
        /*if(type == 1){
            example.createCriteria().andEqualTo("username",value);
        }else{
            example.createCriteria().andEqualTo("phone",value);
        }*/

        example.createCriteria().andEqualTo(type == 1 ? "username" : "phone",value);
        List<UserEntity> userEntities = userMapper.selectByExample(example);

        return this.setResultSuccess(userEntities);
    }

    @Override
    public Result<JSONObject> register(UserDTO userDTO) {

        UserEntity userEntity = BaiduBeanUtil.copyProperties(userDTO, UserEntity.class);
        userEntity.setPassword(BCryptUtil.hashpw(userEntity.getPassword(),BCryptUtil.gensalt()));
        userEntity.setCreated(new Date());

        userMapper.insertSelective(userEntity);
        return this.setResultSuccess();
    }
}
