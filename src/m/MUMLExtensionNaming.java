package m;

import mdr.MDRNamingFormat;
import messages.MessagesBuilder;
import preferences.Preferences;

public enum MUMLExtensionNaming {
    ONELINE_ONEMARKER ("m.uml.extension.naming.oneline.onemarker"),
    ONELINE_MANYMARKER ("m.uml.extension.naming.oneline.manymarker"),
    MANYLINE ("m.uml.extension.naming.manyline");



    //public static String M_UMLEXTENSION_NAMING_ONELINE_ONEMARKER = "m.uml.extension.naming.oneline.onemarker";
    //public static String M_UMLEXTENSION_NAMING_ONELINE_MANYMARKER = "m.uml.extension.naming.oneline.manymarker";
    //public static String M_UMLEXTENSION_NAMING_MANYLINE = "m.uml.extension.naming.manyline";

    private final String name;

    MUMLExtensionNaming(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MUMLExtensionNaming findByText(String text){
        for (MUMLExtensionNaming element: MUMLExtensionNaming.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }
}
