package stereotypes;

import mcd.MCDAttribute;
import mcd.MCDNID;
import mcd.MCDUnique;
import preferences.Preferences;

public class StereotypesCreateDefault {

    private Stereotypes stereotypes;

    public StereotypesCreateDefault(Stereotypes stereotypes) {
        this.stereotypes = stereotypes;
    }

    public void create(){
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
        createStereotypesNID();
        createStereotypesU();
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

    private void createStereotypesNID() {
        for (int i = 0; i < Preferences.STEREOTYPE_NID_MAX; i++){
            createStereotypeMulti(
                    Preferences.STEREOTYPE_NID_BASE_NAME,
                    Preferences.STEREOTYPE_NID_BASE_LIENPROG,
                    MCDNID.class.getName(),
                    i );
            createStereotypeMulti(
                    Preferences.STEREOTYPE_NID_BASE_NAME,
                    Preferences.STEREOTYPE_NID_BASE_LIENPROG,
                    MCDAttribute.class.getName(),
                    i );

        }
    }

    private void createStereotypesU() {
        for (int i = 0; i < Preferences.STEREOTYPE_U_MAX; i++){
            createStereotypeMulti(
                    Preferences.STEREOTYPE_U_BASE_NAME,
                    Preferences.STEREOTYPE_U_BASE_LIENPROG,
                    MCDUnique.class.getName(),
                    i );
            createStereotypeMulti(
                    Preferences.STEREOTYPE_U_BASE_NAME,
                    Preferences.STEREOTYPE_U_BASE_LIENPROG,
                    MCDAttribute.class.getName(),
                    i );

        }
    }
}
