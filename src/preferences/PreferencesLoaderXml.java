package preferences;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import java.io.FileNotFoundException;
import java.io.IOException;

public class PreferencesLoaderXml {

    public Preferences loadFileApplicationPref() throws FileNotFoundException {
        Preferences applicationPref = new Preferences(null, null);
        try {

            //Création du document en memoire
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(Preferences.FILE_APPLICATION_PREF_NAME));

            // Assignation du schéma XSD au fichier pour validation
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("schemas/SchemaApplicationPref.xsd"));
            Validator validator = schema.newValidator();

            //Récuperation des valeurs en memoire
            Element racine = document.getDocumentElement();
            Element preferences = (Element) racine.getElementsByTagName("preferences").item(0);
            Element debug = (Element) racine.getElementsByTagName("debug").item(0);
            Element debugBackgroudPanel = (Element) racine.getElementsByTagName("debugBackgroudPanel").item(0);
            Element debugPrintMvccdElement = (Element) racine.getElementsByTagName("debugPrintMvccdElement").item(0);
            Element debugShowTableColHidden = (Element) racine.getElementsByTagName("debugShowTableColHidden").item(0);
            Element debugInspectObjectInTree = (Element) racine.getElementsByTagName("debugInspectObjectInTree").item(0);
            Element repositoryMcdModelsMany = (Element) racine.getElementsByTagName("repositoryMcdModelsMany").item(0);
            Element repositoryMcdPackagesAuthorizeds = (Element) racine.getElementsByTagName("repositoryMcdPackagesAuthorizeds").item(0);

            // Instanciation des préférences de l'application
            applicationPref.setDEBUG(Boolean.valueOf(debug.getTextContent()));
            applicationPref.setDEBUG_BACKGROUND_PANEL(Boolean.valueOf(debugBackgroudPanel.getTextContent()));
            applicationPref.setDEBUG_PRINT_MVCCDELEMENT(Boolean.valueOf(debugPrintMvccdElement.getTextContent()));
            applicationPref.setDEBUG_SHOW_TABLE_COL_HIDDEN(Boolean.valueOf(debugShowTableColHidden.getTextContent()));
            applicationPref.setDEBUG_INSPECT_OBJECT_IN_TREE(Boolean.valueOf(debugInspectObjectInTree.getTextContent()));
            applicationPref.setREPOSITORY_MCD_MODELS_MANY(Boolean.valueOf(repositoryMcdModelsMany.getTextContent()));
            applicationPref.setREPOSITORY_MCD_PACKAGES_AUTHORIZEDS(Boolean.valueOf(repositoryMcdPackagesAuthorizeds.getTextContent()));

            // Validation du fichier
            validator.validate(new DOMSource(document));

        } catch (FileNotFoundException e) {
            throw (e);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    return applicationPref;
    }

}
