package mpdr;

import exceptions.CodeApplException;
import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRConstraintSpecificRole {
    DATATYPE (Preferences.MPDR_CHECK_DATATYPE),
    INDEX (Preferences.MPDR_FK_INDEX);

    private final String name;

    MPDRConstraintSpecificRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRConstraintSpecificRole findByText(String text){
        for (MPDRConstraintSpecificRole element: MPDRConstraintSpecificRole.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public String getNameFormat(MPDRModel mpdrModel){
        if (this == DATATYPE) {
            return mpdrModel.getCheckColumnDatatypeNameFormat();
        } else {
            throw new CodeApplException("La méthode n'a pas de retour pour cette valeur de rôle de séquence");
        }
    };


    public Integer getFormatUserMarkerLengthMax(){
        if (this == DATATYPE) {
            return Preferences.MARKER_CHECK_LENGTH;
        } else {
            throw new CodeApplException("La méthode n'a pas de retour pour cette valeur de rôle de séquence");
        }
    };


}
