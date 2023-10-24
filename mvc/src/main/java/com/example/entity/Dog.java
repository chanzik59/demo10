package com.example.entity;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author czq
 * @date 2023/9/11 10:28
 * @Description:
 */
public class Dog  extends AA {




    private String id;



    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        List list = Arrays.asList("a", "b", "c");

        Object collect = list.stream().map(v -> (User) v).collect(Collectors.toList());


    }
}
