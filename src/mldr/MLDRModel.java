package mldr;

import main.MVCCDElementFactory;
import mcd.MCDEntity;
import mcd.MCDRelation;
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

    public MLDRModel(ProjectElement parent, String name) {
        super(parent, name);
    }


    public MLDRTable createTable(MCDEntity mcdEntity) {
        MLDRTable newTable = MVCCDElementFactory.instance().createMLDRTable(
                getMDRContTables(), mcdEntity);

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


    public MLDRContRelations getMLDRContRelations() {
        return MLDRModelService.getMLDRContRelations(this);
    }


    public MLDRRelationFK createRelationFK(MCDRelation mcdRelation, MLDRTable mldrTableParent, MLDRTable mldrTableChild) {
        MLDRRelationFK newRelationFK = MVCCDElementFactory.instance().createMLDRRelationFK(
                getMLDRContRelations(), mcdRelation, mldrTableParent, mldrTableChild);

        return newRelationFK;
    }

    public ArrayList<IMLDRRelation> getIMLDRRelations() {
        return MLDRModelService.getIMLDRRelations(this);
    }

    public ArrayList<MLDRRelationFK> getMLDRRelationFKsByMCDRelationSource(MCDRelation mcdRelation) {
        return MLDRModelService.getMLDRRelationFKsByMCDRelationSource(this, mcdRelation);
    }

    public MLDRRelationFK getMLDRRelationFKByMCDRelationSourceAndSameTables(MCDRelation mcdRelation,
                                                                            MLDRTable mldrTableA,
                                                                            MLDRTable mldrTableB) {
        return MLDRModelService.getMLDRRelationFKByMCDRelationSourceAndSameTables(
                this, mcdRelation, mldrTableA, mldrTableB);
    }


    public ArrayList<String> treatTransform() {
        MLDRTransform mldrTransform = new MLDRTransform();
        ArrayList<String> resultat = mldrTransform.transform(this);

        return resultat;

    }


    public ArrayList<IMDRElementWithIteration> getIMDRElementsWithIterationInScope() {
        ArrayList<IMDRElementWithIteration> resultat = new ArrayList<IMDRElementWithIteration>();
        for (MDRElement mdrElement : getMDRDescendants()) {
            if (mdrElement instanceof IMDRElementWithIteration) {
                if (mdrElement instanceof MLDRModel) {
                    resultat.add((IMDRElementWithIteration) mdrElement);
                }
            }
        }
        return resultat;
    }
}
