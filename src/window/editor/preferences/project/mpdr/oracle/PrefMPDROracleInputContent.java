package window.editor.preferences.project.mpdr.oracle;

import main.MVCCDElement;
import mdr.MDRNamingFormat;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;
import window.editor.preferences.project.mdr.utilities.PrefMLPDRInputContent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefMPDROracleInputContent extends PrefMLPDRInputContent {

    protected JLabel labelDelimiterInstructions = new JLabel();
    protected STextField fieldDelimiterInstructions;


    public PrefMPDROracleInputContent(PrefMPDROracleInput prefMPDROracleInput) {
        super(prefMPDROracleInput);
     }

    public void createContentCustom() {

        super.createContentCustom();

        createPanelMaster();

    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);
        super.affectPanelMaster(gbc);
    }



    @Override
    protected void enabledContent() {

    }


    @Override
    protected boolean changeField(DocumentEvent e) {
        return true;
    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        SComboBoxService.selectByText(fieldNamingLength,
                preferences.getMPDRORACLE_PREF_NAMING_LENGTH().getText());
        SComboBoxService.selectByText(fieldNamingFormat,
                preferences.getMPDRORACLE_PREF_NAMING_FORMAT().getText());
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        if (fieldNamingLength.checkIfUpdated()){
            String text = (String) fieldNamingLength.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_30))){
                preferences.setMPDRORACLE_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH30);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_60))){
                preferences.setMPDRORACLE_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH60);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_120))){
                preferences.setMPDRORACLE_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH120);
            }
        }


        if (fieldNamingFormat.checkIfUpdated()){
            String text = (String) fieldNamingFormat.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_NOTHING))){
                preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRNamingFormat.NOTHING);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_UPPERCASE))){
                preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRNamingFormat.UPPERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_LOWERCASE))){
                preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRNamingFormat.LOWERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_CAPITALIZE))){
                preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRNamingFormat.CAPITALIZE);
            }
        }

    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


}

