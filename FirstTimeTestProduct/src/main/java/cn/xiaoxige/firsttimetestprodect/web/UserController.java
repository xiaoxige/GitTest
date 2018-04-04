package cn.xiaoxige.firsttimetestprodect.web;

import cn.xiaoxige.firsttimetestprodect.entity.UserInfo;
import cn.xiaoxige.firsttimetestprodect.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService mUserService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestParam("userName") String userName, @RequestParam("userPwd") String userPwd, @RequestParam("nickName") String nickName) throws Exception {

        UserInfo info = new UserInfo();
        info.setUserName(userName);
        info.setUserPwd(userPwd);
        info.setNickName(nickName);
        int registerState = mUserService.register(info);
        return "" + registerState;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestParam(value = "msg", required = false) String msg) {
        return "数据 = " + msg;
    }


    @RequestMapping(value = "testone", method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestBody UserInfo userInfo) {

        return new Gson().toJson(userInfo);
    }

}
