/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ensignprojects.dockeradmin.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application configuration
 * @author jurrian
 */
public class ApplicationConfig {
    
    private String host;
    private String port;
    
    private static ApplicationConfig instance = null;
    
    private ApplicationConfig() {
        String homeDir = System.getProperty("user.home");
        Path configFile = Paths.get(homeDir,".local","etc","dockeradmin","config.properties");
        
        Boolean configFileExists = Files.exists(configFile, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        
        Properties prop = new Properties();
        
        if (configFileExists) {
            try {
                Reader reader = new FileReader(configFile.toFile());
                prop.load(reader);
                host = prop.getProperty("host");
                port = prop.getProperty("port");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ApplicationConfig.class.getName()).log(Level.SEVERE, "Config file not found", ex);
            } catch (IOException ioe) {
                Logger.getLogger(ApplicationConfig.class.getName()).log(Level.SEVERE, "Config file failed to load", ioe);
            }
        } else {
            host = "localhost";
            port = "2375";
            
            Writer writer = null;
                    
            try {
                if(!Files.exists(configFile.getParent())) {
                    configFile.getParent().toFile().mkdirs();
                }
                
                writer = new FileWriter(configFile.toFile());
                prop.setProperty("host", host);
                prop.setProperty("port", port);
                prop.store(writer, "Initial configuration");
            } catch (IOException ex) {
                Logger.getLogger(ApplicationConfig.class.getName()).log(Level.SEVERE, "config file couldn't be written", ex);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.SEVERE, "config file couldn't be closed", ex);
                    }
                }
            }
        }
    } 
    
    /**
     * Gets the instance, the class is a singleton. 
     * @return instance of ApplicationConfig
     */
    public static ApplicationConfig getInstance() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }
        
        return instance;
    }

    /**
     * Gets hostname
     * @return String representation of the host
     */
    public String getHost() {
        return host;
    }
    
    
    /**
     * Gets port
     * @return String representation of the port
     */
    public String getPort() {
        return port;
    }
        
}
