package project;

import diagram.Diagram;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDFactory;
import mcd.MCDAssEnd;
import mcd.MCDAssociation;
import mcd.MCDAssociationNature;
import mcd.MCDAttribute;
import mcd.MCDConstraint;
import mcd.MCDContAttributes;
import mcd.MCDContConstraints;
import mcd.MCDContDiagrams;
import mcd.MCDContEntities;
import mcd.MCDContModels;
import mcd.MCDContRelations;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.MCDModel;
import mcd.MCDNID;
import mcd.MCDPackage;
import mcd.MCDParameter;
import mcd.MCDUnique;
import mcd.interfaces.IMCDSourceMLDRTable;
import mdr.MDRCaseFormat;
import mdr.MDRColumn;
import mdr.MDRConstraint;
import mdr.MDRContColumns;
import mdr.MDRContConstraints;
import mdr.MDRContRelEnds;
import mdr.MDRContTables;
import mdr.MDRElementNames;
import mdr.MDRFK;
import mdr.MDRModel;
import mdr.MDRNamingLength;
import mdr.MDRPK;
import mdr.MDRRelFKEnd;
import mdr.MDRRelationFK;
import mdr.MDRTable;
import messages.MessagesBuilder;
import mldr.MLDRColumn;
import mldr.MLDRContRelEnds;
import mldr.MLDRContRelations;
import mldr.MLDRFK;
import mldr.MLDRModel;
import mldr.MLDRPK;
import mldr.MLDRParameter;
import mldr.MLDRRelFKEnd;
import mldr.MLDRTable;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.mysql.MPDRMySQLColumn;
import mpdr.mysql.MPDRMySQLModel;
import mpdr.mysql.MPDRMySQLTable;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import mpdr.oracle.MPDROracleColumn;
import mpdr.oracle.MPDROracleModel;
import mpdr.oracle.MPDROracleTable;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.postgresql.MPDRPostgreSQLColumn;
import mpdr.postgresql.MPDRPostgreSQLModel;
import mpdr.postgresql.MPDRPostgreSQLTable;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import preferences.Preferences;
import profile.Profile;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.LabelType;
import window.editor.diagrammer.elements.shapes.relations.MCDAssociationShape;
import window.editor.diagrammer.elements.shapes.relations.RelationPointAncrageShape;

public class ProjectLoaderXml {

  // Version de l'application utilisée par le projet XML chargé
  private String version = null;

  // listes d'éléments nécessaires pour récupérer les conteneurs des paquetages, des entités et les associations
  private ArrayList<Element> elementsPackages = new ArrayList<>();
  private ArrayList<Element> elementsEntities = new ArrayList<>();
  private ArrayList<MVCCDElement> loadedPackages = new ArrayList<>();
  private ArrayList<MVCCDElement> loadedEntities = new ArrayList<>();
  private ArrayList<MVCCDElement> loadedAssociations = new ArrayList<>();
  private NodeList diagramTagsList = null; //Contient la liste des enfants de <diagrammes>

