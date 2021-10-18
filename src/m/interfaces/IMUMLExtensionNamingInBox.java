package m.interfaces;

import constraints.Constraint;
import stereotypes.Stereotype;

import java.util.ArrayList;

public interface IMUMLExtensionNamingInBox {
    ArrayList<Stereotype> getStereotypes();
    String getStereotypesInBox();
    ArrayList<Constraint> getConstraints();
    String getConstraintsInBox();
}
