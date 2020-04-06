package utilities.window.editor;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;

public abstract class PanelInputContentId extends PanelInputContent {

    protected JPanel panelId = new JPanel ();
    protected STextField fieldName = new STextField(this);
    protected STextField fieldShortName = new STextField(this);

    public PanelInputContentId(PanelInput panelInput) {
        super(panelInput);
    }

    protected void createContentId() {

        fieldName.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldName.setCheckPreSave(true);
        fieldName.getDocument().addDocumentListener(this);
        fieldName.addFocusListener(this);


        fieldShortName.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldShortName.getDocument().addDocumentListener(this);
        fieldShortName.addFocusListener(this);

        super.getsComponents().add(fieldName);
        super.getsComponents().add(fieldShortName);

    }


    protected GridBagConstraints createPanelId() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelId, "Identification");

        panelId.add(new JLabel("Nom : "), gbc);
        gbc.gridx++;
        panelId.add(fieldName, gbc);
        gbc.gridx++;
        panelId.add(new JLabel("Nom court : "), gbc);
        gbc.gridx++;
        panelId.add(fieldShortName, gbc);
        return gbc;
    }

    protected abstract boolean checkName(boolean unitaire);
    protected abstract boolean checkShortName(boolean unitaire);

    protected void changeField(DocumentEvent e) {
        // Les champs obligatoires sont testés sur la procédure checkDatasPreSave()


    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();
        if (source == fieldName) {
            checkName(true);
        }
        if (source == fieldShortName) {
            checkShortName(true);
        }
    }

    public boolean checkDatasPreSaveId(boolean unitaire) {
        boolean ok = true;
        ok = checkName(unitaire);
        return ok;
    }

    protected boolean checkDatasId(){
        boolean ok = checkDatasPreSave(false);
        ok =  checkShortName(false)  && ok ;
        return ok ;
    }


    protected void initDatasId() {
        fieldName.setText("");
        fieldShortName.setText("");
    }

    protected void loadDatasId(MVCCDElement mvccdElement) {
        fieldName.setText(mvccdElement.getName());
        fieldShortName.setText(mvccdElement.getShortName());
    }

    protected void saveDatasId(MVCCDElement mvccdElement) {
        if (fieldName.checkIfUpdated()){
            mvccdElement.setName(fieldName.getText());
        }
        if (fieldShortName.checkIfUpdated()){
            mvccdElement.setShortName(fieldShortName.getText());
        }
    }
}
