package window.editor.connections.connector;

import application.ApplElement;
import application.services.ApplElementConvert;
import connections.ConConnection;
import connections.ConConnector;
import connections.ConManager;
import connections.services.ConConnectorService;
import console.ViewLogsManager;
import exceptions.service.ExceptionService;
import main.MVCCDElement;
import mcd.services.MCDUtilService;
import messages.MessagesBuilder;
import preferences.Preferences;
import resultat.ResultatLevel;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.util.ArrayList;

public class ConConnectorInputContent extends PanelInputContent implements ActionListener {

    protected JPanel panelConnection = new JPanel ();

    private JLabel labelName;
    private STextField fieldName;

    private JLabel labelConnection;
    private STextField fieldConnection;
    private JLabel labelURL;
    private STextField fieldURL;

    private JLabel labelUserName;
    private STextField fieldUserName;
    private JLabel labelUserPW;
    private SPasswordField fieldUserPW;
    private SButton btnUserPWClear;
    private JLabel labelUserPWClear;
    private STextField fieldUserPWClear;

    private JLabel labelSaveUserPW;
    private SCheckBox fieldSaveUserPW;

    private SButton btnTest;
    private ConConnection conConnectionParent ;

    public ConConnectorInputContent(ConConnectorInput conConnectorInput)     {
        super(conConnectorInput);
        if (getEditor().getMode().equals(DialogEditor.NEW)){
            conConnectionParent = (ConConnection) getEditor().getMvccdElementParent();
        } else {
            conConnectionParent = (ConConnection) getEditor().getMvccdElementCrt().getParent();
        }
    }


    public ConConnectorInputContent(MVCCDElement element)     {
        super(null);
        elementForCheckInput = element;
        boolean notBatch = panelInput != null;
        conConnectionParent =  (ConConnection) ((ConConnector) elementForCheckInput).getParent();
    }


    @Override
    protected void enabledContent() {
       btnTest.setEnabled(checkDatas(null));

    }

    @Override
    public void createContentCustom() {
        labelName = new JLabel("Nom : ");
        fieldName = new STextField(this, labelName);
        fieldName.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldName.getDocument().addDocumentListener(this);
        fieldName.addFocusListener(this);
        fieldName.setCheckPreSave(true);

        labelConnection = new JLabel("Nom : ");
        fieldConnection = new STextField(this, labelConnection);
        fieldConnection.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldConnection.setToolTipText("Connexion parent...");
        fieldConnection.setReadOnly(true);

        labelURL = new JLabel("URL de connexion : ");
        fieldURL = new STextField(this, labelURL);
        fieldURL.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldURL.setToolTipText("Chaîne de connexion construite d'après la connexion parent...");
        fieldURL.setReadOnly(true);

        labelUserName = new JLabel("Nom utilisateur : ");
        fieldUserName = new STextField(this, labelUserName);
        fieldUserName.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldUserName.getDocument().addDocumentListener(this);
        fieldUserName.addFocusListener(this);

        labelUserPW = new JLabel("PW utilisateur : ");
        fieldUserPW = new SPasswordField(this, labelUserPW);
        fieldUserPW.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldUserPW.getDocument().addDocumentListener(this);
        fieldUserPW.addFocusListener(this);
        fieldUserPW.setEchoChar('*');

        btnUserPWClear = new SButton("Voir");
        btnUserPWClear.addActionListener(this);
        btnUserPWClear.setEnabled(true);

        fieldUserPWClear = new STextField(this);
        fieldUserPWClear.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldUserPWClear.getDocument().addDocumentListener(this);
        fieldUserPWClear.addFocusListener(this);
        fieldUserPWClear.setEnabled(false);
        fieldUserPWClear.setVisible(false);

        labelSaveUserPW = new JLabel("Sauvegarde PW Utilisateur : ");
        fieldSaveUserPW = new SCheckBox(this, labelSaveUserPW);
        fieldSaveUserPW.setToolTipText("Le mot de passe utilisateur est sauvegardé avec la connexion ...");
        fieldSaveUserPW.addFocusListener(this);
        fieldSaveUserPW.addItemListener(this);

        btnTest = new SButton("Test de connexion du connecteur");
        btnTest.addActionListener(this);
        btnTest.setEnabled(false);

        super.getSComponents().add(fieldName);
        super.getSComponents().add(fieldUserName);
        super.getSComponents().add(fieldUserPW);
        super.getSComponents().add(btnUserPWClear);
        super.getSComponents().add(fieldUserPWClear);
        super.getSComponents().add(fieldSaveUserPW);
        super.getSComponents().add(btnTest);

    }

