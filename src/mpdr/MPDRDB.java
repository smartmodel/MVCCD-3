package mpdr;

import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;

public enum MPDRDB {
    ORACLE (Preferences.DB_ORACLE, PreferencesManager.instance().preferences().getMPDRORACLE_DELIMITER_INSTRUCTIONS(),
            "SYSDATE", "USER", "userenv('sessionid')",
            "NULL", "NULL"),
    MYSQL (Preferences.DB_MYSQL, PreferencesManager.instance().preferences().getMPDRMYSQL_DELIMITER_INSTRUCTIONS(),
            "{now}", "SUBSTRING({fct_user}, 1, 200)", "NULL",
            "NULL", "NULL"),
    POSTGRESQL (Preferences.DB_POSTGRESQL,  PreferencesManager.instance().preferences().getMPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS(),
            "{current_timestamp}", "{fct_user}", "pg_backend_pid()::varchar",
            "NULL", "NULL");

    private final String name;
    private final String delimiterInstructions;
    private final String dateTimeInString;
    private final String userInString;
    private final String sessionInString;
    private final String applInString;
    private final String notesInString;

    MPDRDB(String name,
           String delimiterInstructions,
           String dateTimeInString,
           String userInString,
           String sessionInString,
           String applInString,
           String notesInString) {
        this.name = name;
        this.delimiterInstructions = delimiterInstructions;
        this.dateTimeInString = dateTimeInString;
        this.userInString = userInString;
        this.sessionInString = sessionInString;
        this.applInString = applInString;
        this.notesInString = notesInString;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRDB findByText(String text){
        for (MPDRDB element: MPDRDB.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public String getDelimiterInstructions(){
        return delimiterInstructions;
    }

    public String getDateTimeInString() {
        return dateTimeInString;
    }

    public String getUserInString() {
        return userInString;
    }

    public String getSessionInString() {
        return sessionInString;
    }

    public String getApplInString() {
        return applInString;
    }

    public String getNotesInString() {
        return notesInString;
    }
}
