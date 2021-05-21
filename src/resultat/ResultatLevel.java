package resultat;

import console.WarningLevel;
import messages.MessagesBuilder;
import preferences.Preferences;

public enum ResultatLevel {

    EXCEPTION_STACKTRACE(Preferences.RESULTAT_EXCEPTION_STACKTRACE,
            WarningLevel.DEBUG_MODE), // La pile des messages d'exception Java
    EXCEPTION_UNHANDLED(Preferences.RESULTAT_EXCEPTION_UNHANDLED,
            WarningLevel.DETAILS),  // Une exception non maitrisée
    EXCEPTION_CATCHED(Preferences.RESULTAT_EXCEPTION_CATCHED,
            WarningLevel.DETAILS),  // Une exception résolue (mais utile à indiquer)
    FATAL(Preferences.RESULTAT_FATAL,
            WarningLevel.WARNING),  // Une erreur fatale pour le traitement en cours
    NO_FATAL(Preferences.RESULTAT_NO_FATAL,
            WarningLevel.WARNING), // Un avertissement pour le traitement en cours
    INFO(Preferences.RESULTAT_INFO,
            WarningLevel.WARNING); // Une information d'état du traitement (ok, abandonné...)

    private final String name;
    private final WarningLevel warningLevel;

    ResultatLevel(String name, WarningLevel warningLevel) {
        this.name = name;
        this.warningLevel = warningLevel;
    }


    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public WarningLevel getWarningLevel() {
        return warningLevel;
    }

    public static WarningLevel findByText(String text){
        for (WarningLevel element: WarningLevel.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public static WarningLevel findByName(String name){
        for (WarningLevel element: WarningLevel.values()){
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }


}