    @Override
    protected boolean changeField(DocumentEvent e) {
        Document doc = e.getDocument();
        if (doc == fieldName.getDocument()) {
            return checkDatasPreSave(fieldName);
        } else if (doc == fieldUserName.getDocument()) {
            return checkDatas(fieldUserName);
        } else if (doc == fieldUserPW.getDocument()) {
            //TODO-0 A voir getText()
            fieldUserPWClear.setText(fieldUserPW.getText());
            return checkDatas(fieldUserPW);
        } else {
            return true;
        }
    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }


    @Override
    public void focusGained(FocusEvent focusEvent) {

        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();

        if (source == fieldName) {
            checkDatasPreSave(fieldName);
        }
        if (source == fieldUserName) {
            checkDatas(fieldUserName);
        }
        if (source == fieldUserPW) {
            checkDatas(fieldUserPW);
        }
    }

    protected void createPanelMaster(GridBagConstraints gbc) {
        gbc.gridwidth = 6;
        gbc.gridx = 0;
        gbc.gridy = 0;
        createPanelConnection();
        
        panelInputContentCustom.add(panelConnection, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelName, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelUserName, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldUserName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelUserPW, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldUserPW, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(btnUserPWClear, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldUserPWClear, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelSaveUserPW, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldSaveUserPW, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(btnTest, gbc);


    }

    private void createPanelConnection() {
        Border border = BorderFactory.createLineBorder(Color.black);
        TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Connexion");
        panelConnection.setBorder(panelDataypeBorder);

        panelConnection.setLayout(new GridBagLayout());
        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.anchor = GridBagConstraints.NORTHWEST;
        gbcD.insets = new Insets(10, 10, 0, 0);

        gbcD.gridx = 0;
        gbcD.gridy = 0;
        gbcD.gridwidth = 1;
        gbcD.gridheight = 1;


        gbcD.gridwidth = 1;
        gbcD.gridx = 0;
        gbcD.gridy++;
        panelConnection.add(labelConnection, gbcD);
        gbcD.gridx++;
        panelConnection.add(fieldConnection, gbcD);

        gbcD.gridx = 0;
        gbcD.gridy++;
        panelConnection.add(labelURL, gbcD);
        gbcD.gridx++;
        panelConnection.add(fieldURL, gbcD);
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        ConConnector conConnector = (ConConnector) mvccdElementCrt;

        ConConnection parent = (ConConnection) conConnector.getParent();
        fieldConnection.setText(parent.getName()) ;
        fieldURL.setText(parent.getResourceURL()) ;

        fieldName.setText(conConnector.getName());
        fieldUserName.setText(conConnector.getUserName());
        fieldUserPW.setText(conConnector.getUserPW());
        fieldUserPWClear.setText(conConnector.getUserPW());
        fieldSaveUserPW.setSelected(conConnector.isSavePW());
    }

    @Override
    protected void initDatas() {
        ConConnection parent = (ConConnection) getEditor().getMvccdElementParent();
        fieldConnection.setText(parent.getName()) ;
        fieldURL.setText(parent.getResourceURL()) ;
        fieldName.setText("CC");
        fieldUserName.setText("TEST1");
        fieldUserPW.setText("TEST1");
        fieldSaveUserPW.setSelected(true);

        /*
        radDriverDefault.setSelected(true);
        fieldDriverDefault.setText(getEditor().getConDb().getDriverFileNamePathAbsolute());
        fieldDriverCustom.setText("");
        fieldHostName.setText("MM");
        fieldDbName.setText("DD");
        fieldSaveUserPW.setSelected(false);

         */
    }



    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {
        ConConnector conConnector = (ConConnector) mvccdElement;

        if (fieldName.checkIfUpdated()){
            conConnector.setName(fieldName.getText());
        }
        if (fieldUserName.checkIfUpdated()){
            conConnector.setUserName(fieldUserName.getText());
        }
        if (fieldUserPW.checkIfUpdated()){
            conConnector.setUserPW(fieldUserPW.getText());
        }
        if (fieldSaveUserPW.checkIfUpdated()){
            conConnector.setSavePW(fieldSaveUserPW.isSelected());
        }
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }

    public boolean checkDatasPreSave(SComponent sComponent) {
        boolean  ok = super.checkDatasPreSave(sComponent);

        boolean notBatch = panelInput != null;
        boolean unitaire = notBatch && (sComponent == fieldName);
        ok = checkName(unitaire) && ok   ;

        setPreSaveOk(ok);
        return ok;
    }



    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);


        if (ok) {
            boolean notBatch = panelInput != null;

            //TODO-1 Pas de contrôle de format UserName tant qu'une étude du format pour chaque BD n'est pas faite

            // Le password est optionnel

            boolean unitaire = notBatch && (sComponent == fieldUserName);
            ok = checkUserName(unitaire) ;

            // Le password est obligatoire dans un premier temps pour simplifier le développement
            // Ensuite, il faudra associer l'obligation fieldSaveUserPW
            unitaire = notBatch && (sComponent == fieldUserPW);
            ok = checkUserPW(unitaire) && ok;

        }

        return ok ;
    }



