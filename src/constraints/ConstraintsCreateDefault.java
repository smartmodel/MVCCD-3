package constraints;

import mcd.*;
import mdr.MDRColumn;
import preferences.Preferences;

public class ConstraintsCreateDefault {

    private Constraints constraints;

    public ConstraintsCreateDefault(Constraints constraints) {
        this.constraints = constraints;
    }




    public void create(){
        createMCD();
        createMDR();
    }


    private Constraint createConstraint(String name, String lienProg, String className){
        // Vérifier l'unicité  - A faire !
        Constraint constraint = new Constraint(constraints,name, lienProg, className);
        return constraint;
    }

    public void createMCD(){
        createConstraint(
                Preferences.CONSTRAINT_ORDERED_NAME,
                Preferences.CONSTRAINT_ORDERED_LIENPROG,
                MCDAttribute.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_FROZEN_NAME,
                Preferences.CONSTRAINT_FROZEN_LIENPROG,
                MCDAttribute.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_ORDERED_NAME,
                Preferences.CONSTRAINT_ORDERED_LIENPROG,
                MCDAssEnd.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_FROZEN_NAME,
                Preferences.CONSTRAINT_FROZEN_LIENPROG,
                MCDAssociation.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_DELETECASCADE_NAME,
                Preferences.CONSTRAINT_DELETECASCADE_LIENPROG,
                MCDAssociation.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_ORIENTED_NAME,
                Preferences.CONSTRAINT_ORIENTED_LIENPROG,
                MCDAssociation.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_NONORIENTED_NAME,
                Preferences.CONSTRAINT_NONORIENTED_LIENPROG,
                MCDAssociation.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_ABSOLUTE_NAME,
                Preferences.CONSTRAINT_ABSOLUTE_LIENPROG,
                MCDUnique.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_ABSOLUTE_NAME,
                Preferences.CONSTRAINT_ABSOLUTE_LIENPROG,
                MCDNID.class.getName());
    }



    private void createMDR() {

        createMDRDatatype();
    }

    private void createMDRDatatype() {
        createConstraint(
                Preferences.MCDDATATYPE_BOOLEAN_NAME,
                Preferences.MCDDATATYPE_BOOLEAN_LIENPROG,
                MDRColumn.class.getName());

        createMDRDatatypeText();

        createMDRDatatypeNumber();

        createMDRDatatypeDatation();
    }



    private void createMDRDatatypeText() {
        createConstraint(
                Preferences.MCDDATATYPE_STRING_NAME,
                Preferences.MCDDATATYPE_STRING_LIENPROG,
                MDRColumn.class.getName());

        createConstraint(
                Preferences.MCDDATATYPE_NORMALIZEDSTRING_NAME,
                Preferences.MCDDATATYPE_NORMALIZEDSTRING_LIENPROG,
                MDRColumn.class.getName());

        createConstraint(
                Preferences.MCDDATATYPE_TOKEN_NAME,
                Preferences.MCDDATATYPE_TOKEN_LIENPROG,
                MDRColumn.class.getName());

        createConstraint(
                Preferences.MCDDATATYPE_WORD_NAME,
                Preferences.MCDDATATYPE_WORD_LIENPROG,
                MDRColumn.class.getName());

        createConstraint(
                Preferences.MCDDATATYPE_EMAIL_NAME,
                Preferences.MCDDATATYPE_EMAIL_LIENPROG,
                MDRColumn.class.getName());

        createConstraint(
                Preferences.MCDDATATYPE_HTTPURL_NAME,
                Preferences.MCDDATATYPE_HTTPURL_LIENPROG,
                MDRColumn.class.getName());

        createConstraint(
                Preferences.MCDDATATYPE_XML_NAME,
                Preferences.MCDDATATYPE_XML_LIENPROG,
                MDRColumn.class.getName());
    }

    private void createMDRDatatypeNumber() {
        // AID
        createConstraint(
                Preferences.MCDDOMAIN_AID_NAME,
                Preferences.MCDDOMAIN_AID_LIENPROG,
                MDRColumn.class.getName());

        //DECIMAL
        createConstraint(
                Preferences.MCDDATATYPE_DECIMAL_LIENPROG,
                Preferences.MCDDATATYPE_DECIMAL_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_NONPOSITIVEDECIMAL_LIENPROG,
                Preferences.MCDDATATYPE_NONPOSITIVEDECIMAL_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_NEGATIVEDECIMAL_LIENPROG,
                Preferences.MCDDATATYPE_NEGATIVEDECIMAL_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_NONNEGATIVEDECIMAL_LIENPROG,
                Preferences.MCDDATATYPE_NONNEGATIVEDECIMAL_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_POSITIVEDECIMAL_LIENPROG,
                Preferences.MCDDATATYPE_POSITIVEDECIMAL_LIENPROG,
                MDRColumn.class.getName());

        //INTEGER
        createConstraint(
                Preferences.MCDDATATYPE_INTEGER_LIENPROG,
                Preferences.MCDDATATYPE_INTEGER_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_NONPOSITIVEINTEGER_LIENPROG,
                Preferences.MCDDATATYPE_NONPOSITIVEINTEGER_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_NEGATIVEINTEGER_LIENPROG,
                Preferences.MCDDATATYPE_NEGATIVEINTEGER_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_NONNEGATIVEINTEGER_LIENPROG,
                Preferences.MCDDATATYPE_NONNEGATIVEINTEGER_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_POSITIVEINTEGER_LIENPROG,
                Preferences.MCDDATATYPE_POSITIVEINTEGER_LIENPROG,
                MDRColumn.class.getName());

        //MONEY
        createConstraint(
                Preferences.MCDDATATYPE_MONEY_LIENPROG,
                Preferences.MCDDATATYPE_MONEY_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_NONPOSITIVEMONEY_LIENPROG,
                Preferences.MCDDATATYPE_NONPOSITIVEMONEY_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_NEGATIVEMONEY_LIENPROG,
                Preferences.MCDDATATYPE_NEGATIVEMONEY_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_NONNEGATIVEMONEY_LIENPROG,
                Preferences.MCDDATATYPE_NONNEGATIVEMONEY_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_POSITIVEMONEY_LIENPROG,
                Preferences.MCDDATATYPE_POSITIVEMONEY_LIENPROG,
                MDRColumn.class.getName());
    }

    private void createMDRDatatypeDatation() {
        createConstraint(
                Preferences.MCDDATATYPE_DURATION_LIENPROG,
                Preferences.MCDDATATYPE_DURATION_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_DATETIME_LIENPROG,
                Preferences.MCDDATATYPE_DATETIME_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_DATE_LIENPROG,
                Preferences.MCDDATATYPE_DATE_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_TIME_LIENPROG,
                Preferences.MCDDATATYPE_TIME_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_GYEARMONTH_LIENPROG,
                Preferences.MCDDATATYPE_GYEARMONTH_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_GYEAR_LIENPROG,
                Preferences.MCDDATATYPE_GYEAR_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_GMONTHDAY_LIENPROG,
                Preferences.MCDDATATYPE_GMONTHDAY_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_GDAY_LIENPROG,
                Preferences.MCDDATATYPE_GDAY_LIENPROG,
                MDRColumn.class.getName());
        createConstraint(
                Preferences.MCDDATATYPE_GMONTH_LIENPROG,
                Preferences.MCDDATATYPE_GMONTH_LIENPROG,
                MDRColumn.class.getName());

    }


}
