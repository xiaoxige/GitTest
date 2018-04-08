package cn.xiaoxige.springone.spring_bean_xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-bean-xml.xml");

        System.out.println(context);

        Person person = (Person) context.getBean("person");

        System.out.println(person);

        Person person1 = (Person) context.getBean("person1");

        System.out.println(person1);

        Person person3 = (Person) context.getBean("person3");

        System.out.println(person3);

        PersonPlus personPlus = (PersonPlus) context.getBean("personPlus");
        System.out.println(personPlus);
    }
}
