package cn.xiaoxige.springone.spring_aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");

        Computer computer = (Computer) context.getBean("computer");

        int sum = computer.add(1, 3);

        System.out.println(sum);
    }
}
