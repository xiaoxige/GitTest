package cn.xiaoxige.springone.spring_aop;

import org.springframework.stereotype.Component;


@Component
public class Computer {

    public int add(int x, int y) {
        return x + y;
    }

}
