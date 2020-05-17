package utilities.window.scomponents.services;

import preferences.PreferencesManager;
import utilities.window.scomponents.SComponent;

import javax.swing.*;

public class SComponentService {

    static public void colorNormal(JComponent component) {
        component.setBorder(BorderFactory.createLineBorder(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_NORMAL));
        component.setBackground(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_NORMAL);

    }

    static public void colorWarning(JComponent component) {
        component.setBorder(BorderFactory.createLineBorder(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_ERROR));
        component.setBackground(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_NORMAL);
    }


    static public void colorError(JComponent component) {
        component.setBorder(BorderFactory.createLineBorder(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_ERROR));
        component.setBackground(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_ERROR);
    }


}