package main.window.repository;

import datatypes.MCDDatatype;
import datatypes.MDDatatypesManager;
import main.MVCCDElementApplicationPreferences;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.*;
import messages.MessagesBuilder;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.Profile;
import project.Project;
import repository.editingTreat.*;
import utilities.Debug;
import window.editor.preferences.MCD.PrefMCDEditor;
import window.editor.preferences.general.PrefGeneralEditor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinRepositoryPopupMenu extends JPopupMenu{
    private DefaultMutableTreeNode node ;
    private DefaultTreeModel treeModel ;

    public WinRepositoryPopupMenu(DefaultMutableTreeNode node) {
        this.treeModel = treeModel;
        this.node = node;
        init();
    }

    private void init() {
        MVCCDWindow mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        if(node.getUserObject() instanceof MVCCDElementApplicationPreferences){
            treatApplicationPref(mvccdWindow);
        }

        if(node.getUserObject() instanceof MCDDatatype){
            treatMCDDatatype(mvccdWindow);
        }

        if(node.getUserObject() instanceof Preferences){
            treatPreferences(mvccdWindow);
        }

        if(node.getUserObject() instanceof Project){
            treatProject(mvccdWindow);
        }

        if (node.getUserObject() instanceof MCDContModels) {
            treatModels(mvccdWindow);
        }

        if (node.getUserObject() instanceof MCDModel) {
            treatModel(mvccdWindow);
        }

        if (node.getUserObject() instanceof MCDPackage) {
            treatPackage(mvccdWindow);
        }

        if (node.getUserObject() instanceof MCDDiagrams) {
            treatDiagrams(mvccdWindow);
        }

        if (node.getUserObject() instanceof MCDContEntities) {
            treatEntities (mvccdWindow);
        }

        if (node.getUserObject() instanceof MCDEntity) {
            treatEntity(mvccdWindow);

        }

        if (node.getUserObject() instanceof MCDContAttributes) {
            treatAttributes(mvccdWindow);

        }

        if (node.getUserObject() instanceof MCDAttribute) {
            treatAttribute(mvccdWindow);

        }

        if (node.getUserObject() instanceof MCDContEndRels) {


        }

        if (node.getUserObject() instanceof MCDContRelations) {
            treatRelations(mvccdWindow);

        }

        if (node.getUserObject() instanceof MCDAssociation) {
            treatAssociation(mvccdWindow);

        }

        //TODO-0 Mise à jour de l'arbre
    }




    private void treatApplicationPref(MVCCDWindow mvccdWindow) {
        Preferences preferences = PreferencesManager.instance().getApplicationPref();
        JMenuItem preferencesApplication = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.application.preferences"));
        this.add(preferencesApplication);
        MVCCDElementApplicationPreferences applPref = (MVCCDElementApplicationPreferences) node.getUserObject();
        preferencesApplication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PrefApplEditingTreat.treatUpdate(mvccdWindow, applPref);
            }
        });
    }


    private void treatMCDDatatype(MVCCDWindow mvccdWindow) {
        if(node.getUserObject() != MDDatatypesManager.instance().getDefaultMCDDatatypeRoot()) {
            JMenuItem mcdDatatypeEdit = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.mcddatatype"));
            this.add(mcdDatatypeEdit);
            MCDDatatype mcdDatatype = (MCDDatatype) node.getUserObject();
            mcdDatatypeEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Debug.println("Edition d'un type de données");
                    MCDDatatypeEditingTreat.treatRead(mvccdWindow, mcdDatatype);
                 }
            });
        }

    }



    private void treatPreferences(MVCCDWindow mvccdWindow) {

        DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) node.getParent();

        String menuText = "";
        boolean editProfile = false;
        boolean editProject = false;
        if (nodeParent.getUserObject() instanceof Profile){
            menuText = MessagesBuilder.getMessagesProperty("menu.edit.preferences.profile");
            editProfile = true;
        } else if (nodeParent.getUserObject() instanceof Project){
            menuText = MessagesBuilder.getMessagesProperty("menu.edit.preferences.project");
            editProject = true;
        }
        boolean finalEditProfile = editProfile;


        Preferences preferences = (Preferences) node.getUserObject();
        JMenu preferencesEdit = new JMenu(menuText);
        this.add(preferencesEdit);

        JMenuItem preferencesEditGeneral = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.general.preferences"));
        preferencesEdit.add(preferencesEditGeneral);
        preferencesEditGeneral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PrefGeneralEditingTreat.treatUpdate(mvccdWindow, preferences);

                PrefGeneralEditor fen = new PrefGeneralEditor(mvccdWindow , null , preferences, DialogEditor.UPDATE);
                if (finalEditProfile){
                    fen.setTitle(MessagesBuilder.getMessagesProperty("preferences.profile.general.read"));
                    fen.setReadOnly(true);
                }
                fen.setVisible(true);
            }
        });

        JMenuItem preferencesEditMCD = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.mcd.preferences"));
        preferencesEdit.add(preferencesEditMCD);
        preferencesEditMCD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PrefMCDEditor fen = new PrefMCDEditor(mvccdWindow, null, preferences, DialogEditor.UPDATE);
                if (finalEditProfile) {
                    fen.setTitle(MessagesBuilder.getMessagesProperty("preferences.profile.mcd.read"));
                    fen.setReadOnly(true);
                }
                fen.setVisible(true);
            }
        });

        if (editProject) {
            JMenuItem preferencesExportProfil = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.export.profil.preferences"));
            this.add(preferencesExportProfil);
            preferencesExportProfil.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    PreferencesManager.instance().createProfile();
                }
            });
        }

    }


    private void treatProject(MVCCDWindow mvccdWindow) {
        JMenuItem editProject = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.project"));
        this.add(editProject);
        editProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ProjectEditingTreat.treatUpdate(mvccdWindow);
            }
        });
    }

    private void treatModels(MVCCDWindow mvccdWindow) {
        MCDContModels mcdContModels = (MCDContModels) node.getUserObject();
        if (PreferencesManager.instance().preferences().getREPOSITORY_MCD_MODELS_MANY()) {
            final JMenuItem mcdModelCreate = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.model"));
            this.add(mcdModelCreate);
            mcdModelCreate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    MCDModelEditingTreat.treatNew(mvccdWindow, mcdContModels, node);
                }
            });
        } else {
            packageNew(mvccdWindow, mcdContModels,true);
        }
    }

    private void treatModel(MVCCDWindow mvccdWindow) {
        final JMenuItem mcdModelEdit= new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.model"));
        this.add(mcdModelEdit);
        MCDModel mcdModel = (MCDModel) node.getUserObject();
        mcdModelEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MCDModelEditingTreat.treatUpdate(mvccdWindow, mcdModel);
            }
        });

        packageNew(mvccdWindow, mcdModel, true);
    }

    private void treatPackage(MVCCDWindow mvccdWindow) {
        MCDPackage mcdPackage = (MCDPackage) node.getUserObject();

        final JMenuItem mcdPackageEdit= new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.package"));
        this.add(mcdPackageEdit);
        mcdPackageEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new MCDPackageEditingTreat().treatUpdate(mvccdWindow, mcdPackage);
            }
        });

        packageNew(mvccdWindow, mcdPackage, false);


    }


    private void treatDiagrams(MVCCDWindow mvccdWindow) {
        JMenuItem mcdDiagramsNewDiagram = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.diagram"));
        this.add(mcdDiagramsNewDiagram);
         mcdDiagramsNewDiagram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Debug.println("Création d'un nouveau diagramme");
            }
        });
    }


    private void treatEntities(MVCCDWindow mvccdWindow) {
        JMenuItem mcdEntitiesNewEntity = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.entity"));
        this.add(mcdEntitiesNewEntity);
        MCDContEntities mcdContEntities = (MCDContEntities) node.getUserObject();
        mcdEntitiesNewEntity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Debug.println("Création d'une nouvelle entité");
                MCDEntityEditingTreat.treatNew(mvccdWindow, mcdContEntities, node);
            }
        });
    }

    private void treatEntity(MVCCDWindow mvccdWindow) {
        JMenuItem mcdEntityEdit = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.entity"));
        this.add(mcdEntityEdit);
        MCDEntity mcdEntity = (MCDEntity) node.getUserObject();
        mcdEntityEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Debug.println("Modification de l'entité");
                MCDEntityEditingTreat.treatUpdate(mvccdWindow, mcdEntity);
             }
        });

        JMenuItem mcdEntityCheck = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.check.entity"));
        this.add(mcdEntityCheck);
        mcdEntityCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Debug.println("Contrôle de conformité de l'entité");
                MCDEntityEditingTreat.treatCompliant(mvccdWindow, mcdEntity);
            }
        });

    }

    private void treatAttributes(MVCCDWindow mvccdWindow) {
        MCDContAttributes mcdContAttributes = (MCDContAttributes) node.getUserObject();
        JMenuItem mcdAttributesEdit = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.attributes.entity"));
        this.add(mcdAttributesEdit);
        mcdAttributesEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MCDAttributesEditingTreat.treatEdit(mvccdWindow, mcdContAttributes);
            }
        });

        JMenuItem mcdAttributesNewAttribute = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.attribute"));
        this.add(mcdAttributesNewAttribute);
        mcdAttributesNewAttribute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Debug.println("Création d'un nouvel attribut");
                MCDAttributeEditingTreat.treatNew(mvccdWindow, mcdContAttributes);
            }
        });

    }

    private void treatAttribute(MVCCDWindow mvccdWindow) {

        JMenuItem mcdAttributeEdit = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.attribute"));
        this.add(mcdAttributeEdit);
        MCDAttribute mcdAttribute = (MCDAttribute) node.getUserObject();
        mcdAttributeEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MCDAttributeEditingTreat.treatUpdate(mvccdWindow , mcdAttribute);
            }
        });

        JMenuItem mcdAttributeDelete = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.delete.attribute"));
        this.add(mcdAttributeDelete);
        mcdAttributeDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Debug.println("Suppression d'un attribut");
                DefaultTreeModel treeModel =  MVCCDManager.instance().getWinRepositoryContent().getTree().getTreeModel();
                treeModel.removeNodeFromParent(node);
            }
        });

    }

    private void treatRelations(MVCCDWindow mvccdWindow) {
        MCDContRelations mcdContRelations = (MCDContRelations) node.getUserObject();

        JMenuItem mcdRelationsNewAssociation = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.association"));
        this.add(mcdRelationsNewAssociation);
        mcdRelationsNewAssociation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                 MCDAssociationEditingTreat.treatNew(mvccdWindow, mcdContRelations);
            }
        });

        JMenuItem mcdRelationsNewGenSpec = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.gen.spec"));
        this.add(mcdRelationsNewGenSpec);
        mcdRelationsNewGenSpec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });

        JMenuItem mcdRelationsNewLink = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.link"));
        this.add(mcdRelationsNewLink);
        mcdRelationsNewLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });

        JMenuItem mcdRelationsNewAnchor = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.anchor"));
        this.add(mcdRelationsNewAnchor);
        mcdRelationsNewAnchor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });
    }

    private void treatAssociation(MVCCDWindow mvccdWindow) {
        JMenuItem mcdAssociationEdit = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.association"));
        this.add(mcdAssociationEdit);
        MCDAssociation mcdAssociation = (MCDAssociation) node.getUserObject();
        mcdAssociationEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MCDAssociationEditingTreat.treatUpdate(mvccdWindow , mcdAssociation);
            }
        });

    }


    private void packageNew(Window owner, MCDElement parent, boolean top) {
        if (PreferencesManager.instance().preferences().getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS()) {
            String propertyMessage = "";
            if (top){
                propertyMessage= "menu.new.package";
            } else {
                propertyMessage = "menu.new.subpackage";
            }
            final JMenuItem mcdPackageCreate = new JMenuItem(MessagesBuilder.getMessagesProperty(propertyMessage));
            this.add(mcdPackageCreate);
            mcdPackageCreate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new MCDPackageEditingTreat().treatNew(owner, parent);
                }
            });
        }
    }

}
