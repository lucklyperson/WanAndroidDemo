package com.example.wu.wanandroiddemo.bean;

import java.io.Serializable;

/**
 * Created by lw on 2018/1/23.
 */

public class Friend implements Serializable {
    private int id;
    private String name;
    private String link;
    private int visible;
    private int order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", visible=" + visible +
                ", order=" + order +
                '}';
    }
}
