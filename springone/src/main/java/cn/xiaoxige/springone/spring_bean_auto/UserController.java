package cn.xiaoxige.springone.spring_bean_auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.ws.RequestWrapper;
import javax.xml.ws.RespectBinding;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    public void register(String userName, String userPwd) {
        System.out.println("注册控制器");
        userService.register(userName, userPwd);
    }

}
