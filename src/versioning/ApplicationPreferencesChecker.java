package versioning;

public class ApplicationPreferencesChecker implements IVersioning {

  @Override
  public void adjust_3_0_19() {
    System.out.println("Ajustement du fichier de préférences");
  }
}
