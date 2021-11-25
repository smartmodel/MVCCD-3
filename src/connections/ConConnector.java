package connections;

import connections.services.ConConnectorService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import repository.editingTreat.connections.connector.ConConnectorEditingTreat;

public abstract class ConConnector extends ConElement{

    private String userName ;
    //TODO-1 A crypter
    private String userPW ;
    private boolean savePW ;

    public ConConnector(ConConnection parent, ConDB db) {
        super(parent, db);
    }

    public ConConnector(ConConnection parent, String name, ConDB db) {
        super(parent, name, db);
    }

    public void setName(String name) {
        if (StringUtils.isEmpty(lienProg)){
            lienProg = ConConnectorService.getLienProg((ConConnection) getParent(), name);
        }
        super.setName(name);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPW() {
        return userPW;
    }

    public void setUserPW(String userPW) {
        this.userPW = userPW;
    }

    public boolean isSavePW() {
        return savePW;
    }

    public void setSavePW(boolean savePW) {
        this.savePW = savePW;
    }

    public abstract ConConnectorEditingTreat getConConnectorEditingTreat();


    public String getNamePath(){
        return ((ConConnection) getParent()).getNamePath() + Preferences.PATH_NAMING_SEPARATOR + getName();
    }

}
