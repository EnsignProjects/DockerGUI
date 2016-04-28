/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ensignprojects.dockeradmin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import nl.ensignprojects.dockeradmin.container.ContainerFX;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.ensignprojects.dockeradmin.container.Container;
import nl.ensignprojects.dockeradmin.rest.DockerEndpointQuery;

/**
 * Controller - Glue between businessrules and the view
 * @author jurrian
 */
public class FXMLContainerController {
    @FXML
    private TableView<ContainerFX> tableContainers;

    @FXML
    private TableColumn<ContainerFX, Boolean> check;

    @FXML
    private TableColumn<ContainerFX, String> name;    
    
    @FXML
    private TableColumn<ContainerFX, String> nameImage;    
    
    @FXML
    private TableColumn<ContainerFX, String> state;    
    
    @FXML
    private void startContainerAction(ActionEvent event) {
        this.containerAction(c -> DockerEndpointQuery.startContainer(c.getContainer()));
    }
    
    @FXML
    private void stopContainerAction(ActionEvent event) {
        this.containerAction(c -> DockerEndpointQuery.stopContainer(c.getContainer()));
    }
    
    @FXML
    private void removeContainerAction(ActionEvent event) {
        this.containerAction(c -> DockerEndpointQuery.removeContainer(c.getContainer()));
    }
    
    private void containerAction(Consumer<ContainerFX> action) {
        tableContainers.getItems()
                .filtered(c -> c.getCheck())
                .forEach(action);
        this.deSelectAllAction(new ActionEvent());
        tableContainers.setItems(parseInitContainers());
    }
    
    @FXML
    private void selectAllAction(ActionEvent event) {
        tableContainers.getItems().forEach(c -> c.setCheck(Boolean.TRUE));
    }
    
    @FXML 
    private void deSelectAllAction(ActionEvent event) {
        tableContainers.getItems().forEach(c -> c.setCheck(Boolean.FALSE));
    }

    @FXML
    private void invertSelection(ActionEvent event) {
        tableContainers.getItems().forEach(c -> c.setCheck(!c.getCheck()));
    }
    
    @FXML
    private void initialize() {
        check.setCellFactory(CheckBoxTableCell.forTableColumn(check));
        check.setCellValueFactory(c -> c.getValue().checkProperty());

        name.setCellValueFactory(new PropertyValueFactory<ContainerFX, String>("name"));
        nameImage.setCellValueFactory(new PropertyValueFactory<ContainerFX, String>("nameImage"));
        state.setCellValueFactory(new PropertyValueFactory<ContainerFX, String>("state"));
        
        tableContainers.setItems(parseInitContainers());
        
    }
    
    private ObservableList<ContainerFX> parseInitContainers(){
        // parse and construct User datamodel list by looping your ResultSet rs
        // and return the list   
        List<Container> containerList = DockerEndpointQuery.getAllContainers();
        List<ContainerFX> containerFXList = new ArrayList<>();
        
        containerList.stream().forEach((c) -> {
            containerFXList.add(new ContainerFX(c));
        });
        
        ObservableList<ContainerFX> dcs = 
                FXCollections.observableArrayList(containerFXList);
        
        return dcs;
    }    
    
}
