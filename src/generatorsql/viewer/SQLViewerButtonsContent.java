package generatorsql.viewer;

import comparatorsql.fetcher.oracle.DbFetcherOracle;
import connections.ConConnection;
import connections.ConConnector;
import connections.ConDBMode;
import connections.services.ConnectionsService;
import console.ViewLogsManager;
import console.WarningLevel;
import exceptions.CodeApplException;
import exceptions.service.ExceptionService;
import generatorsql.MPDRGenerateSQLUtil;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import messages.MessagesBuilder;
import mpdr.MPDRModel;
import mpdr.oracle.MPDROracleColumn;
import mpdr.oracle.MPDROracleModel;
import mpdr.oracle.MPDROracleTable;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import treatment.services.TreatmentService;
import utilities.Trace;
import utilities.UtilDivers;
import utilities.files.FileRead;
import utilities.files.UtilFiles;
import utilities.window.PanelContent;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.texteditor.TextEditor;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class SQLViewerButtonsContent extends PanelContent implements IPanelInputContent, ActionListener {

    private SQLViewerButtons sqlViewerButtons;
    private SQLViewer sqlViewer;
    private MPDRModel mpdrModel;

    File sqlCreateFile = null;
    File sqlPopulateFile = null;


    private JPanel panelContent = new JPanel();
    private JPanel panelDDL = new JPanel();
    private JPanel panelDML = new JPanel();

    private SButton btnSynchronisationSGBDR;
    private SButton btnConnectionTest;
    private SButton btnConnectorTest;
    private SButton btnDDLExecute;
    private SButton btnDDLSave;
    private SButton btnDMLExecute;
    private SButton btnDMLEdit;
    private SButton btnClose;

    private JLabel labelDDLName;
    private STextField fieldDDLName;
    private JLabel labelDDLSaved;
    private STextField fieldDDLSaved;
    private JLabel labelDDLExecuted;
    private STextField fieldDDLExecuted;

    private JLabel labelDMLName;
    private STextField fieldDMLName;
    private JLabel labelDMLSaved;
    private STextField fieldDMLSaved;
    private JLabel labelDMLExecuted;
    private STextField fieldDMLExecuted;

    public SQLViewerButtonsContent(SQLViewerButtons sqlViewerButtons) {
        super(sqlViewerButtons);
        this.sqlViewerButtons = sqlViewerButtons;
        super.addContent(panelContent);
        sqlViewer = sqlViewerButtons.getSQLViewer();
        mpdrModel = sqlViewer.getMpdrModel();
        if ( MVCCDManager.instance().getFileProjectCurrent() != null) {
            sqlCreateFile = MPDRGenerateSQLUtil.sqlCreateFile(mpdrModel);
        }
        sqlPopulateFile = MPDRGenerateSQLUtil.sqlPopulateFile(mpdrModel);
        createContent();
        createPanelContent();
        loadDatas();
    }



    public void createContent() {

        btnSynchronisationSGBDR = new SButton("Synchronisation du SGBD-R");
        btnSynchronisationSGBDR.addActionListener(this);

        btnConnectionTest = new SButton("Test de connexion");
        btnConnectionTest.addActionListener(this);

        btnConnectorTest = new SButton("Test du connecteur");
        btnConnectorTest.addActionListener(this);

        btnDDLExecute = new SButton("Sauver et exécuter");
        btnDDLExecute.addActionListener(this);

        btnDDLSave = new SButton("Sauver");
        btnDDLSave.addActionListener(this);


        btnDMLExecute = new SButton("Exécuter");
        btnDMLExecute.addActionListener(this);

        btnDMLEdit = new SButton("Editer");
        btnDMLEdit.addActionListener(this);

        btnClose = new SButton("Fermer");
        btnClose.addActionListener(this);


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



        labelDMLName = new JLabel("Nom : ");
        fieldDMLName = new STextField(this, labelDMLName);
        fieldDMLName.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDMLName.setToolTipText("Nom du fichier script de peuplement SQL/DML...");
        fieldDMLName.setReadOnly(true);

        labelDMLSaved = new JLabel("Sauvé : ");
        fieldDMLSaved = new STextField(this, labelDMLSaved);
        fieldDMLSaved.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDMLSaved.setToolTipText("Si existant, date de la dernière modification...");
        fieldDMLSaved.setReadOnly(true);

        labelDMLExecuted = new JLabel("Exécuté : ");
        fieldDMLExecuted = new STextField(this, labelDMLExecuted);
        fieldDMLExecuted.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDMLExecuted.setToolTipText("Dernière exécution pour cette session...");
        fieldDMLExecuted.setReadOnly(true);

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

        gbc.gridy++;
        panelContent.add(btnSynchronisationSGBDR, gbc);

        gbc.gridy++ ;
        createPanelDDL();
        panelContent.add(panelDDL, gbc);

        gbc.gridy++ ;
        createPanelDML();
        panelContent.add(panelDML, gbc);
        
        gbc.gridy++ ;
        panelContent.add(btnClose, gbc);

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

        gbcD.gridx = 0;
        gbcD.gridy++ ;
        panelDDL.add(btnDDLExecute, gbcD);

        gbcD.gridx++;
        panelDDL.add(btnDDLSave, gbcD);
    }


    private void createPanelDML() {
        Border border = BorderFactory.createLineBorder(Color.black);
        TitledBorder panelDMLBorder = BorderFactory.createTitledBorder(border, "Script SQL/DML");
        panelDML.setBorder(panelDMLBorder);

        panelDML.setLayout(new GridBagLayout());
        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.anchor = GridBagConstraints.NORTHWEST;
        gbcD.insets = new Insets(10, 10, 0, 0);

        gbcD.gridx = 0;
        gbcD.gridy = 0;
        gbcD.gridwidth = 1;
        gbcD.gridheight = 1;

        panelDML.add(labelDMLName , gbcD);
        gbcD.gridx++;
        panelDML.add(fieldDMLName, gbcD);

        gbcD.gridx = 0;
        gbcD.gridy++ ;
        panelDML.add(labelDMLSaved, gbcD);
        gbcD.gridx++;
        panelDML.add(fieldDMLSaved, gbcD);

        gbcD.gridx = 0;
        gbcD.gridy++ ;
        panelDML.add(labelDMLExecuted, gbcD);
        gbcD.gridx++;
        panelDML.add(fieldDMLExecuted, gbcD);

        gbcD.gridx = 0;
        gbcD.gridy++ ;
        panelDML.add(btnDMLExecute, gbcD);

        gbcD.gridx++;
        panelDML.add(btnDMLEdit, gbcD);
    }


    private void loadDatas()  {
        SQLViewer sqlViewer = sqlViewerButtons.getSQLViewer();
        MPDRModel mpdrModel = sqlViewer.getMpdrModel();

        if (sqlCreateFile != null) {
            fieldDDLName.setText(sqlCreateFile.getName());
        } else {
            fieldDDLName.setText("");
        }
        fieldDMLName.setText(sqlPopulateFile.getName());
        //setFieldDDLSaved();
        setFieldSaved(sqlCreateFile, fieldDDLSaved);
        setFieldSaved(sqlPopulateFile, fieldDMLSaved);
        fieldDDLExecuted.setText("");
    }
/*
    private void setFieldDDLSaved() {
        Date ddlDateLastModified = null;
        if (sqlCreateFile != null) {
            ddlDateLastModified = UtilFiles.getLastModifiedDate(sqlCreateFile);
        }
        if (ddlDateLastModified != null) {
            Date dateLastModified = ddlDateLastModified;
            String formattedDateLastModified = UtilDivers.dateHourFormatted(dateLastModified);
            fieldDDLSaved.setText(formattedDateLastModified);
        } else {
            fieldDDLSaved.setText("Inexistant");
        }
    }

 */


    private void setFieldSaved(File sqlFile, STextField fieldSaved) {
        Date ddlDateLastModified = null;
        if (sqlFile != null) {
            ddlDateLastModified = UtilFiles.getLastModifiedDate(sqlFile);
        }
        if (ddlDateLastModified != null) {
            Date dateLastModified = ddlDateLastModified;
            String formattedDateLastModified = UtilDivers.dateHourFormatted(dateLastModified);
            fieldSaved.setText(formattedDateLastModified);
        } else {
            fieldSaved.setText("Inexistant");
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
        new Thread(new Runnable() {
            public void run() {
                actionPerformedThread(actionEvent);
            }
        }).start();

    }
    public void actionPerformedThread(ActionEvent actionEvent) {

                ViewLogsManager.clear();
                String propertyMessage = "sqlviewer.btn.exception.new";
                String propertyAction = "";
                try {
                    Object source = actionEvent.getSource();

                    Connection connection = null;
                    if (source == btnConnectionTest) {
                        propertyAction = "editor.mpdr.connection.btn.exception.test";
                        //Resultat resultat = actionTestConnection(true, connection);
                        actionTestConnection(true);
                    }

                    if (source == btnSynchronisationSGBDR){
                        propertyAction = "editor.mpdr.connection.btn.exception.test";
                        /*
                        DbFetcher dbFetcher = new DbFetcher();
                        DbOracleStructure dbOracleStructure = dbFetcher.getDatabaseStructure();
                        OracleComparator oracleComparator = new OracleComparator(mpdrModel, dbOracleStructure);
                        oracleComparator.comparator(mpdrModel, dbOracleStructure);
                        MPDROracleModel mpdrModelOracleDb = MVCCDElementFactory.instance().createMPDRModelOracle(null);
                        */
                        //ATTENTION le passage en mode Debug efface le mot de passe enregistré pour la connection
                        /*Trace.println(mpdrModelOracleDb.toString());
                        Trace.println(mpdrModelOracleDb.getChilds().toString());*/
/*
                        DbFetcher2 dbFetcher2 = new DbFetcher2();
                        DbOracleStructure dbOracleStructure = dbFetcher2.getDatabaseStructure();
                        //OracleComparator oracleComparator = new OracleComparator(mpdrModel, dbOracleStructure);
                        //oracleComparator.comparator(mpdrModel, dbOracleStructure);
                        MPDROracleModel mpdrModelOracleDb = MVCCDElementFactory.instance().createMPDRModelOracle(null);
                        System.out.println(mpdrModelOracleDb.toString());
*/
                        connection = ConnectionsService.actionTestIConConnectionOrConnector(sqlViewer,
                                true,
                                sqlViewer.getConConnection());
                        //DbFetcherOracle dbFetcherOracle = new DbFetcherOracle();
                        DbFetcherOracle dbFetcherOracle = new DbFetcherOracle(sqlViewer.getConConnection(), connection);
                        dbFetcherOracle.fetch();


                        actionTestConnection(false);
                    }

                    if (source == btnConnectorTest) {
                        propertyAction = "editor.con.connector.btn.exception.test";
                        actionTestConnector(true);
                    }

                    if (source == btnDDLExecute) {
                        propertyAction = "sqlviewer.sql.ddl.execute.btn.exception.test";
                        actionDDLExecute();
                    }

                    if (source == btnDDLSave) {
                        propertyAction = "sqlviewer.save.btn.exception.test";
                        actionDDLSave(true);
                    }

                    if (source == btnDMLExecute) {
                        propertyAction = "sqlviewer.sql.dml.execute.btn.exception.test";
                        actionDMLExecute();
                    }

                    if (source == btnDMLEdit) {
                        propertyAction = "sqlviewer.sql.dml.view.btn.exception.test";
                        actionDMLView();
                    }

                    if (source == btnClose) {
                        sqlViewer.dispose();
                    }
                } catch (Exception exception) {
                    ExceptionService.exceptionUnhandled(exception, sqlViewer, mpdrModel,
                            propertyMessage, propertyAction);
                }
    }

    private Connection actionTestConnection(boolean autonomous) {
         ConConnection conConnection = sqlViewer.getConConnection();
        return ConnectionsService.actionTestIConConnectionOrConnector(sqlViewer,
                autonomous,
                conConnection);
   }


    private Connection actionTestConnector(boolean autonomous) {
        ConConnector conConnector = sqlViewer.getConConnector();
        return ConnectionsService.actionTestIConConnectionOrConnector(sqlViewer,
                autonomous,
                conConnector);
    }


    private boolean actionDDLSave(boolean autonomous)  {
        SQLViewer sqlViewer = sqlViewerButtons.getSQLViewer();
        MPDRModel mpdrModel = sqlViewer.getMpdrModel();

        boolean ok = true ;
        File sqlCreateFile = MPDRGenerateSQLUtil.sqlCreateFile(mpdrModel);

        try {
            String message = MessagesBuilder.getMessagesProperty("sqlviewer.sql.ddl.file.saved.start", sqlCreateFile.getPath());
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            MPDRGenerateSQLUtil.generateSQLFile(mpdrModel, sqlViewer.getSqlViewerCodeSQL().getSqlViewerCodeSQLContent().getCodeSQL());
        } catch (Exception e){
            ok = false ;
        }

        if (ok) {
            setFieldSaved(sqlCreateFile, fieldDDLSaved);
        }

        TreatmentService.treatmentFinish(sqlViewer, new String[] {sqlCreateFile.getPath()}, ok, autonomous,
                "sqlviewer.sql.ddl.file.saved.ok", "sqlviewer.sql.ddl.file.saved.abort") ;

        return ok;
    }

    private boolean actionDDLExecute() {

        String message = MessagesBuilder.getMessagesProperty("sqlviewer.sql.ddl.execute.start",
                new String[]{mpdrModel.getNamePath()});
        ViewLogsManager.printMessage(message, WarningLevel.INFO);

        // Sauvegarde du fichier de script
        boolean ok = actionDDLSave(false);

        // Exécution du code
        if (ok) {
            String codeSQL = sqlViewer.getSqlViewerCodeSQL().getSqlViewerCodeSQLContent().getCodeSQL();
            ok = actionExecute(codeSQL, "Le script SQL-DDL n'a pas pu être exécuté :");
            if (ok) {
                String formattedHourExecuted = UtilDivers.hourFormatted(new Date());
                fieldDDLExecuted.setText(formattedHourExecuted);
            }
        }

        TreatmentService.treatmentFinish(sqlViewer, new String[]{mpdrModel.getNamePath()}, ok, true,
                "sqlviewer.sql.ddl.execute.ok", "sqlviewer.sql.ddl.execute.abort") ;
        return ok;
    }

    private boolean actionDMLExecute() {

        String message = MessagesBuilder.getMessagesProperty("sqlviewer.sql.dml.execute.start",
                new String[]{mpdrModel.getNamePath()});
        ViewLogsManager.printMessage(message, WarningLevel.INFO);

        String codeSQL = FileRead.readToString(sqlPopulateFile);
        boolean ok = actionExecute(codeSQL, "Le script SQL-DML n'a pas pu être exécuté :");
        if (ok) {
            String formattedHourExecuted = UtilDivers.hourFormatted(new Date());
            fieldDMLExecuted.setText(formattedHourExecuted);
        }

        TreatmentService.treatmentFinish(sqlViewer, new String[]{mpdrModel.getNamePath()}, ok, true,
                "sqlviewer.sql.dml.execute.ok", "sqlviewer.sql.dml.execute.abort") ;
        return ok;
    }

    private boolean actionExecute(String codeToExecute, String messageError ) {
        boolean ok = true;
        String message = MessagesBuilder.getMessagesProperty("con.connection.start.test");
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        Connection connection = null;
        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTION) {
            connection = actionTestConnection(false);
        }
        if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
            connection = actionTestConnector(false);
        }
        // Exécution du script
        if (connection != null) {
            String commandCopy = "";
            try {
                //TODO-0 A paramétrer
                String[] commands = codeToExecute.split(mpdrModel.getDb().getDelimiterInstructions());
                for (String command : commands) {
                    commandCopy = command;
                    command = MPDRGenerateSQLUtil.clearCommandSQL(command);
                    if (StringUtils.isNotEmpty(command)) {
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(command);
                        statement.close();
                    }
                }
            } catch (Exception e) {
                ok = false;
                ViewLogsManager.catchException(e, messageError + System.lineSeparator() + commandCopy);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    ViewLogsManager.catchException(e, "La connexion n''a pas pu être coupée");
                    e.printStackTrace();
                }
            }
        } else {
            ok = false;
        }
        return ok;
    }