  public Project loadProjectFile(File fileProjectCurrent) throws IOException, SAXException, ParserConfigurationException {
    Project project = null;
    try {
      // Création du document et du parser pour récupérer les information du fichier
      FileInputStream fis = new FileInputStream(fileProjectCurrent);
      Reader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
      InputSource is = new InputSource(reader);
      //is.setEncoding("UTF-8"); //à priori pas nécessaire
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document document = builder.parse(is);

      // Assignation du schéma XSD au fichier pour validation
      SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = factory.newSchema(new File("schemas/SchemaProject.xsd"));
      Validator validator = schema.newValidator();

      // Récupération de la racine du fichier XML (balise <project>)
      Element projectTag = document.getDocumentElement();

      // Chargement du projet et du nom du projet
      project = new Project(projectTag.getAttribute("id"));
      project.setName(projectTag.getElementsByTagName("nameProject").item(0).getTextContent());

      // Chargement de la version
      this.version = projectTag.getElementsByTagName("version").item(0).getTextContent();

      // Initialisation des préférences du projet
      Preferences preferences = MVCCDElementFactory.instance().createPreferences(project, Preferences.REPOSITORY_PREFERENCES_NAME); //Ceci va automatiquement créer un objet "Preferences" qui se trouve dans le référentiel sous le Project.

      // Ajout des éléments du projet
        this.addPropertiesProject(projectTag, project);
        this.addPreferences(projectTag, preferences);

      // Chargement du modèle MCD
      Element mcdTag = (Element) projectTag.getElementsByTagName("MCD").item(0);
      MCDContModels mcd = MVCCDElementFactory.instance().createMCDModels(project, Integer.parseInt(mcdTag.getAttribute("id")));
      mcd.setName(Preferences.REPOSITORY_MCD_MODELS_NAME);

      // Chargement des modèles ou des 3 conteneurs principaux
      ArrayList<Element> modelsTagsList = this.loadModels(mcd, mcdTag);
      // Chargement des packages
        this.loadPackages(mcd, mcdTag);
      // Chargement des entités
        this.loadEntities(mcd, mcdTag, modelsTagsList);
      // Chargement des attributs
        this.loadAttributs();
      // Chargements des contraintes
        this.loadContraints();
      // Chargements des rélations ( associations et généralisations)
        this.loadRelations(mcd, mcdTag, modelsTagsList);
      // Chargements des liens d'entités associatives
        this.loadLinks(mcd, mcdTag, modelsTagsList);

      //Chargement du (ou des) MLDR
        this.loadMLDRs(mcd, mcdTag);

      // Chargement des diagrammes
      Element diagramsTag = (Element) projectTag.getElementsByTagName(Preferences.NODE_DIAGRAMS).item(0);
        this.loadMCDDiagrams(project, diagramsTag, mcd);

      // Validation du fichier
      validator.validate(new DOMSource(document));

    } catch (ParserConfigurationException | SAXException | IOException e) {
      //#MAJ 2021-06-06 TODO-PAS STB faire un throw(e) - Intégration dans la transaction
      throw (e);
      //e.printStackTrace();
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

    // Récupération de la dernière valeur de la séquence des éléments du projet
    Element idElementSequence = (Element) racine.getElementsByTagName("idElementSequence").item(0);
    project.setIdElementSequence(Integer.parseInt(idElementSequence.getTextContent()));
  }

  public void addPreferences(Element racine, Preferences preferences) {

    //Récupération et instantiation des préférences de projet

    //Préférences général
    preferences.setGENERAL_RELATION_NOTATION(this.getTextInTag(racine, "generalRelationNotation"));

    //Préférences MCD
    preferences.setMCD_JOURNALIZATION(this.getBooleanInTag(racine, "mcdJournalization"));
    preferences.setMCD_JOURNALIZATION_EXCEPTION(this.getBooleanInTag(racine, "mcdJournalizationException"));
    preferences.setMCD_AUDIT(this.getBooleanInTag(racine, "mcdAudit"));
    preferences.setMCD_AUDIT_EXCEPTION(this.getBooleanInTag(racine, "mcdAuditException"));
    preferences.setMCD_AID_DATATYPE_LIENPROG(this.getTextInTag(racine, "mcdAidDataTypeLienProg"));
    preferences.setMCDDATATYPE_NUMBER_SIZE_MODE(this.getTextInTag(racine, "mcdDataTypeNumberSizeMode"));
    preferences.setMCD_AID_IND_COLUMN_NAME(this.getTextInTag(racine, "mcdAidIndColumnName"));
    preferences.setMCD_AID_DEP_COLUMN_NAME(this.getTextInTag(racine, "mcdAidDepColumnName"));
    preferences.setMCD_AID_WITH_DEP(this.getBooleanInTag(racine, "mcdAidWithDep"));
    preferences.setMCD_TREE_NAMING_ASSOCIATION(this.getTextInTag(racine, "mcdTreeNamingAssociation"));
    preferences.setMCD_MODE_NAMING_LONG_NAME(this.getTextInTag(racine, "mcdModeNamingLongName"));
    preferences.setMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME(this.getTextInTag(racine, "mcdModeNamingAttributeShortName"));

    // Préférences MCDToMLDR
    preferences.setMCDTOMLDR_MODE(this.getTextInTag(racine, "mcdToMldrMode"));

    //Préférences MLDRToMPDR
    preferences.setMLDRTOMPDR_DB(this.getTextInTag(racine, "mldrToMpdrDb"));

    //Préférences MDR Format
    preferences.setMDR_TABLE_NAME_FORMAT(this.getTextInTag(racine, "mdrTableNameFormat"));
    preferences.setMDR_TABLE_NN_NAME_FORMAT(this.getTextInTag(racine, "mdrTableNNNameFormat"));
    preferences.setMDR_TABLE_NN_NAME_INDICE_FORMAT(this.getTextInTag(racine, "mdrTableNNNameIndiceFormat"));
    preferences.setMDR_COLUMN_ATTR_NAME_FORMAT(this.getTextInTag(racine, "mdrColumnAttrNameFormat"));
    preferences.setMDR_COLUMN_ATTR_SHORT_NAME_FORMAT(this.getTextInTag(racine, "mdrColumnAttrShortNameFormat"));
    preferences.setMDR_COLUMN_DERIVED_MARKER(this.getTextInTag(racine, "mdrColumnDerivedMarker"));
    preferences.setMDR_PK_NAME_FORMAT(this.getTextInTag(racine, "mdrPkNameFormat"));
    preferences.setMDR_COLUMN_PK_NAME_FORMAT(this.getTextInTag(racine, "mdrColumnPkNameFormat"));
    preferences.setMDR_COLUMN_FK_NAME_FORMAT(this.getTextInTag(racine, "mdrColumnfkNameFormat"));
    preferences.setMDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT(this.getTextInTag(racine, "mdrColumnFkNameOneAncestorFormat"));
    preferences.setMDR_FK_NAME_FORMAT(this.getTextInTag(racine, "mdrFkNameFormat"));
    preferences.setMDR_FK_NAME_WITHOUT_ROLE_FORMAT(this.getTextInTag(racine, "mdrFkNameWithoutRoleFormat"));
    preferences.setMDR_ROLE_GENERALIZE_MARKER(this.getTextInTag(racine, "mdrRoleGeneralizeMarker"));
    preferences.setMDR_PATH_SEP_FORMAT(this.getTextInTag(racine, "mdrPathSepFormat"));
    preferences.setMDR_PEA_SEP_FORMAT(this.getTextInTag(racine, "mdrPEASepFormat"));
    preferences.setMDR_TABLE_SEP_FORMAT(this.getTextInTag(racine, "mdrTableSepFormat"));
    preferences.setMDR_ROLE_SEP_FORMAT(this.getTextInTag(racine, "mdrRoleSepFormat"));
    preferences.setMDR_FKIND_SEP_FORMAT(this.getTextInTag(racine, "mdrFkIndSepFormat"));

    //Préférences MDR
    preferences.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR(this.getBooleanInTag(racine, "mdrPrefColumnFkOneAncestor"));
    preferences.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF(this.getTextInTag(racine, "mdrPrefColumnFkOneAncestorDiff"));

    // Préférences MLDR (préférences de type enum)
    switch (this.getIntegerInTag(racine, "mldrPrefNamingLengthLength")) {
      case 30:
        preferences.setMLDR_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH30);
        break;
      case 60:
        preferences.setMLDR_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH60);
        break;
      case 120:
        preferences.setMLDR_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH120);
        break;
    }
    switch (this.getTextInTag(racine, "mldrPrefNamingFormatName")) {
      case "mdr.naming.format.nothing":
        preferences.setMLDR_PREF_NAMING_FORMAT(MDRCaseFormat.NOTHING);
        break;
      case "mdr.naming.format.uppercase":
        preferences.setMLDR_PREF_NAMING_FORMAT(MDRCaseFormat.UPPERCASE);
        break;
      case "mdr.naming.format.lowercase":
        preferences.setMLDR_PREF_NAMING_FORMAT(MDRCaseFormat.LOWERCASE);
        break;
      case "mdr.naming.format.capitalize":
        preferences.setMLDR_PREF_NAMING_FORMAT(MDRCaseFormat.CAPITALIZE);
        break;
    }

    // Préférences MPDR Oracle (préférences de type enum)
    switch (this.getIntegerInTag(racine, "mpdrOraclePrefNamingLengthLength")) {
      case 30:
        preferences.setMPDRORACLE_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH30);
        break;
      case 60:
        preferences.setMPDRORACLE_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH60);
        break;
      case 120:
        preferences.setMPDRORACLE_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH120);
        break;
    }
    switch (this.getTextInTag(racine, "mpdrOraclePrefNamingFormatName")) {
      case "mdr.naming.format.nothing":
        preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRCaseFormat.NOTHING);
        break;
      case "mdr.naming.format.uppercase":
        preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRCaseFormat.UPPERCASE);
        break;
      case "mdr.naming.format.lowercase":
        preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRCaseFormat.LOWERCASE);
        break;
      case "mdr.naming.format.capitalize":
        preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRCaseFormat.CAPITALIZE);
        break;
    }

    // Préférences MPDR MySQL (préférences de type enum)
    switch (this.getIntegerInTag(racine, "mpdrMySQLPrefNamingLengthLength")) {
      case 30:
        preferences.setMPDRMYSQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH30);
        break;
      case 60:
        preferences.setMPDRMYSQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH60);
        break;
      case 120:
        preferences.setMPDRMYSQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH120);
        break;
    }
    switch (this.getTextInTag(racine, "mpdrMySQLPrefNamingFormatName")) {
      case "mdr.naming.format.nothing":
        preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRCaseFormat.NOTHING);
        break;
      case "mdr.naming.format.uppercase":
        preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRCaseFormat.UPPERCASE);
        break;
      case "mdr.naming.format.lowercase":
        preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRCaseFormat.LOWERCASE);
        break;
      case "mdr.naming.format.capitalize":
        preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRCaseFormat.CAPITALIZE);
        break;
    }

    // Préférences MPDR PostgreSQL (préférences de type enum)
    switch (this.getIntegerInTag(racine, "mpdrPostgreSQLPrefNamingLengthLength")) {
      case 30:
        preferences.setMPDRPOSTGRESQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH30);
        break;
      case 60:
        preferences.setMPDRPOSTGRESQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH60);
        break;
      case 120:
        preferences.setMPDRPOSTGRESQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH120);
        break;
    }
    switch (this.getTextInTag(racine, "mpdrPostgreSQLPrefNamingFormatName")) {
      case "mdr.naming.format.nothing":
        preferences.setMPDRPOSTGRESQL_PREF_NAMING_FORMAT(MDRCaseFormat.NOTHING);
        break;
      case "mdr.naming.format.uppercase":
        preferences.setMPDRPOSTGRESQL_PREF_NAMING_FORMAT(MDRCaseFormat.UPPERCASE);
        break;
      case "mdr.naming.format.lowercase":
        preferences.setMPDRPOSTGRESQL_PREF_NAMING_FORMAT(MDRCaseFormat.LOWERCASE);
        break;
      case "mdr.naming.format.capitalize":
        preferences.setMPDRPOSTGRESQL_PREF_NAMING_FORMAT(MDRCaseFormat.CAPITALIZE);
        break;
    }
  }

  /**
   * Retourne la valeur du premier noeud texte de la balise portant le nom donné.
   * @param root Balise racine dans laquelle rechercher la balise portant le nom donné. La recherche se fait en profondeur.
   * @param tagName Nom de la balise à rechercher.
   * @return Valeur du texte contenu dans le premier noeud texte de la balise trouvée.
   */
  private String getTextInTag(Element root, String tagName) {
    return ((Element) root.getElementsByTagName(tagName).item(0)).getTextContent();
  }

  /**
   * Comme getTextInTag, sauf que la valeur retournée est castée en type Boolean.
   * @param root Balise racine dans laquelle rechercher la balise portant le nom donné. La recherche se fait en profondeur.
   * @param tagName Nom de la balise à rechercher.
   * @return Valeur Boolean correspondant au texte contenu dans le premier noeud texte de la balise trouvée
   */
  private Boolean getBooleanInTag(Element root, String tagName) {
    return Boolean.valueOf(this.getTextInTag(root, tagName));
  }

  /**
   * Comme getTextInTag, sauf que la valeur retournée est castée en type Integer.
   * @param root Balise racine dans laquelle rechercher la balise portant le nom donné. La recherche se fait en profondeur.
   * @param tagName Nom de la balise à rechercher.
   * @return Valeur Integer correspondant au texte contenu dans le premier noeud texte de la balise trouvée
   */
  private Integer getIntegerInTag(Element root, String tagName) {
    return Integer.valueOf(this.getTextInTag(root, tagName));
  }

  private ArrayList<Element> loadModels(MCDContModels mcd, Element mcdTag) {
    // Créations de la listes des éléments modèles à renvoyer
    ArrayList<Element> modelsTagsList = new ArrayList<>();
    // Parcours des enfants de MCD
    NodeList childsOfMcdTag = mcdTag.getChildNodes();
    for (int i = 0; i < childsOfMcdTag.getLength(); i++) {
      if (childsOfMcdTag.item(i) instanceof Element) {
        Element childOfMcdTag = (Element) childsOfMcdTag.item(i); //peut être <model>, <diagrammes>, <entities>, <relations> ou <MLDR_DT>
                /*
                Chargement d'un modèle
                 */
        if (childOfMcdTag.getNodeName().equals("model")) {
          // Alimentation de la listes des éléments modèles
          modelsTagsList.add(childOfMcdTag);

          // Création du modèle MCD
          MCDModel mcdModel = MVCCDElementFactory.instance().createMCDModel(mcd, Integer.parseInt(childOfMcdTag.getAttribute("id")));
          mcdModel.setName(childOfMcdTag.getAttribute("name"));

          // Ajout des propriétés du modèle
            this.addPropertiesModelOrPackage(mcdModel, childOfMcdTag);
        }
                /*
                Chargement des conteneurs sans modèle
                 */
/*                //Chargement des diagrammes
                if(childOfMcdTag.getNodeName().equals("diagrammes")) {
                    MCDContDiagrams mcdContDiagrams = MVCCDElementFactory.instance().createMCDDiagrams(mcd, Integer.parseInt(childOfMcdTag.getAttribute("id")));
                    mcdContDiagrams.setName(Preferences.REPOSITORY_MCD_DIAGRAMS_NAME);
                    this.diagramTagsList = childOfMcdTag.getChildNodes(); //Récupération des enfants de <diagrammes>, c'est-à-dire de la liste de chaque <diagramme>.
                }*/
        else if (childOfMcdTag.getNodeName().equals("entities")) {
          MVCCDElementFactory.instance().createMCDEntities(mcd, Preferences.REPOSITORY_MCD_ENTITIES_NAME);
        } else if (childOfMcdTag.getNodeName().equals("relations")) {
          MVCCDElementFactory.instance().createMCDContRelations(mcd, Preferences.REPOSITORY_MCD_RELATIONS_NAME);
        } else if (childOfMcdTag.getNodeName().equals("MLDR_DT")) {
          MVCCDElementFactory.instance().createMLDRModelDT(mcd, Integer.parseInt(childOfMcdTag.getAttribute("id")));
        } else if (childOfMcdTag.getNodeName().equals("MLDR_TI")) {
          MVCCDElementFactory.instance().createMLDRModelTI(mcd, Integer.parseInt(childOfMcdTag.getAttribute("id")));
        }
      }
    }
    return modelsTagsList;
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
            this.addPackagesModels(childs, modelElement);
        }
        // Ajout des paquetages sans modèles
          this.addPackages(mcdChild, mcd);
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
            this.elementsPackages.add(pack);

          // Récupération du nom du parent du paquetage
          Element nameParent = (Element) pack.getElementsByTagName("parent").item(0);

          // Récupération du modèle en lien avec le paquetage
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
              this.loadedPackages.add(mcdPackage);
            // Ajout des propriétés du paquetage
              this.addPropertiesModelOrPackage(mcdPackage, pack);
            // Ajout d'autres paquetages
              this.loadOthersPackages(mcdPackage, pack);
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
          this.addPackages(packChild, mcdPackage);

      }
    }
  }

  private void addPackages(Node packChild, MCDElement paquetage) {
    if (packChild.getNodeName().equals("package")) {
      // Création de l'element de paquetage
      Element pack = (Element) packChild;
      // Remplissage de la listes des éléments pour les paquetages
        this.elementsPackages.add(pack);
      // Création du paquetage
      MCDPackage mcdPackage = MVCCDElementFactory.instance().createMCDPackage(paquetage);
      mcdPackage.setName(pack.getAttribute("name"));
      // Remplissage de la listes des MVCCDElements pour les paquetages
        this.loadedPackages.add(mcdPackage);
      // Ajout des propriétés du paquetage
        this.addPropertiesModelOrPackage(mcdPackage, pack);
      // Ajout d'autres paquetages
        this.loadOthersPackages(mcdPackage, pack);
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

  private void loadMCDDiagrams(Project project, Element diagramsTag, MCDContModels mcdSource) {

    // Création du contenur de diagrammes MCD
    MCDContDiagrams mcdContDiagrams = MVCCDElementFactory.instance().createMCDDiagrams(mcdSource, Integer.parseInt(diagramsTag.getAttribute(Preferences.ATTRIBUTE_ID)));
    mcdContDiagrams.setName(Preferences.REPOSITORY_MCD_DIAGRAMS_NAME);

    // Parcours de tous les diagrammes MCD
    for (int i = 0; i < diagramsTag.getChildNodes().getLength(); i++) {
      Node currentElement = diagramsTag.getChildNodes().item(i);
      if (currentElement instanceof Element) {

        Element diagramTag = (Element) currentElement;

        // Récupère l'ID du parent pour déterminer s'il s'agit d'un diagramme de projet, MCD, etc.
        int parentId = Integer.parseInt(diagramTag.getAttribute(Preferences.ATTRIBUTE_PARENT_ID));
        ProjectElement parent = ProjectService.getProjectElementById(project, parentId);

        Diagram diagram = null;

        // Si le parent est un MCD
        if (parent instanceof MCDContModels) {
          diagram = MVCCDElementFactory.instance().createMCDDiagram(mcdContDiagrams, Integer.parseInt(diagramTag.getAttribute(Preferences.ATTRIBUTE_ID)));
          diagram.setName(diagramTag.getAttribute(Preferences.ATTRIBUTE_NAME));
        }

        // Chargement des shapes
        if (diagramTag.getElementsByTagName(Preferences.NODE_SHAPES).getLength() > 0) {
            this.loadClassShapes(diagramTag, diagram, mcdSource);
            this.loadRelationShapes(diagramTag, diagram, mcdSource);
        }
      }
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
          this.addEntities(mcdContEntities, element);
      }
    }
    // entités avec paquetages
    for (MVCCDElement mvccdElement : this.loadedPackages) {
      ArrayList<MVCCDElement> childsPackage = mvccdElement.getChilds();
        this.addEntitiesPackagesOrModels(childsPackage, this.elementsPackages, mvccdElement);

    }
    // entités avec modèles
    for (MVCCDElement mcdChild : mcdElements) {
      if (mcdChild instanceof MCDModel) {
        ArrayList<MVCCDElement> childModel = mcdChild.getChilds();
          this.addEntitiesPackagesOrModels(childModel, elementsModeles, mcdChild);

      }
    }
  }

  private void addEntitiesPackagesOrModels(ArrayList<MVCCDElement> listMVCCD, ArrayList<Element> listElement, MVCCDElement mvccdElement) {
    // Ajout des entités pour les paquetage ou les modèles
    MCDContEntities mcdContEntities;
    // Parcours de la liste des éléments MVCCD de paquetage ou de modèle
      for (MVCCDElement elementChild : listMVCCD) {
          if (elementChild instanceof MCDContEntities) {
              // Récupération du conteneur des entités
              mcdContEntities = (MCDContEntities) elementChild;
              for (Element element : listElement) {
                  // // Ajout des entités si l'élément parent(paquetage ou modèle) cré dans l'application est égal à celui du fichier
                  if (element.getAttribute("name").equals(mvccdElement.getName())) {
                      this.addEntities(mcdContEntities, element);
                  }
              }
          }
      }
  }

  private void addEntities(MCDContEntities mcdContEntities, Element element) {
    // Récupération de la balise entities
    Element entitiesTag = (Element) element.getElementsByTagName("entities").item(0);
    // Parcours des entités
    NodeList entitiesChilds = entitiesTag.getChildNodes();
    for (int i = 0; i < entitiesChilds.getLength(); i++) {
      Node entitiesChild = entitiesChilds.item(i);
      if (entitiesChild instanceof Element) {
        // Création de l'element entité
        Element entityTag = (Element) entitiesChild;
        // Remplissage de la listes des éléments pour les entités
          this.elementsEntities.add(entityTag);

        // Création de l'entité
        MCDEntity mcdEntity = MVCCDElementFactory.instance().createMCDEntity(mcdContEntities, Integer.parseInt(entityTag.getAttribute("id")));
        mcdEntity.setName(entityTag.getAttribute("name"));

        // Remplissage de la listes des éléments MVCCD pour les entités
          this.loadedEntities.add(mcdEntity);
        // // Ajout des propriétés des entités
          this.addPropertiesEntities(mcdEntity, entityTag);
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

    Element mldrTableName = (Element) entite.getElementsByTagName("mldrTableName").item(0);
      if (mldrTableName != null) {
          mcdEntity.setMldrTableName(mldrTableName.getTextContent());
      }

  }

  private void loadAttributs() {
    // Chargement des attributs
    MCDContAttributes mcdContAttributes;
    // Parcours des éléments MVCCD afin de récupérer les conteneurs d'attributs de chaque entité
    for (MVCCDElement mvccdElement : this.loadedEntities) {
      ArrayList<MVCCDElement> childEntities = mvccdElement.getChilds();
      for (MVCCDElement contEntities : childEntities) {
        if (contEntities instanceof MCDContAttributes) {
          // Récupération des conteneurs d'attributs
          mcdContAttributes = (MCDContAttributes) contEntities;
          for (Element child : this.elementsEntities) {
            // Ajout des attributs si l'élément entité créée dans l'application est égale à celui du fichier
            if (child.getAttribute("name").equals(mvccdElement.getName())) {
                this.addAttributs(mcdContAttributes, child);
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
        Element attributeTag = (Element) attributsChild; // Récupération de la balise <attribut>
        int attributeId = Integer.parseInt(attributeTag.getAttribute("id")); // Récupération de l'id de l'attribut
        MCDAttribute mcdAttribute = MVCCDElementFactory.instance().createMCDAttribute(mcdContAttributes, attributeId); // Création de l'attribut dans l'application
        mcdAttribute.setName(attributeTag.getAttribute("name")); //Attribuer le "name" de l'attribut
          this.addPropertiesAttributs(mcdAttribute, attributeTag); // Ajout des propriétés de l'attribut (des balises enfants)
      }
    }
  }

  private void addPropertiesAttributs(MCDAttribute mcdAttribute, Element attributeTag) {

    // Récupération des propriétés d'attribut
    Element aid = (Element) attributeTag.getElementsByTagName("aid").item(0);
    mcdAttribute.setAid(Boolean.valueOf(aid.getTextContent()));

    Element aidDep = (Element) attributeTag.getElementsByTagName("aidDep").item(0);
    mcdAttribute.setAidDep(Boolean.valueOf(aidDep.getTextContent()));

    Element mandatory = (Element) attributeTag.getElementsByTagName("mandatory").item(0);
    mcdAttribute.setMandatory(Boolean.valueOf(mandatory.getTextContent()));

    Element list = (Element) attributeTag.getElementsByTagName("list").item(0);
    mcdAttribute.setList(Boolean.valueOf(list.getTextContent()));

    Element frozen = (Element) attributeTag.getElementsByTagName("frozen").item(0);
    mcdAttribute.setFrozen(Boolean.valueOf(frozen.getTextContent()));

    Element ordered = (Element) attributeTag.getElementsByTagName("ordered").item(0);
    mcdAttribute.setOrdered(Boolean.valueOf(ordered.getTextContent()));

    Element upperCase = (Element) attributeTag.getElementsByTagName("upperCase").item(0);
    mcdAttribute.setUppercase(Boolean.valueOf(upperCase.getTextContent()));

    Element dataTypeLienProg = (Element) attributeTag.getElementsByTagName("dataTypeLienProg").item(0);
    mcdAttribute.setDatatypeLienProg(dataTypeLienProg.getTextContent());

    // Test pour éviter le nullPointException
    Element scale = (Element) attributeTag.getElementsByTagName("scale").item(0);
    if (!scale.getTextContent().equals("")) {
      mcdAttribute.setScale(Integer.valueOf(scale.getTextContent()));
    }
    Element size = (Element) attributeTag.getElementsByTagName("size").item(0);
    if (!size.getTextContent().equals("")) {
      mcdAttribute.setSize(Integer.valueOf(size.getTextContent()));
    }

    Element initValue = (Element) attributeTag.getElementsByTagName("initValue").item(0);
    mcdAttribute.setInitValue(initValue.getTextContent());

    Element derivedValue = (Element) attributeTag.getElementsByTagName("derivedValue").item(0);
    mcdAttribute.setDerivedValue(derivedValue.getTextContent());

    Element domain = (Element) attributeTag.getElementsByTagName("domain").item(0);
    mcdAttribute.setDomain(domain.getTextContent());

  }

  private void loadContraints() {
    // Chargement des contraintes
    MCDContConstraints mcdContConstraints;
    // Parcours des éléments MVCCD afin de récupérer les conteneurs des contraintes de chaque entité
    for (MVCCDElement mvccdElement : this.loadedEntities) {
      ArrayList<MVCCDElement> childEntities = mvccdElement.getChilds();
      for (MVCCDElement contConstraints : childEntities) {
        if (contConstraints instanceof MCDContConstraints) {
          // Récupération des conteneurs des contraintes
          mcdContConstraints = (MCDContConstraints) contConstraints;
          for (Element child : this.elementsEntities) {
            // Ajout des contraintes si l'élément entité créé dans l'application est égal à celui du fichier
            if (child.getAttribute("name").equals(mvccdElement.getName())) {
                this.addContraints(mcdContConstraints, child);
            }
          }
        }
      }
    }
  }

  private void addContraints(MCDContConstraints mcdContConstraints, Element entityTag) {
    // Récupération de la balise contraintes
    Element constraintsTag = (Element) entityTag.getElementsByTagName("contraintes").item(0);
    // Parcours des éléments enfant de la balise contraintes
    NodeList constraintsChilds = constraintsTag.getChildNodes();
    for (int i = 0; i < constraintsChilds.getLength(); i++) {
      if (constraintsChilds.item(i) instanceof Element) {
        // Création de l'element attribut
        Element constraintTag = (Element) constraintsChilds.item(i);
        // Ajout de la contrainte NID ou Unique en fonction de son type
          this.addTypeConstraintes(constraintTag, mcdContConstraints);
      }
    }
  }

  private void addTypeConstraintes(Element constraintTag, MCDContConstraints mcdContConstraints) {
    Element typeConstraint = (Element) constraintTag.getElementsByTagName("type").item(0); //Récupération du type
    Element shortNameTag = (Element) constraintTag.getElementsByTagName("shortName").item(0); // Récupération du shortName de l'attribut
    MCDConstraint mcdConstraint = null;

    // Contraintes de type NID
    if (typeConstraint.getTextContent().equals("NID")) {
      mcdConstraint = MVCCDElementFactory.instance().createMCDNID(mcdContConstraints, Integer.parseInt(constraintTag.getAttribute("id"))); // Création de la contrainte
      Element lienProg = (Element) constraintTag.getElementsByTagName("lienProg").item(0); //Attribuer le "lienProg" de la contrainte
      ((MCDNID) mcdConstraint).setLienProg(Boolean.valueOf(lienProg.getTextContent()));           //idem suite
    }
    // Contraintes de type Unique
    else if (typeConstraint.getTextContent().equals("Unique")) {
      mcdConstraint = MVCCDElementFactory.instance().createMCDUnique(mcdContConstraints, Integer.parseInt(constraintTag.getAttribute("id"))); // Création de la contrainte
      Element absolute = (Element) constraintTag.getElementsByTagName("absolute").item(0); //Attribuer le "absolute" de la contrainte
      ((MCDUnique) mcdConstraint).setAbsolute(Boolean.valueOf(absolute.getTextContent()));        //idem suite
    }

    //Attribuer les éléments communs pour NID et Unique
    mcdConstraint.setName(constraintTag.getAttribute("name")); // Attribuer le "name" de la contrainte
    mcdConstraint.setShortName(shortNameTag.getTextContent()); //Attribuer le "shortname" de la contrainte

    //Attribuer les parameters (c'est-à-dire les attributs liés à la contrainte)
    //<parameter name="code" target_ClassShortNameUI="Attribute" target_id="36" target_order="10"/>
    NodeList parametersNodes = constraintTag.getElementsByTagName("parameter");
    for (int i = 0; i < parametersNodes.getLength(); i++) { //Boucle toutes les balises <parameter> de la contrainte
      Element parameterTag = (Element) parametersNodes.item(i); //Récupération de la balise <parameter>
      int ParameterTargetId = Integer.parseInt(parameterTag.getAttribute("target_id")); //Récupération du target_id de <parameter>
      MCDParameter mcdParameter = MVCCDElementFactory.instance().createMCDParameter(mcdConstraint); //Création du parameter dans l'application
      MCDAttribute mcdTargetAttribute = ((MCDEntity) mcdContConstraints.getMCDParent()).getMCDAttributeById(ParameterTargetId); //Recherche de l'attribut de l'entité qui porte cet id dans l'application
      mcdParameter.setTarget(mcdTargetAttribute); //Affecter l'attribut de l'entité comme paramètre de la contrainte
    }
  }

  private void loadRelations(MCDContModels mcd, Element mcdTag, ArrayList<Element> modelsTagsList) {
    ArrayList<MVCCDElement> mcdChilds = mcd.getChilds();

    // Recherche du conteneur de relations existant (créé automatiquement lors de la création du modèle MCD)
    for (MVCCDElement mcdChild : mcdChilds) {
      if (mcdChild instanceof MCDContRelations) {
          this.addRelations((MCDContRelations) mcdChild, mcdTag);
      }
    }

    // relations avec paquetages
    for (MVCCDElement mvccdElement : this.loadedPackages) {
      ArrayList<MVCCDElement> childsPackage = mvccdElement.getChilds();
        this.addRelationsPackagesOrModels(childsPackage, this.elementsPackages, mvccdElement);
    }

    // relations avec modèles
    for (MVCCDElement mcdChild : mcdChilds) {
      if (mcdChild instanceof MCDModel) {
        ArrayList<MVCCDElement> childModel = mcdChild.getChilds();
          this.addRelationsPackagesOrModels(childModel, modelsTagsList, mcdChild);
      }
    }
  }

  private void addRelationsPackagesOrModels(ArrayList<MVCCDElement> listMVCCD, ArrayList<Element> listElement, MVCCDElement mvccdElement) {
    //Ajout des relations pour les paquetage ou les modèles
    MCDContRelations mcdContRelations;
    // Parcours de la liste des éléments MVCCD de paquetage ou de modèle
      for (MVCCDElement elementChild : listMVCCD) {
          if (elementChild instanceof MCDContRelations) {
              // Récupération du conteneur des relations
              mcdContRelations = (MCDContRelations) elementChild;
              for (Element element : listElement) {
                  // Ajout des relations si l'élément parent(paquetage ou modèle) cré dans l'application est égal à celui du fichier
                  if (element.getAttribute("name").equals(mvccdElement.getName())) {
                      this.addRelations(mcdContRelations, element);
                  }
              }
          }
      }
  }

  private void addRelations(MCDContRelations mcdContRelations, Element mcdTag) {
    // Récupération de la balise <relations>
    Element relationsTag = (Element) mcdTag.getElementsByTagName("relations").item(0);

    // Parcours des balises sous <relations>
    NodeList relationsChilds = relationsTag.getChildNodes();
    for (int i = 0; i < relationsChilds.getLength(); i++) {
      if (relationsChilds.item(i) instanceof Element) {
        Element relationsChildTag = (Element) relationsChilds.item(i);

        // Chargement des associations
        if (relationsChildTag.getNodeName().equals("associations")) {
            this.addAssociations(mcdContRelations, relationsChildTag);
        }

        //Chargement des généralisations
        if (relationsChildTag.getNodeName().equals("generalisations")) {
            this.addGeneralisation(mcdContRelations, relationsChildTag);
        }
      }
    }
  }

  private void addAssociations(MCDContRelations mcdContRelations, Element associationsTag) {
    // Parcours des balises enfant de <associations>
    NodeList associationChilds = associationsTag.getChildNodes();
    for (int i = 0; i < associationChilds.getLength(); i++) {
      if (associationChilds.item(i) instanceof Element) {
        Element associationTag = (Element) associationChilds.item(i);

        // Récupération des extrémités d'association
        Element extremiteFromTag = (Element) associationTag.getElementsByTagName("roleExtremiteFrom").item(0);
        Element extremiteToTag = (Element) associationTag.getElementsByTagName("roleExtremiteTo").item(0);

        // Récupération des id de l'association et des extrémités d'association
        int assId = Integer.parseInt(associationTag.getAttribute("id"));
        int assEndFromId = Integer.parseInt(extremiteFromTag.getAttribute("id"));
        int assEndToId = Integer.parseInt(extremiteToTag.getAttribute("id"));

        // Récupération des id des entités cibles dans les 2 extrémités de l'association
        int extremiteFromTargetEntityId = Integer.parseInt(extremiteFromTag.getAttribute("target_entity_id"));
        int extremiteToTargetEntityId = Integer.parseInt(extremiteToTag.getAttribute("target_entity_id"));

        // Création du lien entre l'association et les entités To et From de l'association (qui sont déjà créées dans l'application)
        MCDEntity entityFrom = this.getAlreadyLoadedEntityById(extremiteFromTargetEntityId);
        MCDEntity entityTo = this.getAlreadyLoadedEntityById(extremiteToTargetEntityId);

        // Création de l'association dans l'application
        MCDAssociation mcdAssociation = MVCCDElementFactory.instance().createMCDAssociation(mcdContRelations, entityFrom, entityTo, assId, assEndFromId, assEndToId);
        if (!associationTag.getAttribute("name").equals("")) {
          mcdAssociation.setName(associationTag.getAttribute("name"));
        }
        if (!associationTag.getAttribute("shortName").equals("")) {
          mcdAssociation.setShortName(associationTag.getAttribute("shortName"));
        }
          this.loadedAssociations.add(mcdAssociation);

        // Ajout des extrémités d'associations
          this.addExtremiteElement(extremiteFromTag, mcdAssociation, associationTag);
          this.addExtremiteElement(extremiteToTag, mcdAssociation, associationTag);
        // Ajout des propriétés des associations
          this.addProprietesAssociation(associationTag, mcdAssociation);
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

  private void addGeneralisation(MCDContRelations mcdContRelations, Element generalisationsTag) {
    // Parcours des éléments enfant de la balise generalisations
    NodeList generalisationsChilds = generalisationsTag.getChildNodes();
    for (int i = 0; i < generalisationsChilds.getLength(); i++) {
      Node generalisationChild = generalisationsChilds.item(i);
      if (generalisationChild instanceof Element) {
        // Création de l'element generalisation
        Element generalisation = (Element) generalisationChild;

        // Récupération des balises des extrémités de l'association de généralisation-spécialisation
        Element genEntiteTag = (Element) generalisation.getElementsByTagName("genEntite").item(0);
        Element specEntiteTag = (Element) generalisation.getElementsByTagName("specEntite").item(0);

        // Récupération des id des entités cibles dans les 2 extrémités de la généralisation-spécialisation
        int genTargetEntityid = Integer.parseInt(genEntiteTag.getAttribute("target_entity_id"));
        int specTargetEntityid = Integer.parseInt(specEntiteTag.getAttribute("target_entity_id"));

        // Création des entités en lien avec le contenu de généralisation
        MCDEntity entityGen = this.getAlreadyLoadedEntityById(genTargetEntityid);
        MCDEntity entitySpec = this.getAlreadyLoadedEntityById(specTargetEntityid);

        // Création de la généralisation dans l'application
        MVCCDElementFactory.instance().createMCDGeneralization(mcdContRelations, entityGen, entitySpec);
      }
    }
  }

  private MCDEntity getAlreadyLoadedEntityById(int entityId) {
    // Parcours de la listes des entités
    for (MVCCDElement mvccdElement : this.loadedEntities) {
      MCDEntity entity = (MCDEntity) mvccdElement;
      // Comparaison de l'id de l'entité de l'extremité de relation avec les entités créées dans l'aplication
      if (entity.getIdProjectElement() == entityId) {
        return entity;
      }
    }
    return null;
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

  private void loadLinks(MCDContModels mcd, Element mcdTag, ArrayList<Element> modelsTagsList) {
    // Chargement des liens d'entités associatives
    ArrayList<MVCCDElement> mcdChilds = mcd.getChilds();
    MCDContRelations mcdContRelations;

    //  Relations général
    for (MVCCDElement mcdChild : mcdChilds) {
      if (mcdChild instanceof MCDContRelations) {
          this.loadRelationsLink((MCDContRelations) mcdChild, mcdTag);
      }
    }
    //  Relations avec paquetages
    for (MVCCDElement packagee : this.loadedPackages) { //remarque: le nommage "packagee" est volontaire, car "package" est un nom réservé.
      ArrayList<MVCCDElement> childsPackage = packagee.getChilds();
        this.addLinksPackagesOrModels(childsPackage, this.elementsPackages, packagee);

    }
    // Relations avec modèles
    for (MVCCDElement mcdChild : mcdChilds) {
      if (mcdChild instanceof MCDModel) {
          this.addLinksPackagesOrModels(mcdChild.getChilds(), modelsTagsList, mcdChild);
      }
    }
  }

  private void addLinksPackagesOrModels(ArrayList<MVCCDElement> listMVCCD, ArrayList<Element> listElement, MVCCDElement mvccdElement) {
    //Ajout des relations pour les paquetage ou les modèles
    MCDContRelations mcdContRelations;
      for (MVCCDElement elementChild : listMVCCD) {
          if (elementChild instanceof MCDContRelations) {
              // Récupération du conteneur des relations
              mcdContRelations = (MCDContRelations) elementChild;
              for (Element element : listElement) {
                  // Ajout des liens d'entités associatives si l'élément parent(paquetage ou modèle) créé dans l'application est égal à celui du fichier
                  if (element.getAttribute("name").equals(mvccdElement.getName())) {
                      this.loadRelationsLink(mcdContRelations, element);
                  }
              }
          }
      }
  }

  private void loadRelationsLink(MCDContRelations mcdContRelations, Element mcdTag) {
    // Récupération de la balise <relations>
    Element relationsTag = (Element) mcdTag.getElementsByTagName("relations").item(0);

    // Parcours des balises enfants de <relations> pour trouver la balise <links>
    NodeList relationsChilds = relationsTag.getChildNodes();
    for (int i = 0; i < relationsChilds.getLength(); i++) {
      if (relationsChilds.item(i) instanceof Element) {
        if (relationsChilds.item(i).getNodeName().equals("links")) {

          // Ajout des liens d'entités associatives
            this.addLink(mcdContRelations, (Element) relationsChilds.item(i)); //passage en 2e paramètre de la balise <links>
        }
      }
    }
  }

  private void addLink(MCDContRelations mcdContRelations, Element linksTag) {
    // Parcours des balises <lienDEntiteAssociative> qui sont les enfants de la balise <links>
    NodeList linksChilds = linksTag.getChildNodes();
    for (int i = 0; i < linksChilds.getLength(); i++) {
      if (linksChilds.item(i) instanceof Element) {
        Element lienDEntiteAssociativeTag = (Element) linksChilds.item(i);

        // Récupération de l'id de <lienDEntiteAssociative>
        int idAttrOfLienEATag = Integer.parseInt(lienDEntiteAssociativeTag.getAttribute("id"));

        // Récupération des balises <extremiteEntite> et <extremiteAssociation>
        Element extremiteEntiteTag = (Element) lienDEntiteAssociativeTag.getElementsByTagName("extremiteEntite").item(0);
        Element extremiteAssociationTag = (Element) lienDEntiteAssociativeTag.getElementsByTagName("extremiteAssociation").item(0);

        // Récupération des attributs id et target_id des balises <extremiteEntite> et <extremiteAssociation>
        int idAttrOfExtremiteEntiteTag = Integer.parseInt(extremiteEntiteTag.getAttribute("id"));
        int idAttrOfExtremiteAssociationTag = Integer.parseInt(extremiteAssociationTag.getAttribute("id"));
        int targetEntiteIdOfExtremiteEntiteTag = Integer.parseInt(extremiteEntiteTag.getAttribute("entite_target_id"));
        int targetAssIdOfExtremiteAssTag = Integer.parseInt(extremiteAssociationTag.getAttribute("association_target_id"));

        // Récupération de l'entité déjà chargée dans l'application
        MCDEntity mcdEntity = this.getAlreadyLoadedEntityById(targetEntiteIdOfExtremiteEntiteTag);

        // Récupération de l'association déjà chargé dans l'application
        MCDAssociation mcdAssociation = this.getAlreadyLoadedAssociationById(targetAssIdOfExtremiteAssTag);

        // Création du lien d'entité dans l'application
        MVCCDElementFactory.instance().createMCDLink(mcdContRelations, mcdEntity, mcdAssociation, idAttrOfLienEATag);
      }

    }
  }

  private MCDAssociation getAlreadyLoadedAssociationById(int associationId) {
    // Parcours de la listes des associations déjà chargées
    for (MVCCDElement mvccdElement : this.loadedAssociations) {
      MCDAssociation association = (MCDAssociation) mvccdElement;
      // Comparaison de l'id de l'association donné avec l'id de l'association parcourue
      if (association.getIdProjectElement() == associationId) {
        return association;
      }
    }
    return null;
  }

  // *** Méthodes de chargement du MLD ***

  /**
   * À partir de la balise MCD du document XML à charger dans l'application, cette méthode parcourt les balises enfants et charge tous les modèles de type MLDR trouvé.
   * @param mcdSource Le modèle "MCD" (qui est lui-même un contenant d'autres modèles, notamment des modèles MLDR)
   * @param mcdTag La balise <MCD> du document XML à charger, à partir duquel le chargement est effectué.
   */
  private void loadMLDRs(MCDContModels mcdSource, Element mcdTag) {

    //Parcours des enfants de <MCD>
    NodeList mcdTagChilds = mcdTag.getChildNodes();
    for (int i = 0; i < mcdTagChilds.getLength(); i++) {
      if (mcdTagChilds.item(i) instanceof Element) {
        Element mcdTagChild = (Element) mcdTagChilds.item(i);

        //Recherche et chargement de <MLDR_DT> ou <MLDR_TI>
        if (mcdTagChild.getNodeName().equals("MLDR_DT") || mcdTagChild.getNodeName().equals("MLDR_TI")) {
          MLDRModel mldrModel = (MLDRModel) mcdSource.getChildById(Integer.parseInt(mcdTagChild.getAttribute("id"))); //Récupération du MLDR_DT ou MLDR_TI déjà chargé dans l'application
          this.loadMLDR(mcdSource, mldrModel, mcdTagChild); //Chargement de <MLDR_DT> ou <MLDR_TI>
        }
      }
    }
  }

  /**
   * À partir de la balise <MLDR_DT> ou <MLDR_TI>, cette méthode charge dans l'application le MLDR en parcourant toutes les balises enfants.
   * @param mcdSource Il s'agit du MCD source à partir duquel le MLDR a été généré. Le MCD est nécessaire, notamment pour pouvoir récupérer les ids de objets sources pour pouvoir recréer les pointeurs des éléments MLDR vers MCD. Par exemple, la table a un pointeur vers son entité source.
   * @param mldrModel Le modèle MLDR de l'application, initialement vide et dans lequel les éléments sont chargés. À noter que le modèle MLDR est déjà créé dans l'application à ce stade.
   * @param mldrTag Balise <MLDR_DT> ou <MLDR_TI> racine à partir de laquelle faire le parcourt et chargé le modèle logique dans l'application.
   */
  private void loadMLDR(MCDContModels mcdSource, MLDRModel mldrModel, Element mldrTag) {
    //Recherche et chargement des balises enfants <MLDR_DT> ou <MLDR_TI>
    this.loadMDR(mcdSource, null, mldrModel, mldrTag);
  }

  /**
   * À partir de la balise <extremitesRelations>, cette méthode charge l'ensemble des extrémités de relation attachées à la table.
   * @param mcdSource Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mdrTable Il s'agit de la table déjà créé dans l'application, dans laquelle seront insérées les extrémités de relation.
   * @param extremitesRelationsTag Balise <extremitesRelations>
   */
  private void loadMldTableRelEnds(MCDContModels mcdSource, MDRTable mdrTable, Element extremitesRelationsTag) {
    //Parcours des balises enfants de <extremitesRelationsTag>
    NodeList extremitesRelationsTagChilds = extremitesRelationsTag.getChildNodes();
    for (int i = 0; i < extremitesRelationsTagChilds.getLength(); i++) {
      if (extremitesRelationsTagChilds.item(i) instanceof Element) {
        Element extremitesRelationsTagChild = (Element) extremitesRelationsTagChilds.item(i);

        //Chargement de <extremiteRelation>
        if (extremitesRelationsTagChild.getNodeName().equals("extremiteRelation")) {
          this.loadMldTableRelEnd(mcdSource, mdrTable, extremitesRelationsTagChild);
        }
      }
    }
  }

  /**
   * À partir de la balise <extremiteRelation>, cette méthode charge une extrémité de relation attachée à une table.
   * @param mcdSource Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mdrTable Il s'agit de la table contenant déjà un conteneur d'extrémités de relations dans l'application, dans lequel la nouvelle extrémité de relation qui sera créée sera placée.
   * @param extremiteRelationTag Balise <extremiteRelation>
   */
  private void loadMldTableRelEnd(MCDContModels mcdSource, MDRTable mdrTable, Element extremiteRelationTag) {
    MDRContRelEnds mdrContRelEnds = mdrTable.getMDRContRelEnds();

    //Préparation de la nouvelle extrémité de relation à créer dans l'application
    MDRRelFKEnd mdrRelFKEnd = null;

    //Chargement et création de l'extrémité de relation dans le cas du MLD
    if (mdrContRelEnds instanceof MLDRContRelEnds) {
      mdrRelFKEnd = MVCCDElementFactory.instance().createMLDRRelFKEnd((MLDRContRelEnds) mdrContRelEnds, mdrTable, Integer.parseInt(extremiteRelationTag.getAttribute("id")));
    }

    //Chargement des autres propriétés d'identification de l'extrémité de relation
    mdrRelFKEnd.setName(extremiteRelationTag.getAttribute("name"));
    mdrRelFKEnd.setShortName(extremiteRelationTag.getAttribute("shortName"));
    mdrRelFKEnd.setLongName(extremiteRelationTag.getAttribute("longName"));
    mdrRelFKEnd.setNames(new MDRElementNames(extremiteRelationTag.getAttribute("name30"), extremiteRelationTag.getAttribute("name60"), extremiteRelationTag.getAttribute("name120")));
  }

  /**
   * À partir de la balise <mdrRelations>, cette méthode charge dans l'application l'ensembles des relations MDR. Il s'agit des liens entre tables.
   * @param mcdSource Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mdrModel Le modèle qui lui-même contient les tables et les relations. C'est dans le conteneur de relations de ce modèle que sera créé les nouvelles relations.
   * @param mdrRelationsTag Balise racine <mdrRelations>
   */
  private void loadMdrRelations(MCDContModels mcdSource, MDRModel mdrModel, Element mdrRelationsTag) {

    //Parcours des balises enfants de <mdrRelationsTag>
    NodeList mdrRelationsTagChilds = mdrRelationsTag.getChildNodes();
    for (int i = 0; i < mdrRelationsTagChilds.getLength(); i++) {
      if (mdrRelationsTagChilds.item(i) instanceof Element) {
        Element mdrRelationsTagChild = (Element) mdrRelationsTagChilds.item(i);

        //Recherche et chargement de <mdrRelation>
        if (mdrRelationsTagChild.getNodeName().equals("mdrRelation")) {
          this.loadMldRelation(mcdSource, mdrModel, mdrRelationsTagChild);
        }
      }
    }
  }

  /**
   * À partir de la balise <mdrRelation>, cette méthode charge une relation et la créé dans l'application.
   * @param mcdSource Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mdrModel Le modèle qui lui-même contient les tables et les relations. C'est dans le conteneur de relations de ce modèle que sera créé la nouvelle relation.
   * @param mdrRelationTag Balise <mdrRelation> du document XML à partir de laquelle la relation est chargée.
   */
  private void loadMldRelation(MCDContModels mcdSource, MDRModel mdrModel, Element mdrRelationTag) {

    //Récupération de l'élément MCD source de la relation (une association)
    int mcdAssociationSourceId = Integer.parseInt(mdrRelationTag.getAttribute("element_source_id")); //Récupérer l'id de l'élément source
    MCDAssociation mcdAssociationSource = (MCDAssociation) mcdSource.getChildByIdProfondeur(mcdAssociationSourceId); //Recherche l'élément source en fonction de son ID, parmi tous les enfants du MCD

    //Récupération des 2 extrémités de relations
    int relEndAId = Integer.parseInt(mdrRelationTag.getAttribute("extremiteRelA_target_id"));
    int relEndBId = Integer.parseInt(mdrRelationTag.getAttribute("extremiteRelB_target_id"));
    MDRRelFKEnd mdrRelFKEndA = (MDRRelFKEnd) mdrModel.getMDRContTables().getChildByIdProfondeur(relEndAId);
    MDRRelFKEnd mdrRelFKEndB = (MDRRelFKEnd) mdrModel.getMDRContTables().getChildByIdProfondeur(relEndBId);

    //Création de la relation MLDR
    MDRRelationFK mdrRelationFK = null;
    if (mdrModel instanceof MLDRModel) {
      MLDRContRelations mldrContRelations = ((MLDRModel) mdrModel).getMLDRContRelations(); //Le conteneur de relations est déjà créé automatiquement avant
      mdrRelationFK = MVCCDElementFactory.instance().createMLDRRelationFK(mldrContRelations, mcdAssociationSource, (MLDRRelFKEnd) mdrRelFKEndA, (MLDRRelFKEnd) mdrRelFKEndB, Integer.parseInt(mdrRelationTag.getAttribute("id")));
    }

    //Récupération de la FK et affectation de la FK à la relation MDR
    MDRFK mdrFK = (MDRFK) mdrModel.getMDRContTables().getChildByIdProfondeur(Integer.parseInt(mdrRelationTag.getAttribute("fk_target_id")));
    mdrRelationFK.setMDRFK(mdrFK);

    //Chargement des autres propriétés de la relation
    mdrRelationFK.setName(mdrRelationTag.getAttribute("name"));
    mdrRelationFK.setShortName(mdrRelationTag.getAttribute("shortName"));
    mdrRelationFK.setLongName(mdrRelationTag.getAttribute("longName"));
    mdrRelationFK.setNames(new MDRElementNames(mdrRelationTag.getAttribute("name30"), mdrRelationTag.getAttribute("name60"), mdrRelationTag.getAttribute("name120")));
  }

  // *** Méthodes de chargement du MPD ***

  /**
   * À partir de la balise <MPDR_xx> (Oracle, PostgreSQL, MySQL, etc.), cette méthode charge dans l'application le MPDR en parcourant toutes les balises enfants.
   * @param mldrModel Modèle MLDR source du MPDR, dans lequel sera ajouté le MPDR chargé à partir du fichier XML.
   * @param mpdrTag Balise racine <MPDR_xx> à partir de laquelle le MPDR sera chargée.
   */
  private void loadMPDR(MLDRModel mldrModel, Element mpdrTag) {
    MPDRModel mpdrModel = null; //Modèle MPDR dans lequel sera chargé tous les éléments à partir de la balise <MPDR_XX>
    switch (mpdrTag.getNodeName()) {
      case "MPDR_Oracle":
        mpdrModel = MVCCDElementFactory.instance().createMPDRModelOracle(mldrModel);
        break;
      case "MPDR_PostgreSQL":
        mpdrModel = MVCCDElementFactory.instance().createMPDRPostgreSQLModel(mldrModel);
        break;
      case "MPDR_MySQL":
        mpdrModel = MVCCDElementFactory.instance().createMPDRMySQLModel(mldrModel);
        break;
    }
    this.loadMDR(null, mldrModel, mpdrModel, mpdrTag);
  }

  // *** Méthodes de chargement génériques pour MDR ***
    /*
    Remarque: pour que ces méthodes soient pleinement génériques, il serait encore nécessaire de fusionner les classes
    MCDModel (qui semble relativement inutile) et MCDContModels (utilisé dans le référentiel et contenant les entités,
    les relations, les MLDR, etc.). Seulement ceci permettrait de gérer les element_source_id de manière génériques
    pour MLD et MPD.
     */

  /**
   * À partir de la balise racine d'un modèle (MLDR_DT, MLDR_TI ou MPDR_XX (XX pour Oracle, PostgreSQL, MySQL, etc.)), cette méthode charge dans l'application le modèle relationnel en parcourant toutes les balises enfants. Remarque: à termes, il faudrait que les 2 paramètres mcdSource et mldrSource soient fusionné en un seul paramètre.
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrModel Le modèle MDR de l'application, initialement vide et dans lequel les éléments sont chargés. À noter que ce modèle est déjà créé dans l'application à ce stade.
   * @param mdrTag Balise racine <MPDR_xx> à partir de laquelle le MPDR sera chargée.
   */
  private void loadMDR(MCDContModels mcdSource, MLDRModel mldrSource, MDRModel mdrModel, Element mdrTag) {
    NodeList mdrTagChilds = mdrTag.getChildNodes();
    for (int i = 0; i < mdrTagChilds.getLength(); i++) {
      if (mdrTagChilds.item(i) instanceof Element) {
        Element mdrTagChild = (Element) mdrTagChilds.item(i);

        //Recherche et chargement de <tables>
        if (mdrTagChild.getNodeName().equals("tables")) {

          //Chargement des tables sous <tables>
          this.loadMdrTables(mcdSource, mldrSource, mdrModel, mdrTagChild);

          //Chargement des contraintes FK des tables sous <tables>
          this.loadMdrFKsOfAllTables(mcdSource, mldrSource, mdrModel, mdrTagChild);
        }

        //Recherche et chargement de <mdrRelations> (des relations FK) (uniquement dans le cas du chargement du MLDR)
        else if (mdrModel instanceof MLDRModel && mdrTagChild.getNodeName().equals("mdrRelations")) {

          //Chargement des relations sour <mdrRelations>
          this.loadMdrRelations(mcdSource, mdrModel, mdrTagChild);
        }

        //Recherche et chargement de <MPDR_xx> (pour chaque constructeur) (s'appliquera de fait uniquement lors du chargement d'un MPDR)
        else if (mdrTagChild.getNodeName().matches("MPDR_(.*)")) {

          //Chargement des relations sous <mdrRelations>
          this.loadMPDR((MLDRModel) mdrModel, mdrTagChild);
        }
      }
    }
  }

  /**
   * À partir de la balise <tables>, cette méthode charge dans l'application l'ensembles des tables d'un MLDR ou MPDR.
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrModel Le modèle MDR de l'application, initialement vide et dans lequel les éléments sont chargés.
   * @param tablesTag Balise racine <tables>
   */
  private void loadMdrTables(MCDContModels mcdSource, MLDRModel mldrSource, MDRModel mdrModel, Element tablesTag) {
    //Parcours des balises enfants de <tables>
    NodeList tablesTagChilds = tablesTag.getChildNodes();
    for (int i = 0; i < tablesTagChilds.getLength(); i++) {
      if (tablesTagChilds.item(i) instanceof Element) {
        Element tablesTagChild = (Element) tablesTagChilds.item(i);

        //Recherche et chargement de <table>
        if (tablesTagChild.getNodeName().equals("table")) {
          this.loadMdrTable(mcdSource, mldrSource, mdrModel, tablesTagChild);
        }
      }
    }
  }

  /**
   * À partir de la balise <table>, cette méthode charge une table et la créé dans l'application.
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrModel Le modèle MDR de l'application, initialement vide et dans lequel les éléments sont chargés.
   * @param tableTag Balise <table> du document XML à partir de laquelle la table est chargée.
   */
  private void loadMdrTable(MCDContModels mcdSource, MLDRModel mldrSource, MDRModel mdrModel, Element tableTag) {

    //Récupération de l'id de l'élément source (MCD ou MLD) de la table
    int elementSourceId = Integer.parseInt(tableTag.getAttribute("element_source_id")); //Récupérer l'id de l'élément source (entité source ou table MLD source)

    //Récupération du conteneur de tables déjà existant et préparation de l'objet table à créer et à alimenter
    MDRContTables mdrContTables = mdrModel.getMDRContTables(); //Le conteneur de tables est déjà créé automatiquement avant (lors de la création du modèle MDR).
    MDRTable mdrTable = null;

    //Cas de chargement du MLDR: récupération de l'élément source MLD et création de la table MLD.
    if (mdrModel instanceof MLDRModel) {
      IMCDSourceMLDRTable mcdSourceElementOfTable = (IMCDSourceMLDRTable) mcdSource.getChildByIdProfondeur(elementSourceId); //Recherche l'élément source en fonction de son ID, parmi tous les enfants du MCD
      mdrTable = MVCCDElementFactory.instance().createMLDRTable(mdrContTables, mcdSourceElementOfTable, Integer.parseInt(tableTag.getAttribute("id")));
    }

    //Cas de chargement du MPDR: récupération de l'élément source MPD et création de la table MPD.
    else if (mdrModel instanceof MPDRModel) {
      MLDRTable mldTableSourceOfMpdTable = (MLDRTable) mldrSource.getChildByIdProfondeur(elementSourceId); //Recherche l'élément source en fonction de son ID, parmi tous les enfants du MLD
      if (mdrModel instanceof MPDROracleModel) {
        mdrTable = MVCCDElementFactory.instance().createMPDROracleTable(mdrContTables, mldTableSourceOfMpdTable, Integer.parseInt(tableTag.getAttribute("id")));
      } else if (mdrModel instanceof MPDRPostgreSQLModel) {
        mdrTable = MVCCDElementFactory.instance().createMPDRPostgreSQLTable(mdrContTables, mldTableSourceOfMpdTable, Integer.parseInt(tableTag.getAttribute("id")));
      } else if (mdrModel instanceof MPDRMySQLModel) {
        mdrTable = MVCCDElementFactory.instance().createMPDRMySQLTable(mdrContTables, mldTableSourceOfMpdTable, Integer.parseInt(tableTag.getAttribute("id")));
      }
    }

    //Création de la table et de ses attributs
    mdrTable.setName(tableTag.getAttribute("name"));
    mdrTable.setShortName(tableTag.getAttribute("shortName"));
    mdrTable.setLongName(tableTag.getAttribute("longName"));
    mdrTable.setNames(new MDRElementNames(tableTag.getAttribute("name30"), tableTag.getAttribute("name60"), tableTag.getAttribute("name120")));

    //Parcours des balises enfants de <table>
    NodeList tableTagChilds = tableTag.getChildNodes();
    for (int i = 0; i < tableTagChilds.getLength(); i++) {
      if (tableTagChilds.item(i) instanceof Element) {
        Element tableTagChild = (Element) tableTagChilds.item(i);

        //Chargement de <columns>
        if (tableTagChild.getNodeName().equals("columns")) {
          this.loadMdrColumns(mcdSource, mldrSource, mdrModel, mdrTable, tableTagChild);
        }

        //Chargement de <tableConstraints>
        else if (tableTagChild.getNodeName().equals("tableConstraints")) {
          this.loadMdrTableConstraints(mcdSource, mldrSource, mdrTable, tableTagChild);
        }

        //Chargement de <extremitesRelations> (uniquement dans le cas du chargement du MLDR)
        else if (mdrModel instanceof MLDRModel && tableTagChild.getNodeName().equals("extremitesRelations")) {
          this.loadMldTableRelEnds(mcdSource, mdrTable, tableTagChild);
        }
      }
    }
  }

  /**
   * À partir de la balise <columns>, cette méthode charge l'ensemble des colonnes d'une table.
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrModel Le modèle MDR de l'application, initialement vide et dans lequel les éléments sont chargés. Est utilisé pour faire la recherche des colonnes PK pointées par les colonnes FK en fonction de leur id.
   * @param mdrTable Il s'agit de la table déjà créé dans l'application, dans laquelle seront insérées les colonnes.
   * @param columnsTag Balise <columns>
   */
  private void loadMdrColumns(MCDElement mcdSource, MLDRModel mldrSource, MDRModel mdrModel, MDRTable mdrTable, Element columnsTag) {

    //Création d'un conteneur pour mémoriser les colonnes détectées comme colonnes de FK
    List<MDRColumn> fkColumnsList = new ArrayList<MDRColumn>();

    //Parcours des balises enfants de <columns>
    NodeList columnsTagChilds = columnsTag.getChildNodes();
    for (int i = 0; i < columnsTagChilds.getLength(); i++) {
      if (columnsTagChilds.item(i) instanceof Element) {
        Element columnsTagChild = (Element) columnsTagChilds.item(i);

        //Chargement de <column>
        if (columnsTagChild.getNodeName().equals("column")) {
          MDRColumn fkColumn = this.loadMdrColumn(mcdSource, mldrSource, mdrModel, mdrTable, columnsTagChild); //La méthode retourne les colonnes qui sont FK
          if (fkColumn != null) {
            fkColumnsList.add(fkColumn); //Si la colonne est une colonne FK, on l'ajoute à la liste des colonnes FK
          }
        }
      }
    }

    //Parcours des balises enfants de <columns>, pour détecter les colonnes de FK et ajouter les liens vers les colonnes PK
    for (MDRColumn fkColumn : fkColumnsList) {
      int targetColumnPkId = Integer.parseInt(fkColumn.getTempTargetColumnPkId());
      MDRColumn foundedPkColumn = (MDRColumn) mdrModel.getMDRContTables().getChildByIdProfondeur(targetColumnPkId);
      fkColumn.setMdrColumnPK(foundedPkColumn);
    }
  }

  /**
   * À partir de la balise <column>, cette méthode charge une colonne de table avec tous ses attributs.
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrModel Le modèle MDR de l'application dans lequel les éléments sont chargés. Est nécessaire pour déterminer s'il s'agit d'un chargement de MLD ou MPD.
   * @param mdrTable Il s'agit de la table déjà créé dans l'application, dans laquelle sera insérée la colonne.
   * @param columnTag balise <column>
   * @return Si la colonne est détectée comme étant une colonne de FK, alors la colonne est retournée
   */
  private MDRColumn loadMdrColumn(MCDElement mcdSource, MLDRModel mldrSource, MDRModel mdrModel, MDRTable mdrTable, Element columnTag) {

    //Récupération de l'id de l'élément source (MCD ou MLD) de la colonne
    int elementSourceId = Integer.parseInt(columnTag.getAttribute("element_source_id"));

    //Récupération du conteneur de colonnes déjà existant et préparation de l'objet colonne à créer et à alimenter
    MDRContColumns mdrContColumns = mdrTable.getMDRContColumns();
    MDRColumn mdrColumn = null;

    //Cas de chargement du MLDR: récupération de l'attribut (MCD) source et création de cet attribut source
    if (mdrModel instanceof MLDRModel) {
      MCDElement mcdElementSourceOfColumn = (MCDElement) mcdSource.getChildByIdProfondeur(elementSourceId); //La source de la colonne peut être un attribut d'entité ou une extrémité d'association (si colonne FK)
      mdrColumn = MVCCDElementFactory.instance().createMLDRColumn(mdrContColumns, mcdElementSourceOfColumn, Integer.parseInt(columnTag.getAttribute("id")));
    }

    //Cas de chargement du MPDR: récupération de l'élément source MLD et création de la colonne MPD.
    else if (mdrModel instanceof MPDRModel) {
      MLDRColumn mldrColumnSourceOfMpdColumn = (MLDRColumn) mldrSource.getChildByIdProfondeur(elementSourceId); //Recherche l'élément source en fonction de son ID, parmi tous les enfants du MLD
      if (mdrModel instanceof MPDROracleModel) {
        mdrColumn = MVCCDElementFactory.instance().createMPDROracleColumn(mdrContColumns, mldrColumnSourceOfMpdColumn, Integer.parseInt(columnTag.getAttribute("id")));
      } else if (mdrModel instanceof MPDRPostgreSQLModel) {
        mdrColumn = MVCCDElementFactory.instance().createMPDRPostgreSQLColumn(mdrContColumns, mldrColumnSourceOfMpdColumn, Integer.parseInt(columnTag.getAttribute("id")));
      } else if (mdrModel instanceof MPDRMySQLModel) {
        mdrColumn = MVCCDElementFactory.instance().createMPDRMySQLColumn(mdrContColumns, mldrColumnSourceOfMpdColumn, Integer.parseInt(columnTag.getAttribute("id")));
      }
    }

    //Chargement des autres propriétés d'identification de la colonne
    mdrColumn.setName(columnTag.getAttribute("name"));
    mdrColumn.setShortName(columnTag.getAttribute("shortName"));
    mdrColumn.setLongName(columnTag.getAttribute("longName"));
    mdrColumn.setNames(new MDRElementNames(columnTag.getAttribute("name30"), columnTag.getAttribute("name60"), columnTag.getAttribute("name120")));

    //Chargement des autres propriétés de la colonne
    mdrColumn.setMandatory(Boolean.getBoolean(columnTag.getAttribute("mandatory")));
    mdrColumn.setFrozen(Boolean.getBoolean(columnTag.getAttribute("frozen")));
    mdrColumn.setUppercase(Boolean.getBoolean(columnTag.getAttribute("uppercase")));
    mdrColumn.setIteration(Integer.parseInt(columnTag.getAttribute("iteration")));
    mdrColumn.setInitValue(columnTag.getAttribute("initValue"));
    mdrColumn.setDerivedValue(columnTag.getAttribute("derivedValue"));

    //Chargement de l'id de la colonne PK pointée (cas d'une colonne FK)
    if (columnTag.hasAttribute("target_column_pk")) {
      mdrColumn.setTempTargetColumnPkId(columnTag.getAttribute("target_column_pk"));
    }

    //Chargement du type de données
    mdrColumn.setDatatypeLienProg(columnTag.getAttribute("datatype_lienprog"));
    mdrColumn.setDatatypeConstraintLienProg(columnTag.getAttribute("datatype_constraint_lienprog"));

    //Chargement de size et scale
    if (columnTag.hasAttribute("size")) {
      mdrColumn.setSize(Integer.valueOf(columnTag.getAttribute("size")));
    }
    if (columnTag.hasAttribute("scale")) {
      mdrColumn.setScale(Integer.valueOf(columnTag.getAttribute("scale")));
    }

    //Si la colonne est une colonne FK, on la retourne de façon à la mémoriser dans les colonne FK à traiter plus tard.
    if (mdrColumn.getTempTargetColumnPkId() != null) {
      return mdrColumn;
    }
    return null;
  }

  /**
   * À partir de la balise <tableConstraintsTag>, cette méthode charge l'ensemble des contraintes d'une table, hormis les contraintes FK qui sont chargées ultérieurement dans le processus (après que toutes les tables aient été chargées).
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrTable Il s'agit de la table déjà créé dans l'application, dans laquelle seront insérées les contraintes.
   * @param tableConstraintsTag Balise <tableConstraints>
   */
  private void loadMdrTableConstraints(MCDContModels mcdSource, MLDRModel mldrSource, MDRTable mdrTable, Element tableConstraintsTag) {

    //Parcours des balises enfants de <tableConstraints>
    NodeList constraintsTagChilds = tableConstraintsTag.getChildNodes();
    for (int i = 0; i < constraintsTagChilds.getLength(); i++) {
      if (constraintsTagChilds.item(i) instanceof Element) {
        Element ConstraintsTagChild = (Element) constraintsTagChilds.item(i);

        //Chargement de <pk>
        if (ConstraintsTagChild.getNodeName().equals("pk")) {
          this.loadMdrPk(mcdSource, mldrSource, mdrTable, ConstraintsTagChild);
        }
      }
    }
  }

  /**
   * À partir de la balise <pk>, cette méthode charge une contrainte PK de table avec toutes les références vers les colonnes incluses.
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrTable Il s'agit de la table MLD contenant déjà un conteneur de contraintes dans l'application, dans lequel la nouvelle PK qui sera créée sera placée.
   * @param pkTag Balise <pk>
   */
  private void loadMdrPk(MCDContModels mcdSource, MLDRModel mldrSource, MDRTable mdrTable, Element pkTag) {

    //Récupération de l'id de l'élément source (MCD ou MLD) de la colonne
    int elementSourceId = Integer.parseInt(pkTag.getAttribute("element_source_id"));

    //Récupération du conteneur de contraintes déjà existant et préparation de l'objet PK à créer et à alimenter
    MDRContConstraints mdrContConstraints = mdrTable.getMDRContConstraints();
    MDRPK mdrPk = null;

    //Cas de chargement du MLDR: récupération de l'élément source (MCD) et création de la PK MLD
    MCDElement mcdElementSourceOfPk = null;
    if (mdrTable instanceof MLDRTable) {
      mcdElementSourceOfPk = (MCDElement) mcdSource.getChildByIdProfondeur(elementSourceId); //Peut être l'entité source (MCD) de la PK ou l'association source de la PK (cas d'une association n:n)
      mdrPk = MVCCDElementFactory.instance().createMLDRPK(mdrContConstraints, mcdElementSourceOfPk, Integer.parseInt(pkTag.getAttribute("id")));
    }

    //Cas de chargement du MPDR: récupération de l'élément source MLD et création de la PK MPD
    else if (mdrTable instanceof MPDRTable) {
      MLDRPK mldrPkSourceOfMpdPk = (MLDRPK) mldrSource.getChildByIdProfondeur(elementSourceId); //Recherche l'élément source en fonction de son ID, parmi tous les enfants du MLD
      if (mdrTable instanceof MPDROracleTable) {
        mdrPk = MVCCDElementFactory.instance().createMPDROraclePK(mdrContConstraints, mldrPkSourceOfMpdPk, Integer.parseInt(pkTag.getAttribute("id")));
      } else if (mdrTable instanceof MPDRPostgreSQLTable) {
        mdrPk = MVCCDElementFactory.instance().createMPDRPostgreSQLPK(mdrContConstraints, mldrPkSourceOfMpdPk, Integer.parseInt(pkTag.getAttribute("id")));
      } else if (mdrTable instanceof MPDRMySQLTable) {
        mdrPk = MVCCDElementFactory.instance().createMPDRMySQLPK(mdrContConstraints, mldrPkSourceOfMpdPk, Integer.parseInt(pkTag.getAttribute("id")));
      }
    }

    //Chargement des autres propriétés d'identification de la colonne
    mdrPk.setName(pkTag.getAttribute("name"));
    mdrPk.setShortName(pkTag.getAttribute("shortName"));
    mdrPk.setLongName(pkTag.getAttribute("longName"));
    mdrPk.setNames(new MDRElementNames(pkTag.getAttribute("name30"), pkTag.getAttribute("name60"), pkTag.getAttribute("name120")));

    //Parcours des balises enfants de <pk>
    NodeList pkTagChilds = pkTag.getChildNodes();
    for (int i = 0; i < pkTagChilds.getLength(); i++) {
      if (pkTagChilds.item(i) instanceof Element) {
        Element pkTagChild = (Element) pkTagChilds.item(i);

        //Chargement de <targetParameters>
        if (pkTagChild.getNodeName().equals("targetParameters")) {
          this.loadMldTargetParametersOfConstraint(mcdSource, mldrSource, mdrTable, mdrPk, pkTagChild);
        }
      }
    }
  }

  /**
   * À partir de la balise <tables>, cette méthode charge dans l'application l'ensembles des contraintes FKs des tables d'un MLDR.
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrModel Le modèle MDR de l'application, contenant déjà l'ensemble des tables chargées dans lesquelles seront ajoutées les contraintes FKs.
   * @param tablesTag Balise racine <tables>
   */
  private void loadMdrFKsOfAllTables(MCDContModels mcdSource, MLDRModel mldrSource, MDRModel mdrModel, Element tablesTag) {
    //Parcours des balises enfants de <tables>
    NodeList tablesTagChilds = tablesTag.getChildNodes();
    for (int i = 0; i < tablesTagChilds.getLength(); i++) {
      if (tablesTagChilds.item(i) instanceof Element) {
        Element tablesTagChild = (Element) tablesTagChilds.item(i);

        //Recherche de <table>
        if (tablesTagChild.getNodeName().equals("table")) {
          Element tableTag = tablesTagChild;

          //Parcours des balises enfants de <table>
          NodeList tableTagChilds = tableTag.getChildNodes();
          for (int j = 0; j < tableTagChilds.getLength(); j++) {
            if (tableTagChilds.item(j) instanceof Element) {
              Element tableTagChild = (Element) tableTagChilds.item(j);

              //Recherche de <tableConstraints>
              if (tableTagChild.getNodeName().equals("tableConstraints")) {
                MDRTable mdrTable = mdrModel.getMDRContTables().getMDRTableById(Integer.parseInt(tableTag.getAttribute("id"))); //Récupérer la table déjà chargée dans laquelle il faudra placer les contraintes FK
                this.loadMdrFksOfTable(mcdSource, mldrSource, mdrModel, mdrTable, tableTagChild); //Charger les contraintes FK dans cette table déjà chargée
              }
            }
          }
        }
      }
    }
  }

  /**
   * À partir de la balise <tableConstraints>, cette méthode charge dans l'application l'ensembles des contraintes FKs d'une table.
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrModel Le modèle MDR de l'application, contenant déjà l'ensemble des tables chargées dans lesquelles seront ajoutées les contraintes FKs.
   * @param mdrTable La table dans laquelle placer les contraintes FKs à charger.
   * @param tableConstraintsTag Balise racine <tableConstraints>
   */
  private void loadMdrFksOfTable(MCDContModels mcdSource, MLDRModel mldrSource, MDRModel mdrModel, MDRTable mdrTable, Element tableConstraintsTag) {
    //Parcours des balises enfants de <tableConstraints>
    NodeList constraintsTagChilds = tableConstraintsTag.getChildNodes();
    for (int i = 0; i < constraintsTagChilds.getLength(); i++) {
      if (constraintsTagChilds.item(i) instanceof Element) {
        Element constraintsTagChild = (Element) constraintsTagChilds.item(i);

        //Chargement de <fk>
        if (constraintsTagChild.getNodeName().equals("fk")) {
          this.loadMdrFkOfTable(mcdSource, mldrSource, mdrModel, mdrTable, constraintsTagChild);
        }
      }
    }
  }

  /**
   * À partir de la balise <fk>, cette méthode charge dans l'application une contrainte de table FK.
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrModel Le modèle MDR de l'application, contenant déjà l'ensemble de tables chargées dont celle dans laquelle sera créée la contrainte FK. Cet élément est utile à la méthode pour pouvoir parcourir les tables chargées à la recherche de la PK cible de la FK.
   * @param mdrTable Il s'agit de la table déjà chargée en mémoire, dans laquelle ajouter la contrainte FK à charger.
   * @param fkTag Il s'agit de la balise <fk> à charger.
   */
  private void loadMdrFkOfTable(MCDContModels mcdSource, MLDRModel mldrSource, MDRModel mdrModel, MDRTable mdrTable, Element fkTag) {

    //Récupération de l'élément source (MCD ou MLD) de la FK
    int elementSourceId = Integer.parseInt(fkTag.getAttribute("element_source_id"));

    //Récupération du conteneur de contraintes déjà existant et préparation de l'objet FK à créer et à alimenter
    MDRContConstraints mdrContConstraints = mdrTable.getMDRContConstraints();
    MDRFK mdrFk = null;

    //Cas de chargement du MLDR: récupération de l'élément source (MCD) et création de la FK MLD
    MCDElement mcdElementSourceOfFk = null;
    if (mdrTable instanceof MLDRTable) {
      mcdElementSourceOfFk = (MCDElement) mcdSource.getChildByIdProfondeur(elementSourceId); //la source MCD est généralement une extrémité d'association
      mdrFk = MVCCDElementFactory.instance().createMLDRFK(mdrContConstraints, mcdElementSourceOfFk, Integer.parseInt(fkTag.getAttribute("id")));
    }

    //Cas de chargement du MPDR: récupération de l'élément source MLD et création de la FK MPD
    else if (mdrTable instanceof MPDRTable) {
      MLDRFK mldrFkSourceOfMpdFk = (MLDRFK) mldrSource.getChildByIdProfondeur(elementSourceId); //Recherche l'élément source en fonction de son ID, parmi tous les enfants du MLD
      if (mdrTable instanceof MPDROracleTable) {
        mdrFk = MVCCDElementFactory.instance().createMPDROracleFK(mdrContConstraints, mldrFkSourceOfMpdFk, Integer.parseInt(fkTag.getAttribute("id")));
      } else if (mdrTable instanceof MPDRPostgreSQLTable) {
        mdrFk = MVCCDElementFactory.instance().createMPDRPostgreSQLFK(mdrContConstraints, mldrFkSourceOfMpdFk, Integer.parseInt(fkTag.getAttribute("id")));
      } else if (mdrTable instanceof MPDRMySQLTable) {
        mdrFk = MVCCDElementFactory.instance().createMPDRMySQLFK(mdrContConstraints, mldrFkSourceOfMpdFk, Integer.parseInt(fkTag.getAttribute("id")));
      }
    }

    //Chargement des autres propriétés d'identification de la FK
    mdrFk.setName(fkTag.getAttribute("name"));
    mdrFk.setShortName(fkTag.getAttribute("shortName"));
    mdrFk.setLongName(fkTag.getAttribute("longName"));
    mdrFk.setNames(new MDRElementNames(fkTag.getAttribute("name30"), fkTag.getAttribute("name60"), fkTag.getAttribute("name120")));

    //Chargement de la référence vers la contrainte PK
    int targetPkId = Integer.parseInt(fkTag.getAttribute("target_pk"));
    MDRPK mdrPk = (MDRPK) mdrModel.getMDRContTables().getChildByIdProfondeur(targetPkId); //Recherche de la PK dans l'ensemble des tables déjà chargées
    mdrFk.setMdrPK(mdrPk);

    //Parcours des balises enfants de <fk>
    NodeList fkTagChilds = fkTag.getChildNodes();
    for (int i = 0; i < fkTagChilds.getLength(); i++) {
      if (fkTagChilds.item(i) instanceof Element) {
        Element fkTagChild = (Element) fkTagChilds.item(i);

        //Chargement de <targetParameters>
        if (fkTagChild.getNodeName().equals("targetParameters")) {
          this.loadMldTargetParametersOfConstraint(mcdSource, mldrSource, mdrTable, mdrFk, fkTagChild);
        }
      }
    }
  }

  /**
   * À partir de la balise <targetParameters>, cette méthode charge les références des colonnes (Parameters) incluses dans une contrainte (PK et FK notamment).
   * @param mcdSource À renseigner dans le cas du chargement d'un MLDR. Il s'agit du MCD source à partir duquel le MLDR a été généré.
   * @param mldrSource À renseigner dans le cas du chargement d'un MPDR. Il s'agit du MLDR source à partir duquel le MPDR a été généré.
   * @param mdrTable Il s'agit de la table déjà chargée en mémoire, dans laquelle est ajoutée la contrainte en cours de chargement.
   * @param mdrConstraint Il s'agit de la contraint (PK ou FK) déjà créé précédemment dans l'application, à qui sera ajouté les colonnes ciblées par les balises enfants <targetParameter>.
   * @param targetParametersTag Balise <targetParameters> contenant des sous-balises avec les références vers les colonnes de la FK
   */
  private void loadMldTargetParametersOfConstraint(MCDContModels mcdSource, MLDRModel mldrSource, MDRTable mdrTable, MDRConstraint mdrConstraint, Element targetParametersTag) {
    //Parcours des balises enfants de <targetParameters>
    NodeList targetParametersTagChilds = targetParametersTag.getChildNodes();
    for (int i = 0; i < targetParametersTagChilds.getLength(); i++) {
      if (targetParametersTagChilds.item(i) instanceof Element) {
        Element targetParametersTagChild = (Element) targetParametersTagChilds.item(i);

        //Chargement de <targetParameter>
        if (targetParametersTagChild.getNodeName().equals("targetParameter")) {
          Element targetParameterTag = targetParametersTagChild;

          //Récupération de l'id du Parameter et de l'id élément source du Parameter
          int parameterId = Integer.parseInt(targetParameterTag.getAttribute("id"));
          int elementSourceId = Integer.parseInt(targetParameterTag.getAttribute("element_source_id"));

          //Récupération de la colonne sur laquelle est mise la contrainte
          int targetColumnId = Integer.parseInt(targetParameterTag.getAttribute("target_column_id"));
          MDRColumn targetMdrColumn = mdrTable.getMDRColumnById(targetColumnId);

          //Ajout du parameter comme enfant de la contrainte (ce qui ajoute la colonne à la contrainte)
          //Pour MLDR
          if (targetMdrColumn instanceof MLDRColumn) {
            MCDElement mcdElementSource = (MCDElement) mcdSource.getChildByIdProfondeur(elementSourceId); //Recherche l'élément source en fonction de son ID, parmi tous les enfants du MCD
            MVCCDElementFactory.instance().createMLDRParameter(mdrConstraint, targetMdrColumn, mcdElementSource, parameterId);
          }
          //Pour MPDR
          else if (targetMdrColumn instanceof MPDRColumn) {
            MLDRParameter mldrParameterSource = (MLDRParameter) mldrSource.getChildByIdProfondeur(elementSourceId); //Recherche l'élément source en fonction de son ID, parmi tous les enfants du MLD

            //Oracle
            if (targetMdrColumn instanceof MPDROracleColumn) {
              MVCCDElementFactory.instance().createMPDROracleParameter((IMPDROracleElement) mdrConstraint, targetMdrColumn, mldrParameterSource, parameterId);
            }
            //PostgreSQL
            else if (targetMdrColumn instanceof MPDRPostgreSQLColumn) {
              //#MAJ 2022-01-20 Intégration PostgreSQL
              //Ajout CAST
              MVCCDElementFactory.instance().createMPDRPostgreSQLParameter((IMPDRPostgreSQLElement) mdrConstraint, targetMdrColumn, mldrParameterSource, parameterId);
            }
            //MySQL
            else if (targetMdrColumn instanceof MPDRMySQLColumn) {
              MVCCDElementFactory.instance().createMPDRMySQLParameter((IMPDRMySQLElement) mdrConstraint, targetMdrColumn, mldrParameterSource, parameterId);
            }
          }
        }
      }
    }
  }

  private void loadClassShapes(Element diagramTag, Diagram diagram, MCDContModels mcdSource) {
    Node shapesTag = diagramTag.getElementsByTagName(Preferences.NODE_SHAPES).item(0);
    NodeList shapes = shapesTag.getChildNodes();

    for (int i = 0; i < shapes.getLength(); i++) {
      if (shapes.item(i) instanceof Element) {

        // Récupère le noeud courant et son id
        Element currentNode = (Element) shapes.item(i);
        int id = Integer.parseInt(currentNode.getAttribute(Preferences.ATTRIBUTE_ID));

        // La balise courante est une entité d'un MCD
        if (currentNode.getNodeName().equals(Preferences.DIAGRAMMER_MCD_ENTITY_XML_TAG)) {
            this.loadMCDEntityShape(currentNode, mcdSource, diagram, diagramTag, id);
        }
      }
    }
  }

  private void loadMCDEntityShape(Element node, MCDContModels mcd, Diagram diagram, Element diagramTag, int entityShapeId) {

    // Crée la nouvelle forme qui sera affichée dans le diagrammeur
    MCDEntityShape newEntity;

    // Récupère l'entité du référentiel liée
    if (node.hasAttribute(Preferences.ATTRIBUTE_REPOSITORY_ENTITY_ID)) {
      String relatedRepositoryEntityId = node.getAttribute(Preferences.ATTRIBUTE_REPOSITORY_ENTITY_ID);
      MCDEntity entityFound = (MCDEntity) mcd.getEntities().getChildById((Integer.parseInt(relatedRepositoryEntityId)));
      newEntity = new MCDEntityShape(entityShapeId, entityFound);

    } else {
      newEntity = new MCDEntityShape(entityShapeId);
    }

    // Set la size et la position de l'entité
    newEntity.setSize(Integer.parseInt(node.getAttribute(Preferences.ATTRIBUTE_WIDTH)), Integer.parseInt(node.getAttribute(Preferences.ATTRIBUTE_HEIGHT)));
    newEntity.setLocation(Integer.parseInt(node.getAttribute(Preferences.ATTRIBUTE_X)), Integer.parseInt(node.getAttribute(Preferences.ATTRIBUTE_Y)));

    // Ajoute l'entité au diagramme
    diagram.addShape(newEntity);

    // Affiche les différentes zones de l'entité
    newEntity.refreshInformations();
  }

  private void loadRelationShapes(Element diagramTag, Diagram diagram, MCDContModels mcdSource) {
    Node shapesTag = diagramTag.getElementsByTagName(Preferences.NODE_SHAPES).item(0);
    NodeList shapes = shapesTag.getChildNodes();

    for (int i = 0; i < shapes.getLength(); i++) {
      if (shapes.item(i) instanceof Element) {

        // Récupère le noeud courant
        Element currentNode = (Element) shapes.item(i);
        int id = Integer.parseInt(currentNode.getAttribute(Preferences.ATTRIBUTE_ID));

        // La balise courante est une entité d'un MCD
        if (currentNode.getNodeName().equals(Preferences.DIAGRAMMER_MCD_ASSOCIATION_XML_TAG)) {
            this.loadMCDAssociationShape(currentNode, shapes, diagram, diagramTag, id, mcdSource);
        }

      }
    }
  }

  private void loadMCDAssociationShape(Element node, NodeList shapes, Diagram diagram, Element diagramTag, int associationShapeId, MCDContModels mcd) {

    // Récupère les ids de l'association et des entités du référentiel liées
    boolean isReflexive = node.getAttribute(Preferences.ATTRIBUTE_DESTINATION_ENTITY_SHAPE_ID).equals(node.getAttribute(Preferences.ATTRIBUTE_SOURCE_ENTITY_SHAPE_ID));

    MCDEntityShape sourceEntityShape = null;
    MCDEntityShape destinationEntityShape = null;

    // Récupère les entityShape source et destination
    for (int j = 0; j < shapes.getLength(); j++) {
      if (shapes.item(j) instanceof Element) {
        if (shapes.item(j).getNodeName().equals(Preferences.DIAGRAMMER_MCD_ENTITY_XML_TAG)) {
          Element comparedItem = (Element) shapes.item(j);
          if (comparedItem.getAttribute(Preferences.ATTRIBUTE_ID).equals(node.getAttribute(Preferences.ATTRIBUTE_SOURCE_ENTITY_SHAPE_ID)) && comparedItem.getAttribute(Preferences.ATTRIBUTE_ID).equals(node.getAttribute(Preferences.ATTRIBUTE_DESTINATION_ENTITY_SHAPE_ID))) {
            // Réflexive
            int entitiesId = Integer.parseInt(node.getAttribute(Preferences.ATTRIBUTE_DESTINATION_ENTITY_SHAPE_ID));
            destinationEntityShape = diagram.getMCDEntityShapeByID(entitiesId);
            sourceEntityShape = diagram.getMCDEntityShapeByID(entitiesId);
          } else if (comparedItem.getAttribute(Preferences.ATTRIBUTE_ID).equals(node.getAttribute(Preferences.ATTRIBUTE_SOURCE_ENTITY_SHAPE_ID))) {
            int sourceEntityId = Integer.parseInt(node.getAttribute(Preferences.ATTRIBUTE_SOURCE_ENTITY_SHAPE_ID));
            sourceEntityShape = diagram.getMCDEntityShapeByID(sourceEntityId);
          } else if (comparedItem.getAttribute(Preferences.ATTRIBUTE_ID).equals(node.getAttribute(Preferences.ATTRIBUTE_DESTINATION_ENTITY_SHAPE_ID))) {
            int destinationEntityId = Integer.parseInt(node.getAttribute(Preferences.ATTRIBUTE_DESTINATION_ENTITY_SHAPE_ID));
            destinationEntityShape = diagram.getMCDEntityShapeByID(destinationEntityId);
          }
        }
      }
    }

    MCDAssociationShape newAssociation;

    // Récupère l'entité du référentiel liée
    if (node.hasAttribute(Preferences.ATTRIBUTE_REPOSITORY_ASSOCIATION_ID)) {
      int relatedRepositoryAssociationId = Integer.parseInt(node.getAttribute(Preferences.ATTRIBUTE_REPOSITORY_ASSOCIATION_ID));
      MCDAssociation associationFound = (MCDAssociation) mcd.getRelations().getChildById(relatedRepositoryAssociationId);
      newAssociation = new MCDAssociationShape(associationShapeId, associationFound, sourceEntityShape, destinationEntityShape, isReflexive);
    } else {
      newAssociation = new MCDAssociationShape(associationShapeId, sourceEntityShape, destinationEntityShape, isReflexive);
    }

    // Ajoute les points d'ancrage
      this.loadAnchorPoints(newAssociation, node);

    // Ajoute les labels
      if (this.elementHasChildByTagName(node, Preferences.DIAGRAMMER_LABELS_XML_TAG_NAME)) {
          this.loadLabelShapes(newAssociation, node, diagramTag);
      }

    // Ajoute l'association au diagramme
    diagram.addShape(newAssociation);
  }

  private void loadLabelShapes(MCDAssociationShape associationShape, Element currentNode, Element diagramTag) {
    // Récupère les labels
    NodeList labelsParentTag = currentNode.getElementsByTagName(Preferences.DIAGRAMMER_LABELS_XML_TAG_NAME);
    NodeList allLabelsTags = labelsParentTag.item(0).getChildNodes();

    // Vide la liste des labels de l'association
    associationShape.getLabels().clear();

    for (int i = 0; i < allLabelsTags.getLength(); i++) {

      if (allLabelsTags.item(i) instanceof Element) {
        Element currentLabelTag = (Element) allLabelsTags.item(i);
        String type = currentLabelTag.getAttribute(Preferences.ATTRIBUTE_TYPE);

        int relatedAnchorPointId = Integer.parseInt(currentLabelTag.getAttribute(Preferences.ATTRIBUTE_RELATED_ANCHOR_POINT_ID));
        int distanceInXFromAnchorPoint = Integer.parseInt(currentLabelTag.getAttribute(Preferences.ATTRIBUTE_X_DISTANCE_FROM_ANCHOR_POINT));
        int distanceInYFromAnchorPoint = Integer.parseInt(currentLabelTag.getAttribute(Preferences.ATTRIBUTE_Y_DISTANCE_FROM_ANCHOR_POINT));

        // Récupère le point d'ancrage lié
        List<RelationPointAncrageShape> anchorPointFound = associationShape.getPointsAncrage().stream().filter(anchorPoint -> anchorPoint.getId() == relatedAnchorPointId).collect(Collectors.toList());

        // Crée le LabelShape
        if (!anchorPointFound.isEmpty()) {
          // Nom d'association
          if (type.equals(LabelType.ASSOCIATION_NAME.name())) {
            associationShape.createOrUpdateLabel(anchorPointFound.get(0), associationShape.getMCDAssociation().getName(), LabelType.ASSOCIATION_NAME, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);
          } else if (type.equals(LabelType.DESTINATION_CARDINALITY.name())) {
            // Cardinalités destination
            associationShape.createOrUpdateLabel(anchorPointFound.get(0), associationShape.getMCDAssociation().getTo().getMultiStr(), LabelType.DESTINATION_CARDINALITY, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);
          } else if (type.equals(LabelType.SOURCE_CARDINALITY.name())) {
            // Cardinalités source
            associationShape.createOrUpdateLabel(anchorPointFound.get(0), associationShape.getMCDAssociation().getFrom().getMultiStr(), LabelType.SOURCE_CARDINALITY, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);
          } else if (type.equals(LabelType.SOURCE_ROLE.name())) {
            // Rôle source
            associationShape.createOrUpdateLabel(anchorPointFound.get(0), associationShape.getMCDAssociation().getFrom().getName(), LabelType.SOURCE_ROLE, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);
          } else {
            // Rôle destination
            associationShape.createOrUpdateLabel(anchorPointFound.get(0), associationShape.getMCDAssociation().getTo().getName(), LabelType.DESTINATION_ROLE, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);
          }

        }
      }
    }
  }

  private void loadAnchorPoints(MCDAssociationShape associationShape, Element currentNode) {
    // Vide la liste des points d'ancrage
    associationShape.getPointsAncrage().clear();

    // Récupère les points d'ancrage
    NodeList anchorPointsParentTag = currentNode.getElementsByTagName(Preferences.DIAGRAMMER_ANCHOR_POINTS_XML_TAG_NAME);
    NodeList allAnchorPointTags = anchorPointsParentTag.item(0).getChildNodes();

    for (int i = 0; i < allAnchorPointTags.getLength(); i++) {
      if (allAnchorPointTags.item(i) instanceof Element) {
        Element pointTag = (Element) allAnchorPointTags.item(i);
        Point point = new Point(Integer.parseInt(pointTag.getAttribute("x")), Integer.parseInt(pointTag.getAttribute("y")));
        RelationPointAncrageShape pointAncrageShape = new RelationPointAncrageShape(Integer.parseInt(pointTag.getAttribute("id")), point);
        associationShape.getPointsAncrage().add(pointAncrageShape);
      }
    }

    associationShape.reindexAllPointsAncrage();
  }

  private boolean elementHasChildByTagName(Element element, String tagNameToFind) {
    return element.getElementsByTagName(tagNameToFind).getLength() > 0;
  }

}