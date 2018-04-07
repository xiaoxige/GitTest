package cn.xiaoxige.springone.spring_bean_xml;

import java.util.List;

public class PersonPlus extends Person {

    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPersonPlus{" +
                "cars=" + cars +
                '}';
    }
}
