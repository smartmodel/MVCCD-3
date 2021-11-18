package main;

import connections.ConnectionsDB;
import messages.MessagesBuilder;

import java.util.ArrayList;

public class MVCCDElementApplicationConnections extends MVCCDElement {

    public MVCCDElementApplicationConnections(MVCCDElement parent) {
        super(parent,  MessagesBuilder.getMessagesProperty("application.connexions.name"));
    }

    public ArrayList<ConnectionsDB> getConnectionsDB(){
        ArrayList<ConnectionsDB> resultat = new ArrayList<ConnectionsDB>();
        for (MVCCDElement mvccdElement : super.getChilds()){
            if (mvccdElement instanceof ConnectionsDB){
                resultat.add ((ConnectionsDB) mvccdElement);
            }
        }
        return resultat;
    }
}
