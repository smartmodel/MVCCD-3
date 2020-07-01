package project;

import main.*;
import mcd.*;
import messages.MessagesBuilder;
import org.w3c.dom.*;
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

            MCDContModels mcdCont = MVCCDElementFactory.instance().createMCDModels(project, Preferences.REPOSITORY_MCD_MODELS_NAME);

            Element mcd = (Element) racine.getElementsByTagName("MCD").item(0);

            ArrayList<Element> elementsModeles = loadModels(mcdCont, mcd);

            loadPackages(mcdCont, mcd);

            loadEntities(mcdCont, mcd, elementsModeles);

            loadAttributs();

            loadContraints();

            loadRelations(mcdCont, mcd, elementsModeles);

            loadLinks(mcdCont, mcd, elementsModeles);

            // Validation du fichier
            validator.validate(new DOMSource(document));


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


    private ArrayList<Element> loadModels(MCDContModels mcdCont, Element mcd) {
        // Récuperation de la balise MCD
        ArrayList<Element> elementsModeles = new ArrayList<>();
        NodeList mcdChilds = mcd.getChildNodes();
        for (int i = 0; i < mcdChilds.getLength(); i++) {
            Node mcdChild = mcdChilds.item(i);
            if (mcdChild instanceof Element) {
                if (mcdChild.getNodeName().equals("model")) {
                    Element model = (Element) mcdChild;
                    elementsModeles.add(model);

                    //création du modèle
                    MCDModel mcdModel = MVCCDElementFactory.instance().createMCDModel(mcdCont);
                    mcdModel.setName(model.getAttribute("name"));

                    // ajoute des éléments du modèle
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
        ArrayList<MVCCDElement> childs = mcd.getChilds();
        NodeList mcdChilds = element.getChildNodes();
        for (int i = 0; i < mcdChilds.getLength(); i++) {
            Node mcdChild = mcdChilds.item(i);
            if (mcdChild instanceof Element) {
                if (mcdChild.getNodeName().equals("model")) {
                    Element modelElement = (Element) mcdChild;
                    addPackagesModels(childs, modelElement);
                }
                addPackages(mcdChild, mcd);
            }
        }
    }

    private void addPackagesModels(ArrayList<MVCCDElement> listModel, Element model) {
        NodeList packagesList = model.getChildNodes();
        for (int i = 0; i < packagesList.getLength(); i++) {
            Node packChild = packagesList.item(i);
            if (packChild instanceof Element) {
                if (packChild.getNodeName().equals("package")) {

                    Element pack = (Element) packChild;
                    elementsPackages.add(pack);

                    Element nameParent = (Element) pack.getElementsByTagName("parent").item(0);

                    MCDModel mcdModel = null;
                    for (MVCCDElement mvccdElement : listModel) {
                        MCDModel child = (MCDModel) mvccdElement;
                        if (nameParent.getTextContent().equals(child.getName())) {
                            mcdModel = child;
                        }
                    }
                    if (mcdModel != null) {
                        MCDPackage mcdPackage = MVCCDElementFactory.instance().createMCDPackage(mcdModel);
                        mcdPackage.setName(pack.getAttribute("name"));

                        listPackages.add(mcdPackage);

                        addPropertiesModelOrPackage(mcdPackage, pack);
                        loadOthersPackages(mcdPackage, pack);

                    }
                }
            }
        }
    }

    private void loadOthersPackages(MCDPackage paquetage, Element element) {
        NodeList packagesList = element.getChildNodes();
        for (int i = 0; i < packagesList.getLength(); i++) {
            Node packChild = packagesList.item(i);
            if (packChild instanceof Element) {

                addPackages(packChild, paquetage);

            }
        }
    }

    private void addPackages(Node packChild, MCDElement paquetage) {
        if (packChild.getNodeName().equals("package")) {

            Element pack = (Element) packChild;
            elementsPackages.add(pack);

            MCDPackage mcdPackage = MVCCDElementFactory.instance().createMCDPackage(paquetage);
            mcdPackage.setName(pack.getAttribute("name"));
            listPackages.add(mcdPackage);

            addPropertiesModelOrPackage(mcdPackage, pack);
            loadOthersPackages(mcdPackage, pack);
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

    private void loadEntities(MCDContModels mcd, Element element, ArrayList<Element> elementsModeles) {
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
        MCDContEntities mcdContEntities;
        for (MVCCDElement elementChild : listMVCCD)
            if (elementChild instanceof MCDContEntities) {
                mcdContEntities = (MCDContEntities) elementChild;
                for (Element element : listElement) {
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
                Element entite = (Element) entitiesChild;
                elementsEntities.add(entite);

                MCDEntity mcdEntity = MVCCDElementFactory.instance().createMCDEntity(mcdContEntities);
                mcdEntity.setName(entite.getAttribute("name"));

                listeEntities.add(mcdEntity);

                addPropertiesEntities(mcdEntity, entite);


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

    private void loadAttributs() {
        MCDContAttributes mcdContAttributes;
        for (MVCCDElement mvccdElement : listeEntities) {
            ArrayList<MVCCDElement> childEntities = mvccdElement.getChilds();
            for (MVCCDElement contEntities : childEntities) {
                if (contEntities instanceof MCDContAttributes) {
                    mcdContAttributes = (MCDContAttributes) contEntities;
                    for (Element child : elementsEntities) {
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
        MCDContConstraints mcdContConstraints;
        for (MVCCDElement mvccdElement : listeEntities) {
            ArrayList<MVCCDElement> childEntities = mvccdElement.getChilds();
            for (MVCCDElement contConstraints : childEntities) {
                if (contConstraints instanceof MCDContConstraints) {
                    mcdContConstraints = (MCDContConstraints) contConstraints;
                    for (Element child : elementsEntities) {
                        if (child.getAttribute("name").equals(mvccdElement.getName())) {

                            addContraints(mcdContConstraints, child);
                        }
                    }
                }
            }
        }
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

    private void addTypeConstraintes(Element constraint, Element shortName, MCDContConstraints
            mcdContConstraints) {
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
        MCDContRelations mcdContRelations;
        for (MVCCDElement elementChild : listMVCCD)
            if (elementChild instanceof MCDContRelations) {
                mcdContRelations = (MCDContRelations) elementChild;
                for (Element element : listElement) {
                    if (element.getAttribute("name").equals(mvccdElement.getName())) {
                        addRelations(mcdContRelations, element);
                    }
                }
            }
    }

    private void addRelations(MCDContRelations mcdContRelations, Element element) {
        // Récuperation de la balise relations
        Element relations = (Element) element.getElementsByTagName("relations").item(0);
        // parcours des rélations
        NodeList relationsChilds = relations.getChildNodes();
        for (int i = 0; i < relationsChilds.getLength(); i++) {
            Node relationsChild = relationsChilds.item(i);
            if (relationsChild instanceof Element) {
                Element relation = (Element) relationsChild;

                if (relation.getNodeName().equals("associations")) {
                    addAssociations(mcdContRelations, relation);
                }
                if (relation.getNodeName().equals("generalisations")) {
                    addGeneralisation(mcdContRelations, relation);
                }
            }
        }
    }

    private void addAssociations(MCDContRelations mcdContRelations, Element element) {
        NodeList associationChilds = element.getChildNodes();
        for (int i = 0; i < associationChilds.getLength(); i++) {
            Node associationChild = associationChilds.item(i);
            if (associationChild instanceof Element) {
                Element association = (Element) associationChild;

                Element extremiteFrom = (Element) association.getElementsByTagName("roleExtremiteFrom").item(0);
                Element entityNamePathFrom = (Element) extremiteFrom.getElementsByTagName("entiteNamePath").item(0);

                Element extremiteTo = (Element) association.getElementsByTagName("roleExtremiteTo").item(0);
                Element entityNamepathTo = (Element) extremiteTo.getElementsByTagName("entiteNamePath").item(0);


                MCDEntity entityFrom = addRelationsEntities(entityNamePathFrom);
                MCDEntity entityTo = addRelationsEntities(entityNamepathTo);


                MCDAssociation mcdAssociation = MVCCDElementFactory.instance().createMCDAssociation(mcdContRelations, entityFrom, entityTo);
                if (!association.getAttribute("name").equals("")) {
                    mcdAssociation.setName(association.getAttribute("name"));
                }
                listeAssociations.add(mcdAssociation);

                addExtremiteElement(extremiteFrom, mcdAssociation, association);
                addExtremiteElement(extremiteTo, mcdAssociation, association);

                addProprietesAssociation(association, mcdAssociation);
            }
        }
    }

    private void addProprietesAssociation(Element association, MCDAssociation mcdAssociation) {
        Element nature = (Element) association.getElementsByTagName("nature").item(0);

        MCDAssociationNature mcdAssociationNature = MCDAssociationNature.findByText(MessagesBuilder.getMessagesProperty(nature.getTextContent()));
        mcdAssociation.setNature(mcdAssociationNature);

        Element oriented = (Element) association.getElementsByTagName("oriented").item(0);
        if (!oriented.getTextContent().equals("")) {
            mcdAssociation.setOriented(Boolean.valueOf(oriented.getTextContent()));
        }

        Element deleteCascade = (Element) association.getElementsByTagName("deleteCascade").item(0);
        mcdAssociation.setDeleteCascade(Boolean.valueOf(deleteCascade.getTextContent()));

        Element frozen = (Element) association.getElementsByTagName("frozen").item(0);
        mcdAssociation.setFrozen(Boolean.valueOf(frozen.getTextContent()));
    }


    private void addGeneralisation(MCDContRelations mcdContRelations, Element relation) {
        NodeList generalisationsChilds = relation.getChildNodes();
        for (int i = 0; i < generalisationsChilds.getLength(); i++) {
            Node generalisationChild = generalisationsChilds.item(i);
            if (generalisationChild instanceof Element) {
                Element generalisation = (Element) generalisationChild;

                Element gen = (Element) generalisation.getElementsByTagName("genEntite").item(0);

                Element spec = (Element) generalisation.getElementsByTagName("specEntite").item(0);


                MCDEntity entityGen = addRelationsEntities(gen);
                MCDEntity entitySpec = addRelationsEntities(spec);


                MVCCDElementFactory.instance().createMCDGeneralization(mcdContRelations, entityGen, entitySpec);
            }
        }
    }

    private MCDEntity addRelationsEntities(Element element) {
        MCDEntity mcdEntity = null;

        for (MVCCDElement mvccdElement : listeEntities) {
            MCDEntity entity = (MCDEntity) mvccdElement;
            if (entity.getNamePath(1).equals(element.getTextContent())) {
                mcdEntity = entity;
            }
        }
        return mcdEntity;
    }

    private void addExtremiteElement(Element extremite, MCDAssociation mcdAssociation, Element association) {
        MCDAssEnd assEnd;
        if (extremite.getNodeName().equals("roleExtremiteFrom")) {
            assEnd = mcdAssociation.getFrom();
        } else {
            assEnd = mcdAssociation.getTo();
        }
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
        ArrayList<MVCCDElement> mcdElements = mcd.getChilds();
        MCDContRelations mcdContRelations;

        // relations général
        for (MVCCDElement mvccdElement : mcdElements) {
            if (mvccdElement instanceof MCDContRelations) {
                mcdContRelations = (MCDContRelations) mvccdElement;
                loadRelationsLink(mcdContRelations, element);
            }
        }
        // relations avec paquetages
        for (MVCCDElement mvccdElement : listPackages) {
            ArrayList<MVCCDElement> childsPackage = mvccdElement.getChilds();
            addLinksPackagesOrModels(childsPackage, elementsPackages, mvccdElement);

        }
        // relations avec modèles
        for (MVCCDElement mcdChild : mcdElements) {
            if (mcdChild instanceof MCDModel) {
                ArrayList<MVCCDElement> childModel = mcdChild.getChilds();
                addLinksPackagesOrModels(childModel, elementsModeles, mcdChild);
            }
        }
    }


    private void addLinksPackagesOrModels(ArrayList<MVCCDElement> listMVCCD, ArrayList<Element> listElement, MVCCDElement mvccdElement) {
        MCDContRelations mcdContRelations;
        for (MVCCDElement elementChild : listMVCCD)
            if (elementChild instanceof MCDContRelations) {
                mcdContRelations = (MCDContRelations) elementChild;
                for (Element element : listElement) {
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
                if (relation.getNodeName().equals("links")) {
                    addLink(mcdContRelations, relation);
                }

            }
        }
    }

    private void addLink(MCDContRelations mcdContRelations, Element relation) {
        NodeList linksChilds = relation.getChildNodes();
        for (int i = 0; i < linksChilds.getLength(); i++) {
            Node linkChild = linksChilds.item(i);
            if (linkChild instanceof Element) {
                Element link = (Element) linkChild;

                Element entite = (Element) link.getElementsByTagName("entity").item(0);

                Element association = (Element) link.getElementsByTagName("association").item(0);

                Element name = (Element) association.getElementsByTagName("name").item(0);

                Element from = null;
                Element to = null;
                if (name.getTextContent().equals("")) {
                    from = (Element) association.getElementsByTagName("extremiteFrom").item(0);
                    to = (Element) association.getElementsByTagName("extremiteTo").item(0);
                }

                MCDEntity mcdEntity = addRelationsEntities(entite);
                MCDAssociation mcdAssociation = addRelationsAssociations(name, from, to);

                MVCCDElementFactory.instance().createMCDLink(mcdContRelations, mcdEntity, mcdAssociation);
            }

        }
    }

    private MCDAssociation addRelationsAssociations(Element element, Element from, Element to) {
        MCDAssociation mcdAssociation = null;

        for (MVCCDElement mvccdElement : listeAssociations) {
            MCDAssociation association = (MCDAssociation) mvccdElement;
            if (element.getTextContent().equals("")) {

                if (association.getFrom().getNamePath(1).equals(from.getTextContent()) && association.getTo().getNamePath(1).equals(to.getTextContent())) {
                    mcdAssociation = association;
                }
            } else if (association.getNamePath(1).equals(element.getTextContent())) {
                mcdAssociation = association;
            }
        }
        return mcdAssociation;
    }

}

