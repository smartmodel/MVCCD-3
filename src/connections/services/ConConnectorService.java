package connections.services;

import connections.ConConnection;
import preferences.Preferences;

public class ConConnectorService {

    public static String getLienProg(ConConnection conConnection, String name) {
        return conConnection.getLienProg() + Preferences.LIEN_PROG_SEP + name;
    }

}