/*
    private boolean actionDDLExecute() {
        boolean ok = true ;

        String message = MessagesBuilder.getMessagesProperty("sqlviewer.sql.execute.start",
                        new String[]{mpdrModel.getNamePath()});
        ViewLogsManager.printMessage(message, WarningLevel.INFO);

        // Sauvegarde du fichier de script
        String codeSQL = sqlViewer.getSqlViewerCodeSQL().getSqlViewerCodeSQLContent().getCodeSQL();
        ok = actionDDLSave(false);

        // Etablissement de la connexion
        if (ok) {
            message = MessagesBuilder.getMessagesProperty("con.connection.start.test");
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            Connection connection = null;
            if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTION) {
                connection = actionTestConnection(false);
            }
            if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
                connection = actionTestConnector(false);
            }

            // Exécution du script
            if (connection != null) {
                String commandCopy = "";
                try {
                    String generateSQLCode = sqlViewer.getSqlViewerCodeSQL().getSqlViewerCodeSQLContent().getCodeSQL();
                    //TODO-0 A paramétrer
                    String[] commands = generateSQLCode.split(mpdrModel.getDb().getDelimiterInstructions());
                    for (String command : commands) {
                        commandCopy = command;
                        command = MPDRGenerateSQLUtil.clearCommandSQL(command);
                        if (StringUtils.isNotEmpty(command)) {
                                Statement statement = connection.createStatement();
                                statement.executeUpdate(command);
                                statement.close();
                        }
                    }

                    String formattedHourExecuted = UtilDivers.hourFormatted(new Date());
                    fieldDDLExecuted.setText(formattedHourExecuted);
                } catch (Exception e) {
                    ok = false;
                    ViewLogsManager.catchException(e, "Le script SQL-DDL n'a pas pu être exécuté :"
                    + System.lineSeparator() + commandCopy);
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        ViewLogsManager.catchException(e, "La connexion n''a pas pu être coupée");
                        e.printStackTrace();
                    }
                }
            } else {
                ok = false;
            }
        }

        TreatmentService.treatmentFinish(sqlViewer, new String[]{mpdrModel.getNamePath()}, ok, true,
                "sqlviewer.sql.execute.ok", "sqlviewer.sql.execute.abort") ;
        return ok;
    }
*/


    private void actionDMLView() {
        /*
        SQLViewer sqlViewer = sqlViewerButtons.getSQLViewer();
        String codeSQL = ReadFile.fileToString(sqlPopulateFile);
        DialogMessage.showOk(sqlViewer ,codeSQL, "Vue du script de peuplement");

         */

        SQLViewer sqlViewer = sqlViewerButtons.getSQLViewer();
        TextEditor fen = new TextEditor(sqlViewer, sqlPopulateFile);
        fen.setVisible(true);

    }

    public SButton getBtnDDLExecute() {
        return btnDDLExecute;
    }

    public SButton getBtnDDLSave() {
        return btnDDLSave;
    }
}
