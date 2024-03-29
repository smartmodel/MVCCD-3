package mpdr.oracle;

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
import mpdr.MPDRColumn;
import mpdr.MPDRPK;
import mpdr.MPDRTable;
import mpdr.oracle.interfaces.IMPDROracleElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
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
    public MDRConstraint createFK(MLDRFK mldrFK) {
        MPDROracleFK newFK = MVCCDElementFactory.instance().createMPDROracleFK(
                getMDRContConstraints(), mldrFK);
        return newFK;
    }

    @Override
    public MDRConstraint createUnique(MLDRUnique mldrUnique) {
        MPDROracleUnique newUnique = MVCCDElementFactory.instance().createMPDROracleUnique(
                getMDRContConstraints(), mldrUnique);
        return newUnique;
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

}

