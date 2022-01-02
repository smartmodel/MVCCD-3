package window.editor.preferences.project.mpdr.postgresql;

import main.MVCCDElement;
import mdr.MDRNamingFormat;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import mpdr.MPDRDBPK;
import preferences.Preferences;
import utilities.window.scomponents.services.SComboBoxService;
import window.editor.preferences.project.mpdr.PrefMPDRInputContent;

public class PrefMPDRPostgreSQLInputContent extends PrefMPDRInputContent {



    public PrefMPDRPostgreSQLInputContent(PrefMPDRPostgreSQLInput prefMPDRPostgreSQLInput) {
        super(prefMPDRPostgreSQLInput);
     }


    public void createContentCustom() {

        super.createContentCustom();
        fieldPKGenerate.addItem(MPDRDBPK.SEQUENCE.getText());
        fieldPKGenerate.addItem(MPDRDBPK.IDENTITY.getText());
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        SComboBoxService.selectByText(fieldNamingLength,
                preferences.getMPDRPOSTGRESQL_PREF_NAMING_LENGTH().getText());
        SComboBoxService.selectByText(fieldNamingFormat,
                preferences.getMPDRPOSTGRESQL_PREF_NAMING_FORMAT().getText());
        fieldDelimiterInstructions.setText(preferences.getMPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS());
        SComboBoxService.selectByText(fieldPKGenerate,
                preferences.getMPDRPOSTGRESQL_PK_GENERATE().getText());
        fieldTAPIs.setSelected(preferences.getMPDRPOSTGRESQL_TAPIS());
        fieldSeqPKNameFormat.setText(preferences.getMPDRPOSTGRESQL_SEQPK_NAME_FORMAT());

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        if (fieldNamingLength.checkIfUpdated()){
            String text = (String) fieldNamingLength.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_30))){
                preferences.setMPDRPOSTGRESQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH30);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_60))){
                preferences.setMPDRPOSTGRESQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH60);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_120))){
                preferences.setMPDRPOSTGRESQL_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH120);
            }
        }

        if (fieldNamingFormat.checkIfUpdated()){
            String text = (String) fieldNamingFormat.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_NOTHING))){
                preferences.setMPDRPOSTGRESQL_PREF_NAMING_FORMAT(MDRNamingFormat.NOTHING);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_UPPERCASE))){
                preferences.setMPDRPOSTGRESQL_PREF_NAMING_FORMAT(MDRNamingFormat.UPPERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_LOWERCASE))){
                preferences.setMPDRPOSTGRESQL_PREF_NAMING_FORMAT(MDRNamingFormat.LOWERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_CAPITALIZE))){
                preferences.setMPDRPOSTGRESQL_PREF_NAMING_FORMAT(MDRNamingFormat.CAPITALIZE);
            }
        }

        if (fieldDelimiterInstructions.checkIfUpdated()){
            preferences.setMPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS(fieldDelimiterInstructions.getText());
        }


        if (fieldPKGenerate.checkIfUpdated()){
            String text = (String) fieldPKGenerate.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.DB_PK_SEQUENCE))){
                preferences.setMPDRPOSTGRESQL_PK_GENERATE(MPDRDBPK.SEQUENCE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.DB_PK_IDENTITY))){
                preferences.setMPDRPOSTGRESQL_PK_GENERATE(MPDRDBPK.IDENTITY);
            }
        }

        if (fieldTAPIs.checkIfUpdated()){
            preferences.setMPDRPOSTGRESQL_TAPIS(fieldTAPIs.isSelected());
        }

        if (fieldSeqPKNameFormat.checkIfUpdated()){
            preferences.setMPDRPOSTGRESQL_SEQPK_NAME_FORMAT(fieldSeqPKNameFormat.getText());
        }


    }

}

