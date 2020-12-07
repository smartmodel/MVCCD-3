package utilities.window.scomponents.services;

import exceptions.CodeApplException;
import utilities.window.scomponents.STree;
import window.editor.preferences.project.PrefProject;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class STreeService {

    public static ArrayList<DefaultMutableTreeNode> findNodes(STree tree, Object object) {
        return findNodes((DefaultMutableTreeNode) tree.getModel().getRoot(), object);
    }


    public static ArrayList<DefaultMutableTreeNode> findNodes(DefaultMutableTreeNode nodeStart, Object object) {
        ArrayList<DefaultMutableTreeNode> resultat = new ArrayList<DefaultMutableTreeNode>();
        if (nodeStart.getUserObject() == object) {
            resultat.add(nodeStart);
        }
        for (int i = 0; i < nodeStart.getChildCount(); i++) {
            resultat.addAll(findNodes((DefaultMutableTreeNode) nodeStart.getChildAt(i), object));
        }
        return resultat;
    }

    public static DefaultMutableTreeNode findNode(STree tree, Object object) {
        ArrayList<DefaultMutableTreeNode> resultat = findNodes(tree, object);
        if (resultat.size() == 0) {
            return null;
        } else if (resultat.size() == 1) {
            return resultat.get(0);
        } else {
            throw new CodeApplException("L'objet est trouvé dans l'arbre en plusieurs exemplaires." +
                    "\r\n" + object.toString());
        }
    }
}
