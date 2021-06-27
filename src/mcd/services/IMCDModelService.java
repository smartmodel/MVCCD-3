package mcd.services;

import m.MRelationDegree;
import main.MVCCDElement;
import mcd.*;
import mcd.interfaces.IMCDContPackages;
import mcd.interfaces.IMCDModel;
import mldr.MLDRModelDT;
import mldr.MLDRModelTI;
import utilities.Trace;

import java.util.ArrayList;

public class IMCDModelService {

    /*

    Remplacé par MCDElement.getIMCDModelAccueil()

    public static IMCDModel getIMCDModelContainer(MCDElement mcdElement) {
        IMCDModel resultat = null;
        if ( mcdElement instanceof IMCDModel){
            resultat = ((IMCDModel) mcdElement);
        } else {
            if (mcdElement.getParent() != null) {
                resultat = getIMCDModelContainer((MCDElement) mcdElement.getParent());
            }
        }
        return resultat;
    }

     */

    //#MAJ 2021-01-09 Suppression de MCDElement.getMCDElements()
    /*
    public static ArrayList<MCDElement> getMCDElements(IMCDModel imcdModel) {
        return ((MCDElement) imcdModel).getMCDElements();
    }

     */


    public static ArrayList<IMCDContPackages> getIMCDContPackages(IMCDModel imcdModel){
        ArrayList<IMCDContPackages>  resultat = new ArrayList<IMCDContPackages>() ;
        //#MAJ 2021-01-09 Suppression de MCDElement.getMCDElements()
        //for (MCDElement mcdElement : getMCDElements(imcdModel)){
        for (MCDElement mcdElement : imcdModel.getMCDDescendants()){
            if (mcdElement instanceof IMCDContPackages){
                resultat.add((IMCDContPackages) mcdElement);
            }
        }
        return resultat;
    }


    public static  ArrayList<MCDElement> getMCDElementsByClassName(IMCDModel imcdModel,
                                                                   boolean withModel,
                                                                   String className){
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();
        if (withModel){
            resultat.add((MCDElement) imcdModel);
        }
        resultat.addAll(getMCDElementsByClassNameInternal((MCDElement) imcdModel, className));
        return resultat;
    }

    private static  ArrayList<MCDElement> getMCDElementsByClassNameInternal(MCDElement root,
                                                                            String className){
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();

        for (MVCCDElement element :  root.getChilds()){
            if (element instanceof MCDElement){
                if (element.getClass().getName().equals(className)){
                    resultat.add((MCDElement) element);
                } else if (element.implementsInterface(className)){
                    resultat.add((MCDElement) element);
                }
                resultat.addAll(getMCDElementsByClassNameInternal((MCDElement) element, className));
            }
        }
        return resultat;
    }


    public static boolean existMCDElement(IMCDModel imcdModel, MCDElement mcdElement){

        //#MAJ 2021-01-09 Suppression de MCDElement.getMCDElements()
        //for ( MCDElement aMCDElement : getMCDElements(imcdModel)){
        for ( MCDElement aMCDElement : imcdModel.getMCDDescendants()){
            if (aMCDElement == mcdElement){
               return true;
            }
        }
        return false;
    }

