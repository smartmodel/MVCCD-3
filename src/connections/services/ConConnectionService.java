package connections.services;

import connections.ConConnection;
import connections.ConDB;
import connections.ConManager;
import preferences.Preferences;

import java.io.File;

public class ConConnectionService {

    public static String getLienProg(ConDB conDB, String name) {
        return conDB.getLienProg() + Preferences.LIEN_PROG_SEP + name;
    }

    public static File getDriverFileToUse(ConConnection conConnection){
        return getDriverFileToUse( conConnection.getConDB() ,
        conConnection.isDriverDefault(),
        conConnection.getDriverFileCustom() )  ;
    }

    public static File getDriverFileToUse(ConDB conDB,
                                           boolean driverDefault,
                                           String driverFileCustom) {
        if (driverDefault){
            return new File (ConManager.getDefaultDriverFileNamePathAbsolute(conDB));
        } else
            return new File (driverFileCustom);
    }

}
