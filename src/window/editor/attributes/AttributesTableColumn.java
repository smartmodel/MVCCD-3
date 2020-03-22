package window.editor.attributes;

public enum AttributesTableColumn {


    ID("Id"),
    ORDER("Ordre"),
    STEREOTYPES("Stéréotypes"),
    NAME("Nom"),
    DATATYPE("Type"),
    DATASIZE("Taille"),
    DATASCALE("Décimales"),
    UPPERCASE("Uppercase"),
    CONSTRAINTS("Contraintes"),
    DERIVED("Dérivé"),
    DEFAULTVALUE("Défaut / Derivé");

    private final String label;

    private AttributesTableColumn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getPosition(){
        int position= -1;
        int i = -1;;
        for (AttributesTableColumn anElement: AttributesTableColumn.values()){
            i++;
            if (anElement == this){
                position = i;
            }
        }
        return position;
    }

    public static int getNbColumns(){
        return AttributesTableColumn.values().length;
    }

    public static int getPosition (AttributesTableColumn column){
        int position= -1;
        int i = -1;;
        for (AttributesTableColumn anElement: AttributesTableColumn.values()){
            i++;
            if (anElement == column){
                position = i;
            }
        }
        return position;
    }
}
