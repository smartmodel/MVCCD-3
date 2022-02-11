package window.editor.preferences.project.mpdr;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;
import window.editor.preferences.project.mdr.utilities.PrefMDRInputContent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ItemEvent;

public abstract class PrefMPDRInputContent extends PrefMDRInputContent {

    private JPanel panelFormatNames = new JPanel ();

    protected JLabel labelDelimiterInstructions ;
    protected STextField fieldDelimiterInstructions;
    protected JLabel labelPKGenerate;
    protected SComboBox fieldPKGenerate;
    protected JLabel labelTAPIs;
    protected SCheckBox fieldTAPIs;
    protected JLabel labelSeqPKNameFormat ;
    protected STextField fieldSeqPKNameFormat;
    protected JLabel labelTriggerNameFormat ;
    protected STextField fieldTriggerNameFormat;
    protected JLabel labelCheckColumnDatatypeNameFormat ;
    protected STextField fieldCheckColumnDatatypeNameFormat;
    protected JLabel labelCheckColumnDatatypeMax30NameFormat ;
    protected STextField fieldCheckColumnDatatypeMax30NameFormat;



    public PrefMPDRInputContent(PrefMPDRInput prefMPDRInput) {
        super(prefMPDRInput);
     }

    public void createContentCustom() {

        super.createContentCustom();

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

        super.getSComponents().add(fieldDelimiterInstructions);
        super.getSComponents().add(fieldPKGenerate);
        super.getSComponents().add(fieldTAPIs);
        super.getSComponents().add(fieldSeqPKNameFormat);
        super.getSComponents().add(fieldTriggerNameFormat);
        super.getSComponents().add(fieldCheckColumnDatatypeNameFormat);
        super.getSComponents().add(fieldCheckColumnDatatypeMax30NameFormat);

        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);
        super.affectPanelMaster(gbc);

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


}

