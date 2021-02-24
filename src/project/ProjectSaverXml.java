package project;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import messages.MessagesBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import preferences.PreferencesManager;
import utilities.files.TranformerForXml;
import utilities.window.DialogMessage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ProjectSaverXml {

    private Project project = MVCCDManager.instance().getProject();
    private Boolean packApp = PreferencesManager.instance().getApplicationPref().getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS();
    private Boolean modeleApp = PreferencesManager.instance().getApplicationPref().getREPOSITORY_MCD_MODELS_MANY();

    //TODO-STB: ajouter le num de version dans le fichier persisté

    public void createProjectFile(File file) {
        //traitement
        try {
            //Creation du document en memoire;
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.newDocument();

            //Création des éléments
            Element racine = document.createElement("project");
            document.appendChild(racine);

            //properties
            addPropertiesProject(document, racine);

            //Preferences Project
            preferenceProject(document, racine); //TODO-STB: utiliser PreferencesManager.getProjectPref

            // Element MCD
            MCDContModels elementMcd = (MCDContModels) project.getChilds().get(1); //TODO-STB: PAS: Ajouter méthode propre dans MCDContModels. le get(0) c'est les préférences, le (1) c'est le MCD. Ce serait à revoir pour s'assurer de rechercher vraiment le bon.
            Element mcd = document.createElement(elementMcd.getName());
            racine.appendChild(mcd);

            ArrayList<MVCCDElement> mcdChilds = elementMcd.getChilds();

            //Modele
            if (modeleApp) {

                addModelAndChilds(document, mcd, mcdChilds);

                //Package
            } else if (packApp) {

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
            Transformer transformer = new TranformerForXml().createTransformer();

            //Création du fichier

            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new FileOutputStream(file));
            transformer.transform(source, result);

            // Message de confirmation de la sauvegarde du fichier
            String message = MessagesBuilder.getMessagesProperty("project.saved",
                    new String[]{MVCCDManager.instance().getProject().getName()});
            DialogMessage.showOk(MVCCDManager.instance().getMvccdWindow(), message);

        } catch (ParserConfigurationException | TransformerException | FileNotFoundException pce) {
            pce.printStackTrace();
        }
    }


    public void preferenceProject(Document document, Element racine) {

        // Ajout des préférences de projet au document
        Element preferences = document.createElement("preferences");
        racine.appendChild(preferences);

        Element mcdJournalization = document.createElement("mcdJournalization");
        mcdJournalization.appendChild(document.createTextNode(project.getPreferences().getMCD_JOURNALIZATION().toString()));
        preferences.appendChild(mcdJournalization);

        Element mcdJournalizationException = document.createElement("mcdJournalizationException");
        mcdJournalizationException.appendChild(document.createTextNode(project.getPreferences().getMCD_JOURNALIZATION_EXCEPTION().toString()));
        preferences.appendChild(mcdJournalizationException);

        Element mcdAudit = document.createElement("mcdAudit");
        mcdAudit.appendChild(document.createTextNode(project.getPreferences().getMCD_AUDIT().toString()));
        preferences.appendChild(mcdAudit);

        Element mcdAuditException = document.createElement("mcdAuditException");
        mcdAuditException.appendChild(document.createTextNode(project.getPreferences().getMCD_AUDIT_EXCEPTION().toString()));
        preferences.appendChild(mcdAuditException);

        Element mcdAidDataTypeLienProg = document.createElement("mcdAidDataTypeLienProg");
        mcdAidDataTypeLienProg.appendChild(document.createTextNode(project.getPreferences().getMCD_AID_DATATYPE_LIENPROG()));
        preferences.appendChild(mcdAidDataTypeLienProg);

        Element mcdDataTypeNumberSizeMode = document.createElement("mcdDataTypeNumberSizeMode");
        mcdDataTypeNumberSizeMode.appendChild(document.createTextNode(project.getPreferences().getMCDDATATYPE_NUMBER_SIZE_MODE()));
        preferences.appendChild(mcdDataTypeNumberSizeMode);

        Element mcdAidIndColumnName = document.createElement("mcdAidIndColumnName");
        mcdAidIndColumnName.appendChild(document.createTextNode(project.getPreferences().getMCD_AID_IND_COLUMN_NAME()));
        preferences.appendChild(mcdAidIndColumnName);

        Element mcdAidDepColumnName = document.createElement("mcdAidDepColumnName");
        mcdAidDepColumnName.appendChild(document.createTextNode(project.getPreferences().getMCD_AID_DEP_COLUMN_NAME()));
        preferences.appendChild(mcdAidDepColumnName);

        Element mcdAidWithDep = document.createElement("mcdAidWithDep");
        mcdAidWithDep.appendChild(document.createTextNode(project.getPreferences().isMCD_AID_WITH_DEP().toString()));
        preferences.appendChild(mcdAidWithDep);

        Element mcdTreeNamingAssociation = document.createElement("mcdTreeNamingAssociation");
        mcdTreeNamingAssociation.appendChild(document.createTextNode(project.getPreferences().getMCD_TREE_NAMING_ASSOCIATION()));
        preferences.appendChild(mcdTreeNamingAssociation);

        Element mcdModeNamingLongName = document.createElement("mcdModeNamingLongName");
        mcdModeNamingLongName.appendChild(document.createTextNode(project.getPreferences().getMCD_MODE_NAMING_LONG_NAME()));
        preferences.appendChild(mcdModeNamingLongName);

        Element mcdModeNamingAttributeShortName = document.createElement("mcdModeNamingAttributeShortName");
        mcdModeNamingAttributeShortName.appendChild(document.createTextNode(project.getPreferences().getMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME()));
        preferences.appendChild(mcdModeNamingAttributeShortName);

        Element repositoryMcdModelsMany = document.createElement("repositoryMcdModelsMany");
        repositoryMcdModelsMany.appendChild(document.createTextNode(project.getPreferences().getREPOSITORY_MCD_MODELS_MANY().toString()));
        preferences.appendChild(repositoryMcdModelsMany);
    }


    private void addModelAndChilds(Document doc, Element mcd, ArrayList<MVCCDElement> mcdChilds) {
        // Parcours des enfants de l'élément mcd
        for (int i = 0; i < mcdChilds.size(); i++) {
            MVCCDElement child = mcdChilds.get(i);
            // Création du modèle dans le document
            Element model = doc.createElement("model");
            if (child instanceof MCDModel) {
                mcd.appendChild(model);
                Attr name = doc.createAttribute("name");
                name.setValue(child.getName());
                model.setAttributeNode(name);
            }

            ArrayList<MVCCDElement> modelsChilds = child.getChilds();

            if (packApp) {
                // Création des différents éléments du modèle avec packages
                addPropertiesModelsOrPackages(doc, model, child);
                addDiagrams(doc, modelsChilds, model);
                addEntities(doc, modelsChilds, model);
                addRelations(doc, modelsChilds, model);
                addPackages(doc, child, model);

            } else {
                // Création des différents éléments du modèle sans packages
                addPropertiesModelsOrPackages(doc, model, child);
                addDiagrams(doc, modelsChilds, model);
                addEntities(doc, modelsChilds, model);
                addRelations(doc, modelsChilds, model);
            }
        }

    }

    private void addPackages(Document doc, MVCCDElement modelChild, Element racine) {

        // Récupération des packages
        ArrayList<MCDPackage> packagesChilds = getPackages(modelChild);

        // Parcours des paquetage
        for (int i = 0; i < packagesChilds.size(); i++) {
            MVCCDElement pack = packagesChilds.get(i);
            ArrayList<MVCCDElement> packageChilds = pack.getChilds();

            if (!pack.getName().isEmpty()) {

                // Ajout des paquetage dans le document
                Element packages = doc.createElement("package");
                Attr name = doc.createAttribute("name");
                name.setValue(pack.getName());
                packages.setAttributeNode(name);

                // Ajout des éléments qui composent un paquetage
                addPropertiesModelsOrPackages(doc, packages, pack);
                addDiagrams(doc, packageChilds, packages);
                addEntities(doc, packageChilds, packages);
                addRelations(doc, packageChilds, packages);
                addPackages(doc, pack, packages);
                racine.appendChild(packages);
            }
        }

    }

    // Méthode pour récupérer tous les paquetages d'un élément
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
        // Ajout du package diagrammes dans le document
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement childElement = listElement.get(i);
            if (childElement.getName().equals("Diagrammes")) {
                Element diagrams = doc.createElement("diagrammes");
                racine.appendChild(diagrams);

                ArrayList<MVCCDElement> diagramsChilds = childElement.getChilds();

                // Ajout des diagrammes dans le document
                for (MVCCDElement childDiagram : diagramsChilds) {
                    String nameDiagram = childDiagram.getName();

                    Element diagram = doc.createElement(nameDiagram);

                    diagrams.appendChild(diagram);

                }

            }
        }
    }

    private void addEntities(Document doc, ArrayList<MVCCDElement> listElement, Element racine) {
        // ajout du package entités dans le document
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement childElement = listElement.get(i);
            String nameModel = childElement.getName();

            if (nameModel.equals("Entités")) {
                Element entities = doc.createElement("entities");
                racine.appendChild(entities);

                ArrayList<MVCCDElement> entitiesChilds = childElement.getChilds();
                // Ajout des entités dans le document
                for (int j = 0; j < entitiesChilds.size(); j++) {
                    MVCCDElement entitiesChild = entitiesChilds.get(j);
                    MCDEntity childEntity = (MCDEntity) entitiesChild;

                    Element entity = doc.createElement("entite");
                    Attr name = doc.createAttribute("name");
                    name.setValue(childEntity.getName());
                    entity.setAttributeNode(name);

                    entities.appendChild(entity);
                    ArrayList<MVCCDElement> entityChilds = childEntity.getChilds();

                    // Ajout des éléments qui composent une entité
                    addPropertiesEntity(doc, entity, childEntity, racine);
                    addAttributs(doc, entity, entityChilds);

                    addContraints(doc, entity, entityChilds);

                }
            }
        }
    }

    private void addPropertiesProject(Document document, Element racine) {
        // Ajout des propriétés du projet
        Element properties = document.createElement("proprietes");
        racine.appendChild(properties);

        Element name = document.createElement("nameProject");
        name.appendChild(document.createTextNode(project.getName()));
        properties.appendChild(name);

        Element profileFileName = document.createElement("profileFileName");
        properties.appendChild(profileFileName);
        // Si le nom du fichier de profil est null, cela renvoi une exception
        if (project.getProfileFileName() != null) {
            profileFileName.appendChild(document.createTextNode(project.getProfileFileName()));
        }

        Element modelsMany = document.createElement("modelsMany");
        modelsMany.appendChild(document.createTextNode(Boolean.toString(project.isModelsMany())));
        properties.appendChild(modelsMany);

        Element packagesAutorizeds = document.createElement("packagesAutorizeds");
        packagesAutorizeds.appendChild(document.createTextNode(Boolean.toString(project.isPackagesAutorizeds())));
        properties.appendChild(packagesAutorizeds);
    }

    private void addPropertiesEntity(Document doc, Element entity, MCDEntity mcdEntity, Element racine) {
        // Ajout des propriétés des entités
        Element properties = doc.createElement("proprietes");
        entity.appendChild(properties);

        Element shortName = doc.createElement("shortName");
        shortName.appendChild(doc.createTextNode(mcdEntity.getShortName()));
        properties.appendChild(shortName);

        Element ordered = doc.createElement("ordered");
        ordered.appendChild(doc.createTextNode(String.valueOf(mcdEntity.isOrdered())));
        properties.appendChild(ordered);

        Element entAbstract = doc.createElement("entityAbstract");
        entAbstract.appendChild(doc.createTextNode(String.valueOf(mcdEntity.isEntAbstract())));
        properties.appendChild(entAbstract);

        Element journal = doc.createElement("journal");
        journal.appendChild(doc.createTextNode(String.valueOf(mcdEntity.isJournal())));
        properties.appendChild(journal);

        Element audit = doc.createElement("audit");
        audit.appendChild(doc.createTextNode(String.valueOf(mcdEntity.isAudit())));
        properties.appendChild(audit);
    }

    private void addPropertiesModelsOrPackages(Document doc, Element element, MVCCDElement child) {
        //Ajouts des proprités pour les modèles et pour les packages
        Element properties = doc.createElement("proprietes");
        element.appendChild(properties);

        Element shortName = doc.createElement("shortName");
        properties.appendChild(shortName);

        Element audit = doc.createElement("audit");
        properties.appendChild(audit);

        Element auditException = doc.createElement("auditException");
        properties.appendChild(auditException);

        Element journalization = doc.createElement("journalization");
        properties.appendChild(journalization);

        Element journalizationException = doc.createElement("journalizationException");
        properties.appendChild(journalizationException);

        if (child instanceof MCDModel) {
            MCDModel mcdModel = (MCDModel) child;

            Element packagesAutorized = doc.createElement("packagesAutorizeds");
            packagesAutorized.appendChild(doc.createTextNode(String.valueOf(mcdModel.isPackagesAutorizeds())));
            properties.appendChild(packagesAutorized);

            shortName.appendChild(doc.createTextNode(mcdModel.getShortName()));
            audit.appendChild(doc.createTextNode(String.valueOf(mcdModel.isMcdAudit())));
            auditException.appendChild(doc.createTextNode(String.valueOf(mcdModel.isMcdAuditException())));
            journalization.appendChild(doc.createTextNode(String.valueOf(mcdModel.isMcdJournalization())));
            journalizationException.appendChild(doc.createTextNode(String.valueOf(mcdModel.isMcdJournalizationException())));

        } else if (child instanceof MCDPackage) {
            MCDPackage mcdPackage = (MCDPackage) child;

            Element parent = doc.createElement("parent");
            parent.appendChild(doc.createTextNode(mcdPackage.getParent().getName()));
            properties.appendChild(parent);

            shortName.appendChild(doc.createTextNode(mcdPackage.getShortName()));
            audit.appendChild(doc.createTextNode(String.valueOf(mcdPackage.isMcdAudit())));
            auditException.appendChild(doc.createTextNode(String.valueOf(mcdPackage.isMcdAuditException())));
            journalization.appendChild(doc.createTextNode(String.valueOf(mcdPackage.isMcdJournalization())));
            journalizationException.appendChild(doc.createTextNode(String.valueOf(mcdPackage.isMcdJournalizationException())));
        }
    }


    private void addAttributs(Document doc, Element entity, ArrayList<MVCCDElement> listElement) {
        // Ajout du package Attributs au document
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement entitychild = listElement.get(i);
            if (entitychild.getName().equals("Attributs")) {
                Element attributs = doc.createElement("attributs");
                entity.appendChild(attributs);

                ArrayList<MVCCDElement> attributsChilds = entitychild.getChilds();

                // Ajout des attributs
                addAttributsChilds(doc, attributsChilds, attributs);

            }
        }
    }

    private void addAttributsChilds(Document doc, ArrayList<MVCCDElement> attributsChilds, Element attributs) {
        for (int i = 0; i < attributsChilds.size(); i++) {
            MVCCDElement attributsChild = attributsChilds.get(i);
            MCDAttribute childAttribut = (MCDAttribute) attributsChild;

            Element attribut = doc.createElement("attribut");
            Attr name = doc.createAttribute("name");
            name.setValue(childAttribut.getName());
            attribut.setAttributeNode(name);

            attributs.appendChild(attribut);

            Element aid = doc.createElement("aid");
            aid.appendChild(doc.createTextNode(String.valueOf(childAttribut.isAid())));
            attribut.appendChild(aid);

            Element aidDep = doc.createElement("aidDep");
            aidDep.appendChild(doc.createTextNode(String.valueOf(childAttribut.isAidDep())));
            attribut.appendChild(aidDep);

            Element mandatory = doc.createElement("mandatory");
            mandatory.appendChild(doc.createTextNode(String.valueOf(childAttribut.isMandatory())));
            attribut.appendChild(mandatory);

            Element list = doc.createElement("list");
            list.appendChild(doc.createTextNode(String.valueOf(childAttribut.isList())));
            attribut.appendChild(list);

            Element frozen = doc.createElement("frozen");
            frozen.appendChild(doc.createTextNode(String.valueOf(childAttribut.isFrozen())));
            attribut.appendChild(frozen);

            Element ordered = doc.createElement("ordered");
            ordered.appendChild(doc.createTextNode(String.valueOf(childAttribut.isOrdered())));
            attribut.appendChild(ordered);

            Element upperCase = doc.createElement("upperCase");
            upperCase.appendChild(doc.createTextNode(String.valueOf(childAttribut.isUppercase())));
            attribut.appendChild(upperCase);

            // Tout les éléments qui suive peuvent être vide. Dans le fichier ils ne sont pas stockés dans ce cas
            Element dataTypeLienProg = doc.createElement("dataTypeLienProg");
            if (childAttribut.getDatatypeLienProg() != null) {
                dataTypeLienProg.appendChild(doc.createTextNode(childAttribut.getDatatypeLienProg()));
            }
            attribut.appendChild(dataTypeLienProg);

            Element scale = doc.createElement("scale");
            attribut.appendChild(scale);
            Integer test = childAttribut.getScale();
            if (test != null) {
                scale.appendChild(doc.createTextNode(String.valueOf(childAttribut.getScale())));
            }

            Element size = doc.createElement("size");
            attribut.appendChild(size);

            if (childAttribut.getSize() != null) {
                size.appendChild(doc.createTextNode(String.valueOf(childAttribut.getSize())));
            }

            Element initValue = doc.createElement("initValue");
            if (!childAttribut.getInitValue().equals("")) {
                initValue.appendChild(doc.createTextNode(childAttribut.getInitValue()));

            }
            attribut.appendChild(initValue);

            Element derivedValue = doc.createElement("derivedValue");
            if (!childAttribut.getDerivedValue().equals("")) {
                derivedValue.appendChild(doc.createTextNode(childAttribut.getDerivedValue()));
            }
            attribut.appendChild(derivedValue);

            Element domain = doc.createElement("domain");
            attribut.appendChild(domain);
            if (childAttribut.getDomain() != null) {
                domain.appendChild(doc.createTextNode(childAttribut.getDomain()));
            }


        }
    }

    private void addContraints(Document doc, Element entity, ArrayList<MVCCDElement> listElement) {
        // Ajout du package Contraintes au document
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement entitychild = listElement.get(i);
            if (entitychild.getName().equals("Contraintes")) {
                Element contraintes = doc.createElement("contraintes");
                entity.appendChild(contraintes);

                ArrayList<MVCCDElement> contraintsChilds = entitychild.getChilds();

                // Ajout des contraintes au document
                addContraintsChilds(doc, contraintsChilds, contraintes);
            }
        }
    }

    private void addContraintsChilds(Document doc, ArrayList<MVCCDElement> contraintsChilds, Element contraintes) {
        for (int i = 0; i < contraintsChilds.size(); i++) {
            MVCCDElement contraintsChild = contraintsChilds.get(i);
            // Récupération de la contrainte
            MCDConstraint mcdConstraint = (MCDConstraint) contraintsChild;
            // Création de la contrainte dans le document
            Element constraint = doc.createElement("constraint");
            contraintes.appendChild(constraint);

            Attr name = doc.createAttribute("name");
            name.setValue(mcdConstraint.getName());
            constraint.setAttributeNode(name);

            Element shortName = doc.createElement("shortName");
            shortName.appendChild(doc.createTextNode(mcdConstraint.getShortName()));
            constraint.appendChild(shortName);

            // Récupération du type de contrainte
            if (mcdConstraint instanceof MCDNID) {
                MCDNID nid = (MCDNID) mcdConstraint;
                Element lienProg = doc.createElement("lienProg");
                lienProg.appendChild(doc.createTextNode(String.valueOf(nid.isLienProg())));
                constraint.appendChild(lienProg);

                Element typeConstrainte = doc.createElement("type");
                typeConstrainte.appendChild(doc.createTextNode("NID"));
                constraint.appendChild(typeConstrainte);
                // Ajout des parameters ( encore pas implémenté dans l'application)
                addParameters(doc, nid, constraint);

            }
            if (mcdConstraint instanceof MCDUnique) {
                MCDUnique unique = (MCDUnique) mcdConstraint;
                Element absolute = doc.createElement("absolute");
                absolute.appendChild(doc.createTextNode(String.valueOf(unique.isAbsolute())));
                constraint.appendChild(absolute);

                Element typeConstrainte = doc.createElement("type");
                typeConstrainte.appendChild(doc.createTextNode("Unique"));
                constraint.appendChild(typeConstrainte);
                // Ajout des parameters ( encore pas implémenté dans l'application)
                addParameters(doc, unique, constraint);
            }
        }
    }

    private void addRelations(Document doc, ArrayList<MVCCDElement> listElement, Element racine) {
        // Ajout du package Relations au document
        for (int i = 0; i < listElement.size(); i++) {
            MVCCDElement childElement = listElement.get(i);
            if (childElement.getName().equals("Relations")) {
                Element relations = doc.createElement("relations");
                racine.appendChild(relations);

                ArrayList<MVCCDElement> relationsChilds = childElement.getChilds();
                // Ajout des rélations au document
                addRelationsChilds(doc, relationsChilds, relations);

            }
        }
    }

    private void addRelationsChilds(Document doc, ArrayList<MVCCDElement> relationsChilds, Element relations) {
        // Création des 3 différents type de relation
        Element associations = doc.createElement("associations");
        relations.appendChild(associations);

        Element generalisations = doc.createElement("generalisations");
        relations.appendChild(generalisations);

        Element links = doc.createElement("links");
        relations.appendChild(links);

        // Récupération des valeurs pour chaque type
        for (int i = 0; i < relationsChilds.size(); i++) {
            MVCCDElement relationsChild = relationsChilds.get(i);
            MCDRelation mcdRelation = (MCDRelation) relationsChild;

            if (mcdRelation instanceof MCDAssociation) {
                MCDAssociation mcdAssociation = (MCDAssociation) mcdRelation;
                // Ajout des associations
                addAssociations(doc, mcdAssociation, associations);
            }

            if (mcdRelation instanceof MCDGeneralization) {
                MCDGeneralization mcdGeneralization = (MCDGeneralization) mcdRelation;
                // Ajout des généralisations
                addGeneralization(doc, mcdGeneralization, generalisations);
            }

            if (mcdRelation instanceof MCDLink) {
                MCDLink mcdLink = (MCDLink) mcdRelation;
                //Ajout des liens d'entité associative
                addlink(doc, mcdLink, links);
            }
        }
    }

    private void addlink(Document doc, MCDLink mcdLink, Element links) {

        Element link = doc.createElement("lienDEntiteAssociative");
        links.appendChild(link);

        // Récupération de l'association
        MCDLinkEnd linkEnd = mcdLink.getEndAssociation(); //TODO-STB: peut-être prévoir une méthode qui retourne directement l'association MCD et pas ele End. //Dans la méthode, le A est l'entité associative et le B est l'association
        Element association = doc.createElement("association");
        Element name = doc.createElement("name");
        association.appendChild(name);
        if (!linkEnd.getName().equals("")) { //TODO-STB: c'était avant endAssociation.getMcdElement().getName().equals("")
            name.appendChild(doc.createTextNode((linkEnd.getNamePath(1)))); //TODO-STB: c'était avant endAssociation.getMcdElement().getNamePath(...)
        } else {
            MCDAssociation mcdAssociation = (MCDAssociation) linkEnd.getmElement(); //TODO-STB: C'était avant (MCDAssociation) linkEnd.getMcdElement()
            MCDAssEnd from = mcdAssociation.getFrom();
            MCDAssEnd to = mcdAssociation.getTo();

            Element extremiteFrom = doc.createElement("extremiteFrom");
            extremiteFrom.appendChild(doc.createTextNode(from.getNamePath(1)));
            association.appendChild(extremiteFrom);

            Element extremiteTo = doc.createElement("extremiteTo");
            extremiteTo.appendChild(doc.createTextNode(to.getNamePath(1)));
            association.appendChild(extremiteTo);
        }
        link.appendChild(association);

        // Récupération de l'entité
        MCDLinkEnd endEntity = mcdLink.getEndEntity();
        Element entity = doc.createElement("entity");
        entity.appendChild(doc.createTextNode(((MCDElement) endEntity.getmElement()).getNamePath(1))); //TODO-STB: c'était avant entity.appendChild(doc.createTextNode(endEntity.getMCDElement().getNamePath(1)));
        link.appendChild(entity);

    }

    private void addGeneralization(Document doc, MCDGeneralization mcdGeneralization, Element generalisations) {
        Element generalisation = doc.createElement("generalisation");
        generalisations.appendChild(generalisation);

        // Récupération de l'entité de généralisation
        MCDGSEnd gen = mcdGeneralization.getGen();
        MCDEntity genEntity = gen.getMcdEntity();
        Element entityGen = doc.createElement("genEntite");
        entityGen.appendChild(doc.createTextNode(genEntity.getNamePath(1)));
        generalisation.appendChild(entityGen);

        // Récupération de l'entité de spécialisation
        MCDGSEnd spec = mcdGeneralization.getSpec();
        MCDEntity specEntity = spec.getMcdEntity();
        Element entitySpec = doc.createElement("specEntite");
        entitySpec.appendChild(doc.createTextNode(specEntity.getNamePath(1)));
        generalisation.appendChild(entitySpec);

    }

    private void addAssociations(Document doc, MCDAssociation mcdAssociation, Element associations) {
        // Récupération des extrémité d'association
        MCDAssEnd extremiteFrom = mcdAssociation.getFrom();
        MCDAssEnd extremiteTo = mcdAssociation.getTo();

        Element association = doc.createElement("association");
        // Association dans nom général ( noms dans les rôles)
        if (mcdAssociation.getName().equals("")) {

            addPropertiesAssociation(doc, association, mcdAssociation);
            addExtremite(doc, association, extremiteFrom);
            addExtremite(doc, association, extremiteTo);

        } else {
            // Association avec nom général
            Attr name = doc.createAttribute("name");
            name.setValue(mcdAssociation.getName());
            association.setAttributeNode(name);

            addPropertiesAssociation(doc, association, mcdAssociation);
            addExtremite(doc, association, extremiteFrom);
            addExtremite(doc, association, extremiteTo);
        }
        associations.appendChild(association);
    }


    private void addPropertiesAssociation(Document doc, Element association, MCDAssociation mcdAssociation) {

        // Ajout des propriétés d'association
        Element properties = doc.createElement("proprietes");
        association.appendChild(properties);

        Element nature = doc.createElement("nature");
        nature.appendChild(doc.createTextNode(mcdAssociation.getNature().getName()));
        properties.appendChild(nature);

        Element oriented = doc.createElement("oriented");
        if (mcdAssociation.getOriented() != null) {
            oriented.appendChild(doc.createTextNode(String.valueOf(mcdAssociation.getOriented())));

        }
        properties.appendChild(oriented);

        Element deleteCascade = doc.createElement("deleteCascade");
        deleteCascade.appendChild(doc.createTextNode(String.valueOf(mcdAssociation.isDeleteCascade())));
        properties.appendChild(deleteCascade);

        Element frozen = doc.createElement("frozen");
        frozen.appendChild(doc.createTextNode(String.valueOf(mcdAssociation.isFrozen())));
        properties.appendChild(frozen);

    }

    private void addExtremite(Document doc, Element association, MCDAssEnd extremite) {

        // Récupération de l'extremité
        Element roleExtremite = doc.createElement("roleExtremiteFrom");
        // Si la direction du dessin a comme valeur 2 le rôle de l'extremité n'est pas "Tracée depuis" mais "Tracée vers"
        if (extremite.getDrawingDirection() == 2) {

            roleExtremite = doc.createElement("roleExtremiteTo");
        }

        association.appendChild(roleExtremite);

        // Récupération des éléments d'une extremité
        Element nameRole = doc.createElement("name");
        nameRole.appendChild(doc.createTextNode(extremite.getName()));
        roleExtremite.appendChild(nameRole);

        Element shortNameRole = doc.createElement("shortName");
        shortNameRole.appendChild(doc.createTextNode(extremite.getShortName()));
        roleExtremite.appendChild(shortNameRole);

        Element entity = doc.createElement("entiteNamePath");
        entity.appendChild(doc.createTextNode(extremite.getMcdEntity().getNamePath(1)));
        roleExtremite.appendChild(entity);

        Element multiplicity = doc.createElement("multiplicity");
        multiplicity.appendChild(doc.createTextNode(extremite.getMultiStr()));
        roleExtremite.appendChild((multiplicity));

        Element orderedRole = doc.createElement("ordered");
        orderedRole.appendChild(doc.createTextNode(String.valueOf(extremite.isOrdered())));
        roleExtremite.appendChild(orderedRole);

    }

    // méthode pour ajouter les paramètres des contraintes (pas encore implémentés dans cette version de l'application)
    private void addParameters(Document doc, MCDConstraint mcdConstraint, Element constraint) {
        ArrayList<MCDParameter> parametersChilds = mcdConstraint.getMcdParameters();
        for (int i = 0; i < parametersChilds.size(); i++) {
            MCDParameter parameterChild = parametersChilds.get(i);
            Element parameter = doc.createElement("parameter");
            constraint.appendChild(parameter);

            Attr name = doc.createAttribute("name");
            name.setValue(parameterChild.getName());
            parameter.setAttributeNode(name);

            Element target = doc.createElement("target");
            Attr targetName = doc.createAttribute("name");
            name.setValue(parameterChild.getTarget().getName());
            target.setAttributeNode(targetName);

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(String.valueOf(parameterChild.getTarget().getId())));
            target.appendChild(id);

            Element order = doc.createElement("order");
            order.appendChild(doc.createTextNode(String.valueOf(parameterChild.getTarget().getOrder())));
            target.appendChild(order);

            Element classShortNameUi = doc.createElement("classShortNameUi");
            classShortNameUi.appendChild(doc.createTextNode(parameterChild.getTarget().getClassShortNameUI()));
            target.appendChild(classShortNameUi);

        }
    }
}