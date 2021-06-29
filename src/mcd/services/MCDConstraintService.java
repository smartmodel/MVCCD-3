package mcd.services;

import exceptions.CodeApplException;
import m.MRelEndMulti;
import m.MRelEndMultiPart;
import mcd.*;
import utilities.Trace;

import java.util.ArrayList;

public class MCDConstraintService {

    final static int HAUT = -1 ;
    final static int BAS = 1;

    public static ArrayList<MCDAttribute> getMcdAttributesMandatory(MCDConstraint mcdConstraint) {
        return getMcdAttributesMandatory (mcdConstraint, true);
    }

    public static ArrayList<MCDAttribute> getMcdAttributesOptionnal(MCDConstraint mcdConstraint) {
        return getMcdAttributesMandatory (mcdConstraint, false);
    }

    public static ArrayList<MCDAttribute> getMcdAttributesMandatory(MCDConstraint mcdConstraint, boolean mandatory) {
        ArrayList<MCDAttribute> resultat = new ArrayList<MCDAttribute>();
        for (MCDAttribute mcdAttribute: mcdConstraint.getMcdAttributes()){
            if (mcdAttribute.isMandatory() && mandatory ) {
                resultat.add(mcdAttribute);
            }
            if ( (!mcdAttribute.isMandatory()) && (!mandatory) ) {
                resultat.add(mcdAttribute);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAssEnd> getMcdAssEndsMandatory(MCDConstraint mcdConstraint) {
        return getMcdAssEndsMandatory(mcdConstraint, true);
    }

    public static ArrayList<MCDAssEnd> getMcdAssEndsOptionnal(MCDConstraint mcdConstraint) {
        return getMcdAssEndsMandatory(mcdConstraint, false);
    }

    public static ArrayList<MCDAssEnd> getMcdAssEndsMandatory(MCDConstraint mcdConstraint, boolean mandatory) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd: mcdConstraint.getMcdAssEnds()){
            boolean c1 = mcdAssEnd.getMCDAssEndOpposite().getMultiMinStd() == MRelEndMultiPart.MULTI_ONE;
            if (c1 && mandatory ) {
                resultat.add(mcdAssEnd);
            }
            if ( (!c1) && (!mandatory) ) {
                resultat.add(mcdAssEnd);
            }
        }
        return resultat;
    }

    public static int compareToDefault(MCDConstraint courant, MCDConstraint other) {

        if (courant instanceof MCDNID) {
            if (other instanceof MCDNID) {
                // Conservation de l'ordre par défaut de MVCCDElement
                return 0;
            } else {
                return HAUT;
            }
        } else if (courant instanceof MCDUnique) {
            if (other instanceof MCDNID){
                return BAS;
            } else if (other instanceof MCDUnique) {
                // Conservation de l'ordre par défaut de MVCCDElement
                return 0 ;
            } else {
                return HAUT;
            }
        } else {
            return HAUT;
        }

    }
}
