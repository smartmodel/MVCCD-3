package window.editor.mdr.fk;

public enum MDRFKTableColumn {


    COLUMNFK("Colonne FK"),
    COLUMNPK("Colonne de référence (PK)"),
    LEVELREFPK("Prof. réf. PK");

    private final String label;

    private MDRFKTableColumn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getPosition(){
        int position= -1;
        int i = -1;;
        for (MDRFKTableColumn anElement: MDRFKTableColumn.values()){
            i++;
            if (anElement == this){
                position = i;
            }
        }
        return position;
    }

    public static int getNbColumns(){
        return MDRFKTableColumn.values().length;
    }

    public static int getPosition (MDRFKTableColumn column){
        int position= -1;
        int i = -1;;
        for (MDRFKTableColumn anElement: MDRFKTableColumn.values()){
            i++;
            if (anElement == column){
                position = i;
            }
        }
        return position;
    }
}
