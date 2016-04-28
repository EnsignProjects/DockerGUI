/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ensignprojects.dockeradmin.container;

/**
 * Container interface
 * @author jurrian
 */
public interface Container {

    String getId();

    String getName();

    String getNameImage();

    String getState();
    
    ContainerEngine getContainerEngine();
}
