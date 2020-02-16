package utilities.window.services;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ComponentService {
    public static boolean mouseEventInComponentAndContent(Component component, MouseEvent mouseEvent) {
        if (mouseEvent.getComponent() == component) {
            return true;
        } else {
            if (component instanceof Container) {
                Container container = (Container) component;
                for (Component componentChild : container.getComponents()) {
                    if (mouseEvent.getComponent() == componentChild) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean mouseEventInComponentAndContentDeep(Component component, MouseEvent mouseEvent) {
        ArrayList<Component> components = ContainerService.getContentComponentDeep(component);
        if (mouseEvent.getComponent() == component) {
            return true;
        } else {
            for (Component componentChild : components) {
                if (mouseEvent.getComponent() == componentChild) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean mouseInHorizontal(Component component, MouseEvent mouseEvent) {
        int x1 = component.getLocationOnScreen().x  ;
        int x2 = x1 + component.getWidth();
        boolean c1 = mouseEvent.getXOnScreen() >= x1;
        boolean c2 = mouseEvent.getXOnScreen() <= x2;
        return c1 && c2;
    }

    public static boolean mouseInVertical(Component component, MouseEvent mouseEvent) {
        int y1 = component.getLocationOnScreen().y  ;
        int y2 = y1 + component.getHeight();
        boolean c1 = mouseEvent.getYOnScreen() >= y1;
        boolean c2 = mouseEvent.getYOnScreen() <= y2;
        return c1 && c2;
    }

    public static boolean mouseIn(Component component, MouseEvent mouseEvent) {
        return mouseInHorizontal(component, mouseEvent)  && mouseInVertical(component, mouseEvent);
    }



    public static void increaseWidth(Component component, int delta){
        Dimension dim = component.getSize();
        component.setSize(dim.width + delta, dim.height);
    }

    public static void changeWidth(Component component, int width){
        Dimension dim = component.getSize();
        component.setSize(width, dim.height);
    }

    public static void changeMinimumWidth(Component component, int width){
        Dimension dim = component.getSize();
        component.setMinimumSize(new Dimension(width, dim.height));
    }

    public static void increaseHeight(Component component, int delta){
        Dimension dim = component.getSize();
        component.setSize(dim.width, dim.height + delta);
    }


    public static void increasePreferredWidth(Component component, int delta){
        Dimension dim = component.getPreferredSize();
        component.setSize(dim.width + delta, dim.height);
    }

    public static void changePreferredWidth(Component component, int width){
        Dimension dim = component.getSize();
        component.setPreferredSize(new Dimension(width, dim.height));
    }
    public static void increasePreferredHeight(Component component, int delta){
        Dimension dim = component.getPreferredSize();
        component.setSize(dim.width, dim.height + delta);
    }

    public static void increaseLocationX(Component component, int delta){
        Point loc = component.getLocation();
        component.setLocation(loc.x + delta, loc.y);
    }

    public static void increaseLocationY(Component component, int delta){
        Point loc = component.getLocation();
        component.setLocation(loc.x, loc.y  + delta);
    }

}
