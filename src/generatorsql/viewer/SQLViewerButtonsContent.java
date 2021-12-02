package generatorsql.viewer;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConDBMode;
import connections.ConManager;
import console.ViewLogsManager;
import exceptions.CodeApplException;
import exceptions.service.ExceptionService;
import generatorsql.GenerateSQLUtil;
import main.MVCCDManager;
import messages.MessagesBuilder;
import mpdr.MPDRModel;
import preferences.Preferences;
import preferences.PreferencesManager;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import resultat.ResultatService;
import utilities.UtilDivers;
import utilities.files.UtilFiles;
import utilities.window.DialogMessage;
import utilities.window.PanelContent;
import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.IPanelInputContent;
import utilities.window.scomponents.SButton;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

public class SQLViewerButtonsContent extends PanelContent implements IPanelInputContent, ActionListener {

    private SQLViewerButtons sqlViewerButtons;
    private SQLViewer sqlViewer;
    private MPDRModel mpdrModel;

    File sqlCreateFile = GenerateSQLUtil.sqlCreateFile();


    private JPanel panelContent = new JPanel();
    private JPanel panelDDL = new JPanel();
    private JPanel panelDML = new JPanel();

    private SButton btnConnectionTest;
    private SButton btnConnectorTest;
    private SButton btnExecute;
    private SButton btnSave;

    private JLabel labelDDLName;
    private STextField fieldDDLName;
    private JLabel labelDDLSaved;
    private STextField fieldDDLSaved;
    private JLabel labelDDLExecuted;
    private STextField fieldDDLExecuted;

    public SQLViewerButtonsContent(SQLViewerButtons sqlViewerButtons) {
        super(sqlViewerButtons);
        this.sqlViewerButtons = sqlViewerButtons;
        super.addContent(panelContent);
        sqlViewer = sqlViewerButtons.getSQLViewer();
        mpdrModel = sqlViewer.getMpdrModel();
        createContent();
        createPanelContent();
        loadDatas();
    }



    public void createContent() {

        btnConnectionTest = new SButton("Test de connexion");
        btnConnectionTest.addActionListener(this);

        btnConnectorTest = new SButton("Test du connecteur");
        btnConnectorTest.addActionListener(this);

        btnExecute = new SButton("Exécuter");
        btnExecute.addActionListener(this);

        btnSave = new SButton("Sauver");
        btnSave.addActionListener(this);


        labelDDLName = new JLabel("Nom : ");
        fieldDDLName = new STextField(this, labelDDLName);
        fieldDDLName.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDDLName.setToolTipText("Nom du fichier script SQL/DDL...");
        fieldDDLName.setReadOnly(true);

        labelDDLSaved = new JLabel("Sauvé : ");
        fieldDDLSaved = new STextField(this, labelDDLSaved);
        fieldDDLSaved.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDDLSaved.setToolTipText("Si existant, date de la dernière modification...");
        fieldDDLSaved.setReadOnly(true);

        labelDDLExecuted = new JLabel("Exécuté : ");
        fieldDDLExecuted = new STextField(this, labelDDLExecuted);
        fieldDDLExecuted.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDDLExecuted.setToolTipText("Dernière exécution pour cette session...");
        fieldDDLExecuted.setReadOnly(true);

    }


