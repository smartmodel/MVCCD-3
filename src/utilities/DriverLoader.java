package utilities;
import exceptions.CodeApplException;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.sql.DriverManager;

public class DriverLoader {

    public DriverLoader(File fileDriver, String forName)  {
        try {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{fileDriver.toURI().toURL()}, this.getClass().getClassLoader());
            Driver driver = (Driver) Class.forName(forName, true, classLoader).newInstance();
            //DriverManager.registerDriver(new DelegatingDriver(driver)); // register using the Delegating Driver
            DriverManager.registerDriver(driver); // register

            //DriverManager.getDriver("jdbc:postgresql://host/db"); // checks that the driver is found
        } catch(Exception e ){
           throw new CodeApplException(e.getMessage(), e) ;
        }
    }
}

