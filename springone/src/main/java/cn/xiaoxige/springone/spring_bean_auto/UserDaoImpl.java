package cn.xiaoxige.springone.spring_bean_auto;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    public void register(String userName, String userPwd) {
        System.out.println("注册" + userName + "， " + userPwd);
    }
}
