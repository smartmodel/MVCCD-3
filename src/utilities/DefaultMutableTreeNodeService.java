package utilities;

import javax.swing.tree.DefaultMutableTreeNode;

public class DefaultMutableTreeNodeService {

    public static boolean isObjectDescendantOf(DefaultMutableTreeNode node, Object object){

        if (node.getParent() != null){
            DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) node.getParent();
            if (nodeParent.getUserObject().getClass() == object.getClass()){
                return true;
            } else {
                return isObjectDescendantOf(nodeParent, object);
            }
        } else {
            return false;
        }
    }
}
