package preferences;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConDB;
import connections.ConElement;
import connections.ConManager;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utilities.files.TranformerForXml;

/**
 * Cette classe fournit le nécessaire pour sauvegarder les préférences d'application dans le fichier XML application.pref. Cette méthode de sauvegarde vise à remplacer la sauvegarde dans un fichier sérialisé.
 * @author Giorgio Roncallo, adaptée et complétée par Steve Berberat
 */
public class PreferencesOfApplicationSaverXml {

  public void createFileApplicationPref() {
    Preferences prefApp = PreferencesManager.instance().getApplicationPref();

    try {
      //Creation du document en mémoire
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document document = builder.newDocument();

      //Création des éléments
      Element racine = document.createElement("applicationPreferences");
      document.appendChild(racine);

      Element preferences = document.createElement("preferences");
      racine.appendChild(preferences);

      Element debug = document.createElement("debug");
      debug.appendChild(document.createTextNode(prefApp.isDEBUG().toString()));
      preferences.appendChild(debug);

      Element debugBackgroudPanel = document.createElement("debugBackgroudPanel");
      debugBackgroudPanel.appendChild(document.createTextNode(prefApp.isDEBUG_BACKGROUND_PANEL().toString()));
      preferences.appendChild(debugBackgroudPanel);

      Element debugPrintMvccdelement = document.createElement("debugPrintMvccdElement");
      debugPrintMvccdelement.appendChild(document.createTextNode(prefApp.isDEBUG_PRINT_MVCCDELEMENT().toString()));
      preferences.appendChild(debugPrintMvccdelement);

      Element debugShowTableColHidden = document.createElement("debugShowTableColHidden");
      debugShowTableColHidden.appendChild(document.createTextNode(prefApp.isDEBUG_SHOW_TABLE_COL_HIDDEN().toString()));
      preferences.appendChild(debugShowTableColHidden);

      Element debugInspectObjectInTree = document.createElement("debugInspectObjectInTree");
      debugInspectObjectInTree.appendChild(document.createTextNode(prefApp.getDEBUG_INSPECT_OBJECT_IN_TREE().toString()));
      preferences.appendChild(debugInspectObjectInTree);

      Element debugEditorDatasChanged = document.createElement("debugEditorDatasChanged");
      debugEditorDatasChanged.appendChild(document.createTextNode(prefApp.getDEBUG_EDITOR_DATAS_CHANGED().toString()));
      preferences.appendChild(debugEditorDatasChanged);

      Element debugTdPrint = document.createElement("debugTdPrint");
      debugTdPrint.appendChild(document.createTextNode(prefApp.getDEBUG_TD_PRINT().toString()));
      preferences.appendChild(debugTdPrint);

      Element debugTdUnicityPrint = document.createElement("debugTdUnicityPrint");
      debugTdUnicityPrint.appendChild(document.createTextNode(prefApp.getDEBUG_TD_UNICITY_PRINT().toString()));
      preferences.appendChild(debugTdUnicityPrint);

      Element warningLevel = document.createElement("warningLevel");
      //Sauver la constante de texte (warning.level.info par exemple) et non le texte (info)
      warningLevel.appendChild(document.createTextNode(prefApp.getWARNING_LEVEL().getName()));
      preferences.appendChild(warningLevel);

      Element conDBMode = document.createElement("conDBMode");
      conDBMode.appendChild(document.createTextNode(prefApp.getCON_DB_MODE().getName()));
      preferences.appendChild(conDBMode);

      Element repositoryMcdModelsMny = document.createElement("repositoryMcdModelsMany");
      repositoryMcdModelsMny.appendChild(document.createTextNode(prefApp.getREPOSITORY_MCD_MODELS_MANY().toString()));
      preferences.appendChild(repositoryMcdModelsMny);

      Element repositoryMcdPackagesAuthorizeds = document.createElement("repositoryMcdPackagesAuthorizeds");
      repositoryMcdPackagesAuthorizeds.appendChild(document.createTextNode(prefApp.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS().toString()));
      preferences.appendChild(repositoryMcdPackagesAuthorizeds);

      Element persistenceSerialisationInsteadofXML = document.createElement("persistenceSerialisationInsteadofXML");
      persistenceSerialisationInsteadofXML.appendChild(document.createTextNode(prefApp.isPERSISTENCE_SERIALISATION_INSTEADOF_XML().toString()));
      preferences.appendChild(persistenceSerialisationInsteadofXML);

      Element showDiagrammerGrid = document.createElement("showDiagrammerGrid");
      showDiagrammerGrid.appendChild(document.createTextNode(prefApp.isDIAGRAMMER_SHOW_GRID().toString()));
      preferences.appendChild(showDiagrammerGrid);

      //Formatage du fichier
      Transformer transformer = new TranformerForXml().createTransformer();

      // Création des connexions
      saveConnections(document, racine);

      //Création du fichier
      DOMSource source = new DOMSource(document);
      StreamResult result = new StreamResult(new File(Preferences.FILE_APPLICATION_PREF_NAME));
      transformer.transform(source, result);

    } catch (ParserConfigurationException | TransformerException pce) {
      //TODO-PAS STB faire un throw(e) - Intégration dans la transaction d'ouverture (Si fichier inexistant - A checker)
      // et transaction d'édition
      pce.printStackTrace();
    }

  }

