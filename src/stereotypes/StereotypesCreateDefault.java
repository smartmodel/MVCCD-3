package stereotypes;

import mcd.*;
import mdr.*;
import mldr.MLDRTable;
import mpdr.mysql.MPDRMySQLTable;
import mpdr.oracle.MPDROracleTable;
import mpdr.postgresql.MPDRPostgreSQLTable;
import preferences.Preferences;

public class StereotypesCreateDefault {

    private Stereotypes stereotypes;

    public StereotypesCreateDefault(Stereotypes stereotypes) {
        this.stereotypes = stereotypes;
    }

    public void create() {
        createMCD();
        createMDR();
        createMLDR();
        createMPDROracle();
        createMPDRMySQL();
        createMPDRPostgreSQL();
    }


    private Stereotype createStereotype(String name, String lienProg, String className){
        // Vérifier l'unicité  à faire !
        Stereotype stereotype = new Stereotype(stereotypes,name, lienProg, className);
        return stereotype;
    }


    private void createStereotypeMulti(String baseName, String baseLienprog, String className, int position) {
        createStereotype(baseName + Preferences.STEREOTYPE_SEPARATOR + position,
                baseLienprog + Preferences.STEREOTYPE_SEPARATOR + position,
                className);
    }

    public void createMCD(){
        createStereotype(
                Preferences.STEREOTYPE_ENTITY_NAME,
                Preferences.STEREOTYPE_ENTITY_LIENPROG,
                MCDEntity.class.getName());

        createStereotype(
                Preferences.STEREOTYPE_AID_NAME,
                Preferences.STEREOTYPE_AID_LIENPROG,
                MCDAttribute.class.getName());
        createStereotype(
                Preferences.STEREOTYPE_M_NAME,
                Preferences.STEREOTYPE_M_LIENPROG,
                MCDAttribute.class.getName());
        createStereotype(
                Preferences.STEREOTYPE_L_NAME,
                Preferences.STEREOTYPE_L_LIENPROG,
                MCDAttribute.class.getName());

        createStereotype(
                Preferences.STEREOTYPE_LP_NAME,
                Preferences.STEREOTYPE_LP_LIENPROG,
                MCDNID.class.getName());

        //#MAJ 2021-08-01 Vérfification code Stéréotypes et contraintes
        //Uniquement des stéréotypes indicés...
        /*
        createStereotype(
                Preferences.STEREOTYPE_NID_NAME,
                Preferences.STEREOTYPE_NID_LIENPROG,
                MCDNID.class.getName());

         */

        createStereotype(
                Preferences.STEREOTYPE_NID_NAME,
                Preferences.STEREOTYPE_NID_LIENPROG,
                MCDAssociation.class.getName());
        createStereotype(
                Preferences.STEREOTYPE_CID_NAME,
                Preferences.STEREOTYPE_CID_LIENPROG,
                MCDAssociation.class.getName());
        createStereotype(
                Preferences.STEREOTYPE_CP_NAME,
                Preferences.STEREOTYPE_CP_LIENPROG,
                MCDAssociation.class.getName());

        // Stéréotypes indicés
        createStereotypesMCDNID();
        createStereotypesMCDU();
    }



    private void createStereotypesMCDNID() {
        for (int i = 0; i < Preferences.STEREOTYPE_NID_MAX; i++){
            createStereotypeMulti(
                    Preferences.STEREOTYPE_NID_NAME,
                    Preferences.STEREOTYPE_NID_LIENPROG,
                    MCDNID.class.getName(),
                    i );
            createStereotypeMulti(
                    Preferences.STEREOTYPE_NID_NAME,
                    Preferences.STEREOTYPE_NID_LIENPROG,
                    MCDAttribute.class.getName(),
                    i );

        }
    }

    private void createStereotypesMCDU() {
        for (int i = 0; i < Preferences.STEREOTYPE_U_MAX; i++){
            createStereotypeMulti(
                    Preferences.STEREOTYPE_U_NAME,
                    Preferences.STEREOTYPE_U_LIENPROG,
                    MCDUnique.class.getName(),
                    i );
            createStereotypeMulti(
                    Preferences.STEREOTYPE_U_NAME,
                    Preferences.STEREOTYPE_U_LIENPROG,
                    MCDAttribute.class.getName(),
                    i );

        }
    }

