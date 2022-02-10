package mpdr;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import exceptions.CodeApplException;
import md.MDElement;
import mdr.MDRConstraint;
import mdr.MDRTable;
import mldr.*;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCConstraintSpecifc;
import mpdr.interfaces.IMPDRConstraint;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.services.MPDRColumnService;
import mpdr.services.MPDRConstraintService;
import mpdr.services.MPDRTableService;
import mpdr.tapis.*;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MPDRTable extends MDRTable implements IMPDRElement, IMPDRElementWithSource {

    private static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    public MPDRTable(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRTable(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRTable(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, id);
        this.mldrElementSource = mldrElementSource;
    }

    @Override
    public IMLDRElement getMldrElementSource() {
        return mldrElementSource;
    }

    @Override
    public void setMldrElementSource(IMLDRElement imldrElementSource) {
        this.mldrElementSource = mldrElementSource;
    }


    @Override
    public MDElement getMdElementSource() {
        return (MDElement) getMldrElementSource();
    }


    //TODO-0
    // Une association n:n sans entité associative doit avoir Name et shortName!

    /*

    @Override
    public String getShortName() {
        return ((IMLDRElementWithSource) getMldrElementSource()).getMcdElementSource().getShortName();
    }

     */


    public MPDRColumn getMPDRColumnByMLDRColumnSource(MLDRColumn mldrColumn) {
        return MPDRTableService.getMPDRColumnByMLDRColumnSource(this, mldrColumn);
    }


    public IMPDRConstraint getMPDRConstraintInheritedByMLDRConstraintSource(MDRConstraint mldrConstraint) {
        return MPDRTableService.getMPDRConstraintInheritedByMLDRConstraintSource(this, mldrConstraint);
    }

    public MPDRCheckSpecific getMPDRConstraintSpecificByMLDRSourceAndRole(IMLDRSourceMPDRCConstraintSpecifc imldrSourceMPDRCConstraintSpecifc,
                                                                          MPDRConstraintSpecificRole mpdrConstraintSpecificRole){
        return MPDRTableService.getMPDRCheckSpecificByMLDRSourceAndRole(this,
                imldrSourceMPDRCConstraintSpecifc, mpdrConstraintSpecificRole);
    }

    public MPDRFK getMPDRFKByMLDRFKSource(MLDRFK mldrFk) {
        return MPDRTableService.getMPDRFKByMLDRFKSource(this, mldrFk);
    }

    public ArrayList<MPDRColumn> getMPDRColumns() {
        return MPDRColumnService.to(getMDRColumns());
    }

    public ArrayList<MPDRColumn> getMPDRColumnsSortDefault() {
        return MPDRColumnService.to(getMDRColumnsSortDefault());
    }

    public abstract MPDRColumn createColumn(MLDRColumn mldrColumn);

    public abstract MPDRPK createPK(MLDRPK mldrPK);

    public abstract MPDRFK createFK(MLDRFK mldrFK);
    public abstract MPDRIndex createIndex(MLDRFK mldrFK);

    public abstract MPDRUnique createUnique(MLDRUnique mldrUnique);

    public abstract MPDRCheckSpecific createCheckSpecific(IMLDRSourceMPDRCConstraintSpecifc imldrSourceMPDRCConstraintSpecifc);

    public abstract MPDRBoxTriggers createBoxTriggers(MLDRTable mldrTable);

    public abstract MPDRTrigger createTrigger(MPDRTriggerType mpdrTriggerType, MLDRTable mldrTable);


    public abstract MPDRBoxProceduresOrFunctions createBoxProceduresOrFunctions(MLDRTable mldrTable);

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


    public MPDRPK getMPDRPK() {
        return MPDRConstraintService.getMPDRPK(getMDRConstraints());
    }

    public ArrayList<MPDRFK> getMPDRFKs() {
        return MPDRConstraintService.getMPDRFKs(getMDRConstraints());
    }

    public MPDRModel getMPDRModelParent() {
        return (MPDRModel) getMDRModelParent();
    }


    public MLDRTable getMLDRTableSource() {
        if (getMldrElementSource() instanceof MLDRTable) {
            return (MLDRTable) getMldrElementSource();
        }
        return null;
    }

    public String getShortName() {
        if (getMLDRTableSource() != null) {
            return getMLDRTableSource().getShortName();
        } else {
            throw new CodeApplException("Le shortName n'est calculé que pour une table provenant d'une entité");
        }
    }

    public MPDRContTAPIs getMPDRContTAPIs(){
        return MPDRTableService.getMPDRContTAPIs(this);
    }


    public ArrayList<IMPDRConstraint> getIMPDRConstraints(){
        ArrayList<IMPDRConstraint> resultat = new ArrayList<IMPDRConstraint>();
        for ( MDRConstraint mdrConstraint : getMDRContConstraints().getMDRConstraints()){
            if ( mdrConstraint instanceof IMPDRConstraint){
                resultat.add ((IMPDRConstraint) mdrConstraint );
            }
        }
        return resultat;
    }

    public MPDRColumn getMPDRColumnPKProper (){
        return (MPDRColumn) super.getMDRColumnPKProper();
    }

    public MPDRBoxTriggers getMPDRBoxTriggers() {
        return getMPDRContTAPIs().getMPDRBoxTriggers();
    }

    public ArrayList<MPDRTrigger> getMPDRTriggers(){
        if ( getMPDRBoxTriggers() != null) {
            return getMPDRBoxTriggers().getAllTriggers();
        }
        return null;
    }

    public MPDRTrigger getMPDRTriggerByType(MPDRTriggerType type){
        return getMPDRBoxTriggers().getMPDRTriggerByType(type);
    }


    public MPDRBoxProceduresOrFunctions getMPDRBoxProceduresOrFunctions() {
        return getMPDRContTAPIs().getMPDRBoxProceduresOrFunctions();
    }

    public MPDRFunction getMPDRFunctionByType(MPDRFunctionType type){
        return getMPDRBoxProceduresOrFunctions().getMPDRFunctionByType(type);
    }


    public ArrayList<MPDRFunction> getMPDRFunctions(){
        if ( getMPDRBoxProceduresOrFunctions() != null) {
            return getMPDRBoxProceduresOrFunctions().getAllFunctions();
        }
        return null;
    }

    public abstract MPDRFunction createFunction(MPDRFunctionType type, MLDRTable mldrTable);

}
