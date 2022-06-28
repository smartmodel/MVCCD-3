package window.editor.connections.connection;

import application.ApplElement;
import application.services.ApplElementConvert;
import connections.ConConnection;
import connections.ConDB;
import connections.ConDriverFileChooser;
import connections.ConIDDBName;
import connections.services.ConConnectionService;
import connections.services.ConnectionsService;
import exceptions.CodeApplException;
import exceptions.service.ExceptionService;
import main.MVCCDElement;
import mcd.services.MCDUtilService;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesOfApplicationSaverXml;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.*;
import utilities.window.scomponents.services.SComboBoxService;

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
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;

public abstract class ConConnectionInputContent extends PanelInputContent implements ActionListener {

    protected JPanel panelDriver = new JPanel ();
    protected JPanel panelUser = new JPanel ();
    protected JPanel panelDatabase = new JPanel ();
    protected JPanel panelHost = new JPanel ();

    private JLabel labelName;
    private STextField fieldName;

    private JLabel labelDriverDefault;
    private SRadioButton radDriverDefault;
    private JLabel labelDriverCustom;
    private SRadioButton radDriverCustom;
    private ButtonGroup radGroupDriver;

    //private SButton btnDriverDefault;
    private STextField fieldDriverDefault;
    private STextField fieldDriverCustom;
    private SButton btnDriverChoice;

    private JLabel labelHostName;
    private STextField fieldHostName;
    private JLabel labelPort;
    private STextField fieldPort;
    private SButton btnPortDefault;
    private JLabel labelDbName;
    private STextField fieldDbName;
    protected SComboBox fieldIdDbName;

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


    private JLabel labelConfExecScript;
    private SCheckBox fieldConfExecScript;

    private SButton btnTest;

    private ConDB conDB ;

    public ConConnectionInputContent(ConConnectionInput conConnectionInput)     {
        super(conConnectionInput);
        conDB = getEditor().getConDb();
    }


    public ConConnectionInputContent(MVCCDElement element)     {
        super(null);
        elementForCheckInput = element;
        boolean notBatch = panelInput != null;
        conDB = ((ConConnection) elementForCheckInput).getConDB();
    }




    @Override
    protected void enabledContent() {
    }


