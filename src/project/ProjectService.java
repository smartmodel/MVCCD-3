package project;

import main.MVCCDElement;
import main.MVCCDElementService;
import main.MVCCDManager;
import mcd.MCDEntity;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class ProjectService {


    public ProjectService() {

    }

    static Project getProjectRoot(ProjectElement projectElement) {
        if (projectElement instanceof Project) {
            return ((Project) projectElement);
        } else {
            return getProjectRoot((ProjectElement) projectElement.getParent());
        }
    }

    public static ProjectElement getElementById(int id) {
        Project project = MVCCDManager.instance().getProject();
        return getElementById(project, id);
    }

    public static ProjectElement getElementById(ProjectElement projectElement, int id) {
        ProjectElement resultat = null;
        for (MVCCDElement mvccdElement : projectElement.getChilds()) {
            if (mvccdElement instanceof ProjectElement) {
                ProjectElement child = (ProjectElement) mvccdElement;
                if (child.getId() == id) {
                    resultat = child;
                } else {
                    if (resultat == null) {
                        resultat = getElementById(child, id);
                    }
                }
            }
        }
        return resultat;
    }

    public static DefaultMutableTreeNode getNodeById(int id) {

        DefaultMutableTreeNode rootProject = MVCCDManager.instance().getRepository().getNodeProject();
        return getNodeById(rootProject, id);
    }

    public static DefaultMutableTreeNode getNodeById(DefaultMutableTreeNode root, int id) {
        DefaultMutableTreeNode resultat = null;
        System.out.println(root.getUserObject().toString());
        if (root.getUserObject() instanceof ProjectElement) {
            System.out.println(((ProjectElement) root.getUserObject()).getName() + "  " + ((ProjectElement) root.getUserObject()).getId());
            ProjectElement projectElement = (ProjectElement) root.getUserObject();

            System.out.println("Nb childs   " + root.getChildCount());
            if (projectElement.getId() == id) {
                System.out.println("Egalité");
                resultat = root;
            }
        }
        if (resultat == null){
            for (int i = 0; i < root.getChildCount(); i++) {
                System.out.println("¬ ProjectElement");
                if (resultat == null) {
                    resultat = getNodeById((DefaultMutableTreeNode) root.getChildAt(i), id);
                }
            }
        }
        return resultat;
    }
}

