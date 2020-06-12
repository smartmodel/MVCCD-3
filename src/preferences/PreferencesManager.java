package preferences;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import profile.ProfileFileChooser;
import project.Project;
import project.ProjectFileChooser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;


public class PreferencesManager {

    private static PreferencesManager instance ;

    private Preferences defaultPref;
    private Preferences applicationPref;
    private Preferences profilePref;
    private Preferences projectPref;

    public static synchronized PreferencesManager instance(){
        if(instance == null){
            instance = new PreferencesManager();
        }
        return instance;
    }

    public PreferencesManager() {
        defaultPref = new Preferences(null, null);
    }

    public Preferences preferences (){
        if (projectPref != null){
            return projectPref;
        } else  if (applicationPref != null){
            return applicationPref;
        } else  if (defaultPref != null){
            return defaultPref;
        } else {
            return null;
        }
    }

    public Preferences profileOrDefault (){
        if (profilePref != null){
            return profilePref;
        } else  if (defaultPref != null){
            return defaultPref;
        } else {
            return null;
        }
    }

    public Preferences getDefaultPref() {
        return defaultPref;
    }

    public Preferences getApplicationPref() {
        return applicationPref;
    }

    public Preferences getProfilePref() {
        return profilePref;
    }

    public void setProfilePref(Preferences profilePref) {

        this.profilePref = profilePref;
    }

    public Preferences getProjectPref() {

        return projectPref;
    }

    public void setProjectPref(Preferences projectPref) {
        this.projectPref = projectPref;
    }

