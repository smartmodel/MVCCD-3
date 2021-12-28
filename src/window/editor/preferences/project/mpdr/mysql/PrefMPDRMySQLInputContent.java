package window.editor.preferences.project.mpdr.mysql;

import main.MVCCDElement;
import mdr.MDRNamingFormat;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import mpdr.MPDRDBPK;
import preferences.Preferences;
import utilities.window.scomponents.services.SComboBoxService;
import window.editor.preferences.project.mpdr.PrefMPDRInputContent;

public class PrefMPDRMySQLInputContent extends PrefMPDRInputContent {



    public PrefMPDRMySQLInputContent(PrefMPDRMySQLInput prefMPDRMySQLInput) {
        super(prefMPDRMySQLInput);
     }


    public void createContentCustom() {

        super.createContentCustom();
        fieldPKGenerate.addItem(MPDRDBPK.IDENTITY.getText());
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        SComboBoxService.selectByText(fieldNamingLength,
                preferences.getMPDRMYSQL_PREF_NAMING_LENGTH().getText());
        SComboBoxService.selectByText(fieldNamingFormat,
                preferences.getMPDRMYSQL_PREF_NAMING_FORMAT().getText());
        fieldDelimiterInstructions.setText(preferences.getMPDRMYSQL_DELIMITER_INSTRUCTIONS());
        SComboBoxService.selectByText(fieldPKGenerate,
                preferences.getMPDRORACLE_PK_GENERATE().getText());

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        if (fieldNamingLength.checkIfUpdated()){
            String text = (String) fieldNamingLength.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_30))){
                preferences.setMPDRMYSQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH30);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_60))){
                preferences.setMPDRMYSQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH60);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_120))){
                preferences.setMPDRMYSQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH120);
            }
        }

        if (fieldNamingFormat.checkIfUpdated()){
            String text = (String) fieldNamingFormat.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_NOTHING))){
                preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRNamingFormat.NOTHING);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_UPPERCASE))){
                preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRNamingFormat.UPPERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_LOWERCASE))){
                preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRNamingFormat.LOWERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_CAPITALIZE))){
                preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRNamingFormat.CAPITALIZE);
            }
        }

        if (fieldDelimiterInstructions.checkIfUpdated()){
            preferences.setMPDRMYSQL_DELIMITER_INSTRUCTIONS(fieldDelimiterInstructions.getText());
        }


        if (fieldPKGenerate.checkIfUpdated()){
            String text = (String) fieldPKGenerate.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.DB_PK_IDENTITY))){
                preferences.setMPDRORACLE_PK_GENERATE(MPDRDBPK.IDENTITY);
            }
        }

    }

}

