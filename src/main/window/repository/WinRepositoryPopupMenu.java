package main.window.repository;

import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.MCDDiagrams;
import mcd.MCDEntities;
import mcd.MCDEntity;
import mcd.MCDModels;
import mcd.services.MCDEntityService;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.window.DialogMessage;
import window.editor.entity.EntityEditor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinRepositoryPopupMenu extends JPopupMenu{
    private DefaultMutableTreeNode node ;

    public WinRepositoryPopupMenu(DefaultMutableTreeNode node) {
        this.node = node;
        init();
    }

    private void init() {
        MVCCDWindow mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        if (node.getUserObject() instanceof MCDModels) {
            if (Preferences.REPOSITORY_MCD_MODELS_MANY) {
                final JMenuItem mcdModelsCreate = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.model"));
                this.add(mcdModelsCreate);
                mcdModelsCreate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        System.out.println("Création d'un nouveau modèle");
                    }
                });
            }
        }

        if (node.getUserObject() instanceof MCDDiagrams) {
            JMenuItem mcdDiagramsNewDiagram = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.diagram"));
            this.add(mcdDiagramsNewDiagram);
            mcdDiagramsNewDiagram.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("Création d'un nouveau diagramme");
                }
            });
        }

        if (node.getUserObject() instanceof MCDEntities) {
            JMenuItem mcdEntitiesNewEntity = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.entity"));
            this.add(mcdEntitiesNewEntity);
            mcdEntitiesNewEntity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("Création d'une nouvelle entité");
                    EntityEditor fen = showEditorEntity(mvccdWindow , node, EntityEditor.NEW);
                }
            });
        }

        if (node.getUserObject() instanceof MCDEntity) {
            MCDEntity mcdEntity = (MCDEntity) node.getUserObject();
            JMenuItem mcdEntityEdit = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.entity"));
            this.add(mcdEntityEdit);
            mcdEntityEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("Modification de l'entité");
                    EntityEditor fen = showEditorEntity(mvccdWindow , node, EntityEditor.UPDATE);
                }
            });

            JMenuItem mcdEntityCheck = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.check.entity"));
            this.add(mcdEntityCheck);
            mcdEntityCheck.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("Contrôle de conformité de l'entité");
                    if (MCDEntityService.check(mcdEntity).size() == 0){
                        String message = MessagesBuilder.getMessagesProperty ("dialog.check.entity.ok", new String[] {mcdEntity.getName()});
                        DialogMessage.showOk(mvccdWindow, message);
                    } else {
                        String message = MessagesBuilder.getMessagesProperty ("dialog.check.entity.error", new String[] {mcdEntity.getName()});
                        if (DialogMessage.showConfirmYesNo(mvccdWindow, message) == JOptionPane.YES_OPTION){
                            System.out.println("Correction de l'entité");
                            EntityEditor fen = showEditorEntity(mvccdWindow , node, EntityEditor.UPDATE);
                        }
                    }
                }
            });

        }
    }

    private EntityEditor showEditorEntity(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode) {
        EntityEditor fen = new EntityEditor(mvccdWindow , node, mode);
        fen.setVisible(true);
        return fen;
    }
}
