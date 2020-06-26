package mcd;

import main.MVCCDElement;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContPackages;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDNamePathParent;
import preferences.Preferences;
import project.Project;
import project.ProjectElement;
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
        ArrayList<String> resultat =new ArrayList<String>();
        System.out.println("Contrôle de conformité");
        return resultat;
    }

}
