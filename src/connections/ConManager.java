package connections;

import connections.services.ConnectionsService;
import console.ViewLogsManager;
import exceptions.CodeApplException;
import main.MVCCDElement;
import main.MVCCDElementApplicationConnections;
import main.MVCCDManager;
import preferences.Preferences;
import resultat.Resultat;
import resultat.ResultatLevel;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ConManager {

    private static ConManager instance;
    private MVCCDElementApplicationConnections applicationConnections;
    private ConnectionsOracle connectionsOracle;

    public static synchronized ConManager instance() {

        if (instance == null) {
            instance = new ConManager();
        }
        return instance;
    }

    public ConManager() {
        applicationConnections = MVCCDManager.instance().getConnectionsRoot();
        connectionsOracle = (ConnectionsOracle) getConnectionsBD(ConDB.ORACLE);
    }

    private ConnectionsDB getConnectionsBD(ConDB conDB) {
        for (ConnectionsDB connectionsDB : applicationConnections.getConnectionsDB()) {
            if (connectionsDB.getConDB() == conDB) {
                return connectionsDB;
            }
        }
        return null;
    }


    public ArrayList<ConConnection> getConResources(ConDB conDB) {
        ArrayList<ConConnection> resultat = new ArrayList<ConConnection>();
        ConnectionsDB connectionsDB = getConnectionsBD(conDB);
        for (MVCCDElement mvccdElement : connectionsDB.getChilds()) {
            if (mvccdElement instanceof ConConnection) {
                resultat.add((ConConnection) mvccdElement);
            }
        }
        return resultat;
    }

    public ArrayList<ConConnector> getConConnectors(ConConnection conConnection) {
        ArrayList<ConConnector> resultat = new ArrayList<ConConnector>();
        for (MVCCDElement mvccdElement : conConnection.getChilds()) {
            if (mvccdElement instanceof ConConnector) {
                resultat.add((ConConnector) mvccdElement);
            }
        }
        return resultat;
    }

    public ArrayList<ConElement> getConElements(ConDB conDB) {
        ArrayList<ConElement> resultat = new ArrayList<ConElement>();
        for (ConConnection conConnection : getConResources(conDB)) {
            resultat.add(conConnection);
            resultat.addAll(getConConnectors(conConnection));
        }
        return resultat;
    }

    public ArrayList<ConElement> getConElements() {
        ArrayList<ConElement> resultat = new ArrayList<ConElement>();
        for (ConDB conDB : ConDB.values()) {
            resultat.addAll(getConResources(conDB));
        }
        return resultat;
    }


    /**
     * Retourne le chemin et nom du fichier contenant le pilote pour la BD sélectionnée
     */

    static public String getDriverDirectory(ConDB conDB) {
        return Preferences.CON_DIRECTORY_DRIVERS + File.separator + conDB.getDirectoryDriverDefault();
    }

    public static String getDefaultDriverFileName(ConDB conDB) {
        File directoryDriverdefault = new File(getDriverDirectory(conDB));
        String[] driverDefault = directoryDriverdefault.list();
        if (driverDefault.length == 0) {
            throw new CodeApplException("Il n'est pas trouvé le fichier driver par défaut dans le répertoire " + directoryDriverdefault.getPath());
        } else if (driverDefault.length == 1) {
            return driverDefault[0];
        } else {
            throw new CodeApplException("Il est trouvé plus d'un fichier dans le répertoire de driver par défaut " + directoryDriverdefault.getPath());
        }
    }


    public static String getDefaultDriverFileNamePathAbsolute(ConDB conDB) {
        String driverDefault = System.getProperty("user.dir") + File.separator +
                Preferences.CON_DIRECTORY_DRIVERS +File.separator +
                conDB.getDirectoryDriverDefault() +File.separator +
                ConManager.getDefaultDriverFileName(conDB);
        return driverDefault;
    }

    public static Driver createDriver(ConConnection conConnection)  {
        return createDriver(conConnection.getConDB(), conConnection.getDriverFileToUse());
    }

    public static Driver createDriver(ConDB conDB, File fileDriver)  {
        try {
            URL url = fileDriver.toURI().toURL();
            String className = conDB.getForName() ;  // Par exemple : "oracle.jdbc.driver.OracleDriver";
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { url });
            Driver d = (Driver) Class.forName(className, true, urlClassLoader).getDeclaredConstructor().newInstance();

            return d;
        } catch(Exception e ){
            throw new CodeApplException(e.getMessage(), e) ;
        }
    }


    // Appel direct depuis une instance de connecteur
    public static Connection createConnection(Window owner,
                                              ConConnector conConnector) {

        // Test de l'instance de connecteur
        Resultat resultat = conConnector.getConDB().getConConnectorEditingTreat().treatCompletness(
                owner, conConnector, true);

        // Test du connecteur avec la connexion parent et userName/PW du connecteur
        if (resultat.isNotError()) {
            return createConnection(owner,
                                    (ConConnection) conConnector.getParent(),
                                    conConnector.getUserName(),
                                    conConnector.getUserPW() );
        }
        return null;
    }

    // Appel direct depuis l'édition d'un connecteur
    public static Connection createConnection(Window owner,
                                              ConConnection conConnectionParent,
                                              String userName,
                                              String userPW) {

        //Test de la connexion parent du connecteur
        Connection connection = createConnection(owner, conConnectionParent);
        // Clone de la connexion car, il y a changement du user/pw !
        ConConnection conConnectionParentClone = (ConConnection) conConnectionParent.clone();
        if (connection != null){
            // L'appel se fait depuis l'éditeur, sinon c'est un objet conConnecteur qui est passé en paramètre
            //Mettre le userName et password du connecteur dans l'objet connexion
            conConnectionParentClone.setUserName(userName);
            conConnectionParentClone.setUserPW(userPW);
            return createConnection(owner, conConnectionParentClone);
        }
        return null;
    }

    // Appel direct depuis une instance de connection
    public static Connection createConnection(Window owner,
                                              ConConnection conConnection)  {
        // Test de l'instance de connexion

        Resultat resultat = conConnection.getConDB().getConConnectionEditingTreat().treatCompletness(
                owner, conConnection, true);

        if (resultat.isNotError()) {
            return createConnection(owner,
                    conConnection.getConDB(),
                    conConnection.getDriverFileToUse(),
                    conConnection.getHostName(),
                    conConnection.getPort(),
                    conConnection.getConIDDBName(),
                    conConnection.getDbName(),
                    conConnection.getUserName(),
                    conConnection.getUserPW()
            );
        } else {
            return null;
        }
    }


    // Appel direct depuis l'édition d'une connexion
    // Sinon appel en fin par les 3 autres méthodes createConnection()
    public static Connection createConnection(Window owner,
                                              ConDB conDB,
                                              File driverFileToUse,
                                              String hostName,
                                              String port,
                                              ConIDDBName iddbName,
                                              String dbName,
                                              String userName,
                                              String userPW) {

    /*
                Exemple de code en clair de connexion
                ========================
                File fileDriver = new File("C:\\Users\\pasun\\.DataGrip2019.2\\config\\jdbc-drivers\\Oracle\\12.1.0.2\\ojdbc6-12.1.0.2.jar");
                URL url = fileDriver.toURI().toURL();
                String className = "oracle.jdbc.driver.OracleDriver";
                URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { url });
                Driver d = (Driver) Class.forName(className, true, urlClassLoader).getDeclaredConstructor().newInstance();
                DriverManager.registerDriver(d);  //Ne fonctionne pas
                // Mais DriverManager ne m'est pas nécessaire car je recrée le driver et la connexion à chaque génération de code
                Properties connectionProps = new Properties();
                connectionProps.put("user", "TEST1");
                connectionProps.put("password", "TEST1");
                // Avec SID
                Connection connection = d.connect("jdbc:oracle:thin:@localhost:1521:XE", connectionProps );
                // Avec Service name
                Connection connection = d.connect("jdbc:oracle:thin:@localhost:1521/XEPDB1", connectionProps );
    */

        try {
            Driver driver = createDriver(conDB, driverFileToUse);
            // Je n'enregistre pas le driver car cela ne marche pas et ce ne m'est pas nécessaire
            //DriverManager.registerDriver(driver);

            String connectURL = ConnectionsService.getResourceURL(conDB,
                    hostName, port, iddbName, dbName);

            Properties connectionProps = new Properties();
            connectionProps.put("user", userName);
            connectionProps.put("password", userPW);

            Connection connection = driver.connect(connectURL, connectionProps);
            return connection;

        } catch(SQLException e ){
            //TODO-1 Finaliser le traitement d'erreur
            if ( e.getMessage().indexOf("ORA-12505") > -1 ){
                String message = "Erreur Oracle : " + e.getMessage() +
                        "\r\nIl ne faut pas que la liste réfère SID et que le nom  soit un service";
                ViewLogsManager.printMessage(message, ResultatLevel.INFO);
                ViewLogsManager.dialogQuittance(owner, message);
                return null;
            } else {
                throw new CodeApplException(e.getMessage(), e) ;
            }
        } catch(Exception e ){
            throw new CodeApplException(e.getMessage(), e) ;
        }
    }

}
