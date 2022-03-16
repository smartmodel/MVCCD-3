package transform.mldrtompdr;

import mdr.MDRElement;
import mdr.services.MDRModelService;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRModel;
import mpdr.interfaces.IMPDRElement;

public class MLDRTransformService {


    public static void modifyNames(IMLDRElement mldrElement, IMPDRElement mpdrElement) {
        modifyNamesInternal((MDRElement) mldrElement, (MDRElement) mpdrElement);
    }

    public static void modifyNames(IMPDRElement mpdrElementSource, IMPDRElement mpdrElementCible) {
        modifyNamesInternal((MDRElement) mpdrElementSource, (MDRElement) mpdrElementCible);
    }


    private static void modifyNamesInternal (MDRElement source, MDRElement cible) {
        String name30 = source.getNames().getName30();
        if (!name30.equals(cible.getNames().getName30())) {
            cible.getNames().setName30(name30);
        }
        String name60 = source.getNames().getName60();
        if (!name60.equals(cible.getNames().getName60())) {
            cible.getNames().setName60(name60);
        }
        String name120 = source.getNames().getName120();
        if (!name120.equals(cible.getNames().getName120())) {
            cible.getNames().setName120(name120);
        }
    }

    public static void modifyName(MPDRModel mpdrModel, MDRElement mpdrElement) {
        String name = MDRModelService.buildName(mpdrModel, mpdrElement);
        if (!name.equals(mpdrElement.getName())) {
            mpdrElement.setName(name);
        }
    }
}