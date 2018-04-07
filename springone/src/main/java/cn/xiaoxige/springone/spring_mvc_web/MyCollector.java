package cn.xiaoxige.springone.spring_mvc_web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyCollector {

    public MyCollector() {
        System.out.println("MyCollector create ...");
    }

    @RequestMapping("/test")
    @ResponseBody
    public UserEntity test() {
        return new UserEntity("朱小安");
    }

}
