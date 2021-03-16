package project;

import console.Console;
import diagram.mcd.MCDDiagram;
import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import messages.MessagesBuilder;
import mldr.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import preferences.Preferences;
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

/**
 * @author Giorgio Roncallo, adapté et complété par Steve Berberat
 */
public class ProjectSaverXml {

    private Project project = MVCCDManager.instance().getProject();
    private Preferences projectPreferences = PreferencesManager.instance().getProjectPref(); //Il est mieux de récupérer les préférences par ce biais plutôt que project.getPreferences(): cela permet de s'assurer de récupérer des préférences si celles du projet n'existent pas (à priori, à confirmer avec PAS).
    private Boolean packagesAuthorized = PreferencesManager.instance().preferences().getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS();
    private Boolean manyModelsAuthorized = PreferencesManager.instance().preferences().getREPOSITORY_MCD_MODELS_MANY();

    /*
    Le code commenté ci-dessous était celui de Giorgio Roncallo. Il chargeait d'office les préférences d'application,
    alors qu'il faut à priori charger les bonnes préférences applicables (projet, sinon profile, sinon application...)
    */
    //private Boolean packagesAuthorized = PreferencesManager.instance().getApplicationPref().getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS();
    //private Boolean manyModelsAuthorized = PreferencesManager.instance().getApplicationPref().getREPOSITORY_MCD_MODELS_MANY();

    //TODO-STB: vérifier que la version s'enregistre bien avec les préférences dans le fichier XML: Preferences.VERSION

