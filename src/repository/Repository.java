package repository;

import main.MVCCDElement;
import main.MVCCDManager;
import project.Project;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Repository extends DefaultTreeModel{

    //private DefaultMutableTreeNode rootNode;
    private DefaultMutableTreeNode nodeProject = null;

    public Repository(DefaultMutableTreeNode rootNode, MVCCDElement rootMvccdElement){
        super(rootNode);
        //this.rootNode = rootNode;
        addTreeModelListener(new RepositoryTreeModelListener(this));
        addChildsNodes(rootNode, rootMvccdElement);
    }

    public void addChildsNodes(DefaultMutableTreeNode nodeParent, MVCCDElement mvccdElementParent) {
        for ( MVCCDElement mvccdElement : mvccdElementParent.getChilds()){
            System.out.println(mvccdElement.toString());
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(mvccdElement);
            nodeParent.add (node);
            if (mvccdElement.getChilds().size()>0){
                addChildsNodes(node, mvccdElement);
            }
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

    public DefaultMutableTreeNode addMVCCDElement(MVCCDElement mvccdElement , DefaultMutableTreeNode nodeParent) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(mvccdElement);
        insertNodeInto(node, nodeParent, nodeParent.getChildCount());
        //nodeParent.add (node);
        return node;
    }

    public DefaultMutableTreeNode getNodeProject() {
        return nodeProject;
    }
}
