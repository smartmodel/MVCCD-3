package mpdr.tapis;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import main.MVCCDElement;
import md.MDElement;
import mdr.MDRColumn;
import mdr.MDRTable;
import mdr.MDRTableOrView;
import mdr.interfaces.IMDRElementNamingPreferences;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.MLDRConstraintCustomSpecialized;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRFK;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.interfaces.IMPDRTableOrView;
import mpdr.tapis.interfaces.IMPDRWithDynamicCode;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MPDRView extends MDRTableOrView implements IMPDRElement, IMPDRElementWithSource,
        IMDRElementWithIteration, IMDRElementNamingPreferences, IMPDRWithDynamicCode, IMPDRTableOrView {

    private  static final long serialVersionUID = 1000;

    MPDRViewType type  ;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation
    private IMLDRElement mldrElementSource;

    public MPDRView(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRView(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRView(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, id);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRViewType getType() {
        return type;
    }

    public void setType(MPDRViewType type) {
        this.type = type;
    }

    @Override
    public Integer getIteration() {
        return iteration;
    }

    @Override
    public void setIteration(Integer iteration) {
        this.iteration = iteration;
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

    public MPDRContTAPIs  getMPDRContTAPIs (){
        return (MPDRContTAPIs) getParent();
    }


    public MPDRTable getMPDRTableAccueil(){
        return getMPDRContTAPIs().getMPDRTableAccueil();
    }

    public abstract String generateSQLDDL() ;


    public MPDRModel getMPDRModelParent(){
        return getMPDRTableAccueil ().getMPDRModelParent();
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(stereotypes.getStereotypeByLienProg(MDRTable.class.getName(),
                preferences.STEREOTYPE_VIEW_LIENPROG));

        return resultat;
    }

    @Override
    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();


        return resultat;
    }


    public MPDRBoxTriggers getMPDRBoxTriggers() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MPDRBoxTriggers){
                return (MPDRBoxTriggers) mvccdElement ;
            }
        }
        return null ;
    }

    public abstract MPDRBoxTriggers createBoxTriggers(MLDRConstraintCustomSpecialized mldrConstraintCustomSpecialized);

    public abstract MPDRColumnView createColumnView();


    public String getListColumnsRefOriginAsString(String separator){
        String resultat = "";
        // ! getMDRColumnsSortDefault() !
        // Le tri doit être le même que pour getListColumnsAsString de MPDRTableOrView
        // car cette liste permet de créer le mapping entre le nom des colonnes de vue
        // et les colonnes d'origine (au sein de la requête)
        for (MDRColumn mdrColumn : getMDRColumnsSortDefault()){
            if (StringUtils.isNotEmpty(resultat)){
                resultat =  resultat+ separator;
            }
            MPDRTable mpdrTableOrigin = ((MPDRColumnView)mdrColumn).getMpdrColumnAsQuerry().getMPDRTableAccueil();
           resultat = resultat + mpdrTableOrigin.getName() + Preferences.SQL_MARKER_NAMESPACE + mdrColumn.getName();
        }
        return resultat;
    }

    public MPDRFK getMDRFKToTableGen(){
        return getMPDRTableAccueil().getMPDRConstraintCustomSpecialized().getMPDRFKToTableGen();
    }

    // Depuis la table spécialisée jusqu'à la table généralisée origine ou racine
    public ArrayList<MPDRFK> getMDRFKToTablesGenInCascade(){
        ArrayList<MPDRFK> resultat = new ArrayList<MPDRFK>();
        resultat.add(getMDRFKToTableGen());
        resultat.addAll(getMDRFKToTablesGenInCascadeInternal(getMDRFKToTableGen()));

        return resultat;
    }

    private ArrayList<MPDRFK> getMDRFKToTablesGenInCascadeInternal(MPDRFK mpdrFKGen) {
        ArrayList<MPDRFK> resultat = new ArrayList<MPDRFK>();
        MPDRTable tablePK = (MPDRTable) mpdrFKGen.getMdrPK().getMDRTableAccueil() ;
        if (tablePK.getMPDRConstraintCustomSpecialized() != null){
            MPDRFK mpdrFKCascade = tablePK.getMPDRConstraintCustomSpecialized().getMPDRFKToTableGen();
            resultat.add(mpdrFKCascade);
            resultat.addAll(getMDRFKToTablesGenInCascadeInternal(mpdrFKCascade));
        }
        return resultat;
    }

    public MPDRTable getMPDRTableGenOrigin(){
        ArrayList<MPDRFK> mpdrFKToTablesGenInCascade = getMDRFKToTablesGenInCascade();
        MPDRFK mpdrFKToOrigin = mpdrFKToTablesGenInCascade.get(mpdrFKToTablesGenInCascade.size() -1);
        return (MPDRTable) mpdrFKToOrigin.getMdrPK().getMDRTableAccueil();
    }


    public ArrayList<MPDRColumn> getMDRColumnsInTable(MPDRTable mpdrTableIn) {
        ArrayList<MPDRColumn> resultat = new ArrayList<MPDRColumn>();
        for (MDRColumn mdrColumn : getMDRColumnsSortDefault()) {
            if (mdrColumn instanceof MPDRColumnView){
                MPDRColumnView mpdrColumnView = (MPDRColumnView) mdrColumn;
                MPDRColumn  mpdrColumnInTable = mpdrColumnView.getMpdrColumnAsQuerry() ;
                if (mpdrColumnInTable.getMPDRTableAccueil() == mpdrTableIn){
                    resultat.add(mpdrColumnInTable);
                }
            }
        }
        return resultat;
    }
}
