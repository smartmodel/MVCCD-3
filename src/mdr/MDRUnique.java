package mdr;

import constraints.Constraint;
import constraints.ConstraintService;
import mcd.MCDAttribute;
import mcd.MCDNID;
import mcd.MCDUnicity;
import mdr.services.MDRColumnsService;
import mdr.services.MDRFKService;
import mdr.services.MDRUniqueService;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MDRUnique extends MDRConstraint {

    private  static final long serialVersionUID = 1000;

    private MDRUniqueNature mdrUniqueNature;
    private boolean absolute = false ;
    private boolean frozen = false ;


    public MDRUnique(ProjectElement parent) {
        super(parent);
    }

    public MDRUnique(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MDRUniqueNature getMdrUniqueNature() {
        return mdrUniqueNature;
    }

    public void setMdrUniqueNature(MDRUniqueNature mdrUniqueNature) {
        this.mdrUniqueNature = mdrUniqueNature;
    }

    public boolean isAbsolute() {
        return absolute;
    }

    public void setAbsolute(boolean absolute) {
        this.absolute = absolute;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public int compareToDefault(MDRUnique other) {
        return MDRUniqueService.compareToDefault(this,  other);
    }

    public abstract MCDUnicity getMcdUnicitySource();

    public boolean isFromMcdUnicitySource(){
        return getMcdUnicitySource() != null;
    }

    public  Stereotype getDefaultStereotype(){
        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();
        return stereotypes.getStereotypeByLienProg(MDRUnique.class.getName(),
                preferences.STEREOTYPE_U_LIENPROG);
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(getDefaultStereotype());
        return resultat;
    }


    @Override
    public ArrayList<Constraint> getConstraints() {
        return new ArrayList<Constraint>();
    }

}

