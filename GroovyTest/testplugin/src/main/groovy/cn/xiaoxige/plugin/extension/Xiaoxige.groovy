package cn.xiaoxige.plugin.extension

public class Xiaoxige {

    String version
    String name

    Xiaoxige() {
        version = ""
        name = ""
    }

    Xiaoxige(String version, String name) {
        this.version = version
        this.name = name
    }


    @Override
    public String toString() {
        return "Xiaoxige{" +
                "version='" + version + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}