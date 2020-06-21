package profile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import preferences.PreferencesManager;
import project.ProjectFileChooser;
import project.ProjectSaverXML;
import utilities.files.TranformerForXml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ProfileSaverXml {

    public void createFileProfileXML() {

        try {
            //Creation du document en memoire
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            //Création des éléments
            Element racine = document.createElement("FileProfile");
            document.appendChild(racine);

            //récuperation des préférences du projet
            new ProjectSaverXML().preferenceProject(document, racine);

            Transformer transformer = new TranformerForXml().createTransformer();

            ProfileFileChooser fileChooser = new ProfileFileChooser(ProjectFileChooser.SAVE);
            File fileChoose = fileChooser.fileChoose();

            if (fileChoose != null) {
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new FileOutputStream(fileChoose));
                transformer.transform(source, result);
            }

        } catch (ParserConfigurationException | TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
