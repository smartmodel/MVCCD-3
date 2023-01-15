package main.window.repository;

import diagram.Diagram;
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
        this.treeModel = treeModel;
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setShowsRootHandles(true);
        this.setEditable(false);

        // add MouseListener to tree
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTree tree = (JTree) e.getSource();
                TreePath path = tree.getPathForLocation(e.getX(), e.getY());

                tree.setSelectionPath(path);

                if (path == null) {
                    return;
                }

                DefaultMutableTreeNode clickedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

                if (clickedNode != null) {
                    if (clickedNode.getUserObject() instanceof Diagram) {
                        if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e) && !e.isConsumed()) {
                            e.consume();
                            DiagrammerService.getDrawPanel().unloadAllShapes();
                            Diagram diagramClicked = (Diagram) clickedNode.getUserObject();
                            MVCCDManager.instance().setCurrentDiagram(diagramClicked);
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    this.myPopupEvent(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    this.myPopupEvent(e);
                }
            }

            private void myPopupEvent(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                JTree tree = (JTree) e.getSource();
                TreePath path = tree.getPathForLocation(x, y);
                if (path == null) {
                    return;
                }

                tree.setSelectionPath(path);

                DefaultMutableTreeNode clickedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

                if (clickedNode != null) {

                    WinRepositoryPopupMenu popup = new WinRepositoryPopupMenu(clickedNode);
                    popup.show(tree, x, y);
                }
            }
        };
        this.addMouseListener(ma);


        TreeSelectionListener ts = new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent evt) {
                if (evt.getNewLeadSelectionPath() != null) {
                    // récupérer le noeud sélectionné
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) evt.getNewLeadSelectionPath().getLastPathComponent();
                }
            }
        };
        this.addTreeSelectionListener(ts);

        TreeExpansionListener te = new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent evt) {
                this.memorizeLastNodeUsed(evt, true);
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent evt) {
                this.memorizeLastNodeUsed(evt, false);
            }

            private void memorizeLastNodeUsed(TreeExpansionEvent evt, boolean expandCollapse) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) evt.getPath().getLastPathComponent();

                ProjectElement lastProjectElement = null;
                if (node.getUserObject() instanceof ProjectElement) {
                    lastProjectElement = (ProjectElement) node.getUserObject();
                }
                if (MVCCDManager.instance().getProject() != null) {
                    MVCCDManager.instance().getProject().setLastWinRepositoryProjectElement(lastProjectElement);
                    MVCCDManager.instance().getProject().setLastWinRepositoryExpand(expandCollapse);
                }
            }
        };
        this.addTreeExpansionListener(te);

    }

    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = this.getSelectionPath();

        if (parentPath == null) {
            //There is no selection. Default to the root node.
            parentNode = (DefaultMutableTreeNode) this.treeModel.getRoot(); //rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }

        return this.addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

        this.treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            this.scrollPathToVisible(new TreePath(childNode.getPath()));
        }

        return childNode;
    }


    public void changeModel(DefaultTreeModel treeModel) {
        this.setModel(treeModel);
        this.treeModel = treeModel;
        this.updateIcons();
    }

    public DefaultTreeModel getTreeModel() {
        return this.treeModel;
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

    public void updateIcons() {
        // Récupérer tous les noeuds du référentiel
        int rowCount = this.getRowCount();

    }
}
