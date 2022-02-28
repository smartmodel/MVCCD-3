package window.editor.preferences.project.mpdr.mysql;

import main.MVCCDElement;
import mdr.MDRCaseFormat;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import mpdr.MPDRDB;
import mpdr.MPDRDBPK;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.scomponents.services.SComboBoxService;
import window.editor.preferences.project.mpdr.PrefMPDRInputContent;

public class PrefMPDRMySQLInputContent extends PrefMPDRInputContent {



    public PrefMPDRMySQLInputContent(PrefMPDRMySQLInput prefMPDRMySQLInput) {
        super(prefMPDRMySQLInput);
        super.setMPDRDB(MPDRDB.MYSQL);

    }


    public void createContentCustom() {
        super.createContentCustom();
        fieldPKGenerate.addItem(MPDRDBPK.IDENTITY.getText());
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
                preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRCaseFormat.NOTHING);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_UPPERCASE))){
                preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRCaseFormat.UPPERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_LOWERCASE))){
                preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRCaseFormat.LOWERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_CAPITALIZE))){
                preferences.setMPDRMYSQL_PREF_NAMING_FORMAT(MDRCaseFormat.CAPITALIZE);
            }
        }

        if (fieldReservedWordsFormat.checkIfUpdated()){
            String text = (String) fieldReservedWordsFormat.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_NOTHING))){
                preferences.setMPDRMYSQL_PREF_RESERDWORDS_FORMAT(MDRCaseFormat.NOTHING);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_UPPERCASE))){
                preferences.setMPDRMYSQL_PREF_RESERDWORDS_FORMAT(MDRCaseFormat.UPPERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_LOWERCASE))){
                preferences.setMPDRMYSQL_PREF_RESERDWORDS_FORMAT(MDRCaseFormat.LOWERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_CAPITALIZE))){
                preferences.setMPDRMYSQL_PREF_RESERDWORDS_FORMAT(MDRCaseFormat.CAPITALIZE);
            }
        }

        if (fieldDelimiterInstructions.checkIfUpdated()){
            preferences.setMPDRMYSQL_DELIMITER_INSTRUCTIONS(fieldDelimiterInstructions.getText());
        }


        if (fieldPKGenerate.checkIfUpdated()){
            String text = (String) fieldPKGenerate.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.DB_PK_IDENTITY))){
                preferences.setMPDRMYSQL_PK_GENERATE(MPDRDBPK.IDENTITY);
            }
        }

        if (fieldTAPIs.checkIfUpdated()){
            preferences.setMPDRMYSQL_TAPIS(fieldTAPIs.isSelected());
        }

        if (fieldSeqPKNameFormat.checkIfUpdated()){
            preferences.setMPDRMYSQL_SEQPK_NAME_FORMAT(fieldSeqPKNameFormat.getText());
        }

        if (fieldTriggerNameFormat.checkIfUpdated()){
            preferences.setMPDRMYSQL_TRIGGER_NAME_FORMAT(fieldTriggerNameFormat.getText());
        }

        if (fieldCheckColumnDatatypeNameFormat.checkIfUpdated()){
            preferences.setMPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT(fieldCheckColumnDatatypeNameFormat.getText());
        }

        if (fieldCheckColumnDatatypeMax30NameFormat.checkIfUpdated()){
            preferences.setMPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT(fieldCheckColumnDatatypeMax30NameFormat.getText());
        }
    }

/*
    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        SComboBoxService.selectByText(fieldNamingLength,
                preferences.getMPDRMYSQL_PREF_NAMING_LENGTH().getText());
        SComboBoxService.selectByText(fieldNamingFormat,
                preferences.getMPDRMYSQL_PREF_NAMING_FORMAT().getText());
        SComboBoxService.selectByText(fieldReservedWordsFormat,
                preferences.getMPDRMYSQL_PREF_RESERDWORDS_FORMAT().getText());
        fieldDelimiterInstructions.setText(preferences.getMPDRMYSQL_DELIMITER_INSTRUCTIONS());
        SComboBoxService.selectByText(fieldPKGenerate,
                preferences.getMPDRMYSQL_PK_GENERATE().getText());
        fieldTAPIs.setSelected(preferences.getMPDRMYSQL_TAPIS());
        fieldSeqPKNameFormat.setText(preferences.getMPDRMYSQL_SEQPK_NAME_FORMAT());
        fieldTriggerNameFormat.setText(preferences.getMPDRMYSQL_TRIGGER_NAME_FORMAT());
        fieldCheckColumnDatatypeNameFormat.setText(preferences.getMPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT());
        fieldCheckColumnDatatypeMax30NameFormat.setText(preferences.getMPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT());
    }

 */

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        loadDatasWithSource(mvccdElement, preferences);
    }


    protected void reInitDatas(MVCCDElement mvccdElement){
        Preferences preferences ;
        if (PreferencesManager.instance().getProfilePref() != null) {
            preferences = PreferencesManager.instance().getProfilePref();
        } else {
            preferences = PreferencesManager.instance().getApplicationPref();
        }
        loadDatasWithSource(mvccdElement, preferences);
    }

    public void loadDatasWithSource(MVCCDElement mvccdElement, Preferences preferences) {
        SComboBoxService.selectByText(fieldNamingLength,
                preferences.getMPDRMYSQL_PREF_NAMING_LENGTH().getText());
        SComboBoxService.selectByText(fieldNamingFormat,
                preferences.getMPDRMYSQL_PREF_NAMING_FORMAT().getText());
        SComboBoxService.selectByText(fieldReservedWordsFormat,
                preferences.getMPDRMYSQL_PREF_RESERDWORDS_FORMAT().getText());
        fieldDelimiterInstructions.setText(preferences.getMPDRMYSQL_DELIMITER_INSTRUCTIONS());
        SComboBoxService.selectByText(fieldPKGenerate,
                preferences.getMPDRMYSQL_PK_GENERATE().getText());
        fieldTAPIs.setSelected(preferences.getMPDRMYSQL_TAPIS());
        fieldSeqPKNameFormat.setText(preferences.getMPDRMYSQL_SEQPK_NAME_FORMAT());
        fieldTriggerNameFormat.setText(preferences.getMPDRMYSQL_TRIGGER_NAME_FORMAT());
        fieldCheckColumnDatatypeNameFormat.setText(preferences.getMPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT());
        fieldCheckColumnDatatypeMax30NameFormat.setText(preferences.getMPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT());
    }



}

