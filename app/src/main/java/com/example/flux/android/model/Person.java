package com.example.flux.android.model;

/**
 * Created by ntop on 16/7/28.
 */
public class Person {
    // 联系人唯一标识
    private String id;
    // 联系人名称
    private String name;
    // 联系人头像
    private int avatar;

    public Person(){}
    //... other information

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
