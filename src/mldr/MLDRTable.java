package mldr;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import exceptions.CodeApplException;
import main.MVCCDElementFactory;
import mcd.*;
import mcd.interfaces.IMCDSourceMLDRTable;
import md.MDElement;
import mdr.MDRTable;
import mdr.MDRUniqueNature;
import mdr.orderbuildnaming.MDROrderService;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.services.MLDRColumnService;
import mldr.services.MLDRFKService;
import mldr.services.MLDRPKService;
import mldr.services.MLDRTableService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
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

    public MLDRColumn getMLDRColumnByMCDElementSource(IMCDSourceMLDRTable imcdSourceMLDRTable, boolean columnPKTI){
        return MLDRTableService.getMLDRColumnByMCDElementSource(this, imcdSourceMLDRTable, columnPKTI);
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


    public MLDRColumn createColumnPK(IMCDSourceMLDRTable imcdSourceMLDRTable) {
        MLDRColumn mldrColumn = MVCCDElementFactory.instance().createMLDRColumn(
                getMDRContColumns(), (MCDElement) imcdSourceMLDRTable);
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

    public MLDRUnique getMLDRUniqueByMCDElementSourceAndNature(MCDElement mcdElement, MDRUniqueNature mdrUniqueNature){
        return MLDRTableService.getMLDRUniqueByMCDElementSourceAndNature(this, mcdElement, mdrUniqueNature);
    }

    public MLDRUnique createUnique(MCDElement mcdElement) {
        MLDRUnique mldrUnique= MVCCDElementFactory.instance().createMLDRUnique(
                (MLDRContConstraints) getMDRContConstraints(), mcdElement);
        return mldrUnique;
    }

    /*
    public MLDRUnique createUnique(MCDUnicity mcdUnicity) {
        MLDRUnique mldrUnique= MVCCDElementFactory.instance().createMLDRUnique(
                (MLDRContConstraints) getMDRContConstraints(), mcdUnicity);
        return mldrUnique;
    }

     */


    public MLDRConstraintCustomSpecialized getMLDRSpecializeByMCDElementSource(MCDElement mcdElement){
        return MLDRTableService.getMLDRSpecializeByMCDElementSource(this, mcdElement);
    }


    public MLDRConstraintCustomSpecialized createSpecialized(MCDGeneralization mcdGeneralization) {
        MLDRConstraintCustomSpecialized mldrSpecialized= MVCCDElementFactory.instance().createMLDRSpecialized(
                (MLDRContConstraints) getMDRContConstraints(), mcdGeneralization);
        return mldrSpecialized;
    }


    public MLDRConstraintCustomJnal getMLDRJnalByMCDElementSource(MCDElement mcdElement){
        return MLDRTableService.getMLDRJnalByMCDElementSource(this, mcdElement);
    }


    public MLDRConstraintCustomJnal createJnal(MCDEntity mcdEntity) {
        MLDRConstraintCustomJnal mldrJnal = MVCCDElementFactory.instance().createMLDRJnal(
                (MLDRContConstraints) getMDRContConstraints(), mcdEntity);
        return mldrJnal;
    }

    public MLDRConstraintCustomAudit getMLDRAuditByMCDElementSource(MCDElement mcdElement){
        return MLDRTableService.getMLDRAuditByMCDElementSource(this, mcdElement);
    }


    public MLDRConstraintCustomAudit createAudit(MCDEntity mcdEntity) {
        MLDRConstraintCustomAudit mldrAudit = MVCCDElementFactory.instance().createMLDRAudit(
                (MLDRContConstraints) getMDRContConstraints(), mcdEntity);
        return mldrAudit;
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
    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = super.getConstraints();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }


    public MCDEntity getEntityParentSource() {
        if (getMcdElementSource() instanceof MCDEntity) {
            return (MCDEntity) getMcdElementSource();
        }
        return null;
    }


    public MCDAssociation getAssNNParentSource() {
        if ( getMcdElementSource() instanceof MCDAssociation) {
            return (MCDAssociation) getMcdElementSource();
        }
        return null ;
    }


    public String getShortName() {
        MCDEntity mcdEntityParentSource = getEntityParentSource();
        MCDAssociation mcdAssNNParentSource = getAssNNParentSource();
        if (mcdEntityParentSource != null) {
            return MDROrderService.getPath(mcdEntityParentSource) + mcdEntityParentSource.getShortName();
        }  else if (mcdAssNNParentSource != null) {
            if (StringUtils.isNotEmpty(mcdAssNNParentSource.getShortName())) {
                return MDROrderService.getPath(mcdAssNNParentSource) + mcdAssNNParentSource.getShortName();
            } else {
                throw new CodeApplException("Le shortName de l'association n:n " + mcdAssNNParentSource.getNameTreePath() + " n'est pas déterminé");
            }
        } else {
            throw new CodeApplException("Le shortName n'est calculé que pour une table provenant d'une entité ou d'une association n:n");
        }
    }

}
