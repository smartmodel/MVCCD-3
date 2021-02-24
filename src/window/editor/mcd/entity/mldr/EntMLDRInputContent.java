package window.editor.mcd.entity.mldr;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;

public class EntMLDRInputContent extends PanelInputContent {

    private JLabel labelMLDRTableName;
    private STextField fieldMLDRTableName;

    public EntMLDRInputContent(EntMLDRInput EntMLDRInput)     {
        super(EntMLDRInput);
    }

    public EntMLDRInputContent(MVCCDElement element)     {
        super(null);
        elementForCheckInput = element;
    }

    @Override
    public void createContentCustom() {

        labelMLDRTableName = new JLabel("Nom de table");
        fieldMLDRTableName = new STextField(this, labelMLDRTableName);

        fieldMLDRTableName.setPreferredSize((new Dimension(150, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldMLDRTableName.setCheckPreSave(false);
        fieldMLDRTableName.getDocument().addDocumentListener(this);
        fieldMLDRTableName.addFocusListener(this);

        super.getSComponents().add(fieldMLDRTableName);
        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        panelInputContentCustom.add(labelMLDRTableName,gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(fieldMLDRTableName, gbc);

        this.add(panelInputContentCustom);
    }




    protected boolean changeField(DocumentEvent e) {
        Document doc = e.getDocument();

        if (doc == fieldMLDRTableName.getDocument()) {
            return checkDatas(fieldMLDRTableName);
        }

        return true;
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

        if (source == fieldMLDRTableName) {
            checkDatas(fieldMLDRTableName);
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }

    @Override
    public boolean checkDatas(SComponent sComponent) {
        boolean  ok = super.checkDatas(sComponent);

        boolean notBatch = panelInput != null;
        boolean unitaire = notBatch && (sComponent == fieldMLDRTableName);
        ok = checkTableName(unitaire) && ok   ;

        return ok;
    }


    protected boolean checkTableName(boolean unitaire){
        return checkTableName(unitaire, true);
    }


    protected boolean checkTableName(boolean unitaire, boolean mandatory){
        return super.checkInput(fieldMLDRTableName, unitaire, MCDEntityService.checkTableName(
                (MCDEntity) getElementForCheck(),
                fieldMLDRTableName.getText() ) );
    }



    protected MCDElement getParentForCheck(){
        // Pour utilisation  uniquement checkDatas
        if (panelInput != null) {
            return (MCDElement) getEditor().getMvccdElementParent();
        } else {
            return (MCDElement) elementForCheckInput.getParent();
        }
    }
    protected MCDElement getElementForCheck(){
        // Pour utilisation  uniquement checkDatas
        if (panelInput != null) {
            return (MCDElement) getEditor().getMvccdElementCrt();
        } else {
            return   (MCDElement) elementForCheckInput;
        }
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }

    @Override
    protected void initDatas() {
        // Pas de mode New
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElementCrt;

        if (StringUtils.isEmpty(mcdEntity.getMldrTableName())){
             fieldMLDRTableName.setText(mcdEntity.getName());
        } else {
            fieldMLDRTableName.setText(mcdEntity.getMldrTableName());
        }


        //fieldMLDRTableName.setText(mcdEntity.getMldrTableName());

    }

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElement;
        if (fieldMLDRTableName.checkIfUpdated()){
            mcdEntity.setMldrTableName(fieldMLDRTableName.getText());
        }
    }




    @Override
    protected void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
    }



}
