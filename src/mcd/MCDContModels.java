package mcd;

import main.MVCCDElement;
import mcd.compliant.MCDCompliant;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContPackages;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDNamePathParent;
import preferences.Preferences;
import project.Project;
import project.ProjectElement;
import project.ProjectService;
import utilities.files.UtilXML;

import java.util.ArrayList;

public class MCDContModels extends MCDElement implements IMCDModel, IMCDNamePathParent, IMCDContPackages,
        IMCDContContainer {

    private static final long serialVersionUID = 1000;

    public MCDContModels(ProjectElement parent, String name) {
        super(parent, name);
    }



    public MCDContModels(ProjectElement parent) {
        super (parent);
    }


    public ArrayList<String> treatCompliant(){
        MCDCompliant mcdCompliant = new MCDCompliant();
        // Il n'y a pas de modèles. Il faut donc tester toutes les entités
        ArrayList<String> resultat = mcdCompliant.check(ProjectService.getMCDEntities(), false);

        return resultat;

    }

}