    protected void createPanelContent() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelContent);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTION) {
            panelContent.add(btnConnectionTest, gbc);
        }

        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
            panelContent.add(btnConnectorTest, gbc);
        }

        gbc.gridy++ ;
        createPanelDDL();
        panelContent.add(panelDDL, gbc);
        gbc.gridy++ ;
        panelContent.add(btnExecute, gbc);
        gbc.gridy++ ;
        panelContent.add(btnSave, gbc);

    }

    private void createPanelDDL() {
        Border border = BorderFactory.createLineBorder(Color.black);
        TitledBorder panelDDLBorder = BorderFactory.createTitledBorder(border, "Script SQL/DDL");
        panelDDL.setBorder(panelDDLBorder);

        panelDDL.setLayout(new GridBagLayout());
        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.anchor = GridBagConstraints.NORTHWEST;
        gbcD.insets = new Insets(10, 10, 0, 0);

        gbcD.gridx = 0;
        gbcD.gridy = 0;
        gbcD.gridwidth = 1;
        gbcD.gridheight = 1;

        panelDDL.add(labelDDLName , gbcD);
        gbcD.gridx++;
        panelDDL.add(fieldDDLName, gbcD);

        gbcD.gridx = 0;
        gbcD.gridy++ ;
        panelDDL.add(labelDDLSaved, gbcD);
        gbcD.gridx++;
        panelDDL.add(fieldDDLSaved, gbcD);

        gbcD.gridx = 0;
        gbcD.gridy++ ;
        panelDDL.add(labelDDLExecuted, gbcD);
        gbcD.gridx++;
        panelDDL.add(fieldDDLExecuted, gbcD);

    }


    private void loadDatas()  {
        SQLViewer sqlViewer = sqlViewerButtons.getSQLViewer();
        MPDRModel mpdrModel = sqlViewer.getMpdrModel();

        fieldDDLName.setText(sqlCreateFile.getName());
        setFieldDDLSaved();
        fieldDDLExecuted.setText("");
    }

    private void setFieldDDLSaved() {
        Date ddlDateLastModified = UtilFiles.getLastModifiedDate(sqlCreateFile);
        if (ddlDateLastModified != null) {
            Date dateLastModified = UtilFiles.getLastModifiedDate(sqlCreateFile);
            String formattedDateLastModified = UtilDivers.dateHourFormatted(dateLastModified);
            fieldDDLSaved.setText(formattedDateLastModified);
        } else {
            fieldDDLSaved.setText("Inexistant");
        }
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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String propertyMessage = "sqlviewer.btn.exception.new";
        String propertyAction = "";
        try {
            Object source = actionEvent.getSource();

            if (source == btnConnectionTest) {
                propertyAction = "editor.mpdr.connection.btn.exception.test";
                actionTestConnection(true);
            }

            if (source == btnConnectorTest) {
                propertyAction = "editor.con.connector.btn.exception.test";
                actionTestConnector(true);
            }

            if (source == btnExecute) {
                propertyAction = "sqlviewer.execute.btn.exception.test";
                actionExecute();
            }

            if (source == btnSave) {
                propertyAction = "sqlviewer.save.btn.exception.test";
                actionSave(true);
                // Mise à jour de l'indicateur de dernière modif.
                setFieldDDLSaved();
            }
        } catch (Exception exception) {
            ExceptionService.exceptionUnhandled(exception, sqlViewer, mpdrModel,
                    propertyMessage, propertyAction);
        }
    }

    private Connection actionTestConnection(boolean autonomous) {
        ConConnection conConnection = sqlViewer.getConConnection();
        if (conConnection != null) {
            Connection connection = ConManager.createConnection(sqlViewer, conConnection);
            // S'il y a erreur, elle est levée directement par createConnection()
            if ( connection != null) {
                String message = MessagesBuilder.getMessagesProperty("editor.con.connection.btn.test.ok");
                ViewLogsManager.printMessage(message, ResultatLevel.INFO);
                if (autonomous){
                    DialogMessage.showOk(sqlViewer, message);
                }
            }
            return connection;
        } else {
            String message = MessagesBuilder.getMessagesProperty("sqlviewer.connection.unknow", mpdrModel.getNameTreePath());
            ViewLogsManager.printMessage(message, ResultatLevel.INFO);
            if (autonomous){
                DialogMessage.showOk(sqlViewer, message);
            }
        }
        return null;
    }

    private Connection actionTestConnector(boolean autonomous) {
         ConConnector conConnector = sqlViewer.getConConnector();
        if (conConnector != null) {
            Connection connection = ConManager.createConnection(sqlViewer, conConnector);
            // S'il y a erreur, elle est levée directement par createConnection()
            if ( connection != null) {
                String message = MessagesBuilder.getMessagesProperty("editor.con.connector.btn.test.ok");
                ViewLogsManager.printMessage(message, ResultatLevel.INFO);
                if (autonomous){
                    DialogMessage.showOk(sqlViewer, message);
                }
            }
            return connection;
        } else {
            String message = MessagesBuilder.getMessagesProperty("sqlviewer.connector.unknow", mpdrModel.getNameTreePath());
            ViewLogsManager.printMessage(message, ResultatLevel.INFO);
            if (autonomous){
                DialogMessage.showOk(sqlViewer, message);
            }
        }
        return null;
    }

    private Resultat actionSave(boolean autonomous)  {
        Resultat resultat = GenerateSQLUtil.generateSQLFile(sqlViewer.getSqlViewerCodeSQL().getSqlViewerCodeSQLContent().getCodeSQL());
        String message ;
        if (resultat.isNotError()) {
            message = MessagesBuilder.getMessagesProperty("sqlviewer.dml.file.saved.ok", sqlCreateFile);
        } else {
            message = MessagesBuilder.getMessagesProperty("sqlviewer.dml.file.saved.error", sqlCreateFile);
        }
        resultat.add(new ResultatElement (message, ResultatLevel.INFO));
        if (autonomous) {
            DialogMessage.showOk(sqlViewer, message);
            ViewLogsManager.printResultat(resultat);
        }
        return resultat;
    }


    private void actionExecute() {
        Resultat resultat = new Resultat();
        sqlViewer.clearConsole();
        String message = MessagesBuilder.getMessagesProperty("sqlviewer.sql.execute.start",
                new String[]{mpdrModel.getNamePath()});
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        MVCCDManager.instance().getConsoleManager().printMessage(message);
        // Sauvegarde du fichier de script
        String codeSQL = sqlViewer.getSqlViewerCodeSQL().getSqlViewerCodeSQLContent().getCodeSQL();
        Resultat resultatSave = actionSave(false);
        resultat.addResultat(resultatSave);


        // Exécution du script
        Connection connection = null;
        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTION) {
            connection = actionTestConnection(false);
        }

        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
            connection = actionTestConnector(false);
        }

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sqlViewer.getSqlViewerCodeSQL().getSqlViewerCodeSQLContent().getCodeSQL());
                statement.close();
                String formattedHourExecuted = UtilDivers.hourFormatted(new Date());
                fieldDDLExecuted.setText(formattedHourExecuted);
            } catch (Exception e) {
                resultat.addExceptionUnhandled(e);
                // Pour la console sqlViewer
                Resultat resultatExceptionUnhandled = new Resultat();
                ResultatService.addExceptionUnhandled(resultatExceptionUnhandled, e);
                sqlViewer.printResultatConsole(resultatExceptionUnhandled);
            }
        }
        if (resultat.isNotError()) {
            message = MessagesBuilder.getMessagesProperty("sqlviewer.sql.execute.ok",
                    new String[]{mpdrModel.getNamePath()});
        } else {
            message = MessagesBuilder.getMessagesProperty("sqlviewer.sql.execute.abort",
                    new String[]{mpdrModel.getNamePath()});

        }
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));
        DialogMessage.showOk(sqlViewer, message);
        ViewLogsManager.printResultat(resultat);
    }


}
