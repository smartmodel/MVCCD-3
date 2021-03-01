package project;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDFactory;
import mcd.*;
import messages.MessagesBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import preferences.Preferences;
import profile.Profile;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectLoaderXml {
    // listes d'éléments nécessaires pour récupérer les conteneurs des paquetages, des entités et les associations
    private ArrayList<Element> elementsPackages = new ArrayList<>();
    private ArrayList<MVCCDElement> listPackages = new ArrayList<>();
    private ArrayList<Element> elementsEntities = new ArrayList<>();
    private ArrayList<MVCCDElement> listeEntities = new ArrayList<>();
    private ArrayList<MVCCDElement> listeAssociations = new ArrayList<>();

    public Project loadProjectFile(File fileProjectCurrent) {
        // création du projet et initialisation des préférences du projet
        Project project = new Project(null);
        Preferences preferences = MVCCDElementFactory.instance().createPreferences(project, Preferences.REPOSITORY_PREFERENCES_NAME);

        try {
            // Création du document et du parseur pour récupérer les information du fichier
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new FileInputStream(fileProjectCurrent));

            // Assignation du schéma XSD au fichier pour validation
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("schemas/SchemaProject.xsd"));
            Validator validator = schema.newValidator();


            // Récupération de la racine du fichier
            Element racine = document.getDocumentElement();

            // Récupération du nom du projet
            Element name = (Element) racine.getElementsByTagName("nameProject").item(0);
            project.setName(name.getTextContent());

            // Ajout des éléments du projet
            addPropertiesProject(racine, project);
            addPreferences(racine, preferences);

            // Création du conteneur MCD
            MCDContModels mcdCont = MVCCDElementFactory.instance().createMCDModels(project, Preferences.REPOSITORY_MCD_MODELS_NAME);
            // Récupération de la balise MCD du fichier
            Element mcd = (Element) racine.getElementsByTagName("MCD").item(0);
            // Chargement des modèles ou des 3 conteurs principaux
            ArrayList<Element> elementsModeles = loadModels(mcdCont, mcd);
            // Chargement des packages
            loadPackages(mcdCont, mcd);
            // Chargement des entités
            loadEntities(mcdCont, mcd, elementsModeles);
            // Chargement des attributs
            loadAttributs();
            // Chargements des contraintes
            loadContraints();
            // Chargements des rélations ( associations et généralisations)
            loadRelations(mcdCont, mcd, elementsModeles);
            // Chargements des liens d'entités associatives
            loadLinks(mcdCont, mcd, elementsModeles);

            // Validation du fichier
            validator.validate(new DOMSource(document));


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return project;
    }

    private void addPropertiesProject(Element racine, Project project) {

        // Récupération et création du profil du projet
        Element profileFileName = (Element) racine.getElementsByTagName("profileFileName").item(0);
        if (!profileFileName.getTextContent().equals("")) {
            project.setProfileFileName(profileFileName.getTextContent());

            Profile profile = MVCCDFactory.instance().createProfile(project.getProfileFileName());
            project.setProfile(profile);
        }
        // Récupération des autres propriétés du projet
        Element modelsMany = (Element) racine.getElementsByTagName("modelsMany").item(0);
        project.setModelsMany(Boolean.valueOf(modelsMany.getTextContent()));

        Element packagesAutorizeds = (Element) racine.getElementsByTagName("packagesAutorizeds").item(0);
        project.setPackagesAutorizeds(Boolean.valueOf(packagesAutorizeds.getTextContent()));
    }

    public void addPreferences(Element racine, Preferences preferences) {

        //Récupération et instantiation des préférences de projet

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
    }

    private ArrayList<Element> loadModels(MCDContModels mcdCont, Element mcd) {
        // Créations de la listes des éléments modèles à renvoyer
        ArrayList<Element> elementsModeles = new ArrayList<>();
        // Parcours des enfants de MCD
        NodeList mcdChilds = mcd.getChildNodes();
        for (int i = 0; i < mcdChilds.getLength(); i++) {
            Node mcdChild = mcdChilds.item(i);
            if (mcdChild instanceof Element) {
                if (mcdChild.getNodeName().equals("model")) {
                    Element model = (Element) mcdChild;
                    // Alimentation de la listes des éléments modèles
                    elementsModeles.add(model);

                    //création du modèle
                    MCDModel mcdModel = MVCCDElementFactory.instance().createMCDModel(mcdCont);
                    mcdModel.setName(model.getAttribute("name"));

                    // Ajout des propriétés du modèle
                    addPropertiesModelOrPackage(mcdModel, model);
                }
                // Ajout des conteneurs sans modèles
                if (mcdChild.getNodeName().equals("diagrammes")) {
                    MVCCDElementFactory.instance().createMCDDiagrams(mcdCont, Preferences.REPOSITORY_MCD_DIAGRAMS_NAME);
                }
                if (mcdChild.getNodeName().equals("entities")) {
                    MVCCDElementFactory.instance().createMCDEntities(mcdCont, Preferences.REPOSITORY_MCD_ENTITIES_NAME);
                }
                if (mcdChild.getNodeName().equals("relations")) {
                    MVCCDElementFactory.instance().createMCDContRelations(mcdCont, Preferences.REPOSITORY_MCD_RELATIONS_NAME);
                }
            }
        }
        return elementsModeles;
    }

    private void loadPackages(MCDContModels mcd, Element element) {
        // Parcours des enfants de MCD
        ArrayList<MVCCDElement> childs = mcd.getChilds();
        NodeList mcdChilds = element.getChildNodes();
        for (int i = 0; i < mcdChilds.getLength(); i++) {
            Node mcdChild = mcdChilds.item(i);
            if (mcdChild instanceof Element) {
                if (mcdChild.getNodeName().equals("model")) {
                    Element modelElement = (Element) mcdChild;
                    // Ajout des paquetages pour les modèles
                    addPackagesModels(childs, modelElement);
                }
                // Ajout des paquetages sans modèles
                addPackages(mcdChild, mcd);
            }
        }
    }

    private void addPackagesModels(ArrayList<MVCCDElement> listModel, Element model) {
        // Parcours des enfants de modèles
        NodeList packagesList = model.getChildNodes();
        for (int i = 0; i < packagesList.getLength(); i++) {
            Node packChild = packagesList.item(i);
            if (packChild instanceof Element) {
                if (packChild.getNodeName().equals("package")) {
                    // Création de l'element de paquetage
                    Element pack = (Element) packChild;
                    // Remplissage de la listes des éléments pour les paquetages
                    elementsPackages.add(pack);

                    // Récupération du nom du parent du paquetage
                    Element nameParent = (Element) pack.getElementsByTagName("parent").item(0);

                    // Récuperation du modèle en lien avec le paquetage
                    MCDModel mcdModel = null;
                    for (MVCCDElement mvccdElement : listModel) {
                        MCDModel child = (MCDModel) mvccdElement;
                        if (nameParent.getTextContent().equals(child.getName())) {
                            mcdModel = child;
                        }
                    }
                    if (mcdModel != null) {
                        // Création du paquetage
                        MCDPackage mcdPackage = MVCCDElementFactory.instance().createMCDPackage(mcdModel);
                        mcdPackage.setName(pack.getAttribute("name"));
                        // Remplissage de la listes des MVCCDElements pour les paquetages
                        listPackages.add(mcdPackage);
                        // Ajout des propriétés du paquetage
                        addPropertiesModelOrPackage(mcdPackage, pack);
                        // Ajout d'autres paquetages
                        loadOthersPackages(mcdPackage, pack);
                    }
                }
            }
        }
    }

    private void loadOthersPackages(MCDPackage mcdPackage, Element element) {
        // Parcours des enfants du paquetage
        NodeList packagesList = element.getChildNodes();
        for (int i = 0; i < packagesList.getLength(); i++) {
            Node packChild = packagesList.item(i);
            if (packChild instanceof Element) {
                // Ajout des paquetages
                addPackages(packChild, mcdPackage);

            }
        }
    }

    private void addPackages(Node packChild, MCDElement paquetage) {
        if (packChild.getNodeName().equals("package")) {
            // Création de l'element de paquetage
            Element pack = (Element) packChild;
            // Remplissage de la listes des éléments pour les paquetages
            elementsPackages.add(pack);
            // Création du paquetage
            MCDPackage mcdPackage = MVCCDElementFactory.instance().createMCDPackage(paquetage);
            mcdPackage.setName(pack.getAttribute("name"));
            // Remplissage de la listes des MVCCDElements pour les paquetages
            listPackages.add(mcdPackage);
            // Ajout des propriétés du paquetage
            addPropertiesModelOrPackage(mcdPackage, pack);
            // Ajout d'autres paquetages
            loadOthersPackages(mcdPackage, pack);
        }
    }

    private void addPropertiesModelOrPackage(MCDElement mcdElement, Element element) {
        // Ajout des propriétés du modèle ou du paquetage
        Element shortName = (Element) element.getElementsByTagName("shortName").item(0);
        mcdElement.setShortName(shortName.getTextContent());

        Element audit = (Element) element.getElementsByTagName("audit").item(0);

        Element auditException = (Element) element.getElementsByTagName("auditException").item(0);

        Element journalization = (Element) element.getElementsByTagName("journalization").item(0);

        Element journalizationException = (Element) element.getElementsByTagName("journalizationException").item(0);

        Element packagesAutorizeds = (Element) element.getElementsByTagName("packagesAutorizeds").item(0);

        if (mcdElement instanceof MCDModel) {
            // Instanciation des propriétés du modèle
            MCDModel mcdModel = (MCDModel) mcdElement;
            mcdModel.setMcdAudit(Boolean.valueOf(audit.getTextContent()));
            mcdModel.setMcdAuditException(Boolean.valueOf(auditException.getTextContent()));
            mcdModel.setMcdJournalization(Boolean.valueOf(journalization.getTextContent()));
            mcdModel.setMcdJournalizationException(Boolean.valueOf(journalizationException.getTextContent()));
            mcdModel.setPackagesAutorizeds(Boolean.valueOf(packagesAutorizeds.getTextContent()));
        }

        if (mcdElement instanceof MCDPackage) {
            // Instanciation des propriétés du paquetage
            MCDPackage mcdPackage = (MCDPackage) mcdElement;
            mcdPackage.setMcdAudit(Boolean.valueOf(audit.getTextContent()));
            mcdPackage.setMcdAuditException(Boolean.valueOf(auditException.getTextContent()));
            mcdPackage.setMcdJournalization(Boolean.valueOf(journalization.getTextContent()));
            mcdPackage.setMcdJournalizationException(Boolean.valueOf(journalizationException.getTextContent()));

        }
    }

    private void loadEntities(MCDContModels mcd, Element element, ArrayList<Element> elementsModeles) {
        //Chargement des entités
        ArrayList<MVCCDElement> mcdElements = mcd.getChilds();
        MCDContEntities mcdContEntities;

        // entités général
        for (MVCCDElement mvccdElement : mcdElements) {
            if (mvccdElement instanceof MCDContEntities) {
                mcdContEntities = (MCDContEntities) mvccdElement;
                addEntities(mcdContEntities, element);
            }
        }
        // entités avec paquetages
        for (MVCCDElement mvccdElement : listPackages) {
            ArrayList<MVCCDElement> childsPackage = mvccdElement.getChilds();
            addEntitiesPackagesOrModels(childsPackage, elementsPackages, mvccdElement);

        }
        // entités avec modèles
        for (MVCCDElement mcdChild : mcdElements) {
            if (mcdChild instanceof MCDModel) {
                ArrayList<MVCCDElement> childModel = mcdChild.getChilds();
                addEntitiesPackagesOrModels(childModel, elementsModeles, mcdChild);

            }
        }
    }

    private void addEntitiesPackagesOrModels(ArrayList<MVCCDElement> listMVCCD, ArrayList<Element> listElement, MVCCDElement mvccdElement) {
        // Ajout des entités pour les paquetage ou les modèles
        MCDContEntities mcdContEntities;
        // Parcours de la liste des éléments MVCCD de paquetage ou de modèle
        for (MVCCDElement elementChild : listMVCCD)
            if (elementChild instanceof MCDContEntities) {
                // Récupération du conteneur des entités
                mcdContEntities = (MCDContEntities) elementChild;
                for (Element element : listElement) {
                    // // Ajout des entités si l'élément parent(paquetage ou modèle) cré dans l'application est égal à celui du fichier
                    if (element.getAttribute("name").equals(mvccdElement.getName())) {
                        addEntities(mcdContEntities, element);
                    }
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
                // Création de l'element entité
                Element entite = (Element) entitiesChild;
                // Remplissage de la listes des éléments pour les entités
                elementsEntities.add(entite);
                // Création de l'entité
                MCDEntity mcdEntity = MVCCDElementFactory.instance().createMCDEntity(mcdContEntities);
                mcdEntity.setName(entite.getAttribute("name"));
                // Remplissage de la listes des éléments MVCCD pour les entités
                listeEntities.add(mcdEntity);
                // // Ajout des propriétés des entités
                addPropertiesEntities(mcdEntity, entite);
            }
        }

    }

    private void addPropertiesEntities(MCDEntity mcdEntity, Element entite) {
        // Ajout des propriétés des entités
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

    private void loadAttributs() {
        // Chargement des attributs
        MCDContAttributes mcdContAttributes;
        // Parcours des éléments MVCCD afin de récupérer les conteneurs d'attributs de chaque entité
        for (MVCCDElement mvccdElement : listeEntities) {
            ArrayList<MVCCDElement> childEntities = mvccdElement.getChilds();
            for (MVCCDElement contEntities : childEntities) {
                if (contEntities instanceof MCDContAttributes) {
                    // Récupération des conteneurs d'attributs
                    mcdContAttributes = (MCDContAttributes) contEntities;
                    for (Element child : elementsEntities) {
                        // Ajout des attributs si l'élément entité cré dans l'application est égal à celui du fichier
                        if (child.getAttribute("name").equals(mvccdElement.getName())) {
                            addAttributs(mcdContAttributes, child);
                        }
                    }
                }
            }
        }
    }

    private void addAttributs(MCDContAttributes mcdContAttributes, Element entite) {
        // Récuperation de la balise attributs
        Element attributs = (Element) entite.getElementsByTagName("attributs").item(0);
        // parcours des éléments enfant de la balise attributs
        NodeList attributsChilds = attributs.getChildNodes();
        for (int i = 0; i < attributsChilds.getLength(); i++) {
            Node attributsChild = attributsChilds.item(i);
            if (attributsChild instanceof Element) {
                // Création de l'element attribut
                Element attribut = (Element) attributsChild;
                // création de l'attribut dans l'application
                MCDAttribute mcdAttribute = MVCCDElementFactory.instance().createMCDAttribute(mcdContAttributes);
                mcdAttribute.setName(attribut.getAttribute("name"));
                // Ajout des propriétés de l'attribut
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
        if (!scale.getTextContent().equals("")) {
            mcdAttribute.setScale(Integer.valueOf(scale.getTextContent()));
        }
        Element size = (Element) attribut.getElementsByTagName("size").item(0);
        if (!scale.getTextContent().equals("")) {
            mcdAttribute.setSize(Integer.valueOf(size.getTextContent()));
        }

        Element initValue = (Element) attribut.getElementsByTagName("initValue").item(0);
        mcdAttribute.setInitValue(initValue.getTextContent());

        Element derivedValue = (Element) attribut.getElementsByTagName("derivedValue").item(0);
        mcdAttribute.setDerivedValue(derivedValue.getTextContent());

        Element domain = (Element) attribut.getElementsByTagName("domain").item(0);
        mcdAttribute.setDomain(domain.getTextContent());

    }

    private void loadContraints() {
        // Chargement des contraintes
        MCDContConstraints mcdContConstraints;
        // Parcours des éléments MVCCD afin de récupérer les conteneurs des contraintes de chaque entité
        for (MVCCDElement mvccdElement : listeEntities) {
            ArrayList<MVCCDElement> childEntities = mvccdElement.getChilds();
            for (MVCCDElement contConstraints : childEntities) {
                if (contConstraints instanceof MCDContConstraints) {
                    // Récupération des conteneurs des contraintes
                    mcdContConstraints = (MCDContConstraints) contConstraints;
                    for (Element child : elementsEntities) {
                        // Ajout des contraintes si l'élément entité cré dans l'application est égal à celui du fichier
                        if (child.getAttribute("name").equals(mvccdElement.getName())) {
                            addContraints(mcdContConstraints, child);
                        }
                    }
                }
            }
        }
    }

    private void addContraints(MCDContConstraints mcdContConstraints, Element entite) {
        // Récupération de la balise contraintes
        Element constraints = (Element) entite.getElementsByTagName("contraintes").item(0);
        // Parcours des éléments enfant de la balise contraintes
        NodeList constraintsChilds = constraints.getChildNodes();
        for (int i = 0; i < constraintsChilds.getLength(); i++) {
            Node constraintsChild = constraintsChilds.item(i);
            if (constraintsChild instanceof Element) {
                // Création de l'element attribut
                Element constraint = (Element) constraintsChild;
                // Récupération du shortName de l'attribut
                Element shortName = (Element) constraint.getElementsByTagName("shortName").item(0);
                // Ajout du type de contrainte
                addTypeConstraintes(constraint, shortName, mcdContConstraints);
            }
        }
    }

    private void addTypeConstraintes(Element constraint, Element shortName, MCDContConstraints mcdContConstraints) {
        // Contraintes de type NID
        Element typeConstraint = (Element) constraint.getElementsByTagName("type").item(0);
        if (typeConstraint.getTextContent().equals("NID")) {
            // Création de la contrainte
            MCDNID mcdnid = MVCCDElementFactory.instance().createMCDNID(mcdContConstraints);
            mcdnid.setName(constraint.getAttribute("name"));

            mcdnid.setShortName(shortName.getTextContent());

            Element lienProg = (Element) constraint.getElementsByTagName("lienProg").item(0);
            mcdnid.setLienProg(Boolean.valueOf(lienProg.getTextContent()));

        }
        // Contraintes de type Unique
        if (typeConstraint.getTextContent().equals("Unique")) {
            // Création de la contrainte
            MCDUnique mcdUnique = MVCCDElementFactory.instance().createMCDUnique(mcdContConstraints);
            mcdUnique.setName(constraint.getAttribute("name"));

            mcdUnique.setShortName(shortName.getTextContent());

            Element absolute = (Element) constraint.getElementsByTagName("absolute").item(0);
            mcdUnique.setAbsolute(Boolean.valueOf(absolute.getTextContent()));

        }
    }

    private void loadRelations(MCDContModels mcd, Element element, ArrayList<Element> elementsModeles) {
        ArrayList<MVCCDElement> mcdElements = mcd.getChilds();
        MCDContRelations mcdContRelations;

        // relations général
        for (MVCCDElement mvccdElement : mcdElements) {
            if (mvccdElement instanceof MCDContRelations) {
                mcdContRelations = (MCDContRelations) mvccdElement;
                addRelations(mcdContRelations, element);
            }
        }
        // relations avec paquetages
        for (MVCCDElement mvccdElement : listPackages) {
            ArrayList<MVCCDElement> childsPackage = mvccdElement.getChilds();
            addRelationsPackagesOrModels(childsPackage, elementsPackages, mvccdElement);

        }
        // relations avec modèles
        for (MVCCDElement mcdChild : mcdElements) {
            if (mcdChild instanceof MCDModel) {
                ArrayList<MVCCDElement> childModel = mcdChild.getChilds();
                addRelationsPackagesOrModels(childModel, elementsModeles, mcdChild);

            }
        }

    }

    private void addRelationsPackagesOrModels(ArrayList<MVCCDElement> listMVCCD, ArrayList<Element> listElement, MVCCDElement mvccdElement) {
        //Ajout des relations pour les paquetage ou les modèles
        MCDContRelations mcdContRelations;
        // Parcours de la liste des éléments MVCCD de paquetage ou de modèle
        for (MVCCDElement elementChild : listMVCCD)
            if (elementChild instanceof MCDContRelations) {
                // Récupération du conteneur des relations
                mcdContRelations = (MCDContRelations) elementChild;
                for (Element element : listElement) {
                    // Ajout des relations si l'élément parent(paquetage ou modèle) cré dans l'application est égal à celui du fichier
                    if (element.getAttribute("name").equals(mvccdElement.getName())) {
                        addRelations(mcdContRelations, element);
                    }
                }
            }
    }

    private void addRelations(MCDContRelations mcdContRelations, Element element) {
        // Récuperation de la balise relations
        Element relations = (Element) element.getElementsByTagName("relations").item(0);
        // Parcours des éléments enfant de la balise relations
        NodeList relationsChilds = relations.getChildNodes();
        for (int i = 0; i < relationsChilds.getLength(); i++) {
            Node relationsChild = relationsChilds.item(i);
            if (relationsChild instanceof Element) {
                // Création de l'element relations
                Element relation = (Element) relationsChild;

                if (relation.getNodeName().equals("associations")) {
                    // Ajout des associations
                    addAssociations(mcdContRelations, relation);
                }
                if (relation.getNodeName().equals("generalisations")) {
                    // Ajout des généralisations
                    addGeneralisation(mcdContRelations, relation);
                }
            }
        }
    }

    private void addAssociations(MCDContRelations mcdContRelations, Element element) {
        // Parcours des éléments enfant de la balise associations
        NodeList associationChilds = element.getChildNodes();
        for (int i = 0; i < associationChilds.getLength(); i++) {
            Node associationChild = associationChilds.item(i);
            if (associationChild instanceof Element) {
                // Création de l'element association
                Element association = (Element) associationChild;
                // Récupération des extremités d'association et des namePath
                Element extremiteFrom = (Element) association.getElementsByTagName("roleExtremiteFrom").item(0);
                Element entityNamePathFrom = (Element) extremiteFrom.getElementsByTagName("entiteNamePath").item(0);

                Element extremiteTo = (Element) association.getElementsByTagName("roleExtremiteTo").item(0);
                Element entityNamepathTo = (Element) extremiteTo.getElementsByTagName("entiteNamePath").item(0);

                // Création des entités en lien avec les extremités de rélation
                MCDEntity entityFrom = addRelationsEntities(entityNamePathFrom);
                MCDEntity entityTo = addRelationsEntities(entityNamepathTo);

                // Création de l'association dans l'application
                MCDAssociation mcdAssociation = MVCCDElementFactory.instance().createMCDAssociation(mcdContRelations, entityFrom, entityTo);
                if (!association.getAttribute("name").equals("")) {
                    mcdAssociation.setName(association.getAttribute("name"));
                }
                listeAssociations.add(mcdAssociation);

                // Ajout des extremités d'associations
                addExtremiteElement(extremiteFrom, mcdAssociation, association);
                addExtremiteElement(extremiteTo, mcdAssociation, association);
                // Ajout des propriétés des associations
                addProprietesAssociation(association, mcdAssociation);
            }
        }
    }

    private void addProprietesAssociation(Element association, MCDAssociation mcdAssociation) {
        // Ajout des propriétés d'association
        Element nature = (Element) association.getElementsByTagName("nature").item(0);

        MCDAssociationNature mcdAssociationNature = MCDAssociationNature.findByText(MessagesBuilder.getMessagesProperty(nature.getTextContent()));
        mcdAssociation.setNature(mcdAssociationNature);

        Element oriented = (Element) association.getElementsByTagName("oriented").item(0);
        // L'élément oriented peut être vide
        if (!oriented.getTextContent().equals("")) {
            mcdAssociation.setOriented(Boolean.valueOf(oriented.getTextContent()));
        }

        Element deleteCascade = (Element) association.getElementsByTagName("deleteCascade").item(0);
        mcdAssociation.setDeleteCascade(Boolean.valueOf(deleteCascade.getTextContent()));

        Element frozen = (Element) association.getElementsByTagName("frozen").item(0);
        mcdAssociation.setFrozen(Boolean.valueOf(frozen.getTextContent()));
    }


    private void addGeneralisation(MCDContRelations mcdContRelations, Element relation) {
        // Parcours des éléments enfant de la balise generalisations
        NodeList generalisationsChilds = relation.getChildNodes();
        for (int i = 0; i < generalisationsChilds.getLength(); i++) {
            Node generalisationChild = generalisationsChilds.item(i);
            if (generalisationChild instanceof Element) {
                // Création de l'element generalisation
                Element generalisation = (Element) generalisationChild;

                // Récupération du contenu de généralisation
                Element gen = (Element) generalisation.getElementsByTagName("genEntite").item(0);
                Element spec = (Element) generalisation.getElementsByTagName("specEntite").item(0);

                // Création des entités en lien avec le contenu de généralisation
                MCDEntity entityGen = addRelationsEntities(gen);
                MCDEntity entitySpec = addRelationsEntities(spec);

                // Création de la généralisation dans l'application
                MVCCDElementFactory.instance().createMCDGeneralization(mcdContRelations, entityGen, entitySpec);
            }
        }
    }

    private MCDEntity addRelationsEntities(Element element) {
        MCDEntity mcdEntity = null;
        // Parcours de la listes des entités
        for (MVCCDElement mvccdElement : listeEntities) {
            MCDEntity entity = (MCDEntity) mvccdElement;
            // Compararion du namePath de l'entité de l'extremité de relation avec les entités créer dans l'aplication
            if (entity.getNamePath(1).equals(element.getTextContent())) {
                mcdEntity = entity;
            }
        }
        return mcdEntity;
    }

    private void addExtremiteElement(Element extremite, MCDAssociation mcdAssociation, Element association) {
        // Création des extremités
        MCDAssEnd assEnd;
        // Comparaison pour savoir si c'est l'éxtrémité source ou l'éxtrémité cible
        if (extremite.getNodeName().equals("roleExtremiteFrom")) {
            assEnd = mcdAssociation.getFrom();
        } else {
            assEnd = mcdAssociation.getTo();
        }
        // Récupération des propriétés des propriétés des extremités
        Element name = (Element) extremite.getElementsByTagName("name").item(0);
        if (name != null) {
            assEnd.setName(name.getTextContent());

            Element shortName = (Element) extremite.getElementsByTagName("shortName").item(0);
            assEnd.setShortName(shortName.getTextContent());
        }

        Element multiply = (Element) extremite.getElementsByTagName("multiplicity").item(0);
        assEnd.setMultiStr(multiply.getTextContent());

        Element ordered = (Element) extremite.getElementsByTagName("ordered").item(0);
        assEnd.setOrdered(Boolean.valueOf(ordered.getTextContent()));
    }

    private void loadLinks(MCDContModels mcd, Element element, ArrayList<Element> elementsModeles) {
        // Chargement des liens d'entités associatives
        ArrayList<MVCCDElement> mcdElements = mcd.getChilds();
        MCDContRelations mcdContRelations;

        //  Relations général
        for (MVCCDElement mvccdElement : mcdElements) {
            if (mvccdElement instanceof MCDContRelations) {
                mcdContRelations = (MCDContRelations) mvccdElement;
                loadRelationsLink(mcdContRelations, element);
            }
        }
        //  Relations avec paquetages
        for (MVCCDElement mvccdElement : listPackages) {
            ArrayList<MVCCDElement> childsPackage = mvccdElement.getChilds();
            addLinksPackagesOrModels(childsPackage, elementsPackages, mvccdElement);

        }
        // Relations avec modèles
        for (MVCCDElement mcdChild : mcdElements) {
            if (mcdChild instanceof MCDModel) {
                ArrayList<MVCCDElement> childModel = mcdChild.getChilds();
                addLinksPackagesOrModels(childModel, elementsModeles, mcdChild);
            }
        }
    }


    private void addLinksPackagesOrModels(ArrayList<MVCCDElement> listMVCCD, ArrayList<Element> listElement, MVCCDElement mvccdElement) {
        //Ajout des relations pour les paquetage ou les modèles
        MCDContRelations mcdContRelations;
        for (MVCCDElement elementChild : listMVCCD)
            if (elementChild instanceof MCDContRelations) {
                // Récupération du conteneur des relations
                mcdContRelations = (MCDContRelations) elementChild;
                for (Element element : listElement) {
                    // Ajout des liens d'entités associatives si l'élément parent(paquetage ou modèle) cré dans l'application est égal à celui du fichier
                    if (element.getAttribute("name").equals(mvccdElement.getName())) {
                        loadRelationsLink(mcdContRelations, element);
                    }
                }
            }
    }

    private void loadRelationsLink(MCDContRelations mcdContRelations, Element element) {
        Element relations = (Element) element.getElementsByTagName("relations").item(0);
        // parcours des rélations
        NodeList relationsChilds = relations.getChildNodes();
        for (int i = 0; i < relationsChilds.getLength(); i++) {
            Node relationsChild = relationsChilds.item(i);
            if (relationsChild instanceof Element) {
                Element relation = (Element) relationsChild;
                // Ajout des liens d'entités associatives
                if (relation.getNodeName().equals("links")) {
                    addLink(mcdContRelations, relation);
                }

            }
        }
    }

    private void addLink(MCDContRelations mcdContRelations, Element relation) {
        // Parcours des enfants de la balise links
        NodeList linksChilds = relation.getChildNodes();
        for (int i = 0; i < linksChilds.getLength(); i++) {
            Node linkChild = linksChilds.item(i);
            if (linkChild instanceof Element) {
                // Création de l'element de lien d'entité associative
                Element link = (Element) linkChild;
                // Récupération de l'entité de lien sauvegardée dans le fichier
                Element entite = (Element) link.getElementsByTagName("entity").item(0);
                // Récupération de l'association de lien sauvegardée dans le fichier
                Element association = (Element) link.getElementsByTagName("association").item(0);
                // Récupération du nom de l'association
                Element name = (Element) association.getElementsByTagName("name").item(0);

                // Récupération des extremités de l'association si le nom est vide
                Element from = null;
                Element to = null;
                if (name.getTextContent().equals("")) {
                    from = (Element) association.getElementsByTagName("extremiteFrom").item(0);
                    to = (Element) association.getElementsByTagName("extremiteTo").item(0);
                }

                // Récupération de l'entité
                MCDEntity mcdEntity = addRelationsEntities(entite);
                // Récupération de l'association
                MCDAssociation mcdAssociation = addAssociationsForLinks(name, from, to);
                // Création du lien d'entité dans l'application
                MVCCDElementFactory.instance().createMCDLink(mcdContRelations, mcdEntity, mcdAssociation);
            }

        }
    }

    private MCDAssociation addAssociationsForLinks(Element element, Element from, Element to) {
        MCDAssociation mcdAssociation = null;

        for (MVCCDElement mvccdElement : listeAssociations) {
            MCDAssociation association = (MCDAssociation) mvccdElement;
            if (element.getTextContent().equals("")) {
                // Comparatif des noms des extremités de l'association du lien d'entité associative avec les associations crées dans l'application
                if (association.getFrom().getNamePath(1).equals(from.getTextContent()) && association.getTo().getNamePath(1).equals(to.getTextContent())) {
                    mcdAssociation = association;
                }
                // Comparatif du nom de l'association du lien d'entité associative avec les associations crées dans l'application
            } else if (association.getNamePath(1).equals(element.getTextContent())) {
                mcdAssociation = association;
            }
        }
        return mcdAssociation;
    }

}

