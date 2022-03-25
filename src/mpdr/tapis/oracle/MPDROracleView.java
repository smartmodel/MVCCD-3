package mpdr.tapis.oracle;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import exceptions.CodeApplException;
import generatorsql.generator.oracle.MPDROracleGenerateSQL;
import generatorsql.generator.oracle.MPDROracleGenerateSQLView;
import main.MVCCDElementFactory;
import mldr.MLDRConstraintCustomSpecialized;
import mldr.interfaces.IMLDRElement;
import mpdr.oracle.MPDROracleColumnView;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.tapis.*;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MPDROracleView extends MPDRView implements IMPDROracleElement {

    private static final long serialVersionUID = 1000;

    public MPDROracleView(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROracleView(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public String generateSQLDDL() {
        MPDROracleGenerateSQL mpdrOracleGenerateSQL = new MPDROracleGenerateSQL(getMPDRModelParent());
        MPDROracleGenerateSQLView mpdrOracleGenerateSQLView = new MPDROracleGenerateSQLView(mpdrOracleGenerateSQL);
        return mpdrOracleGenerateSQLView.generateSQLCreateView(this);
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
    public MPDRBoxTriggers createBoxTriggers(MLDRConstraintCustomSpecialized mldrConstraintCustomSpecialized) {
        MPDROracleBoxTriggers mpdrOracleBoxTriggers = MVCCDElementFactory.instance().createMPDROracleBoxTriggers(
                this, mldrConstraintCustomSpecialized);
        return mpdrOracleBoxTriggers;
    }

    @Override
    public MPDRColumnView createColumnView() {
        MPDROracleColumnView newColumnView = MVCCDElementFactory.instance().createMPDROracleColumnView(
                getMDRContColumns());

        return newColumnView;
    }

    @Override
    public MPDRTrigger getMPDRTriggerByType(MPDRTriggerType type) {
        return null;
    }

    @Override
    public MPDRTrigger createTrigger(MPDRTriggerType type, IMLDRElement imldrElement) {
        MPDROracleBoxTriggers mpdrOracleBoxTriggers = (MPDROracleBoxTriggers) getMPDRBoxTriggers();
        if (mpdrOracleBoxTriggers != null){
            MPDROracleTrigger mpdrOracleTrigger = MVCCDElementFactory.instance().createMPDROracleTrigger(
                    getMPDRBoxTriggers(), imldrElement);
            return mpdrOracleTrigger;
        } else {
            throw new CodeApplException("La boîte Triggers doit exister avant de créer un trigger");
        }

    }
}

