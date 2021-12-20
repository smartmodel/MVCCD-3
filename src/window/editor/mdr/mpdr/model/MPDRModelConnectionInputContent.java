package window.editor.mdr.mpdr.model;

import connections.ConConnection;
import connections.services.ConnectionsService;
import console.ViewLogsManager;
import console.WarningLevel;
import exceptions.service.ExceptionService;
import main.MVCCDElement;
import messages.MessagesBuilder;
import mpdr.MPDRModel;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.SButton;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.Connection;

public class MPDRModelConnectionInputContent extends MPDRModelInputContent implements ActionListener {

    private JLabel labelConnectionUserName;
    private STextField fieldConnectionUserName;
    private SButton btnConnectionTest;


    public MPDRModelConnectionInputContent(MPDRModelInput mpdrModelInput) {
        super(mpdrModelInput);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();

        labelConnectionUserName = new JLabel("Nom utilisateur : ");
        fieldConnectionUserName = new STextField(this, labelConnectionUserName);
        fieldConnectionUserName.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldConnectionUserName.setToolTipText("Nom d'utilisateur ...");
        fieldConnectionUserName.setReadOnly(true);


        btnConnectionTest = new SButton("Test de connexion");
        btnConnectionTest.addActionListener(this);
        btnConnectionTest.setEnabled(false);


        super.getSComponents().add(fieldConnectionUserName);
        super.getSComponents().add(btnConnectionTest);
        createPanelMaster();
    }

    protected void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);
        super.createPanelMaster(gbc);

        createPanelConnection();
        gbc.gridwidth = 6;

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(panelConnection, gbc);


        this.add(panelInputContentCustom);
    }

    private void createPanelConnection() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelConnection,
                "Connexion");

        super.createPanelConnection(gbcA);

            gbcA.gridx = 0;
            gbcA.gridy++;
            panelConnection.add(labelConnectionUserName, gbcA);
            gbcA.gridx++;
            panelConnection.add(fieldConnectionUserName, gbcA);
            gbcA.gridx = 0;
            gbcA.gridy++;
            panelConnection.add(btnConnectionTest, gbcA);

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        super.loadDatas(mvccdElementCrt);
        MPDRModel mpdrModel = (MPDRModel) mvccdElementCrt;

        // Connexion
        ConConnection conConnection = ConnectionsService.getConConnectionByLienProg(mpdrModel.getConnectionLienProg());

        if (conConnection != null) {
            SComboBoxService.selectByText(fieldConnectionLienProg, conConnection.getName());
        } else {
            boolean c1 = StringUtils.isNotEmpty(mpdrModel.getConnectionLienProg());
            boolean c2 = conConnection == null;
            if (c1 && c2) {
                String message = MessagesBuilder.getMessagesProperty("editor.mpdr.load.connection.unknow",
                        new String[] {mpdrModel.getConnectionLienProg(), mpdrModel.getNamePath()});
                ViewLogsManager.printMessageAndDialog(getEditor(), message, WarningLevel.INFO);
            }
            SComboBoxService.selectByText(fieldConnectionLienProg, withoutItem);
            fieldConnectionLienProg.forceUpdated();
        }

        //UserName de connexion
        setFieldConnectionUserName();

        //Test de connexion
        enabledBtnTestConnection();

        //URL de connexion
        setFieldConnectionURL();
    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        super.saveDatas(mvccdElement);
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;

        if (fieldConnectionLienProg.checkIfUpdated()) {
            if (!fieldConnectionLienProg.isSelectedEmpty()) {
                mpdrModel.setConnectionLienProg(getConConnectionByFieldConnectionLienProg().getLienProg());
            } else {
                mpdrModel.setConnectionLienProg(null);
            }
        }

    }


    @Override
    protected void changeFieldSelected(ItemEvent e) {
        super.changeFieldSelected(e);
        Object source = e.getSource();
        if (source == fieldConnectionLienProg) {
            setFieldConnectionUserName();
            enabledBtnTestConnection();
        }
    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        super.changeFieldDeSelected(e);
    }


    private void setFieldConnectionUserName() {
        ConConnection conConnection = getConConnectionByFieldConnectionLienProg();
        if (conConnection != null) {
            fieldConnectionUserName.setText(conConnection.getUserName());
        } else {
            fieldConnectionUserName.setText("");
        }
    }

    private void enabledBtnTestConnection() {
        ConConnection conConnection = getConConnectionByFieldConnectionLienProg();
        btnConnectionTest.setEnabled(conConnection != null);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String propertyMessage = "";
        String propertyAction = "";
        MVCCDElement mvccdElementForCatchException = null;
        if (getEditor().getMode().equals(DialogEditor.NEW)) {
            // Pas de NEW
        } else {
            propertyMessage = "editor.mpdr.btn.exception.not.new" ;
            mvccdElementForCatchException = getEditor().getMvccdElementCrt();
        }
        try {
            Object source = actionEvent.getSource();

            if (source == btnConnectionTest) {
                propertyAction = "editor.mpdr.connection.btn.exception.test";

                new Thread(new Runnable() {
                    public void run() {
                        actionTestConnection();
                    }
                }).start();
           }
        } catch (Exception exception) {
            ExceptionService.exceptionUnhandled(exception, getEditor(), mvccdElementForCatchException,
                    propertyMessage, propertyAction);
        }
    }

    private void actionTestConnection() {
        ConConnection conConnection = getConConnectionByFieldConnectionLienProg();
        Connection connection = ConnectionsService.actionTestIConConnectionOrConnector(getEditor(),
                true,
                conConnection );
    }
}


