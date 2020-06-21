package project;

import main.MVCCDManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import preferences.Preferences;
import profile.Profile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ProjectLoadXML {

    public Project loadProjectFile() {
        Project project = new Project(null);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(MVCCDManager.instance().getProject().getName() + Preferences.FILE_DOT + Preferences.FILE_PROJECT_EXTENSION));

            Element racine = document.getDocumentElement();

            NodeList mcdChilds = racine.getChildNodes();

            for (int i = 0; i < mcdChilds.getLength(); i++) {
                Node mcdChild = mcdChilds.item(i);
                if (mcdChild instanceof Element) {
                    if (mcdChild.getNodeName().equals("Propriétés")) {
                        getPropertiesProject(mcdChild, project);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return project;
    }

    private void getPropertiesProject(Node mcdChild, Project project) {

        NodeList propertiesList = mcdChild.getChildNodes();

        for (int i = 0; i < propertiesList.getLength(); i++) {
            Node property = propertiesList.item(i);
            if (property instanceof Element) {
                if (property.getNodeName().equals("Name")) {
                    Element name = (Element) property;
                    project.setName(name.getTextContent());
                }
                if (property.getNodeName().equals("Profile_file_name")){
                    Element profileFileName = (Element) property;
                    project.setProfileFileName(profileFileName.getTextContent());
                }
                if (property.getNodeName().equals("Profile")){
                    Element elementProfile = (Element) property;
                    Profile profile = new Profile(project,elementProfile.getTextContent());
                    project.setProfile(profile);
                }
                if (property.getNodeName().equals("Models_many")){

                }
                if (property.getNodeName().equals("Packages_autorizeds")){

                }
            }
        }
    }
}
