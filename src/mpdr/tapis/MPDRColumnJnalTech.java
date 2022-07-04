package mpdr.tapis;

import mpdr.MPDRConstraintCustomJnal;
import mpdr.tapis.services.MPDRColumnsJnalTechService;
import project.ProjectElement;
import stereotypes.Stereotype;

import java.util.ArrayList;

public abstract class MPDRColumnJnalTech extends MPDRColumnJnal{

    private static final long serialVersionUID = 1000;

    Stereotype sterereotypeJnal  ;

    // Constructeur pour les données techniques JN_xxx
    public MPDRColumnJnalTech(ProjectElement parent,
                              MPDRConstraintCustomJnal mpdrConstraintCustomJnal,
                              Stereotype stereotypeJnal) {
        super(parent, mpdrConstraintCustomJnal);
        this.sterereotypeJnal = stereotypeJnal;
    }
    public Stereotype getSterereotypeJnal() {
        return sterereotypeJnal;
    }

    public ArrayList<Stereotype> getStereotypes(){
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();
        resultat.add(sterereotypeJnal);
        return resultat;
    }

    public int compareToDefault(MPDRColumnJnalTech other) {
        return MPDRColumnsJnalTechService.compareToDefault(this, other);
    }

}
