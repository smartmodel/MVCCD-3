package mpdr.postgresql;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import main.MVCCDElementFactory;
import mdr.MDRConstraint;
import mldr.*;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRPK;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRBoxTriggers;
import mpdr.tapis.MPDRTrigger;
import mpdr.tapis.MPDRTriggerType;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MPDRPostgreSQLTable extends MPDRTable {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLTable(ProjectElement parent,  IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPostgreSQLTable(ProjectElement parent,  IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRColumn createColumn(MLDRColumn mldrColumn) {
        MPDRPostgreSQLColumn newColumn = MVCCDElementFactory.instance().createMPDRPostgreSQLColumn(
                getMDRContColumns(),  mldrColumn);

        return newColumn;
    }

    @Override
    public MPDRPK createPK(MLDRPK mldrPK) {
        return null;
    }

    @Override
    public MDRConstraint createFK(MLDRFK mldrFK) {
        return null;
    }

    @Override
    public MDRConstraint createUnique(MLDRUnique mldrUnique) {
        return null;
    }

    @Override
    public MPDRBoxTriggers createBoxTriggers(MLDRTable mldrTable) {
        return null;
    }

    @Override
    public MPDRTrigger createTrigger(MPDRTriggerType mpdrTriggerType, MLDRTable mldrTable) {
        return null;
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = super.getStereotypes();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(stereotypes.getStereotypeByLienProg(this.getClass().getName(),
                preferences.STEREOTYPE_POSTGRESQL_LIENPROG));

        return resultat;
    }

    @Override
    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = super.getConstraints();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }

}
