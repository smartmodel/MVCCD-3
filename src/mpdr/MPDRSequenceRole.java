package mpdr;

import exceptions.CodeApplException;
import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRSequenceRole {
    PK (Preferences.MPDR_SEQUENCE_PK);

    private final String name;

    MPDRSequenceRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRSequenceRole findByText(String text){
        for (MPDRSequenceRole element: MPDRSequenceRole.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public String getNameFormat(MPDRModel mpdrModel){
        if (this == PK) {
            return mpdrModel.getSequencePKNameFormat();
        } else {
            throw new CodeApplException("La méthode n'a pas de retour pour cette valeur de rôle de séquence");
        }
    };


    public Integer getFormatUserMarkerLengthMax(){
        if (this == PK) {
            return Preferences.MARKER_CUSTOM_SEQPK_LENGTH;
        } else {
            throw new CodeApplException("La méthode n'a pas de retour pour cette valeur de rôle de séquence");
        }
    };


}
