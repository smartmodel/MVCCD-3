package project;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.Profile;
import stereotypes.Stereotype;
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
import java.io.FileOutputStream;
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

    public void createProjectFile(File file) {
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
            preferenceProject(document, racine);

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
            //StreamResult result = new StreamResult(new FileOutputStream(file));
            StreamResult result = new StreamResult(new File( "projectFile.xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public void preferenceProject(Document document, Element racine) {
        Element preferences = document.createElement("Preferences");
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


    private void AddModelAndChilds(Document doc, Element mcd, ArrayList<MVCCDElement> mcdChilds) {

        ArrayList<MVCCDElement> modelsChilds;

        for (int i = 0; i < mcdChilds.size(); i++) {
            MVCCDElement child = mcdChilds.get(i);
            Element model = doc.createElement(child.getName());
            mcd.appendChild(model);
            modelsChilds = child.getChilds();

            MCDModel modelChild = (MCDModel) child;

            if (modelChild.isPackagesAutorizeds()) {
                addPropertiesModelsOrPackages(doc, model, child);
                addEntities(doc, modelsChilds, model);
                addDiagrams(doc, modelsChilds, model);
                addRelations(doc, modelsChilds, model);
                addPackages(doc, child, model);

            } else {
                addPropertiesModelsOrPackages(doc, model, child);
                addEntities(doc, modelsChilds, model);
                addDiagrams(doc, modelsChilds, model);
                addRelations(doc, modelsChilds, model);
            }
        }

    }

    private void addPackages(Document doc, MVCCDElement listChilds, Element racine) {
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

    private ArrayList<MCDPackage> getPackages(MVCCDElement element) {
        ArrayList<MCDPackage> packages = new ArrayList<>();
        for (MVCCDElement mvccdElement : element.getChilds()) {
            if (mvccdElement instanceof MCDPackage) {
                packages.add((MCDPackage) mvccdElement);
            }
        }
        return packages;
    }

    private void addDiagrams(Document doc, ArrayList<MVCCDElement> listElement, Element racine) {

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

    private void addEntities(Document doc, ArrayList<MVCCDElement> listElement, Element racine) {

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

    private void addPropertiesProject(Document document, Element racine) {
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

    private void addPropertiesEntity(Document doc, Element entity, MCDEntity mcdEntity) {

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

    private void addPropertiesModelsOrPackages(Document doc, Element element, MVCCDElement child) {
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


    private void addAttributs(Document doc, Element entity, ArrayList<MVCCDElement> listElement) {
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement entitychild = listElement.get(i);

            if (entitychild.getName().equals(Preferences.REPOSITORY_MCD_ATTRIBUTES_NAME)) {
                Element attributs = doc.createElement(entitychild.getName());
                entity.appendChild(attributs);

                ArrayList<MVCCDElement> attributsChilds = entitychild.getChilds();

                addAttributsChilds(doc, attributsChilds, attributs);

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
            frozen.appendChild(doc.createTextNode(String.valueOf(childAttribut.isFrozen())));
            attribut.appendChild(frozen);

            Element ordered = doc.createElement("Ordered");
            ordered.appendChild(doc.createTextNode(String.valueOf(childAttribut.isOrdered())));
            attribut.appendChild(ordered);

            Element upperCase = doc.createElement("UpperCase");
            upperCase.appendChild(doc.createTextNode(String.valueOf(childAttribut.isUppercase())));
            attribut.appendChild(upperCase);

            Element dataTypeLienProg = doc.createElement("Data_Type_Lien_Prog");
            dataTypeLienProg.appendChild(doc.createTextNode(childAttribut.getDatatypeLienProg()));
            attribut.appendChild(dataTypeLienProg);

            Element scale = doc.createElement("Scale");
            scale.appendChild(doc.createTextNode(String.valueOf(childAttribut.getScale())));
            attribut.appendChild(scale);

            Element size = doc.createElement("Size");
            size.appendChild(doc.createTextNode(String.valueOf(childAttribut.getSize())));
            attribut.appendChild(size);

            Element initValue = doc.createElement("Init_Value");
            initValue.appendChild(doc.createTextNode(childAttribut.getInitValue()));
            attribut.appendChild(initValue);

            Element derivedValue = doc.createElement("Derived_Value");
            derivedValue.appendChild(doc.createTextNode(childAttribut.getDerivedValue()));
            attribut.appendChild(derivedValue);

            Element domain = doc.createElement("Domain");
            if (childAttribut.getDomain() != null) {
                domain.appendChild(doc.createTextNode(childAttribut.getDomain()));
            }
            attribut.appendChild(domain);

        }
    }

    private void addExtremites(Document doc, Element entity, ArrayList<MVCCDElement> listElement) {
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement entitychild = listElement.get(i);

            if (entitychild.getName().equals(Preferences.REPOSITORY_MCD_RELATIONS_ENDS_NAME)) {
                Element extremitesRelations = doc.createElement("Extrémités_de_relations");
                entity.appendChild(extremitesRelations);

                ArrayList<MVCCDElement> extremitesRelationsChilds = entitychild.getChilds();
                for (int j = 0; j < extremitesRelationsChilds.size(); j++) {
                    if (extremitesRelationsChilds.get(j) instanceof MCDAssEnd) {
                        MCDAssEnd childExtremite = (MCDAssEnd) extremitesRelationsChilds.get(j);

                        addExtremite(doc, extremitesRelations, childExtremite);
                    }
                }
            }
        }
    }

    private void addContraints(Document doc, Element entity, ArrayList<MVCCDElement> listElement) {
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement entitychild = listElement.get(i);

            if (entitychild.getName().equals(Preferences.REPOSITORY_MCD_CONSTRAINTS_NAME)) {
                Element contraintes = doc.createElement(entitychild.getName());
                entity.appendChild(contraintes);

                ArrayList<MVCCDElement> contraintsChilds = entitychild.getChilds();
                addContraintsChilds(doc, contraintsChilds, contraintes);
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

                addStereotype(doc, nid, constraint);

            } else if (childContraint instanceof MCDUnique) {
                MCDUnique unique = (MCDUnique) childContraint;
                Element absolute = doc.createElement("Absolute");
                absolute.appendChild(doc.createTextNode(String.valueOf(unique.isAbsolute())));
                constraint.appendChild(absolute);

                addStereotype(doc, unique, constraint);
            }
        }
    }

    private void addStereotype(Document doc, MCDConstraint mcdConstraint, Element constraint) {
        ArrayList<Stereotype> stereotypesChilds = mcdConstraint.getToStereotypes();
        for (int i = 0; i < stereotypesChilds.size(); i++) {
            Stereotype stereotypeChild = stereotypesChilds.get(i);
            Element sterotype = doc.createElement("Stereotype");
            constraint.appendChild(sterotype);

            Element nameStereotype = doc.createElement("Name");
            nameStereotype.appendChild(doc.createTextNode(stereotypeChild.getName()));
            sterotype.appendChild(nameStereotype);

            Element lienProgStereotype = doc.createElement("Lien_prog");
            lienProgStereotype.appendChild(doc.createTextNode(stereotypeChild.getLienProg()));
            sterotype.appendChild(lienProgStereotype);

            Element classTargetName = doc.createElement("Class_target_Name");
            classTargetName.appendChild(doc.createTextNode(stereotypeChild.getClassTargetName()));
            sterotype.appendChild(classTargetName);
        }

    }

    private void addRelations(Document doc, ArrayList<MVCCDElement> listElement, Element racine) {

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
        Element associations = doc.createElement("Associations");
        relations.appendChild(associations);

        Element generalisations = doc.createElement("Généralisations");
        relations.appendChild(generalisations);

        Element link = doc.createElement("Links");
        relations.appendChild(link);

        for (int i = 0; i < relationsChilds.size(); i++) {
            MCDRelation mcdRelation = (MCDRelation) relationsChilds.get(i);

            if (mcdRelation instanceof MCDAssociation) {
                MCDAssociation mcdAssociation = (MCDAssociation) mcdRelation;
                addassociations(doc, mcdAssociation, associations);
            }

            if (mcdRelation instanceof MCDGeneralization) {
                MCDGeneralization mcdGeneralization = (MCDGeneralization) mcdRelation;
                addGeneralization(doc, mcdGeneralization, generalisations);
            }

            if (mcdRelation instanceof MCDLink) {
                MCDLink mcdLink = (MCDLink) mcdRelation;
                addlink(doc, mcdLink, link);
            }
        }
    }

    private void addlink(Document doc, MCDLink mcdLink, Element link) {
        MCDLinkEnd endAssociation = mcdLink.getEndAssociation();
        Element association = doc.createElement("Association");
        association.appendChild(doc.createTextNode(endAssociation.getMcdElement().toString()));
        link.appendChild(association);


        MCDLinkEnd endEntity = mcdLink.getEndEntity();
        Element entity = doc.createElement("Entity");
        entity.appendChild(doc.createTextNode(endEntity.getNamePath(1)));
        link.appendChild(entity);

    }

    private void addGeneralization(Document doc, MCDGeneralization mcdGeneralization, Element generalisations) {
        MCDGSEnd gen = mcdGeneralization.getGen();
        MCDEntity genEntity = gen.getMcdEntity();
        Element entityGen = doc.createElement("Gen_Entité");
        entityGen.appendChild(doc.createTextNode(genEntity.getNamePath(1)));
        generalisations.appendChild(entityGen);


        MCDGSEnd spec = mcdGeneralization.getSpec();
        MCDEntity specEntity = spec.getMcdEntity();
        Element entitySpec = doc.createElement("Spec_Entité");
        entitySpec.appendChild(doc.createTextNode(specEntity.getNamePath(1)));
        generalisations.appendChild(entitySpec);

    }

    private void addassociations(Document doc, MCDAssociation mcdAssociation, Element associations) {

        MCDAssEnd extremiteFrom = mcdAssociation.getFrom();
        MCDAssEnd extremiteTo = mcdAssociation.getTo();

        Element association = doc.createElement("Association");
        if (mcdAssociation.getName().equals("")) {


            addPropertiesAssociation(doc, association, mcdAssociation);
            addExtremite(doc, association, extremiteFrom);
            addExtremite(doc, association, extremiteTo);


        } else {
            association = doc.createElement(mcdAssociation.getName());
            addPropertiesAssociation(doc, association, mcdAssociation);
            addExtremite(doc, association, extremiteFrom);
            addExtremite(doc, association, extremiteTo);

        }
        associations.appendChild(association);
    }


    private void addPropertiesAssociation(Document doc, Element association, MCDAssociation mcdAssociation) {

        Element properties = doc.createElement("Propriétés");
        association.appendChild(properties);

        Element nature = doc.createElement("Nature");
        nature.appendChild(doc.createTextNode(mcdAssociation.getNature().getName()));
        properties.appendChild(nature);

        Element oriented = doc.createElement("Oriented");
        oriented.appendChild(doc.createTextNode(String.valueOf(mcdAssociation.getOriented())));
        properties.appendChild(oriented);

        Element deleteCascade = doc.createElement("Delete_cascade");
        deleteCascade.appendChild(doc.createTextNode(String.valueOf(mcdAssociation.isDeleteCascade())));
        properties.appendChild(deleteCascade);

        Element frozen = doc.createElement("Frozen");
        frozen.appendChild(doc.createTextNode(String.valueOf(mcdAssociation.isFrozen())));
        properties.appendChild(frozen);

    }

    private void addExtremite(Document doc, Element association, MCDAssEnd extremite) {

        Element roleExtremite = doc.createElement("Role_extremité_From");

        if (extremite.getDrawingDirection() == 2) {

            roleExtremite = doc.createElement("Role_extremité_To");
        }

        association.appendChild(roleExtremite);

        Element nameRole = doc.createElement("Name");
        nameRole.appendChild(doc.createTextNode(extremite.getName()));
        roleExtremite.appendChild(nameRole);

        Element shortNameRole = doc.createElement("Short_name");
        shortNameRole.appendChild(doc.createTextNode(extremite.getShortName()));
        roleExtremite.appendChild(shortNameRole);

        Element drawingDirection = doc.createElement("Drawing_direction");
        drawingDirection.appendChild(doc.createTextNode(String.valueOf(extremite.getDrawingDirection())));
        roleExtremite.appendChild(drawingDirection);

        Element multiplicity = doc.createElement("Multiplicity");
        multiplicity.appendChild(doc.createTextNode(extremite.getMultiStr()));
        roleExtremite.appendChild((multiplicity));

        Element orderedRole = doc.createElement("Ordered");
        orderedRole.appendChild(doc.createTextNode(String.valueOf(extremite.isOrdered())));
        roleExtremite.appendChild(orderedRole);

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




