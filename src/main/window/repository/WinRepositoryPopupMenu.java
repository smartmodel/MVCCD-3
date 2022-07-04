package main.window.repository;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConDBMode;
import connections.ConnectionsDB;
import console.ConsoleManager;
import console.LogsManager;
import console.ViewLogsManager;
import consolidationMpdrDb.viewer.WaitingSyncViewer;
import constraints.Constraint;
import constraints.ConstraintService;
import constraints.ConstraintsManager;
import datatypes.MDDatatype;
import datatypes.MDDatatypeService;
import datatypes.MPDRDatatype;
import diagram.mcd.MCDDiagram;
import exceptions.service.ExceptionService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import m.interfaces.IMCompletness;
import m.interfaces.IMUMLExtensionNamingInBox;
import m.interfaces.IMUMLExtensionNamingInLine;
import main.MVCCDElement;
import main.MVCCDElementApplicationPreferences;
import main.MVCCDElementRepositoryRoot;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.MCDAssEnd;
import mcd.MCDAssociation;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import mcd.MCDContConstraints;
import mcd.MCDContDiagrams;
import mcd.MCDContEntities;
import mcd.MCDContModels;
import mcd.MCDContRelEnds;
import mcd.MCDContRelations;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.MCDGSEnd;
import mcd.MCDGeneralization;
import mcd.MCDLink;
import mcd.MCDLinkEnd;
import mcd.MCDModel;
import mcd.MCDNID;
import mcd.MCDPackage;
import mcd.MCDUnique;
import mcd.interfaces.IMCDCompliant;
import mcd.interfaces.IMCDElementWithTargets;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDNIDService;
import mdr.MDRColumn;
import mdr.MDRRelFKEnd;
import mdr.MDRRelationFK;
import mdr.interfaces.IMDRElementWithIteration;
import messages.MessagesBuilder;
import mldr.MLDRColumn;
import mldr.MLDRFK;
import mldr.MLDRModel;
import mldr.MLDRPK;
import mldr.MLDRParameter;
import mldr.MLDRRelFKEnd;
import mldr.MLDRRelationFK;
import mldr.MLDRTable;
import mldr.MLDRUnique;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mpdr.MPDRCheck;
import mpdr.MPDRColumn;
import mpdr.MPDRContDiagrams;
import mpdr.MPDRDB;
import mpdr.MPDRFK;
import mpdr.MPDRModel;
import mpdr.MPDRPK;
import mpdr.MPDRParameter;
import mpdr.MPDRRelFKEnd;
import mpdr.MPDRRelationFK;
import mpdr.MPDRSequence;
import mpdr.MPDRTable;
import mpdr.MPDRUnique;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.tapis.MPDRColumnJnal;
import mpdr.tapis.MPDRStoredCode;
import mpdr.tapis.MPDRTrigger;
import mpdr.tapis.MPDRView;
import mpdr.tapis.interfaces.ITapisElementWithSource;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.Profile;
import profile.ProfileSaverXml;
import project.Project;
import project.ProjectElement;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.ProjectEditingTreat;
import repository.editingTreat.connections.connector.ConConnectorEditingTreat;
import repository.editingTreat.diagram.MCDDiagramEditingTreat;
import repository.editingTreat.diagram.MPDRDiagramEditingTreat;
import repository.editingTreat.mcd.MCDAssEndEditingTreat;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import repository.editingTreat.mcd.MCDAttributeEditingTreat;
import repository.editingTreat.mcd.MCDAttributesEditingTreat;
import repository.editingTreat.mcd.MCDCompliantEditingTreat;
import repository.editingTreat.mcd.MCDConstraintsEditingTreat;
import repository.editingTreat.mcd.MCDContModelsEditingTreat;
import repository.editingTreat.mcd.MCDEntCompliantEditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import repository.editingTreat.mcd.MCDGSEndEditingTreat;
import repository.editingTreat.mcd.MCDGeneralizationEditingTreat;
import repository.editingTreat.mcd.MCDLinkEditingTreat;
import repository.editingTreat.mcd.MCDLinkEndEditingTreat;
import repository.editingTreat.mcd.MCDModelEditingTreat;
import repository.editingTreat.mcd.MCDNIDEditingTreat;
import repository.editingTreat.mcd.MCDNIDParameterEditingTreat;
import repository.editingTreat.mcd.MCDPackageEditingTreat;
import repository.editingTreat.mcd.MCDRelEndsEditingTreat;
import repository.editingTreat.mcd.MCDRelationsEditingTreat;
import repository.editingTreat.mcd.MCDTargetsEditingTreat;
import repository.editingTreat.mcd.MCDTransformEditingTreat;
import repository.editingTreat.mcd.MCDUniqueEditingTreat;
import repository.editingTreat.mcd.MCDUniqueParameterEditingTreat;
import repository.editingTreat.md.MDDatatypeEditingTreat;
import repository.editingTreat.mdr.MDRCheckEditingTreat;
import repository.editingTreat.mdr.MDRColumnEditingTreat;
import repository.editingTreat.mdr.MDRFKEditingTreat;
import repository.editingTreat.mdr.MDRPKEditingTreat;
import repository.editingTreat.mdr.MDRParameterEditingTreat;
import repository.editingTreat.mdr.MDRTableEditingTreat;
import repository.editingTreat.mdr.MDRUniqueEditingTreat;
import repository.editingTreat.mldr.MLDRModelEditingTreat;
import repository.editingTreat.mpdr.MPDRModelEditingTreat;
import repository.editingTreat.mpdr.MPDRSequenceEditingTreat;
import repository.editingTreat.mpdr.MPDRStoredCodeEditingTreat;
import repository.editingTreat.mpdr.MPDRTriggerEditingTreat;
import repository.editingTreat.mpdr.MPDRViewEditingTreat;
import repository.editingTreat.naming.NamingEditingTreat;
import repository.editingTreat.preferences.PrefApplEditingTreat;
import repository.editingTreat.preferences.PrefGeneralEditingTreat;
import repository.editingTreat.preferences.PrefMCDEditingTreat;
import repository.editingTreat.preferences.PrefMCDToMLDREditingTreat;
import repository.editingTreat.preferences.PrefMDREditingTreat;
import repository.editingTreat.preferences.PrefMDRFormatEditingTreat;
import repository.editingTreat.preferences.PrefMLDREditingTreat;
import repository.editingTreat.preferences.PrefMLDRToMPDREditingTreat;
import repository.editingTreat.preferences.PrefMPDRMySQLEditingTreat;
import repository.editingTreat.preferences.PrefMPDROracleEditingTreat;
import repository.editingTreat.preferences.PrefMPDRPostgreSQLEditingTreat;
import utilities.DefaultMutableTreeNodeService;
import utilities.window.DialogMessage;
import utilities.window.scomponents.ISMenu;
import utilities.window.scomponents.SMenu;
import utilities.window.scomponents.SPopupMenu;

