package mldr.services;

import main.MVCCDElement;
import mcd.MCDAssociation;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.MCDRelation;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mdr.MDRContTables;
import mldr.MLDRContRelations;
import mldr.MLDRModel;
import mldr.MLDRRelationFK;
import mldr.MLDRTable;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.interfaces.IMLDRRelation;
import mpdr.mysql.MPDRMySQLModel;
import mpdr.oracle.MPDROracleModel;
import mpdr.postgresql.MPDRPostgreSQLModel;
import utilities.Trace;

import java.util.ArrayList;

public class MLDRModelService {

    public static MDRContTables getMDRContTables(MLDRModel mldrModel) {
        for (MVCCDElement mvccdElement : mldrModel.getChilds()){
            if (mvccdElement instanceof MDRContTables){
                return (MDRContTables) mvccdElement ;
            }
        }
        return null ;
    }

    public static ArrayList<MLDRTable> getMLDRTables(MLDRModel mldrModel){
        ArrayList<MLDRTable> resultat = new ArrayList<MLDRTable>();
        MDRContTables mdrContTables = getMDRContTables(mldrModel);
        for (MVCCDElement mvccdElement: mdrContTables.getChilds()){
            if (mvccdElement instanceof MLDRTable){
                resultat.add((MLDRTable) mvccdElement);
            }
        }
        return resultat;
    }



    public static MLDRTable getMLDRTableByEntitySource(MLDRModel mldrModel, MCDEntity mcdEntity) {
        for (MLDRTable mldrTable : getMLDRTables(mldrModel)){
            if (mldrTable.getMcdElementSource() == mcdEntity){
                return mldrTable;
            }
        }
        return null ;
    }


    public static MLDRTable getMLDRTableByAssNNSource(MLDRModel mldrModel, MCDAssociation mcdAssociation) {
        for (MLDRTable mldrTable : getMLDRTables(mldrModel)){
            if (mldrTable.getMcdElementSource() == mcdAssociation){
                return mldrTable;
            }
        }
        return null ;
    }



    public static MLDRContRelations getMLDRContRelations(MLDRModel mldrModel) {
        for (MVCCDElement mvccdElement : mldrModel.getChilds()){
            if (mvccdElement instanceof MLDRContRelations){
                return (MLDRContRelations) mvccdElement ;
            }
        }
        return null ;
    }

    public static ArrayList<IMLDRRelation> getIMLDRRelations(MLDRModel mldrModel){
        ArrayList<IMLDRRelation> resultat = new ArrayList<IMLDRRelation>();
        MLDRContRelations mldrContRelations = getMLDRContRelations(mldrModel);
        for (MVCCDElement mvccdElement: mldrContRelations.getChilds()){
            if (mvccdElement instanceof IMLDRRelation){
                resultat.add((IMLDRRelation) mvccdElement);
            }
        }
        return resultat;
    }

/*
    public static IMLDRRelation getIMLDRRelationByMCDRelationSource(MLDRModel mldrModel, MCDRelation mcdRelation) {
        for (IMLDRRelation imldrRelation : getIMLDRRelations(mldrModel)){
            if (imldrRelation.getMcdElementSource() == mcdRelation){
                return imldrRelation;
            }
        }
        return null ;
    }

 */

    // Il peut y avoir 2 relations logiques pour une association n:n de niveau conceptuel
    public static ArrayList<MLDRRelationFK> getMLDRRelationFKsByMCDRelationSource(MLDRModel mldrModel, MCDRelation mcdRelation) {
        ArrayList<MLDRRelationFK> resultat = new ArrayList<MLDRRelationFK>();
        for (IMLDRRelation imldrRelation : getIMLDRRelations(mldrModel)) {
            if (imldrRelation instanceof MLDRRelationFK) {
                MLDRRelationFK mldrRelationFK = (MLDRRelationFK) imldrRelation;
                if (mldrRelationFK.getMcdElementSource() == mcdRelation) {
                    resultat.add(mldrRelationFK);
                }
            }
        }
        return resultat;
    }

    /*
    public static MLDRRelationFK getMLDRRelationFKByMCDRelationSourceAndTableParent(MLDRModel mldrModel,
                                                                                MCDRelation mcdRelation,
                                                                                MLDRTable mldrTableParent) {
        for (MLDRRelationFK mldrRelationFK : getMLDRRelationFKsByMCDRelationSource(mldrModel, mcdRelation)){
            if (mldrRelationFK.getEndChild().getMDRTable() == mldrTableParent){
                return mldrRelationFK;
            }
        }
        return null ;
    }

     */
/*
    public static MLDRRelationFK getMLDRRelationFKByMCDRelationSourceAndMLDRFK(MLDRModel mldrModel,
                                                                               MCDRelation mcdRelation,
                                                                               MLDRFK mldrFK) {
        for (MLDRRelationFK mldrRelationFK : getMLDRRelationFKsByMCDRelationSource(mldrModel, mcdRelation)){
            if (mldrRelationFK.getMDRFK() == mldrFK){
                return mldrRelationFK;
            }
        }
        return null ;
    }

 */

    // Une relation de niveau logique peut être simplement modifiée alors que la contrainte de clé étrangère
    // passe d'une table à une autre en changeant par exemple le sens d'un degré 1:n
    public static MLDRRelationFK getMLDRRelationFKByMCDRelationSourceAndSameTables(MLDRModel mldrModel,
                                                                                    MCDRelation mcdRelation,
                                                                                    MLDRTable mldrTableA,
                                                                                    MLDRTable mldrTableB) {

        for (MLDRRelationFK mldrRelationFK : getMLDRRelationFKsByMCDRelationSource(mldrModel, mcdRelation)){
            MLDRTable mldrTable1 = (MLDRTable) mldrRelationFK.getEndChild().getMDRTable();
            MLDRTable mldrTable2 = (MLDRTable) mldrRelationFK.getEndParent().getMDRTable();

            boolean c1 = (mldrTable1 == mldrTableA) && (mldrTable2 == mldrTableB);
            boolean c2 = (mldrTable1 == mldrTableB) && (mldrTable2 == mldrTableA);

            boolean sameTables = c1 || c2;
            if (sameTables){
                return mldrRelationFK;
            }
        }
        return null ;
    }



    public static MPDROracleModel getMPDRModelOracle(MLDRModel mldrModel) {
        for ( MVCCDElement mvccdElement : mldrModel.getChilds()){
            if ( mvccdElement instanceof MPDROracleModel){
                return (MPDROracleModel) mvccdElement ;
            }
        }
        return null;
    }

    
    public static MPDRMySQLModel getMPDRModelMySQL(MLDRModel mldrModel) {
        for ( MVCCDElement mvccdElement : mldrModel.getChilds()){
            if ( mvccdElement instanceof MPDRMySQLModel){
                return (MPDRMySQLModel) mvccdElement ;
            }
        }
        return null;
    }

    public static MPDRPostgreSQLModel getMPDRModelPostgreSQL(MLDRModel mldrModel) {
        for ( MVCCDElement mvccdElement : mldrModel.getChilds()){
            if ( mvccdElement instanceof MPDRPostgreSQLModel){
                return (MPDRPostgreSQLModel) mvccdElement ;
            }
        }
        return null;
    }

    public static boolean foundMCDElementSource(IMLDRElementWithSource imldrElementWithSource,
                                                IMCDModel imcdModel){
        MCDElement mcdElementSource = imldrElementWithSource.getMcdElementSource();
        if (mcdElementSource == null){
            return false;
        } else {
            return IMCDModelService.existMCDElement(imcdModel, mcdElementSource);
        }

    }

}
