package utilities;

import main.MVCCDElement;
import mdr.MDRElement;
import mdr.MDRElementNames;
import mdr.MDRNamingLength;

public class TransformService {

    public static void name(MDRElement mdrElement, MDRElementNames newNames, MDRNamingLength nameLength){
        // Enregistrement de l'objet contenant tous les noms
        mdrElement.setNames(newNames);

        // Affectation du nom
        // A partir des noms calculés et du nom sélectionné
        String newName = newNames.getNameByNameLength(nameLength);
        if (mdrElement.getName() != null) {
            if (!mdrElement.getName().equals(newName)) {
                mdrElement.setName(newName);
            }
        } else {
            mdrElement.setName(newName);
        }
    }

    public static void name(MVCCDElement mvccdElement, String newName){
        if (mvccdElement.getName() != null) {
            if (!mvccdElement.getName().equals(newName)) {
                mvccdElement.setName(newName);
            }
        } else {
            mvccdElement.setName(newName);
        }
    }

}
