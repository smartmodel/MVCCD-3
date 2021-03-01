package mdr;

import exceptions.CodeApplException;
import main.MVCCDElement;
import mdr.interfaces.IMDRElementNamingPreferences;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRTable extends MDRTableOrView implements IMDRElementNamingPreferences {

    private  static final long serialVersionUID = 1000;

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
        throw new CodeApplException("La table  " + this.getName() + "retourne : " + resultat.size() + " PK.");
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

    public ArrayList<MDRColumn> getMDRColumnsPK() {

        return getMDRPK().getMDRColumns();
    }

    public ArrayList<MDRColumn> getMDRColumnsFK() {
        ArrayList<MDRColumn>  resultat = new ArrayList<MDRColumn>();
        for (MDRFK mdrfk : getMDRFKs()){
            resultat.addAll(mdrfk.getMDRColumns());
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
        throw new CodeApplException("La table  " + this.getName() + "retourne : " + mdrColumnsPKProper.size() + " Pcolonnes PK propres.");

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
}
