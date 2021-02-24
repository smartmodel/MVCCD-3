package repository;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class RepositoryTreeModelListener implements TreeModelListener {

    private DefaultTreeModel defaultTreeModel;

    public RepositoryTreeModelListener(DefaultTreeModel defaultTreeModel) {
        this.defaultTreeModel = defaultTreeModel;
    }

    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode)
                (e.getTreePath().getLastPathComponent());

        /*
         * If the event lists children, then the changed
         * node is the child of the node we have already
         * gotten.  Otherwise, the changed node and the
         * specified node are the same.
         */
        try {
            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode)
                    (node.getChildAt(index));
        } catch (NullPointerException exc) {}

        //defaultTreeModel.nodeChanged(node);

    }

    @Override
    public void treeNodesInserted(TreeModelEvent treeModelEvent) {
    }

    @Override
    public void treeNodesRemoved(TreeModelEvent treeModelEvent) {

    }

    @Override
    public void treeStructureChanged(TreeModelEvent treeModelEvent) {
    }
}
