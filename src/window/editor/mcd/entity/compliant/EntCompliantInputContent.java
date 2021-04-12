package window.editor.mcd.entity.compliant;

import main.MVCCDElement;
import mcd.MCDEntity;
import mcd.MCDEntityNature;
import preferences.Preferences;
import preferences.PreferencesManager;
import repository.editingTreat.mcd.MCDEntCompliantEditingTreat;
import resultat.Resultat;
import resultat.ResultatElement;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextArea;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;

public class EntCompliantInputContent extends PanelInputContent {

    private JLabel labelNature;
    private STextField fieldNature;
    private JLabel labelGeneralize;
    private SCheckBox fieldGeneralize;
    //private JLabel labelAbstract;
    //private SCheckBox fieldAbstract;
    private JLabel labelErrors;
    private STextArea fieldErrors;

    public EntCompliantInputContent(EntCompliantInput entCompliantInput)     {
        super(entCompliantInput);
    }

    public EntCompliantInputContent(MVCCDElement element)     {
        super(null);
        elementForCheckInput = element;
    }

    @Override
    public void createContentCustom() {

        labelNature = new JLabel("Nature");
        fieldNature = new STextField(this, labelNature);

        labelGeneralize = new JLabel("Généralisée");
        fieldGeneralize= new SCheckBox(this, labelGeneralize);

        //labelAbstract = new JLabel("Abstraite");
        //fieldAbstract = new SCheckBox(this, labelAbstract);

        labelErrors = new JLabel("Erreurs");
        fieldErrors = new STextArea(this);

        super.getSComponents().add(fieldNature);
        super.getSComponents().add(fieldGeneralize);
        super.getSComponents().add(fieldErrors);
        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        panelInputContentCustom.add(labelNature,gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(fieldNature, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelGeneralize,gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(fieldGeneralize, gbc);

        /*
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelAbstract,gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(fieldAbstract, gbc);

         */

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelErrors,gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(fieldErrors, gbc);

        this.add(panelInputContentCustom);

    }




    protected boolean changeField(DocumentEvent e) {
        // Lecture seule
        return true;

    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {
       // Lecture seule
    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        //Lecture seule
    }


    @Override
    public void focusGained(FocusEvent focusEvent) {

        super.focusGained(focusEvent);
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }

    @Override
    protected void initDatas() {
        // pas d'insertion
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElementCrt;

        Resultat resultat = mcdEntity.treatCompliant();
        ((MCDEntCompliantEditingTreat) getEditor().getEditingTreat()).treatCompliant(getEditor(), mcdEntity);

        MCDEntityNature nature = mcdEntity.getNature();
        if (nature != null){
            fieldNature.setText(nature.getText());
        }else {
            fieldNature.setText(SComboBox.LINEDASH);
        }

        fieldGeneralize.setSelected(mcdEntity.isGeneralized());
        //fieldAbstract.setSelected(mcdEntity.isEntAbstract());

        if (resultat.isWithElementFatal() ){
            for (ResultatElement error : resultat.getElementsAllLevel()) {
                fieldErrors.append(error.getText() + System.lineSeparator());
            }
        } else {
            fieldErrors.setVisible (false);
            labelErrors.setText("L'entité est conforme !");
        }
    }

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {
        // Pas d'édition
    }




    @Override
    protected void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
    }



}
