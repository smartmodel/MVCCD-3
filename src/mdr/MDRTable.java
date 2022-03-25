package mdr;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import exceptions.CodeApplException;
import main.MVCCDElement;
import mdr.services.MDRTableService;
import mpdr.MPDRCheck;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MDRTable extends MDRTableOrView {

    private  static final long serialVersionUID = 1000;

    public MDRTable(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MDRTable(ProjectElement parent) {
        super(parent);
    }



    public MDRPK getMDRPK(){
        ArrayList<MDRPK> resultat = new ArrayList<MDRPK>();
        for (MDRConstraint mdrConstraint : getMDRConstraints()){
            if (mdrConstraint instanceof MDRPK){
                resultat.add ((MDRPK) mdrConstraint);
            }
        }
        if (resultat.size() == 0){
            return null ;
        }
        if (resultat.size() == 1){
            return resultat.get(0) ;
        }
        throw new CodeApplException("La table  " + this.getNamePath() + "retourne : " + resultat.size() + " PK.");
    }

    public MDRConstraintCustomSpecialized getMDRConstraintCustomSpecialized(){
        ArrayList<MDRConstraintCustomSpecialized> resultat = new ArrayList<MDRConstraintCustomSpecialized>();
        for (MDRConstraint mdrConstraint : getMDRConstraints()){
            if (mdrConstraint instanceof MDRConstraintCustomSpecialized){
                resultat.add ((MDRConstraintCustomSpecialized) mdrConstraint);
            }
        }
        if (resultat.size() == 0){
            return null ;
        }
        if (resultat.size() == 1){
            return resultat.get(0) ;
        }
        throw new CodeApplException("La table  " + this.getNamePath() + "retourne : " + resultat.size() + " ConstraintCustomSpcialized.");
    }



    public ArrayList<MDRFK> getMDRFKs(){
        ArrayList<MDRFK> resultat = new ArrayList<MDRFK>();
        for (MDRConstraint mdrConstraint : getMDRConstraints()){
            if (mdrConstraint instanceof MDRFK){
                resultat.add ((MDRFK) mdrConstraint);
            }
        }
        return resultat;
    }


    public ArrayList<MDRFK> getMDRFKsIdComp(){
        ArrayList<MDRFK> resultat = new ArrayList<MDRFK>();
        for (MDRFK mdrFK : getMDRFKs()){
            if (mdrFK.getNature() == MDRFKNature.IDCOMP){
                resultat.add (mdrFK);
            }
        }
        return resultat;
    }

    public int getNbFKsIdComp(){
        return getMDRFKsIdComp().size();
    }

    public ArrayList<MDRTable> getMDRTablesIdComp() {
        ArrayList<MDRTable> resultat = new ArrayList<MDRTable>();
        for (MDRFK mdrFK : getMDRFKsIdComp()) {
            MDRPK mdrPK = mdrFK.getMdrPK();
            MDRTable mdrTablePK = mdrPK.getMDRTableAccueil();
            if (!resultat.contains(mdrTablePK)) {
                resultat.add(mdrTablePK);
            }
        }
        return resultat;
    }



    public ArrayList<MDRFK> getMDRFKsIdNat(){
        ArrayList<MDRFK> resultat = new ArrayList<MDRFK>();
        for (MDRFK mdrFK : getMDRFKs()){
            if (mdrFK.getNature() == MDRFKNature.IDNATURAL){
                resultat.add (mdrFK);
            }
        }
        return resultat;
    }

    public ArrayList<MDRColumn> getMDRColumnsPK() {
        // Pendant la phase de développement les contraintes ne sont pas finalisées
        if (getMDRPK() != null) {
            return getMDRPK().getMDRColumns();
        } else {
            return new ArrayList<MDRColumn>();
        }
    }

    public ArrayList<MDRColumn> getMDRColumnsFK() {
        ArrayList<MDRColumn>  resultat = new ArrayList<MDRColumn>();
        for (MDRFK mdrfk : getMDRFKs()){
            resultat.addAll(mdrfk.getMDRColumns());
        }
        return resultat;
    }


    public ArrayList<MDRColumn> getMDRColumnsFKIdNat() {
        ArrayList<MDRColumn>  resultat = new ArrayList<MDRColumn>();
        for (MDRFK mdrfkIdNat : getMDRFKsIdNat()){
            resultat.addAll(mdrfkIdNat.getMDRColumns());
        }
        return resultat;
    }

    public ArrayList<MDRColumn> getMDRColumnsPFK() {
        ArrayList<MDRColumn> mdrColumnsPFK = (ArrayList<MDRColumn>) getMDRColumnsPK().clone();
        mdrColumnsPFK.retainAll(getMDRColumnsFK());
        return mdrColumnsPFK;

    }

    public MDRColumn getMDRColumnPKProper() {
        ArrayList<MDRColumn> mdrColumnsPKProper = (ArrayList<MDRColumn>) getMDRColumnsPK().clone();
        mdrColumnsPKProper.removeAll(getMDRColumnsPFK());
        if (mdrColumnsPKProper.size() == 0){
            return null;
        }
        if (mdrColumnsPKProper.size() == 1){
            return mdrColumnsPKProper.get(0) ;
        }
        throw new CodeApplException("La table  " + this.getNamePath() + "retourne : " + mdrColumnsPKProper.size() + " colonnes PK propres.");

    }


    //TODO-0 A voir !!!  Erreur?
    //public abstract String getShortName();


    public MDRModel getMDRModelParent(){
        return (MDRModel) getParent().getParent();
    }


    public MDRContRelEnds getMDRContRelEnds() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MDRContRelEnds) {
                return (MDRContRelEnds) mvccdElement;
            }
        }
        return null;
    }

    /**
     * Parcourt les colonnes de la table et recherche la colonne portant l'id donné en paramètre.
     * @param id Id utilisé pour effectuer la recherche.
     * @return Retourne la colonne trouvée. Retourne null si aucune colonne n'est trouvée.
     */
    public MDRColumn getMDRColumnById(int id){
        for(MDRColumn mdrColumn : this.getMDRColumns()){
            if(mdrColumn.getIdProjectElement() == id){
                return mdrColumn;
            }
        }
        return null;
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

    public MDRTableNature getNature() {
        return MDRTableService.getNature(this);
    }

    public boolean  isIndependant() {
        return MDRTableService.getNature(this) == MDRTableNature.IND;
    }

    public boolean  isDependant() {
        return MDRTableService.getNature(this) == MDRTableNature.DEP;
    }

    public boolean  isSpecialized() {
        return MDRTableService.getNature(this) == MDRTableNature.SPEC;
    }

    public boolean  isAssociative() {
        return MDRTableService.getNature(this) == MDRTableNature.ASS;
    }

    public boolean  isAssociativeDep() {
        return MDRTableService.getNature(this) == MDRTableNature.ASSDEP;
    }

    public boolean  isNAire() {
        return MDRTableService.getNature(this) == MDRTableNature.NAIRE;
    }

    public boolean  isNAireDep() {
        return MDRTableService.getNature(this) == MDRTableNature.NAIREDEP;
    }


    public boolean  isKindDependant() {
        ArrayList<MDRTableNature> kindDependant = new ArrayList<MDRTableNature>() ;
        kindDependant.add(MDRTableNature.DEP);
        kindDependant.add(MDRTableNature.ASSDEP);
        kindDependant.add(MDRTableNature.NAIREDEP);
        return kindDependant.contains(getNature()) ;
    }

    public ArrayList<MPDRCheck> getMPDRChecks(){
        ArrayList<MPDRCheck> resultat = new ArrayList<MPDRCheck>();
        for (MDRCheck mdrCheck : getMDRContConstraints().getMDRChecks()) {
            if (mdrCheck instanceof MPDRCheck) {
                resultat.add((MPDRCheck) mdrCheck);
            }
        }
        return resultat;
    }

}
