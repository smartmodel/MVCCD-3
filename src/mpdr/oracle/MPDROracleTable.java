package mpdr.oracle;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import exceptions.CodeApplException;
import main.MVCCDElementFactory;
import mldr.*;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCheck;
import mpdr.*;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.tapis.*;
import mpdr.tapis.oracle.MPDROracleBoxTriggers;
import mpdr.tapis.oracle.MPDROracleTrigger;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MPDROracleTable extends MPDRTable implements IMPDROracleElement {

    private static final long serialVersionUID = 1000;

    public MPDROracleTable(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROracleTable(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRColumn createColumn(MLDRColumn mldrColumn) {
        MPDROracleColumn newColumn = MVCCDElementFactory.instance().createMPDROracleColumn(
                getMDRContColumns(), mldrColumn);

        return newColumn;
    }

    @Override
    public MPDRPK createPK(MLDRPK mldrPK) {
        MPDROraclePK newPK = MVCCDElementFactory.instance().createMPDROraclePK(
                getMDRContConstraints(), mldrPK);
        return newPK;
    }

    @Override
    public MPDRFK createFK(MLDRFK mldrFK) {
        MPDROracleFK newFK = MVCCDElementFactory.instance().createMPDROracleFK(
                getMDRContConstraints(), mldrFK);
        return newFK;
    }

    @Override
    public MPDRUnique createUnique(MLDRUnique mldrUnique) {
        MPDROracleUnique newUnique = MVCCDElementFactory.instance().createMPDROracleUnique(
                getMDRContConstraints(), mldrUnique);
        return newUnique;
    }

    @Override
    public MPDRCheck createCheck(IMLDRSourceMPDRCheck imldrSourceMPDRCheck) {
        MPDROracleCheck newCheck = MVCCDElementFactory.instance().createMPDROracleCheck(
                getMDRContConstraints(), imldrSourceMPDRCheck);
        return newCheck;
    }

    @Override
    public MPDRBoxTriggers createBoxTriggers(MLDRTable mldrTable) {
        MPDROracleBoxTriggers mpdrOracleBoxTriggers = MVCCDElementFactory.instance().createMPDROracleBoxTriggers(
                getMPDRContTAPIs(), mldrTable);
        return mpdrOracleBoxTriggers;
    }



    @Override
    public MPDRTrigger createTrigger(MPDRTriggerType mpdrTriggerType, MLDRTable mldrTable) {
        MPDROracleBoxTriggers mpdrOracleBoxTriggers = (MPDROracleBoxTriggers) getMPDRBoxTriggers();
        if (mpdrOracleBoxTriggers != null){
            MPDROracleTrigger mpdrOracleTrigger = MVCCDElementFactory.instance().createMPDROracleTrigger(
                    getMPDRBoxTriggers(), mldrTable);
            return mpdrOracleTrigger;
        } else {
            throw new CodeApplException("La boîte Triggers doit exister avant de créer un trigger");
        }
    }

    @Override
    public MPDRBoxProceduresOrFunctions createBoxProceduresOrFunctions(MLDRTable mldrTable) {
        return null;
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = super.getStereotypes();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(stereotypes.getStereotypeByLienProg(this.getClass().getName(),
                preferences.STEREOTYPE_ORACLE_LIENPROG));

        return resultat;
    }


    @Override
    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = super.getConstraints();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }

    @Override
    public MPDRFunction createFunction(MPDRFunctionType type, MLDRTable mldrTable) {
        return null;
    }

}

