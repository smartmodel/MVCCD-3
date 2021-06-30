package mcd;

import mdr.MDRUniqueNature;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MCDNID extends MCDUnicity {

    private  static final long serialVersionUID = 1000;
    public static final String CLASSSHORTNAMEUI = "Identifiant naturel";


    private boolean lienProg = false;

    public MCDNID(ProjectElement parent) {
        super(parent);
    }

    public MCDNID(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MCDNID(ProjectElement parent, String name) {
        super(parent, name);
    }

    @Override
    public String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }


    @Override
    public Stereotype getDefaultStereotype() {
        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();
        return stereotypes.getStereotypeByLienProg(MCDNID.class.getName(),
                preferences.STEREOTYPE_NID_LIENPROG,
                getOrderIndexInParentSameClass() + 1);
    }



    @Override
    public ArrayList<Stereotype> getToStereotypes() {
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(getDefaultStereotype());

        if (lienProg){
            resultat.add(stereotypes.getStereotypeByLienProg(this.getClass().getName(),
                    preferences.STEREOTYPE_LP_LIENPROG));
        }

        return resultat;
    }


    public boolean isLienProg() {
        return lienProg;
    }

    public void setLienProg(boolean lienProg) {
        this.lienProg = lienProg;
    }

    @Override
    public String getOfUnicity() {
        return MessagesBuilder.getMessagesProperty("of.nid.2");
    }

    @Override
    public MDRUniqueNature getMDRUniqueNature() {
        return MDRUniqueNature.NID;
    }
}