    public static ArrayList<MCDEntity> getMCDEntities(IMCDModel imcdModel){
        ArrayList<MCDEntity>  resultat = new ArrayList<MCDEntity>() ;
        //#MAJ 2021-01-09 Suppression de MCDElement.getMCDElements()
        //for (MCDElement aMCDElement : getMCDElements(imcdModel)){
        for ( MCDElement aMCDElement : imcdModel.getMCDDescendants()){
            if (aMCDElement instanceof MCDEntity){
                resultat.add((MCDEntity) aMCDElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDEntity> getMCDEntitiesConcrets(IMCDModel imcdModel){
        ArrayList<MCDEntity>  resultat = new ArrayList<MCDEntity>() ;
        for (MCDEntity aMCDEntity : getMCDEntities(imcdModel)){
            if ( aMCDEntity.getNature() != MCDEntityNature.PSEUDOENTASS){
                resultat.add((MCDEntity) aMCDEntity);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDEntity> getMCDEntitiesIndependants(IMCDModel imcdModel) {
        ArrayList<MCDEntity>  resultat = new ArrayList<MCDEntity>() ;
        for (MCDEntity aMCDEntity : getMCDEntities(imcdModel)){
            if ( aMCDEntity.getNature() == MCDEntityNature.IND){
                resultat.add((MCDEntity) aMCDEntity);
            }
        }
        return resultat;
    }

    public static MCDEntity getMCDEntityByNamePath(IMCDModel imcdModel,
                                                   String namePath){
        return (MCDEntity) IMCDModelService.getMCDElementByClassAndNamePath(imcdModel, false,
                MCDEntity.class.getName(), namePath);
    }


    public static ArrayList<MCDEntity> getMCDEntitiesConcretsNoInd(IMCDModel imcdModel) {
        ArrayList<MCDEntity>  resultat = new ArrayList<MCDEntity>() ;
        for (MCDEntity aMCDEntity : getMCDEntities(imcdModel)){
            if (    (aMCDEntity.getNature() == MCDEntityNature.DEP) ||
                    (aMCDEntity.getNature() == MCDEntityNature.ENTASS) ||
                    (aMCDEntity.getNature() == MCDEntityNature.ENTASSDEP) ||
                    (aMCDEntity.getNature() == MCDEntityNature.NAIRE) ||
                    (aMCDEntity.getNature() == MCDEntityNature.NAIREDEP) ||
                    (aMCDEntity.getNature() == MCDEntityNature.SPEC) ){
                resultat.add((MCDEntity) aMCDEntity);
            }
        }
        return resultat;
    }


    public static ArrayList<MCDUnicity> getAllMCDUnicitiesInModel (IMCDModel imcdModel){
        ArrayList<MCDUnicity> resultat = new ArrayList<MCDUnicity>();
        for (MCDEntity aMCDEntity : getMCDEntities(imcdModel)) {
            for (MCDUnicity aMCDUnicity : aMCDEntity.getMCDUnicities()) {
                resultat.add(aMCDUnicity);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDNID> getAllMCDNIDsInModel (IMCDModel imcdModel){
        ArrayList<MCDNID> resultat = new ArrayList<MCDNID>();
        for (MCDEntity aMCDEntity : getMCDEntities(imcdModel)) {
            for (MCDNID aMCDNID : aMCDEntity.getMCDNIDs()) {
                resultat.add(aMCDNID);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDUnique> getAllMCDUniquesInModel (IMCDModel imcdModel){
        ArrayList<MCDUnique> resultat = new ArrayList<MCDUnique>();
        for (MCDEntity aMCDEntity : getMCDEntities(imcdModel)) {
            for (MCDUnique aMCDUnique : aMCDEntity.getMCDUniques()) {
                resultat.add(aMCDUnique);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDRelation> getAllMCDRelationsInIModel(IMCDModel imcdModel){
        ArrayList<MCDRelation> resultat = new ArrayList<MCDRelation>();
        for (MCDElement mcdElement : getMCDElementsByClassName(imcdModel, false, MCDRelation.class.getName())){
            resultat.add ((MCDRelation) mcdElement);
        }
        return resultat;
    }

    public static ArrayList<MCDAssociation> getAllMCDAssociationsInIModel(IMCDModel imcdModel){
        ArrayList<MCDAssociation> resultat = new ArrayList<MCDAssociation>();
        for (MCDElement mcdElement : getMCDElementsByClassName(imcdModel, false, MCDAssociation.class.getName())){
            resultat.add ((MCDAssociation) mcdElement);
        }
        return resultat;
    }

    //#MAJ 2021-06-21 IMCDModelService - getMCDAssociationNoIdOrIdNatural remplacé par getMCDAssociationNotIdCompAndNotNN
/*
    public static ArrayList<MCDAssociation> getMCDAssociationNoIdOrIdNatural(IMCDModel imcdModel) {
        ArrayList<MCDAssociation> resultat = new ArrayList<MCDAssociation>();
        for (MCDElement mcdElement : getAllMCDAssociationsInIModel(imcdModel)){
            MCDAssociation mcdAssociation = (MCDAssociation) mcdElement;
            boolean c1 = mcdAssociation.isIdNatural();
            boolean c2 = mcdAssociation.isNoId();
            boolean c3 = mcdAssociation.getDegree()  != MRelationDegree.DEGREE_MANY_MANY;
            if (c1 || (c2 && c3)) {
                resultat.add(mcdAssociation);
            }
        }
        return resultat;
    }

 */

    public static ArrayList<MCDAssociation> getMCDAssociationNotIdCompAndNotNN(IMCDModel imcdModel) {
        ArrayList<MCDAssociation> resultat = new ArrayList<MCDAssociation>();
        for (MCDElement mcdElement : getAllMCDAssociationsInIModel(imcdModel)){
            MCDAssociation mcdAssociation = (MCDAssociation) mcdElement;
            boolean c1 = ! mcdAssociation.isIdComp();
            boolean c2 = mcdAssociation.getDegree()  != MRelationDegree.DEGREE_MANY_MANY;
            if (c1 && c2) {
                resultat.add(mcdAssociation);
            }
        }
        return resultat;
    }

    public static MCDElement getMCDElementByClassAndNamePath(IMCDModel imcdModel,
                                                             boolean withModel,
                                                             String className,
                                                             String namePath){
        for (MCDElement mcdElement: getMCDElementsByClassName(imcdModel, withModel, className)){
            if (mcdElement.getNamePath().equals(namePath)){
                return mcdElement;
            }
        }
        return null;
    }


    public static ArrayList<MCDElement> getMCDElementsByNameTree(IMCDModel imcdModel, String nameTree){
        ArrayList<MCDElement>  resultat = new ArrayList<MCDElement>() ;
        //#MAJ 2021-01-09 Suppression de MCDElement.getMCDElements()
        // for (MCDElement mcdElement : getMCDElements(imcdModel)){
        for ( MCDElement mcdElement : imcdModel.getMCDDescendants()){
            if (mcdElement.getNameTree().equals(nameTree)){
                resultat.add(mcdElement);
            }
        }
        return resultat;
    }

    public static MCDElement getMCDElementByClassAndNameTree(IMCDModel imcdModel, boolean withModel, String className,String nameTree){
        for (MCDElement mcdElement: getMCDElementsByClassName(imcdModel, withModel, className)){
            if (mcdElement.getNameTree().equals(nameTree)){
                return mcdElement;
            }
        }
        return null;
    }

    public static MLDRModelDT getMLDRModelDT(IMCDModel imcdModel) {
        for ( MVCCDElement mvccdElement : imcdModel.getChilds()){
            if ( mvccdElement instanceof MLDRModelDT){
                return (MLDRModelDT) mvccdElement ;
            }
        }
        return null;
    }

    public static MLDRModelTI getMLDRModelTI(IMCDModel imcdModel) {
        for ( MVCCDElement mvccdElement : imcdModel.getChilds()){
            if ( mvccdElement instanceof MLDRModelTI){
                return (MLDRModelTI) mvccdElement ;
            }
        }
        return null;
    }


    public static ArrayList<MCDPackage> getMCDPackages(IMCDModel imcdModel){
        ArrayList<MCDPackage>  resultat = new ArrayList<MCDPackage>() ;
        //#MAJ 2021-01-09 Suppression de MCDElement.getMCDElements()
        //for (MCDElement mcdElement : getMCDElements(imcdModel)){
        for ( MCDElement mcdElement : imcdModel.getMCDDescendants()){
            if (mcdElement instanceof MCDPackage){
                resultat.add((MCDPackage) mcdElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDRelation> getMCDRelations(IMCDModel imcdModel) {
        ArrayList<MCDRelation>  resultat = new ArrayList<MCDRelation>() ;
        //#MAJ 2021-01-09 Suppression de MCDElement.getMCDElements()
        for ( MCDElement mcdElement : imcdModel.getMCDDescendants()){
            if (mcdElement instanceof MCDRelation){
                resultat.add((MCDRelation) mcdElement);
            }
        }
        return resultat;
    }
    public static ArrayList<MCDAssociation> getMCDAssociations(IMCDModel imcdModel) {
        ArrayList<MCDAssociation>  resultat = new ArrayList<MCDAssociation>() ;
        for (MCDRelation mcdRelation : getMCDRelations(imcdModel)){
            if (mcdRelation instanceof MCDAssociation) {
                resultat.add((MCDAssociation) mcdRelation);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAssociation> getMCDAssociationsNN(IMCDModel imcdModel) {
        ArrayList<MCDAssociation>  resultat = new ArrayList<MCDAssociation>() ;
        for (MCDAssociation mcdAssociation : getMCDAssociations(imcdModel)){
            if (mcdAssociation.getDegree() == MRelationDegree.DEGREE_MANY_MANY) {
                resultat.add(mcdAssociation);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAssociation> getMCDAssociationsNNWithoutEntity(IMCDModel imcdModel) {
        ArrayList<MCDAssociation>  resultat = new ArrayList<MCDAssociation>() ;
        for (MCDAssociation mcdAssociationNN : getMCDAssociationsNN(imcdModel)){
            if (mcdAssociationNN.getLink() == null) {
                resultat.add(mcdAssociationNN);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDContEntities> getMCDContEntities(IMCDModel imcdModel) {
        ArrayList<MCDContEntities>  resultat = new ArrayList<MCDContEntities>() ;
        //#MAJ 2021-01-09 Suppression de MCDElement.getMCDElements()
        for ( MCDElement mcdElement : imcdModel.getMCDDescendants()){
            if (mcdElement instanceof MCDContEntities) {
                resultat.add( (MCDContEntities) mcdElement);
            }
        }
        return resultat;
    }


    public static MCDContEntities getMCDContEntitiesByNamePath(IMCDModel imcdModel, String namePath){
        for (MCDContEntities mcdContEntities : IMCDModelService.getMCDContEntities(imcdModel)){
            if (mcdContEntities.getNamePath().equals(namePath)){
                return mcdContEntities;
            }
        }
        return null;
    }

    public static ArrayList<MCDContRelations> getMCDContRelations(IMCDModel imcdModel) {
        ArrayList<MCDContRelations> resultat =  new ArrayList<MCDContRelations>();
        //#MAJ 2021-01-09 Suppression de MCDElement.getMCDElements()
        for ( MCDElement mcdElement : imcdModel.getMCDDescendants()){
            if (mcdElement instanceof MCDContRelations) {
                resultat.add((MCDContRelations) mcdElement);
            }
        }
        return resultat;
    }

    public static MCDContRelations getMCDContRelationsByNamePath(IMCDModel imcdModel,
                                                                 String namePath){
        for (MCDContRelations mcdContRelation : IMCDModelService.getMCDContRelations(imcdModel)){
            if (mcdContRelation.getNamePath().equals(namePath)){
                return mcdContRelation;
            }
        }
        return null;
    }



}
