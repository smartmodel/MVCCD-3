package mldr;

import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDAssociation;
import mcd.MCDEntity;
import mcd.MCDRelation;
import mcd.interfaces.IMCDSourceMLDRRelationFK;
import mdr.MDRContTables;
import mdr.MDRElement;
import mdr.MDRModel;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRRelation;
import mldr.services.MLDRModelService;
import project.ProjectElement;
import transform.mldrtompdr.MLDRTransform;

import java.util.ArrayList;

public abstract class MLDRModel extends MDRModel implements IMLDRElement {

    private static final long serialVersionUID = 1000;

    public MLDRModel(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MLDRModel(ProjectElement parent, String name) {
        super(parent, name);
    }


    public MLDRTable createTable(MCDEntity mcdEntity) {
        MLDRTable newTable = MVCCDElementFactory.instance().createMLDRTable(
                getMDRContTables(), mcdEntity);

        return newTable;
    }

    public MLDRTable createTable(MCDAssociation mcdAssNN) {
        MLDRTable newTable = MVCCDElementFactory.instance().createMLDRTable(
                getMDRContTables(), mcdAssNN);

        return newTable;
    }

    public MDRContTables getMDRContTables() {
        return MLDRModelService.getMDRContTables(this);
    }

    public ArrayList<MLDRTable> getMLDRTables() {
        return MLDRModelService.getMLDRTables(this);
    }

    public MLDRTable getMLDRTableByEntitySource(MCDEntity mcdEntity) {
        return MLDRModelService.getMLDRTableByEntitySource(this, mcdEntity);
    }

    public MLDRTable getMLDRTableByAssNNSource(MCDAssociation mcdAssociation) {
        return MLDRModelService.getMLDRTableByAssNNSource(this, mcdAssociation);
    }


    public MLDRContRelations getMLDRContRelations() {
        return MLDRModelService.getMLDRContRelations(this);
    }


    public MLDRRelationFK createRelationFK(IMCDSourceMLDRRelationFK imcdSourceMLDRRelationFK, MLDRTable mldrTableParent, MLDRTable mldrTableChild) {
        MLDRRelationFK newRelationFK = MVCCDElementFactory.instance().createMLDRRelationFK(
                getMLDRContRelations(), imcdSourceMLDRRelationFK, mldrTableParent, mldrTableChild);

        return newRelationFK;
    }

    public ArrayList<IMLDRRelation> getIMLDRRelations() {
        return MLDRModelService.getIMLDRRelations(this);
    }

    public ArrayList<MLDRRelationFK> getMLDRRelationFKsByMCDRelationSource(MCDRelation mcdRelation) {
        return MLDRModelService.getMLDRRelationFKsByIMCDSource(this, mcdRelation);
    }

    public MLDRRelationFK getMLDRRelationFKByIMCDSourceAndSameTables(IMCDSourceMLDRRelationFK imcdSourceMLDRRelationFK,
                                                                     MLDRTable mldrTableA,
                                                                     MLDRTable mldrTableB) {
        return MLDRModelService.getMLDRRelationFKByMCDRelationSourceAndSameTables(
                this, imcdSourceMLDRRelationFK, mldrTableA, mldrTableB);
    }


    public boolean treatTransform() {
        MLDRTransform mldrTransform = new MLDRTransform();
        return  mldrTransform.transform(this);
    }


    public ArrayList<IMDRElementWithIteration> getIMDRElementsWithIterationInScope() {
        ArrayList<IMDRElementWithIteration> resultat = new ArrayList<IMDRElementWithIteration>();
        for (MDRElement mdrElement : getMDRDescendants()) {
            if (mdrElement instanceof IMDRElementWithIteration) {
                //if (mdrElement instanceof MLDRModel) {
                if (mdrElement instanceof IMLDRElement) {
                    resultat.add((IMDRElementWithIteration) mdrElement);
                }
            }
        }
        return resultat;
    }

    //TODO-1 Je vourais raffraichir les noeud modifiés
    // Mettre un listner de l'arbre sur les objets IMLDRElement ?
    /*
    public ArrayList<DefaultMutableTreeNode> getTreeMLDR(){
        DefaultMutableTreeNode rootMLDR = this.getNode();
        return getTreeMLDRInternal(rootMLDR);
    }

    protected ArrayList<DefaultMutableTreeNode> getTreeMLDRInternal(DefaultMutableTreeNode nodeMLDR){
        ArrayList<DefaultMutableTreeNode> resultat = new ArrayList<DefaultMutableTreeNode>();
        for (int i = 0 ; i < nodeMLDR.getChildCount() ; i++){
            DefaultMutableTreeNode nodeChild = (DefaultMutableTreeNode) nodeMLDR.getChildAt(i);
           if (nodeChild.getUserObject() instanceof IMLDRElement){
               resultat.add(nodeChild);
               resultat.addAll(getTreeMLDRInternal(nodeChild));
           }
        }
        return resultat;
    }

    public void refreshTreeMLDR(){
        for (DefaultMutableTreeNode node : getTreeMLDR()){
            Trace.println(node.getUserObject().toString());
        }
        WinRepositoryTree tree = MVCCDManager.instance().getWinRepositoryContent().getTree();
        tree.getTreeModel().reload(this.getNode());
    }

     */

    //TODO-1 A suppimer si la solution du listner est possible
    public void refreshTreeMLDR(){
        MVCCDManager.instance().getWinRepositoryContent().reload(this.getNode());
    }


}
