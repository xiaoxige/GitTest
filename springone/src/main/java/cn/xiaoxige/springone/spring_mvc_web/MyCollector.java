package cn.xiaoxige.springone.spring_mvc_web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyCollector {

    @RequestMapping("/test")
    @ResponseBody
    public UserEntity test() {
        return new UserEntity("朱小安");
    }

    /**
     * 在测试的结果总结
     * 1. 不加@RequestBody只能使用默认的 Content-Type : application/x-www-form-urlencoded
     * 2. body数据格式： a=x&b=y
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ResponseBody
    public UserEntity test1(UserEntity entity) {
        return entity;
    }

    /**
     * 在测试的结果总结
     * 1. Content-Type : 默认 application/x-www-form-urlencoded
     * 2. body数据格式： a=x&b=y
     * @param name
     * @param pwd
     * @return
     */
    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    @ResponseBody
    public UserEntity test2(@RequestParam String name, @RequestParam String pwd) {
        return new UserEntity(name, pwd);
    }

    /**
     * 在测试的结果总结
     * 1. @RequestBody修饰String取的是body中的值
     * 2. Content-Type : 默认 application/x-www-form-urlencoded
     * 3. body数据格式： a=x&b=y
     * @param name
     * @param pwd
     * @return
     */
    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    @ResponseBody
    public UserEntity test3(@RequestBody String name, @RequestBody String pwd) {
        System.out.println(name);
        System.out.println(pwd);
        return new UserEntity(name, pwd);
    }

    /**
     * 在测试的结果总结
     * 1. @RequestBody 修饰类，body中发送的内容要为json
     * 2. Content-Type ： application/json
     * 3. body数据格式： {
     *     a:x,
     *     b:y
     * }
     * @param entity
     * @return
     */
    @RequestMapping(value = "/test4", method = RequestMethod.POST)
    @ResponseBody
    public UserEntity test4(@RequestBody UserEntity entity) {
        return entity;
    }
}
