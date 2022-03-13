package AttributesGrid;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
    //Tableau contenant les noms des colonnes
    final private String[] columnNames = {"Num", "Nom", "Type", "Obligatoire"};
    //Tableau 2d avec les attributs
    private static Object[][] data ;

    /**
     * Constructeur : reçoit tableau d'attributs en paramètre et le stocke en local
     */
    MyTableModel(Object[][] data) {
        MyTableModel.data = data;
    }

    /**
     * Méthode qui retourne le nombre de lignes
     */
    public int getRowCount() {
        return data.length;
    }

    /**
     * Méthode qui retourne le nombre de colonnes
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Méthode qui retourne le nom de la colonne à l'index indiqué
     */
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    /**
     * Méthode qui retourne le type de la donnée de la colonne
     */
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    /**
     * Méthode qui retourne faux pour la colonne num car elle n'est pas éditable, et vrai pour
     * les autres colonnes.
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 1;
    }

    /**
     * Méthode qui retourne le contenu d'une cellule
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}