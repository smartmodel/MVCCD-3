package preferences;

import connections.*;
import connections.mysql.ConnectionsMySQL;
import connections.oracle.ConConnectionOracle;
import connections.oracle.ConConnectorOracle;
import connections.oracle.ConnectionsOracle;
import connections.postgresql.ConConnectionPostgreSQL;
import connections.postgresql.ConConnectorPostgreSQL;
import connections.postgresql.ConnectionsPostgreSQL;
import console.WarningLevel;
import main.MVCCDElementApplicationConnections;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Cette classe fournit le nécessaire pour charger les préférences d'application sauvegardées dans le fichier des préférences XML (application.pref). Les préférences sont récupérées de ce fichier et sont affectées aux préférences d'application existantes dans Preferences.java.
 * @author Giorgio Roncallo, adaptée et complétée par Steve Berberat
 */
public class PreferencesOfApplicationLoaderXml {

  public Preferences loadFileApplicationPref() throws IOException, SAXException, ParserConfigurationException {
    Preferences applicationPrefs = new Preferences(null, null);
    try {

      //Création du document en mémoire
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      File applicationPrefsFilePath = new File(Preferences.FILE_APPLICATION_PREF_NAME);
      Document document = builder.parse(applicationPrefsFilePath); //Parse en charge le fichier dans un DOM. Si le fichier n'existe pas, une FileNotFoundException est levée (IOexception)

      //Assignation du schéma XSD au fichier pour validation
      SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      File applicationPrefsSchemaPath = new File("schemas/SchemaApplicationPref.xsd");
      Schema schema = factory.newSchema(applicationPrefsSchemaPath); //Parse et charge le fichier en tant que schema XSD. Si le fichier n'existe pas, une FileNotFoundException est levée (IOexception)
      Validator validator = schema.newValidator();

      //Récupération des valeurs en mémoire
      Element racine = document.getDocumentElement();
      Element preferences = (Element) racine.getElementsByTagName("preferences").item(0);
      Element debug = (Element) racine.getElementsByTagName("debug").item(0);
      Element debugBackgroudPanel = (Element) racine.getElementsByTagName("debugBackgroudPanel").item(0);
      Element debugPrintMvccdElement = (Element) racine.getElementsByTagName("debugPrintMvccdElement").item(0);
      Element debugShowTableColHidden = (Element) racine.getElementsByTagName("debugShowTableColHidden").item(0);
      Element debugInspectObjectInTree = (Element) racine.getElementsByTagName("debugInspectObjectInTree").item(0);
      Element debugEditorDatasChanged = (Element) racine.getElementsByTagName("debugEditorDatasChanged").item(0);
      Element debugTdPrint = (Element) racine.getElementsByTagName("debugTdPrint").item(0);
      Element debugTdUnicityPrint = (Element) racine.getElementsByTagName("debugTdUnicityPrint").item(0);
      Element warningLevel = (Element) racine.getElementsByTagName("warningLevel").item(0);
      Element conDBMode = (Element) racine.getElementsByTagName("conDBMode").item(0);
      Element repositoryMcdModelsMany = (Element) racine.getElementsByTagName("repositoryMcdModelsMany").item(0);
      Element repositoryMcdPackagesAuthorizeds = (Element) racine.getElementsByTagName("repositoryMcdPackagesAuthorizeds").item(0);
      Element persistenceSerialisationInsteadofXML = (Element) racine.getElementsByTagName("persistenceSerialisationInsteadofXML").item(0);
      Element showDiagrammerGrid = (Element) racine.getElementsByTagName("showDiagrammerGrid").item(0);

      // Instantiation des préférences de l'application
      applicationPrefs.setDEBUG(Boolean.valueOf(debug.getTextContent()));
      applicationPrefs.setDEBUG_BACKGROUND_PANEL(Boolean.valueOf(debugBackgroudPanel.getTextContent()));
      applicationPrefs.setDEBUG_PRINT_MVCCDELEMENT(Boolean.valueOf(debugPrintMvccdElement.getTextContent()));
      applicationPrefs.setDEBUG_SHOW_TABLE_COL_HIDDEN(Boolean.valueOf(debugShowTableColHidden.getTextContent()));
      applicationPrefs.setDEBUG_INSPECT_OBJECT_IN_TREE(Boolean.valueOf(debugInspectObjectInTree.getTextContent()));
      applicationPrefs.setDEBUG_EDITOR_DATAS_CHANGED(Boolean.valueOf(debugEditorDatasChanged.getTextContent()));
      applicationPrefs.setDEBUG_TD_PRINT(Boolean.valueOf(debugTdPrint.getTextContent()));
      applicationPrefs.setDEBUG_TD_UNICITY_PRINT(Boolean.valueOf(debugTdUnicityPrint.getTextContent()));

      //Lire la constante de texte (warning.level.info par exemple) et non le texte (info)
      applicationPrefs.setWARNING_LEVEL(WarningLevel.findByName(warningLevel.getTextContent()));
      applicationPrefs.setCON_DB_MODE(ConDBMode.findByName(conDBMode.getTextContent()));
      applicationPrefs.setREPOSITORY_MCD_MODELS_MANY(Boolean.valueOf(repositoryMcdModelsMany.getTextContent()));
      applicationPrefs.setREPOSITORY_MCD_PACKAGES_AUTHORIZEDS(Boolean.valueOf(repositoryMcdPackagesAuthorizeds.getTextContent()));
      applicationPrefs.setPERSISTENCE_SERIALISATION_INSTEADOF_XML(Boolean.valueOf(persistenceSerialisationInsteadofXML.getTextContent()));
      applicationPrefs.setDIAGRAMMER_SHOW_GRID(Boolean.valueOf(showDiagrammerGrid.getTextContent()));

      // Validation du fichier
      validator.validate(new DOMSource(document));

    } catch (Exception e) {
      //throw new CodeApplException("Erreur de lecture du fichier de préférences");
      throw e;
    }
    return applicationPrefs;
  }

