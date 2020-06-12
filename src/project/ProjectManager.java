package project;

import datatypes.MCDDatatype;
import datatypes.MDDatatypesManager;
import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDElement;
import mcd.MCDEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import preferences.Preferences;
import preferences.PreferencesLoader;
import preferences.PreferencesManager;
import utilities.files.UtilFiles;
import utilities.window.DialogMessage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ProjectManager {

    private static ProjectManager instance;
    private static Project project;

    public static synchronized ProjectManager instance() {
        if (instance == null) {
            instance = new ProjectManager();
        }
        project = MVCCDManager.instance().getProject();
        return instance;
    }

    public void createProjectFile() {
        // initialisation de variable
        Boolean modelsApp = PreferencesManager.instance().getApplicationPref().getREPOSITORY_MCD_MODELS_MANY();
        Boolean modelsProj = project.isModelsMany();
        Boolean packApp = PreferencesManager.instance().getApplicationPref().getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS();
        Boolean packProj = project.isPackagesAutorizeds();

        //traitement
        try {
            //Creation du document en memoire
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            //Création des éléments
            Element racine = (Element) document.createElement("Project");
            document.appendChild(racine);

            //properties
            Element properties = document.createElement("Proprietes");
            racine.appendChild(properties);

            if (project.getProfileFileName() != null) {
                Element profileFileName = document.createElement("profileFileName");
                profileFileName.appendChild(document.createTextNode(project.getProfileFileName()));
                properties.appendChild(profileFileName);

                Element profile = document.createElement("profile");
                profile.appendChild(document.createTextNode(project.getProfile().getName()));
                properties.appendChild(profile);
            }
            // à ajouter tout les attributs du profile

            Element modelsMany = document.createElement("modelsMany");
            modelsMany.appendChild(document.createTextNode(Boolean.toString(project.isModelsMany())));
            properties.appendChild(modelsMany);

            Element packagesAutorizeds = document.createElement("packagesAutorizeds");
            packagesAutorizeds.appendChild(document.createTextNode(Boolean.toString(project.isPackagesAutorizeds())));
            properties.appendChild(packagesAutorizeds);

            //Preferences Project
            Element preferences = document.createElement("Prefereces");
            racine.appendChild(preferences);

            Element mcdJournalization = document.createElement("MCD_JOURNALIZATION");
            preferences.appendChild(document.createTextNode(project.getPreferences().getMCD_JOURNALIZATION().toString()));preferences.appendChild(mcdJournalization);

            Element mcdJournalizationException = document.createElement("MCD_JOURNALIZATION_EXCEPTION");
            mcdJournalizationException.appendChild(document.createTextNode(project.getPreferences().getMCD_JOURNALIZATION_EXCEPTION().toString()));
            preferences.appendChild(mcdJournalizationException);

            Element mcdAudit = document.createElement("MCD_AUDIT");
            mcdAudit.appendChild(document.createTextNode(project.getPreferences().getMCD_AUDIT().toString()));
            preferences.appendChild(mcdAudit);

            Element mcdAuditException = document.createElement("MCD_AUDIT_EXCEPTION");
            mcdAuditException.appendChild(document.createTextNode(project.getPreferences().getMCD_AUDIT_EXCEPTION().toString()));
            preferences.appendChild(mcdAuditException);

            Element mcdAidDataTypeLienProg = document.createElement("MCD_AID_DATATYPE_LIENPROG");
            mcdAidDataTypeLienProg.appendChild(document.createTextNode(project.getPreferences().getMCD_AID_DATATYPE_LIENPROG()));
            preferences.appendChild(mcdAidDataTypeLienProg);

            Element mcdDataTypeNumberSizemode = document.createElement("MCD_DATATYPE_NUMBER_SIZE_MODE");
            mcdDataTypeNumberSizemode.appendChild(document.createTextNode(project.getPreferences().getMCDDATATYPE_NUMBER_SIZE_MODE()));
            preferences.appendChild(mcdDataTypeNumberSizemode);

            Element mcdAidIndColumnName = document.createElement("MCD_AID_IND_COLUMN_NAME");
            mcdAidIndColumnName.appendChild(document.createTextNode(project.getPreferences().getMCD_AID_IND_COLUMN_NAME()));
            preferences.appendChild(mcdAidIndColumnName);

            Element mcdAidDepColumnName = document.createElement("MCD_AID_DEP_COLUMN_NAME");
            mcdAidDepColumnName.appendChild(document.createTextNode(project.getPreferences().getMCD_AID_DEP_COLUMN_NAME()));
            preferences.appendChild(mcdAidDepColumnName);

            Element mcdAidWithDep = document.createElement("MCD_AID_WITH_DEP");
            mcdAidWithDep.appendChild(document.createTextNode(project.getPreferences().isMCD_AID_WITH_DEP().toString()));
            preferences.appendChild(mcdAidWithDep);

            Element mcdTreeNamingAssociation = document.createElement("MCD_TREE_NAMING_ASSOCIATION");
            mcdTreeNamingAssociation.appendChild(document.createTextNode(project.getPreferences().getMCD_TREE_NAMING_ASSOCIATION()));
            preferences.appendChild(mcdTreeNamingAssociation);

            Element mcdModeNamingLongName = document.createElement("MCD_MODE_NAMING_LONG_NAME");
            mcdModeNamingLongName.appendChild(document.createTextNode(project.getPreferences().getMCD_MODE_NAMING_LONG_NAME()));
            preferences.appendChild(mcdModeNamingLongName);

            Element mcdModeNamingAttributeShortName = document.createElement("MCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME");
            mcdModeNamingAttributeShortName.appendChild(document.createTextNode(project.getPreferences().getMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME()));
            preferences.appendChild(mcdModeNamingAttributeShortName);

            // Element MCD
            Element mcd = document.createElement("MCD");
            racine.appendChild(mcd);

            Element diagrams = document.createElement("Diagrams");
            mcd.appendChild(diagrams);

            Element diagram = document.createElement("Diagram");
            diagrams.appendChild(diagram);

            Element entities = document.createElement("Entites");
            mcd.appendChild(entities);

            Element entity = document.createElement("Entite");
            entities.appendChild(entity);

            Element relations = document.createElement("Relations");
            mcd.appendChild(relations);

            Element relation = document.createElement("Relation");
            relations.appendChild(relation);

            Element models = document.createElement("Modeles");

            Element model = document.createElement("Modele");

            if (modelsApp.equals(Boolean.TRUE) && modelsProj.equals(Boolean.TRUE)) {
                mcd.appendChild(models);
                models.appendChild(model);
                model.appendChild(diagrams);
                model.appendChild(entities);
                model.appendChild(relations);
            }

            Element paquetages = document.createElement("Paquetages");

            if (packApp.equals(Boolean.TRUE) && packProj.equals(Boolean.TRUE)) {
                mcd.appendChild(paquetages);
                if (modelsApp.equals(Boolean.TRUE) && modelsProj.equals(Boolean.TRUE)) {
                    model.appendChild(paquetages);
                }
                Element paquetage = document.createElement("Paquetage");
                paquetages.appendChild(paquetage);

                Element packDiagrams = document.createElement("PackDiagrams");
                paquetage.appendChild(packDiagrams);

                Element packDiagram = document.createElement("PackDiagram");
                packDiagrams.appendChild(packDiagram);

                Element packEntities = document.createElement("PackEntites");
                paquetage.appendChild(packEntities);

                Element packEntity = document.createElement("PackEntite");
                packEntities.appendChild(packEntity);

                Element packRelations = document.createElement("PackRelations");
                paquetage.appendChild(packRelations);

                Element packRelation = document.createElement("PackRelation");
                packRelations.appendChild(packRelation);
            }

             //formatage du fichier
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            //Création du fichier
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("test.xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        }
    }


    /*
    public  ProjectElement getElementById (int id) {
        return getElementById(project, id);
    }

    public  ProjectElement getElementById (ProjectElement projectElement, int id) {
        ProjectElement resultat = null;
        for (MVCCDElement mvccdElement : projectElement.getChilds()) {
            if (mvccdElement instanceof ProjectElement) {
                ProjectElement child = (ProjectElement) mvccdElement;
                if (child.getId() == id) {
                    resultat = child;
                } else {
                    if (resultat == null) {
                        resultat = getElementById(child, id);
                    }
                }
            }
        }
        return resultat;
    }

     */

    /*
    private ArrayList<ProjectElement> getProjectElementsByClassName(ProjectElement racine,
                                                                    String className){
        ArrayList<ProjectElement> resultat = new ArrayList<ProjectElement>();
        for (MVCCDElement element :  racine.getChilds()){
            if (element instanceof ProjectElement){

                if (element.getClass().getName().equals(className)){
                    resultat.add((ProjectElement) element);
                }
                resultat.addAll(getProjectElementsByClassName((ProjectElement) element, className));
            }
        }
        return resultat;

    }
*/
    /*
    public ArrayList<MCDEntity> getEntities(){
        ArrayList<MCDEntity> resultat =  new ArrayList<MCDEntity>();
        for (ProjectElement element :  getProjectElementsByClassName(project, MCDEntity.class.getName())){
            resultat.add((MCDEntity) element);
        }
        return resultat;
    }

    public MCDEntity getMCDEntityByName(String name){
        for (MCDEntity mcdEntity: getEntities()){
            if (mcdEntity.getName().equals(name)){
                return mcdEntity;
            }
        }
        return null;
    }
*/




