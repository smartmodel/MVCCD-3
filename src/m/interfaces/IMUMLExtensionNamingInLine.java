package m.interfaces;

import constraints.Constraint;
import stereotypes.Stereotype;

import java.util.ArrayList;

public interface IMUMLExtensionNamingInLine {
    ArrayList<Stereotype> getStereotypes();
    String getStereotypesInLine();
    ArrayList<Constraint> getConstraints();
    String getConstraintsInLine();
}
