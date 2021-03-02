package transform.mldrtompdr;

import datatypes.MPDRDatatype;
import exceptions.CodeApplException;
import mldr.MLDRColumn;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MLDRTransformToMPDRDatatype {

    public static MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {

        Preferences preferences = PreferencesManager.instance().preferences();
        if (preferences.getMLDRTOMPDR_DB().equals(Preferences.MLDRTOMPDR_DB_ORACLE)) {
            return MLDRTransformToMPDROracleDatatype.fromMLDRDatatype(mldrColumn);
        } else if (preferences.getMLDRTOMPDR_DB().equals(Preferences.MLDRTOMPDR_DB_MYSQL)) {
            return MLDRTransformToMPDRMySQLDatatype.fromMLDRDatatype(mldrColumn);
        } else if (preferences.getMLDRTOMPDR_DB().equals(Preferences.MLDRTOMPDR_DB_POSTGRESQL)) {
            return MLDRTransformToMPDRPostgreSQLDatatype.fromMLDRDatatype(mldrColumn);
        } else {
            throw new CodeApplException("fromMLDRDatatype () " + " - Le modèle physique n'est pas trouvé");
        }
    }
}

