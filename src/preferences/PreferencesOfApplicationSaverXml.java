package preferences;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConDB;
import connections.ConManager;
import console.ViewLogsManager;
import exceptions.CodeApplException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utilities.files.TranformerForXml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

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
      racine.setAttribute("version", Preferences.APPLICATION_VERSION);
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

      // Création des connexions si le gestionnaire de connexion est en place
      // Il n'est pas en place lorsque le fichier des préférences doit être créé lors de l'installation du programme
      //#MAJ 2022-03-04 Création du fichier des préférences sans connexions
      try {
        if (ConManager.instance() != null) {
          saveConnections(document, racine);
        }
      } catch (Exception e){
        // Pas de connexion à sauver!
      }

      //Création du fichier
      DOMSource source = new DOMSource(document);
      StreamResult result = new StreamResult(new File(Preferences.FILE_APPLICATION_PREF_NAME));
      transformer.transform(source, result);

    } catch (ParserConfigurationException | TransformerException pce) {
      //#MAJ 2022-03-04 Création du fichier des préférences sans connexions
      //TODO1-PAS Mettre un/des messages explicites lié au Parser ou au transformer
      // Erreur du Parser ou Transformer
      throw new CodeApplException("Le fichier de préférence n'a pas pu être créé...");
    } catch (Exception e){
      //#MAJ 2022-03-04 Création du fichier des préférences sans connexions
      // Erreur inconnue
      ViewLogsManager.catchException(e, "Le fichier ne préférence ne peut pas être créé");
    }

  }

  private void saveConnections(Document document, Element rootTag) {
    Element connectionsRoot = document.createElement("dbConnections");

    //#MAJ 2022-03-03 Persistance XML des connexions
    //L'instruction ci-dessous est erronnée
    //List<ConElement> connections = ConManager.instance().getConElements();
    //Map<String, List<ConConnection>> constructors = new HashMap<>();

    // Pour chaque élément
    //for (int i = 0; i < connections.size(); i++) {
      //Trace.println(connections.toString());
      //ConDB con = connections.get(i).getConDB();
    /*
    for (ConDB con : ConDB.values()){
      List<ConConnection> relatedConnections = ConManager.instance().getConConnections(con);
      // Pour chaque connexion
      for (int j = 0; j < relatedConnections.size(); j++) {
        List<ConConnector> relatedConnectors = ConManager.instance().getConConnectors(relatedConnections.get(j));
      }
    }

     */

    // Création des éléments
    //for (String constructorName : constructors.keySet()) {
    for (ConDB con : ConDB.values()){
      // Création des constructeurs (Oracle, PostgreSQL, ...)
      Element constructorTag = document.createElement(con.getText());
      List<ConConnection> relatedConnections = ConManager.instance().getConConnections(con);
      connectionsRoot.appendChild(constructorTag);

      // Création de la balise parents des connexions
      Element connectionsTag = document.createElement("connections");
      constructorTag.appendChild(connectionsTag);

      // Pour chaque connexion
      for (ConConnection c : relatedConnections) {

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
