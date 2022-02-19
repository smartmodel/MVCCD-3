package window.editor.mdr.mpdr.model;

import connections.ConConnection;
import connections.ConDB;
import connections.ConManager;
import main.MVCCDElement;
import mdr.MDRCaseFormat;
import mpdr.MPDRDropBefore;
import mpdr.MPDRModel;
import mpdr.postgresql.MPDRPostgreSQLModel;
import preferences.Preferences;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;
import window.editor.mdr.model.MDRModelInputContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public abstract class MPDRModelInputContent extends MDRModelInputContent  {

    ConDB conDB;
    protected String withoutItem = SComboBox.LINEDASH;
    protected JPanel panelConnection = new JPanel();


    protected JLabel labelConnectionLienProg;
    protected SComboBox fieldConnectionLienProg;
    protected JLabel labelConnectionURL;
    protected STextField fieldConnectionURL;


    private JPanel panelReservedWords = new JPanel ();
    private JLabel labelReservedWordsFormatFuture;
    protected SComboBox fieldReservedWordsFormatFuture;
    private JLabel labelReservedWordsFormatActual;
    private STextField fieldReservedWordsFormatActual;


    protected JLabel labelSchema;
    protected STextField fieldSchema;
    protected JLabel labelDropBeforeCreate ;
    protected SComboBox fieldDropBeforeCreate ;


    protected JPanel panelOptionsTransform = new JPanel();

    protected JLabel labelMPDRDbPK;
    protected STextField fieldMPDRDbPK;
    protected JLabel labelTAPIs;
    protected SCheckBox fieldTAPIs;
    protected JLabel labelSeqPKNameFormat ;
    protected STextField fieldSeqPKNameFormat;
    protected JLabel labelCheckColumnDatatypeNameFormat;
    protected STextField fieldCheckColumnDatatypeNameFormat;

    public MPDRModelInputContent(MPDRModelInput mpdrModelInput) {
        super(mpdrModelInput);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();
        conDB = ((MPDRModelEditor) getEditor()).getConDB();
        boolean conBDWithNamingFormat = (conDB == ConDB.ORACLE) || (conDB == ConDB.POSTGRESQL);
        if (conBDWithNamingFormat){
            fieldNamingFormatFuture.addItem(MDRCaseFormat.LIKEBD.getText());
        }

        labelConnectionLienProg = new JLabel("Name : ");
        fieldConnectionLienProg = new SComboBox(this, labelConnectionLienProg);


        fieldConnectionLienProg.setToolTipText("Connexion de la liste définie dans les préférences de l'application");
        fieldConnectionLienProg.addItemListener(this);
        fieldConnectionLienProg.addFocusListener(this);

        fieldConnectionLienProg.addItem(withoutItem);
        for (ConConnection conConnection : ConManager.instance().getConConnections(conDB)) {
            fieldConnectionLienProg.addItem(conConnection.getName());
        }


        labelConnectionURL = new JLabel("URL : ");
        fieldConnectionURL = new STextField(this, labelConnectionURL);
        fieldConnectionURL.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldConnectionURL.setToolTipText("Chaîne de connexion construite...");
        fieldConnectionURL.setReadOnly(true);


        // Casse
        labelReservedWordsFormatActual = new JLabel("Casse de caractères en préférence");
        fieldReservedWordsFormatActual = new STextField (this, labelReservedWordsFormatActual);
        fieldReservedWordsFormatActual.getDocument().addDocumentListener(this);
        fieldReservedWordsFormatActual.addFocusListener(this);
        fieldReservedWordsFormatActual.setReadOnly(true);

        labelReservedWordsFormatFuture = new JLabel("Casse de caractères propre au modèle");
        fieldReservedWordsFormatFuture = new SComboBox(this, labelReservedWordsFormatFuture);
        fieldReservedWordsFormatFuture.addItem(MDRCaseFormat.NOTHING.getText());
        fieldReservedWordsFormatFuture.addItem(MDRCaseFormat.UPPERCASE.getText());
        fieldReservedWordsFormatFuture.addItem(MDRCaseFormat.LOWERCASE.getText());
        fieldReservedWordsFormatFuture.addItem(MDRCaseFormat.LIKEBD.getText());


        fieldReservedWordsFormatFuture.setToolTipText("Casse de caractères appliqué à tous les objets du modèles");
        fieldReservedWordsFormatFuture.addItemListener(this);
        fieldReservedWordsFormatFuture.addFocusListener(this);


        labelSchema = new JLabel("Schéma : ");
        fieldSchema = new STextField(this, labelSchema);
        fieldSchema.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        //TODO-0 Faire un contôle minimal de saisie
        //TODO-0 Prévoir un bouton de test d'existence du schéma
        fieldSchema.getDocument().addDocumentListener(this);
        fieldSchema.addFocusListener(this);
        fieldSchema.setToolTipText("Schéma d'exécution des scripts SQL-DDL...");

        labelDropBeforeCreate = new JLabel("Drop avant Create : ");
        fieldDropBeforeCreate = new SComboBox(this, labelDropBeforeCreate);
        fieldDropBeforeCreate.setToolTipText("Choix de suppression des objets avant leur création");
        fieldDropBeforeCreate.addItemListener(this);
        fieldDropBeforeCreate.addFocusListener(this);
        for (MPDRDropBefore mpdrDropBefore : MPDRDropBefore.values()) {
            fieldDropBeforeCreate.addItem(mpdrDropBefore.getText());
        }

        labelMPDRDbPK = new JLabel("Génération colonne PK : ");
        fieldMPDRDbPK = new STextField(this, labelMPDRDbPK);
        fieldMPDRDbPK.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldMPDRDbPK.setToolTipText("Mode de génération de la colonne de clé primaire");
        fieldMPDRDbPK.setReadOnly(true);

        labelTAPIs = new JLabel("Génération des TAPIs : ");
        fieldTAPIs = new SCheckBox(this, labelTAPIs);
        fieldTAPIs.setToolTipText("Génération des APIs de tables");
        fieldTAPIs.setReadOnly(true);

        labelSeqPKNameFormat = new JLabel("Séquence de PK : ");
        fieldSeqPKNameFormat = new STextField(this, labelSeqPKNameFormat);
        fieldSeqPKNameFormat.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldSeqPKNameFormat.setToolTipText("Format de nommage de la séquence de PK");
        fieldSeqPKNameFormat.setReadOnly(true);

        labelCheckColumnDatatypeNameFormat = new JLabel("Check de contrôle de type de données : ");
        fieldCheckColumnDatatypeNameFormat = new STextField(this, labelCheckColumnDatatypeNameFormat);
        fieldCheckColumnDatatypeNameFormat.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldCheckColumnDatatypeNameFormat.setToolTipText("Format de la contraint CHECK de type de données");
        fieldCheckColumnDatatypeNameFormat.setReadOnly(true);

        fieldConnectionLienProg.setName("fieldConnectionLienProg");
        fieldConnectionURL.setName("fieldConnectionURL");
        fieldDropBeforeCreate.setName("fieldDropBeforeCreate");

        super.getSComponents().add(fieldConnectionLienProg);
        super.getSComponents().add(fieldConnectionURL);
        super.getSComponents().add(fieldReservedWordsFormatActual);
        super.getSComponents().add(fieldReservedWordsFormatFuture);
        super.getSComponents().add(fieldSchema);
        super.getSComponents().add(fieldDropBeforeCreate);
        super.getSComponents().add(fieldMPDRDbPK);
        super.getSComponents().add(fieldTAPIs);
        super.getSComponents().add(fieldSeqPKNameFormat);
        super.getSComponents().add(fieldCheckColumnDatatypeNameFormat);
    }

    protected void createPanelMaster(GridBagConstraints gbc) {
        super.createPanelMaster(gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 4;
        createPanelReservedWords();
        panelInputContentCustom.add(panelReservedWords, gbc);
        gbc.gridwidth = 1;

        if (conDB == ConDB.POSTGRESQL) {
            gbc.gridx = 0;
            gbc.gridy++;
            panelInputContentCustom.add(labelSchema, gbc);
            gbc.gridx = 1;
            panelInputContentCustom.add(fieldSchema, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelDropBeforeCreate, gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(fieldDropBeforeCreate, gbc);

        createPanelOptionsTransform();
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panelInputContentCustom.add(panelOptionsTransform, gbc);

    }

    protected void createPanelConnection(GridBagConstraints gbcA) {
        panelConnection.add(labelConnectionLienProg, gbcA);
        gbcA.gridx++;
        panelConnection.add(fieldConnectionLienProg, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelConnection.add(labelConnectionURL, gbcA);
        gbcA.gridx++;
        panelConnection.add(fieldConnectionURL, gbcA);
    }

    protected void createPanelOptionsTransform() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelOptionsTransform,
                "Option de la transformation depuis le MLD-R");

        panelOptionsTransform.add(labelMPDRDbPK, gbcA);
        gbcA.gridx++;
        panelOptionsTransform.add(fieldMPDRDbPK, gbcA);

        gbcA.gridx ++;
        panelOptionsTransform.add(labelTAPIs, gbcA);
        gbcA.gridx++;
        panelOptionsTransform.add(fieldTAPIs, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelOptionsTransform.add(labelSeqPKNameFormat, gbcA);
        gbcA.gridx++;
        panelOptionsTransform.add(fieldSeqPKNameFormat, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelOptionsTransform.add(labelCheckColumnDatatypeNameFormat, gbcA);
        gbcA.gridx++;
        panelOptionsTransform.add(fieldCheckColumnDatatypeNameFormat, gbcA);
    }


    private void createPanelReservedWords() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelReservedWords,
                "Ecriture des mots réservés du SGBD-R");

        panelReservedWords.add(labelReservedWordsFormatActual, gbcA);
        gbcA.gridx++;
        panelReservedWords.add(fieldReservedWordsFormatActual, gbcA);

        gbcA.gridx++;
        panelReservedWords.add(labelReservedWordsFormatFuture, gbcA);
        gbcA.gridx++;
        panelReservedWords.add(fieldReservedWordsFormatFuture, gbcA);
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        super.loadDatas(mvccdElementCrt);
        MPDRModel mpdrModel = (MPDRModel) mvccdElementCrt;
        SComboBoxService.selectByText(fieldDropBeforeCreate, mpdrModel.getDropBeforeCreate().getText());
        fieldMPDRDbPK.setText(mpdrModel.getMpdrDbPK().getText());
        fieldTAPIs.setSelected(mpdrModel.isTapis());
        fieldSeqPKNameFormat.setText(mpdrModel.getSequencePKNameFormat());
        fieldCheckColumnDatatypeNameFormat.setText(mpdrModel.getCheckColumnDatatypeNameFormat());

        if (conDB == ConDB.POSTGRESQL) {
            MPDRPostgreSQLModel mpdrPostgreSQLModel = (MPDRPostgreSQLModel) mpdrModel;
            fieldSchema.setText(mpdrPostgreSQLModel.getSchema());
        }
        fieldReservedWordsFormatActual.setText(mpdrModel.getReservedWordsFormatForDB().getText());
        SComboBoxService.selectByText(fieldReservedWordsFormatFuture, mpdrModel.getReservedWordsFormatForDB().getText());

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        super.saveDatas(mvccdElement);
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;
        if (fieldDropBeforeCreate.checkIfUpdated()) {
            String text = (String) fieldDropBeforeCreate.getSelectedItem();
            for (MPDRDropBefore mpdrDropBefore : MPDRDropBefore.values()) {
                if (text.equals(mpdrDropBefore.getText())) {
                    mpdrModel.setDropBeforeCreate(mpdrDropBefore);
                }
            }
        }

        if (conDB == ConDB.POSTGRESQL) {
            MPDRPostgreSQLModel mpdrPostgreSQLModel = (MPDRPostgreSQLModel) mpdrModel;
            if (fieldSchema.checkIfUpdated()) {
                mpdrPostgreSQLModel.setSchema(fieldSchema.getText());
            }
        }

        if (fieldReservedWordsFormatFuture.checkIfUpdated()) {
            String text = (String) fieldReservedWordsFormatFuture.getSelectedItem();
            for (MDRCaseFormat mdrCaseFormat : MDRCaseFormat.values()) {
                if (text.equals(mdrCaseFormat.getText())) {
                    mpdrModel.setReservedWordsFormatFuture(mdrCaseFormat);
                }
            }
        }
    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {
        Object source = e.getSource();
        if (source == fieldConnectionLienProg) {
            setFieldConnectionURL();
        }
    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }

    protected void setFieldConnectionURL() {
        ConConnection conConnection = getConConnectionByFieldConnectionLienProg();
        if (conConnection != null) {
            fieldConnectionURL.setText(conConnection.getResourceURL());
        } else {
            fieldConnectionURL.setText("");
        }
    }


    protected ConConnection getConConnectionByFieldConnectionLienProg(){
        String connectionName = (String) fieldConnectionLienProg.getSelectedItem();
        ConConnection conConnection = ConManager.instance().getConConnectionByName(conDB, connectionName );
        return conConnection ;
    }

}


