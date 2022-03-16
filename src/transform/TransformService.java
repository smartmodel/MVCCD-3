package transform;

import mdr.MDRElement;

public class TransformService {

    public static void modifyNames (String name, MDRElement cible) {
        if (!name.equals(cible.getNames().getName30())) {
            cible.getNames().setName30(name);
        }
        if (!name.equals(cible.getNames().getName60())) {
            cible.getNames().setName60(name);
        }
        if (!name.equals(cible.getNames().getName120())) {
            cible.getNames().setName120(name);
        }
    }

}