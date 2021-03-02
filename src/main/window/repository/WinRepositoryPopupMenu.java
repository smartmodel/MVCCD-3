package main.window.repository;

import datatypes.MDDatatype;
import diagram.mcd.MCDDiagram;
import m.interfaces.IMCompletness;
import main.MVCCDElement;
import main.MVCCDElementApplicationPreferences;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.*;
import mcd.interfaces.IMCDElementWithTargets;
import mcd.services.MCDElementService;
import messages.MessagesBuilder;
import mldr.*;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElementWithSource;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.Profile;
import profile.ProfileSaverXml;
import project.Project;
import project.ProjectElement;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.EditingTreatTransform;
import repository.editingTreat.ProjectEditingTreat;
import repository.editingTreat.diagram.MCDDiagramEditingTreat;
import repository.editingTreat.mcd.*;
import repository.editingTreat.md.MDDatatypeEditingTreat;
import repository.editingTreat.mdr.MDRColumnEditingTreat;
import repository.editingTreat.mdr.MDRFKEditingTreat;
import repository.editingTreat.mdr.MDRPKEditingTreat;
import repository.editingTreat.mdr.MDRTableEditingTreat;
import repository.editingTreat.mldr.MLDRModelEditingTreat;
import repository.editingTreat.mpdr.MPDRModelEditingTreat;
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

        if (PreferencesManager.instance().getApplicationPref().isDEBUG()) {
            if (PreferencesManager.instance().getApplicationPref().getDEBUG_INSPECT_OBJECT_IN_TREE()) {
                treatInspectObject();
            }
        }

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
            treatGenericCompliant(this, new MCDModelEditingTreat());
            treatGenericTransform(this, new MCDModelEditingTreat(),
                    "menu.transform.mcd.to.mldr");
        }

        if (node.getUserObject() instanceof MCDPackage) {
            treatGeneric(this, new MCDPackageEditingTreat());
            treatGenericCompliant(this, new MCDPackageEditingTreat());
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
            treatGenericNew( this, new MCDEntityEditingTreat());
        }

        if (node.getUserObject() instanceof MCDEntity) {
            treatGeneric(this, new MCDEntityEditingTreat());
            //treatGenericCompliant(this, new MCDEntityEditingTreat());
            treatGenericRead(this, new MCDEntCompliantEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.compliant"));
        }

        if (node.getUserObject() instanceof MCDContAttributes) {
            treatGenericNew( this, new MCDAttributeEditingTreat());
            treatGenericRead( this, new MCDAttributesEditingTreat());
        }

        if (node.getUserObject() instanceof MCDContConstraints) {
            treatConstraints( this);
        }

        if (node.getUserObject() instanceof MCDAttribute) {
            treatGeneric(this, new MCDAttributeEditingTreat());
        }

        if (node.getUserObject() instanceof MCDContRelEnds) {
            treatGenericRead( this, new MCDRelEndsEditingTreat());
        }

        if (node.getUserObject() instanceof MCDContRelations) {
            treatRelations(this);
        }

        if ( (node.getUserObject() instanceof MCDUnique) &&
                (!(node.getUserObject() instanceof MCDNID))){
            treatGenericNew( this, new MCDUniqueParameterEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.new.operation.parameter"));
            treatGeneric(this, new MCDUniqueEditingTreat());
        }

        if (node.getUserObject() instanceof MCDNID) {
            treatGenericNew( this, new MCDNIDParameterEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.new.operation.parameter"));
            treatGeneric(this, new MCDNIDEditingTreat());
        }

        if (node.getUserObject() instanceof MCDAssociation) {
            treatGeneric(this, new MCDAssociationEditingTreat());
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
            treatGenericRead( this, new MCDTargetsEditingTreat(), textMenu);
        }

        if (node.getUserObject() instanceof MLDRModel) {
            treatGeneric(this, new MLDRModelEditingTreat());
            treatGenericTransform(this, new MLDRModelEditingTreat(),
                    "menu.transform.mldr.to.mpdr");
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

        if (node.getUserObject() instanceof MPDRModel) {
            treatGeneric(this, new MPDRModelEditingTreat());
        }

        if (node.getUserObject() instanceof MPDRTable) {
            treatGenericRead(this, new MDRTableEditingTreat());
        }

        if (node.getUserObject() instanceof MPDRColumn) {
            treatGenericRead(this, new MDRColumnEditingTreat());
        }

    }



    private void treatInspectObject() {
        JMenuItem inspecter = new JMenuItem("Inspecter l'objet ");
        this.add(inspecter);
        inspecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String message = "Classe : " + mvccdElement.getClass().getName();
                if (mvccdElement instanceof ProjectElement){
                    ProjectElement projectElement = (ProjectElement) mvccdElement;
                    message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Id     : " + projectElement.getIdProjectElement();
                }
                new DialogMessage().showOk(mvccdWindow, message);
            }
        });

    }


    private void treatSourceMLDRElementWithSource() {
        JMenuItem source = new JMenuItem("Source conceptuelle");
        this.add(source);
        source.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                IMLDRElementWithSource imldrElementWithSource = (IMLDRElementWithSource) mvccdElement ;
                MCDElement mcdElementSource = imldrElementWithSource.getMcdElementSource();
                String message = "Classe : " +  mcdElementSource.getClass().getName();
                message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Nom     : " + mcdElementSource.getNamePath(MCDElementService.PATHNAME);
                ProjectElement projectElement = (ProjectElement) mvccdElement;
                message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Id     : " + projectElement.getIdProjectElement();

                new DialogMessage().showOk(mvccdWindow, message,
                        "Source de niveau conceptuel de l'objet : " + mvccdElement.getName());
            }
        });

    }


    private void treatSourceMPDRElementWithSource() {
        JMenuItem source = new JMenuItem("Source logique");
        this.add(source);
        source.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                IMPDRElementWithSource impdrElementWithSource = (IMPDRElementWithSource) mvccdElement ;
                IMLDRElement mldrElementSource = impdrElementWithSource.getMldrElementSource();
                String message = "Classe : " +  mldrElementSource.getClass().getName();
                message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Nom     : " + mldrElementSource.getName();
                ProjectElement projectElement = (ProjectElement) mvccdElement;
                message = message + Preferences.SYSTEM_LINE_SEPARATOR + "Id     : " + projectElement.getIdProjectElement();

                new DialogMessage().showOk(mvccdWindow, message,
                        "Source de niveau logique de l'objet : " + mvccdElement.getName());
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
                    if(Preferences.PERSISTENCE_SERIALISATION_INSTEADOF_XML){
                        PreferencesManager.instance().createProfile(); //Persistance avec sérialisation
                    }else{
                        new ProfileSaverXml().createFileProfileXML(); //Ajout de Giorgio
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
                // Pas d'extension de la classe EditingTreat
                ProjectEditingTreat.treatUpdate(mvccdWindow);
            }
        });
    }

    private void treatMCDModels(ISMenu menu) {
        if (PreferencesManager.instance().preferences().getREPOSITORY_MCD_MODELS_MANY()) {
            treatGenericNew( menu, new MCDModelEditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.new.model"));
       } else {
            if (PreferencesManager.instance().preferences().getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS()) {
                packageNew(menu, "menu.new.package");
            }
            treatGenericCompliant(menu, new MCDContModelsEditingTreat());
            treatGenericTransform(menu, new MCDContModelsEditingTreat(),
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
                editingTreat.treatNew(mvccdWindow, mvccdElement);
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

                editingTreat.treatRead(mvccdWindow, mvccdElement);
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
                editingTreat.treatUpdate(mvccdWindow, mvccdElement);
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
                editingTreat.treatDelete(mvccdWindow, mvccdElement);
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
                editingTreat.treatDeleteChilds(mvccdWindow, mvccdElement);
            }
        });
    }

    private void treatGenericCompletness(ISMenu menu, EditingTreat editingTreat) {
        JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.completness"));
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editingTreat.treatCompletness(mvccdWindow, mvccdElement, true);
            }
        });
    }

    private void treatGenericCompliant(ISMenu menu, EditingTreatTransform editingTreatTransform) {
        JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.compliant"));
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editingTreatTransform.treatCompliant(mvccdWindow, mvccdElement);
            }
        });
    }

    private void treatGenericTransform(ISMenu menu, EditingTreatTransform editingTreatTransform, String propertyTextMenu) {
        JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty(propertyTextMenu));
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editingTreatTransform.treatTransform(mvccdWindow, mvccdElement);
            }
        });
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