    /**
     * Méthode principale qui se charge de créer un fichier XML contenant la sauvegarde du projet utilisateur.
     * @param file Chemin d'accès au fichier (y compris le nom du fichier) qui sera créé ou qui sera modifié.
     */
    public void createProjectFile(File file) {
        try {
            //Creation du document XML en mémoire;
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.newDocument();

            //Création de la balise racine <project>
            Element projectTag = document.createElement("project");
            document.appendChild(projectTag);
            Attr idAttrOfProjectTag = document.createAttribute("id");
            idAttrOfProjectTag.setValue(String.valueOf(project.getIdProjectElement()));
            projectTag.setAttributeNode(idAttrOfProjectTag);

            //Propriété du projet
            addProperties(document, projectTag);

            //Préférences du projet
            addProjectPreferences(document, projectTag);

            //Création de la balise <MCD>
            MCDContModels mcdContModels = project.getMCDContModels();
            Element mcdTag = document.createElement(mcdContModels.getName());
            projectTag.appendChild(mcdTag);
            Attr idAttrOfMcdTag = document.createAttribute("id");
            idAttrOfMcdTag.setValue(String.valueOf(mcdContModels.getIdProjectElement()));
            mcdTag.setAttributeNode(idAttrOfMcdTag);

            ArrayList<MVCCDElement> mcdModels = mcdContModels.getChilds();

            //Modèle
            if (manyModelsAuthorized) {

                addModelAndChilds(document, mcdTag, mcdModels);

            //Package
            } else if (packagesAuthorized) {

                addDiagrams(document, mcdModels, mcdTag);
                addEntities(document, mcdModels, mcdTag);
                addRelations(document, mcdModels, mcdTag);
                addPackages(document, mcdContModels, mcdTag);
                addMLD(document, mcdModels, mcdTag);

            //projet simple
            } else {

                addDiagrams(document, mcdModels, mcdTag);
                addEntities(document, mcdModels, mcdTag);
                addRelations(document, mcdModels, mcdTag);
                addMLD(document, mcdModels, mcdTag);
            }

            //Formatage du fichier
            Transformer transformer = new TranformerForXml().createTransformer();

            //Création du fichier

            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new FileOutputStream(file)); //Génère un FileNotFoundException si le fichier ne peut pas être créé, s'il existe mais ne peut pas être modifié ou si un répertoire du même nom existe.
            transformer.transform(source, result);

            // Message de confirmation de la sauvegarde du fichier
            String message = MessagesBuilder.getMessagesProperty("project.saved",
                    new String[]{MVCCDManager.instance().getProject().getName()});
            DialogMessage.showOk(MVCCDManager.instance().getMvccdWindow(), message);

        } catch (ParserConfigurationException | TransformerException | FileNotFoundException pce) {
            pce.printStackTrace();
        }
    }


    /**
     * Ajoute les préférences de projet au document XML
     * @param document Document XML auquel ajouter les préférences
     * @param racine Noeud racine du document XML. Les préférences seront ajoutées en tant que fils de ce noeud.
     */
    public void addProjectPreferences(Document document, Element racine) {

        Element preferences = document.createElement("preferences");
        racine.appendChild(preferences);

        //Préférences Général
        Element generalRelationNotation = document.createElement("generalRelationNotation");
        generalRelationNotation.appendChild(document.createTextNode(projectPreferences.getGENERAL_RELATION_NOTATION().toString()));
        preferences.appendChild(generalRelationNotation);

        //Préférences MCD
        Element mcdJournalization = document.createElement("mcdJournalization");
        mcdJournalization.appendChild(document.createTextNode(projectPreferences.getMCD_JOURNALIZATION().toString()));
        preferences.appendChild(mcdJournalization);

        Element mcdJournalizationException = document.createElement("mcdJournalizationException");
        mcdJournalizationException.appendChild(document.createTextNode(projectPreferences.getMCD_JOURNALIZATION_EXCEPTION().toString()));
        preferences.appendChild(mcdJournalizationException);

        Element mcdAudit = document.createElement("mcdAudit");
        mcdAudit.appendChild(document.createTextNode(projectPreferences.getMCD_AUDIT().toString()));
        preferences.appendChild(mcdAudit);

        Element mcdAuditException = document.createElement("mcdAuditException");
        mcdAuditException.appendChild(document.createTextNode(projectPreferences.getMCD_AUDIT_EXCEPTION().toString()));
        preferences.appendChild(mcdAuditException);

        Element mcdAidDataTypeLienProg = document.createElement("mcdAidDataTypeLienProg");
        mcdAidDataTypeLienProg.appendChild(document.createTextNode(projectPreferences.getMCD_AID_DATATYPE_LIENPROG()));
        preferences.appendChild(mcdAidDataTypeLienProg);

        Element mcdDataTypeNumberSizeMode = document.createElement("mcdDataTypeNumberSizeMode");
        mcdDataTypeNumberSizeMode.appendChild(document.createTextNode(projectPreferences.getMCDDATATYPE_NUMBER_SIZE_MODE()));
        preferences.appendChild(mcdDataTypeNumberSizeMode);

        Element mcdAidIndColumnName = document.createElement("mcdAidIndColumnName");
        mcdAidIndColumnName.appendChild(document.createTextNode(projectPreferences.getMCD_AID_IND_COLUMN_NAME()));
        preferences.appendChild(mcdAidIndColumnName);

        Element mcdAidDepColumnName = document.createElement("mcdAidDepColumnName");
        mcdAidDepColumnName.appendChild(document.createTextNode(projectPreferences.getMCD_AID_DEP_COLUMN_NAME()));
        preferences.appendChild(mcdAidDepColumnName);

        Element mcdAidWithDep = document.createElement("mcdAidWithDep");
        mcdAidWithDep.appendChild(document.createTextNode(projectPreferences.isMCD_AID_WITH_DEP().toString()));
        preferences.appendChild(mcdAidWithDep);

        Element mcdTreeNamingAssociation = document.createElement("mcdTreeNamingAssociation");
        mcdTreeNamingAssociation.appendChild(document.createTextNode(projectPreferences.getMCD_TREE_NAMING_ASSOCIATION()));
        preferences.appendChild(mcdTreeNamingAssociation);

        Element mcdModeNamingLongName = document.createElement("mcdModeNamingLongName");
        mcdModeNamingLongName.appendChild(document.createTextNode(projectPreferences.getMCD_MODE_NAMING_LONG_NAME()));
        preferences.appendChild(mcdModeNamingLongName);

        Element mcdModeNamingAttributeShortName = document.createElement("mcdModeNamingAttributeShortName");
        mcdModeNamingAttributeShortName.appendChild(document.createTextNode(projectPreferences.getMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME()));
        preferences.appendChild(mcdModeNamingAttributeShortName);
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

            if (packagesAuthorized) {
                // Création des différents éléments du modèle avec packages
                addPropertiesModelsOrPackages(doc, model, child);
                addDiagrams(doc, modelsChilds, model);
                addEntities(doc, modelsChilds, model);
                addRelations(doc, modelsChilds, model);
                addPackages(doc, child, model);
                addMLD(doc, modelsChilds, model);
            } else {
                // Création des différents éléments du modèle sans packages
                addPropertiesModelsOrPackages(doc, model, child);
                addDiagrams(doc, modelsChilds, model);
                addEntities(doc, modelsChilds, model);
                addRelations(doc, modelsChilds, model);
                addMLD(doc, modelsChilds, model);
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

    /**
     * Méthode pour récupérer tous les paquetages d'un élément
     */
    private ArrayList<MCDPackage> getPackages(MVCCDElement element) {
        ArrayList<MCDPackage> packages = new ArrayList<>();
        for (MVCCDElement mvccdElement : element.getChilds()) {
            if (mvccdElement instanceof MCDPackage) {
                packages.add((MCDPackage) mvccdElement);
            }
        }
        return packages;
    }

    // *** Méthodes de sauvegarde du MCD ***

    private void addDiagrams(Document doc, ArrayList<MVCCDElement> listElements, Element racineTag) {
        // Ajout du package diagrammes dans le document
        for (int i = 0; i < listElements.size(); i++) {
            if(listElements.get(i) instanceof MCDContDiagrams){
                MCDContDiagrams mcdContDiagrams = (MCDContDiagrams) listElements.get(i);
                Element diagramsTag = doc.createElement("diagrammes");
                Attr idAttrOfDiagramsTag = doc.createAttribute("id");
                idAttrOfDiagramsTag.setValue(String.valueOf(mcdContDiagrams.getIdProjectElement()));
                diagramsTag.setAttributeNode(idAttrOfDiagramsTag);
                racineTag.appendChild(diagramsTag);

                ArrayList<MVCCDElement> diagrams = mcdContDiagrams.getChilds();

                // Ajout des diagrammes dans le document
                for (MVCCDElement diagram : diagrams) {
                    if(diagram instanceof MCDDiagram) {
                        // Création de la balise <diagramme>
                        MCDDiagram mcdDiagram = (MCDDiagram) diagram;
                        Element diagramTag = doc.createElement("diagramme");
                        diagramsTag.appendChild(diagramTag);

                        // Ajout de l'id à la balise <diagramme>
                        Attr idAttrOfDiagramTag = doc.createAttribute("id");
                        idAttrOfDiagramTag.setValue(String.valueOf(mcdDiagram.getIdProjectElement()));
                        diagramTag.setAttributeNode(idAttrOfDiagramTag);

                        // Ajout de l'attribut "name" à la balise <diagramme>
                        Attr nameAttrOfDiagramTag = doc.createAttribute("name");
                        nameAttrOfDiagramTag.setValue(mcdDiagram.getName());
                        diagramTag.setAttributeNode(nameAttrOfDiagramTag);
                    }
                }

            }
        }
    }

    private void addEntities(Document doc, ArrayList<MVCCDElement> mcdContModelsChilds, Element racineTag) {
        // Parcours tous les conteneurs d'entités
        for (int i = 0; i < mcdContModelsChilds.size(); i++) {
            MVCCDElement mcdContModelsChild = mcdContModelsChilds.get(i);
            if(mcdContModelsChild instanceof MCDContEntities){
                MCDContEntities mcdContEntities = (MCDContEntities) mcdContModelsChild;

                // Création de la balise <entities>
                Element entitiesTag = doc.createElement("entities");
                racineTag.appendChild(entitiesTag);

                // Ajout de l'id à la balise <entities>
                Attr idAttrOfEntitiesTag = doc.createAttribute("id");
                idAttrOfEntitiesTag.setValue(String.valueOf(mcdContEntities.getIdProjectElement()));
                entitiesTag.setAttributeNode(idAttrOfEntitiesTag);

                // Parcours et ajout des entités
                ArrayList<MVCCDElement> entities = mcdContEntities.getChilds();
                for (int j = 0; j < entities.size(); j++) {
                    MCDEntity entity = (MCDEntity) entities.get(j);

                    // Création de la balise <entite>
                    Element entityTag = doc.createElement("entite");
                    entitiesTag.appendChild(entityTag);

                    // Ajout de l'attribut "id" à <entite>
                    Attr idAttrOfEntityTag = doc.createAttribute("id");
                    idAttrOfEntityTag.setValue(String.valueOf(entity.getIdProjectElement()));
                    entityTag.setAttributeNode(idAttrOfEntityTag);

                    // Ajout de l'attribut "name" à <entite>
                    Attr nameAttrOfEntityTag = doc.createAttribute("name");
                    nameAttrOfEntityTag.setValue(entity.getName());
                    entityTag.setAttributeNode(nameAttrOfEntityTag);

                    // Ajout des éléments qui composent une entité
                    ArrayList<MVCCDElement> entityChilds = entity.getChilds();
                    addPropertiesEntity(doc, entityTag, entity, racineTag);
                    addAttributs(doc, entityTag, entityChilds);
                    addContraints(doc, entityTag, entityChilds);
                }
            }
        }
    }

    private void addProperties(Document document, Element racine) {
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
                Element attributsTag = doc.createElement("attributs");
                entity.appendChild(attributsTag);

                ArrayList<MVCCDElement> attributsChilds = entitychild.getChilds();

                // Ajout des attributs
                addAttributsChilds(doc, attributsChilds, attributsTag);

            }
        }
    }

    private void addAttributsChilds(Document doc, ArrayList<MVCCDElement> attributsChilds, Element attributs) {
        for (int i = 0; i < attributsChilds.size(); i++) {
            MVCCDElement attributsChild = attributsChilds.get(i);
            MCDAttribute childAttribut = (MCDAttribute) attributsChild;

            // Création de la balise <attribut>
            Element attributTag = doc.createElement("attribut");
            attributs.appendChild(attributTag);

            // Ajout de l'attribut "id" à <attribut>
            Attr id = doc.createAttribute("id");
            id.setValue(String.valueOf(childAttribut.getIdProjectElement()));
            attributTag.setAttributeNode(id);

            // Ajout de l'attribut "name" à <attribut>
            Attr name = doc.createAttribute("name");
            name.setValue(childAttribut.getName());
            attributTag.setAttributeNode(name);

            Element aid = doc.createElement("aid");
            aid.appendChild(doc.createTextNode(String.valueOf(childAttribut.isAid())));
            attributTag.appendChild(aid);

            Element aidDep = doc.createElement("aidDep");
            aidDep.appendChild(doc.createTextNode(String.valueOf(childAttribut.isAidDep())));
            attributTag.appendChild(aidDep);

            Element mandatory = doc.createElement("mandatory");
            mandatory.appendChild(doc.createTextNode(String.valueOf(childAttribut.isMandatory())));
            attributTag.appendChild(mandatory);

            Element list = doc.createElement("list");
            list.appendChild(doc.createTextNode(String.valueOf(childAttribut.isList())));
            attributTag.appendChild(list);

            Element frozen = doc.createElement("frozen");
            frozen.appendChild(doc.createTextNode(String.valueOf(childAttribut.isFrozen())));
            attributTag.appendChild(frozen);

            Element ordered = doc.createElement("ordered");
            ordered.appendChild(doc.createTextNode(String.valueOf(childAttribut.isOrdered())));
            attributTag.appendChild(ordered);

            Element upperCase = doc.createElement("upperCase");
            upperCase.appendChild(doc.createTextNode(String.valueOf(childAttribut.isUppercase())));
            attributTag.appendChild(upperCase);

            // Tout les éléments qui suive peuvent être vide. Dans le fichier ils ne sont pas stockés dans ce cas
            Element dataTypeLienProg = doc.createElement("dataTypeLienProg");
            if (childAttribut.getDatatypeLienProg() != null) {
                dataTypeLienProg.appendChild(doc.createTextNode(childAttribut.getDatatypeLienProg()));
            }
            attributTag.appendChild(dataTypeLienProg);

            Element scale = doc.createElement("scale");
            attributTag.appendChild(scale);
            Integer test = childAttribut.getScale();
            if (test != null) {
                scale.appendChild(doc.createTextNode(String.valueOf(childAttribut.getScale())));
            }

            Element size = doc.createElement("size");
            attributTag.appendChild(size);

            if (childAttribut.getSize() != null) {
                size.appendChild(doc.createTextNode(String.valueOf(childAttribut.getSize())));
            }

            Element initValue = doc.createElement("initValue");
            if (!childAttribut.getInitValue().equals("")) {
                initValue.appendChild(doc.createTextNode(childAttribut.getInitValue()));

            }
            attributTag.appendChild(initValue);

            Element derivedValue = doc.createElement("derivedValue");
            if (!childAttribut.getDerivedValue().equals("")) {
                derivedValue.appendChild(doc.createTextNode(childAttribut.getDerivedValue()));
            }
            attributTag.appendChild(derivedValue);

            Element domain = doc.createElement("domain");
            attributTag.appendChild(domain);
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
                // Ajout des parameters
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
        MCDLinkEnd linkEnd = mcdLink.getEndAssociation(); //TODO-STB: peut-être prévoir une méthode qui retourne directement l'association MCD et pas le End. //Dans la méthode, le A est l'entité associative et le B est l'association
        Element association = doc.createElement("association");
        Element name = doc.createElement("name");
        association.appendChild(name);
        if (!linkEnd.getName().equals("")) {
            name.appendChild(doc.createTextNode((linkEnd.getNamePath(1))));
        } else {
            MCDAssociation mcdAssociation = (MCDAssociation) linkEnd.getmElement();
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
        entity.appendChild(doc.createTextNode(((MCDElement) endEntity.getmElement()).getNamePath(1)));
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

    /**
     * Méthode pour ajouter les paramètres (les attributs référencés) des contraintes
     */
    private void addParameters(Document doc, MCDConstraint mcdConstraint, Element constraint) {
        //ArrayList<MCDParameter> parametersChilds = mcdConstraint.getMcdParameters(); //old: à supprimer
        //for (int i = 0; i < parametersChilds.size(); i++) { //old: à supprimer
        for(MCDParameter parameterOfConstraint : mcdConstraint.getMcdParameters()){
            //MCDParameter parameterOfConstraint = parametersChilds.get(i); //old: à supprimer
            // TODO-STB: implémenter les paramètres de contraintes Unique et UID.

            // Créer la balise <parameter> pour chaque paramètre (donc pour chaque attribut)
            Element parameterTag = doc.createElement("parameter");
            constraint.appendChild(parameterTag);

            // Ajout de l'attribut "name" à la balise <parameter>
            Attr nameAttributeOfParameterTag = doc.createAttribute("name");
            nameAttributeOfParameterTag.setValue(parameterOfConstraint.getName());
            parameterTag.setAttributeNode(nameAttributeOfParameterTag);

            // Ajout de l'attribut "target_id" à la balise <parameter>
            Attr targetIdAttributeOfParameterTag = doc.createAttribute("target_id");
            targetIdAttributeOfParameterTag.setValue(String.valueOf(parameterOfConstraint.getTarget().getIdProjectElement()));
            parameterTag.setAttributeNode(targetIdAttributeOfParameterTag);

            // Ajout de l'attribut "target_order" à la balise <parameter>
            Attr targetOrderAttributeOfParameterTag = doc.createAttribute("target_order");
            targetOrderAttributeOfParameterTag.setValue(String.valueOf(parameterOfConstraint.getTarget().getOrder()));
            parameterTag.setAttributeNode(targetOrderAttributeOfParameterTag);

            // Ajout de l'attribut "target_ClassShortNameUI"* à la balise <parameter>
            Attr targetClassShortNameUIAttributeOfParameterTag = doc.createAttribute("target_ClassShortNameUI");
            targetClassShortNameUIAttributeOfParameterTag.setValue(parameterOfConstraint.getTarget().getClassShortNameUI());
            parameterTag.setAttributeNode(targetClassShortNameUIAttributeOfParameterTag);

            //Old: développements de Giorgio Roncallo (semblent inutiles/trop compliqué)
            /*
            Element target = doc.createElement("target");
            Attr targetName = doc.createAttribute("name");
            nameAttributeOfParameterTag.setValue(parameterOfConstraint.getTarget().getName());
            target.setAttributeNode(targetName);

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(String.valueOf(parameterOfConstraint.getTarget().getIdProjectElement())));
            target.appendChild(id);

            Element order = doc.createElement("order");
            order.appendChild(doc.createTextNode(String.valueOf(parameterOfConstraint.getTarget().getOrder())));
            target.appendChild(order);

            Element classShortNameUi = doc.createElement("classShortNameUi");
            classShortNameUi.appendChild(doc.createTextNode(parameterOfConstraint.getTarget().getClassShortNameUI()));
            target.appendChild(classShortNameUi);

             */

        }
    }


    // *** Méthodes de sauvegarde du MCD ***


    /**
     * Sauvegarde du ou des modèles MLDR_DT ou MLDR_TI qui se trouvent sous un modèle MCD.
     * @param doc Document DOM
     * @param models Liste des modèles à parcourir: tous ceux qui sont MLDR_DT ou MLDR_TI seront traités et persistés.
     * @param racineTag balise parent (par exemple, la balise <mcd> du fichier persisté).
     */
    private void addMLD(Document doc, ArrayList<MVCCDElement> models, Element racineTag) {

        //Pour chaque modèle MLDR (que ce soit MLDR_DT ou MLDR_TI)
        for(MVCCDElement model : models){
            if(model instanceof MLDRModel){
                MLDRModel mldrModel = (MLDRModel) model;

                //Création de la balise <MLDR_xx>
                Element mldrTag = null;
                if(mldrModel instanceof MLDRModelDT) {
                    mldrTag = doc.createElement("MLDR_DT");
                }else if(mldrModel instanceof MLDRModelTI){
                    mldrTag = doc.createElement("MLDR_TI");
                }
                racineTag.appendChild(mldrTag);

                //Ajout de l'id à la balise <MLDR_xx>
                Attr idAttrOfmldrTag = doc.createAttribute("id");
                idAttrOfmldrTag.setValue(String.valueOf(mldrModel.getIdProjectElement()));
                mldrTag.setAttributeNode(idAttrOfmldrTag);

                //Persistance des tables
                this.addTables(doc, mldrModel, mldrTag);
            }
        }
    }

    /**
     * Sauvegarde les tables dans une balise <tables> en parcourant le modèle MLDR
     * @param doc Document XML dans lequel la persistance se fait
     * @param mldrModel Modèle MLDR parcouru pour lequel toutes les tables qu'il contient seront persistées.
     * @param mldrTag Balise racine <mldr> qui sera la balise parent de la balise <tables> enfant.
     */
    private void addTables(Document doc, MLDRModel mldrModel, Element mldrTag){

        //Création de la balise <tables>
        Element tablesTag = doc.createElement("tables");
        mldrTag.appendChild(tablesTag);

        //Ajout de l'id à la balise <tables>
        Attr idAttrOfTablesTag = doc.createAttribute("id");
        idAttrOfTablesTag.setValue(String.valueOf(mldrModel.getMDRContTables().getIdProjectElement()));
        tablesTag.setAttributeNode(idAttrOfTablesTag);

        //Parcours des tables
        for(MLDRTable mldrTable : mldrModel.getMLDRTables()){

            //Persistance d'une table
            this.addTable(doc, mldrTable, tablesTag);

        }
    }

    /**
     * Sauvegarde d'une table dans une balise <table>.
     * @param doc Document XML dans lequel la persistance se fait
     * @param mldrTable Table à persister
     * @param tablesTag Balise parent <tables> qui contient la nouvelle balise <table>
     */
    private void addTable(Document doc, MLDRTable mldrTable, Element tablesTag){
        //Création de la balise <table>
        Element tableTag = doc.createElement("table");
        tablesTag.appendChild(tableTag);

        //Ajout de l'attribut "id" à <table>
        Attr tableIdAttr = doc.createAttribute("id");
        tableIdAttr.setValue(String.valueOf(mldrTable.getIdProjectElement()));
        tableTag.setAttributeNode(tableIdAttr);

        //Ajout de l'attribut "name" à <table>
        Attr tableNameAttr = doc.createAttribute("name");
        tableNameAttr.setValue(mldrTable.getName());
        tableTag.setAttributeNode(tableNameAttr);

        //Ajout de l'attribut "entity_source" à <table>
        Attr tableEntitySourceAttr = doc.createAttribute("entity_source");
        tableEntitySourceAttr.setValue(String.valueOf(mldrTable.getMcdElementSource().getIdProjectElement()));
        tableTag.setAttributeNode(tableEntitySourceAttr);

        //Persistance des colonnes
        this.addColumns(doc, mldrTable, tableTag);
    }

    /**
     * Sauvegarde des colonnes d'une table.
     * @param doc Document XML dans lequel les colonnes seront persistées
     * @param mldrTable Table pour laquelle les colonnes qu'elle contient seront persistées
     * @param tableTag Balise parent <table> qui contiendra la nouvelle balise <columns>
     */
    private void addColumns(Document doc, MLDRTable mldrTable, Element tableTag) {

        //Création de la balise <columns>
        Element columnsTag = doc.createElement("columns");
        tableTag.appendChild(columnsTag);

        //Ajout de l'id à la balise <columns>
        Attr idAttrOfColumnsTag = doc.createAttribute("id");
        idAttrOfColumnsTag.setValue(String.valueOf(mldrTable.getMDRContColumns().getIdProjectElement()));
        columnsTag.setAttributeNode(idAttrOfColumnsTag);

        //Parcours des colonnes
        for(MLDRColumn mldrColumn : mldrTable.getMLDRColumns()){

            //Persistance d'une colonne
            //TODO-STB: continuer ici avec la persistance d'une colonne
        }
    }
}
