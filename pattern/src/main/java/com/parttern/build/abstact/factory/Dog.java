package com.parttern.build.abstact.factory;

/**
 * @author chenzhiqin
 * @date 18/8/2023 10:05
 * @info XX
 */
public class Dog implements  Animal{
    @Override
    public void cry() {
        System.out.println("汪汪");
    }
}
