package preferences;

import console.WarningLevel;
import exceptions.CodeApplException;
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

/**
 * Cette classe fournit le nécessaire pour charger les préférences d'application sauvegardées dans le fichier des
 * préférences XML (application.pref). Les préférences sont récupérées de ce fichier et sont affectées aux préférences
 * d'application existantes dans Preferences.java.
 * @author Giorgio Roncallo, adaptée et complétée par Steve Berberat
 */
public class PreferencesOfApplicationLoaderXml {

    public Preferences loadFileApplicationPref() throws FileNotFoundException {
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
            Element repositoryMcdModelsMany = (Element) racine.getElementsByTagName("repositoryMcdModelsMany").item(0);
            Element repositoryMcdPackagesAuthorizeds = (Element) racine.getElementsByTagName("repositoryMcdPackagesAuthorizeds").item(0);
            Element persistenceSerialisationInsteadofXML = (Element) racine.getElementsByTagName("persistenceSerialisationInsteadofXML").item(0);


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
            applicationPrefs.setREPOSITORY_MCD_MODELS_MANY(Boolean.valueOf(repositoryMcdModelsMany.getTextContent()));
            applicationPrefs.setREPOSITORY_MCD_PACKAGES_AUTHORIZEDS(Boolean.valueOf(repositoryMcdPackagesAuthorizeds.getTextContent()));
            applicationPrefs.setPERSISTENCE_SERIALISATION_INSTEADOF_XML(Boolean.valueOf(
                    persistenceSerialisationInsteadofXML.getTextContent()));

            // Validation du fichier
            validator.validate(new DOMSource(document));

        } catch (FileNotFoundException e) {
            throw (e);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new CodeApplException(e);
        }
    return applicationPrefs;
    }

}
