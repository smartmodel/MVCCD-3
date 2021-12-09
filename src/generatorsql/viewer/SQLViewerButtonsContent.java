package generatorsql.viewer;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConDBMode;
import connections.services.ConnectionsService;
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

    File sqlCreateFile = null;


    private JPanel panelContent = new JPanel();
    private JPanel panelDDL = new JPanel();
    private JPanel panelDML = new JPanel();

    private SButton btnConnectionTest;
    private SButton btnConnectorTest;
    private SButton btnExecute;
    private SButton btnSave;
    private SButton btnClose;

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
        if ( MVCCDManager.instance().getFileProjectCurrent() != null) {
            sqlCreateFile = GenerateSQLUtil.sqlCreateFile();
        }
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

    }


    private void loadDatas()  {
        SQLViewer sqlViewer = sqlViewerButtons.getSQLViewer();
        MPDRModel mpdrModel = sqlViewer.getMpdrModel();

        if (sqlCreateFile != null) {
            fieldDDLName.setText(sqlCreateFile.getName());
        } else {
            fieldDDLName.setText("");
        }
        setFieldDDLSaved();
        fieldDDLExecuted.setText("");
    }

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

    /*
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String propertyMessage = "sqlviewer.btn.exception.new";
        String propertyAction = "";
        try {
            Object source = actionEvent.getSource();

            Connection connection = null;
            if (source == btnConnectionTest) {
                propertyAction = "editor.mpdr.connection.btn.exception.test";
                //Resultat resultat = actionTestConnection(true, connection);
                actionTestConnection(true, connection);
            }

            if (source == btnConnectorTest) {
                propertyAction = "editor.con.connector.btn.exception.test";
                actionTestConnector(true, connection);
            }

            if (source == btnExecute) {
                propertyAction = "sqlviewer.execute.btn.exception.test";
                new Thread(new Runnable() {
                    public void run() {
                        actionExecute();
                    }
                }).start();

            }

            if (source == btnSave) {
                propertyAction = "sqlviewer.save.btn.exception.test";
                new Thread(new Runnable() {
                    public void run() {
                        actionSave(true);
                    }
                }).start();
                // Mise à jour de l'indicateur de dernière modif.
                setFieldDDLSaved();
            }
        } catch (Exception exception) {
            ExceptionService.exceptionUnhandled(exception, sqlViewer, mpdrModel,
                    propertyMessage, propertyAction);
        }
    }

     */

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new Thread(new Runnable() {
            public void run() {
                actionPerformedThread(actionEvent);
            }
        }).start();

    }
    public void actionPerformedThread(ActionEvent actionEvent) {

                String propertyMessage = "sqlviewer.btn.exception.new";
                String propertyAction = "";
                try {
                    Object source = actionEvent.getSource();

                    Connection connection = null;
                    if (source == btnConnectionTest) {
                        propertyAction = "editor.mpdr.connection.btn.exception.test";
                        //Resultat resultat = actionTestConnection(true, connection);
                        actionTestConnection(true, connection);
                    }

                    if (source == btnConnectorTest) {
                        propertyAction = "editor.con.connector.btn.exception.test";
                        actionTestConnector(true, connection);
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
                    if (source == btnClose) {
                        sqlViewer.dispose();
                    }
                } catch (Exception exception) {
                    ExceptionService.exceptionUnhandled(exception, sqlViewer, mpdrModel,
                            propertyMessage, propertyAction);
                }
    }

    private Resultat actionTestConnection(boolean autonomous, Connection connection) {
        ConConnection conConnection = sqlViewer.getConConnection();
        return ConnectionsService.actionTestIConConnectionOrConnector(sqlViewer,
                true,
                conConnection,
                connection);
   }


    private Resultat actionTestConnector(boolean autonomous, Connection connection) {
        ConConnector conConnector = sqlViewer.getConConnector();
        return ConnectionsService.actionTestIConConnectionOrConnector(sqlViewer,
                autonomous,
                conConnector,
                connection );
    }


    private Resultat actionSave(boolean autonomous)  {
        Resultat resultat = new Resultat();
        if (autonomous) {
            resultat.setPrintImmediatelyForResultat(true);
        }
        resultat.addResultat(GenerateSQLUtil.generateSQLFile(sqlViewer.getSqlViewerCodeSQL().getSqlViewerCodeSQLContent().getCodeSQL()));
        String message ;
        if (resultat.isNotError()) {
            File sqlCreateFile = GenerateSQLUtil.sqlCreateFile();
            setFieldDDLSaved();
            message = MessagesBuilder.getMessagesProperty("sqlviewer.dml.file.saved.ok", sqlCreateFile);
        } else {
            message = MessagesBuilder.getMessagesProperty("sqlviewer.dml.file.saved.error", sqlCreateFile);
        }
        resultat.add(new ResultatElement (message, ResultatLevel.INFO));
        if (autonomous) {
            DialogMessage.showOk(sqlViewer, message);
        }
        return resultat;
    }


    private void actionExecute() {
        Resultat resultat = new Resultat();
        resultat.setPrintImmediatelyForResultat(true);
        resultat.showViewer(); // ResultatViewer est désactivé
                String message = MessagesBuilder.getMessagesProperty("sqlviewer.sql.execute.start",
                        new String[]{mpdrModel.getNamePath()});
                resultat.add(new ResultatElement(message, ResultatLevel.INFO));

                // Sauvegarde du fichier de script
                String codeSQL = sqlViewer.getSqlViewerCodeSQL().getSqlViewerCodeSQLContent().getCodeSQL();
                Resultat resultatSave = actionSave(false);
                resultat.addResultat(resultatSave);

                // Etablissement de la connexion
                message = MessagesBuilder.getMessagesProperty("con.connection.start.test");
                resultat.add(new ResultatElement(message, ResultatLevel.INFO));
                Connection connection = null;
                if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTION) {
                    resultat.addResultat(actionTestConnection(false, connection));
                }
                if (PreferencesManager.instance().getApplicationPref().getCON_DB_MODE() == ConDBMode.CONNECTOR) {
                    resultat.addResultat(actionTestConnector(false, connection));
                }

                // Exécution du script
                if (connection != null) {
                    try {
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(sqlViewer.getSqlViewerCodeSQL().getSqlViewerCodeSQLContent().getCodeSQL());
                        statement.close();
                        String formattedHourExecuted = UtilDivers.hourFormatted(new Date());
                        fieldDDLExecuted.setText(formattedHourExecuted);
                    } catch (Exception e) {
                        resultat.addExceptionUnhandled(e);
                    }
                }

                // Quittance de fin de traitement
                if (resultat.isNotError() && (connection != null)) {
                    message = MessagesBuilder.getMessagesProperty("sqlviewer.sql.execute.ok",
                            new String[]{mpdrModel.getNamePath()});
                } else {
                    message = MessagesBuilder.getMessagesProperty("sqlviewer.sql.execute.abort",
                            new String[]{mpdrModel.getNamePath()});

                }
                resultat.add(new ResultatElement(message, ResultatLevel.INFO));
                DialogMessage.showOk(sqlViewer, message);
                resultat.hideViewer();

   }


    public SButton getBtnExecute() {
        return btnExecute;
    }

    public SButton getBtnSave() {
        return btnSave;
    }
}
