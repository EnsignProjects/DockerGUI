# DockerGUI

This is a JavaFX GUI to perform simple admin tasks on docker containers.
It is not secure by default, so the advise is to use it on localhost not on servers (however if you create a SSH tunnel, it can be secure).

To use this program on linux, you need to make the docker socket available. For hosts running systemd you can find more directions in the following [post](http://www.campalus.com/enable-remote-tcp-connections-to-docker-host-running-ubuntu-15-04/).

You can change the defaults of the configuration (hostname and port number) in your home directory under ~/.local/etc/dockeradmin/config.properties

When you start the application the configuration file will be created.  

 ## To compile the source
 ```bash
 mvn install
 ```
