package window.editor.operation.constraint.constraints;

public enum ConstraintsTableColumn {


    ID("Id"),
    TRANSITORY("Trans."),
    ORDER("Ordre"),
    STEREOTYPES("Stéréotypes"),
    NATURE("Nature"),
    NAME("Nom"),
    CONSTRAINTS("Contraintes");

    private final String label;

    private ConstraintsTableColumn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getPosition(){
        int position= -1;
        int i = -1;;
        for (ConstraintsTableColumn anElement: ConstraintsTableColumn.values()){
            i++;
            if (anElement == this){
                position = i;
            }
        }
        return position;
    }

    public static int getNbColumns(){
        return ConstraintsTableColumn.values().length;
    }

    public static int getPosition (ConstraintsTableColumn column){
        int position= -1;
        int i = -1;;
        for (ConstraintsTableColumn anElement: ConstraintsTableColumn.values()){
            i++;
            if (anElement == column){
                position = i;
            }
        }
        return position;
    }
}
