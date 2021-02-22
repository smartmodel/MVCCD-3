package window.editor.mcd.mcdtargets;

public enum MCDTargetsTableColumn {


    ID("Id"),
    NAME("Nom"),
    MCDELEMENT("Type élément"),;

    private final String label;

    private MCDTargetsTableColumn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getPosition(){
        int position= -1;
        int i = -1;;
        for (MCDTargetsTableColumn anElement: MCDTargetsTableColumn.values()){
            i++;
            if (anElement == this){
                position = i;
            }
        }
        return position;
    }

    public static int getNbColumns(){
        return MCDTargetsTableColumn.values().length;
    }

    public static int getPosition (MCDTargetsTableColumn column){
        int position= -1;
        int i = -1;;
        for (MCDTargetsTableColumn anElement: MCDTargetsTableColumn.values()){
            i++;
            if (anElement == column){
                position = i;
            }
        }
        return position;
    }
}
