package preferences;

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

/**
 * Cette classe fournit le nécessaire pour sauvegarder les préférences d'application dans le fichier XML
 * application.pref.
 * Cette méthode de sauvegarde vise à remplacer la sauvegarde dans un fichier sérialisé.
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
            debugEditorDatasChanged.appendChild(document.createTextNode(prefApp.getDEBUG_INSPECT_OBJECT_IN_TREE().toString()));
            preferences.appendChild(debugEditorDatasChanged);

            Element debugTdPrint = document.createElement("debugTdPrint");
            debugTdPrint.appendChild(document.createTextNode(prefApp.getDEBUG_INSPECT_OBJECT_IN_TREE().toString()));
            preferences.appendChild(debugTdPrint);

            Element debugTdUnicityPrint = document.createElement("debugTdUnicityPrint");
            debugTdUnicityPrint.appendChild(document.createTextNode(prefApp.getDEBUG_INSPECT_OBJECT_IN_TREE().toString()));
            preferences.appendChild(debugTdUnicityPrint);

            Element repositoryMcdModelsMny  = document.createElement("repositoryMcdModelsMany");
            repositoryMcdModelsMny.appendChild(document.createTextNode(prefApp.getREPOSITORY_MCD_MODELS_MANY().toString()));
            preferences.appendChild(repositoryMcdModelsMny);

            Element repositoryMcdPackagesAuthorizeds  = document.createElement("repositoryMcdPackagesAuthorizeds");
            repositoryMcdPackagesAuthorizeds.appendChild(document.createTextNode(prefApp.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS().toString()));
            preferences.appendChild(repositoryMcdPackagesAuthorizeds);

            //Formatage du fichier
            Transformer transformer = new TranformerForXml().createTransformer();

            //Création du fichier
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(Preferences.FILE_APPLICATION_PREF_NAME));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }

    }
}
