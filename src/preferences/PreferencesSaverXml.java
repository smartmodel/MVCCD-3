package preferences;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import project.ProjectSaverXML;
import utilities.files.TranformerForXml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class PreferencesSaverXml {

    public void createFileApplicationPref() {
        Preferences prefApp = PreferencesManager.instance().getApplicationPref();

        try {
            //Creation du document en memoire
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            //Création des éléments
            Element racine = document.createElement(Preferences.REPOSITORY_PREFERENCES_APPLICATION_NAME);
            document.appendChild(racine);

            Element debug = document.createElement("DEBUG");
            debug.appendChild(document.createTextNode(prefApp.isDEBUG().toString()));
            racine.appendChild(debug);

            Element debugBackgroudPanel = document.createElement("DEBUG_BACKGROUND_PANEL");
            debugBackgroudPanel.appendChild(document.createTextNode(prefApp.isDEBUG_BACKGROUND_PANEL().toString()));
            racine.appendChild(debugBackgroudPanel);

            Element debugPrintMvccdelement = document.createElement("DEBUG_PRINT_MVCCDELEMENT");
            debugPrintMvccdelement.appendChild(document.createTextNode(prefApp.isDEBUG_PRINT_MVCCDELEMENT().toString()));
            racine.appendChild(debugPrintMvccdelement);

            Element debugShowTableColHidden = document.createElement("DEBUG_SHOW_TABLE_COL_HIDDEN");
            debugShowTableColHidden.appendChild(document.createTextNode(prefApp.isDEBUG_SHOW_TABLE_COL_HIDDEN().toString()));
            racine.appendChild(debugShowTableColHidden);

            Element debugInspectObjectInTree = document.createElement("DEBUG_INSPECT_OBJECT_IN_TREE");
            debugInspectObjectInTree.appendChild(document.createTextNode(prefApp.getDEBUG_INSPECT_OBJECT_IN_TREE().toString()));
            racine.appendChild(debugInspectObjectInTree);

            Element repositoryMcdModelsMny  = document.createElement("REPOSITORY_MCD_MODELS_MANY");
            repositoryMcdModelsMny.appendChild(document.createTextNode(prefApp.getREPOSITORY_MCD_MODELS_MANY().toString()));
            racine.appendChild(repositoryMcdModelsMny);

            Element repositoryMcdPackagesAuthorizeds  = document.createElement("REPOSITORY_MCD_PACKAGES_AUTHORIZEDS");
            repositoryMcdPackagesAuthorizeds.appendChild(document.createTextNode(prefApp.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS().toString()));
            racine.appendChild(repositoryMcdPackagesAuthorizeds);

            // formatage du fichier
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