    public void copyApplicationPref(int projectState) {
        projectPref.setDEBUG(applicationPref.isDEBUG());
        projectPref.setDEBUG_PRINT_MVCCDELEMENT(applicationPref.isDEBUG_PRINT_MVCCDELEMENT());
        projectPref.setDEBUG_BACKGROUND_PANEL(applicationPref.isDEBUG_BACKGROUND_PANEL());
        projectPref.setDEBUG_SHOW_TABLE_COL_HIDDEN(applicationPref.isDEBUG_SHOW_TABLE_COL_HIDDEN());
        projectPref.setDEBUG_INSPECT_OBJECT_IN_TREE(applicationPref.getDEBUG_INSPECT_OBJECT_IN_TREE());

        // Pour le moment pas de changement possible pour un projet existant
        // A analyser et reprendre plus tard
        if (projectState == Project.NEW) {
            projectPref.setREPOSITORY_MCD_MODELS_MANY(applicationPref.getREPOSITORY_MCD_MODELS_MANY());
        }

        projectPref.setREPOSITORY_MCD_PACKAGES_AUTHORIZEDS(applicationPref.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
    }

    public void copyProfilePref() {
        copyPref(profilePref, projectPref);
    }

    /*
    public void copyDefaultPref() {
        copyPref(defaultPref, projectPref);
     }

     */

    private void copyPref(Preferences from, Preferences to) {
        // MCD
        to.setMCD_AID_IND_COLUMN_NAME(from.getMCD_AID_IND_COLUMN_NAME());
        to.setMCD_AID_WITH_DEP(from.isMCD_AID_WITH_DEP());
        to.setMCD_AID_DEP_COLUMN_NAME(from.getMCD_AID_DEP_COLUMN_NAME());
        to.setMCD_AID_DATATYPE_LIENPROG(from.getMCD_AID_DATATYPE_LIENPROG());
        to.setMCDDATATYPE_NUMBER_SIZE_MODE(from.getMCDDATATYPE_NUMBER_SIZE_MODE());
        to.setMCD_JOURNALIZATION(from.getMCD_JOURNALIZATION());
        to.setMCD_JOURNALIZATION_EXCEPTION(from.getMCD_JOURNALIZATION_EXCEPTION());
        to.setMCD_AUDIT(from.getMCD_AUDIT());
        to.setMCD_AUDIT_EXCEPTION(from.getMCD_AUDIT_EXCEPTION());
        to.setMCD_TREE_NAMING_ASSOCIATION(from.getMCD_TREE_NAMING_ASSOCIATION());
        to.setMCD_MODE_NAMING_LONG_NAME(from.getMCD_MODE_NAMING_LONG_NAME());
        to.setMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME(from.getMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME());
    }

    public void loadOrCreateFileApplicationPreferences() {
        try{
            PreferencesLoader loader = new PreferencesLoader();
            applicationPref = loader.load(new File(Preferences.FILE_APPLICATION_PREF_NAME));
        } catch (FileNotFoundException e) {
            applicationPref = new Preferences(null, null);
            PreferencesSaver saver = new PreferencesSaver();
            saver.save(new File(Preferences.FILE_APPLICATION_PREF_NAME), applicationPref);
        }
    }

    public void createProfile() {
        ProfileFileChooser fileChooser = new ProfileFileChooser(ProjectFileChooser.SAVE);
        File fileChoose = fileChooser.fileChoose();
        if (fileChoose != null){
            new PreferencesSaver().save(fileChoose, projectPref);
        }
    }

    // création du document Application
    public void createApplicationPref() {

        try {
            //Creation du document en memoire
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            //Création des éléments
            Element racine = (Element) document.createElement("PreferencesApplication");
            document.appendChild(racine);

            Element debug = document.createElement("DEBUG");
            debug.appendChild(document.createTextNode(applicationPref.isDEBUG().toString()));
            racine.appendChild(debug);

            Element debugBackgroudPanel = document.createElement("DEBUG_BACKGROUND_PANEL");
            debugBackgroudPanel.appendChild(document.createTextNode(applicationPref.isDEBUG_BACKGROUND_PANEL().toString()));
            racine.appendChild(debugBackgroudPanel);

            Element debugPrintMvccdElement = document.createElement("DEBUG_PRINT_MVCCDELEMENT");
            debugPrintMvccdElement.appendChild(document.createTextNode(applicationPref.isDEBUG_PRINT_MVCCDELEMENT().toString()));
            racine.appendChild(debugPrintMvccdElement);

            Element debugShowTableColHidden = document.createElement("DEBUG_SHOW_TABLE_COL_HIDDEN");
            debugShowTableColHidden.appendChild(document.createTextNode(applicationPref.isDEBUG_SHOW_TABLE_COL_HIDDEN().toString()));
            racine.appendChild(debugShowTableColHidden);

            Element debugInspectObjectInTree = document.createElement("DEBUG_INSPECT_OBJECT_IN_TREE");
            debugInspectObjectInTree.appendChild(document.createTextNode(applicationPref.getDEBUG_INSPECT_OBJECT_IN_TREE().toString()));
            racine.appendChild(debugInspectObjectInTree);

            Element repositoryMcdModelsMny  = document.createElement("REPOSITORY_MCD_MODELS_MANY");
            repositoryMcdModelsMny.appendChild(document.createTextNode(applicationPref.getREPOSITORY_MCD_MODELS_MANY().toString()));
            racine.appendChild(repositoryMcdModelsMny);

            Element repositoryMcdPackagesAuthorizeds  = document.createElement("REPOSITORY_MCD_PACKAGES_AUTHORIZEDS");
            repositoryMcdPackagesAuthorizeds.appendChild(document.createTextNode(applicationPref.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS().toString()));
            racine.appendChild(repositoryMcdPackagesAuthorizeds);

            // formatage du fichier
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            //Création du fichier
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("applicationPref.xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }

    public void loadApplicationPref() throws FileNotFoundException  {
        try {

            //Création du document en memoire
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("applicationPref.xml"));

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

        }catch (FileNotFoundException e) {
            throw (e);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadOrCreateFileXMLApplicationPref() {
        applicationPref = new Preferences(null, null);
        try{
            loadApplicationPref();
        } catch (FileNotFoundException e) {
            createApplicationPref();
        }
    }


    }
