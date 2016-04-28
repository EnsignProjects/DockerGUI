/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ensignprojects.dockeradmin.rest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.ensignprojects.dockeradmin.config.ApplicationConfig;
import nl.ensignprojects.dockeradmin.container.Container;
import nl.ensignprojects.dockeradmin.container.ContainerEngine;
import nl.ensignprojects.dockeradmin.container.ContainerImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Docker endpoint query utility.
 * @author jurrian
 */
public class DockerEndpointQuery {
    
    private static String getAddress() {
        ApplicationConfig ac = ApplicationConfig.getInstance();
        
        return "http://" + ac.getHost() + ":" + ac.getPort();
        
    }
    
    /**
     * Stops a container
     * @param container 
     */
    public static void stopContainer(Container container) {
        performQuery("/containers/" + container.getId() + "/stop", "POST");       
    }
    
    /**
     * Starts a stopped container
     * @param container 
     */
    public static void startContainer(Container container) {
        performQuery("/containers/" + container.getId() + "/start", "POST");       
    }
    
    /**
     * Removes a container
     * If a container is not stopped, then the container is stopped and then removed.
     * @param container 
     */
    public static void removeContainer(Container container) {
        performQuery("/containers/" + container.getId() + "/stop", "POST");       
        performQuery("/containers/" + container.getId(), "DELETE");       
    }
    
    private static void performQuery(String endpoint, String requestMethod) {
        try {
            URL url = new URL(getAddress() + endpoint);
            HttpURLConnection connection = (HttpURLConnection)  url.openConnection();
            connection.setRequestMethod(requestMethod);
            Logger.getLogger(DockerEndpointQuery.class.getName()).log(Level.INFO, "HTTP status on stop container: {0}", connection.getResponseCode());
            connection.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(DockerEndpointQuery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DockerEndpointQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This function gets all containers from the endpoint
     * @return a list of containers
     */
    public static List<Container> getAllContainers() {
        
        List<Container> containerList = new ArrayList<>();
        
        try {
            URL url = new URL(getAddress() + "/containers/json?all=1");
            
            InputStreamReader isr = new InputStreamReader(url.openStream());
            
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonContainers = (JSONArray) jsonParser.parse(isr);
            
            Iterator iterator = jsonContainers.iterator();
            
            while (iterator.hasNext()) {
                JSONObject jsonContainer = (JSONObject) iterator.next();
                String id = (String) jsonContainer.get("Id");
                JSONArray jsonNames = (JSONArray) jsonContainer.get("Names");
                String name = (String) jsonNames.get(0);
                if (name.charAt(0) == '/') { 
                    name = name.substring(1);
                }
                String nameImage = (String) jsonContainer.get("Image");
                String state = (String) jsonContainer.get("State");
                
                Container container = new ContainerImpl(id, name, nameImage, state, ContainerEngine.DOCKER);
                containerList.add(container);
            }
                
        } catch (MalformedURLException ex) {
            Logger.getLogger(DockerEndpointQuery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(DockerEndpointQuery.class.getName()).log(Level.SEVERE, null, ex);            
        }
        
        return containerList;
                
                
    }
}
