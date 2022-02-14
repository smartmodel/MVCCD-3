package mpdr;

import constraints.Constraint;
import md.MDElement;
import mdr.MDRConstraint;
import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRConstraint;
import mpdr.interfaces.IMPDRConstraintSpecific;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.services.MPDRIndexService;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MPDRIndex extends MDRConstraint implements IMPDRElement, IMPDRElementWithSource,
        IMPDRConstraint, IMPDRConstraintSpecific {

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    private MPDRConstraintSpecificRole role = null ;

    public MPDRIndex(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRIndex(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRIndex(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, id);
        this.mldrElementSource = mldrElementSource;
    }

    @Override
    public IMLDRElement getMldrElementSource() {
        return mldrElementSource;
    }

    @Override
    public void setMldrElementSource(IMLDRElement imldrElementSource) {
        this.mldrElementSource = mldrElementSource;
    }

    @Override
    public MDElement getMdElementSource() {
        return (MDElement) getMldrElementSource();
    }

    public MPDRConstraintSpecificRole getRole() {
        return role;
    }

    public void setRole(MPDRConstraintSpecificRole role) {
        this.role = role;
    }

    @Override
    public Stereotype getDefaultStereotype() {
        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();
        return stereotypes.getStereotypeByLienProg(MPDRIndex.class.getName(),
                preferences.STEREOTYPE_INDEX_LIENPROG);
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

    public ArrayList<MPDRColumn> getMPDRColumns(){
        return MPDRIndexService.getMPDRColumns(this);
    }

}
