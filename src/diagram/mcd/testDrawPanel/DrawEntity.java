package diagram.mcd.testDrawPanel;

import diagram.Diagram;
import main.MVCCDElement;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDEntityEditingTreat;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;




public class DrawEntity extends JPanel {

    private java.util.List<MCDEntityDraw> boxes;
    private MCDEntityDraw selected;
    private int selectedIndex;
    public MathsForEntity mathsForEntity;


    public DrawEntity(ArrayList<MCDEntityDraw> listEntite, JPanel panelZoneDessin, MCDContEntities mcdContEntities) {
        //Une entité est composée d'un rectangle, de trois lignes permettant de séparer le rectangle en deux et d'une liste d'attribut
        //Crée une nouvelle liste de type entités pour contenir les elements à dessiner
        boxes = new ArrayList<>();

        int x = 0;
        int y = 0;
        int width=250;
        int height=200;
        mathsForEntity= new MathsForEntity();


        for (int index = 0; index < listEntite.size(); index++) {
            x= listEntite.get(index).getRectangleEntite().x;
            y= listEntite.get(index).getRectangleEntite().y;
            width= (int) listEntite.get(index).getRectangleEntite().getWidth();
            height= listEntite.get(index).getRectangleEntite().height;
            ArrayList<Attributes> attributesList= new ArrayList<>();
            attributesList.add(0, listEntite.get(index).getAttributesList().get(0));
            attributesList.add(1, listEntite.get(index).getAttributesList().get(1));


            for (int i=2; i<listEntite.get(index).getAttributesList().size(); i++ ){

                attributesList.add(i,listEntite.get(index).getAttributesList().get(i));

            }

            ArrayList<Line2D.Double> linesList= new ArrayList<>();
            linesList.add(0,new Line2D.Double(x,mathsForEntity.calculateYLine1(y,height),x+width,mathsForEntity.calculateYLine1(y,height)));
            linesList.add(1,new Line2D.Double(x,mathsForEntity.calculateYLine2(y,height),x+width,mathsForEntity.calculateYLine2(y,height)));
            linesList.add(2,new Line2D.Double(x,mathsForEntity.calculateYLine3(y,height),x+width,mathsForEntity.calculateYLine3(y,height)));

            boxes.add(new MCDEntityDraw(new Rectangle(x,y,width,height),linesList,attributesList));

        }

        int finalHeight = height;
        int finalWidth = width;
        MouseAdapter ma = new MouseAdapter() {

            private MCDEntityDraw previous;
            private Point delta;

            @Override
            public void mousePressed(MouseEvent e) {
                //ArrayList<MCDEntityDraw> reversed = new ArrayList<>(boxes);

                previous = selected;
                if (selected == null || !selected.getRectangleEntite().contains(e.getPoint())) {
                    for (int index = 0; index <boxes.size(); index++) {
                        MCDEntityDraw box = boxes.get(index);
                        if (box.getRectangleEntite().contains(e.getPoint())) {
                            selected = box;
                            selectedIndex= index;
                            delta = new Point(e.getX() - selected.getRectangleEntite().x, e.getY() - selected.getRectangleEntite().y);
                            repaint();
                            break;
                        }
                    }
                    if (selected != null && boxes.size()>1) {
                        boxes.remove(selected);
                        boxes.add(boxes.size() - 1, selected);
                    }
                } else if (selected != null) {
                    delta = new Point(e.getX() - selected.getRectangleEntite().x, e.getY() - selected.getRectangleEntite().y);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && selected.getRectangleEntite().contains(e.getPoint())) {
                    System.out.println("Right Click");
                    MVCCDWindow mvccdWindow = MVCCDManager.instance().getMvccdWindow();

                    ArrayList<MCDEntity> listVraiEntites= mcdContEntities.getMCDEntities();
                    for (int index=0; index<listVraiEntites.size(); index++){
                        if (selected.titre==listVraiEntites.get(index).getLongName()){
                            MCDEntity entiteModifier = listVraiEntites.get(index);
                            //PopUpMenuDiagram popmenu= new PopUpMenuDiagram(entiteModifier);
                            MCDEntityEditingTreat mcdEntityEditingTreat= new MCDEntityEditingTreat();
                            mcdEntityEditingTreat.treatUpdate(mvccdWindow,entiteModifier);


                            break;
                        }

                    }


                }
                if (selected == previous && selected != null && selected.getRectangleEntite().contains(e.getPoint())) {
                    selected = null;
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                List<MCDEntityDraw> reversed = new ArrayList<>(boxes);
                if (selected != null) {
                    int x = e.getX() - delta.x;
                    int y = e.getY() - delta.y;




                    selected.getRectangleEntite().x = x;
                    selected.getRectangleEntite().y = y;
                    selected.getRectangleEntite().height= finalHeight;
                    selected.getRectangleEntite().width= finalWidth;

                    selected.getLinesList().get(0).x1=x;
                    selected.getLinesList().get(0).y1=mathsForEntity.calculateYLine1(y, finalHeight);
                    selected.getLinesList().get(0).x2=x+ finalWidth;
                    selected.getLinesList().get(0).y2=mathsForEntity.calculateYLine1(y, finalHeight);

                    selected.getLinesList().get(1).x1=x;
                    selected.getLinesList().get(1).y1=mathsForEntity.calculateYLine2(y, finalHeight);
                    selected.getLinesList().get(1).x2=x+ finalWidth;
                    selected.getLinesList().get(1).y2=mathsForEntity.calculateYLine2(y, finalHeight);

                    selected.getLinesList().get(2).x1=x;
                    selected.getLinesList().get(2).y1=mathsForEntity.calculateYLine3(y, finalHeight);
                    selected.getLinesList().get(2).x2=x+ finalWidth;
                    selected.getLinesList().get(2).y2=mathsForEntity.calculateYLine3(y, finalHeight);

                    selected.getAttributesList().get(0).xTextCenterTitre=x+((finalWidth /2)-"<<Entity>>".length()*6/2);
                    selected.getAttributesList().get(0).yTextCenterTitre=y+15;
                    selected.getAttributesList().get(1).xTextCenterTitre=x+((finalWidth /2)-selected.attributesList.get(1).textTitre.length()*6/2);
                    selected.getAttributesList().get(1).yTextCenterTitre=y+30;
                    for (int i=2; i<selected.getAttributesList().size(); i++ ){
                        selected.getAttributesList().get(i).xTextCenterTitre=x+5;
                        selected.getAttributesList().get(i).yTextCenterTitre=(y+47)+((i-2)*15);
                    }

                    //Code pour introduire les nouvelles cordonnées dans la liste MVCCDElement
                    listEntite.set(selectedIndex, selected);
                    repaint();


                }
            }

        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();


        for (MCDEntityDraw box : boxes) {
            try {
                if (box != selected) {
                    g2d.setColor(Color.BLACK);
                    g2d.draw(box.rectangleEntite);
                    g2d.draw(box.linesList.get(0));
                    g2d.draw(box.linesList.get(1));
                    g2d.draw(box.linesList.get(2));

                    g2d.drawString(box.attributesList.get(0).getTextTitre(), box.attributesList.get(0).xTextCenterTitre, box.attributesList.get(0).yTextCenterTitre);
                    g2d.drawString(box.getAttributesList().get(1).textTitre, box.getAttributesList().get(1).xTextCenterTitre, box.getAttributesList().get(1).yTextCenterTitre);
                    for (int i = 2; i < box.getAttributesList().size(); i++) {
                        g2d.drawString(box.getAttributesList().get(i).textTitre, box.getAttributesList().get(i).xTextCenterTitre, box.getAttributesList().get(i).yTextCenterTitre);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (selected != null) {
            g2d.setColor(Color.BLUE);
            g2d.draw(selected.getRectangleEntite());
            g2d.draw(selected.getLinesList().get(0));
            g2d.draw(selected.getLinesList().get(1));
            g2d.draw(selected.getLinesList().get(2));

            g2d.drawString(selected.attributesList.get(0).getTextTitre(),
                    selected.attributesList.get(0).xTextCenterTitre,
                    selected.attributesList.get(0).yTextCenterTitre);
            g2d.drawString(selected.attributesList.get(1).getTextTitre(),selected.attributesList.get(1).xTextCenterTitre,selected.attributesList.get(1).yTextCenterTitre);
            for (int i=2; i<selected.getAttributesList().size(); i++){
                g2d.drawString(selected.attributesList.get(i).getTextTitre(),selected.attributesList.get(i).xTextCenterTitre, selected.attributesList.get(i).yTextCenterTitre);
            }
        }
        g2d.dispose();
    }

}
