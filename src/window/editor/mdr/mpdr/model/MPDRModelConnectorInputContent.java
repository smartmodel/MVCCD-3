package window.editor.mdr.mpdr.model;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConManager;
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
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.Connection;

public class MPDRModelConnectorInputContent extends MPDRModelInputContent implements ActionListener {


    private JLabel labelConnectorLienProg;
    private SComboBox fieldConnectorLienProg;
    private JLabel labelConnectorUserName;
    private STextField fieldConnectorUserName;



    private SButton btnConnectorTest;


    public MPDRModelConnectorInputContent(MPDRModelInput mpdrModelInput) {
        super(mpdrModelInput);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();


        labelConnectorLienProg = new JLabel("Nom du connecteur : ");
        fieldConnectorLienProg = new SComboBox(this, labelConnectorLienProg);

        fieldConnectorLienProg.setToolTipText("Connecteur de la liste définie dans les préférences de l'application");
        fieldConnectorLienProg.addItemListener(this);
        fieldConnectorLienProg.addFocusListener(this);

        fieldConnectorLienProg.addItem(withoutItem);

        labelConnectorUserName = new JLabel("Nom utilisateur : ");
        fieldConnectorUserName = new STextField(this, labelConnectorUserName);
        fieldConnectorUserName.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldConnectorUserName.setToolTipText("Nom d'utilisateur du connecteur...");
        fieldConnectorUserName.setReadOnly(true);


        btnConnectorTest = new SButton("Test de connexion du connecteur");
        btnConnectorTest.addActionListener(this);
        btnConnectorTest.setEnabled(false);


        super.getSComponents().add(fieldConnectorLienProg);
        super.getSComponents().add(fieldConnectorUserName);
        super.getSComponents().add(btnConnectorTest);
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
        panelConnection.add(labelConnectorLienProg, gbcA);
        gbcA.gridx++;
        panelConnection.add(fieldConnectorLienProg, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelConnection.add(labelConnectorUserName, gbcA);
        gbcA.gridx++;
        panelConnection.add(fieldConnectorUserName, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelConnection.add(btnConnectorTest, gbcA);

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        super.loadDatas(mvccdElementCrt);
        MPDRModel mpdrModel = (MPDRModel) mvccdElementCrt;

        // Connecteur
        ConConnector conConnector = ConnectionsService.getConConnectorByLienProg(mpdrModel.getConnectorLienProg());

        if (conConnector != null) {
            ConConnection conConnection = (ConConnection) conConnector.getParent();
            SComboBoxService.selectByText(fieldConnectionLienProg, conConnection.getName());
            for (ConConnector conConnectorSiblings : ConManager.instance().getConConnectors(conConnection)) {
                fieldConnectorLienProg.addItem(conConnector.getName());
            }
            SComboBoxService.selectByText(fieldConnectorLienProg, conConnector.getName());
            setFieldConnectorUserName();
            enabledBtnTestConnector();
        } else {
            SComboBoxService.selectByText(fieldConnectionLienProg, withoutItem);
            boolean c1 = StringUtils.isNotEmpty(mpdrModel.getConnectorLienProg());
            boolean c2 = conConnector == null;
            if (c1 && c2) {
                String message = MessagesBuilder.getMessagesProperty("editor.mpdr.load.connector.unknow",
                        new String[] {mpdrModel.getConnectorLienProg(), mpdrModel.getNamePath()});
                ViewLogsManager.printMessageAndDialog(getEditor(), message, WarningLevel.INFO);
                fieldConnectorLienProg.forceUpdated();
            }
            SComboBoxService.selectByText(fieldConnectorLienProg, withoutItem);
        }

        //UserName de connexion
        setFieldConnectorUserName();

        //Test de connexion
        enabledBtnTestConnector();

        //URL de connexion
        setFieldConnectionURL();

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        super.saveDatas(mvccdElement);
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;

        if (fieldConnectorLienProg.checkIfUpdated()) {
            if (!fieldConnectorLienProg.isSelectedEmpty()) {
                mpdrModel.setConnectorLienProg(getConConnectorByFieldConnectorLienProg().getLienProg());
            } else {
                mpdrModel.setConnectorLienProg(null);
            }
        }

    }


    @Override
    protected void changeFieldSelected(ItemEvent e) {
        super.changeFieldSelected(e);
        Object source = e.getSource();
        if (source == fieldConnectionLienProg) {
            resetItemsFieldConnectorLienProg();
        } if (source == fieldConnectorLienProg) {
            setFieldConnectorUserName();
            enabledBtnTestConnector();
        }
    }



    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        super.changeFieldDeSelected(e);
    }


    private void setFieldConnectorUserName() {
        ConConnector conConnector = getConConnectorByFieldConnectorLienProg();
        if (conConnector != null) {
            fieldConnectorUserName.setText(conConnector.getUserName());
        } else {
            fieldConnectorUserName.setText("");
        }
    }

    private ConConnector getConConnectorByFieldConnectorLienProg(){
        String connectorName = (String) fieldConnectorLienProg.getSelectedItem();
        ConConnection conConnection = getConConnectionByFieldConnectionLienProg();
        if (conConnection != null) {
            ConConnector conConnector = ConManager.instance().getConConnectorByName(
                    getConConnectionByFieldConnectionLienProg(), connectorName);
            return conConnector;
        }
        return null;
    }

    private void enabledBtnTestConnector() {
        ConConnector conConnector = getConConnectorByFieldConnectorLienProg();
        btnConnectorTest.setEnabled(conConnector != null);
    }

    private void resetItemsFieldConnectorLienProg() {
        ConConnection conConnection = getConConnectionByFieldConnectionLienProg();
        fieldConnectorLienProg.removeAllItems();
        fieldConnectorLienProg.addItem(withoutItem);
        if (conConnection != null) {
            for (ConConnector conConnectorSiblings : ConManager.instance().getConConnectors(conConnection)) {
                fieldConnectorLienProg.addItem(conConnectorSiblings.getName());
            }
        }
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

            if (source == btnConnectorTest) {
                propertyAction = "editor.mpdr.connection.btn.exception.test";

                new Thread(new Runnable() {
                    public void run() {
                        actionTestConnector();
                    }
                }).start();

            }


        } catch (Exception exception) {
            ExceptionService.exceptionUnhandled(exception, getEditor(), mvccdElementForCatchException,
                    propertyMessage, propertyAction);

        }
    }

    private void actionTestConnector() {
        ConConnector conConnector = getConConnectorByFieldConnectorLienProg();
        Connection connection = ConnectionsService.actionTestIConConnectionOrConnector(getEditor(),
                true,
                conConnector);
     }

}


