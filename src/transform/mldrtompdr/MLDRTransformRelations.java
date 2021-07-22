package transform.mldrtompdr;

import main.MVCCDElement;
import main.MVCCDManager;
import mdr.MDRElement;
import mldr.*;
import mldr.interfaces.IMLDRRelation;
import mpdr.*;
import mpdr.interfaces.IMPDRRelation;
import mpdr.interfaces.IMPDRelEnd;

public class MLDRTransformRelations {

    private MLDRTransform mldrTransform ;
    private MLDRModel mldrModel ;
    private MPDRModel mpdrModel ;

    public MLDRTransformRelations(MLDRTransform mldrTransform, MLDRModel mldrModel, MPDRModel mpdrModel) {
        this.mldrTransform = mldrTransform;
        this.mldrModel = mldrModel;
        this.mpdrModel = mpdrModel;
    }


    void transformRelations() {
        //tansformTablesWithoutFKsIdComp();
        for (IMLDRRelation imldrRelation : mldrModel.getIMLDRRelations()){
            transformRelation(imldrRelation);
        }
    }


    private void transformRelation(IMLDRRelation imldrRelation) {

        IMPDRRelation iMPDRRelation = mpdrModel.getIMPDRRelationByIMLDRRelationSource(imldrRelation);
        if (iMPDRRelation == null){
            iMPDRRelation = mpdrModel.createIMPDRRelation(imldrRelation);
            MVCCDManager.instance().addNewMVCCDElementInRepository((MVCCDElement) iMPDRRelation);
            MVCCDManager.instance().addNewMVCCDElementInRepository((MVCCDElement) iMPDRRelation.getA());
            MVCCDManager.instance().addNewMVCCDElementInRepository((MVCCDElement) iMPDRRelation.getB());
        }
        modifyRelation(imldrRelation, iMPDRRelation );
        iMPDRRelation.setIteration(mldrTransform.getIteration());
        ((IMPDRelEnd)iMPDRRelation.getA()).setIteration(mldrTransform.getIteration());
        ((IMPDRelEnd)iMPDRRelation.getB()).setIteration(mldrTransform.getIteration());
    }

    private void modifyRelation(IMLDRRelation imldrRelation,
                                IMPDRRelation impdrRelation) {
        MLDRTransformService.modifyNames(imldrRelation, impdrRelation);
        MLDRTransformService.modifyName(mpdrModel, (MDRElement) impdrRelation);
        if (impdrRelation instanceof MPDRRelationFK) {
            modifyReferencingBetweenFKAndRelationFK((MLDRRelationFK)imldrRelation, (MPDRRelationFK)impdrRelation);
            modifyRelFKEnds((MPDRRelationFK) impdrRelation);
        }
    }


    private void modifyReferencingBetweenFKAndRelationFK(MLDRRelationFK mldrRelationFK,
                                                         MPDRRelationFK mpdrRelationFK) {
        MLDRFK mldrfk= (MLDRFK) mldrRelationFK.getMDRFK();
        MPDRTable mpdrTableAccueilNewValue = mpdrModel.getMPDRTableByMLDRTableSource((MLDRTable) mldrfk.getMDRTableAccueil());
        MPDRFK mpdrfkNewValue = mpdrTableAccueilNewValue.getMPDRFKByMLDRFKSource(mldrfk);

        if (mpdrRelationFK.getMDRFK() != mpdrfkNewValue) {
            // soit c'est une nouvelle relation
            // soit c'est un changement de cardinalité et l'ancienne FK sera automatiquement supprimée car
            // plus dans l'itération courante
            mpdrfkNewValue.setMDRRelationFK(mpdrRelationFK);
        }
    }

    private void modifyRelFKEnds(MPDRRelationFK mpdrRelationFK) {
        modifyRelFKEnd((MPDRRelFKEnd)mpdrRelationFK.getA());
        modifyRelFKEnd((MPDRRelFKEnd)mpdrRelationFK.getB());
    }

    private void modifyRelFKEnd(MPDRRelFKEnd mpdrRelFKEnd) {
        MLDRRelFKEnd mldrRelFKEnd = (MLDRRelFKEnd) mpdrRelFKEnd.getMldrElementSource();

        if (mpdrRelFKEnd.getRole() == null){
            mpdrRelFKEnd.setRole(mldrRelFKEnd.getRole());
        } else if (mpdrRelFKEnd.getRole() != mldrRelFKEnd.getRole()){
            mpdrRelFKEnd.setRole(mldrRelFKEnd.getRole());
        }

        if (mpdrRelFKEnd.getMultiMinStd() == null){
            mpdrRelFKEnd.setMultiMinStd(mldrRelFKEnd.getMultiMinStd());
        } else if (mpdrRelFKEnd.getMultiMinStd() != mldrRelFKEnd.getMultiMinStd()){
            mpdrRelFKEnd.setMultiMinStd(mldrRelFKEnd.getMultiMinStd());
        }

        if (mpdrRelFKEnd.getMultiMinCustom() == null){
            mpdrRelFKEnd.setMultiMinCustom(mldrRelFKEnd.getMultiMinCustom());
        } else if (mpdrRelFKEnd.getMultiMinCustom() != mldrRelFKEnd.getMultiMinCustom()){
            mpdrRelFKEnd.setMultiMinCustom(mldrRelFKEnd.getMultiMinCustom());
        }

        if (mpdrRelFKEnd.getMultiMaxStd() == null){
            mpdrRelFKEnd.setMultiMaxStd(mldrRelFKEnd.getMultiMaxStd());
        } else if (mpdrRelFKEnd.getMultiMaxStd() != mldrRelFKEnd.getMultiMaxStd()){
            mpdrRelFKEnd.setMultiMaxStd(mldrRelFKEnd.getMultiMaxStd());
        }

        if (mpdrRelFKEnd.getMultiMaxCustom() == null){
            mpdrRelFKEnd.setMultiMaxCustom(mldrRelFKEnd.getMultiMaxCustom());
        } else if (mpdrRelFKEnd.getMultiMaxCustom() != mldrRelFKEnd.getMultiMaxCustom()){
            mpdrRelFKEnd.setMultiMaxCustom(mldrRelFKEnd.getMultiMaxCustom());
        }
    }


}
