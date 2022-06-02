package comparatorsql.oracle;

public class OracleComparatorColumn {

    public boolean compareColumnName(String mpdrColumnName, String dbColumnName){
        //TODO
        if (mpdrColumnName.equals(dbColumnName)){
            System.out.println("colonnes : " + mpdrColumnName);
            return true;
        } else {
            return false;
        }
    }
}
