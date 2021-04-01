package profile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import preferences.Preferences;
import project.ProjectLoaderXml;

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

//TODO-STB: Gérer si possible le format des profile avec le même XSD que celui des projets
/**
 * @author Giorgio Roncallo
 */
public class ProfileLoaderXml {

    public Preferences loadFileProfileXML(String profileFileName) {
        Preferences profilePref = new Preferences(null, null);
        if (profileFileName != null) {
            try {

                // Création du document en mémoire et du parseur pour parcourir le fichier
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = builder.parse(new File(Preferences.DIRECTORY_PROFILE_NAME + Preferences.SYSTEM_FILE_SEPARATOR + profileFileName));

                // Assignation du schéma XSD au fichier pour validation
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = factory.newSchema(new File("schemas/SchemaProfil.xsd"));
                Validator validator = schema.newValidator();

                // Récupération de la racine
                Element racine = document.getDocumentElement();

                // Récupération des préférences
                new ProjectLoaderXml().addPreferences(racine, profilePref);

                // Validation du fichier
                validator.validate(new DOMSource(document));

            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }
        return profilePref;
    }
}
