package window.editor.preferences.project.mpdr;

import main.MVCCDElement;
import mdr.MDRCaseFormat;
import mdr.MDRNamingLength;
import mpdr.MPDRDB;
import preferences.Preferences;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ItemEvent;

public abstract class PrefMPDRInputContent extends PanelInputContent {

    private MPDRDB mpdrDb = null;

    protected JPanel panelNaming;
    protected JLabel labelNamingLength = new JLabel();
    protected SComboBox fieldNamingLength;
    protected JLabel labelNamingFormat = new JLabel();
    protected SComboBox fieldNamingFormat;

    protected JPanel panelReservedWords;
    protected JLabel labelReservedWordsFormat = new JLabel();
    protected SComboBox fieldReservedWordsFormat;


    protected JLabel labelDelimiterInstructions ;
    protected STextField fieldDelimiterInstructions;
    protected JLabel labelPKGenerate;
    protected SComboBox fieldPKGenerate;
    protected JLabel labelTAPIs;
    protected SCheckBox fieldTAPIs;

    private JPanel panelFormatNames = new JPanel ();
    protected JLabel labelSeqPKNameFormat ;
    protected STextField fieldSeqPKNameFormat;
    protected JLabel labelTriggerNameFormat ;
    protected STextField fieldTriggerNameFormat;
    protected JLabel labelPackageNameFormat ;
    protected STextField fieldPackageNameFormat;
    protected JLabel labelViewNameFormat ;
    protected STextField fieldViewNameFormat;
    protected JLabel labelCheckColumnDatatypeNameFormat ;
    protected STextField fieldCheckColumnDatatypeNameFormat;
    protected JLabel labelCheckColumnDatatypeMax30NameFormat ;
    protected STextField fieldCheckColumnDatatypeMax30NameFormat;



    public PrefMPDRInputContent(PrefMPDRInput prefMPDRInput) {
        super(prefMPDRInput);
     }

