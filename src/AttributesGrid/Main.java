package AttributesGrid;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    //Création d'un tableau 2d et ajout des attributs
    private static final Object[][] attributesTab = { {"1", "nom", "word", false} ,
            {"2", "prénom", "word", true},
            {"3", "adresse", "token", true} };

    public static void main(String[] args) {
        try {
             // Appliquer le theme de la librairie Flatlaf sur la fenêtre :
             // Pour Ajouter la librairie Flatlaf, il faut tout d’abord aller sur le site officiel de Flatlaf
             // (https://www.formdev.com/flatlaf/) et télécharger le fichier flatlaf-<version>.jar.
             // Ensuite il faut aller dans File -> Project Structure -> Librairies et ajouter le fichier téléchargé
             // auparavant.
            UIManager.setLookAndFeel( new FlatDarculaLaf() );

            //Créer et afficher la fenêtre en passant la liste des attributs en paramètre
            AttributesGridFrame attributesFrame = new AttributesGridFrame(attributesTab);
            //Rendre visible la fenêtre
            attributesFrame.setVisible(true);

            //Récupérer le tableau apres la fermeture de la fenêtre
            //Si des changements ont été appliqués alors le tableau sera mise à jour
            //Si les changements ont été annulés alors le tableau restera inchangée
            attributesFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) { attributesFrame.getTab();}
            });

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
