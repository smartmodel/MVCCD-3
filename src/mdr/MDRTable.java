package mdr;

import exceptions.CodeApplException;
import mcd.MCDAttribute;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.MCDRelation;
import mcd.services.MCDElementService;
import mdr.interfaces.IMDRElementNamingPreferences;
import mdr.interfaces.IMDRElementWithSource;
import mdr.interfaces.IMDRParameter;
import mldr.MLDRColumn;
import mldr.MLDRFK;
import mldr.MLDRPK;
import mldr.MLDRTable;
import org.apache.commons.lang.StringUtils;
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

}
