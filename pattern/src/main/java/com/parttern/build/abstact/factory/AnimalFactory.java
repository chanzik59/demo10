package com.parttern.build.abstact.factory;


/**
 * @author chenzhiqin
 * @date 17/8/2023 17:39
 * @info XX
 */
public class AnimalFactory  implements  AbstractFactory{



    @Override
    public Plant getPlant(String name) {
        return null;
    }

    @Override
    public Animal getAnimal(String name) {
        if ("dog".equals(name)) {
            return new Dog();
        }
        if ("sheep".equals(name)) {
            return new Sheep();
        }
        return null;
    }
}
