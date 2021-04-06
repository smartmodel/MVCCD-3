package profile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import project.ProjectFileChooser;
import project.ProjectSaverXml;
import utilities.files.TranformerForXml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Giorgio Roncallo
 */
public class ProfileSaverXml {

    public void createFileProfileXML() {

        try {
            //Creation du document en memoire
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.newDocument();


            //Création des éléments
            Element racine = document.createElement("fileProfile");
            document.appendChild(racine);

            //récuperation des préférences du projet
            new ProjectSaverXml().addProjectPreferences(document, racine);

            // Formatage du fichier
            Transformer transformer = new TranformerForXml().createTransformer();

            // Création de la boîte de dialogue de fichiers
            ProfileFileChooser fileChooser = new ProfileFileChooser(ProjectFileChooser.SAVE);
            File fileChoose = fileChooser.fileChoose();

            if (fileChoose != null) {

                // Création du fichier par la boîte de dialogue
                DOMSource source =  new DOMSource(document);
                StreamResult result = new StreamResult(new FileOutputStream(fileChoose));
                transformer.transform(source, result);
            }

        } catch (ParserConfigurationException | TransformerException | IOException e) {
            e.printStackTrace();
        }
    }

}
