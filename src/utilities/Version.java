package utilities;

import exceptions.version.VersionFormatException;

public class Version {

  public static Integer weightVersion(String version) throws VersionFormatException {
    Integer resultat = 0;
    String[] parts = version.split("\\.");
    if (parts.length != 3) {
      throw new VersionFormatException();
    } else {
      for (int i = 0; i <= 2; i++) {
        Integer partNum;
        try {
          partNum = Integer.valueOf(parts[i]);
        } catch (Exception e) {
          throw new VersionFormatException();
        }
        resultat = (int) (resultat + partNum * powpos(10, (2 - i) * 3));
      }
    }
    return resultat;
  }

  private static int powpos(int base, int exp) {
    int resultat;
    if (exp > 0) {
      resultat = base * powpos(base, exp - 1);
    } else {
      resultat = 1;
    }
    return resultat;
  }
}
