package main.window.repository;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConDBMode;
import connections.ConnectionsDB;
import console.ConsoleManager;
import console.LogsManager;
import console.ViewLogsManager;
import datatypes.MDDatatype;
import diagram.mcd.MCDDiagram;
import exceptions.service.ExceptionService;
import m.interfaces.IMCompletness;
import m.interfaces.IMUMLExtensionNamingInBox;
import m.interfaces.IMUMLExtensionNamingInLine;
import main.*;
import mcd.*;
import mcd.interfaces.IMCDCompliant;
import mcd.interfaces.IMCDElementWithTargets;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDNIDService;
import mdr.MDRRelFKEnd;
import mdr.MDRRelationFK;
import mdr.interfaces.IMDRElementWithIteration;
import messages.MessagesBuilder;
import mldr.*;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mpdr.*;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.tapis.MPDRStoredCode;
import mpdr.tapis.MPDRTrigger;
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
import repository.editingTreat.mcd.*;
import repository.editingTreat.md.MDDatatypeEditingTreat;
import repository.editingTreat.mdr.*;
import repository.editingTreat.mldr.MLDRModelEditingTreat;
import repository.editingTreat.mpdr.MPDRModelEditingTreat;
import repository.editingTreat.mpdr.MPDRSequenceEditingTreat;
import repository.editingTreat.mpdr.MPDRStoredCodeEditingTreat;
import repository.editingTreat.mpdr.MPDRTriggerEditingTreat;
import repository.editingTreat.naming.NamingEditingTreat;
import repository.editingTreat.preferences.*;
import utilities.DefaultMutableTreeNodeService;
import utilities.window.DialogMessage;
import utilities.window.scomponents.ISMenu;
import utilities.window.scomponents.SMenu;
import utilities.window.scomponents.SPopupMenu;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinRepositoryPopupMenu extends SPopupMenu {
    private DefaultMutableTreeNode node;
    private DefaultTreeModel treeModel;
    private MVCCDWindow mvccdWindow;
    private MVCCDElement mvccdElement;

    public WinRepositoryPopupMenu(DefaultMutableTreeNode node) {
        this.treeModel = treeModel;
        this.node = node;
        mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        mvccdElement = (MVCCDElement) node.getUserObject();
        init();
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
                    treatInspectObject();
                }
                if (node.getUserObject() instanceof MVCCDElementRepositoryRoot){
                    treatRepositoryRoot(this);
                }
            }

            //TODO-1 A terme, mettre une restriction Debug ou autre
            treatNaming(this);

            if (node.getUserObject() instanceof IMLDRElementWithSource) {
                treatSourceMLDRElementWithSource();
            }

            if (node.getUserObject() instanceof IMPDRElementWithSource) {
                treatSourceMPDRElementWithSource();
            }

            if (node.getUserObject() instanceof MVCCDElementApplicationPreferences) {
                treatGenericUpdate(this, new PrefApplEditingTreat());
            }

            if (node.getUserObject() instanceof MDDatatype) {
                treatGenericRead(this, new MDDatatypeEditingTreat());
            }

            if (node.getUserObject() instanceof ConnectionsDB) {
                treatConnections(this);
            }

            if (node.getUserObject() instanceof ConConnection) {
                treatConConnection(this);
            }

            if (node.getUserObject() instanceof ConConnector) {
                treatConConnector(this);
            }

            if (node.getUserObject() instanceof Preferences) {
                treatPreferences(this);
            }

            if (node.getUserObject() instanceof Profile) {
                treatProfile(this);
            }

            if (node.getUserObject() instanceof Project) {
                treatProject(this);
            }

            if (node.getUserObject() instanceof MCDContModels) {
                treatMCDModels(this);
            }

            if (node.getUserObject() instanceof MCDModel) {
                treatGeneric(this, new MCDModelEditingTreat());
                MCDModel mcdModel = (MCDModel) node.getUserObject();
                if (mcdModel.isPackagesAutorizeds()) {
                    packageNew(this, "menu.new.package");
                }
                treatGenericCompliantMCD(this, new MCDModelEditingTreat());
                treatGenericTransformMCD(this, new MCDModelEditingTreat(),
                        "menu.transform.mcd.to.mldr");
            }

            if (node.getUserObject() instanceof MCDPackage) {
                treatGeneric(this, new MCDPackageEditingTreat());
                treatGenericCompliantMCD(this, new MCDPackageEditingTreat());
                MCDPackage mcdPackage = (MCDPackage) node.getUserObject();
                // Pas nécessaire de vérifier les droits de créer unpackage puisque le parent est déjà un package
                packageNew(this, "menu.new.subpackage");

            }


            if (node.getUserObject() instanceof MCDContDiagrams) {
                treatGenericNew(this, new MCDDiagramEditingTreat(),
                        MessagesBuilder.getMessagesProperty("menu.new.diagram"));
            }
            if (node.getUserObject() instanceof MCDDiagram) {
                treatGeneric(this, new MCDDiagramEditingTreat());
            }

            if (node.getUserObject() instanceof MCDContEntities) {
                treatGenericNew(this, new MCDEntityEditingTreat());
            }

            if (node.getUserObject() instanceof MCDEntity) {
                treatGeneric(this, new MCDEntityEditingTreat());
                //Contrôle de complétude
                treatGenericRead(this, new MCDEntCompliantEditingTreat(),
                        MessagesBuilder.getMessagesProperty("menu.compliant"));
            }

            if (node.getUserObject() instanceof MCDContAttributes) {
                treatGenericNew(this, new MCDAttributeEditingTreat());
                treatGenericRead(this, new MCDAttributesEditingTreat());
            }

            if (node.getUserObject() instanceof MCDContConstraints) {
                treatConstraints(this);
            }

            if (node.getUserObject() instanceof MCDAttribute) {
                treatGeneric(this, new MCDAttributeEditingTreat());
                MCDAttribute mcdAttribute = (MCDAttribute) node.getUserObject();
                if (MCDNIDService.attributeCandidateForNID1(mcdAttribute)) {
                    treatCreateNID1FromAttribute();
                }
            }

            if (node.getUserObject() instanceof MCDContRelEnds) {
                treatGenericRead(this, new MCDRelEndsEditingTreat());
            }

            if (node.getUserObject() instanceof MCDContRelations) {
                treatRelations(this);
            }

            if ((node.getUserObject() instanceof MCDUnique) &&
                    (!(node.getUserObject() instanceof MCDNID))) {
                treatGenericNew(this, new MCDUniqueParameterEditingTreat(),
                        MessagesBuilder.getMessagesProperty("menu.new.operation.parameter"));
                treatGeneric(this, new MCDUniqueEditingTreat());
            }

            if (node.getUserObject() instanceof MCDNID) {
                treatGenericNew(this, new MCDNIDParameterEditingTreat(),
                        MessagesBuilder.getMessagesProperty("menu.new.operation.parameter"));
                treatGeneric(this, new MCDNIDEditingTreat());
            }


            if (node.getUserObject() instanceof MCDAssociation) {
                treatGeneric(this, new MCDAssociationEditingTreat());
                //TODO-1 Développer la classe MCDAssociationCompliantEditingTreat
                // à l'image de MCDEntityCompliantEditingTreat
                // treatGenericRead(this, new MCDAssociationCompliantEditingTreat(),
                //            MessagesBuilder.getMessagesProperty("menu.compliant"));

            }

            if (node.getUserObject() instanceof MCDAssEnd) {
                //mvccdElement = ((MCDAssEnd) node.getUserObject()).getMcdAssociation();
                //treatGeneric(this, new MCDAssociationEditingTreat());
                treatGeneric(this, new MCDAssEndEditingTreat());
            }

            if (node.getUserObject() instanceof MCDGeneralization) {
                treatGeneric(this, new MCDGeneralizationEditingTreat());
            }

            if (node.getUserObject() instanceof MCDGSEnd) {
                //mvccdElement = ((MCDGSEnd) node.getUserObject()).getMcdGeneralization();
                //treatGeneric(this, new MCDGeneralizationEditingTreat());
                treatGeneric(this, new MCDGSEndEditingTreat());
            }

            if (node.getUserObject() instanceof MCDLink) {
                treatGeneric(this, new MCDLinkEditingTreat());
            }

            if (node.getUserObject() instanceof MCDLinkEnd) {
                //mvccdElement = ((MCDLinkEnd) node.getUserObject()).getMcdLink();
                //treatGeneric(this, new MCDLinkEditingTreat());
                treatGeneric(this, new MCDLinkEndEditingTreat());

            }


            if (node.getUserObject() instanceof IMCDElementWithTargets) {
                String textMenu = MessagesBuilder.getMessagesProperty("menu.mcd.targets.read");
                treatGenericRead(this, new MCDTargetsEditingTreat(), textMenu);
            }

            if (node.getUserObject() instanceof MLDRModel) {
                treatGeneric(this, new MLDRModelEditingTreat());

                JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty(
                        "menu.transform.mldr.to.mpdr"));
                addItem(this, menuItem);
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            (new MLDRModelEditingTreat()).treatTransform(mvccdWindow, mvccdElement);
                        } catch (Exception e){
                            exceptionUnhandled(e, mvccdElement, "repository.menu.exception.transform");
                        }
                    }
                });
            }

            if (node.getUserObject() instanceof MLDRTable) {
                treatGenericRead(this, new MDRTableEditingTreat());
            }

            if (node.getUserObject() instanceof MLDRColumn) {
                treatGenericRead(this, new MDRColumnEditingTreat());
            }

            if (node.getUserObject() instanceof MLDRPK) {
                treatGenericRead(this, new MDRPKEditingTreat());
            }

            if (node.getUserObject() instanceof MLDRFK) {
                treatGenericRead(this, new MDRFKEditingTreat());
            }

            if (node.getUserObject() instanceof MLDRUnique) {
                treatGenericRead(this, new MDRUniqueEditingTreat());
            }

            if (node.getUserObject() instanceof MLDRParameter) {
                treatGenericRead(this, new MDRParameterEditingTreat());
            }

            if (node.getUserObject() instanceof MLDRRelationFK) {
                treatMDRRelationFKRead(this);
            }

            if (node.getUserObject() instanceof MLDRRelFKEnd) {
                treatMDRRelFKEndRead(this);
            }

            if (node.getUserObject() instanceof MPDRModel) {
               treatGeneric(this, new MPDRModelEditingTreat());

                JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty(
                        "menu.generate.sql.from.mpdr"));
                addItem(this, menuItem);
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            (new MPDRModelEditingTreat()).treatGenerate(mvccdWindow, mvccdElement);
                        } catch (Exception e){
                            exceptionUnhandled(e, mvccdElement, "repository.menu.exception.generate.sql");
                        }
                    }
                });
            }

            if (node.getUserObject() instanceof MPDRTable) {
                treatGenericRead(this, new MDRTableEditingTreat());
            }

            if (node.getUserObject() instanceof MPDRColumn) {
                treatGenericRead(this, new MDRColumnEditingTreat());
            }

            if (node.getUserObject() instanceof MPDRPK) {
                treatGenericRead(this, new MDRPKEditingTreat());
            }

            if (node.getUserObject() instanceof MPDRFK) {
                treatGenericRead(this, new MDRFKEditingTreat());
            }

            if (node.getUserObject() instanceof MPDRUnique) {
                treatGenericRead(this, new MDRUniqueEditingTreat());
            }


            if (node.getUserObject() instanceof MPDRCheck) {
                treatGenericRead(this, new MDRCheckEditingTreat());
            }

            if (node.getUserObject() instanceof MPDRParameter) {
                treatGenericRead(this, new MDRParameterEditingTreat());
            }

            if (node.getUserObject() instanceof MPDRRelationFK) {
                treatMDRRelationFKRead(this);
            }

            if (node.getUserObject() instanceof MPDRRelFKEnd) {
                treatMDRRelFKEndRead(this);
            }

            if (node.getUserObject() instanceof MPDRSequence) {
                treatGenericRead(this, new MPDRSequenceEditingTreat());
            }

            if (node.getUserObject() instanceof MPDRTrigger) {
                treatGenericRead(this, new MPDRTriggerEditingTreat());
            }

            if (node.getUserObject() instanceof MPDRStoredCode) {
                treatGenericRead(this, new MPDRStoredCodeEditingTreat());
            }

        } catch (Exception e){
            //TODO-PAS A terme ce bloc de traitement d'exception devrait pouvoir être supprimé
            // si toutes les actionPerformed() qui modifient la présentation du référentiel
            // traitent les exceptions

            String message = MessagesBuilder.getMessagesProperty("repository.create.menu.exception");
            ViewLogsManager.catchException(e, mvccdWindow, message);
        }
    }




    private void treatInspectObject() {
        JMenuItem inspecter = new JMenuItem("Inspecter l'objet ");
        this.add(inspecter);
        inspecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String message = "Classe : " + mvccdElement.getClass().getName();
                    if (mvccdElement instanceof ProjectElement) {
                        ProjectElement projectElement = (ProjectElement) mvccdElement;
                        message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Id : " + projectElement.getIdProjectElement();
                    }
                    message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Ordre dans la fraterie : " + mvccdElement.getOrder();
                    if (mvccdElement instanceof IMDRElementWithIteration) {
                        IMDRElementWithIteration mdrElement = (IMDRElementWithIteration) mvccdElement;
                        message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Itération : " + mdrElement.getIteration();
                    }
                    if (mvccdElement instanceof MDRRelationFK) {
                        MDRRelationFK mdrRelationFK = (MDRRelationFK) mvccdElement;
                        message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Contrainte FK - nom : " + mdrRelationFK.getMDRFK().getName();
                        message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Contrainte FK - id : " + mdrRelationFK.getMDRFK().getIdProjectElement();
                    }
                    if (mvccdElement instanceof MDRRelFKEnd) {
                        MDRRelFKEnd mdrRelFKEnd = (MDRRelFKEnd) mvccdElement;
                        message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Rôle : " + mdrRelFKEnd.getRoleText();
                    }
                    if (mvccdElement instanceof IMUMLExtensionNamingInLine) {
                        IMUMLExtensionNamingInLine imumlExtensionNamingInLine = (IMUMLExtensionNamingInLine) mvccdElement;
                        message = message + Preferences.SYSTEM_LINE_SEPARATOR  + "Stéréo InLine : " + imumlExtensionNamingInLine.getStereotypesInLine();
                        message = message + Preferences.SYSTEM_LINE_SEPARATOR  + "Contr. InLine : " + imumlExtensionNamingInLine.getConstraintsInLine();
                    }
                    if (mvccdElement instanceof IMUMLExtensionNamingInBox) {
                        IMUMLExtensionNamingInBox imumlExtensionNamingInBox = (IMUMLExtensionNamingInBox) mvccdElement;
                        message = message + Preferences.SYSTEM_LINE_SEPARATOR  + "Stéréo InBox : "  + imumlExtensionNamingInBox.getStereotypesInBox();
                        message = message + Preferences.SYSTEM_LINE_SEPARATOR  + "Contr. InBox : " + imumlExtensionNamingInBox.getConstraintsInBox();
                    }
                    message = message + Preferences.SYSTEM_LINE_SEPARATOR  + "Childs : "  ;
                    int i = 0;
                    for (MVCCDElement mvccdElement : mvccdElement.getChilds()){
                        if (i > 0){
                            message += ", ";
                        }
                        message = message + mvccdElement.getNameTree();
                        i++;
                    }

                    new DialogMessage().showOk(mvccdWindow, message);
                }catch (Exception e){
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.inspector");
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
                    IMLDRElementWithSource imldrElementWithSource = (IMLDRElementWithSource) mvccdElement;
                    MCDElement mcdElementSource = imldrElementWithSource.getMcdElementSource();
                    String message = "Classe : " + mcdElementSource.getClass().getName();
                    message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Nom     : " + mcdElementSource.getNameSourcePath();
                    message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Id     : " + mcdElementSource.getIdProjectElement();

                    new DialogMessage().showOk(mvccdWindow, message,
                            "Source de niveau conceptuel de l'objet : " + mvccdElement.getName());

                } catch (Exception e) {
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.source.mcd");
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
                    IMPDRElementWithSource impdrElementWithSource = (IMPDRElementWithSource) mvccdElement;
                    IMLDRElement mldrElementSource = impdrElementWithSource.getMldrElementSource();
                    String message = "Classe : " + mldrElementSource.getClass().getName();
                    message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Nom     : " + mldrElementSource.getName();
                    message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Id     : " + mldrElementSource.getIdProjectElement();
                    new DialogMessage().showOk(mvccdWindow, message,
                            "Source de niveau logique de l'objet : " + mvccdElement.getName());
                } catch(Exception e){
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.source.mldr");
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
                    MCDNIDService.confirmCreateNID1FromAttribute(mvccdWindow, (MCDAttribute) node.getUserObject());
                } catch (Exception e) {
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.attribute.nid1");
                }
            }
        });

    }




    private void treatProfile(ISMenu menu) {
    }

    private void treatPreferences(ISMenu menu) {

        SMenu preferencesEdit = new SMenu(MessagesBuilder.getMessagesProperty("menu.preferences"));
        addItem(menu, preferencesEdit);


        //DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) node.getParent();

        if (DefaultMutableTreeNodeService.isObjectDescendantOf(node, new Profile(null, null))){
        //if (nodeParent.getUserObject() instanceof Profile) {
            treatGenericRead( preferencesEdit, new PrefGeneralEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.general"));

            treatGenericRead( preferencesEdit, new PrefMCDEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mcd"));

            treatGenericRead( preferencesEdit, new PrefMDREditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mdr"));

            treatGenericRead( preferencesEdit, new PrefMDRFormatEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mdr.format"));

            treatGenericRead( preferencesEdit, new PrefMLDREditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mldr"));

            treatGenericRead( preferencesEdit, new PrefMCDToMLDREditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mcd.to.mldr"));

            // Sous-menu MPDR
            SMenu preferencesEditMPDR = new SMenu(MessagesBuilder.getMessagesProperty("menu.preferences"));
            addItem(preferencesEditMPDR, preferencesEditMPDR);

            treatGenericRead( preferencesEdit, new PrefMPDROracleEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.oracle"));

            treatGenericRead( preferencesEditMPDR, new PrefMPDRMySQLEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.mysql"));

            treatGenericRead( preferencesEditMPDR, new PrefMPDRPostgreSQLEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.postgresql"));

            // Fin Sous-menu MPDR


            treatGenericRead( preferencesEdit, new PrefMLDRToMPDREditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mldr.to.mpdr"));
        }

        if (DefaultMutableTreeNodeService.isObjectDescendantOf(node, new Project(null))){
        //if (nodeParent.getUserObject() instanceof Project) {
            treatGenericUpdate( preferencesEdit, new PrefGeneralEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.general"));

            treatGenericUpdate( preferencesEdit, new PrefMCDEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mcd"));

            treatGenericUpdate( preferencesEdit, new PrefMDREditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mdr"));

            treatGenericUpdate( preferencesEdit, new PrefMDRFormatEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mdr.format"));

            treatGenericUpdate( preferencesEdit, new PrefMLDREditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mldr"));

            treatGenericUpdate( preferencesEdit, new PrefMCDToMLDREditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mcd.to.mldr"));

            // Sous-menu MPDR
            SMenu preferencesEditMPDR = new SMenu(MessagesBuilder.getMessagesProperty("menu.preferences.mpdr"));
            addItem(preferencesEdit, preferencesEditMPDR);

            treatGenericUpdate( preferencesEditMPDR, new PrefMPDROracleEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.oracle"));

            treatGenericUpdate( preferencesEditMPDR, new PrefMPDRMySQLEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.mysql"));

            treatGenericUpdate( preferencesEditMPDR, new PrefMPDRPostgreSQLEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mpdr.postgresql"));

            // Fin Sous-menu MPDR

            treatGenericUpdate( preferencesEdit, new PrefMLDRToMPDREditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mldr.to.mpdr"));

            JMenuItem preferencesExportProfil = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.export.profil.preferences"));
            addItem(menu, preferencesExportProfil);
            preferencesExportProfil.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                       if (PreferencesManager.instance().preferences().isPERSISTENCE_SERIALISATION_INSTEADOF_XML()) {
                            PreferencesManager.instance().createProfile(); //Persistance avec sérialisation
                        } else {
                            new ProfileSaverXml().createFileProfileXML(); //Ajout de Giorgio
                        }
                    } catch (Exception e){
                        exceptionUnhandled(e, mvccdElement, "repository.menu.exception.export.pref");
                    }
                }
            });

        }
    }


    private void treatProject(ISMenu menu) {
        JMenuItem editProject = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.update"));
        addItem(menu, editProject);
        editProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    // Pas d'extension de la classe EditingTreat
                    ProjectEditingTreat.treatUpdate(mvccdWindow);
                } catch (Exception e){
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.update");
                }
            }
        });
    }

    private void treatConnections(ISMenu menu) {
        /*if (node.getUserObject() instanceof ConnectionsOracle) {
            treatGenericNew(this, new ConConnectionOracleEditingTreat());
        }

         */
        if (node.getUserObject() instanceof ConnectionsDB) {
            ConnectionsDB connectionsDB = (ConnectionsDB) node.getUserObject();
            treatGenericNew(this, connectionsDB.getConConnectionEditingTreat());
        }
    }

    private void treatMCDModels(ISMenu menu) {
        if (PreferencesManager.instance().preferences().getREPOSITORY_MCD_MODELS_MANY()) {
            treatGenericNew( menu, new MCDModelEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.new.model"));
       } else {
            if (PreferencesManager.instance().preferences().getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS()) {
                packageNew(menu, "menu.new.package");
            }
            treatGenericCompliantMCD(menu, new MCDContModelsEditingTreat());
            treatGenericTransformMCD(menu, new MCDContModelsEditingTreat(),
                    "menu.transform.mcd.to.mldr");
        }
    }


    private void treatRelations(ISMenu menu) {
        treatGenericNew(this, new MCDAssociationEditingTreat(),
                MessagesBuilder.getMessagesProperty("menu.new.association"));
        treatGenericNew(this, new MCDGeneralizationEditingTreat(),
                MessagesBuilder.getMessagesProperty("menu.new.generalization"));
        treatGenericNew(this, new MCDLinkEditingTreat(),
                MessagesBuilder.getMessagesProperty("menu.new.link"));
        treatGenericDeleteChilds(this, new MCDRelationsEditingTreat());
    }

    private void treatConstraints(ISMenu menu) {
        treatGenericNew(this, new MCDNIDEditingTreat(),
                MessagesBuilder.getMessagesProperty("menu.new.constraint.nid"));
        treatGenericNew(this, new MCDUniqueEditingTreat(),
                MessagesBuilder.getMessagesProperty("menu.new.constraint.unique"));
        treatGenericRead( this, new MCDConstraintsEditingTreat());

    }

    private void packageNew(ISMenu menu, String propertyMessage) {
        treatGenericNew(menu, new MCDPackageEditingTreat(),
                        MessagesBuilder.getMessagesProperty(propertyMessage));
    }


    private void treatGeneric(ISMenu menu, EditingTreat editingTreat) {
        treatGenericRead(menu, editingTreat);
        treatGenericUpdate(menu, editingTreat);
        treatGenericDelete(menu, editingTreat);

        if (mvccdElement instanceof IMCompletness) {
            treatGenericCompletness(menu, editingTreat);
        }
    }

    private void treatGenericNew(ISMenu menu, EditingTreat editingTreat) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.new");
        treatGenericNew(menu, editingTreat, textMenu);
    }

    private void treatGenericNew(ISMenu menu, EditingTreat editingTreat, String textMenu) {
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    editingTreat.treatNew(mvccdWindow, mvccdElement);
                } catch (Exception e) {
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.new");
                }
            }
        });
    }


    private void treatGenericRead(ISMenu menu, EditingTreat editingTreat) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.read");
        treatGenericRead(menu, editingTreat, textMenu);
    }

    private void treatGenericRead(ISMenu menu, EditingTreat editingTreat, String textMenu) {
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    editingTreat.treatRead(mvccdWindow, mvccdElement);
                } catch (Exception e){
                    String propertyMessage ;
                    if ( editingTreat instanceof MCDEntCompliantEditingTreat){
                        propertyMessage = "repository.menu.exception.compliant";
                    }else {
                        propertyMessage = "repository.menu.exception.read";
                    }
                    exceptionUnhandled(e, mvccdElement, propertyMessage);
                }
            }
        });
    }

    private void treatGenericUpdate(ISMenu menu, EditingTreat editingTreat) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.update");
        treatGenericUpdate(menu, editingTreat, textMenu);
    }

    private void treatGenericUpdate(ISMenu menu, EditingTreat editingTreat, String textMenu) {
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    editingTreat.treatUpdate(mvccdWindow, mvccdElement);
                } catch (Exception e){
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.update");
                }
            }
        });
    }

    private void treatGenericDelete(ISMenu menu, EditingTreat editingTreat) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.delete");
        treatGenericDelete(menu, editingTreat, textMenu);
    }

    private void treatGenericDelete(ISMenu menu, EditingTreat editingTreat, String textMenu) {
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    editingTreat.treatDelete(mvccdWindow, mvccdElement);
                } catch (Exception e){
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.delete");
                }
            }
        });
    }

    private void treatGenericDeleteChilds(ISMenu menu, EditingTreat editingTreat) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.delete.childs");
        treatGenericDeleteChilds(menu, editingTreat, textMenu);
    }

    private void treatGenericDeleteChilds(ISMenu menu, EditingTreat editingTreat, String textMenu) {
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    editingTreat.treatDeleteChilds(mvccdWindow, mvccdElement);
                } catch (Exception e){
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.delete.childs");
                }
            }
        });
    }

    private void treatGenericCompletness(ISMenu menu, EditingTreat editingTreat) {
        JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.completness"));
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    boolean ok =  editingTreat.treatCompletness(mvccdWindow, mvccdElement, true);
                } catch (Exception e) {
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.completness");
                }
            }
        });
    }

    private void treatGenericCompliantMCD(ISMenu menu,
                                          MCDCompliantEditingTreat mcdCompliantEditingTreat) {

        JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.compliant"));
        addItem(menu, menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    mcdCompliantEditingTreat.treatCompliant(mvccdWindow, (IMCDCompliant) mvccdElement);
                } catch (Exception e) {
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.compliant");
                }
            }
        });
    }

    private void treatGenericTransformMCD(ISMenu menu, MCDTransformEditingTreat mcdTransformEditingTreat, String propertyTextMenu) {
        JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty(propertyTextMenu));
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    mcdTransformEditingTreat.treatTransform(mvccdWindow, (IMCDModel)mvccdElement);
                } catch (Exception e) {
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.transform");
                }
            }
        });
    }



    private void treatNaming(ISMenu menu) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.mvccdelement.naming");
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Preferences preferences = PreferencesManager.instance().preferences();

                    // Mémorisation de la préférence qui peut être modifiée
                    String pathNaming = preferences.getMCD_TREE_NAMING_ASSOCIATION();

                    // Appel du formulaire
                    (new NamingEditingTreat()).treatNaming(mvccdWindow, mvccdElement);

                    //Remise en l'état initial de la préférence
                    if ( ! preferences.getMCD_TREE_NAMING_ASSOCIATION().equals(pathNaming)){
                        preferences.setMCD_TREE_NAMING_ASSOCIATION(pathNaming);
                    }

                } catch (Exception e) {
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.naming");
                }
            }
        });
    }

    private void treatRepositoryRoot(WinRepositoryPopupMenu menu) {
        String textMenu = "Visualisation des CLASSPATH";
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String classpath = System.getProperty("java.class.path");
                    String[] classpathEntries = classpath.split(System.getProperty("path.separator"));
                    DialogMessage.showOk(mvccdWindow, classpath, "Liste des CLASSPATH");
                } catch (Exception e) {
                    exceptionUnhandled(e, mvccdElement, "repository.menu.exception.classpath");
                }
            }
        });

    }

    private void treatMDRRelationFKRead(ISMenu menu) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.read");
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    MDRRelationFK mdrRelationFK = (MDRRelationFK) mvccdElement;
                    new MDRFKEditingTreat().treatRead(mvccdWindow,mdrRelationFK.getMDRFK());
                } catch (Exception e){
                    String propertyMessage ;
                    propertyMessage = "repository.menu.exception.read";
                    exceptionUnhandled(e, mvccdElement, propertyMessage);
                }
            }
        });
    }


    private void treatMDRRelFKEndRead(ISMenu menu) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.read");
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    MDRRelFKEnd mdrRelFKEnd = (MDRRelFKEnd) mvccdElement;
                    new MDRFKEditingTreat().treatRead(mvccdWindow,mdrRelFKEnd.getMDRRelationFK().getMDRFK());
                } catch (Exception e){
                    String propertyMessage ;
                    propertyMessage = "repository.menu.exception.read";
                    exceptionUnhandled(e, mvccdElement, propertyMessage);
                }
            }
        });
    }

    private void treatConConnection(WinRepositoryPopupMenu winRepositoryPopupMenu) {

        if (node.getUserObject() instanceof ConConnection) {
            ConConnection conConnection = (ConConnection) node.getUserObject();
            treatGeneric(this, conConnection.getConConnectionEditingTreat());
            if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
                // Nouveau connecteur
               treatGenericNew(this, conConnection.getConConnectorEditingTreat());
            }
        }
     }

    private void treatConConnector(WinRepositoryPopupMenu winRepositoryPopupMenu) {

        if (node.getUserObject() instanceof ConConnector) {
            ConConnector conConnector = (ConConnector) node.getUserObject();
            ConConnectorEditingTreat conConnectorEditingTreat = conConnector.getConConnectorEditingTreat();
            treatGenericRead(this, conConnectorEditingTreat);
            if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE()== ConDBMode.CONNECTOR) {
                treatGenericUpdate(this, conConnectorEditingTreat);
            }
            treatGenericDelete(this, conConnectorEditingTreat);
            if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE()== ConDBMode.CONNECTOR) {
                treatGenericCompletness(this, conConnectorEditingTreat);
            }
        }
    }


    private void exceptionUnhandled(Exception e,
                                    MVCCDElement mvccdElement,
                                    String propertyAction) {
        ExceptionService.exceptionUnhandled(e, mvccdWindow, mvccdElement,
                "repository.menu.exception" ,propertyAction);
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
