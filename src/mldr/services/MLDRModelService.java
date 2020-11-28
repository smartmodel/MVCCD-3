package mldr.services;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mdr.MDRContTables;
import mldr.MLDRModel;
import mldr.MLDRTable;
import mldr.interfaces.IMLDRElementWithSource;
import mpdr.mysql.MPDRMySQLModel;
import mpdr.oracle.MPDROracleModel;
import mpdr.postgresql.MPDRPostgreSQLModel;

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
            return IMCDModelService.foundMCDElementInIModelByInstance(imcdModel, mcdElementSource);
        }

    }


}
