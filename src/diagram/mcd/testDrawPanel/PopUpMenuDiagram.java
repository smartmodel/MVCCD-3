package diagram.mcd.testDrawPanel;

import m.IMCompliant;
import main.MVCCDElement;
import main.MVCCDWindow;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import utilities.window.scomponents.ISMenu;
import utilities.window.scomponents.SMenu;
import utilities.window.scomponents.SPopupMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpMenuDiagram extends SPopupMenu {
    private MVCCDWindow mvccdWindow;
    private MVCCDElement mvccdElement;

    public PopUpMenuDiagram(MVCCDElement element){
        MVCCDElement elementAModifier = element;
    }

    public void init(){
        treatGeneric(this, new MCDEntityEditingTreat());;
    }

    private void treatGeneric(ISMenu menu, EditingTreat editingTreat) {
        treatGenericRead(menu, editingTreat);
        treatGenericUpdate(menu, editingTreat);
        treatGenericDelete(menu, editingTreat);
    }

    private void treatGenericRead(ISMenu menu, EditingTreat editingTreat) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.read");
        treatGenericRead(menu, editingTreat, textMenu);
    }

    private void treatGenericUpdate(ISMenu menu, EditingTreat editingTreat) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.update");
        treatGenericUpdate(menu, editingTreat, textMenu);
    }

    private void treatGenericDelete(ISMenu menu, EditingTreat editingTreat) {
        String textMenu = MessagesBuilder.getMessagesProperty("menu.delete");
        treatGenericDelete(menu, editingTreat, textMenu);
    }

    private void treatGenericRead(ISMenu menu, EditingTreat editingTreat, String textMenu) {
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                editingTreat.treatRead(mvccdWindow, mvccdElement);
            }
        });
    }

    private void treatGenericUpdate(ISMenu menu, EditingTreat editingTreat, String textMenu) {
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editingTreat.treatUpdate(mvccdWindow, mvccdElement);
            }
        });
    }

    private void treatGenericDelete(ISMenu menu, EditingTreat editingTreat, String textMenu) {
        JMenuItem menuItem = new JMenuItem(textMenu);
        addItem(menu, menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editingTreat.treatDelete(mvccdWindow, mvccdElement);
            }
        });
    }

    private void addItem(ISMenu menu, JMenuItem menuItem) {
        if (menu instanceof SPopupMenu) {
            ((SPopupMenu) menu).add(menuItem);
        }
        if (menu instanceof SMenu) {
            ((SMenu) menu).add(menuItem);
        }
    }
}
