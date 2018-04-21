package cn.xiaoxige.firstssm.dao;

import cn.xiaoxige.firstssm.entity.UserTest;
import org.apache.ibatis.annotations.Param;

public interface UserTestDao {

    int addUserInfo(@Param("user") UserTest test);

}