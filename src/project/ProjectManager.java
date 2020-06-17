package project;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import preferences.Preferences;
import preferences.PreferencesManager;

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
            Element racine = document.createElement("Project");
            document.appendChild(racine);

            //properties
            addPropertiesProject(document, racine);

            //Preferences Project
            addPreferenceProject(document, racine);

            // Element MCD
            MVCCDElement elementMcd = project.getChilds().get(1);
            Element mcd = document.createElement(elementMcd.getName());
            racine.appendChild(mcd);

            ArrayList<MVCCDElement> mcdChilds = elementMcd.getChilds();

            //Modele
            if (modelsApp.equals(Boolean.TRUE) && modelsProj.equals(Boolean.TRUE)) {

                AddModelAndChilds(document, mcd, mcdChilds);

                //Package
            } else if (packApp.equals(Boolean.TRUE) && packProj.equals(Boolean.TRUE)) {

                addDiagrams(document, mcdChilds, mcd);
                addEntities(document, mcdChilds, mcd);
                addRelations(document, mcdChilds, mcd);
                addPackages(document, elementMcd, mcd);

                //projet simple
            } else {

                addDiagrams(document, mcdChilds, mcd);
                addEntities(document, mcdChilds, mcd);
                addRelations(document, mcdChilds, mcd);

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

    public void addPreferenceProject(Document document, Element racine) {
        Element preferences = document.createElement("Prefereces");
        racine.appendChild(preferences);

        Element mcdJournalization = document.createElement("Mcd_journalization");
        mcdJournalization.appendChild(document.createTextNode(project.getPreferences().getMCD_JOURNALIZATION().toString()));
        preferences.appendChild(mcdJournalization);

        Element mcdJournalizationException = document.createElement("Mcd_Journalization_exception");
        mcdJournalizationException.appendChild(document.createTextNode(project.getPreferences().getMCD_JOURNALIZATION_EXCEPTION().toString()));
        preferences.appendChild(mcdJournalizationException);

        Element mcdAudit = document.createElement("Mcd_audit");
        mcdAudit.appendChild(document.createTextNode(project.getPreferences().getMCD_AUDIT().toString()));
        preferences.appendChild(mcdAudit);

        Element mcdAuditException = document.createElement("Mcd_audit_Exception");
        mcdAuditException.appendChild(document.createTextNode(project.getPreferences().getMCD_AUDIT_EXCEPTION().toString()));
        preferences.appendChild(mcdAuditException);

        Element mcdAidDataTypeLienProg = document.createElement("Mcd_aid_data_type_lien_prog");
        mcdAidDataTypeLienProg.appendChild(document.createTextNode(project.getPreferences().getMCD_AID_DATATYPE_LIENPROG()));
        preferences.appendChild(mcdAidDataTypeLienProg);

        Element mcdDataTypeNumberSizemode = document.createElement("Mcd_data_type_number_size_mode");
        mcdDataTypeNumberSizemode.appendChild(document.createTextNode(project.getPreferences().getMCDDATATYPE_NUMBER_SIZE_MODE()));
        preferences.appendChild(mcdDataTypeNumberSizemode);

        Element mcdAidIndColumnName = document.createElement("Mcd_aid_ind_column_name");
        mcdAidIndColumnName.appendChild(document.createTextNode(project.getPreferences().getMCD_AID_IND_COLUMN_NAME()));
        preferences.appendChild(mcdAidIndColumnName);

        Element mcdAidDepColumnName = document.createElement("Mcd_aid_dep_column_name");
        mcdAidDepColumnName.appendChild(document.createTextNode(project.getPreferences().getMCD_AID_DEP_COLUMN_NAME()));
        preferences.appendChild(mcdAidDepColumnName);

        Element mcdAidWithDep = document.createElement("Mcd_aid_with_dep");
        mcdAidWithDep.appendChild(document.createTextNode(project.getPreferences().isMCD_AID_WITH_DEP().toString()));
        preferences.appendChild(mcdAidWithDep);

        Element mcdTreeNamingAssociation = document.createElement("Mcd_tree_naming_association");
        mcdTreeNamingAssociation.appendChild(document.createTextNode(project.getPreferences().getMCD_TREE_NAMING_ASSOCIATION()));
        preferences.appendChild(mcdTreeNamingAssociation);

        Element mcdModeNamingLongName = document.createElement("Mcd_mode_naming_long_name");
        mcdModeNamingLongName.appendChild(document.createTextNode(project.getPreferences().getMCD_MODE_NAMING_LONG_NAME()));
        preferences.appendChild(mcdModeNamingLongName);

        Element mcdModeNamingAttributeShortName = document.createElement("Mcd_mode_naming_attribute_short_name");
        mcdModeNamingAttributeShortName.appendChild(document.createTextNode(project.getPreferences().getMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME()));
        preferences.appendChild(mcdModeNamingAttributeShortName);
    }


    public void AddModelAndChilds(Document doc, Element mcd, ArrayList<MVCCDElement> mcdChilds) {

        ArrayList<MVCCDElement> modelsChilds;

        for (int i = 0; i < mcdChilds.size(); i++) {
            MVCCDElement child = mcdChilds.get(i);
            Element modele = doc.createElement(child.getName());
            mcd.appendChild(modele);
            modelsChilds = child.getChilds();

            MCDModel modelChild = (MCDModel) child;

            if (modelChild.isPackagesAutorizeds()) {
                addPropertiesModelsOrPackages(doc, modele, child);
                addEntities(doc, modelsChilds, modele);
                addDiagrams(doc, modelsChilds, modele);
                addRelations(doc, modelsChilds, modele);
                addPackages(doc, child, modele);

            } else {
                addPropertiesModelsOrPackages(doc, modele, child);
                addEntities(doc, modelsChilds, modele);
                addDiagrams(doc, modelsChilds, modele);
                addRelations(doc, modelsChilds, modele);
            }
        }

    }

    public void addPackages(Document doc, MVCCDElement listChilds, Element racine) {
        ArrayList<MCDPackage> packagesChilds = getPackages(listChilds);

        for (int i = 0; i < packagesChilds.size(); i++) {
            MVCCDElement pack = packagesChilds.get(i);

            ArrayList<MVCCDElement> packageChilds = pack.getChilds();

            if (!pack.getName().isEmpty()) {
                Element packages = doc.createElement(pack.getName());
                addPropertiesModelsOrPackages(doc, packages, pack);
                addDiagrams(doc, packageChilds, packages);
                addEntities(doc, packageChilds, packages);
                addRelations(doc, packageChilds, packages);
                racine.appendChild(packages);
            }
        }

    }

    public ArrayList<MCDPackage> getPackages(MVCCDElement element) {
        ArrayList<MCDPackage> packages = new ArrayList<>();
        for (MVCCDElement mvccdElement : element.getChilds()) {
            if (mvccdElement instanceof MCDPackage) {
                packages.add((MCDPackage) mvccdElement);
            }
        }
        return packages;
    }

    public void addDiagrams(Document doc, ArrayList<MVCCDElement> listElement, Element racine) {

        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement childElement = listElement.get(i);

            if (childElement.getName().equals(Preferences.REPOSITORY_MCD_DIAGRAMS_NAME)) {
                Element diagrams = doc.createElement(childElement.getName());
                racine.appendChild(diagrams);

                ArrayList<MVCCDElement> diagramsChilds = childElement.getChilds();

                for (int j = 0; j < diagramsChilds.size(); j++) {
                    MVCCDElement childDiagram = diagramsChilds.get(j);
                    String nameDiagram = childDiagram.getName();

                    Element diagram = doc.createElement(nameDiagram);

                    diagrams.appendChild(diagram);

                }

            }
        }
    }

    public void addEntities(Document doc, ArrayList<MVCCDElement> listElement, Element racine) {

        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement childElement = listElement.get(i);
            String nameModel = childElement.getName();


            if (nameModel.equals(Preferences.REPOSITORY_MCD_ENTITIES_NAME)) {
                Element entities = doc.createElement(nameModel);
                racine.appendChild(entities);


                ArrayList<MVCCDElement> entitiesChilds = childElement.getChilds();
                for (int j = 0; j < entitiesChilds.size(); j++) {
                    MCDEntity childEntity = (MCDEntity) entitiesChilds.get(j);
                    Element entity = doc.createElement(childEntity.getName());
                    entities.appendChild(entity);
                    ArrayList<MVCCDElement> entityChilds = childEntity.getChilds();

                    addPropertiesEntity(doc, entity, childEntity);
                    addAttributs(doc, entity, entityChilds);
                    addExtremites(doc, entity, entityChilds);
                    addContraints(doc, entity, entityChilds);

                }
            }
        }
    }

    public void addPropertiesProject(Document document, Element racine) {
        Element properties = document.createElement("Propriétés");
        racine.appendChild(properties);

        Element name = document.createElement("Name");
        name.appendChild(document.createTextNode(project.getName()));
        properties.appendChild(name);

        Element profileFileName = document.createElement("Profile_file_name");
        properties.appendChild(profileFileName);
        Element profile = document.createElement("Profile");
        properties.appendChild(profile);

        if (project.getProfileFileName() != null) {
            profileFileName.appendChild(document.createTextNode(project.getProfileFileName()));
            profile.appendChild(document.createTextNode(project.getProfile().getName()));
        }
        // à ajouter tout les attributs du profile

        Element modelsMany = document.createElement("Models_many");
        modelsMany.appendChild(document.createTextNode(Boolean.toString(project.isModelsMany())));
        properties.appendChild(modelsMany);

        Element packagesAutorizeds = document.createElement("Packages_autorizeds");
        packagesAutorizeds.appendChild(document.createTextNode(Boolean.toString(project.isPackagesAutorizeds())));
        properties.appendChild(packagesAutorizeds);
    }

    public void addPropertiesEntity(Document doc, Element entity, MCDEntity mcdEntity) {

        Element properties = doc.createElement("Propriétés");
        entity.appendChild(properties);

        Element shortName = doc.createElement("Short_name");
        shortName.appendChild(doc.createTextNode(mcdEntity.getShortName()));
        properties.appendChild(shortName);

        Element ordered = doc.createElement("Ordered");
        ordered.appendChild(doc.createTextNode(String.valueOf(mcdEntity.isOrdered())));
        properties.appendChild(ordered);

        Element entAbstract = doc.createElement("Ent_abstract");
        entAbstract.appendChild(doc.createTextNode(String.valueOf(mcdEntity.isEntAbstract())));
        properties.appendChild(entAbstract);

        Element journal = doc.createElement("Journal");
        journal.appendChild(doc.createTextNode(String.valueOf(mcdEntity.isJournal())));
        properties.appendChild(journal);

        Element audit = doc.createElement("Audit");
        audit.appendChild(doc.createTextNode(String.valueOf(mcdEntity.isAudit())));
    }

    public void addPropertiesModelsOrPackages(Document doc, Element element, MVCCDElement child) {
        Element properties = doc.createElement("Propriétés");
        element.appendChild(properties);

        Element shortName = doc.createElement("Short_name");
        properties.appendChild(shortName);

        Element audit = doc.createElement("Audit");
        properties.appendChild(audit);

        Element auditException = doc.createElement("Audit_exception");
        properties.appendChild(auditException);

        Element journalization = doc.createElement("Journalization");
        properties.appendChild(journalization);

        Element journalizationException = doc.createElement("Journalization_exception");
        properties.appendChild(journalizationException);

        if (child instanceof MCDModel) {
            MCDModel mcdModel = (MCDModel) child;

            Element packagesAutorized = doc.createElement("Packages_autorizeds");
            packagesAutorized.appendChild(doc.createTextNode(String.valueOf(mcdModel.isPackagesAutorizeds())));
            properties.appendChild(packagesAutorized);

            shortName.appendChild(doc.createTextNode(mcdModel.getShortName()));
            audit.appendChild(doc.createTextNode(String.valueOf(mcdModel.isMcdAudit())));
            auditException.appendChild(doc.createTextNode(String.valueOf(mcdModel.isMcdAuditException())));
            journalization.appendChild(doc.createTextNode(String.valueOf(mcdModel.isMcdJournalization())));
            journalizationException.appendChild(doc.createTextNode(String.valueOf(mcdModel.isMcdJournalizationException())));

        } else {
            MCDPackage mcdPackage = (MCDPackage) child;

            shortName.appendChild(doc.createTextNode(mcdPackage.getShortName()));
            audit.appendChild(doc.createTextNode(String.valueOf(mcdPackage.isMcdAudit())));
            auditException.appendChild(doc.createTextNode(String.valueOf(mcdPackage.isMcdAuditException())));
            journalization.appendChild(doc.createTextNode(String.valueOf(mcdPackage.isMcdJournalization())));
            journalizationException.appendChild(doc.createTextNode(String.valueOf(mcdPackage.isMcdJournalizationException())));
        }
    }


    public void addAttributs(Document doc, Element entity, ArrayList<MVCCDElement> listElement) {
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement entitychild = listElement.get(i);

            if (entitychild.getName().equals(Preferences.REPOSITORY_MCD_ATTRIBUTES_NAME)) {
                Element attributs = doc.createElement(entitychild.getName());
                entity.appendChild(attributs);

                ArrayList<MVCCDElement> attributsChilds = entitychild.getChilds();

                addAttributsChilds(doc,attributsChilds,attributs);

            }
        }
    }

    private void addAttributsChilds(Document doc, ArrayList<MVCCDElement> attributsChilds, Element attributs) {
        for (int i = 0; i < attributsChilds.size(); i++) {
            MCDAttribute childAttribut = (MCDAttribute) attributsChilds.get(i);

            Element attribut = doc.createElement(childAttribut.getName());
            attributs.appendChild(attribut);

            Element aid = doc.createElement("Aid");
            aid.appendChild(doc.createTextNode(String.valueOf(childAttribut.isAid())));
            attribut.appendChild(aid);

            Element aidDep = doc.createElement("AidDep");
            aidDep.appendChild(doc.createTextNode(String.valueOf(childAttribut.isAidDep())));
            attribut.appendChild(aidDep);

            Element mandatory = doc.createElement("Mandatory");
            mandatory.appendChild(doc.createTextNode(String.valueOf(childAttribut.isMandatory())));
            attribut.appendChild(mandatory);

            Element list = doc.createElement("List");
            list.appendChild(doc.createTextNode(String.valueOf(childAttribut.isList())));
            attribut.appendChild(list);

            Element frozen = doc.createElement("Frozen");
            frozen.appendChild(doc.createTextNode(String.valueOf( childAttribut.isFrozen())));
            attribut.appendChild(frozen);

            Element ordered = doc.createElement("Ordered");
            ordered.appendChild(doc.createTextNode(String.valueOf( childAttribut.isOrdered())));
            attribut.appendChild(ordered);

            Element upperCase = doc.createElement("UpperCase");
            upperCase.appendChild(doc.createTextNode(String.valueOf(childAttribut.isUppercase())));
            attribut.appendChild(upperCase);

            Element dataTypeLienProg = doc.createElement("Data_Type_Lien_Prog");
            if (childAttribut.getDatatypeLienProg() != null) {
                dataTypeLienProg.appendChild(doc.createTextNode(childAttribut.getDatatypeLienProg()));
            }
            attribut.appendChild(dataTypeLienProg);

            Element scale = doc.createElement("Scale");
            if (childAttribut.getScale() != null) {
                scale.appendChild(doc.createTextNode(String.valueOf(childAttribut.getScale())));
            }
            attribut.appendChild(scale);

            Element size = doc.createElement("Size");
            if (childAttribut.getSize() != null) {
                size.appendChild(doc.createTextNode(String.valueOf(childAttribut.getSize())));
            }
            attribut.appendChild(size);

            Element initValue = doc.createElement("Init_Value");
            if (childAttribut.getSize() != null) {
                initValue.appendChild(doc.createTextNode(childAttribut.getInitValue()));
            }
            attribut.appendChild(initValue);

            Element derivedValue = doc.createElement("Derived_Value");
            if (childAttribut.getDerivedValue() != null) {
                derivedValue.appendChild(doc.createTextNode(childAttribut.getDerivedValue()));
            }
            attribut.appendChild(derivedValue);

            Element domain = doc.createElement("Domain");
            if (childAttribut.getDomain() != null) {
                domain.appendChild(doc.createTextNode(childAttribut.getDomain()));
            }
            attribut.appendChild(domain);

        }
    }

    public void addExtremites(Document doc, Element entity, ArrayList<MVCCDElement> listElement) {
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement entitychild = listElement.get(i);

            if (entitychild.getName().equals(Preferences.REPOSITORY_MCD_RELATIONS_ENDS_NAME)) {
                Element extremiteRelations = doc.createElement("Extrémités_de_relations");
                entity.appendChild(extremiteRelations);
            }
        }
    }

    public void addContraints(Document doc, Element entity, ArrayList<MVCCDElement> listElement) {
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement entitychild = listElement.get(i);

            if (entitychild.getName().equals(Preferences.REPOSITORY_MCD_CONSTRAINTS_NAME)) {
                Element contraintes = doc.createElement(entitychild.getName());
                entity.appendChild(contraintes);

                ArrayList<MVCCDElement> contraintsChilds = entitychild.getChilds();
                addContraintsChilds(doc,contraintsChilds, contraintes);
            }
        }
    }

    private void addContraintsChilds(Document doc, ArrayList<MVCCDElement> contraintsChilds, Element contraintes) {
        for (int i = 0; i < contraintsChilds.size(); i++) {
            MCDConstraint childContraint = (MCDConstraint) contraintsChilds.get(i);
            Element constraint = doc.createElement(childContraint.getName());
            contraintes.appendChild(constraint);

            Element shortName = doc.createElement("Short_name");
            shortName.appendChild(doc.createTextNode(childContraint.getShortName()));
            constraint.appendChild(shortName);

            if (childContraint instanceof MCDNID) {
                MCDNID nid = (MCDNID) childContraint;
                Element lienProg = doc.createElement("Lien_prog");
                lienProg.appendChild(doc.createTextNode(String.valueOf(nid.isLienProg())));
                constraint.appendChild(lienProg);
            } else if(childContraint instanceof MCDUnique) {
                MCDUnique unique = (MCDUnique) childContraint;
                Element absolute = doc.createElement("Absolute");
                absolute.appendChild(doc.createTextNode(String.valueOf(unique.isAbsolute())));
                constraint.appendChild(absolute);
            }
        }
    }

    public void addRelations(Document doc, ArrayList<MVCCDElement> listElement, Element racine) {

        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement childElement = listElement.get(i);

            if (childElement.getName().equals(Preferences.REPOSITORY_MCD_RELATIONS_NAME)) {
                Element relations = doc.createElement(childElement.getName());
                racine.appendChild(relations);

                ArrayList<MVCCDElement> relationsChilds = childElement.getChilds();

                addRelationsChilds(doc, relationsChilds, relations);

            }
        }
    }

    private void addRelationsChilds(Document doc, ArrayList<MVCCDElement> relationsChilds, Element relations) {
            for (int j = 0; j < relationsChilds.size(); j++) {
                MCDRelation childRelation = (MCDRelation) relationsChilds.get(j);
                MCDAssEnd extremiteA = (MCDAssEnd) childRelation.getA();
                MCDAssEnd extremiteB = (MCDAssEnd) childRelation.getB();

                if (childRelation.getName().equals("")) {

                    Element relation = doc.createElement("Relation");
                    Element roleExtremiteA = doc.createElement("ExtremitéA");
                    Element roleExtremiteB = doc.createElement("ExtremitéB");


                    Element multiplicityA = doc.createElement("MultiplicityA");
                    multiplicityA.appendChild(doc.createTextNode(extremiteA.getMultiStr()));
                    Element multiplicityB = doc.createElement("MultiplicityB");
                    multiplicityB.appendChild(doc.createTextNode(extremiteB.getMultiStr()));

                    roleExtremiteA.appendChild(doc.createTextNode(extremiteA.getName()));
                    roleExtremiteB.appendChild(doc.createTextNode(extremiteB.getName()));

                    relation.appendChild(roleExtremiteA);
                    relation.appendChild(roleExtremiteB);
                    relation.appendChild((multiplicityA));
                    relation.appendChild((multiplicityB));
                    relations.appendChild(relation);
                } else {
                    Element relation = doc.createElement(childRelation.getName());

                    Element multiplicityA = doc.createElement("multiplicityA");
                    multiplicityA.appendChild(doc.createTextNode(extremiteA.getMultiStr()));
                    Element multiplicityB = doc.createElement("multiplicityB");
                    multiplicityB.appendChild(doc.createTextNode(extremiteB.getMultiStr()));

                    relation.appendChild((multiplicityA));
                    relation.appendChild((multiplicityB));

                    relations.appendChild(relation);
                }
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




