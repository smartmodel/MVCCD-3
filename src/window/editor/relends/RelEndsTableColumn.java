package window.editor.relends;

public enum RelEndsTableColumn {


    ID("Id"),
    TRANSITORY("Trans."),
    ORDER("Ordre"),
    STEREOTYPES("Stéréotypes"),
    NATURE("Nature"),
    OPPOSITE("Elément opposé"),
    CONSTRAINTS("Contraintes");

    private final String label;

    private RelEndsTableColumn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getPosition(){
        int position= -1;
        int i = -1;;
        for (RelEndsTableColumn anElement: RelEndsTableColumn.values()){
            i++;
            if (anElement == this){
                position = i;
            }
        }
        return position;
    }

    public static int getNbColumns(){
        return RelEndsTableColumn.values().length;
    }

    public static int getPosition (RelEndsTableColumn column){
        int position= -1;
        int i = -1;;
        for (RelEndsTableColumn anElement: RelEndsTableColumn.values()){
            i++;
            if (anElement == column){
                position = i;
            }
        }
        return position;
    }
}
