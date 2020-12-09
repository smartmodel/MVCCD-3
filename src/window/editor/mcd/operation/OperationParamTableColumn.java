package window.editor.mcd.operation;


import utilities.window.editor.ITableColumn;

public enum OperationParamTableColumn implements ITableColumn {


    ID("Id"),
    TRANSITORY("Trans."),
    ORDER("Ordre"),
    NAME("Paramètre"),
    TYPE("Type"),
    SUBTYPE("Sous-type");


    private final String label;

    private OperationParamTableColumn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getPosition(){
        int position= -1;
        int i = -1;;
        for (OperationParamTableColumn anElement: OperationParamTableColumn.values()){
            i++;
            if (anElement == this){
                position = i;
            }
        }
        return position;
    }

    public static int getNbColumns(){
        return OperationParamTableColumn.values().length;
    }

    public static int getPosition (OperationParamTableColumn column){
        int position= -1;
        int i = -1;;
        for (OperationParamTableColumn anElement: OperationParamTableColumn.values()){
            i++;
            if (anElement == column){
                position = i;
            }
        }
        return position;
    }
}
