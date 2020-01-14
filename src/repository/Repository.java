package repository;

import main.MVCCDElement;
import project.Project;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Repository extends DefaultTreeModel{

    private DefaultMutableTreeNode rootNode;

    public Repository(DefaultMutableTreeNode rootNode, MVCCDElement rootMvccdElement){
        super(rootNode);
        this.rootNode = rootNode;
        addTreeModelListener(new RepositoryTreeModelListener(this));
        addChildsNodes(rootNode, rootMvccdElement);
    }

    private void addChildsNodes(DefaultMutableTreeNode rootParent, MVCCDElement mvccdElementParent) {
        for ( MVCCDElement mvccdElement : mvccdElementParent.getChilds()){
            System.out.println(mvccdElement.toString());
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(mvccdElement);
            rootParent.add (node);
            if (mvccdElement.getChilds().size()>0){
                addChildsNodes(node, mvccdElement);
            }
        }
    }

/*
    public DefaultTreeModel emptyTreeModel(){
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new RepositoryTreeModelListener());
        return treeModel;
    }
*/

    public DefaultMutableTreeNode getRootNode() {
        return rootNode;
    }


    public DefaultMutableTreeNode addMVCCDElement(MVCCDElement mvccdElement , DefaultMutableTreeNode nodeParent) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(mvccdElement);
        insertNodeInto(node, nodeParent, nodeParent.getChildCount());
        //nodeParent.add (node);
        return node;
    }
}
