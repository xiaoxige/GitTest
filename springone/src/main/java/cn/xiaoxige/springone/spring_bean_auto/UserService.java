package cn.xiaoxige.springone.spring_bean_auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void register(String userName, String userPwd) {
        System.out.println("注册服务");
        userDao.register(userName, userPwd);
    }
}
