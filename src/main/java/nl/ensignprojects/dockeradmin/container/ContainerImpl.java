/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ensignprojects.dockeradmin.container;

/**
 * Implementation of the container
 * @author jurrian
 */
public class ContainerImpl implements Container {
    private final ContainerEngine containerEngine;

    private final String id;
    private final String name;
    private final String nameImage;
    private final String state;

    public ContainerImpl(String id, String name, String nameImage, String state, ContainerEngine containerEngine) {
        this.id = id;
        this.name = name;
        this.nameImage = nameImage;
        this.state = state;
        this.containerEngine = containerEngine;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNameImage() {
        return nameImage;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public ContainerEngine getContainerEngine() {
        return containerEngine;
    }
    
}