    protected boolean checkName(boolean unitaire){
        return checkName(unitaire, true);
    }


    protected boolean checkName(boolean unitaire, boolean mandatory){
        boolean ok = super.checkInput(fieldName, unitaire, MCDUtilService.checkNameAlone(
                ApplElementConvert.from(getApplBrothers()),
                fieldName.getText(),
                true,
                Preferences.CON_NAME_LENGTH,
                "naming.of.name",   // Le non
                "of.connector",     // La connexion
                "naming.a.brother.connector"));
        if (ok){
            ok = super.checkInput(fieldName, unitaire, MCDUtilService.checkExistLienProgInBrothers(
                    getApplBrothers(),
                    ConConnectorService.getLienProg(conConnectionParent, fieldName.getText())));
        }
        return ok ;
    }


    protected ArrayList<ApplElement> getApplBrothers(){
        if (panelInput != null) {
            if (getEditor().getMode().equals(DialogEditor.NEW)) {
                ArrayList<MVCCDElement> childs =  getEditor().getMvccdElementParent().getChilds();
                return ApplElementConvert.to(childs);
            } else {
                return ((ApplElement)getEditor().getMvccdElementCrt()).getApplBrothers();
            }
        } else {
            return ((ApplElement)elementForCheckInput).getApplBrothers();
        }
    }


    protected boolean checkUserName(boolean unitaire){
        return checkUserName(unitaire, true);
    }

    protected boolean checkUserName(boolean unitaire, boolean mandatory){
        // Amazon 0 - 65535
        return super.checkInput(fieldUserName, unitaire, MCDUtilService.checkString(
                fieldUserName.getText(),
                mandatory,
                null,
                null,
                "of.username",
                "of.connector"));
    }


    protected boolean checkUserPW(boolean unitaire){
        // A terme, mandatory sera associé à fieldSaveUserPW
        return checkUserPW(unitaire, true);
    }

    protected boolean checkUserPW(boolean unitaire, boolean mandatory){
        // Amazon 0 - 65535
        return super.checkInput(fieldUserName, unitaire, MCDUtilService.checkString(
                fieldUserName.getText(),
                mandatory,
                null,
                null,
                "of.userpw",
                "of.connector"));
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String propertyMessage = "";
        String propertyAction = "";
        MVCCDElement mvccdElementForCatchException = null;
        if (getEditor().getMode().equals(DialogEditor.NEW)) {
            propertyMessage = "editor.con.connector.btn.exception.new";
            mvccdElementForCatchException = getEditor().getMvccdElementParent();
        } else {
            propertyMessage = "editor.con.connector.btn.exception.not.new" ;
            mvccdElementForCatchException = getEditor().getMvccdElementCrt();
        }
        try {
            Object source = actionEvent.getSource();

            if (source == btnUserPWClear) {
                propertyAction = "editor.con.resource.btn.exception.pw.clear";
                fieldUserPWClear.setVisible(! fieldUserPWClear.isVisible());
                panelInputContentCustom.revalidate();
                panelInputContentCustom.repaint();
                btnUserPWClear.setText( (! fieldUserPWClear.isVisible()) ? "Voir" : "Cacher");
            }
            if (source == btnTest) {
                propertyAction = "editor.con.connector.btn.exception.test";
                actionTestConnector();
            }

       } catch (Exception exception) {
            ExceptionService.exceptionUnhandled(exception, getEditor(), mvccdElementForCatchException,
                    propertyMessage, propertyAction);

        }
    }


    private void actionTestConnector() {

        Connection connection = ConManager.createConnection(getEditor(),
                conConnectionParent,
                fieldUserName.getText(),
                fieldUserPW.getText() //TODO-0 a finaliser avec getPassword()
        );

        // S'il y a erreur, elle est levée directement par createConnection()
        String message = MessagesBuilder.getMessagesProperty("editor.con.connector.btn.test.ok");
        ViewLogsManager.printMessage(message, ResultatLevel.INFO);
        ViewLogsManager.dialogQuittance(getEditor(), message);
    }

    public ConConnectorEditor getEditor() {
        return (ConConnectorEditor) super.getEditor();
    }


}
