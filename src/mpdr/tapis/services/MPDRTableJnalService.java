package mpdr.tapis.services;

import main.MVCCDElement;
import mdr.MDRContColumns;
import mpdr.MPDRColumn;
import mpdr.tapis.MPDRColumnJnal;
import mpdr.tapis.MPDRColumnJnalDatas;
import mpdr.tapis.MPDRColumnJnalTech;
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

    public static ArrayList<MPDRColumnJnalTech> getMPDRColumnsJnalTech(MPDRTableJnal mpdrTableJnal) {
        ArrayList<MPDRColumnJnalTech> resultat = new ArrayList<MPDRColumnJnalTech>();
        for (MPDRColumnJnal mpdrColumnJnal : getMPDRColumnsJnal(mpdrTableJnal)){
            if (mpdrColumnJnal instanceof MPDRColumnJnalTech) {
                resultat.add((MPDRColumnJnalTech) mpdrColumnJnal);
            }
        }
        return resultat ;
    }

    public static MPDRColumnJnalTech getMPDRColumnJnalTechByStereotypeJnal(MPDRTableJnal mpdrTableJnal, Stereotype stereotypeJnal) {
        for (MPDRColumnJnalTech mpdrColumnJnalTech : getMPDRColumnsJnalTech(mpdrTableJnal)){
            if (mpdrColumnJnalTech.getSterereotypeJnal() == stereotypeJnal) {
                return mpdrColumnJnalTech;
            }
        }
        return null ;
    }


    public static ArrayList<MPDRColumnJnalDatas> getMPDRColumnsJnalDatas(MPDRTableJnal mpdrTableJnal) {
        ArrayList<MPDRColumnJnalDatas> resultat = new ArrayList<MPDRColumnJnalDatas>();
        for (MPDRColumnJnal mpdrColumnJnal : getMPDRColumnsJnal(mpdrTableJnal)){
            if (mpdrColumnJnal instanceof MPDRColumnJnalDatas) {
                resultat.add((MPDRColumnJnalDatas) mpdrColumnJnal);
            }
        }
        return resultat ;
    }

    public static MPDRColumnJnal getMPDRColumnJnalDatasByMPDRColumnSource(MPDRTableJnal mpdrTableJnal,
                                                                     MPDRColumn mpdrColumnSource) {
        for (MPDRColumnJnalDatas mpdrColumnJnalDatas : getMPDRColumnsJnalDatas(mpdrTableJnal)){
            if (mpdrColumnJnalDatas.getMpdrColumnSource() == mpdrColumnSource) {
                return mpdrColumnJnalDatas;
            }
        }
        return null ;
    }
}
