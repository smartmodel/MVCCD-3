package mcd;

import mdr.MDRUniqueNature;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MCDUnique extends MCDUnicity{

    private  static final long serialVersionUID = 1000;
    public static final String CLASSSHORTNAMEUI = "Unique";


    //private boolean absolute = false;

    public MCDUnique(ProjectElement parent) {
        super(parent);
    }

    public MCDUnique(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MCDUnique(ProjectElement parent, String name) {
        super(parent, name);
    }

    @Override
    public Stereotype getDefaultStereotype() {
        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();
        return stereotypes.getStereotypeByLienProg(MCDUnique.class.getName(),
                preferences.STEREOTYPE_U_LIENPROG,
                getOrderIndexInParentSameClass() + 1);
    }

    @Override
    public ArrayList<Stereotype> getStereotypes() {
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(getDefaultStereotype());

        return resultat;
    }

    @Override
    public String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }

    @Override
    public String getOfUnicity() {
        return MessagesBuilder.getMessagesProperty("of.unique.2");
    }

    @Override
    public MDRUniqueNature getMDRUniqueNature() {
        return MDRUniqueNature.UNIQUE;
    }
}
