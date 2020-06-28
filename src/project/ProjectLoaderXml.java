package project;

import main.*;
import mcd.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import preferences.Preferences;
import profile.Profile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectLoaderXml {

    public Project loadProjectFile() {
        // création du projet et initialisation des préférences du projet
        Project project = new Project(null);
        Preferences preferences = MVCCDElementFactory.instance().createPreferences(project, Preferences.REPOSITORY_PREFERENCES_NAME);

        try {
            // Création du document et du parseur pour récupérer les information du fichier
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File("projectFile." + Preferences.FILE_PROJECT_EXTENSION));

            //MVCCDManager.instance().getProject().getName() + Preferences.FILE_DOT + Preferences.FILE_PROJECT_EXTENSION

            // Récupération de la racine du fichier
            Element racine = document.getDocumentElement();

            // Récupération du nom du projet
            Element name = (Element) racine.getElementsByTagName("nameProject").item(0);
            project.setName(name.getTextContent());

            // Ajout des éléments du projet
            addPropertiesProject(racine, project);
            addPreferences(racine, preferences);
            addMcdAndChild(racine, project);


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return project;
    }

    private void addPropertiesProject(Element racine, Project project) {

        // récuperation et création du profil du projet
        Element profileFileName = (Element) racine.getElementsByTagName("profileFileName").item(0);
        if (!profileFileName.getTextContent().equals("")) {
            project.setProfileFileName(profileFileName.getTextContent());

            Profile profile = MVCCDFactory.instance().createProfile(project.getProfileFileName());
            project.setProfile(profile);
        }

        Element modelsMany = (Element) racine.getElementsByTagName("modelsMany").item(0);
        project.setModelsMany(Boolean.valueOf(modelsMany.getTextContent()));

        Element packagesAutorizeds = (Element) racine.getElementsByTagName("packagesAutorizeds").item(0);
        project.setPackagesAutorizeds(Boolean.valueOf(packagesAutorizeds.getTextContent()));
    }

    public void addPreferences(Element racine, Preferences preferences) {

        //Récuperation et instaciation des préférences de projet

        Element mcdJournalization = (Element) racine.getElementsByTagName("mcdJournalization").item(0);
        preferences.setMCD_JOURNALIZATION(Boolean.valueOf(mcdJournalization.getTextContent()));

        Element mcdJournalizationException = (Element) racine.getElementsByTagName("mcdJournalizationException").item(0);
        preferences.setMCD_JOURNALIZATION_EXCEPTION(Boolean.valueOf(mcdJournalizationException.getTextContent()));

        Element mcdAudit = (Element) racine.getElementsByTagName("mcdAudit").item(0);
        preferences.setMCD_AUDIT(Boolean.valueOf(mcdAudit.getTextContent()));

        Element mcdAuditException = (Element) racine.getElementsByTagName("mcdAuditException").item(0);
        preferences.setMCD_AUDIT_EXCEPTION(Boolean.valueOf(mcdAuditException.getTextContent()));

        Element mcdAidDataTypeLienProg = (Element) racine.getElementsByTagName("mcdAidDataTypeLienProg").item(0);
        preferences.setMCD_AID_DATATYPE_LIENPROG(mcdAidDataTypeLienProg.getTextContent());

        Element mcdDataTypeNumberSizeMode = (Element) racine.getElementsByTagName("mcdDataTypeNumberSizeMode").item(0);
        preferences.setMCDDATATYPE_NUMBER_SIZE_MODE(mcdDataTypeNumberSizeMode.getTextContent());

        Element mcdAidIndColumnName = (Element) racine.getElementsByTagName("mcdAidIndColumnName").item(0);
        preferences.setMCD_AID_IND_COLUMN_NAME(mcdAidIndColumnName.getTextContent());

        Element mcdAidDepColumnName = (Element) racine.getElementsByTagName("mcdAidDepColumnName").item(0);
        preferences.setMCD_AID_DEP_COLUMN_NAME(mcdAidDepColumnName.getTextContent());

        Element mcdAidWithDep = (Element) racine.getElementsByTagName("mcdAidWithDep").item(0);
        preferences.setMCD_AID_WITH_DEP(Boolean.valueOf(mcdAidWithDep.getTextContent()));

        Element mcdTreeNamingAssociation = (Element) racine.getElementsByTagName("mcdTreeNamingAssociation").item(0);
        preferences.setMCD_TREE_NAMING_ASSOCIATION(mcdTreeNamingAssociation.getTextContent());

        Element mcdModeNamingLongName = (Element) racine.getElementsByTagName("mcdModeNamingLongName").item(0);
        preferences.setMCD_MODE_NAMING_LONG_NAME(mcdModeNamingLongName.getTextContent());

        Element mcdModeNamingAttributeShortName = (Element) racine.getElementsByTagName("mcdModeNamingAttributeShortName").item(0);
        preferences.setMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME(mcdModeNamingAttributeShortName.getTextContent());

        Element repositoryMcdModelsMany = (Element) racine.getElementsByTagName("repositoryMcdModelsMany").item(0);
        preferences.setREPOSITORY_MCD_MODELS_MANY(Boolean.valueOf(repositoryMcdModelsMany.getTextContent()));
    }

    private void addMcdAndChild(Element racine, Project project) {
        // création du packages mcd dans l'application
        MCDContModels mcdCont = MVCCDElementFactory.instance().createMCDModels(project, Preferences.REPOSITORY_MCD_MODELS_NAME);

        // création des packages de base (diagrammes, entités, relations) pour un projet sans modèles et packages
        if (!project.isModelsMany()) {
            MVCCDElementFactory.instance().createMCDDiagrams(mcdCont, Preferences.REPOSITORY_MCD_DIAGRAMS_NAME);
            MVCCDElementFactory.instance().createMCDEntities(mcdCont, Preferences.REPOSITORY_MCD_ENTITIES_NAME);
            MVCCDElementFactory.instance().createMCDContRelations(mcdCont, Preferences.REPOSITORY_MCD_RELATIONS_NAME);
        }

        // Récuperation de la balise MCD
        Element mcd = (Element) racine.getElementsByTagName("MCD").item(0);
        // Parcours des enfants de MCD afin d'ajouter les modèles ou les packages
        NodeList mcdChilds = mcd.getChildNodes();
        for (int i = 0; i < mcdChilds.getLength(); i++) {
            Node mcdChild = mcdChilds.item(i);
            if (mcdChild instanceof Element) {
                if (mcdChild.getNodeName().equals("model")) {
                    Element model = (Element) mcdChild;

                    //création du modèle
                    MCDModel mcdModel = MVCCDElementFactory.instance().createMCDModel(mcdCont);
                    mcdModel.setName(model.getAttribute("name"));

                    ArrayList<MVCCDElement> modelChilds = mcdModel.getChilds();

                    // ajoute des éléments du modèle
                    addPropertiesModelOrPackage(mcdModel, model);
                    addContChilds(modelChilds, model);
                    addPackages(mcdModel, model);
                }

                if (mcdChild.getNodeName().equals("package")) {
                    addPackages(mcdCont, mcd);
                }

            }
        }
    }

    private void addPackages(MCDElement mcdModel, Element model) {
        Element pack = (Element) model.getElementsByTagName("package").item(0);
        if (pack != null) {
            // Création du package
            MCDPackage mcdPackage = MVCCDElementFactory.instance().createMCDPackage(mcdModel);
            mcdPackage.setName(pack.getAttribute("name"));

            // Listes des enfants du package
            ArrayList<MVCCDElement> packagesChilds = mcdPackage.getChilds();

            addPropertiesModelOrPackage(mcdPackage, pack);
            addContChilds(packagesChilds, pack);
            addPackages(mcdPackage, pack);
        }
    }

    private void addPropertiesModelOrPackage(MCDElement mcdElement, Element element) {
        // Ajout des propriétés du modèle ou du package
        Element shortName = (Element) element.getElementsByTagName("shortName").item(0);
        mcdElement.setShortName(shortName.getTextContent());

        Element audit = (Element) element.getElementsByTagName("audit").item(0);

        Element auditException = (Element) element.getElementsByTagName("auditException").item(0);

        Element journalization = (Element) element.getElementsByTagName("journalization").item(0);

        Element journalizationException = (Element) element.getElementsByTagName("journalizationException").item(0);

        Element packagesAutorizeds = (Element) element.getElementsByTagName("packagesAutorizeds").item(0);

        if (mcdElement instanceof MCDModel) {
            MCDModel mcdModel = (MCDModel) mcdElement;
            mcdModel.setMcdAudit(Boolean.valueOf(audit.getTextContent()));
            mcdModel.setMcdAuditException(Boolean.valueOf(auditException.getTextContent()));
            mcdModel.setMcdJournalization(Boolean.valueOf(journalization.getTextContent()));
            mcdModel.setMcdJournalizationException(Boolean.valueOf(journalizationException.getTextContent()));
            mcdModel.setPackagesAutorizeds(Boolean.valueOf(packagesAutorizeds.getTextContent()));
        }

        if (mcdElement instanceof MCDPackage) {
            MCDPackage mcdPackage = (MCDPackage) mcdElement;
            mcdPackage.setMcdAudit(Boolean.valueOf(audit.getTextContent()));
            mcdPackage.setMcdAuditException(Boolean.valueOf(auditException.getTextContent()));
            mcdPackage.setMcdJournalization(Boolean.valueOf(journalization.getTextContent()));
            mcdPackage.setMcdJournalizationException(Boolean.valueOf(journalizationException.getTextContent()));

        }
    }

    private void addContChilds(ArrayList<MVCCDElement> list, Element element) {
        // Récuperation des 3 conteneurs pour les diagrammes, entités et relations
        for (int i = 0; i < list.size(); i++) {
            MVCCDElement modelChild = list.get(i);
            if (modelChild instanceof MCDContDiagrams) {
                MCDContDiagrams mcdContDiagrams = (MCDContDiagrams) modelChild;
                // méthode pour ajouter les diagrammes à reprendre
            }
            if (modelChild instanceof MCDContEntities) {
                MCDContEntities mcdContEntities = (MCDContEntities) modelChild;
                addEntities(mcdContEntities, element);
            }

            if (modelChild instanceof MCDContRelations) {
                MCDContRelations mcdContRelations = (MCDContRelations) modelChild;
                addRelations(mcdContRelations, element);
            }
        }
    }


    private void addEntities(MCDContEntities mcdContEntities, Element element) {
        // Récuperation de la balise entities
        Element entities = (Element) element.getElementsByTagName("entities").item(0);
        // Parcours des entités
        NodeList entitiesChilds = entities.getChildNodes();
        for (int i = 0; i < entitiesChilds.getLength(); i++) {
            Node entitiesChild = entitiesChilds.item(i);
            if (entitiesChild instanceof Element) {
                Element entite = (Element) entitiesChild;
                // Création des entités
                MCDEntity mcdEntity = MVCCDElementFactory.instance().createMCDEntity(mcdContEntities);
                mcdEntity.setName(entite.getAttribute("name"));

                addPropertiesEntities(mcdEntity, entite);
                addContEntityChilds(mcdEntity, entite);
            }
        }
    }

    private void addPropertiesEntities(MCDEntity mcdEntity, Element entite) {

        Element shortName = (Element) entite.getElementsByTagName("shortName").item(0);
        mcdEntity.setShortName(shortName.getTextContent());

        Element ordered = (Element) entite.getElementsByTagName("ordered").item(0);
        mcdEntity.setOrdered(Boolean.valueOf(ordered.getTextContent()));

        Element entityAbstract = (Element) entite.getElementsByTagName("entityAbstract").item(0);
        mcdEntity.setEntAbstract(Boolean.valueOf(entityAbstract.getTextContent()));

        Element journal = (Element) entite.getElementsByTagName("journal").item(0);
        mcdEntity.setJournal(Boolean.valueOf(journal.getTextContent()));

        Element audit = (Element) entite.getElementsByTagName("audit").item(0);
        mcdEntity.setAudit(Boolean.valueOf(audit.getTextContent()));

    }


    private void addContEntityChilds(MCDEntity mcdEntity, Element entite) {
        // Ajout des conteurs (attributs, extrémité de relations, contraintes)
        ArrayList<MVCCDElement> entityChilds = mcdEntity.getChilds();
        for (int i = 0; i < entityChilds.size(); i++) {
            MVCCDElement entityChild = entityChilds.get(i);
            if (entityChild instanceof MCDContAttributes) {
                MCDContAttributes mcdContAttributes = (MCDContAttributes) entityChild;
                addAttributs(mcdContAttributes, entite);
            }
            if (entityChild instanceof MCDContEndRels) {
                MCDContEndRels mcdContEndRels = (MCDContEndRels) entityChild;
                addEndRels(mcdContEndRels, entite);
            }
            if (entityChild instanceof MCDContConstraints) {
                MCDContConstraints mcdContConstraints = (MCDContConstraints) entityChild;
                addContraints(mcdContConstraints, entite);
            }
        }
    }

    private void addAttributs(MCDContAttributes mcdContAttributes, Element entite) {
        // Récuperation de la balise attributs
        Element attributs = (Element) entite.getElementsByTagName("attributs").item(0);
        // parcours des attributs
        NodeList attributsChilds = attributs.getChildNodes();
        for (int i = 0; i < attributsChilds.getLength(); i++) {
            Node attributsChild = attributsChilds.item(i);
            if (attributsChild instanceof Element) {
                Element attribut = (Element) attributsChild;

                // création des attributs
                MCDAttribute mcdAttribute = MVCCDElementFactory.instance().createMCDAttribute(mcdContAttributes);
                mcdAttribute.setName(attribut.getAttribute("name"));

                addPropertiesAttributs(mcdAttribute, attribut);

            }
        }
    }

    private void addPropertiesAttributs(MCDAttribute mcdAttribute, Element attribut) {

        // Récupération des propriétés d'attribut
        Element aid = (Element) attribut.getElementsByTagName("aid").item(0);
        mcdAttribute.setAid(Boolean.valueOf(aid.getTextContent()));

        Element aidDep = (Element) attribut.getElementsByTagName("aidDep").item(0);
        mcdAttribute.setAidDep(Boolean.valueOf(aidDep.getTextContent()));

        Element mandatory = (Element) attribut.getElementsByTagName("mandatory").item(0);
        mcdAttribute.setMandatory(Boolean.valueOf(mandatory.getTextContent()));

        Element list = (Element) attribut.getElementsByTagName("list").item(0);
        mcdAttribute.setList(Boolean.valueOf(list.getTextContent()));

        Element frozen = (Element) attribut.getElementsByTagName("frozen").item(0);
        mcdAttribute.setFrozen(Boolean.valueOf(frozen.getTextContent()));

        Element ordered = (Element) attribut.getElementsByTagName("ordered").item(0);
        mcdAttribute.setOrdered(Boolean.valueOf(ordered.getTextContent()));

        Element upperCase = (Element) attribut.getElementsByTagName("upperCase").item(0);
        mcdAttribute.setUppercase(Boolean.valueOf(upperCase.getTextContent()));

        Element dataTypeLienProg = (Element) attribut.getElementsByTagName("dataTypeLienProg").item(0);
        mcdAttribute.setDatatypeLienProg(dataTypeLienProg.getTextContent());

        // Test pour éviter le nullPointException
        Element scale = (Element) attribut.getElementsByTagName("scale").item(0);
        if (!scale.getTextContent().equals("null")) {
            mcdAttribute.setScale(Integer.valueOf(scale.getTextContent()));
        }
        Element size = (Element) attribut.getElementsByTagName("size").item(0);
        if (!scale.getTextContent().equals("null")) {
            mcdAttribute.setSize(Integer.valueOf(size.getTextContent()));
        }

        Element initValue = (Element) attribut.getElementsByTagName("initValue").item(0);
        mcdAttribute.setInitValue(initValue.getTextContent());

        Element derivedValue = (Element) attribut.getElementsByTagName("derivedValue").item(0);
        mcdAttribute.setDerivedValue(derivedValue.getTextContent());

        Element domain = (Element) attribut.getElementsByTagName("domain").item(0);
        mcdAttribute.setDomain(domain.getTextContent());

    }

    private void addContraints(MCDContConstraints mcdContConstraints, Element entite) {
        // Récuperation de la balise contraintes
        Element constraints = (Element) entite.getElementsByTagName("contraintes").item(0);
        // parcours des contraintes
        NodeList constraintsChilds = constraints.getChildNodes();
        for (int i = 0; i < constraintsChilds.getLength(); i++) {
            Node constraintsChild = constraintsChilds.item(i);
            if (constraintsChild instanceof Element) {
                Element constraint = (Element) constraintsChild;

                Element shortName = (Element) constraint.getElementsByTagName("shortName").item(0);

                addTypeConstraintes(constraint, shortName, mcdContConstraints);
            }
        }
    }

    private void addTypeConstraintes(Element constraint, Element shortName, MCDContConstraints mcdContConstraints) {
        // Contraintes de type NID
        Element typeConstraint = (Element) constraint.getElementsByTagName("type").item(0);
        if (typeConstraint.getTextContent().equals("NID")) {

            MCDNID mcdnid = MVCCDElementFactory.instance().createMCDNID(mcdContConstraints);
            mcdnid.setName(constraint.getAttribute("name"));
            mcdnid.setShortName(shortName.getTextContent());

            Element lienProg = (Element) constraint.getElementsByTagName("lienProg").item(0);
            mcdnid.setLienProg(Boolean.valueOf(lienProg.getTextContent()));

        }
        // Contraintes de type Unique
        if (typeConstraint.getTextContent().equals("Unique")) {
            MCDUnique mcdUnique = MVCCDElementFactory.instance().createMCDUnique(mcdContConstraints);
            mcdUnique.setName(constraint.getAttribute("name"));

            mcdUnique.setShortName(shortName.getTextContent());

            Element absolute = (Element) constraint.getElementsByTagName("absolute").item(0);
            mcdUnique.setAbsolute(Boolean.valueOf(absolute.getTextContent()));

        }
    }

    private void addEndRels(MCDContEndRels mcdContEndRels, Element entite) {
    }


    private void addRelations(MCDContRelations mcdContRelations, Element element) {
        addAssociation();
        addGeneralisation();
        addLink();
    }

    private void addAssociation() {
    }

    private void addGeneralisation() {
    }

    private void addLink() {
    }


}

