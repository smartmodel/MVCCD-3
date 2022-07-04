package mpdr.services;

import exceptions.CodeApplException;
import main.MVCCDElement;
import mdr.MDRElement;
import mldr.MLDRTable;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRRelation;
import mpdr.MPDRContRelations;
import mpdr.MPDRContTables;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.interfaces.IMPDRRelation;

import java.util.ArrayList;

public class MPDRModelService {


    public static MPDRContTables getMPDRContTables(MPDRModel mpdrModel) {
        for (MVCCDElement mvccdElement : mpdrModel.getChilds()){
            if (mvccdElement instanceof MPDRContTables){
                return (MPDRContTables) mvccdElement ;
            }
        }
        return null ;

    }

    public static ArrayList<MPDRTable> getMPDRTables(MPDRModel mpdrModel){
        ArrayList<MPDRTable> resultat = new ArrayList<MPDRTable>();
        MPDRContTables mpdrContTables = getMPDRContTables(mpdrModel);
        for (MVCCDElement mvccdElement: mpdrContTables.getChilds()){
            if (mvccdElement instanceof MPDRTable){
                resultat.add((MPDRTable) mvccdElement);
            }
        }
        return resultat;
    }


    public static MPDRTable getMPDRTableByMLDRTableSource(MPDRModel mpdrModel, MLDRTable mldrTable) {
        for (MPDRTable mpdrTable : getMPDRTables(mpdrModel)){
            if (mpdrTable.getMldrElementSource() == mldrTable){
                return mpdrTable;
            }
        }
        return null ;
    }

    public static IMPDRElement getIMPDRElementByIMLDRElementSource(MPDRModel mpdrModel, IMLDRElement imldrElement) {

        for (MDRElement mdrElement : mpdrModel.getMDRDescendants()){
            if (mdrElement instanceof IMPDRElementWithSource) {
                IMPDRElementWithSource impdrElement = (IMPDRElementWithSource) mdrElement;
                if (impdrElement.getMldrElementSource() == imldrElement) {
                    if ( mdrElement instanceof IMPDRElement) {
                        return (IMPDRElement) mdrElement;
                    }
                }
            }
        }
        return null ;
    }

    public static MPDRContRelations getMPDRContRelations(MPDRModel mpdrModel) {
        for (MVCCDElement mvccdElement : mpdrModel.getChilds()){
            if (mvccdElement instanceof MPDRContRelations){
                return (MPDRContRelations) mvccdElement ;
            }
        }
        return null ;
    }


    public static ArrayList<IMPDRRelation> getIMPDRRelations(MPDRModel mpdrModel){
        ArrayList<IMPDRRelation> resultat = new ArrayList<IMPDRRelation>();
        MPDRContRelations mpdrContRelations = getMPDRContRelations(mpdrModel);
        for (MVCCDElement mvccdElement: mpdrContRelations.getChilds()){
            if (mvccdElement instanceof MPDRTable){
                resultat.add((IMPDRRelation) mvccdElement);
            }
        }
        return resultat;
    }


    public static IMPDRRelation getIMPDRRelationByIMLDRRelationSource(MPDRModel mpdrModel,
                                                                      IMLDRRelation imldrRelation) {
        for (IMPDRRelation impdrRelation : getIMPDRRelations(mpdrModel)){
            if ( impdrRelation.getMldrElementSource()  == imldrRelation){
                return impdrRelation;
            }
        }
        return null;
    }

/*
    public static MPDRModel getMPDRModelParent(IMPDRElement impdrElement) {
        Trace.println(((MVCCDElement) impdrElement).getName());
        if (impdrElement instanceof MPDRModel){
            return (MPDRModel) impdrElement;
        } else {
            MVCCDElement mvccdElementParent = ((MVCCDElement) impdrElement).getParent();
            if (mvccdElementParent instanceof IMPDRElement){
                return getMPDRModelParent((IMPDRElement) mvccdElementParent);
            } else {
                throw new CodeApplException("Le modèle (MPDR) d'accueil n'est pas trouvé");
            }
        }
    }

 */

    public static MPDRModel getMPDRModelParent(MVCCDElement mvccdElement) {
        if (mvccdElement instanceof MPDRModel){
            return (MPDRModel) mvccdElement;
        } else {
            MVCCDElement mvccdElementParent = mvccdElement.getParent();
            if (mvccdElementParent != null){
                return getMPDRModelParent(mvccdElementParent);
            } else {
                throw new CodeApplException("Le modèle (MPDR) d'accueil n'est pas trouvé");
            }
        }
    }
}
