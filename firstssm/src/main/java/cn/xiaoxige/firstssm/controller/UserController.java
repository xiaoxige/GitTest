package cn.xiaoxige.firstssm.controller;

import cn.xiaoxige.firstssm.entity.UserTest;
import cn.xiaoxige.firstssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public UserTest userTest(@RequestBody UserTest test) {
        userService.addUser(test);
        return test;
    }
}
