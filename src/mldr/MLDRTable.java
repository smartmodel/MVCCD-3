package mldr;

import constraints.Constraint;
import constraints.ConstraintService;
import constraints.Constraints;
import constraints.ConstraintsManager;
import main.MVCCDElementFactory;
import mcd.*;
import md.MDElement;
import mdr.MDRTable;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.services.MLDRColumnService;
import mldr.services.MLDRFKService;
import mldr.services.MLDRPKService;
import mldr.services.MLDRTableService;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MLDRTable extends MDRTable implements IMLDRElement, IMLDRElementWithSource {

    private  static final long serialVersionUID = 1000;

    private MCDElement mcdElementSource ;
    private MCDEntityNature mcdEntitySourceNature ;

    public MLDRTable(ProjectElement parent,  MCDElement mcdElementSource, int id) {
        super(parent, id);
        this.mcdElementSource = mcdElementSource;
    }

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


    public ArrayList<MLDRColumn> getMLDRColumnsFKIdNat() {
        return MLDRColumnService.to(getMDRColumnsFKIdNat());
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

    public MLDRPK createPK(MCDAssociation mcdAssNN) {
        MLDRPK mldrPK= MVCCDElementFactory.instance().createMLDRPK(
                getMDRContConstraints(), mcdAssNN);
        return mldrPK;
    }

    public MLDRPK getMLDRPK(){
        return MLDRPKService.to (getMDRPK());
    }

    public ArrayList<MLDRFK> getMLDRFKs(){
        return MLDRFKService.to(getMDRFKs());
    }

    public ArrayList<MLDRFK> getMLDRFKsIdNat(){
        return MLDRFKService.to(getMDRFKsIdNat());
    }


    public MLDRFK createFK(MCDRelEnd mcdRelEnd) {
        MLDRFK mldrFK= MVCCDElementFactory.instance().createMLDRFK(
                (MLDRContConstraints) getMDRContConstraints(), mcdRelEnd);
        return mldrFK;
    }


    public MLDRUnique getMLDRUniqueByMCDElementSource(MCDElement mcdElement){
        return MLDRTableService.getMLDRUniqueByMCDElementSource(this, mcdElement);
    }

    public MLDRUnique createUnique(MCDUnicity mcdUnicity) {
        MLDRUnique mldrUnique= MVCCDElementFactory.instance().createMLDRUnique(
                (MLDRContConstraints) getMDRContConstraints(), mcdUnicity);
        return mldrUnique;
    }



    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = super.getStereotypes();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }

    @Override
    public String getStereotypesInBox() {
        return StereotypeService.getUMLNamingInBox(getStereotypes());
    }

    @Override
    public String getStereotypesInLine() {
        return StereotypeService.getUMLNamingInLine(getStereotypes());
    }


    @Override
    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = super.getConstraints();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }

    @Override
    public String getConstraintsInBox() {
        return ConstraintService.getUMLNamingInBox(getConstraints());
    }

    @Override
    public String getConstraintsInLine() {
        return ConstraintService.getUMLNamingInLine(getConstraints());
    }

}
