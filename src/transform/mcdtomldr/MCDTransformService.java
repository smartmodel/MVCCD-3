package transform.mcdtomldr;

import mdr.MDRElement;
import mdr.MDRElementNames;
import mdr.MDRNamingFormat;
import mdr.MDRNamingLength;
import mdr.services.MDRModelService;
import mldr.MLDRModel;
import mldr.MLDRTable;

public class MCDTransformService {

    public static void names(MDRElement mdrElement, MDRElementNames newNames, MLDRModel mldrModel){
        // Enregistrement de l'objet contenant tous les noms
        mdrElement.setNames(newNames);

        // Affectation du nom
        // A partir des noms calculés et du nom sélectionné
        String newName = newNames.getNameByNameLength(mldrModel.getNamingLengthFuture());
        newName = MDRModelService.formatNaming(newName, mldrModel.getNamingFormatFuture());

        if (mdrElement.getName() != null) {
            if (!mdrElement.getName().equals(newName)) {
                mdrElement.setName(newName);
            }
        } else {
            mdrElement.setName(newName);
        }
    }

}
