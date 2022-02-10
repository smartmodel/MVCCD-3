package mpdr;

import exceptions.CodeApplException;
import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRCheckRole {
    DATATYPE (Preferences.MPDR_CHECK_DATATYPE);

    private final String name;

    MPDRCheckRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRCheckRole findByText(String text){
        for (MPDRCheckRole element: MPDRCheckRole.values()){
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
