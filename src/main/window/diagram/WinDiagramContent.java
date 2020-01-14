package main.window.diagram;

import main.MVCCDManager;
import main.window.repository.WinRepositoryContent;
import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinDiagramContent extends PanelContent implements ActionListener {

    JButton btnAdd;
    int cpte = 0;
    public WinDiagramContent(WinDiagram diagram) {
        super(diagram);

        JPanel content = new JPanel();
        JLabel label1 =new JLabel("Nom: ");
        JTextField text1 = new JTextField();
        text1.setPreferredSize(new Dimension(200,15));
        btnAdd = new JButton("Ajouter");
        content.add(btnAdd);
        btnAdd.addActionListener(this);

        super.setContent(content);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnAdd){
            WinRepositoryContent repContent = MVCCDManager.instance().getWinRepositoryContent();
            repContent.getTree().addObject(new String("NewAdd " + ++cpte) /*+ newNodeSuffix++*/);
        }
    }
}
