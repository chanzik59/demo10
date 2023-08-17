package com.parttern.build.factory;

/**
 * @author chenzhiqin
 * @date 17/8/2023 17:39
 * @info XX
 */
public class AnimalFactory {


    public static void main(String[] args) {
        buildAnimal("dog").cry();
    }

    public static Animal buildAnimal(String name){
        if("dog".equals(name)){
            return new Dog();
        }
        if("sheep".equals(name)){
            return new Sheep();
        }
        return null;
    }
}
