package main.window.repository;

import datatypes.MDDatatype;
import diagram.mcd.MCDDiagram;
import m.IMCompletness;
import main.MVCCDElement;
import main.MVCCDElementApplicationPreferences;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.*;
import messages.MessagesBuilder;
import mldr.MLDRColumn;
import mldr.MLDRModel;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import repository.editingTreat.diagram.MCDDiagramEditingTreat;
import repository.editingTreat.mcd.*;
import repository.editingTreat.md.MDDatatypeEditingTreat;
import repository.editingTreat.mdr.MDRColumnEditingTreat;
import repository.editingTreat.mdr.MDRTableEditingTreat;
import repository.editingTreat.mldr.MLDRModelEditingTreat;
import repository.editingTreat.mpdr.MPDRModelEditingTreat;
import repository.editingTreat.preferences.*;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.Profile;
import project.Project;
import repository.editingTreat.*;
import utilities.DefaultMutableTreeNodeService;
import utilities.window.scomponents.ISMenu;
import utilities.window.scomponents.SMenu;
import utilities.window.scomponents.SPopupMenu;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
            packageNew(this, true);
            treatGenericCompliant(this, new MCDModelEditingTreat());
            treatGenericTransform(this, new MCDModelEditingTreat(),
                    "menu.transform.mcd.to.mldr");
        }

        if (node.getUserObject() instanceof MCDPackage) {
            treatGeneric(this, new MCDPackageEditingTreat());
            treatGenericCompliant(this, new MCDPackageEditingTreat());
            packageNew(this, false);
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
            mvccdElement = ((MCDAssEnd) node.getUserObject()).getMcdAssociation();
            treatGeneric(this, new MCDAssociationEditingTreat());
        }

        if (node.getUserObject() instanceof MCDGeneralization) {
            treatGeneric(this, new MCDGeneralizationEditingTreat());
        }

        if (node.getUserObject() instanceof MCDGSEnd) {
            mvccdElement = ((MCDGSEnd) node.getUserObject()).getMcdGeneralization();
            treatGeneric(this, new MCDGeneralizationEditingTreat());
        }

        if (node.getUserObject() instanceof MCDLink) {
            treatGeneric(this, new MCDLinkEditingTreat());
        }

        if (node.getUserObject() instanceof MCDLinkEnd) {
            mvccdElement = ((MCDLinkEnd) node.getUserObject()).getMcdLink();
            treatGeneric(this, new MCDLinkEditingTreat());
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

        if (node.getUserObject() instanceof MPDRModel) {
            treatGeneric(this, new MPDRModelEditingTreat());
        }

        if (node.getUserObject() instanceof MPDRTable) {
            treatGenericRead(this, new MDRTableEditingTreat());
        }
    }



    private void treatInspectObject() {
        JMenuItem inspecter = new JMenuItem("Inpecter l'objet --> Résultat dans la console!");
        this.add(inspecter);
        inspecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(node.getUserObject().getClass().getName());
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

            // Fin Sous-menu MPDR

            treatGenericUpdate( preferencesEdit, new PrefMLDRToMPDREditingTreat(),
                    MessagesBuilder.getMessagesProperty("menu.preferences.mldr.to.mpdr"));

            JMenuItem preferencesExportProfil = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.export.profil.preferences"));
            addItem(menu, preferencesExportProfil);
            preferencesExportProfil.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    PreferencesManager.instance().createProfile();
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
            packageNew(menu, true);
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

    private void packageNew(ISMenu menu, boolean top) {
        if (PreferencesManager.instance().preferences().getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS()) {
            String propertyMessage ;
            if (top) {
                propertyMessage = "menu.new.package";
            } else {
                propertyMessage = "menu.new.subpackage";
            }
            if (node.getUserObject() instanceof MCDPackage) {
                MCDPackage mcdPackage = (MCDPackage) node.getUserObject();
                if (mcdPackage.getLevel() < Preferences.PACKAGE_LEVEL_MAX) {
                    treatGenericNew(menu, new MCDPackageEditingTreat(),
                            MessagesBuilder.getMessagesProperty(propertyMessage));
                }
            }
         }
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

    private void treatGenericCompliant(ISMenu menu, EditingTreat editingTreat) {
        JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.compliant"));
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<String> messages = editingTreat.treatCompliant(mvccdWindow, mvccdElement);
            }
        });
    }

    private void treatGenericTransform(ISMenu menu, EditingTreat editingTreat, String propertyTextMenu) {
        JMenuItem menuItem = new JMenuItem(MessagesBuilder.getMessagesProperty(propertyTextMenu));
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<String> messages = editingTreat.treatTransform(mvccdWindow, mvccdElement);
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