  private void saveConnections(Document document, Element rootTag) {
    Element connectionsRoot = document.createElement("dbConnections");

    List<ConElement> connections = ConManager.instance().getConElements();
    Map<String, List<ConConnection>> constructors = new HashMap<>();

    // Pour chaque élément
    for (int i = 0; i < connections.size(); i++) {
      ConDB con = connections.get(i).getConDB();
      List<ConConnection> relatedConnections = ConManager.instance().getConConnections(con);

      // Pour chaque connexion
      for (int j = 0; j < relatedConnections.size(); j++) {
        List<ConConnector> relatedConnectors = ConManager.instance().getConConnectors(relatedConnections.get(j));

        // Récupère les constructeurs
        for (int k = 0; k < relatedConnectors.size(); k++) {
          constructors.put(relatedConnectors.get(k).getParent().getParent().getName(), relatedConnections);
        }
      }
    }

    // Création des éléments
    for (String constructorName : constructors.keySet()) {
      // Création des constructeurs (Oracle, PostgreSQL, ...)
      Element constructorTag = document.createElement(constructorName);
      connectionsRoot.appendChild(constructorTag);

      // Pour chaque connexion
      List<ConConnection> relatedConnetions = constructors.get(constructorName);

      // Création de la balise parents des connexions
      Element connectionsTag = document.createElement("connections");
      constructorTag.appendChild(connectionsTag);

      for (ConConnection c : relatedConnetions) {

        // On crée un nouvel élément pour chaque connexion
        Element connectionTag = document.createElement("connection");
        connectionTag.setAttribute("name", c.getName());
        connectionTag.setAttribute("hostname", c.getHostName());
        connectionTag.setAttribute("port", c.getPort());
        connectionTag.setAttribute("conIDDBName", c.getConIDDBName().getName());
        connectionTag.setAttribute("username", c.getUserName());
        connectionTag.setAttribute("dbName", c.getDbName());
        connectionTag.setAttribute("driverDefault", String.valueOf(c.isDriverDefault()));
        connectionTag.setAttribute("driverFileCustom", c.getDriverFileCustom());

        if (c.isSavePW()) {
          connectionTag.setAttribute("password", c.getUserPW());
        }

        // Crée la balise connectors enfant
        Element connectorsTag = document.createElement("connectors");
        connectionTag.appendChild(connectorsTag);

        // On récupère les connecteurs liés à la connexion courante
        List<ConConnector> relatedConnectors = ConManager.instance().getConConnectors(c);

        // Pour chaque connecteurs
        for (ConConnector connector : relatedConnectors) {
          Element connectorTag = document.createElement("connector");

          connectorTag.setAttribute("name", connector.getName());
          connectorTag.setAttribute("username", connector.getUserName());
          if (connector.isSavePW()) {
            connectorTag.setAttribute("password", connector.getUserPW());
          }

          // Ajoute le connecteur à la connexion courante
          connectorsTag.appendChild(connectorTag);
        }

        connectionsTag.appendChild(connectionTag);
      }
    }

    // Ajoute le noeud connections au document
    rootTag.appendChild(connectionsRoot);
  }
}
