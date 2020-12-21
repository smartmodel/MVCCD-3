package mldr;

import exceptions.CodeApplException;
import main.MVCCDElementFactory;
import mcd.*;
import md.MDElement;
import mdr.MDRColumn;
import mdr.MDRConstraint;
import mdr.MDRTable;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.services.MLDRColumnService;
import mldr.services.MLDRFKService;
import mldr.services.MLDRPKService;
import mldr.services.MLDRTableService;
import project.ProjectElement;

import java.util.ArrayList;

public class MLDRTable extends MDRTable implements IMLDRElement, IMLDRElementWithSource {

    private  static final long serialVersionUID = 1000;

    private MCDElement mcdElementSource ;
    private MCDEntityNature mcdEntitySourceNature ;

    public MLDRTable(ProjectElement parent,  MCDElement mcdElementSource) {
        super(parent);
        this.mcdElementSource = mcdElementSource;
    }

    @Override
    public MDElement getMdElementSource() {
        return mcdElementSource;
    }

    @Override
    public MCDElement getMcdElementSource() {
        return mcdElementSource;
    }

    @Override
    public void setMcdElementSource(MCDElement mcdElementSource) {
        this.mcdElementSource = mcdElementSource;
    }

    public MCDEntityNature getMcdEntitySourceNature() {
        return mcdEntitySourceNature;
    }

    public void setMcdEntitySourceNature(MCDEntityNature mcdEntitySourceNature) {
        this.mcdEntitySourceNature = mcdEntitySourceNature;
    }

    public ArrayList<MLDRColumn> getMLDRColumns() {
        return MLDRColumnService.to(getMDRColumns());
    }

    public ArrayList<MLDRColumn> getMLDRColumnsPK() {
        return MLDRColumnService.to(getMDRPK().getMDRColumns());
    }

    public ArrayList<MLDRColumn> getMLDRColumnsFK() {
        return MLDRColumnService.to(getMDRColumnsFK());
    }

    public ArrayList<MLDRColumn> getMLDRColumnsPFK() {
        return MLDRColumnService.to(getMDRColumnsPFK());
    }

    public MLDRColumn getMLDRColumnPKProper() {
        return MLDRColumnService.to(getMDRColumnPKProper());
    }


    public MLDRColumn getMLDRColumnByMCDElementSource(MCDElement mcdElement){
        return MLDRTableService.getMLDRColumnByMCDElementSource(this, mcdElement);
    }

    public ArrayList<MLDRColumn> getMLDRColumnsByMCDElementSource(MCDElement mcdElement){
        return MLDRTableService.getMLDRColumnsByMCDElementSource(this, mcdElement);
    }

    public MLDRColumn getMLDRColumnFKByMCDRelEndChildAndMLDRColumnPK(MCDRelEnd mcdRelEnd,
                                                                  MLDRColumn mldrColumnPK) {
        return MLDRTableService.getMLDRColumnFKByMCDRelEndChildAndMLDRColumnPK(this, mcdRelEnd, mldrColumnPK);
    }

    public MLDRColumn createColumn(MCDAttribute mcdAttribute) {
        MLDRColumn mldrColumn = MVCCDElementFactory.instance().createMLDRColumn(
                getMDRContColumns(),  mcdAttribute);
        return mldrColumn;
    }

    public MLDRColumn createColumnPK(MCDEntity mcdEntity) {
        MLDRColumn mldrColumn = MVCCDElementFactory.instance().createMLDRColumn(
                getMDRContColumns(), mcdEntity);
        return mldrColumn;
    }

    public MLDRColumn createColumnFK(MCDRelEnd mcdRelEnd, MLDRColumn mldrColumnPK){
        MLDRColumn mldrColumnFK = MVCCDElementFactory.instance().createMLDRColumn(
                getMDRContColumns(), mcdRelEnd);
        mldrColumnFK.setMdrColumnPK(mldrColumnPK);
        return mldrColumnFK;
    }

    public MLDRFK getMLDRFKByMCDElementSource(MCDElement mcdElement){
        return MLDRTableService.getMLDRFKByMCDElementSource(this, mcdElement);
    }


    public MLDRPK createPK(MCDEntity mcdEntity) {
        MLDRPK mldrPK= MVCCDElementFactory.instance().createMLDRPK(
                getMDRContConstraints(), mcdEntity);
        return mldrPK;
    }

    public MLDRPK createPK(MCDEntity mcdEntity, ArrayList<MCDAssociation> mcdAssociationsId) {
        MLDRPK mldrPK= MVCCDElementFactory.instance().createMLDRPK(
                getMDRContConstraints(), mcdEntity);
        return mldrPK;
    }


    public MLDRPK getMLDRPK(){
        return MLDRPKService.to (getMDRPK());
    }


    public ArrayList<MLDRFK> getMLDRFKs(){
        return MLDRFKService.to(getMDRFKs());
    }


    public MLDRFK createFK(MCDRelation mcdRelation) {
        MLDRFK mldrFK= MVCCDElementFactory.instance().createMLDRFK(
                (MLDRContConstraints) getMDRContConstraints(), mcdRelation);
        return mldrFK;
    }

    //TODO-0
    // Une association n:n sans entité associative doit avoir Name et shortName!

    /*
    @Override
    public String getShortName() {
        //TODO-0
        // Une association n:n sans entité associative doit avoir Name et shortName!
        return getMcdElementSource().getShortName();
    }

     */
}
