package main;

import console.ViewLogsManager;
import java.io.Console;
import javax.swing.SwingUtilities;
import messages.MessagesBuilder;
import versioning.VersionChecker;

public class MVCCD {

  /**
   * Démarre MVC-CD-3
   */
  public static void main(String[] args) {
    Console console;

    try {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                MVCCDManager.instance().start();
//            }
//        });

      MVCCDManager.instance().start();

      // Vérification des versions du projet, de l'application et prochainement du profil utilisateur
      VersionChecker versionChecker = new VersionChecker();
      versionChecker.main();
      
    } catch (Exception e) {
      if (MVCCDManager.instance().getConsoleManager() != null) {
        String message = MessagesBuilder.getMessagesProperty("main.finish.error");
        ViewLogsManager.catchException(e, MVCCDManager.instance().getMvccdWindow(), message);
      } else {
        e.printStackTrace();
      }
    }
  }
}
