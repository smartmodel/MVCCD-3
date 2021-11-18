package window.editor.connections.connector.oracle;

import main.MVCCDElement;
import utilities.window.scomponents.SComponent;
import utilities.window.services.PanelService;
import window.editor.connections.connector.ConConnectorInputContent;

import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;

public class ConConnectorOracleInputContent extends ConConnectorInputContent {

    public ConConnectorOracleInputContent(ConConnectorOracleInput conConnectorOracleInput)     {
        super(conConnectorOracleInput);
    }


    @Override
    protected void enabledContent() {
        super.enabledContent();
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();
        createPanelMaster();
    }

    @Override
    protected boolean changeField(DocumentEvent e) {
        return super.changeField(e);
    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {
        super.changeFieldSelected(e);
    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        super.changeFieldDeSelected(e);
    }


    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
    }

    protected void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);
        super.createPanelMaster(gbc);
        this.add(panelInputContentCustom);
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        super.loadDatas(mvccdElementCrt);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
    }

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {
        super.saveDatas(mvccdElement);
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {
        super.loadSimulationChange(mvccdElementCrt);
    }

    public boolean checkDatasPreSave(SComponent sComponent) {
        boolean  ok = super.checkDatasPreSave(sComponent);

        boolean notBatch = panelInput != null;
        //boolean unitaire = notBatch && (sComponent == fieldName);
        //ok = checkName(unitaire) && ok   ;

        setPreSaveOk(ok);
        return ok;
    }



    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);
        return ok;
    }


    protected boolean checkName(boolean unitaire){
        return checkName(unitaire, true);
    }



}
