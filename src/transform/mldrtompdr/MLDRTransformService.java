package transform.mldrtompdr;

import mdr.MDRElement;
import mdr.services.MDRModelService;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRModel;
import mpdr.interfaces.IMPDRElement;

public class MLDRTransformService {


    public static void modifyNames(IMLDRElement mldrElement, IMPDRElement mpdrElement) {
        String name30 = ((MDRElement) mldrElement).getNames().getName30();
        if (!name30.equals(((MDRElement) mpdrElement).getNames().getName30())) {
            ((MDRElement) mpdrElement).getNames().setName30(name30);
        }
        String name60 = ((MDRElement) mldrElement).getNames().getName60();
        if (!name60.equals(((MDRElement) mpdrElement).getNames().getName60())) {
            ((MDRElement) mpdrElement).getNames().setName60(name60);
        }
        String name120 = ((MDRElement) mldrElement).getNames().getName120();
        if (!name120.equals(((MDRElement) mpdrElement).getNames().getName120())) {
            ((MDRElement) mpdrElement).getNames().setName120(name120);
        }
    }

    public static void modifyName(MPDRModel mpdrModel, MDRElement mpdrElement) {
        String name = MDRModelService.buildName(mpdrModel, mpdrElement);
        if (!name.equals(mpdrElement.getName())) {
            mpdrElement.setName(name);
        }
    }

}