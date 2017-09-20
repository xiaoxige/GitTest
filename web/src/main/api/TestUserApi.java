package main.api;

import main.bean.TestUserEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestUserApi {


    int insetTestUser(@Param("userEntity") TestUserEntity entity) throws Exception;


    TestUserEntity getTestUserById(@Param("id") String userEntityId) throws Exception;

}
