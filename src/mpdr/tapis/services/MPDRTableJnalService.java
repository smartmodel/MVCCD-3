package mpdr.tapis.services;

import main.MVCCDElement;
import mdr.MDRContColumns;
import mpdr.MPDRColumn;
import mpdr.tapis.MPDRColumnJnal;
import mpdr.tapis.MPDRTableJnal;
import stereotypes.Stereotype;

import java.util.ArrayList;

public class MPDRTableJnalService {



    public static MDRContColumns getMDRContColumns(MPDRTableJnal mpdrTableJnal) {
        for (MVCCDElement mvccdElement : mpdrTableJnal.getChilds()){
            if (mvccdElement instanceof MDRContColumns){
                return (MDRContColumns) mvccdElement ;
            }
        }
        return null ;
    }


    public static ArrayList<MPDRColumnJnal> getMPDRColumnsJnal(MPDRTableJnal mpdrTableJnal){
        ArrayList<MPDRColumnJnal> resultat = new ArrayList<MPDRColumnJnal>();
        MDRContColumns mdrContColumns = getMDRContColumns(mpdrTableJnal);
        for (MVCCDElement mvccdElement: mdrContColumns.getChilds()){
            if (mvccdElement instanceof MPDRColumnJnal){
                resultat.add((MPDRColumnJnal) mvccdElement);
            }
        }
        return resultat;
    }

    public static MPDRColumnJnal getMPDRColumnJnalByStereotypeJnal(MPDRTableJnal mpdrTableJnal, Stereotype stereotypeJnal) {
        for (MPDRColumnJnal mpdrColumnJnal : getMPDRColumnsJnal(mpdrTableJnal)){
                if (mpdrColumnJnal.getSterereotypeJnal() == stereotypeJnal) {
                    return mpdrColumnJnal;
                }
        }
        return null ;
    }

    public static MPDRColumnJnal getMPDRColumnJnalByMPDRColumnSource(MPDRTableJnal mpdrTableJnal,
                                                                     MPDRColumn mpdrColumnSource) {
        for (MPDRColumnJnal mpdrColumnJnal : getMPDRColumnsJnal(mpdrTableJnal)){
            if (mpdrColumnJnal.getMpdrColumnSource() != null ){
                if (mpdrColumnJnal.getMpdrColumnSource() == mpdrColumnSource) {
                    return (MPDRColumnJnal) mpdrColumnJnal;
                }
            }
        }
        return null ;
    }
}
