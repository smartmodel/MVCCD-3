package utilities.window.editor.shuttle;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShuttleDialog extends JDialog {

    private boolean closeProperly=false;
    private ShuttleList<String>  sl;

    public ShuttleDialog(String title, Point position){
        super.setModal(true);
        super.setTitle(title);
        super.setResizable(true);



        super.setSize(500,200);
        //sl = new ShuttleList<>(initialDatas);
       sl = new ShuttleList<>();

        this.add(getComponent());
    }

    public boolean canClosed() {
        if (!closeProperly){
            sl.setResultat(false);
        }
        super.dispose();
        closeProperly = false;
        return true;
    }


    public Component getComponent() {

        JPanel thePanel = new JPanel();
        thePanel.setLayout(new BorderLayout(10,10));
        thePanel.add(sl,BorderLayout.NORTH);

        Box panelButton = Box.createHorizontalBox();
        componentPanelButton(panelButton);
        thePanel.add(panelButton,BorderLayout.SOUTH);
        return thePanel;
    }







    public ArrayList<String> getResultat(){
        return sl.getResultat();
    }

    public void putDatas(ArrayList<String> allInitialElements, ArrayList<String> selectedInitialElements){
        ArrayList<String> leftElements = new ArrayList();
        for (String elementLeft:allInitialElements){
            boolean exist = false;
            for (String elementRight:selectedInitialElements){
                if (elementRight.equals(elementLeft)){
                    exist = true;
                }
            }
            if (!exist){
                leftElements.add(elementLeft);
            }
        }
        sl.init(leftElements, selectedInitialElements);
    }

    private void componentPanelButton(Box panelButton){


        JButton  buttonCancel = new JButton("Annuler");
        buttonCancel.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                sl.setResultat(false);
                closeProperly = true;
                canClosed();
            }
        });
        panelButton.add(buttonCancel);
        panelButton.add(Box.createHorizontalStrut(10));
        JButton  buttonOk = new JButton("Ok");
        buttonOk.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                sl.setResultat(true);
                closeProperly = true;
                canClosed();
            }

        });

        panelButton.add(buttonOk);
    }


    public void setPosition(Point position) {
        if (position != null){
            super.setLocation(position.x, position.y + 20);
        }
    }
}

