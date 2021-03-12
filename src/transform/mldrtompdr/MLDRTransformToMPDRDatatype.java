package transform.mldrtompdr;

import datatypes.MPDRDatatype;
import exceptions.CodeApplException;
import mldr.MLDRColumn;
import mpdr.MPDRDB;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MLDRTransformToMPDRDatatype {

    /*
    public static MPDRDatatype fromMLDRDatatype(MPDRDB db, MLDRColumn mldrColumn) {

        Preferences preferences = PreferencesManager.instance().preferences();
        if (db == MPDRDB.ORACLE) {
            return MLDRTransformToMPDROracleDatatype.fromMLDRDatatype(mldrColumn);
        } else if (db == MPDRDB.MYSQL) {
            return MLDRTransformToMPDRMySQLDatatype.fromMLDRDatatype(mldrColumn);
        } else if (db == MPDRDB.POSTGRESQL) {
            return MLDRTransformToMPDRPostgreSQLDatatype.fromMLDRDatatype(mldrColumn);
        } else {
            throw new CodeApplException("fromMLDRDatatype () " + " - Le modèle physique n'est pas trouvé");
        }
     }

     */

           /*
        if (preferences.getMLDRTOMPDR_DB().equals(Preferences.MLDRTOMPDR_DB_ORACLE)) {
            return MLDRTransformToMPDROracleDatatype.fromMLDRDatatype(mldrColumn);
        } else if (preferences.getMLDRTOMPDR_DB().equals(Preferences.MPDR_DB_MYSQL)) {
            return MLDRTransformToMPDRMySQLDatatype.fromMLDRDatatype(mldrColumn);
        } else if (preferences.getMLDRTOMPDR_DB().equals(Preferences.MLDRTOMPDR_DB_POSTGRESQL)) {
            return MLDRTransformToMPDRPostgreSQLDatatype.fromMLDRDatatype(mldrColumn);
        } else {
            throw new CodeApplException("fromMLDRDatatype () " + " - Le modèle physique n'est pas trouvé");
        }

         */

}

