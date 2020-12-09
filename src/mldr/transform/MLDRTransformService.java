package mldr.transform;

import mdr.MDRElement;
import mdr.MDRElementNames;
import mdr.MDRNamingFormat;
import mdr.MDRNamingLength;
import mdr.services.MDRModelService;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import org.apache.commons.lang.StringUtils;
import utilities.Trace;

public class MLDRTransformService {


    public static void modifyNames(MDRElement mpdrElement, MDRElement mldrElement) {
        String name30 = mldrElement.getNames().getName30();
        if (!name30.equals(mpdrElement.getNames().getName30())) {
            mpdrElement.getNames().setName30(name30);
        }
        String name60 = mldrElement.getNames().getName60();
        if (!name60.equals(mpdrElement.getNames().getName60())) {
            mpdrElement.getNames().setName60(name60);
        }
        String name120 = mldrElement.getNames().getName120();
        if (!name120.equals(mpdrElement.getNames().getName120())) {
            mpdrElement.getNames().setName120(name120);
        }
    }

    public static void modifyName(MPDRModel mpdrModel, MDRElement mpdrElement, MDRElement mplrElement ) {
        String name = MDRModelService.buildName(mpdrModel, mpdrElement);
        Trace.println(name);
        // String name = mldrElement.getName();
        if (!name.equals(mpdrElement.getName())) {
            mpdrElement.setName(name);
        }
    }

}