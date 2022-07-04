package window.editor.preferences.project.mpdr.oracle;

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

public class PrefMPDROracleInputContent extends PrefMPDRInputContent {


    public PrefMPDROracleInputContent(PrefMPDROracleInput prefMPDROracleInput) {
        super(prefMPDROracleInput);
        super.setMPDRDB(MPDRDB.ORACLE);
    }

    public void createContentCustom() {
        super.createContentCustom();
        fieldNamingFormat.addItem(MDRCaseFormat.LIKEBD.getText());
        fieldReservedWordsFormat.addItem(MDRCaseFormat.LIKEBD.getText());

        fieldPKGenerate.addItem(MPDRDBPK.SEQUENCE.getText());
        fieldPKGenerate.addItem(MPDRDBPK.IDENTITY.getText());
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
                preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRCaseFormat.NOTHING);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_UPPERCASE))){
                preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRCaseFormat.UPPERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_LOWERCASE))){
                preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRCaseFormat.LOWERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_CAPITALIZE))){
                preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRCaseFormat.CAPITALIZE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MPDR_NAMING_FORMAT_LIKEDB))){
                preferences.setMPDRORACLE_PREF_NAMING_FORMAT(MDRCaseFormat.LIKEBD);
            }
        }


        if (fieldReservedWordsFormat.checkIfUpdated()){
            String text = (String) fieldReservedWordsFormat.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_NOTHING))){
                preferences.setMPDRORACLE_PREF_RESERDWORDS_FORMAT(MDRCaseFormat.NOTHING);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_UPPERCASE))){
                preferences.setMPDRORACLE_PREF_RESERDWORDS_FORMAT(MDRCaseFormat.UPPERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_LOWERCASE))){
                preferences.setMPDRORACLE_PREF_RESERDWORDS_FORMAT(MDRCaseFormat.LOWERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_CAPITALIZE))){
                preferences.setMPDRORACLE_PREF_RESERDWORDS_FORMAT(MDRCaseFormat.CAPITALIZE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MPDR_NAMING_FORMAT_LIKEDB))){
                preferences.setMPDRORACLE_PREF_RESERDWORDS_FORMAT(MDRCaseFormat.LIKEBD);
            }
        }

        if (fieldDelimiterInstructions.checkIfUpdated()){
            preferences.setMPDRORACLE_DELIMITER_INSTRUCTIONS(fieldDelimiterInstructions.getText());
        }

        if (fieldPKGenerate.checkIfUpdated()){
            String text = (String) fieldPKGenerate.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.DB_PK_SEQUENCE))){
                preferences.setMPDRORACLE_PK_GENERATE(MPDRDBPK.SEQUENCE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.DB_PK_IDENTITY))){
                preferences.setMPDRORACLE_PK_GENERATE(MPDRDBPK.IDENTITY);
            }
        }

        if (fieldTAPIs.checkIfUpdated()){
            preferences.setMPDRORACLE_TAPIS(fieldTAPIs.isSelected());
        }

        if (fieldSeqPKNameFormat.checkIfUpdated()){
            preferences.setMPDRORACLE_SEQPK_NAME_FORMAT(fieldSeqPKNameFormat.getText());
        }

        if (fieldTriggerTableNameFormat.checkIfUpdated()){
            preferences.setMPDRORACLE_TRIGGER_TABLE_NAME_FORMAT(fieldTriggerTableNameFormat.getText());
        }

        if (fieldTriggerViewNameFormat.checkIfUpdated()){
            preferences.setMPDRORACLE_TRIGGER_VIEW_NAME_FORMAT(fieldTriggerViewNameFormat.getText());
        }

        if (fieldPackageNameFormat.checkIfUpdated()){
            preferences.setMPDRORACLE_PACKAGE_NAME_FORMAT(fieldPackageNameFormat.getText());
        }

        if (fieldViewNameFormat.checkIfUpdated()){
            preferences.setMPDRORACLE_VIEW_NAME_FORMAT(fieldViewNameFormat.getText());
        }

        if (fieldTableJnalNameFormat.checkIfUpdated()){
            preferences.setMPDRORACLE_TABLEJNAL_NAME_FORMAT(fieldTableJnalNameFormat.getText());
        }

        if (fieldCheckColumnDatatypeNameFormat.checkIfUpdated()){
            preferences.setMPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT(fieldCheckColumnDatatypeNameFormat.getText());
        }

        if (fieldCheckColumnDatatypeMax30NameFormat.checkIfUpdated()){
            preferences.setMPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT(fieldCheckColumnDatatypeMax30NameFormat.getText());
        }

    }

/*
    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        SComboBoxService.selectByText(fieldNamingLength,
                preferences.getMPDRORACLE_PREF_NAMING_LENGTH().getText());
        SComboBoxService.selectByText(fieldNamingFormat,
                preferences.getMPDRORACLE_PREF_NAMING_FORMAT().getText());
        SComboBoxService.selectByText(fieldReservedWordsFormat,
                preferences.getMPDRORACLE_PREF_RESERDWORDS_FORMAT().getText());
        fieldDelimiterInstructions.setText(preferences.getMPDRORACLE_DELIMITER_INSTRUCTIONS());
        SComboBoxService.selectByText(fieldPKGenerate,
                preferences.getMPDRORACLE_PK_GENERATE().getText());
        fieldTAPIs.setSelected(preferences.getMPDRORACLE_TAPIS());
        fieldSeqPKNameFormat.setText(preferences.getMPDRORACLE_SEQPK_NAME_FORMAT());
        fieldTriggerNameFormat.setText(preferences.getMPDRORACLE_TRIGGER_NAME_FORMAT());
        fieldPackageNameFormat.setText(preferences.getMPDRORACLE_PACKAGE_NAME_FORMAT());
        fieldCheckColumnDatatypeNameFormat.setText(preferences.getMPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT());
        fieldCheckColumnDatatypeMax30NameFormat.setText(preferences.getMPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT());
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
                preferences.getMPDRORACLE_PREF_NAMING_LENGTH().getText());
        SComboBoxService.selectByText(fieldNamingFormat,
                preferences.getMPDRORACLE_PREF_NAMING_FORMAT().getText());
        SComboBoxService.selectByText(fieldReservedWordsFormat,
                preferences.getMPDRORACLE_PREF_RESERDWORDS_FORMAT().getText());
        fieldDelimiterInstructions.setText(preferences.getMPDRORACLE_DELIMITER_INSTRUCTIONS());
        SComboBoxService.selectByText(fieldPKGenerate,
                preferences.getMPDRORACLE_PK_GENERATE().getText());
        fieldTAPIs.setSelected(preferences.getMPDRORACLE_TAPIS());
        fieldSeqPKNameFormat.setText(preferences.getMPDRORACLE_SEQPK_NAME_FORMAT());
        fieldTriggerTableNameFormat.setText(preferences.getMPDRORACLE_TRIGGER_TABLE_NAME_FORMAT());
        fieldTriggerViewNameFormat.setText(preferences.getMPDRORACLE_TRIGGER_VIEW_NAME_FORMAT());
        fieldPackageNameFormat.setText(preferences.getMPDRORACLE_PACKAGE_NAME_FORMAT());
        fieldViewNameFormat.setText(preferences.getMPDRORACLE_VIEW_NAME_FORMAT());
        fieldTableJnalNameFormat.setText(preferences.getMPDRORACLE_TABLEJNAL_NAME_FORMAT());
        fieldCheckColumnDatatypeNameFormat.setText(preferences.getMPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT());
        fieldCheckColumnDatatypeMax30NameFormat.setText(preferences.getMPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT());
    }

}

