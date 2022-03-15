package versioning;

import exceptions.version.VersionFormatException;
import main.MVCCDManager;
import main.MVCCDWindow;
import messages.MessagesBuilder;
import preferences.Preferences;
import profile.ProfileManager;
import project.ProjectManager;
import utilities.Version;
import utilities.window.DialogMessage;

public class VersionChecker {

  private final String VERSION_3_0_19 = "3.0.19";

  private final String projectVersion = ProjectManager.getProject().getVersion();
  private final String profileVersion = null;
  private final String applicationVersion = Preferences.APPLICATION_VERSION;
  private final String applicationLatestRelease = Preferences.APPLICATION_LATEST_VERSION_RELEASED;

  private Integer projectVersionWeight;
  // private Integer profileVersionWeight;
  private Integer applicationVersionWeight;
  private Integer projectApplicationLatestReleaseWeight;

  private final UserProfileChecker userProfileChecker = new UserProfileChecker();
  private final ProjectChecker projectChecker = new ProjectChecker();
  private final ApplicationPreferencesChecker applicationPreferencesChecker = new ApplicationPreferencesChecker();

  public void main() {

    try {

      // Calcul du poids des versions
      calculateVersionsWeights();

      // Compare les versions utilisées
      compareVersions();

    } catch (VersionFormatException e) {
      e.printStackTrace();
    }

  }

  private void calculateVersionsWeights() throws VersionFormatException {
    // Calcul des poids des versions
    applicationVersionWeight = Version.weightVersion(applicationVersion);
    projectVersionWeight = Version.weightVersion(projectVersion);
    projectApplicationLatestReleaseWeight = Version.weightVersion(applicationLatestRelease);

    System.out.println("Poids de la version actuelle de l'app : " + applicationVersionWeight);
    System.out.println("Poids de la version du projet : " + projectVersionWeight);

  }

  private void compareVersions() throws VersionFormatException {

    // Compare la version de l'application
    compareApplicationVersions();

    // Compare le projet
    compareProjectVersions();

  }

  private void compareProjectVersions() throws VersionFormatException {

    // Vérifie si la version du projet est plus récente que celle de l'application
    if (projectVersionWeight > applicationVersionWeight) {
      String message = MessagesBuilder.getMessagesProperty("project.version.older", new String[]{applicationVersion, projectVersion});
      DialogMessage.showError(MVCCDManager.instance().getMvccdWindow(), message);
    }

    // Vérifie si la version du projet est plus ancienne que celle de l'application
    if (projectVersionWeight < applicationVersionWeight) {
      String message = MessagesBuilder.getMessagesProperty("project.version.newer", new String[]{projectVersion, applicationVersion});
      DialogMessage.showOk(MVCCDManager.instance().getMvccdWindow(), message);

    // Ajustement des versions
    if (projectVersionWeight < Version.weightVersion(VERSION_3_0_19)) projectChecker.adjust_3_0_19();
      // ...
    }

  }

  private void compareApplicationVersions() {
    // Une mise à jour de l'application est disponible
    if (applicationVersionWeight < projectApplicationLatestReleaseWeight){
      String message = MessagesBuilder.getMessagesProperty("application.version.update.available", new String[]{applicationLatestRelease});
      DialogMessage.showOk(MVCCDManager.instance().getMvccdWindow(), message);
    }
  }

}
