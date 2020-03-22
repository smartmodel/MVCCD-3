package repository;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import preferences.Preferences;
import project.Project;
import project.ProjectElement;

import javax.swing.tree.DefaultMutableTreeNode;

public class RepositoryService {

    private static RepositoryService instance;

    public static synchronized RepositoryService instance() {
        if (instance == null) {
            instance = new RepositoryService();
        }
        return instance;
    }

    public DefaultMutableTreeNode getNodeInChildsByIdElement(DefaultMutableTreeNode parent,
                                                             Integer id ){

        int nbChilds = parent.getChildCount();
        if (nbChilds >= 1) {
            for (int i = 0 ; i < nbChilds ; i++){
                DefaultMutableTreeNode nodeChild = (DefaultMutableTreeNode) parent.getChildAt(i);
                if (nodeChild.getUserObject() instanceof ProjectElement){
                    ProjectElement projectElement = (ProjectElement) nodeChild.getUserObject();
                    if (projectElement.getId() == id ){
                        return nodeChild;
                    }
                }
            }
        }
        return null;
    }




}