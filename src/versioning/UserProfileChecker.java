package versioning;

public class UserProfileChecker implements IVersioning {

  @Override
  public void adjust_3_0_19() {
    System.out.println("Ajustement du profil utilisateur");
  }
}
