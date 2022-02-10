package mpdr.mysql;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import main.MVCCDElementFactory;
import mldr.*;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCConstraintSpecifc;
import mpdr.*;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import mpdr.tapis.*;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MPDRMySQLTable extends MPDRTable implements IMPDRMySQLElement {

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
        MPDRMySQLPK newPK = MVCCDElementFactory.instance().createMPDRMySQLPK(
                getMDRContConstraints(), mldrPK);
        return newPK;
    }

    @Override
    public MPDRFK createFK(MLDRFK mldrFK) {
        MPDRMySQLFK newFK = MVCCDElementFactory.instance().createMPDRMySQLFK(
                getMDRContConstraints(), mldrFK);
        return newFK;
    }

    @Override
    public MPDRIndex createIndex(MLDRFK mldrFK) {
        MPDRMySQLIndex newIndex = MVCCDElementFactory.instance().createMPDRMySQLIndex(
                getMDRContConstraints(), mldrFK);
        return newIndex;
    }

    @Override
    public MPDRUnique createUnique(MLDRUnique mldrUnique) {
        MPDRMySQLUnique newUnique = MVCCDElementFactory.instance().createMPDRMySQLUnique(
            getMDRContConstraints(), mldrUnique);
        return newUnique;
    }


    @Override
    public  MPDRCheckSpecific createCheckSpecific(IMLDRSourceMPDRCConstraintSpecifc imldrSourceMPDRCConstraintSpecifc) {
        MPDRMySQLCheckSpecific newCheck = MVCCDElementFactory.instance().createMPDRMySQLCheckSpecific(
                getMDRContConstraints(), imldrSourceMPDRCConstraintSpecifc);
        return newCheck;
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

    @Override
    public MPDRFunction createFunction(MPDRFunctionType type, MLDRTable mldrTable) {
        return null;
    }

}
