package mpdr.tapis;

import constraints.Constraint;
import constraints.ConstraintService;
import constraints.Constraints;
import constraints.ConstraintsManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import main.MVCCDElement;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRTable;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

public abstract class MPDRBoxPackages extends MPDRBoxStoredCode {

  private static final long serialVersionUID = 1000;


  public MPDRBoxPackages(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
    super(parent, name, mldrElementSource);
  }

  public MPDRBoxPackages(ProjectElement parent, IMLDRElement mldrElementSource) {
    super(parent, mldrElementSource);
  }

  public MPDRBoxPackages(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
    super(parent, mldrElementSource, id);
  }


  @Override
  public ArrayList<Stereotype> getStereotypes() {
    // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
    ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

    Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
    Preferences preferences = PreferencesManager.instance().preferences();

    resultat.add(stereotypes.getStereotypeByLienProg(MPDRBoxPackages.class.getName(),
        preferences.STEREOTYPE_PROCEDURES_LIENPROG));

    return resultat;
  }

  @Override
  public ArrayList<Constraint> getConstraints() {
    ArrayList<Constraint> resultat = new ArrayList<Constraint>();

    Constraints constraints = ConstraintsManager.instance().constraints();
    Preferences preferences = PreferencesManager.instance().preferences();

    return resultat;
  }

  @Override
  public String getStereotypesInBox() {
    return StereotypeService.getUMLNamingInBox(getStereotypes());
  }

  @Override
  public String getStereotypesInLine() {
    return StereotypeService.getUMLNamingInLine(getStereotypes());
  }

  @Override
  public String getConstraintsInBox() {
    return ConstraintService.getUMLNamingInBox(getConstraints());
  }

  @Override
  public String getConstraintsInLine() {
    return ConstraintService.getUMLNamingInLine(getConstraints());
  }

  public ArrayList<MPDRPackage> getAllPackages() {
    ArrayList<MPDRPackage> resultat = new ArrayList<MPDRPackage>();
    for (MVCCDElement mvccdElement : getChilds()) {
      if (mvccdElement instanceof MPDRPackage) {
        resultat.add((MPDRPackage) mvccdElement);
      }
    }
    return resultat;
  }

  public List<String> getAllPackagesString() {
    if (getAllPackages().isEmpty()) {
      return null;
    } else {
      return this.getAllPackages().stream()
          .map(e ->
              e.getNames().getName30() + "()"
          )
          .collect(Collectors.toList());
    }
  }

  public MPDRPackage getMPDRPackageByType(MPDRPackageType type) {
    for (MPDRPackage mpdrPackage : getAllPackages()) {
      if (mpdrPackage.getType() == type) {
        return mpdrPackage;
      }
    }
    return null;
  }

  public MPDRContTAPIs getMPDRContTAPIs() {
    return (MPDRContTAPIs) getParent();
  }

  public MPDRTable getMPDRTableAccueil() {
    return getMPDRContTAPIs().getMPDRTableAccueil();
  }

  //TODO-0 A retirer en ayant les icones
  public String toString() {
    return "Code - " + super.toString();
  }
}