    public void enabledButtons() {
        super.enabledButtons();
        btnDriverChoice.setEnabled(radDriverCustom.isSelected());
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

        labelDriverDefault = new JLabel("Défaut ");
        radDriverDefault = new SRadioButton(this,labelDriverDefault);
        radDriverDefault.addItemListener(this);
        radDriverDefault.addFocusListener(this);

        labelDriverCustom = new JLabel("Personnalisé ");
        radDriverCustom = new SRadioButton(this, labelDriverCustom);
        radDriverCustom.addItemListener(this);
        radDriverCustom.addFocusListener(this);

        radGroupDriver = new ButtonGroup();
        radGroupDriver.add(radDriverDefault);
        radGroupDriver.add(radDriverCustom);

        fieldDriverDefault = new STextField(this);
        fieldDriverDefault.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDriverDefault.setToolTipText("Fichier jar de driver par défaut ...");
        fieldDriverDefault.getDocument().addDocumentListener(this);
        fieldDriverDefault.addFocusListener(this);
        fieldDriverDefault.setReadOnly(true);

        fieldDriverCustom = new STextField(this);
        fieldDriverCustom.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDriverCustom.setToolTipText("Fichier jar de driver personnel ...");
        fieldDriverCustom.getDocument().addDocumentListener(this);
        fieldDriverCustom.addFocusListener(this);
        fieldDriverCustom.setReadOnly(true);

        btnDriverChoice = new SButton(MessagesBuilder.getMessagesProperty("button.con.driver.choice"));
        btnDriverChoice.addActionListener(this);
        btnDriverChoice.setEnabled(false);

        labelHostName = new JLabel("Host Name : ");
        fieldHostName = new STextField(this, labelHostName);
        fieldHostName.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldHostName.setToolTipText("Nom de la machine hôte...");
        fieldHostName.getDocument().addDocumentListener(this);
        fieldHostName.addFocusListener(this);

        labelPort = new JLabel("Port : ");
        fieldPort = new STextField(this, labelPort);
        fieldPort.setPreferredSize((new Dimension(30, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldPort.setToolTipText("Port");
        fieldPort.getDocument().addDocumentListener(this);
        fieldPort.addFocusListener(this);

        btnPortDefault = new SButton(MessagesBuilder.getMessagesProperty("button.con.port.default"));
        btnPortDefault.addActionListener(this);

        labelDbName = new JLabel("Nom : ");
        fieldDbName = new STextField(this, labelDbName);
        fieldDbName.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDbName.setToolTipText("Nom de la base de données...");
        fieldDbName.getDocument().addDocumentListener(this);
        fieldDbName.addFocusListener(this);

        fieldIdDbName = new SComboBox(this);
        // Les items sont attribués par la classe spécialisée
        fieldIdDbName.addItemListener(this);
        fieldIdDbName.addFocusListener(this);

        labelURL = new JLabel("URL de connexion : ");
        fieldURL = new STextField(this, labelURL);
        fieldURL.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldURL.setToolTipText("Chaîne de connexion construite...");
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
        //TODO-1 Renoncement dans un premier pour simplifier le développement
        fieldSaveUserPW.setEnabled(true);


        labelConfExecScript = new JLabel("Conf. exéc. : ");
        fieldConfExecScript = new SCheckBox(this, labelConfExecScript);
        fieldConfExecScript.setToolTipText("Confirmation d'exécution des scripts ...");
        fieldConfExecScript.addFocusListener(this);
        fieldConfExecScript.addItemListener(this);

        btnTest = new SButton("Test de connexion");
        btnTest.addActionListener(this);
        btnTest.setEnabled(false);

        super.getSComponents().add(fieldName);
        super.getSComponents().add(radDriverDefault);
        super.getSComponents().add(radDriverCustom);
        super.getSComponents().add(fieldDriverDefault);
        super.getSComponents().add(fieldDriverCustom);
        super.getSComponents().add(btnDriverChoice);
        super.getSComponents().add(fieldHostName);
        super.getSComponents().add(fieldPort);
        super.getSComponents().add(btnPortDefault);
        super.getSComponents().add(fieldDbName);
        super.getSComponents().add(fieldIdDbName);
        super.getSComponents().add(fieldUserName);
        super.getSComponents().add(fieldUserPW);
        super.getSComponents().add(btnUserPWClear);
        super.getSComponents().add(fieldUserPWClear);
        super.getSComponents().add(fieldSaveUserPW);
        super.getSComponents().add(fieldConfExecScript);
        super.getSComponents().add(btnTest);

    }

    @Override
    protected boolean changeField(DocumentEvent e) {
        Document doc = e.getDocument();
        if (doc == fieldName.getDocument()) {
            return checkDatas(fieldName);
        } else if (doc == fieldHostName.getDocument()) {
            return checkDatas(fieldHostName);
        } else if (doc == fieldPort.getDocument()) {
            return checkDatas(fieldPort);
        } else if (doc == fieldDbName.getDocument()) {
            return checkDatas(fieldDbName);
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
        Object source = e.getSource();
        if (source == fieldIdDbName) {
            checkDatas(fieldIdDbName);
        }

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }


    @Override
    public void focusGained(FocusEvent focusEvent) {

        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();

        if (source == fieldName) {
            checkDatas(fieldName);
        }
        if (source == fieldHostName) {
            checkDatas(fieldHostName);
        }
        if (source == fieldPort) {
            checkDatas(fieldPort);
        }
        if (source == fieldDbName) {
            checkDatas(fieldDbName);
        }
        if (source == fieldIdDbName) {
            checkDatas(fieldIdDbName);
        }
        if (source == fieldUserName) {
            checkDatas(fieldUserName);
        }
        if (source == fieldUserPW) {
            checkDatas(fieldUserPW);
        }

    }

    protected void createPanelMaster(GridBagConstraints gbc) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInputContentCustom.add(labelName, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldName, gbc);

        gbc.gridwidth = 6;
        gbc.gridx = 0;
        gbc.gridy++;
        createPanelDriver();
        panelInputContentCustom.add(panelDriver, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        createPanelHost();
        panelInputContentCustom.add(panelHost, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        createPanelDatabase();
        panelInputContentCustom.add(panelDatabase, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelURL, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldURL, gbc);


        gbc.gridwidth = 6;
        gbc.gridx = 0;
        gbc.gridy++;
        createPanelUser();
        panelInputContentCustom.add(panelUser, gbc);


        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelConfExecScript, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldConfExecScript, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(btnTest, gbc);


    }


    protected void createPanelDriver() {
        Border border = BorderFactory.createLineBorder(Color.black);
        TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Driver file");
        panelDriver.setBorder(panelDataypeBorder);

        panelDriver.setLayout(new GridBagLayout());
        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.anchor = GridBagConstraints.NORTHWEST;
        gbcD.insets = new Insets(10, 10, 0, 0);

        gbcD.gridx = 0;
        gbcD.gridy = 0;
        gbcD.gridwidth = 1;
        gbcD.gridheight = 1;

        //panelDriver.add(btnDriverDefault, gbcD);
        panelDriver.add(labelDriverDefault, gbcD);
        gbcD.gridx++;
        panelDriver.add(radDriverDefault, gbcD);
        gbcD.gridx++;
        panelDriver.add(fieldDriverDefault, gbcD);

        gbcD.gridx = 0 ;
        gbcD.gridy++;
        panelDriver.add(labelDriverCustom, gbcD);
        gbcD.gridx++;
        panelDriver.add(radDriverCustom, gbcD);
        gbcD.gridx++;
        panelDriver.add(fieldDriverCustom, gbcD);
        gbcD.gridx++;
        panelDriver.add(btnDriverChoice, gbcD);
    }

    protected void createPanelUser() {
        Border border = BorderFactory.createLineBorder(Color.black);
        TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Utilisateur");
        panelUser.setBorder(panelDataypeBorder);

        panelUser.setLayout(new GridBagLayout());
        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.anchor = GridBagConstraints.NORTHWEST;
        gbcD.insets = new Insets(10, 10, 0, 0);

        gbcD.gridx = 0;
        gbcD.gridy = 0;
        gbcD.gridwidth = 1;
        gbcD.gridheight = 1;

        gbcD.gridx = 0;
        gbcD.gridy++;
        panelUser.add(labelUserName, gbcD);
        gbcD.gridx++;
        panelUser.add(fieldUserName, gbcD);

        gbcD.gridx = 0;
        gbcD.gridy++;
        panelUser.add(labelUserPW, gbcD);
        gbcD.gridx++;
        panelUser.add(fieldUserPW, gbcD);
        gbcD.gridx++;
        panelUser.add(btnUserPWClear, gbcD);
        gbcD.gridx++;
        panelUser.add(fieldUserPWClear, gbcD);

        gbcD.gridx = 0;
        gbcD.gridy++;
        panelUser.add(labelSaveUserPW, gbcD);
        gbcD.gridx++;
        panelUser.add(fieldSaveUserPW, gbcD);

    }

    protected void createPanelDatabase() {
        Border border = BorderFactory.createLineBorder(Color.black);
        TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Database");
        panelDatabase.setBorder(panelDataypeBorder);

        panelDatabase.setLayout(new GridBagLayout());
        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.anchor = GridBagConstraints.NORTHWEST;
        gbcD.insets = new Insets(10, 10, 0, 0);

        gbcD.gridx = 0;
        gbcD.gridy = 0;
        gbcD.gridwidth = 1;
        gbcD.gridheight = 1;


        panelDatabase.add(fieldIdDbName, gbcD);
        gbcD.gridx++;
        panelDatabase.add(labelDbName, gbcD);
        gbcD.gridx++;
        panelDatabase.add(fieldDbName, gbcD);
    }

    protected void createPanelHost() {
        Border border = BorderFactory.createLineBorder(Color.black);
        //TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Host");
        //panelHost.setBorder(panelDataypeBorder);

        panelHost.setLayout(new GridBagLayout());
        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.anchor = GridBagConstraints.NORTHWEST;
        gbcD.insets = new Insets(10, 10, 0, 0);

        gbcD.gridx = 0;
        gbcD.gridy = 0;
        gbcD.gridwidth = 1;
        gbcD.gridheight = 1;

        panelHost.add(labelHostName, gbcD);
        gbcD.gridx++;
        panelHost.add(fieldHostName, gbcD);
        gbcD.gridx++;
        panelHost.add(labelPort, gbcD);
        gbcD.gridx++;
        panelHost.add(fieldPort, gbcD);
        gbcD.gridx++;
        panelHost.add(btnPortDefault, gbcD);
    }



    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        ConConnection conConnection = (ConConnection) mvccdElementCrt;

        fieldName.setText(conConnection.getName());
        //fieldDriverDefault.setText(getEditor().getConDb().getDefaultDriverFileNamePathAbsolute());
        fieldDriverDefault.setText(conConnection.getConDB().getDefaultDriverFileNamePathAbsolute());

        radDriverDefault.setSelected(conConnection.isDriverDefault());
        radDriverCustom.setSelected(! conConnection.isDriverDefault());
        fieldDriverCustom.setText(conConnection.getDriverFileCustom());
        if (!radDriverDefault.isSelected()){
            btnDriverChoice.setEnabled(true);
        }

        fieldHostName.setText(conConnection.getHostName());
        fieldPort.setText(conConnection.getPort());
        fieldDbName.setText(conConnection.getDbName());
        SComboBoxService.selectByText(fieldIdDbName, conConnection.getConIDDBName().getText());
        fieldUserName.setText(conConnection.getUserName());
        fieldUserPW.setText(conConnection.getUserPW());
        fieldUserPWClear.setText(conConnection.getUserPW());
        fieldSaveUserPW.setSelected(conConnection.isSavePW());

        fieldConfExecScript.setSelected(conConnection.isConfExecScript());
    }

    @Override
    protected void initDatas() {
        fieldName.setText("");
        radDriverDefault.setSelected(true);
        fieldDriverDefault.setText(getEditor().getConDb().getDefaultDriverFileNamePathAbsolute());
        fieldDriverCustom.setText("");
        //TODO-0 Valeur par défaut tant que la sauvegarde XML n'est pas faite
        //fieldIdDbName est initialisé par la calsse spécialisée;
        fieldHostName.setText("");
        initPort();
        fieldDbName.setText("");
        // fieldIdDbName initialisé par la classe spécialisée
        fieldUserName.setText("");
        fieldUserPW.setText("");
        fieldUserPWClear.setText(fieldUserPW.getText()); //TODO-0 Voir getPassword
        fieldSaveUserPW.setSelected(true);

        fieldConfExecScript.setSelected(false);
    }

    private void initPort(){
        fieldPort.setText(((ConConnectionEditor)getEditor()).getConDb().getPortDefault());
    }

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {
        ConConnection conConnection = (ConConnection) mvccdElement;

        if (fieldName.checkIfUpdated()){
            conConnection.setName(fieldName.getText());
        }

        if (radDriverDefault.checkIfUpdated()){
            conConnection.setDriverDefault(radDriverDefault.isSelected());
        }

        if (fieldDriverCustom.checkIfUpdated()){
            conConnection.setDriverFileCustom(fieldDriverCustom.getText());
        }

        if (fieldHostName.checkIfUpdated()){
            conConnection.setHostName(fieldHostName.getText());
        }

        if (fieldPort.checkIfUpdated()){
            conConnection.setPort(fieldPort.getText());
        }

        if (fieldDbName.checkIfUpdated()){
            conConnection.setDbName(fieldDbName.getText());
        }


        if (fieldIdDbName.checkIfUpdated()){
            String text = (String) fieldIdDbName.getSelectedItem();
            conConnection.setConIDDBName(ConIDDBName.findByText(text));
        }

        if (fieldUserName.checkIfUpdated()){
            conConnection.setUserName(fieldUserName.getText());
        }
        if (fieldUserPW.checkIfUpdated()){
            conConnection.setUserPW(fieldUserPW.getText());
        }
        if (fieldSaveUserPW.checkIfUpdated()){
            conConnection.setSavePW(fieldSaveUserPW.isSelected());
        }

        if (fieldConfExecScript.checkIfUpdated()){
            conConnection.setConfExecScript(fieldConfExecScript.isSelected());
        }

        // Sauvegarde (fichier) des préférences d'application
        new PreferencesOfApplicationSaverXml().createFileApplicationPref();
        String message = MessagesBuilder.getMessagesProperty("preferences.connections.saved");
        new DialogMessage().showOk(getEditor(), message);
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

        String hostNameURL = null;
        String portURL = null;
        String dbNameURL = null;
        ConIDDBName iddbName = null;

        if (ok) {
            boolean notBatch = panelInput != null;

            boolean unitaire = notBatch && (sComponent == radDriverCustom);
            ok = checkDriverCustom(unitaire) && ok;

            unitaire = notBatch && (sComponent == fieldHostName);
            boolean hostNameOk = checkHostName(unitaire);
            hostNameURL = (hostNameOk)?fieldHostName.getText():null;
            ok = hostNameOk && ok;

            unitaire = notBatch && (sComponent == fieldPort);
            boolean portOk = checkPort(unitaire);
            portURL = (portOk)?fieldPort.getText():null;
            ok = portOk && ok;
            if (fieldIdDbName.getSelectedItem() != null) {
                String text = (String)fieldIdDbName.getSelectedItem();
                iddbName = ConIDDBName.findByText((String) fieldIdDbName.getSelectedItem());
            }

            unitaire = notBatch && (sComponent == fieldDbName);
            boolean dbNameOk = checkDbName(unitaire);
            dbNameURL = (dbNameOk)?fieldDbName.getText():null;
            ok = dbNameOk && ok;

            unitaire = notBatch && (sComponent == fieldUserName);
            ok = checkUserName(unitaire) && ok;

            // Le password est obligatoire dans un premier temps pour simplifier le développement
            // Ensuite, il faudra associer l'obligation fieldSaveUserPW
            unitaire = notBatch && (sComponent == fieldUserPW);
            ok = checkUserPW(unitaire) && ok;


        }

       fieldURL.setText(ConnectionsService.getResourceURL(conDB,
                hostNameURL, portURL, iddbName, dbNameURL));

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
                Preferences.NAME_NOTMODEL_REGEXPR,
                "naming.of.name",   // Le non
                "of.connection",     // La connexion
                "naming.a.sister.connexion"));
        if (ok){
            ok = super.checkInput(fieldName, unitaire, MCDUtilService.checkExistLienProgInBrothers(
                    getApplBrothers(),
                    ConConnectionService.getLienProg(conDB, fieldName.getText())));
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



    protected boolean checkDriverCustom(boolean unitaire){
        if ( radDriverCustom.isSelected()) {
            return super.checkInput(fieldDriverCustom, unitaire, MCDUtilService.checkString(
                    fieldDriverCustom.getText(),
                    true,
                    null,
                    null, // Le format est pris en charge par le sélecteur de fichier
                    "of.filename",
                    "of.driver"));
        } else {
            return true;
        }
    }
    protected boolean checkHostName(boolean unitaire){
        return checkHostName(unitaire, true);
    }

    protected boolean checkHostName(boolean unitaire, boolean mandatory){
        // Amazon 0 - 65535
        return super.checkInput(fieldHostName, unitaire, MCDUtilService.checkString(
                fieldHostName.getText(),
                mandatory,
                Preferences.CON_HOSTNAME_LENGTH,
                Preferences.CON_HOSTNAME_REGEXPR,
                "of.hostname",
                "of.connection"));

    }

    protected boolean checkPort(boolean unitaire){
        return checkPort(unitaire, true);
    }

    protected boolean checkPort(boolean unitaire, boolean mandatory){
        // Amazon 0 - 65535
        return super.checkInput(fieldPort, unitaire, MCDUtilService.checkInteger(
                fieldPort.getText(),
                true,
                Preferences.CON_PORT_MIN,
                Preferences.CON_PORT_MAX,
                "of.port",
                "of.connection" ));
    }


    protected boolean checkDbName(boolean unitaire){
        return checkDbName(unitaire, true);
    }

    protected boolean checkDbName(boolean unitaire, boolean mandatory){
        // Amazon 0 - 65535
        return super.checkInput(fieldDbName, unitaire, MCDUtilService.checkString(
                fieldDbName.getText(),
                mandatory,
                Preferences.CON_DBNAME_LENGTH,
                Preferences.CON_DBNAME_REGEXPR,
                "of.bdname",
                "of.connection"));

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
                "of.connection"));

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
                "of.connection"));

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String propertyMessage = "";
        String propertyAction = "";
        MVCCDElement mvccdElementForCatchException = null;
        if (getEditor().getMode().equals(DialogEditor.NEW)) {
            propertyMessage = "editor.con.connection.btn.exception.new";
            mvccdElementForCatchException = getEditor().getMvccdElementParent();
        } else {
            propertyMessage = "editor.con.connection.btn.exception.not.new" ;
            mvccdElementForCatchException = getEditor().getMvccdElementCrt();
        }
        try {
            Object source = actionEvent.getSource();
            if (source == btnPortDefault) {
                propertyAction = "editor.con.connection.btn.exception.port.default";
                actionPortDefault();
            }
            /*
            if (source == radDriverDefault) {
                propertyAction = "editor.con.connection.rad.btn.exception.defaultt";
                btnDriverChoice.setEnabled(false);
            }
            if (source == radDriverCustom) {
                propertyAction = "editor.con.connection.rad.btn.exception.custom";
                btnDriverChoice.setEnabled(true);
            }

             */
            if (source == btnDriverChoice) {
                propertyAction = "editor.con.connection.btn.exception.custom.choice";
                actionDiverFileChoice();
                // Relancer le contrôle
                checkDatas(btnDriverChoice);
                // Activer/Désactiver Ok/Apply
                enabledButtons();
            }
            if (source == btnUserPWClear) {
                propertyAction = "editor.con.connection.btn.exception.pw.clear";
                fieldUserPWClear.setVisible(! fieldUserPWClear.isVisible());
                panelInputContentCustom.revalidate();
                panelInputContentCustom.repaint();
                btnUserPWClear.setText( (! fieldUserPWClear.isVisible()) ? "Voir" : "Cacher");
            }
            if (source == btnTest) {
                propertyAction = "editor.con.connection.btn.exception.test";

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

    private void actionDiverFileChoice() {
        try {
            ConDB conDB = getEditor().getConDb();
            ConDriverFileChooser conDriverFileChooser = new ConDriverFileChooser(conDB);
            File fileChoosed = conDriverFileChooser.conResourceDriverFileChoose();
            fieldDriverCustom.setText(fileChoosed.getAbsolutePath());
        } catch (Exception e){
            throw new CodeApplException(e.getMessage(), e);
        }
    }

    private void actionTestConnection() {
        Connection connection =  ConConnectionService.actionTestConnection(getEditor(),
                true,
                getEditor().getConDb(),
                fieldHostName.getText(),
                fieldPort.getText(),
                fieldDbName.getText(),
                radDriverDefault.isSelected(),
                fieldDriverCustom.getText(),
                fieldUserName.getText(),
                fieldUserPW.getText(), //TODO-0 a finaliser avec getPassword()
                ConIDDBName.findByText((String) fieldIdDbName.getSelectedItem()));
    }

    private void actionPortDefault(){
        initPort();
        checkDatas(fieldPort);
    }

    public ConConnectionEditor getEditor() {
        return (ConConnectionEditor) super.getEditor();
    }

}
