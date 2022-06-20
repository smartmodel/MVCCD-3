package mpdr.tapis;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import md.MDElement;
import mdr.MDRTable;
import mdr.MDRTableOrView;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRConstraintCustomJnal;
import mpdr.interfaces.IMPDRColumn;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.interfaces.IMPDRTable;
import mpdr.services.IMPDRColumnService;
import mpdr.tapis.services.MPDRTableJnalService;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MPDRTableJnal extends MDRTableOrView implements IMPDRElement, IMPDRElementWithSource, IMPDRTable {

    private  static final long serialVersionUID = 1000;

    private IMLDRElement mldrElementSource;

    public static final String OPERATION_INSERT = "INS";
    public static final String OPERATION_UPDATE = "UPD";
    public static final String OPERATION_DELETE = "DEL";

    public MPDRTableJnal(ProjectElement parent,
                         IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(stereotypes.getStereotypeByLienProg(MDRTable.class.getName(),
                preferences.STEREOTYPE_TABLE_LIENPROG));

        return resultat;
    }

    @Override
    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();


        return resultat;
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

    public MPDRColumnJnal getMPDRColumnJnalTechByStereotypeJnal(Stereotype stereotypeJnal) {
        return MPDRTableJnalService.getMPDRColumnJnalTechByStereotypeJnal(this, stereotypeJnal);
    }


    public MPDRColumnJnal getMPDRColumnJnalByMPDRColumnSource(MPDRColumn mpdrColumnSource) {
        return MPDRTableJnalService.getMPDRColumnJnalDatasByMPDRColumnSource(this, mpdrColumnSource);
    }

    public abstract MPDRColumnJnal createColumnJnalTech(MPDRConstraintCustomJnal mpdrConstraintCustomJnal, Stereotype stereotype);

    public abstract MPDRColumnJnal createColumnJnalDatas(MPDRColumn mpdrColumnSource);


    public ArrayList<IMPDRColumn> getIMPDRColumnsSortDefault() {
        return IMPDRColumnService.to(getMDRColumnsSortDefault());
    }

    public ArrayList<MPDRColumnJnal> getMPDRColumnsJnal() {
        return MPDRTableJnalService.getMPDRColumnsJnal(this);
    }

    public ArrayList<MPDRColumnJnalTech> getMPDRColumnsJnalTech() {
        return MPDRTableJnalService.getMPDRColumnsJnalTech(this);
    }

    public ArrayList<MPDRColumnJnalDatas> getMPDRColumnsJnalDatas() {
        return MPDRTableJnalService.getMPDRColumnsJnalDatas(this);
    }
}
