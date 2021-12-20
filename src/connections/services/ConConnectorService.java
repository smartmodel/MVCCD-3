package connections.services;

import connections.ConConnection;
import preferences.Preferences;

import java.awt.*;
import java.sql.Connection;

public class ConConnectorService {

    public static String getLienProg(ConConnection conConnection, String name) {
        return conConnection.getLienProg() + Preferences.LIEN_PROG_SEP + name;
    }


    public static Connection actionTestConnection(Window owner,
                                                boolean autonomous,
                                                ConConnection conConnectionParent,
                                                String userName,
                                                String userPW) {

        // Clone de la connexion car, il y a changement du user/pw !
        ConConnection conConnectionParentClone = (ConConnection) conConnectionParent.clone();

        // L'appel se fait depuis l'éditeur, sinon c'est un objet conConnecteur qui est passé en paramètre
        //Mettre le userName et password du connecteur dans l'objet connexion
        conConnectionParentClone.setUserName(userName);
        conConnectionParentClone.setUserPW(userPW);

        return ConnectionsService.actionTestIConConnectionOrConnector(owner,
                autonomous,
                conConnectionParentClone);
    }

}
