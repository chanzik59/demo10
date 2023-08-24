package com.parttern.build.abstact.factory;

/**
 * @author chenzhiqin
 * @date 18/8/2023 10:02
 * @info
 */
public interface AbstractFactory {

    /**
     * 构建植物
     *
     * @return
     */
    Plant getPlant(String name);

    /**
     * 构建动物
     *
     * @return
     */
    Animal getAnimal(String name);


}
