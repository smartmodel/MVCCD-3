package connections.services;

import connections.ConDB;
import connections.ConElement;
import connections.ConIDDBName;
import connections.ConManager;
import generatesql.MPDRGenerateSQLUtil;
import main.MVCCDManager;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

import javax.swing.tree.DefaultMutableTreeNode;

public class ConnectionsService {

    public static ConElement getConElementByLienProg(String lienProg) {
        for (ConElement conElement : ConManager.instance().getConElements()){
            if (conElement.getLienProg().equals(lienProg)){
                return conElement;
            }
        }
        return null;
    }

    public static DefaultMutableTreeNode getNodeByLienProg(String lienProg) {

        DefaultMutableTreeNode rootConnexions = MVCCDManager.instance().getRepository().getNodeConnexionsEntry();
        return getNodeByLienProg(rootConnexions, lienProg);
    }

    public static DefaultMutableTreeNode getNodeByLienProg(DefaultMutableTreeNode root, String lienProg) {
        DefaultMutableTreeNode resultat = null;
        if (root.getUserObject() instanceof ConElement) {
            ConElement conElement = (ConElement) root.getUserObject();
            if (conElement.getLienProg().equals(lienProg)) {
                resultat = root;
            }
        }
        // Pas trouvé de correspondance
        if (resultat == null){
            // Sur le noeud conteneur de l'application
            //if (root.getUserObject() instanceof MVCCDElementApplicationConnections) {
                // Sur un noeud d'élément de connexion
                //if (root.getUserObject() instanceof ConElement) {
                    for (int i = 0; i < root.getChildCount(); i++) {
                        if (resultat == null) {
                            resultat = getNodeByLienProg((DefaultMutableTreeNode) root.getChildAt(i), lienProg);
                        }
                    }
                //}
            //}
        }
        return resultat;
    }

    public static String getResourceURL(ConDB conDB, String hostName, String port, ConIDDBName iddbName, String dbName) {
        String url = conDB.getUrlTemplate();
        if (StringUtils.isNotEmpty(hostName)){
            url = MPDRGenerateSQLUtil.replaceKeyValue(url, Preferences.CON_HOSTNAME_WORD, hostName);
        }
        if (StringUtils.isNotEmpty(port)){
            url = MPDRGenerateSQLUtil.replaceKeyValue(url, Preferences.CON_PORT_WORD, port);
        }
        if (StringUtils.isNotEmpty(dbName)){
            url = MPDRGenerateSQLUtil.replaceKeyValue(url, Preferences.CON_DBNAME_WORD, dbName);
        }
        if (iddbName != null){
            if (iddbName != ConIDDBName.NAME_STD) {
                if (iddbName == ConIDDBName.SID) {
                    url = MPDRGenerateSQLUtil.replaceKeyValue(url, Preferences.CON_DBNAME_ID_MARKER, conDB.getUrlTemplateSIDMarker());
                }
                if (iddbName == ConIDDBName.SERVICE_NAME) {
                    url = MPDRGenerateSQLUtil.replaceKeyValue(url, Preferences.CON_DBNAME_ID_MARKER, conDB.getUrlTemplateServiceNameMarker());
                }
            }
        }
        return url ;
    }
}
