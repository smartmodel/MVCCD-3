package connections;

import connections.services.ConConnectionService;
import m.interfaces.IMCompletness;
import main.MVCCDElement;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import repository.editingTreat.connections.connection.ConConnectionEditingTreat;
import repository.editingTreat.connections.connector.ConConnectorEditingTreat;

import java.io.File;

public abstract class ConConnection extends ConElement implements IMCompletness {

    private String hostName;
    private String port;
    private String dbName;
    private boolean confExecScript = false;
    private boolean driverDefault = true;
    private String driverFileCustom;

    private String userName ;
    //TODO-1 A crypter
    private String userPW ;
    private boolean savePW ;

    private ConIDDBName conIDDBName ;

    public ConConnection(MVCCDElement parent, ConDB db) {
        super(parent, db);
    }

    public ConConnection(MVCCDElement parent, String name, ConDB db) {
        super(parent, name, db);
    }


    public void setName(String name) {
        if (StringUtils.isEmpty(lienProg)){ //
            // lienProg = getConDB().getLienProg() + Preferences.LIEN_PROG_SEP + name;
            lienProg = ConConnectionService.getLienProg(getConDB(), name);
        }
        super.setName(name);
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean isConfExecScript() {
        return confExecScript;
    }

    public void setConfExecScript(boolean confExecScript) {
        this.confExecScript = confExecScript;
    }

    public boolean isDriverDefault() {
        return driverDefault;
    }

    public void setDriverDefault(boolean driverDefault) {
        this.driverDefault = driverDefault;
    }

    public String getDriverFileCustom() {
        return driverFileCustom;
    }

    public void setDriverFileCustom(String driverFileCustom) {
        this.driverFileCustom = driverFileCustom;
    }

    public abstract String getResourceURL() ;

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

    public File getDriverFileToUse (){
        return ConConnectionService.getDriverFileToUse(this);
    }

    public ConIDDBName getConIDDBName() {
        return conIDDBName;
    }

    public void setConIDDBName(ConIDDBName conIDDBName) {
        this.conIDDBName = conIDDBName;
    }

    public abstract ConConnectionEditingTreat getConConnectionEditingTreat();
    public abstract ConConnectorEditingTreat getConConnectorEditingTreat();

    public String getNamePath(){
        return getConDB() + Preferences.PATH_NAMING_SEPARATOR + getName();
    }

}
