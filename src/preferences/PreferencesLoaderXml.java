package preferences;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PreferencesLoaderXml {

    public Preferences loadFileApplicationPref() throws FileNotFoundException {
        Preferences applicationPref = new Preferences(null, null);
        try {

            //Création du document en memoire
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(Preferences.FILE_APPLICATION_PREF_NAME));

            //Récuperation des valeurs en memoire
            Element racine = document.getDocumentElement();
            Element debug = (Element) racine.getElementsByTagName("DEBUG").item(0);
            Element debugBackgroudPanel = (Element) racine.getElementsByTagName("DEBUG_BACKGROUND_PANEL").item(0);
            Element debugPrintMvccdElement = (Element) racine.getElementsByTagName("DEBUG_PRINT_MVCCDELEMENT").item(0);
            Element debugShowTableColHidden = (Element) racine.getElementsByTagName("DEBUG_SHOW_TABLE_COL_HIDDEN").item(0);
            Element debugInspectObjectInTree = (Element) racine.getElementsByTagName("DEBUG_INSPECT_OBJECT_IN_TREE").item(0);
            Element repositoryMcdModelsMany = (Element) racine.getElementsByTagName("REPOSITORY_MCD_MODELS_MANY").item(0);
            Element repositoryMcdPackagesAuthorizeds = (Element) racine.getElementsByTagName("REPOSITORY_MCD_PACKAGES_AUTHORIZEDS").item(0);

            // Instanciation des préférences de l'application
            applicationPref.setDEBUG(Boolean.valueOf(debug.getTextContent()));
            applicationPref.setDEBUG_BACKGROUND_PANEL(Boolean.valueOf(debugBackgroudPanel.getTextContent()));
            applicationPref.setDEBUG_PRINT_MVCCDELEMENT(Boolean.valueOf(debugPrintMvccdElement.getTextContent()));
            applicationPref.setDEBUG_SHOW_TABLE_COL_HIDDEN(Boolean.valueOf(debugShowTableColHidden.getTextContent()));
            applicationPref.setDEBUG_INSPECT_OBJECT_IN_TREE(Boolean.valueOf(debugInspectObjectInTree.getTextContent()));
            applicationPref.setREPOSITORY_MCD_MODELS_MANY(Boolean.valueOf(repositoryMcdModelsMany.getTextContent()));
            applicationPref.setREPOSITORY_MCD_PACKAGES_AUTHORIZEDS(Boolean.valueOf(repositoryMcdPackagesAuthorizeds.getTextContent()));

        } catch (FileNotFoundException e) {
            throw (e);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    return applicationPref;
    }

}
