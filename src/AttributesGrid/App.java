package AttributesGrid;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class App {
    //Liste contenant les attributs
    private static ArrayList<Attribute> list = new ArrayList<>();

    public static void main(String[] args) {
        try {
            /**
             * Appliquer le theme de la librairie Flatlaf sur la fenêtre :
             * Pour Ajouter la librairie Flatalf dans un projet, il faut tout d’abord aller sur le site officiel de
             * Flatlaf (https://www.formdev.com/flatlaf/) et télécharger le fichier flatlaf-<version>.jar.
             * Ensuite il faut aller dans File -> Project Structure -> Librairies et ajouter le fichier téléchargé
             * auparavant.
             */
            UIManager.setLookAndFeel( new FlatDarculaLaf() );

            //Ajouter les attributs
            list.add(new Attribute(1, "nom", "word", false, false));
            list.add(new Attribute(2, "prénom", "word", true, false));
            list.add(new Attribute(3, "adresse", "token", true, false));

            //Créer et afficher la fenêtre en passant la liste en paramètre
            AttributesGridFrame attributesFrame = new AttributesGridFrame(list);
            //Rendre visible la fenêtre
            attributesFrame.setVisible(true);

            //Récupérer la liste apres la fermeture de la fenêtre
            //Si des changements ont été appliqués alors la liste sera mise à jour
            //Si les changements ont été annulés alors la lite restera inchangée
            attributesFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    //Récupère la liste des attributs dans list
                    list = attributesFrame.getList();
                    //Affichage du tableau dans le terminal pour voir ce qui a été enregistré (Test)
                    for (Attribute attribut : list)
                        attribut.printAttribut();
                }
            });

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}