public class WinRepositoryPopupMenu extends SPopupMenu {

  @Serial
  private static final long serialVersionUID = 2650445726860322512L;
  private DefaultMutableTreeNode node;
  private DefaultTreeModel treeModel;
  private MVCCDWindow mvccdWindow;
  private MVCCDElement mvccdElement;

  public WinRepositoryPopupMenu(DefaultMutableTreeNode node) {
    this.treeModel = this.treeModel;
    this.node = node;
    this.mvccdWindow = MVCCDManager.instance().getMvccdWindow();
    this.mvccdElement = (MVCCDElement) node.getUserObject();
    this.init();
  }

  private void init() {

    try {
      Preferences preferences = PreferencesManager.instance().preferences();
      // Effacement des anciens contenus qui ne devraient plus subsister si aucune erreur ne survient
      String message = MessagesBuilder.getMessagesProperty("file.add.text", LogsManager.getlogFilePath());
      ConsoleManager consoleManager = MVCCDManager.instance().getConsoleManager();
      consoleManager.clearMessages();
      if (PreferencesManager.instance().getApplicationPref().isDEBUG()) {
        if (PreferencesManager.instance().getApplicationPref().getDEBUG_INSPECT_OBJECT_IN_TREE()) {
          this.treatInspectObject();
        }
        if (this.node.getUserObject() instanceof MVCCDElementRepositoryRoot) {
          this.treatRepositoryRoot(this);
        }
      }

      //TODO-1 A terme, mettre une restriction Debug ou autre
      this.treatNaming(this);

      if (this.node.getUserObject() instanceof IMLDRElementWithSource) {
        this.treatSourceMLDRElementWithSource();
      }

      if (this.node.getUserObject() instanceof IMPDRElementWithSource) {
        this.treatSourceMPDRElementWithSource();
      }

      if (this.node.getUserObject() instanceof ITapisElementWithSource) {
        this.treatSourceITapisElementWithSource();
      }

      if (this.node.getUserObject() instanceof MVCCDElementApplicationPreferences) {
        this.treatGenericUpdate(this, new PrefApplEditingTreat());
      }

      if (this.node.getUserObject() instanceof MDDatatype) {
        this.treatGenericRead(this, new MDDatatypeEditingTreat());
      }

      if (this.node.getUserObject() instanceof ConnectionsDB) {
        this.treatConnections(this);
      }

      if (this.node.getUserObject() instanceof ConConnection) {
        this.treatConConnection(this);
      }

      if (this.node.getUserObject() instanceof ConConnector) {
        this.treatConConnector(this);
      }

      if (this.node.getUserObject() instanceof Preferences) {
        this.treatPreferences(this);
      }

      if (this.node.getUserObject() instanceof Profile) {
        this.treatProfile(this);
      }

      if (this.node.getUserObject() instanceof Project) {
        this.treatProject(this);
      }

      if (this.node.getUserObject() instanceof MCDContModels) {
        this.treatMCDModels(this);
      }

      if (this.node.getUserObject() instanceof MCDModel) {
        this.treatGeneric(this, new MCDModelEditingTreat());
        MCDModel mcdModel = (MCDModel) this.node.getUserObject();
        if (mcdModel.isPackagesAutorizeds()) {
          this.packageNew(this, "menu.new.package");
        }
        this.treatGenericCompliantMCD(this, new MCDModelEditingTreat());
        this.treatGenericTransformMCD(this, new MCDModelEditingTreat(), "menu.transform.mcd.to.mldr");
      }

      if (this.node.getUserObject() instanceof MCDPackage) {
        this.treatGeneric(this, new MCDPackageEditingTreat());
        this.treatGenericCompliantMCD(this, new MCDPackageEditingTreat());
        MCDPackage mcdPackage = (MCDPackage) this.node.getUserObject();
        // Pas nécessaire de vérifier les droits de créer unpackage puisque le parent est déjà un package
        this.packageNew(this, "menu.new.subpackage");

      }

      if (this.node.getUserObject() instanceof MCDContDiagrams) {
        this.treatGenericNew(this, new MCDDiagramEditingTreat(), MessagesBuilder.getMessagesProperty("menu.new.diagram"));
      }

      if (this.node.getUserObject() instanceof MPDRContDiagrams) {
        this.treatGenericNew(this, new MPDRDiagramEditingTreat(), MessagesBuilder.getMessagesProperty("menu.new.diagram"));
      }

      if (this.node.getUserObject() instanceof MCDDiagram) {
        this.treatGeneric(this, new MCDDiagramEditingTreat());
      }

      if (this.node.getUserObject() instanceof MCDContEntities) {
        this.treatGenericNew(this, new MCDEntityEditingTreat());
      }

      if (this.node.getUserObject() instanceof MCDEntity) {
        this.treatGeneric(this, new MCDEntityEditingTreat());
        //Contrôle de complétude
        this.treatGenericRead(this, new MCDEntCompliantEditingTreat(), MessagesBuilder.getMessagesProperty("menu.compliant"));
      }

      if (this.node.getUserObject() instanceof MCDContAttributes) {
        this.treatGenericNew(this, new MCDAttributeEditingTreat());
        this.treatGenericRead(this, new MCDAttributesEditingTreat());
      }

      if (this.node.getUserObject() instanceof MCDContConstraints) {
        this.treatConstraints(this);
      }

      if (this.node.getUserObject() instanceof MCDAttribute) {
        this.treatGeneric(this, new MCDAttributeEditingTreat());
        MCDAttribute mcdAttribute = (MCDAttribute) this.node.getUserObject();
        if (MCDNIDService.attributeCandidateForNID1(mcdAttribute)) {
          this.treatCreateNID1FromAttribute();
        }
      }

      if (this.node.getUserObject() instanceof MCDContRelEnds) {
        this.treatGenericRead(this, new MCDRelEndsEditingTreat());
      }

      if (this.node.getUserObject() instanceof MCDContRelations) {
        this.treatRelations(this);
      }

      if ((this.node.getUserObject() instanceof MCDUnique) && (!(this.node.getUserObject() instanceof MCDNID))) {
        this.treatGenericNew(this, new MCDUniqueParameterEditingTreat(), MessagesBuilder.getMessagesProperty("menu.new.operation.parameter"));
        this.treatGeneric(this, new MCDUniqueEditingTreat());
      }

      if (this.node.getUserObject() instanceof MCDNID) {
        this.treatGenericNew(this, new MCDNIDParameterEditingTreat(), MessagesBuilder.getMessagesProperty("menu.new.operation.parameter"));
        this.treatGeneric(this, new MCDNIDEditingTreat());
      }

      if (this.node.getUserObject() instanceof MCDAssociation) {
        this.treatGeneric(this, new MCDAssociationEditingTreat());
        //TODO-1 Développer la classe MCDAssociationCompliantEditingTreat
        // à l'image de MCDEntityCompliantEditingTreat
        // treatGenericRead(this, new MCDAssociationCompliantEditingTreat(),
        //            MessagesBuilder.getMessagesProperty("menu.compliant"));

      }

      if (this.node.getUserObject() instanceof MCDAssEnd) {
        //mvccdElement = ((MCDAssEnd) node.getUserObject()).getMcdAssociation();
        //treatGeneric(this, new MCDAssociationEditingTreat());
        this.treatGeneric(this, new MCDAssEndEditingTreat());
      }

      if (this.node.getUserObject() instanceof MCDGeneralization) {
        this.treatGeneric(this, new MCDGeneralizationEditingTreat());
      }

      if (this.node.getUserObject() instanceof MCDGSEnd) {
        //mvccdElement = ((MCDGSEnd) node.getUserObject()).getMcdGeneralization();
        //treatGeneric(this, new MCDGeneralizationEditingTreat());
        this.treatGeneric(this, new MCDGSEndEditingTreat());
      }

      if (this.node.getUserObject() instanceof MCDLink) {
        this.treatGeneric(this, new MCDLinkEditingTreat());
      }

      if (this.node.getUserObject() instanceof MCDLinkEnd) {
        //mvccdElement = ((MCDLinkEnd) node.getUserObject()).getMcdLink();
        //treatGeneric(this, new MCDLinkEditingTreat());
        this.treatGeneric(this, new MCDLinkEndEditingTreat());

      }

      if (this.node.getUserObject() instanceof IMCDElementWithTargets) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.mcd.targets.read");
        this.treatGenericRead(this, new MCDTargetsEditingTreat(), textMenu);
      }

      if (this.node.getUserObject() instanceof MLDRModel) {
        this.treatGeneric(this, new MLDRModelEditingTreat());

        JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.transform.mldr.to.mpdr"));
        this.addItem(this, menuItem);
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            try {
              (new MLDRModelEditingTreat()).treatTransform(WinRepositoryPopupMenu.this.mvccdWindow, WinRepositoryPopupMenu.this.mvccdElement);
            } catch (Exception e) {
              WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.transform");
            }
          }
        });
      }

      if (this.node.getUserObject() instanceof MLDRTable) {
        this.treatGenericRead(this, new MDRTableEditingTreat());
      }

      if (this.node.getUserObject() instanceof MLDRColumn) {
        this.treatGenericRead(this, new MDRColumnEditingTreat());
      }

      if (this.node.getUserObject() instanceof MLDRPK) {
        this.treatGenericRead(this, new MDRPKEditingTreat());
      }

      if (this.node.getUserObject() instanceof MLDRFK) {
        this.treatGenericRead(this, new MDRFKEditingTreat());
      }

      if (this.node.getUserObject() instanceof MLDRUnique) {
        this.treatGenericRead(this, new MDRUniqueEditingTreat());
      }

      if (this.node.getUserObject() instanceof MLDRParameter) {
        this.treatGenericRead(this, new MDRParameterEditingTreat());
      }

      if (this.node.getUserObject() instanceof MLDRRelationFK) {
        this.treatMDRRelationFKRead(this);
      }

      if (this.node.getUserObject() instanceof MLDRRelFKEnd) {
        this.treatMDRRelFKEndRead(this);
      }

      if (this.node.getUserObject() instanceof MPDRModel) {
        this.treatGeneric(this, new MPDRModelEditingTreat());

        JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.generate.sql.from.mpdr"));
        this.addItem(this, menuItem);
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            try {
              (new MPDRModelEditingTreat()).treatGenerate(WinRepositoryPopupMenu.this.mvccdWindow, WinRepositoryPopupMenu.this.mvccdElement);
            } catch (Exception e) {
              WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.generate.sql");
            }
          }
        });
      }

            //TODO VINCENT
            if (node.getUserObject() instanceof MPDRModel) {
                JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty(
                        "menu.generate.sql.from.mpdr.and.sgbdr"));
                addItem(this, menuItem);
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            WaitingSyncViewer waitingSyncViewer = new WaitingSyncViewer();
                            new MPDRModelEditingTreat().treatSyncMpdrDb(mvccdWindow, mvccdElement, waitingSyncViewer);
                            waitingSyncViewer.setVisible(false);
                        } catch (Exception e){
                            exceptionUnhandled(e, mvccdElement, "repository.menu.exception.consolidation.sql");
                        }
                    }
                });
            }

            if (node.getUserObject() instanceof MPDRTable) {
                treatGenericRead(this, new MDRTableEditingTreat());
            }
      if (this.node.getUserObject() instanceof MPDRTable) {
        this.treatGenericRead(this, new MDRTableEditingTreat());
      }

      if (this.node.getUserObject() instanceof MPDRColumn) {
        this.treatGenericRead(this, new MDRColumnEditingTreat());
      }

      if (this.node.getUserObject() instanceof MPDRColumnJnal) {
        this.treatViewMPRDColumnJnal();
      }

      if (this.node.getUserObject() instanceof MPDRPK) {
        this.treatGenericRead(this, new MDRPKEditingTreat());
      }

      if (this.node.getUserObject() instanceof MPDRFK) {
        this.treatGenericRead(this, new MDRFKEditingTreat());
      }

      if (this.node.getUserObject() instanceof MPDRUnique) {
        this.treatGenericRead(this, new MDRUniqueEditingTreat());
      }

      if (this.node.getUserObject() instanceof MPDRCheck) {
        this.treatGenericRead(this, new MDRCheckEditingTreat());
      }

      if (this.node.getUserObject() instanceof MPDRParameter) {
        this.treatGenericRead(this, new MDRParameterEditingTreat());
      }

      if (this.node.getUserObject() instanceof MPDRRelationFK) {
        this.treatMDRRelationFKRead(this);
      }

      if (this.node.getUserObject() instanceof MPDRRelFKEnd) {
        this.treatMDRRelFKEndRead(this);
      }

      if (this.node.getUserObject() instanceof MPDRSequence) {
        this.treatGenericRead(this, new MPDRSequenceEditingTreat());
      }

      if (this.node.getUserObject() instanceof MPDRTrigger) {
        this.treatGenericRead(this, new MPDRTriggerEditingTreat());
      }

      if (this.node.getUserObject() instanceof MPDRStoredCode) {
        this.treatGenericRead(this, new MPDRStoredCodeEditingTreat());
      }

      if (this.node.getUserObject() instanceof MPDRView) {
        this.treatGenericRead(this, new MPDRViewEditingTreat());
      }

    } catch (Exception e) {
      //TODO-PAS A terme ce bloc de traitement d'exception devrait pouvoir être supprimé
      // si toutes les actionPerformed() qui modifient la présentation du référentiel
      // traitent les exceptions

      String message = MessagesBuilder.getMessagesProperty("repository.create.menu.exception");
      ViewLogsManager.catchException(e, this.mvccdWindow, message);
    }
  }

  private void treatInspectObject() {
    JMenuItem inspecter = new JMenuItem("Inspecter l'objet ");
    this.add(inspecter);
    inspecter.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          String message = "Classe : " + WinRepositoryPopupMenu.this.mvccdElement.getClass().getName();
          if (WinRepositoryPopupMenu.this.mvccdElement instanceof ProjectElement) {
            ProjectElement projectElement = (ProjectElement) WinRepositoryPopupMenu.this.mvccdElement;
            message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Id : " + projectElement.getIdProjectElement();
          }
          message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Ordre dans la fraterie : " + WinRepositoryPopupMenu.this.mvccdElement.getOrder();
          if (WinRepositoryPopupMenu.this.mvccdElement instanceof IMDRElementWithIteration) {
            IMDRElementWithIteration mdrElement = (IMDRElementWithIteration) WinRepositoryPopupMenu.this.mvccdElement;
            message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Itération : " + mdrElement.getIteration();
          }
          if (WinRepositoryPopupMenu.this.mvccdElement instanceof MDRRelationFK) {
            MDRRelationFK mdrRelationFK = (MDRRelationFK) WinRepositoryPopupMenu.this.mvccdElement;
            message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Contrainte FK - nom : " + mdrRelationFK.getMDRFK().getName();
            message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Contrainte FK - id : " + mdrRelationFK.getMDRFK().getIdProjectElement();
          }
          if (WinRepositoryPopupMenu.this.mvccdElement instanceof MDRRelFKEnd) {
            MDRRelFKEnd mdrRelFKEnd = (MDRRelFKEnd) WinRepositoryPopupMenu.this.mvccdElement;
            message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Rôle : " + mdrRelFKEnd.getRoleText();
          }
          if (WinRepositoryPopupMenu.this.mvccdElement instanceof IMUMLExtensionNamingInLine) {
            IMUMLExtensionNamingInLine imumlExtensionNamingInLine = (IMUMLExtensionNamingInLine) WinRepositoryPopupMenu.this.mvccdElement;
            message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Stéréo InLine : " + imumlExtensionNamingInLine.getStereotypesInLine();
            message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Contr. InLine : " + imumlExtensionNamingInLine.getConstraintsInLine();
          }
          if (WinRepositoryPopupMenu.this.mvccdElement instanceof IMUMLExtensionNamingInBox) {
            IMUMLExtensionNamingInBox imumlExtensionNamingInBox = (IMUMLExtensionNamingInBox) WinRepositoryPopupMenu.this.mvccdElement;
            message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Stéréo InBox : " + imumlExtensionNamingInBox.getStereotypesInBox();
            message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Contr. InBox : " + imumlExtensionNamingInBox.getConstraintsInBox();
          }
          message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Childs : ";
          int i = 0;
          for (MVCCDElement mvccdElement : WinRepositoryPopupMenu.this.mvccdElement.getChilds()) {
            if (i > 0) {
              message += ", ";
            }
            message = message + mvccdElement.getNameTree();
            i++;
          }

          new DialogMessage().showOk(WinRepositoryPopupMenu.this.mvccdWindow, message);
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.inspector");
        }
      }
    });
  }

  private void treatSourceMLDRElementWithSource() {
    JMenuItem source = new JMenuItem("Source conceptuelle");
    this.add(source);
    source.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          IMLDRElementWithSource imldrElementWithSource = (IMLDRElementWithSource) WinRepositoryPopupMenu.this.mvccdElement;
          MCDElement mcdElementSource = imldrElementWithSource.getMcdElementSource();
          String message = "Classe : " + mcdElementSource.getClass().getName();
          message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Nom     : " + mcdElementSource.getNameSourcePath();
          message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Id     : " + mcdElementSource.getIdProjectElement();

          new DialogMessage().showOk(WinRepositoryPopupMenu.this.mvccdWindow, message, "Source de niveau conceptuel de l'objet : " + WinRepositoryPopupMenu.this.mvccdElement.getName());

        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.source.mcd");
        }
      }
    });

  }

  private void treatSourceMPDRElementWithSource() {
    JMenuItem source = new JMenuItem("Source logique");
    this.add(source);
    source.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          IMPDRElementWithSource impdrElementWithSource = (IMPDRElementWithSource) WinRepositoryPopupMenu.this.mvccdElement;
          IMLDRElement mldrElementSource = impdrElementWithSource.getMldrElementSource();
          String message = "Classe : " + mldrElementSource.getClass().getName();
          message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Nom     : " + mldrElementSource.getName();
          message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Id     : " + mldrElementSource.getIdProjectElement();
          new DialogMessage().showOk(WinRepositoryPopupMenu.this.mvccdWindow, message, "Source de niveau logique de l'objet : " + WinRepositoryPopupMenu.this.mvccdElement.getName());
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.source.mldr");
        }
      }
    });
  }

  private void treatSourceITapisElementWithSource() {
    JMenuItem source = new JMenuItem("Source physique");
    this.add(source);
    source.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          ITapisElementWithSource iTapisElementWithSource = (ITapisElementWithSource) WinRepositoryPopupMenu.this.mvccdElement;
          IMPDRElement mpdrElementSource = iTapisElementWithSource.getMpdrElementSource();
          String message = "Classe : " + mpdrElementSource.getClass().getName();
          message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Nom     : " + mpdrElementSource.getNameTreePath();
          message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Id     : " + mpdrElementSource.getIdProjectElement();
          new DialogMessage().showOk(WinRepositoryPopupMenu.this.mvccdWindow, message, "Source de niveau physique de l'objet : " + WinRepositoryPopupMenu.this.mvccdElement.getName());
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.source.mpdr");
        }
      }
    });
  }

  private void treatViewMPRDColumnJnal() {
    JMenuItem source = new JMenuItem("Visualisation");
    this.add(source);
    source.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          MPDRColumnJnal mpdrColumnJnal = (MPDRColumnJnal) WinRepositoryPopupMenu.this.mvccdElement;
          MPDRDB mpdrdb = mpdrColumnJnal.getMPDRTableToJournalize().getMPDRModelParent().getDb();
          MPDRDatatype mpdrDatatype = MDDatatypeService.getMPDRDatatypeByLienProg(mpdrdb, mpdrColumnJnal.getDatatypeLienProg());
          Integer size = mpdrColumnJnal.getSize();
          Constraint constraint = ConstraintsManager.instance().constraints().getConstraintByLienProg(MDRColumn.class.getName(), mpdrColumnJnal.getDatatypeConstraintLienProg());
          String message = "Nom : " + mpdrColumnJnal.getName();
          message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Datatype     : " + mpdrColumnJnal.getDatatypeLienProg();
          if (size != null) {
            message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Taille     : " + size;
          }
          message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Contrainte     : " + ConstraintService.getUMLName(constraint);

          new DialogMessage().showOk(WinRepositoryPopupMenu.this.mvccdWindow, message, "Lecture : " + WinRepositoryPopupMenu.this.mvccdElement.getName());
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.source.mldr");
        }
      }
    });

  }

  private void treatCreateNID1FromAttribute() {
    JMenuItem source = new JMenuItem("Création NID-1");
    this.add(source);
    source.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          MCDNIDService.confirmCreateNID1FromAttribute(WinRepositoryPopupMenu.this.mvccdWindow, (MCDAttribute) WinRepositoryPopupMenu.this.node.getUserObject());
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.attribute.nid1");
        }
      }
    });

  }

  private void treatProfile(ISMenu menu) {
  }

  private void treatPreferences(ISMenu menu) {

    SMenu preferencesEdit = new SMenu(MessagesBuilder.getMessagesProperty("menu.preferences"));
    this.addItem(menu, preferencesEdit);

    //DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) node.getParent();

    if (DefaultMutableTreeNodeService.isObjectDescendantOf(this.node, new Profile(null, null))) {
      //if (nodeParent.getUserObject() instanceof Profile) {
      this.treatGenericRead(preferencesEdit, new PrefGeneralEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.general"));

      this.treatGenericRead(preferencesEdit, new PrefMCDEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mcd"));

      this.treatGenericRead(preferencesEdit, new PrefMDREditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mdr"));

      this.treatGenericRead(preferencesEdit, new PrefMDRFormatEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mdr.format"));

      this.treatGenericRead(preferencesEdit, new PrefMLDREditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mldr"));

      this.treatGenericRead(preferencesEdit, new PrefMCDToMLDREditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mcd.to.mldr"));

      // Sous-menu MPDR
      SMenu preferencesEditMPDR = new SMenu(MessagesBuilder.getMessagesProperty("menu.preferences"));
      this.addItem(preferencesEditMPDR, preferencesEditMPDR);

      this.treatGenericRead(preferencesEdit, new PrefMPDROracleEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.oracle"));

      this.treatGenericRead(preferencesEditMPDR, new PrefMPDRMySQLEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.mysql"));

      this.treatGenericRead(preferencesEditMPDR, new PrefMPDRPostgreSQLEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.postgresql"));

      // Fin Sous-menu MPDR

      this.treatGenericRead(preferencesEdit, new PrefMLDRToMPDREditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mldr.to.mpdr"));
    }

    if (DefaultMutableTreeNodeService.isObjectDescendantOf(this.node, new Project(null))) {
      //if (nodeParent.getUserObject() instanceof Project) {
      this.treatGenericUpdate(preferencesEdit, new PrefGeneralEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.general"));

      this.treatGenericUpdate(preferencesEdit, new PrefMCDEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mcd"));

      this.treatGenericUpdate(preferencesEdit, new PrefMDREditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mdr"));

      this.treatGenericUpdate(preferencesEdit, new PrefMDRFormatEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mdr.format"));

      this.treatGenericUpdate(preferencesEdit, new PrefMLDREditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mldr"));

      this.treatGenericUpdate(preferencesEdit, new PrefMCDToMLDREditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mcd.to.mldr"));

      // Sous-menu MPDR
      SMenu preferencesEditMPDR = new SMenu(MessagesBuilder.getMessagesProperty("menu.preferences.mpdr"));
      this.addItem(preferencesEdit, preferencesEditMPDR);

      this.treatGenericUpdate(preferencesEditMPDR, new PrefMPDROracleEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.oracle"));

      this.treatGenericUpdate(preferencesEditMPDR, new PrefMPDRMySQLEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.mysql"));

      this.treatGenericUpdate(preferencesEditMPDR, new PrefMPDRPostgreSQLEditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.postgresql"));

      // Fin Sous-menu MPDR

      this.treatGenericUpdate(preferencesEdit, new PrefMLDRToMPDREditingTreat(), MessagesBuilder.getMessagesProperty("menu.preferences.mldr.to.mpdr"));

      JMenuItem preferencesExportProfil = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.export.profil.preferences"));
      this.addItem(menu, preferencesExportProfil);
      preferencesExportProfil.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          try {
            if (PreferencesManager.instance().preferences().isPERSISTENCE_SERIALISATION_INSTEADOF_XML()) {
              PreferencesManager.instance().createProfile(); //Persistance avec sérialisation
            } else {
              new ProfileSaverXml().createFileProfileXML(); //Ajout de Giorgio
            }
          } catch (Exception e) {
            WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.export.pref");
          }
        }
      });

    }
  }

  private void treatProject(ISMenu menu) {
    JMenuItem editProject = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.update"));
    this.addItem(menu, editProject);
    editProject.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          // Pas d'extension de la classe EditingTreat
          ProjectEditingTreat.treatUpdate(WinRepositoryPopupMenu.this.mvccdWindow);
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.update");
        }
      }
    });
  }

  private void treatConnections(ISMenu menu) {
        /*if (node.getUserObject() instanceof ConnectionsOracle) {
            treatGenericNew(this, new ConConnectionOracleEditingTreat());
        }

         */
    if (this.node.getUserObject() instanceof ConnectionsDB) {
      ConnectionsDB connectionsDB = (ConnectionsDB) this.node.getUserObject();
      this.treatGenericNew(this, connectionsDB.getConConnectionEditingTreat());
    }
  }

  private void treatMCDModels(ISMenu menu) {
    if (PreferencesManager.instance().preferences().getREPOSITORY_MCD_MODELS_MANY()) {
      this.treatGenericNew(menu, new MCDModelEditingTreat(), MessagesBuilder.getMessagesProperty("menu.new.model"));
    } else {
      if (PreferencesManager.instance().preferences().getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS()) {
        this.packageNew(menu, "menu.new.package");
      }
      this.treatGenericCompliantMCD(menu, new MCDContModelsEditingTreat());
      this.treatGenericTransformMCD(menu, new MCDContModelsEditingTreat(), "menu.transform.mcd.to.mldr");
    }
  }

  private void treatRelations(ISMenu menu) {
    this.treatGenericNew(this, new MCDAssociationEditingTreat(), MessagesBuilder.getMessagesProperty("menu.new.association"));
    this.treatGenericNew(this, new MCDGeneralizationEditingTreat(), MessagesBuilder.getMessagesProperty("menu.new.generalization"));
    this.treatGenericNew(this, new MCDLinkEditingTreat(), MessagesBuilder.getMessagesProperty("menu.new.link"));
    this.treatGenericDeleteChilds(this, new MCDRelationsEditingTreat());
  }

  private void treatConstraints(ISMenu menu) {
    this.treatGenericNew(this, new MCDNIDEditingTreat(), MessagesBuilder.getMessagesProperty("menu.new.constraint.nid"));
    this.treatGenericNew(this, new MCDUniqueEditingTreat(), MessagesBuilder.getMessagesProperty("menu.new.constraint.unique"));
    this.treatGenericRead(this, new MCDConstraintsEditingTreat());

  }

  private void packageNew(ISMenu menu, String propertyMessage) {
    this.treatGenericNew(menu, new MCDPackageEditingTreat(), MessagesBuilder.getMessagesProperty(propertyMessage));
  }

  private void treatGeneric(ISMenu menu, EditingTreat editingTreat) {
    this.treatGenericRead(menu, editingTreat);
    this.treatGenericUpdate(menu, editingTreat);
    this.treatGenericDelete(menu, editingTreat);

    if (this.mvccdElement instanceof IMCompletness) {
      this.treatGenericCompletness(menu, editingTreat);
    }
  }

  private void treatGenericNew(ISMenu menu, EditingTreat editingTreat) {
    String textMenu = MessagesBuilder.getMessagesProperty("menu.new");
    this.treatGenericNew(menu, editingTreat, textMenu);
  }

  private void treatGenericNew(ISMenu menu, EditingTreat editingTreat, String textMenu) {
    JMenuItem menuItem = new JMenuItem(textMenu);
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          editingTreat.treatNew(WinRepositoryPopupMenu.this.mvccdWindow, WinRepositoryPopupMenu.this.mvccdElement);
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.new");
        }
      }
    });
  }

  private void treatGenericRead(ISMenu menu, EditingTreat editingTreat) {
    String textMenu = MessagesBuilder.getMessagesProperty("menu.read");
    this.treatGenericRead(menu, editingTreat, textMenu);
  }

  private void treatGenericRead(ISMenu menu, EditingTreat editingTreat, String textMenu) {
    JMenuItem menuItem = new JMenuItem(textMenu);
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          editingTreat.treatRead(WinRepositoryPopupMenu.this.mvccdWindow, WinRepositoryPopupMenu.this.mvccdElement);
        } catch (Exception e) {
          String propertyMessage;
          if (editingTreat instanceof MCDEntCompliantEditingTreat) {
            propertyMessage = "repository.menu.exception.compliant";
          } else {
            propertyMessage = "repository.menu.exception.read";
          }
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, propertyMessage);
        }
      }
    });
  }

  private void treatGenericUpdate(ISMenu menu, EditingTreat editingTreat) {
    String textMenu = MessagesBuilder.getMessagesProperty("menu.update");
    this.treatGenericUpdate(menu, editingTreat, textMenu);
  }

  private void treatGenericUpdate(ISMenu menu, EditingTreat editingTreat, String textMenu) {
    JMenuItem menuItem = new JMenuItem(textMenu);
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          editingTreat.treatUpdate(WinRepositoryPopupMenu.this.mvccdWindow, WinRepositoryPopupMenu.this.mvccdElement);
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.update");
        }
      }
    });
  }

  private void treatGenericDelete(ISMenu menu, EditingTreat editingTreat) {
    String textMenu = MessagesBuilder.getMessagesProperty("menu.delete");
    this.treatGenericDelete(menu, editingTreat, textMenu);
  }

  private void treatGenericDelete(ISMenu menu, EditingTreat editingTreat, String textMenu) {
    JMenuItem menuItem = new JMenuItem(textMenu);
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          editingTreat.treatDelete(WinRepositoryPopupMenu.this.mvccdWindow, WinRepositoryPopupMenu.this.mvccdElement);
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.delete");
        }
      }
    });
  }

  private void treatGenericDeleteChilds(ISMenu menu, EditingTreat editingTreat) {
    String textMenu = MessagesBuilder.getMessagesProperty("menu.delete.childs");
    this.treatGenericDeleteChilds(menu, editingTreat, textMenu);
  }

  private void treatGenericDeleteChilds(ISMenu menu, EditingTreat editingTreat, String textMenu) {
    JMenuItem menuItem = new JMenuItem(textMenu);
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          editingTreat.treatDeleteChilds(WinRepositoryPopupMenu.this.mvccdWindow, WinRepositoryPopupMenu.this.mvccdElement);
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.delete.childs");
        }
      }
    });
  }

  private void treatGenericCompletness(ISMenu menu, EditingTreat editingTreat) {
    JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.completness"));
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          boolean ok = editingTreat.treatCompletness(WinRepositoryPopupMenu.this.mvccdWindow, WinRepositoryPopupMenu.this.mvccdElement, true);
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.completness");
        }
      }
    });
  }

  private void treatGenericCompliantMCD(ISMenu menu, MCDCompliantEditingTreat mcdCompliantEditingTreat) {

    JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.compliant"));
    this.addItem(menu, menuItem);

    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          mcdCompliantEditingTreat.treatCompliant(WinRepositoryPopupMenu.this.mvccdWindow, (IMCDCompliant) WinRepositoryPopupMenu.this.mvccdElement);
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.compliant");
        }
      }
    });
  }

  private void treatGenericTransformMCD(ISMenu menu, MCDTransformEditingTreat mcdTransformEditingTreat, String propertyTextMenu) {
    JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty(propertyTextMenu));
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          mcdTransformEditingTreat.treatTransform(WinRepositoryPopupMenu.this.mvccdWindow, (IMCDModel) WinRepositoryPopupMenu.this.mvccdElement);
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.transform");
        }
      }
    });
  }

  private void treatNaming(ISMenu menu) {
    String textMenu = MessagesBuilder.getMessagesProperty("menu.mvccdelement.naming");
    JMenuItem menuItem = new JMenuItem(textMenu);
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          Preferences preferences = PreferencesManager.instance().preferences();

          // Mémorisation de la préférence qui peut être modifiée
          String pathNaming = preferences.getMCD_TREE_NAMING_ASSOCIATION();

          // Appel du formulaire
          (new NamingEditingTreat()).treatNaming(WinRepositoryPopupMenu.this.mvccdWindow, WinRepositoryPopupMenu.this.mvccdElement);

          //Remise en l'état initial de la préférence
          if (!preferences.getMCD_TREE_NAMING_ASSOCIATION().equals(pathNaming)) {
            preferences.setMCD_TREE_NAMING_ASSOCIATION(pathNaming);
          }

        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.naming");
        }
      }
    });
  }

  private void treatRepositoryRoot(WinRepositoryPopupMenu menu) {
    String textMenu = "Visualisation des CLASSPATH";
    JMenuItem menuItem = new JMenuItem(textMenu);
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          String classpath = System.getProperty("java.class.path");
          String[] classpathEntries = classpath.split(System.getProperty("path.separator"));
          DialogMessage.showOk(WinRepositoryPopupMenu.this.mvccdWindow, classpath, "Liste des CLASSPATH");
        } catch (Exception e) {
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, "repository.menu.exception.classpath");
        }
      }
    });

  }

  private void treatMDRRelationFKRead(ISMenu menu) {
    String textMenu = MessagesBuilder.getMessagesProperty("menu.read");
    JMenuItem menuItem = new JMenuItem(textMenu);
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          MDRRelationFK mdrRelationFK = (MDRRelationFK) WinRepositoryPopupMenu.this.mvccdElement;
          new MDRFKEditingTreat().treatRead(WinRepositoryPopupMenu.this.mvccdWindow, mdrRelationFK.getMDRFK());
        } catch (Exception e) {
          String propertyMessage;
          propertyMessage = "repository.menu.exception.read";
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, propertyMessage);
        }
      }
    });
  }

  private void treatMDRRelFKEndRead(ISMenu menu) {
    String textMenu = MessagesBuilder.getMessagesProperty("menu.read");
    JMenuItem menuItem = new JMenuItem(textMenu);
    this.addItem(menu, menuItem);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          MDRRelFKEnd mdrRelFKEnd = (MDRRelFKEnd) WinRepositoryPopupMenu.this.mvccdElement;
          new MDRFKEditingTreat().treatRead(WinRepositoryPopupMenu.this.mvccdWindow, mdrRelFKEnd.getMDRRelationFK().getMDRFK());
        } catch (Exception e) {
          String propertyMessage;
          propertyMessage = "repository.menu.exception.read";
          WinRepositoryPopupMenu.this.exceptionUnhandled(e, WinRepositoryPopupMenu.this.mvccdElement, propertyMessage);
        }
      }
    });
  }

  private void treatConConnection(WinRepositoryPopupMenu winRepositoryPopupMenu) {

    if (this.node.getUserObject() instanceof ConConnection) {
      ConConnection conConnection = (ConConnection) this.node.getUserObject();
      this.treatGeneric(this, conConnection.getConConnectionEditingTreat());
      if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
        // Nouveau connecteur
        this.treatGenericNew(this, conConnection.getConConnectorEditingTreat());
      }
    }
  }

  private void treatConConnector(WinRepositoryPopupMenu winRepositoryPopupMenu) {

    if (this.node.getUserObject() instanceof ConConnector) {
      ConConnector conConnector = (ConConnector) this.node.getUserObject();
      ConConnectorEditingTreat conConnectorEditingTreat = conConnector.getConConnectorEditingTreat();
      this.treatGenericRead(this, conConnectorEditingTreat);
      if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
        this.treatGenericUpdate(this, conConnectorEditingTreat);
      }
      this.treatGenericDelete(this, conConnectorEditingTreat);
      if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
        this.treatGenericCompletness(this, conConnectorEditingTreat);
      }
    }
  }

  private void exceptionUnhandled(Exception e, MVCCDElement mvccdElement, String propertyAction) {
    ExceptionService.exceptionUnhandled(e, this.mvccdWindow, mvccdElement, "repository.menu.exception", propertyAction);
  }

  private void addItem(ISMenu menu, JMenuItem menuItem) {
    if (menu instanceof SPopupMenu) {
      ((SPopupMenu) menu).add(menuItem);
    }
    if (menu instanceof SMenu) {
      ((SMenu) menu).add(menuItem);
    }
  }
}