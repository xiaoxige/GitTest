package cn.xiaoxige.firsttimetestprodect.service.impl;

import cn.xiaoxige.firsttimetestprodect.dao.UserInfoDao;
import cn.xiaoxige.firsttimetestprodect.entity.UserInfo;
import cn.xiaoxige.firsttimetestprodect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户相关的service
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDao userInfoDao;

    public int register(UserInfo info) throws Exception {
        return userInfoDao.registerUser(info);
    }
}
