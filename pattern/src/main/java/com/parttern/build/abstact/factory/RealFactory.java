package com.parttern.build.abstact.factory;

/**
 * @author chenzhiqin
 * @date 18/8/2023 10:12
 * @info XX
 */
public class RealFactory {

    public static AbstractFactory getFactory(String name) {
        if ("animal".equals(name)) {
            return new AnimalFactory();
        }
        if ("plant".equals(name)) {
            return new PlantFactory();
        }
        return null;
    }


}
