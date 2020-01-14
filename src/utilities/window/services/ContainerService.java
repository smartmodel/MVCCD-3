package utilities.window.services;

import java.awt.*;
import java.util.ArrayList;

public class ContainerService {


    public static ArrayList<Component> getContentComponentDeep(Component component){
        ArrayList<Component> resultat = new ArrayList<Component>();
        if (component instanceof Container){
            Container container = (Container) component;
            if (container.getComponents().length > 0){
                for (Component componentChild : container.getComponents()) {
                   resultat.addAll(getContentComponentDeep(componentChild));
                }
             }
        }
        resultat.add(component);
        return resultat ;
    }
}
