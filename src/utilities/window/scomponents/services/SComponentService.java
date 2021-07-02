package utilities.window.scomponents.services;

import preferences.Preferences;
import preferences.PreferencesManager;

import javax.swing.*;
import java.awt.*;

import static preferences.Preferences.*;

public class SComponentService {

    static public void colorNormal(JComponent component) {
        if (UIManager.getLookAndFeel().toString().equals(FLATDARK.toString())){
            component.setBorder(BorderFactory.createLineBorder(
                    PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_NORMAL));
            component.setBackground(GRIS);
            component.setForeground(TEXTE_THEMEFONCE);
        }

        if (UIManager.getLookAndFeel().toString().equals(FLATLIGHT.toString())){
            component.setBorder(BorderFactory.createLineBorder(
                    PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_NORMAL));
            component.setBackground(
                    PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_NORMAL);
        }
    }

    static public void colorWarning(JComponent component) {
        if (UIManager.getLookAndFeel().toString().equals(FLATDARK.toString())){
            component.setBorder(BorderFactory.createLineBorder(
                    PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_ERROR));
            component.setBackground(GRIS);
            component.setForeground(TEXTE_THEMEFONCE);
        }

        if (UIManager.getLookAndFeel().toString().equals(FLATLIGHT.toString())){
            component.setBorder(BorderFactory.createLineBorder(EDITOR_SCOMPONENT_LINEBORDER_ERROR));
            component.setBackground(EDITOR_SCOMPONENT_BACKGROUND_NORMAL);
        }
    }


    static public void colorError(JComponent component) {
        if (UIManager.getLookAndFeel().toString().equals(FLATDARK.toString())){
            component.setBorder(BorderFactory.createLineBorder(EDITOR_SCOMPONENT_LINEBORDER_ERROR));
            component.setBackground(EDITOR_SCOMPONENT_BACKGROUND_ERROR);
            component.setForeground(Color.DARK_GRAY);
        }

        if (UIManager.getLookAndFeel().toString().equals(FLATLIGHT.toString())){
            component.setBorder(BorderFactory.createLineBorder(EDITOR_SCOMPONENT_LINEBORDER_ERROR));
            component.setBackground(EDITOR_SCOMPONENT_BACKGROUND_ERROR);
        }
    }


}