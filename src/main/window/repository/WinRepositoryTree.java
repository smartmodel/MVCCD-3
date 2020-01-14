package main.window.repository;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
                System.out.println("Path:  "  + path.toString());

                DefaultMutableTreeNode rightClickedNode =
                        (DefaultMutableTreeNode)path.getLastPathComponent();

                System.out.println("Node:  "  + rightClickedNode.toString());

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
        System.out.println("Ajout fait");
        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            System.out.println("Rendre visible...");
            this.scrollPathToVisible(new TreePath(childNode.getPath()));
        }

        //treeModel.reload(parent);
        return childNode;
    }

    public void changeModel(DefaultTreeModel treeModel){
       this.setModel(treeModel);
       this.treeModel = treeModel;
    }


}
