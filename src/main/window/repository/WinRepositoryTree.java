package main.window.repository;

import diagram.Diagram;
import diagram.mpdr.MPDRDiagram;
import main.MVCCDManager;
import project.Project;
import project.ProjectElement;
import project.ProjectService;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La classe gère l'arbre de représentation du référentiel.
 */
public class WinRepositoryTree extends JTree {

    private DefaultTreeModel treeModel;


    public WinRepositoryTree(DefaultTreeModel treeModel) {
        this.setModel(treeModel);
    }

    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = this.getSelectionPath();

        if (parentPath == null) {
            //There is no selection. Default to the root node.
            parentNode = (DefaultMutableTreeNode) treeModel.getRoot(); //rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)
                    (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child,
                                            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode =
                new DefaultMutableTreeNode(child);

        treeModel.insertNodeInto(childNode, parent,
                parent.getChildCount());
        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            this.scrollPathToVisible(new TreePath(childNode.getPath()));
        }

        //treeModel.reload(parent);
        return childNode;
    }

    public void changeModel(DefaultTreeModel treeModel) {
        this.setModel(treeModel);
        this.treeModel = treeModel;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public void showLastPath(Project project) {
        ProjectElement lastProjectElement = project.getLastWinRepositoryProjectElement();
        if (lastProjectElement != null) {
            DefaultMutableTreeNode lastNode = ProjectService.getNodeById(lastProjectElement.getIdProjectElement());
            if (lastNode != null) {
                TreeNode[] lastArrayNodes = lastNode.getPath();
                TreePath lastPath = new TreePath(lastArrayNodes);
                boolean lastProjectElementExpanded = project.isLastWinRepositoryExpand();
                if (lastProjectElementExpanded) {
                    this.expandPath(lastPath);
                } else {
                    this.collapsePath(lastPath);
                }
            }
        }
    }
}