    private void createMDR() {
        createStereotype(
                Preferences.STEREOTYPE_TABLE_NAME,
                Preferences.STEREOTYPE_TABLE_LIENPROG,
                MDRTable.class.getName());

        createStereotype(
                Preferences.STEREOTYPE_PK_NAME,
                Preferences.STEREOTYPE_PK_LIENPROG,
                MDRColumn.class.getName());

        createStereotype(
                Preferences.STEREOTYPE_M_NAME,
                Preferences.STEREOTYPE_M_LIENPROG,
                MDRColumn.class.getName());

        createStereotype(
                Preferences.STEREOTYPE_PK_NAME,
                Preferences.STEREOTYPE_PK_LIENPROG,
                MDRConstraint.class.getName());

        createStereotype(
                Preferences.STEREOTYPE_PK_NAME,
                Preferences.STEREOTYPE_PK_LIENPROG,
                MDRPK.class.getName());

        createStereotype(
                Preferences.STEREOTYPE_U_NAME,
                Preferences.STEREOTYPE_U_LIENPROG,
                MDRUnique.class.getName());

        createStereotypesMDRFK();
        createStereotypesMDRPFK();
        createStereotypesMDRNID();  // Pour les colonnes et non les contraintes
        // Difficile à traiter et peut-être source de confusion
        // Je ne les traite pas pour l'instant dans MDRColumn.getStereotypes()
        createStereotypesMDRU();  // Pour les colonnes et non les contraintes
    }



    private void createStereotypesMDRFK() {
        for (int i = 0; i < Preferences.STEREOTYPE_FK_MAX; i++){
            createStereotypeMulti(
                    Preferences.STEREOTYPE_FK_NAME,
                    Preferences.STEREOTYPE_FK_LIENPROG,
                    MDRColumn.class.getName(),
                    i );
            createStereotypeMulti(
                    Preferences.STEREOTYPE_FK_NAME,
                    Preferences.STEREOTYPE_FK_LIENPROG,
                    MDRFK.class.getName(),
                    i );
        }
    }



    private void createStereotypesMDRPFK() {
        for (int i = 0; i < Preferences.STEREOTYPE_PFK_MAX; i++){
            createStereotypeMulti(
                    Preferences.STEREOTYPE_PFK_NAME,
                    Preferences.STEREOTYPE_PFK_LIENPROG,
                    MDRColumn.class.getName(),
                    i );
            createStereotypeMulti(
                    Preferences.STEREOTYPE_PFK_NAME,
                    Preferences.STEREOTYPE_PFK_LIENPROG,
                    MDRFK.class.getName(),
                    i );
        }
    }


    private void createStereotypesMDRNID() {
        for (int i = 0; i < Preferences.STEREOTYPE_NID_MAX; i++){
            createStereotypeMulti(
                    Preferences.STEREOTYPE_NID_NAME,
                    Preferences.STEREOTYPE_NID_LIENPROG,
                    MDRColumn.class.getName(),
                    i );
        }
    }

    private void createStereotypesMDRU() {
        for (int i = 0; i < Preferences.STEREOTYPE_U_MAX; i++){
            createStereotypeMulti(
                    Preferences.STEREOTYPE_U_NAME,
                    Preferences.STEREOTYPE_U_LIENPROG,
                    MDRColumn.class.getName(),
                    i );
        }
    }



    private void createMLDR() {
    }


    private void createMPDROracle() {
        createStereotype(
                Preferences.STEREOTYPE_ORACLE_NAME,
                Preferences.STEREOTYPE_ORACLE_LIENPROG,
                MPDROracleTable.class.getName());
    }

    private void createMPDRMySQL() {
        createStereotype(
                Preferences.STEREOTYPE_MYSQL_NAME,
                Preferences.STEREOTYPE_MYSQL_LIENPROG,
                MPDRMySQLTable.class.getName());
    }

    private void createMPDRPostgreSQL() {
        createStereotype(
                Preferences.STEREOTYPE_POSTGRESQL_NAME,
                Preferences.STEREOTYPE_POSTGRESQL_LIENPROG,
                MPDRPostgreSQLTable.class.getName());
    }

}
