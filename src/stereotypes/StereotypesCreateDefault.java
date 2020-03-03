package stereotypes;

import m.MElement;
import mcd.MCDAttribute;
import preferences.Preferences;

public class StereotypesCreateDefault {

    private Stereotypes stereotypes;

    public StereotypesCreateDefault(Stereotypes stereotypes) {
        this.stereotypes = stereotypes;
    }

    public void create(){
        createStereotype(
                Preferences.STEREOTYPE_MCDATTRIBUTE_AID_NAME,
                Preferences.STEREOTYPE_MCDATTRIBUTE_AID_LIENPROG,
                MCDAttribute.class.getName());
        createStereotype(
                Preferences.STEREOTYPE_MCDATTRIBUTE_M_NAME,
                Preferences.STEREOTYPE_MCDATTRIBUTE_M_LIENPROG,
                MCDAttribute.class.getName());
        createStereotype(
                Preferences.STEREOTYPE_MCDATTRIBUTE_L_NAME,
                Preferences.STEREOTYPE_MCDATTRIBUTE_L_LIENPROG,
                MCDAttribute.class.getName());
    }


    private Stereotype createStereotype(String name, String lienProg, String className){
        // Vérifier l'unicité  à faire !
        Stereotype stereotype = new Stereotype(stereotypes,name, lienProg, className);
        return stereotype;
    }

}
