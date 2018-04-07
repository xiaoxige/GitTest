package cn.xiaoxige.springone.spring_bean_auto;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-bean-auto.xml");
        AutoTest autoTest = (AutoTest) context.getBean("autoTest");
        System.out.println(autoTest);

        UserController userController = (UserController) context.getBean("userController");

        userController.register("小稀革", "zhuxiaoan?19931!");

    }
}