  public static void loadConnections(Document document, MVCCDElementApplicationConnections applicationConnections) throws ParserConfigurationException, IOException, SAXException {

    // On récupère toutes les balise connection
    NodeList allConnectionTags = document.getElementsByTagName("connection");

    //#MAJ 2022-03-03 Persistance XML des connexions
    ConnectionsOracle applicationConnexionsOracle = new ConnectionsOracle(applicationConnections);
    ConnectionsPostgreSQL applicationConnexionsPostgreSQL = new ConnectionsPostgreSQL(applicationConnections);
    ConnectionsMySQL applicationConnexionsMySQL = new ConnectionsMySQL(applicationConnections);

    for (int i = 0; i < allConnectionTags.getLength(); i++) {

      Node currentConnection = allConnectionTags.item(i);

      // On récupère le constructeur (Oracle, PostgreSQL, ...)
      String constructorName = allConnectionTags.item(i).getParentNode().getParentNode().getNodeName();

      // On récupère les attributs de la balise courante
      NamedNodeMap attributes = currentConnection.getAttributes();

      if (attributes != null) {
        String name = attributes.getNamedItem("name").getNodeValue();
        String dbName = attributes.getNamedItem("dbName").getNodeValue();
        String hostName = attributes.getNamedItem("hostname").getNodeValue();
        String port = attributes.getNamedItem("port").getNodeValue();
        ConIDDBName conIDDBName = ConIDDBName.findByName(attributes.getNamedItem("conIDDBName").getNodeValue());
        String username = attributes.getNamedItem("username").getNodeValue();
        boolean driverDefault = Boolean.parseBoolean(attributes.getNamedItem("driverDefault").getNodeValue());
        String driverFileCustom = attributes.getNamedItem("driverFileCustom").getNodeValue();

        // Vérifie si le mot de passe est persisté
        String password = null;
        if (attributes.getNamedItem("password") != null) {
          password = attributes.getNamedItem("password").getNodeValue();
        }

        ConConnection connection = null;

        //#MAJ 2022-03-03 Persistance XML des connexions

        // Oracle
        if (constructorName.equals(ConDB.ORACLE.getLienProg())) {
          connection = new ConConnectionOracle(applicationConnexionsOracle);
        }

        // PostgreSQL
        if (constructorName.equals(ConDB.POSTGRESQL.getLienProg())) {
          connection = new ConConnectionPostgreSQL(applicationConnexionsPostgreSQL);
        }

        // Set les propriétés à la connexion
        if (connection != null) {
          connection.setName(name);
          connection.setDbName(dbName);
          connection.setHostName(hostName);
          connection.setPort(port);
          connection.setConIDDBName(conIDDBName);
          connection.setUserName(username);
          connection.setDriverDefault(driverDefault);
          connection.setDriverFileCustom(driverFileCustom);
          if (password != null) {
            connection.setUserPW(password);
          }
        }

        // On charge les connecteurs pour la connexion courante
        loadConnectors(connection, currentConnection);

      }
    }

  }

  private static void loadConnectors(ConConnection connection, Node currentConnection) {

    Node connectorsTag = currentConnection.getChildNodes().item(1);
    NodeList allConnectorTags = connectorsTag.getChildNodes();

    for (int i = 0; i < allConnectorTags.getLength(); i++) {
      Node currentConnector = allConnectorTags.item(i);

      NamedNodeMap connectorAttributes = currentConnector.getAttributes();

      if (connectorAttributes != null) {

        ConConnector connector = null;

        if (connection instanceof ConConnectionOracle) {
          connector = new ConConnectorOracle(connection);
        }

        if (connection instanceof ConConnectionPostgreSQL) {
          connector = new ConConnectorPostgreSQL(connection);
        }

        connector.setName(connectorAttributes.getNamedItem("name").getNodeValue());
        connector.setUserName(connectorAttributes.getNamedItem("username").getNodeValue());

        if (connectorAttributes.getNamedItem("password") != null) {
          connector.setUserPW(connectorAttributes.getNamedItem("password").getNodeValue());
        }

      }
    }
  }

}
