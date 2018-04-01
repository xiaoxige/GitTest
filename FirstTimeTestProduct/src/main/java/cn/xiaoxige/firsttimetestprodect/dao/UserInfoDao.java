package cn.xiaoxige.firsttimetestprodect.dao;


import cn.xiaoxige.firsttimetestprodect.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户相关的dao
 */
public interface UserInfoDao {

    /**
     * 注册用户
     *
     * @param info
     * @return
     */
    int registerUser(@Param("user") UserInfo info) throws Exception;

    /**
     * 判断用户是否存在
     *
     * @param userName
     * @param userPwd
     * @return
     */
    int getUserState(@Param("userName") String userName, @Param("userPwd") String userPwd) throws Exception;

    /**
     * 得到用户的信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    UserInfo getUserInfo(@Param("userId") int userId) throws Exception;

}
