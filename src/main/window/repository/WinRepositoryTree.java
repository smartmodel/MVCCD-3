package main.window.repository;

import main.MVCCDManager;
import project.Project;
import project.ProjectElement;
import project.ProjectService;

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
            private void myPopupEvent(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                JTree tree = (JTree)e.getSource();
                TreePath path = tree.getPathForLocation(x, y);
                if (path == null)
                    return;

                tree.setSelectionPath(path);
                DefaultMutableTreeNode rightClickedNode =
                        (DefaultMutableTreeNode)path.getLastPathComponent();
                if(rightClickedNode != null){
                    WinRepositoryPopupMenu popup = new WinRepositoryPopupMenu (rightClickedNode);
                    popup.show(tree, x, y);
                }
            }
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) myPopupEvent(e);
            }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) myPopupEvent(e);
            }
        };
        this.addMouseListener(ma);


        TreeSelectionListener ts = new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent evt) {
                if (evt.getNewLeadSelectionPath() != null)
                {
                    // récupérer le noeud sélectionné
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                            evt.getNewLeadSelectionPath().getLastPathComponent();
                }
            }
        };
        this.addTreeSelectionListener(ts);

        TreeExpansionListener te = new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent evt) {
                memorizeLastNodeUsed(evt, true);
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent evt) {
                memorizeLastNodeUsed(evt, false);
            }

            private void memorizeLastNodeUsed(TreeExpansionEvent evt, boolean expandCollapse){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) evt.getPath().getLastPathComponent();

                ProjectElement lastProjectElement = null;
                if (node.getUserObject() instanceof ProjectElement){
                    lastProjectElement = (ProjectElement) node.getUserObject();
                }
                if ( MVCCDManager.instance().getProject() != null) {
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

    public void changeModel(DefaultTreeModel treeModel){
       this.setModel(treeModel);
       this.treeModel = treeModel;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public void showLastPath(Project project) {
        ProjectElement lastProjectElement = project.getLastWinRepositoryProjectElement();
        if (lastProjectElement != null) {
            DefaultMutableTreeNode lastNode = ProjectService.getNodeById(lastProjectElement.getId());
            TreeNode[] lastArrayNodes = lastNode.getPath();
            TreePath lastPath = new TreePath (lastArrayNodes);
            boolean lastProjectElementExpanded = project.isLastWinRepositoryExpand();
            if (lastProjectElementExpanded) {
                this.expandPath(lastPath);
            }else{
                this.collapsePath(lastPath);
            }
        }
    }
}
