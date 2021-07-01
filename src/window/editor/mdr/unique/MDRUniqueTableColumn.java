package window.editor.mdr.unique;

public enum MDRUniqueTableColumn {


    COLUMNNAME("Nom de colonne"),
    PARAMTYPE("Type de paramètre conceptuel");

    private final String label;

    private MDRUniqueTableColumn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getPosition(){
        int position= -1;
        int i = -1;;
        for (MDRUniqueTableColumn anElement: MDRUniqueTableColumn.values()){
            i++;
            if (anElement == this){
                position = i;
            }
        }
        return position;
    }

    public static int getNbColumns(){
        return MDRUniqueTableColumn.values().length;
    }

    public static int getPosition (MDRUniqueTableColumn column){
        int position= -1;
        int i = -1;;
        for (MDRUniqueTableColumn anElement: MDRUniqueTableColumn.values()){
            i++;
            if (anElement == column){
                position = i;
            }
        }
        return position;
    }
}
