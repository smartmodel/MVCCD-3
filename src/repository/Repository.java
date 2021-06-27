package repository;

import delete.Delete;
import main.MVCCDElement;
import main.MVCCDElementProfileEntry;
import main.MVCCDElementService;
import main.MVCCDManager;
import mdr.MDRTable;
import mpdr.MPDRContTables;
import profile.Profile;
import project.Project;
import utilities.Trace;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;

public class Repository extends DefaultTreeModel{

    //private DefaultMutableTreeNode rootNode;
    private DefaultMutableTreeNode nodeProject = null;
    private DefaultMutableTreeNode nodeProfileEntry = null;
    private DefaultMutableTreeNode nodeProfile = null;

    public Repository(DefaultMutableTreeNode rootNode, MVCCDElement rootMvccdElement){
        super(rootNode);
        //this.rootNode = rootNode;
        addTreeModelListener(new RepositoryTreeModelListener(this));
        addChildsNodes(rootNode, rootMvccdElement);
    }

    public void addChildsNodes(DefaultMutableTreeNode nodeParent, MVCCDElement mvccdElementParent) {


        /*
        for ( MVCCDElement mvccdElement : mvccdElementParent.getChilds()){
            Trace.println("Elément Classe: " +mvccdElement.getClass().getName());
            //Trace.println("Elément name : " + mvccdElement.getName() );
            // La trace ci-dessous provoque une erreur !
            //Trace.println("Elément : " + mvccdElement.getName() );
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(mvccdElement);
            if (mvccdElement instanceof MVCCDElementProfileEntry){
                nodeProfileEntry = node ;
            }
            nodeParent.add (node);
            //#MAJ 2021-06-24 getChildsSortedDefault - MDRColumn (PK-FK) Entités/tables (nom)...
            //if (mvccdElement.getChildsSortedDefault().size()>0){
                addChildsNodes(node, mvccdElement);
            //}
        }

         */


        //#MAJ 2021-06-24 getChildsSortedDefault - MDRColumn (PK-FK) Entités/tables (nom)...
        ArrayList <? extends MVCCDElement> childs = mvccdElementParent.getChildsSortDefault();
        int i = 0 ;
        while ( i < childs.size() ){
            // La trace ci-dessous provoque une erreur !
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(childs.get(i));
            if (childs.get(i) instanceof MVCCDElementProfileEntry){
                nodeProfileEntry = node ;
            }
            nodeParent.add (node);
            //#MAJ 2021-06-24 getChildsSortedDefault - MDRColumn (PK-FK) Entités/tables (nom)...
            if (childs.get(i).getChilds().size()>0){
                addChildsNodes(node, childs.get(i));
            }
            i++;
        }

    }

    public DefaultMutableTreeNode addNode (DefaultMutableTreeNode nodeParent, MVCCDElement mvccdElement) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(mvccdElement);
        nodeParent.add (node);
        return node ;
    }


    public DefaultMutableTreeNode addNodeAndChilds(DefaultMutableTreeNode rootParent, MVCCDElement mvccdElement) {
        DefaultMutableTreeNode node = addNode(rootParent, mvccdElement);
        addChildsNodes(node, mvccdElement);
        return node ;
    }

    public void addProject( Project project){
        //nodeProject = addNodeAndChilds(getRootNode(), project);
        nodeProject = addNodeAndChilds((DefaultMutableTreeNode) getRoot(), project);
        this.reload();
    }


    public void removeProject(){
        if (nodeProject != null) {
            removeNodeFromParent(nodeProject);
            this.reload();
            nodeProject = null;
        }
    }


    public void addProfile(Profile profile) {
        nodeProfile = addNodeAndChilds(nodeProfileEntry, profile);
        this.reload();
    }

    public void removeProfile(){
        if (nodeProfileEntry != null) {
            if (nodeProfileEntry.getChildCount()> 0) {
                DefaultMutableTreeNode nodeProfile = (DefaultMutableTreeNode) getChild(nodeProfileEntry, 0);
                if (nodeProfile != null) {
                    removeNodeFromParent(nodeProfile);
                }
                this.reload();
                nodeProfile = null;
            }
        }
    }
    /*
    public void removeProject(DefaultMutableTreeNode node){
        int nbChilds = getChildCount(node);
        if (nbChilds > 0){
            for (int i = 0 ; i < nbChilds ; i++) {
                if ( getChild(node, i) instanceof Project){
                    removeNodeFromParent(getChild(node, i));
                }
            }
        }

    }
*/

/*
    public DefaultTreeModel emptyTreeModel(){
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new RepositoryTreeModelListener());
        return treeModel;
    }
*/

/*
    public DefaultMutableTreeNode getRootNode() {
        return rootNode;
    }
*/

    public DefaultMutableTreeNode addMVCCDElement(DefaultMutableTreeNode nodeParent, MVCCDElement mvccdElement)  {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(mvccdElement);
        int index = 0;
        if (mvccdElement.getParent() != null) {
            for (MVCCDElement child : mvccdElement.getParent().getChilds()) {
                if (child == mvccdElement) {
                    break;
                }
                index++;
            }
        }

        //insertNodeInto(node, nodeParent, nodeParent.getChildCount());
        insertNodeInto(node, nodeParent, index);
        if (mvccdElement.getChilds().size()>0){
            for (int i = 0 ; i < mvccdElement.getChilds().size(); i++ ){
                addMVCCDElement(node, mvccdElement.getChilds().get(i));
            }
        }
        return node;
    }

    public DefaultMutableTreeNode getNodeProject() {
        return nodeProject;
    }

    @Override
    public Object getRoot() {
        return (DefaultMutableTreeNode) super.getRoot();
    }
}