    public void createContentCustom() {

        panelNaming = new JPanel();
        panelReservedWords = new JPanel();


        labelNamingLength = new JLabel("Nombre de caractères");
        fieldNamingLength = new SComboBox(this, labelNamingLength);
        if (MDRNamingLength.LENGTH30.isRequired()){
            fieldNamingLength.addItem(MDRNamingLength.LENGTH30.getText());
        }
        if (MDRNamingLength.LENGTH60.isRequired()){
            fieldNamingLength.addItem(MDRNamingLength.LENGTH60.getText());
        }
        if (MDRNamingLength.LENGTH120.isRequired()){
            fieldNamingLength.addItem(MDRNamingLength.LENGTH120.getText());
        }
        fieldNamingLength.setToolTipText("Taillle maximales des noms de tous les objets du modèle");
        fieldNamingLength.addItemListener(this);
        fieldNamingLength.addFocusListener(this);
        fieldNamingLength.setName("fieldNamingLength");


        labelNamingFormat = new JLabel("Casse de caractères");
        fieldNamingFormat = new SComboBox(this, labelNamingFormat);
        fieldNamingFormat.addItem(MDRCaseFormat.NOTHING.getText());
        fieldNamingFormat.addItem(MDRCaseFormat.UPPERCASE.getText());
        fieldNamingFormat.addItem(MDRCaseFormat.LOWERCASE.getText());
        fieldNamingFormat.setToolTipText("Casse de caractères des noms de tous les objets du modèle");
        fieldNamingFormat.addItemListener(this);
        fieldNamingFormat.addFocusListener(this);
        fieldNamingFormat.setName("fieldNamingFormat");



        labelReservedWordsFormat = new JLabel("Casse de caractères");
        fieldReservedWordsFormat = new SComboBox(this, labelReservedWordsFormat);
        fieldReservedWordsFormat.addItem(MDRCaseFormat.NOTHING.getText());
        fieldReservedWordsFormat.addItem(MDRCaseFormat.UPPERCASE.getText());
        fieldReservedWordsFormat.addItem(MDRCaseFormat.LOWERCASE.getText());
        fieldReservedWordsFormat.setToolTipText("Casse de caractères des mots réservés du SGBD-R");
        fieldReservedWordsFormat.addItemListener(this);
        fieldReservedWordsFormat.addFocusListener(this);
        fieldReservedWordsFormat.setName("fieldReservedWordsFormat");

        labelDelimiterInstructions = new JLabel("Délimiteur d'instructions");
        fieldDelimiterInstructions = new STextField(this, labelDelimiterInstructions);
        fieldDelimiterInstructions.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDelimiterInstructions.setToolTipText("Délimiteur utilisé pour sépsrer les instructions au sein d'un bloc d'instructions");
        //TODO-1 Prévoir un formattage/contrôle minimal
        fieldDelimiterInstructions.getDocument().addDocumentListener(this);
        fieldDelimiterInstructions.addFocusListener(this);

        labelPKGenerate = new JLabel("Mode de génération de la colonne PK");
        fieldPKGenerate = new SComboBox(this, labelPKGenerate);
        fieldPKGenerate.addItemListener(this);
        fieldPKGenerate.addFocusListener(this);

        labelTAPIs = new JLabel("Générations des TAPIs");
        fieldTAPIs = new SCheckBox(this, labelTAPIs);
        fieldTAPIs.addItemListener(this);
        fieldTAPIs.addFocusListener(this);

        labelSeqPKNameFormat = new JLabel("Séquence de PK");
        fieldSeqPKNameFormat = new STextField(this, labelSeqPKNameFormat);
        fieldSeqPKNameFormat.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldSeqPKNameFormat.setToolTipText("Format de nommage de la séquence de clé primaire pour les tables indépendantes");
        //TODO-1 Prévoir un formattage/contrôle minimal
        fieldSeqPKNameFormat.getDocument().addDocumentListener(this);
        fieldSeqPKNameFormat.addFocusListener(this);

        labelTriggerNameFormat = new JLabel("Triggers");
        fieldTriggerNameFormat = new STextField(this, labelTriggerNameFormat);
        fieldTriggerNameFormat.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldTriggerNameFormat.setToolTipText("Format de nommage des triggers de table");
        //TODO-1 Prévoir un formattage/contrôle minimal
        fieldTriggerNameFormat.getDocument().addDocumentListener(this);
        fieldTriggerNameFormat.addFocusListener(this);

        labelPackageNameFormat = new JLabel("Packages");
        fieldPackageNameFormat = new STextField(this, labelPackageNameFormat);
        fieldPackageNameFormat.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldPackageNameFormat.setToolTipText("Format de nommage des paquetages de table");
        //TODO-1 Prévoir un formattage/contrôle minimal
        fieldPackageNameFormat.getDocument().addDocumentListener(this);
        fieldPackageNameFormat.addFocusListener(this);

        labelViewNameFormat = new JLabel("Views");
        fieldViewNameFormat = new STextField(this, labelViewNameFormat);
        fieldViewNameFormat.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldViewNameFormat.setToolTipText("Format de nommage des vues des TAPIs");
        //TODO-1 Prévoir un formattage/contrôle minimal
        fieldViewNameFormat.getDocument().addDocumentListener(this);
        fieldViewNameFormat.addFocusListener(this);

        labelCheckColumnDatatypeNameFormat = new JLabel("Check des types de colonnes");
        fieldCheckColumnDatatypeNameFormat = new STextField(this, labelCheckColumnDatatypeNameFormat);
        fieldCheckColumnDatatypeNameFormat.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldCheckColumnDatatypeNameFormat.setToolTipText("Format de des contraintes de Check des colonnes numériques...");
        //TODO-1 Prévoir un formattage/contrôle minimal
        fieldCheckColumnDatatypeNameFormat.getDocument().addDocumentListener(this);
        fieldCheckColumnDatatypeNameFormat.addFocusListener(this);

        labelCheckColumnDatatypeMax30NameFormat = new JLabel("Check des types de colonnes (Max30)");
        fieldCheckColumnDatatypeMax30NameFormat = new STextField(this, labelCheckColumnDatatypeMax30NameFormat);
        fieldCheckColumnDatatypeMax30NameFormat.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldCheckColumnDatatypeMax30NameFormat.setToolTipText("Format de des contraintes de Check des colonnes numériques avec nom court de l'attribut source...");
        //TODO-1 Prévoir un formattage/contrôle minimal
        fieldCheckColumnDatatypeMax30NameFormat.getDocument().addDocumentListener(this);
        fieldCheckColumnDatatypeMax30NameFormat.addFocusListener(this);

        super.getSComponents().add(fieldNamingLength);
        super.getSComponents().add(fieldNamingFormat);
        super.getSComponents().add(fieldReservedWordsFormat);
        super.getSComponents().add(fieldDelimiterInstructions);
        super.getSComponents().add(fieldPKGenerate);
        super.getSComponents().add(fieldTAPIs);
        super.getSComponents().add(fieldSeqPKNameFormat);
        super.getSComponents().add(fieldTriggerNameFormat);
        super.getSComponents().add(fieldPackageNameFormat);
        super.getSComponents().add(fieldViewNameFormat);
        super.getSComponents().add(fieldCheckColumnDatatypeNameFormat);
        super.getSComponents().add(fieldCheckColumnDatatypeMax30NameFormat);

        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        createPanelNaming();
        panelInputContentCustom.add(panelNaming, gbc);

        createPanelReservedWords();
        gbc.gridy++;
        panelInputContentCustom.add(panelReservedWords, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelDelimiterInstructions, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldDelimiterInstructions, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelPKGenerate, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldPKGenerate, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelTAPIs, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldTAPIs, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        createFormatNames();
        panelInputContentCustom.add(panelFormatNames, gbc);
    }

    private void createPanelNaming(){
        GridBagConstraints gbcN = PanelService.createSubPanelGridBagConstraints(panelNaming,
                "Taille et écriture des noms d'objets créés" );

        gbcN.gridx = 0;
        gbcN.gridy = 0 ;
        panelNaming.add(labelNamingLength, gbcN);
        gbcN.gridx++ ;
        panelNaming.add(fieldNamingLength, gbcN);

        gbcN.gridx = 0;
        gbcN.gridy++ ;
        panelNaming.add(labelNamingFormat, gbcN);
        gbcN.gridx++ ;
        panelNaming.add(fieldNamingFormat, gbcN);
    }

    private void createPanelReservedWords(){
        GridBagConstraints gbcN = PanelService.createSubPanelGridBagConstraints(panelReservedWords,
                "Ecriture des mots réservés du SGBD-R" );

        gbcN.gridx = 0;
        gbcN.gridy = 0 ;
        panelReservedWords.add(labelReservedWordsFormat, gbcN);
        gbcN.gridx++ ;
        panelReservedWords.add(fieldReservedWordsFormat, gbcN);
    }

    protected  void createFormatNames(){
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelFormatNames,
                "Formatage des noms");

        gbcA.gridx = 0;
        gbcA.gridy = 0;

        panelFormatNames.add(labelSeqPKNameFormat, gbcA);
        gbcA.gridx++ ;
        panelFormatNames.add(fieldSeqPKNameFormat, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelFormatNames.add(labelTriggerNameFormat, gbcA);
        gbcA.gridx++ ;
        panelFormatNames.add(fieldTriggerNameFormat, gbcA);

        if (mpdrDb == MPDRDB.ORACLE) {
            gbcA.gridx = 0;
            gbcA.gridy++;
            panelFormatNames.add(labelPackageNameFormat, gbcA);
            gbcA.gridx++;
            panelFormatNames.add(fieldPackageNameFormat, gbcA);
        }

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelFormatNames.add(labelViewNameFormat, gbcA);
        gbcA.gridx++ ;
        panelFormatNames.add(fieldViewNameFormat, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelFormatNames.add(labelCheckColumnDatatypeNameFormat, gbcA);
        gbcA.gridx++ ;
        panelFormatNames.add(fieldCheckColumnDatatypeNameFormat, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelFormatNames.add(labelCheckColumnDatatypeMax30NameFormat, gbcA);
        gbcA.gridx++ ;
        panelFormatNames.add(fieldCheckColumnDatatypeMax30NameFormat, gbcA);
    }


    @Override
    protected void enabledContent() {

    }


    @Override
    protected boolean changeField(DocumentEvent e) {
        Document doc = e.getDocument();
        SComponent sComponent = null;
        if (doc == fieldDelimiterInstructions.getDocument()) {
            sComponent = fieldDelimiterInstructions;
        }

        return checkDatas(fieldDelimiterInstructions);
    }


    @Override
    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent) ;
        boolean notBatch = panelInput != null;
        boolean unitaire;

        return ok;
    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    protected void setMPDRDB(MPDRDB db){
        mpdrDb = db;
    }
}

