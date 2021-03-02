package utilities.window.scomponents;

import utilities.window.editor.DialogEditor;

/**
 * Les panneaux qui contiennent des composants réalisent l'interface ISComponent doivent à leur tour réaliser l'interface IPanelInputContent.
 */
public interface IPanelInputContent {

    /**
     * Utilisée par les composants tels que STextField pour savoir si les données du panneau sont initialisées ou pas.
     * Dès que les données du panneau sont initialisées, tout changement dans un composant est détectable par la méthode
     * isUpdate() spécifiée dans l'interface SComponent.
     * @return
     */
    public boolean isDataInitialized();
    public DialogEditor getEditor();
}
