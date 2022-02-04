package mpdr.tapis.postgresql;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import mldr.interfaces.IMLDRElement;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import mpdr.tapis.MPDRBoxProceduresOrFunctions;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MPDRPostgreSQLBoxProceduresOrFunctions extends MPDRBoxProceduresOrFunctions implements IMPDRPostgreSQLElement {
    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLBoxProceduresOrFunctions(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRPostgreSQLBoxProceduresOrFunctions(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPostgreSQLBoxProceduresOrFunctions(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
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
