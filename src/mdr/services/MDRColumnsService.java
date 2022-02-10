package mdr.services;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import datatypes.MCDDatatype;
import exceptions.CodeApplException;
import mcd.MCDAttribute;
import mcd.MCDNID;
import mdr.MDRColumn;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MDRColumnsService {

    final static int HAUT = -1 ;
    final static int BAS = 1;
    final static int COMPARE_DEFAULT = 0 ;

    // A l'appel la colonne est bien PK et FK
    public static Integer getLevelForPK(MDRColumn columnRefPK) {
        return getLevelForPK(columnRefPK, 1);
    }


    private static Integer getLevelForPK(MDRColumn columnRefPK, int level) {
        if (!columnRefPK.isFk()) {
            return level;
        } else {
            return getLevelForPK(columnRefPK.getMDRColumnPK(), ++level);
        }
    }

/*
    public static ArrayList<MDRColumn> from(ArrayList<MVCCDElement> mvccdElements) {
        ArrayList<MDRColumn> resultat = new ArrayList<MDRColumn>();
        for (MVCCDElement mvccdElement : mvccdElements) {
            if (mvccdElement instanceof MDRColumn) {
                resultat.add((MDRColumn) mvccdElement);
            }
        }
        return resultat;
    }

 */


    public static int compareToDefault(MDRColumn courant, MDRColumn other) {
        return compareToInternal(courant, other, COMPARE_DEFAULT);
    }



    private static int compareToInternal(MDRColumn courant, MDRColumn other, int mode) {

        //Surcharge getChilds pour présenter :
        // 1. Les PFK
        // 2. La PK seules
        // 3. Les FK seules
        // 4. les colonnes non clés provenant d'attribut ou colonnes générées !

        if (courant.isPFk()) {
            if (other.isPFk()) {
                return compareFKs(courant, other);
            } else {
                return HAUT;
            }
        } else if (courant.isPkNotFk()){
            if (other.isPFk()) {
                return BAS;
            } else if (other.isPkNotFk()) {
                throw new CodeApplException("2 colonnes PK ne peuvent exister simultanément  :  " +
                        courant.getNameTreePath() + " et " + other.getNameTreePath());
            } else {
                return HAUT;
            }
        } else if (courant.isFk()) {
            if (other.isPFk() || other.isPkNotFk()){
                return BAS ;
            } else if (other.isFk()){
                return compareFKs(courant, other);
            } else {
                return HAUT;
            }
        } else {
            if (other.isPFk() || other.isPkNotFk() || other.isFk()) {
                return BAS;
            } else {
                return compareNotKey(courant, other, mode);
            }
        }
    }

    private static int compareFKs(MDRColumn courant, MDRColumn other) {
        // Les indices de FKs les plus petits en haut
        if (courant.getFk().getIndice() < other.getFk().getIndice()){
            return HAUT;
        } else if (courant.getFk().getIndice() == other.getFk().getIndice()){
            // Appel récursif en allant chercher le parent
            MDRColumn courantRefPK = courant.getMDRColumnPK();
            MDRColumn otherRefPK = other.getMDRColumnPK();
            if (! courantRefPK.isFk()){
                // PK seule après PFKs
                return BAS ;
            } else if (! otherRefPK.isFk()) {
                // PFK en premier
                return HAUT ;
            } else {
                return compareFKs(courantRefPK, otherRefPK);
            }
       } else {
            return BAS;
        }
    }


    private static int compareNotKey(MDRColumn courant, MDRColumn other, int mode) {
        if (courant.isFromMcdAttributeSource() ){
            if (other.isFromMcdAttributeSource()) {
                if (mode == COMPARE_DEFAULT) {
                    return courant.getMcdAttributeSource().compareToOrder(other.getMcdAttributeSource());
                } else {
                    return 0 ;
                }
            } else {
                return HAUT;
            }
        } else {
            return BAS;
        }
     }

    public static ArrayList<Constraint> getConstraints(MDRColumn mdrColumn) {

        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        Constraint constraintDatatype = getConstraintDatatype(mdrColumn);
        if (constraintDatatype != null) {
            resultat.add(constraintDatatype);
        }

        if (mdrColumn.isFrozen()){
            resultat.add(constraints.getConstraintByLienProg(MDRColumn.class.getName(),
                    preferences.CONSTRAINT_FROZEN_LIENPROG));
        }
        return resultat;

    }

    private static Constraint getConstraintDatatype(MDRColumn mdrColumn) {
        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        if (mdrColumn.isFromMcdAttributeSource()){
            // Colonne provenant d'un attribut
            MCDAttribute mcdAttributeSource = mdrColumn.getMcdAttributeSource();
            MCDDatatype mcdDatatypeSource = mcdAttributeSource.getMCDDatatype();
            return constraints.getConstraintByLienProg(MDRColumn.class.getName(),
                    mcdDatatypeSource.getLienProg());
        } else if (mdrColumn.isPk() || mdrColumn.isFk()){
            // Colonne PK générée à partir d'une entité sans attribut AID
            // Colonne FK issue de la transformation
            return constraints.getConstraintByLienProg(MDRColumn.class.getName(),
                    preferences.getMCD_AID_DATATYPE_LIENPROG());
        }
        return null;
    }

    public static ArrayList<MCDNID> partOfMCDNIds(MDRColumn mdrColumn) {
        ArrayList<MCDNID> resultat = new ArrayList<MCDNID>();
        if (mdrColumn.getMcdAttributeSource() != null){
            MCDAttribute mcdAttributeSource = (MCDAttribute) mdrColumn.getMcdAttributeSource();
            resultat.addAll(mcdAttributeSource.partOfNIds());
        }
        return resultat;
    }
}
