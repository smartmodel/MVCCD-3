package mpdr.oracle;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import exceptions.CodeApplException;
import main.MVCCDElementFactory;
import mldr.*;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCConstraintSpecifc;
import mpdr.*;
import mpdr.interfaces.IMPDRTableRequirePackages;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.tapis.*;
import mpdr.tapis.oracle.MPDROracleBoxPackages;
import mpdr.tapis.oracle.MPDROracleBoxTriggers;
import mpdr.tapis.oracle.MPDROraclePackage;
import mpdr.tapis.oracle.MPDROracleTrigger;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MPDROracleTable extends MPDRTable implements IMPDROracleElement, IMPDRTableRequirePackages {

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
    public MPDRIndex createIndex(MLDRFK mldrFK) {
        MPDROracleIndex newIndex = MVCCDElementFactory.instance().createMPDROracleIndex(
                getMDRContConstraints(), mldrFK);
        return newIndex;
    }

    @Override
    public MPDRUnique createUnique(MLDRUnique mldrUnique) {
        MPDROracleUnique newUnique = MVCCDElementFactory.instance().createMPDROracleUnique(
                getMDRContConstraints(), mldrUnique);
        return newUnique;
    }


    @Override
    public  MPDRCheckSpecific createCheckSpecific(IMLDRSourceMPDRCConstraintSpecifc imldrSourceMPDRCConstraintSpecifc) {
        MPDROracleCheckSpecific newCheck = MVCCDElementFactory.instance().createMPDROracleCheckSpecific(
                getMDRContConstraints(), imldrSourceMPDRCConstraintSpecifc);
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

    //TODO-0 mettre des interfaces pour ne créer les procédures stockées que pour les BD qui le supporte
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

    @Override
    public MPDRBoxPackages getMPDRBoxPackages() {
        return getMPDRContTAPIs().getMPDRBoxPackages();
    }

    @Override
    public MPDRBoxPackages createBoxPackages(MLDRTable mldrTable) {
        MPDRBoxPackages mpdrBoxPackages = MVCCDElementFactory.instance().createMPDROracleBoxPackages(
                getMPDRContTAPIs(), mldrTable);
        return mpdrBoxPackages;

    }


    @Override
    public MPDRPackage getMPDRPackageByType(MPDRPackageType type) {
        return getMPDRBoxPackages().getMPDRPackageByType(type);
    }

    @Override
    public MPDRPackage createPackage(MPDRPackageType type, MLDRTable mldrTable) {
        MPDROracleBoxPackages mpdrOracleBoxPackages = (MPDROracleBoxPackages) getMPDRBoxPackages();
        if (mpdrOracleBoxPackages != null){
            MPDROraclePackage mpdrOraclePackage = MVCCDElementFactory.instance().createMPDROraclePackage(
                    mpdrOracleBoxPackages, mldrTable);
            return mpdrOraclePackage;
        } else {
            throw new CodeApplException("La boîte Procédures/Functions doit exister avant de créer un trigger");
        }

    }

    public ArrayList<MPDRPackage> getMPDRPackages(){
        if ( getMPDRBoxPackages() != null) {
            return getMPDRBoxPackages().getAllPackages();
        }
        return null;
    }

}

