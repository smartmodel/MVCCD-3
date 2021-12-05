package generatorsql.viewer;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConDBMode;
import connections.services.ConnectionsService;
import console.ViewLogsManager;
import exceptions.CodeApplException;
import generatorsql.GenerateSQLUtil;
import messages.MessagesBuilder;
import mpdr.MPDRModel;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import resultat.ResultatLevel;
import utilities.window.PanelContent;
import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.IPanelInputContent;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SQLViewerParametersContent extends PanelContent implements IPanelInputContent{

    SQLViewerParameters sqlViewerParameters;

    private JPanel panelContent = new JPanel();
    private JPanel panelConnection = new JPanel();

    private JLabel labelConnectionName;
    private STextField fielConnectionName;

    private JLabel labelConnectorName;
    private STextField fielConnectorName;
    
    private JLabel labelHostName;
    private STextField fieldHostName;
    private JLabel labelPort;
    private STextField fieldPort;
    private JLabel labelDbName;
    private STextField fieldDbName;

    private JLabel labelURL;
    private STextField fieldURL;

    private JLabel labelUserName;
    private STextField fieldUserName;

    private JLabel labelDirectorySQL;
    private STextField fieldDirectorySQL;


    public SQLViewerParametersContent(SQLViewerParameters sqlViewerParameters) {
        super(sqlViewerParameters);
        this.sqlViewerParameters = sqlViewerParameters;
        super.addContent(panelContent);
        createContent();
        createPanelContent();
        loadDatas();
    }



    public void createContent() {
        labelConnectionName = new JLabel("Nom de connexion : ");
        fielConnectionName = new STextField(this, labelConnectionName);
        fielConnectionName.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fielConnectionName.setReadOnly(true);
      
        labelConnectorName = new JLabel("Nom du connecteur : ");
        fielConnectorName = new STextField(this, labelConnectorName);
        fielConnectorName.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fielConnectorName.setReadOnly(true);

        labelHostName = new JLabel("Host Name : ");
        fieldHostName = new STextField(this, labelHostName);
        fieldHostName.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldHostName.setToolTipText("Nom de la machine hôte...");
        fieldHostName.setReadOnly(true);

        labelPort = new JLabel("Port : ");
        fieldPort = new STextField(this, labelPort);
        fieldPort.setPreferredSize((new Dimension(30, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldPort.setToolTipText("Port");
        fieldPort.setReadOnly(true);

        labelDbName = new JLabel("Nom : ");
        fieldDbName = new STextField(this, labelDbName);
        fieldDbName.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDbName.setToolTipText("Nom de la base de données...");
        fieldDbName.setReadOnly(true);

        labelURL = new JLabel("URL de connexion : ");
        fieldURL = new STextField(this, labelURL);
        fieldURL.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldURL.setToolTipText("Chaîne de connexion construite...");
        fieldURL.setReadOnly(true);

        labelUserName = new JLabel("Nom utilisateur : ");
        fieldUserName = new STextField(this, labelUserName);
        fieldUserName.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldUserName.setReadOnly(true);

        labelDirectorySQL = new JLabel("Répertoire SQL : ");
        fieldDirectorySQL = new STextField(this, labelDirectorySQL);
        fieldDirectorySQL.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDirectorySQL.setReadOnly(true);
    }


    protected void createPanelContent() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelContent);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        createPanelConnection();
        panelContent.add(panelConnection, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelContent.add(labelDirectorySQL, gbc);
        gbc.gridx++;
        panelContent.add(fieldDirectorySQL, gbc);
    }

    private void createPanelConnection() {
        Border border = BorderFactory.createLineBorder(Color.black);
        TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Connection à la base de données");
        panelConnection.setBorder(panelDataypeBorder);

        panelConnection.setLayout(new GridBagLayout());
        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.anchor = GridBagConstraints.NORTHWEST;
        gbcD.insets = new Insets(10, 10, 0, 0);

        gbcD.gridx = 0;
        gbcD.gridy = 0;
        gbcD.gridwidth = 1;
        gbcD.gridheight = 1;

        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTION) {
            panelConnection.add(labelConnectionName, gbcD);
            gbcD.gridx++;
            panelConnection.add(fielConnectionName, gbcD);
        }

        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
            panelConnection.add(labelConnectorName, gbcD);
            gbcD.gridx++;
            panelConnection.add(fielConnectorName, gbcD);
        }

        gbcD.gridx = 0;
        gbcD.gridy++;
        panelConnection.add(labelHostName, gbcD);
        gbcD.gridx++;
        panelConnection.add(fieldHostName, gbcD);
        gbcD.gridx++;
        panelConnection.add(labelPort, gbcD);
        gbcD.gridx++;
        panelConnection.add(fieldPort, gbcD);
        gbcD.gridx++;
        panelConnection.add(labelDbName, gbcD);
        gbcD.gridx++;
        panelConnection.add(fieldDbName, gbcD);
        gbcD.gridx = 0;
        gbcD.gridy++;
        panelConnection.add(labelUserName, gbcD);
        gbcD.gridx++;
        panelConnection.add(fieldUserName, gbcD);
    }


    private void loadDatas() {
        SQLViewer sqlViewer = sqlViewerParameters.getSQLViewer();
        MPDRModel mpdrModel = sqlViewer.getMpdrModel();

        ConConnection conConnection = null;

        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTION) {
            if (StringUtils.isNotEmpty(mpdrModel.getConnectionLienProg())) {
                conConnection = ConnectionsService.getConConnectionByLienProg(mpdrModel.getConnectionLienProg());
                if (conConnection != null) {
                    //fieldUserName.setText(conConnection.getUserName());
                    //fielConnectionName.setText(conConnection.getNamePath());
                    sqlViewer.setConConnection(conConnection);
                } else {
                    String message = MessagesBuilder.getMessagesProperty("editor.mpdr.load.connection.unknow");
                    ViewLogsManager.printNewResultatWithMessage(message, ResultatLevel.INFO);
                    ViewLogsManager.dialogQuittance(getEditor(), message);
                }
            }
        }
        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
            if (StringUtils.isNotEmpty(mpdrModel.getConnectorLienProg())) {
                ConConnector conConnector = ConnectionsService.getConConnectorByLienProg(mpdrModel.getConnectorLienProg());
                if (conConnector != null){
                    conConnection = (ConConnection) conConnector.getParent();
                    //fieldUserName.setText(conConnector.getUserName());
                    //fielConnectorName.setText(conConnector.getNamePath());
                    sqlViewer.setConConnector(conConnector);
                } else {
                    String message = MessagesBuilder.getMessagesProperty("editor.mpdr.load.connector.unknow");
                    ViewLogsManager.printNewResultatWithMessage(message, ResultatLevel.INFO);
                    ViewLogsManager.dialogQuittance(getEditor(), message);
                }

            }
        }
        if (conConnection != null) {
            fieldHostName.setText(conConnection.getHostName());
            fieldPort.setText(conConnection.getPort());
            fieldDbName.setText(conConnection.getDbName());
        }
        String directorySQL = GenerateSQLUtil.directorySQLFiles().getPath();
        fieldDirectorySQL.setText(directorySQL);
    }

    @Override
    // Pas utilisé
    public boolean isDataInitialized() {
        return false;
    }

    @Override
    // Pas utilisé
    public DialogEditor getEditor() {
        throw new CodeApplException("getEditor n'est pas affecté...");
    }
}
