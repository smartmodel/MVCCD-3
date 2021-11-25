package application;

import application.services.ApplElementConvert;
import main.MVCCDElement;

import java.util.ArrayList;

//TODO-1
// A terme Stereotype, Constraint doivent hériter de cette classe

public abstract class ApplElement extends MVCCDElement {

    public ApplElement(MVCCDElement parent) {
        super(parent);
    }

    public ApplElement(MVCCDElement parent, String name) {
        super(parent, name);
    }

    public abstract String getLienProg();

    /**
     * Retourne une liste ordonnée des enfants.
     */
    public ArrayList<ApplElement> getApplChilds() {
        return ApplElementConvert.to(super.getChilds());
    }
    /**
     * Retourne une liste ordonnée des frères et soeurs.
     */
    public ArrayList<ApplElement> getApplBrothers(){
        return ApplElementConvert.to(getBrothers());
    }
}
