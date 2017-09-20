package main.bean;

public class TestUserEntity {

    private int user_id;
    private String user_name;
    private boolean user_sex;
    private int user_age;
    private String user_desc;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public boolean isUser_sex() {
        return user_sex;
    }

    public void setUser_sex(boolean user_sex) {
        this.user_sex = user_sex;
    }

    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public String getUser_desc() {
        return user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    @Override
    public String toString() {
        return "TestUserEntity{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_sex=" + user_sex +
                ", user_age=" + user_age +
                ", user_desc='" + user_desc + '\'' +
                '}';
    }
}
