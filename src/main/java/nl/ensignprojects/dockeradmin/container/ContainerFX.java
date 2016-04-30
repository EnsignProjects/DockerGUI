/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ensignprojects.dockeradmin.container;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class defines a FX model for container
 * @author jurrian
 */
public class ContainerFX {
    
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleStringProperty state = new SimpleStringProperty("");
    private final SimpleStringProperty nameImage = new SimpleStringProperty("");
    private final SimpleBooleanProperty check = new SimpleBooleanProperty(false);
    private final Container container;
    
    public ContainerFX(Container container) {
        this.container = container;
        this.setName(container.getName());
        this.setNameImage(container.getNameImage());
        this.setState(container.getState());
    }
    
    public Container getContainer() {
        return container;
    }
    
    public void setCheck(Boolean check) {
        this.check.set(check);
    }
    
    public Boolean getCheck() {
        return check.get();
    }
    
    public BooleanProperty checkProperty() {
        return check;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    @Override
    public String toString() {
        return "Value changed to: " 
                + this.check.getValue() 
                + " - "
                + this.name.getValue();
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public String getNameImage() {
        return nameImage.get();
    }

    public void setNameImage(String nameImage) {
        this.nameImage.set(nameImage);
    }
        
}
