package com.parttern.build.abstact.factory;


/**
 * @author chenzhiqin
 * @date 17/8/2023 17:39
 * @info XX
 */
public class PlantFactory implements  AbstractFactory{

    @Override
    public Plant getPlant(String name) {
        if ("apple".equals(name)) {
            return new Apple();
        }
        if ("pineapple".equals(name)) {
            return new Pineapple();
        }
        return null;
    }

    @Override
    public Animal getAnimal(String name) {
        return null;
    }
}
