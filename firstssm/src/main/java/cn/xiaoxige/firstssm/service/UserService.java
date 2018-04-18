package cn.xiaoxige.firstssm.service;

import cn.xiaoxige.firstssm.dao.UserTestDao;
import cn.xiaoxige.firstssm.entity.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserTestDao dao;

    public void addUser(UserTest test) {
        int i = dao.addUserInfo(test);
        System.out.println("插入完成 = " + i);
    }
}
