package com.gary.springboot.mapper;

import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.controller.request.UserPageRequest;
import com.gary.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper//為spring的組件，才能用@Autowired
public interface UserMapper {

//    @Select("select * from user")
    List<User> list();//查詢數據庫所有資料

    List<User> listByCondition(BaseRequest baseRequest);

    void save(User user);

    User getById(Integer id);

    void updateById(User user);

    void deleteById(Integer id);

    User getByUsername(String username);


}
