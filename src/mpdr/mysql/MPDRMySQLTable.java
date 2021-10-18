package mpdr.mysql;

import constraints.Constraint;
import constraints.ConstraintService;
import constraints.Constraints;
import constraints.ConstraintsManager;
import main.MVCCDElementFactory;
import mdr.MDRConstraint;
import mldr.MLDRColumn;
import mldr.MLDRFK;
import mldr.MLDRPK;
import mldr.MLDRUnique;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRPK;
import mpdr.MPDRTable;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MPDRMySQLTable extends MPDRTable {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLTable(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRMySQLTable(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRMySQLTable(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRMySQLColumn createColumn(MLDRColumn mldrColumn) {
        MPDRMySQLColumn newColumn = MVCCDElementFactory.instance().createMPDRMySQLColumn(
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
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = super.getStereotypes();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(stereotypes.getStereotypeByLienProg(this.getClass().getName(),
                preferences.STEREOTYPE_MYSQL_LIENPROG));

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
