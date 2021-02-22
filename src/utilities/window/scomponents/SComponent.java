/**
 * Le paquetage offre une extension des composants Swing pour l'interaction utilisateur.
 */
package utilities.window.scomponents;

import javax.swing.*;

/**
 * Tous les composants étendus de Swing (classes S... dans ce paquetage) réalisent l'interface SComponent.
 */
public interface  SComponent {
    // Changement au niveau de l'application
    public static String CHANGEINAPPLICATION = "ChangeInAplication";
    // Changement au niveau du projet
    public static String CHANGEINPROJECT = "ChangeInProject";

    // Mise en évidence d'une erreur empêchant la validation d'un formulaire
    public static final int COLORERROR = 1;
    // Mise en évidence d'une incohérence n'empêchant la validation d'un formulaire
    public static final int COLORWARNING = 2;
    // Pas de mise en évidence d'erreur ou incohérence
    public static final int COLORNORMAL = 3;

    // Le contenu a changé
    boolean checkIfUpdated();
    // Equivalent de l'effet d'un commit; nouvelle transaction initialisée avec le contenu actuel
    void restartChange();
    // Réinitialisation du contenu
    void reset();
    // Mode en lecture seule
    void setReadOnly(boolean readOnly);
    boolean isReadOnly();
    // Mise en évidence d'erreur ou incohérence
    void setColor(int color);
    int getColor();
    // Indicateur d'erreur de saisie
    void setErrorInput(boolean errorInput);
    boolean isErrorInput();
    // Si oui, ce composant empêche la validation du formulaire en cs d'erreur de saisie
    void setCheckPreSave(boolean checkPreSave);
    boolean isCheckPreSave();
    //Etiquette associée
    JLabel getJLabel();
    // Nom donné au composant (pour faciliter la mise au point)
    String getName();
}
