package window.editor.preferences.project.mpdr;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;
import window.editor.preferences.project.mdr.utilities.PrefMLPDRInputContent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ItemEvent;

public abstract class PrefMPDRInputContent extends PrefMLPDRInputContent {

    protected JLabel labelDelimiterInstructions ;
    protected STextField fieldDelimiterInstructions;
    protected JLabel labelPKGenerate;
    protected SComboBox fieldPKGenerate;


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

        super.getSComponents().add(fieldDelimiterInstructions);
        super.getSComponents().add(fieldPKGenerate);

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

