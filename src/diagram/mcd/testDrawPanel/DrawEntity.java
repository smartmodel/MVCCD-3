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


    public DrawEntity(ArrayList<MCDEntityDraw> listEntite) {
        //Une entité est composée d'un rectangle, de trois lignes permettant de séparer le rectangle en deux et d'une liste d'attribut
        //Crée une nouvelle liste de type entités pour contenir les elements à dessiner
        boxes = new ArrayList<>();

        int x = 0;
        int y = 0;
        int width=250;
        int height=500;
        mathsForEntity= new MathsForEntity();
        selected=null;

        /*Boucle parcourant toutes les entités de la liste passée en paramètre
        Chaque itération va récuperer toutes les informations concernant les entités (Coordonnées, taille, attributs
        Coordonnées des lignes pour les ajouté dans une liste*/
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

            //Ajout de l'entité dans la liste Box qui va être utilisé dans les listeners

            boxes.add(new MCDEntityDraw(new Rectangle(x,y,width,height),linesList,attributesList));

        }


        MouseAdapter ma = new MouseAdapter() {

            private MCDEntityDraw previous;
            private Point delta;

            //Listener qui va s'activer si l'utilisateur appuye sur une entité
            @Override
            public void mousePressed(MouseEvent e) {
                //Il faut tester si l'utilisateur a bien cliquer sur une entité ou s'il a cliquer à coté
                previous = selected;
                if (selected == null || !selected.getRectangleEntite().contains(e.getPoint())) {
                    for (int index = 0; index <boxes.size(); index++) {
                        MCDEntityDraw box = boxes.get(index);
                        //Si l'utilisateur clique sur une entité il faut peindre le rectangle en bleu
                        if (box.getRectangleEntite().contains(e.getPoint())) {
                            selected = box;
                            selectedIndex= index;
                            delta = new Point(e.getX() - selected.getRectangleEntite().x, e.getY() - selected.getRectangleEntite().y);
                            repaint();
                            break;
                        }
                    }
                    //Si l'utilisateur a cliqué sur deux entités il faut uniquement que la dernière soit peinte en bleu
                    if (selected != null && boxes.size()>1) {
                        boxes.remove(selected);
                        boxes.add(boxes.size() - 1, selected);
                    }
                } else if (selected != null) {
                    delta = new Point(e.getX() - selected.getRectangleEntite().x, e.getY() - selected.getRectangleEntite().y);
                }
            }

            //Ce listener s'active uniquement si l'utilisateur clique sur une entité (Cliquer = presser et relacher très vite)
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO Base du code pour ajouter un menu déroulant en cas de clic droit (Le clic droit fonctionne)
                /*
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


                }*/
                //Permet de déselectionné l'entité si elle est peinte en bleu elle redevient noire
                if (selected == previous && selected != null && selected.getRectangleEntite().contains(e.getPoint())) {
                    selected = null;
                    repaint();
                }
            }

            //Listener qui va s'activer quand l'utilisateur relache une entité
            @Override
            public void mouseDragged(MouseEvent e) {
                //Dans cette méthode il faut modifié toute les coordonnées de l'entité deplacée afin de l'afficher ailleurs
                List<MCDEntityDraw> reversed = new ArrayList<>(boxes);
                if (selected != null) {
                    int x = e.getX() - delta.x;
                    int y = e.getY() - delta.y;
                    int height= (int) selected.rectangleEntite.getHeight();
                    int width= (int) selected.rectangleEntite.getWidth();




                    selected.getRectangleEntite().x = x;
                    selected.getRectangleEntite().y = y;
                    selected.getRectangleEntite().height= height;
                    selected.getRectangleEntite().width= width;

                    selected.getLinesList().get(0).x1=x;
                    selected.getLinesList().get(0).y1=mathsForEntity.calculateYLine1(y, height);
                    selected.getLinesList().get(0).x2=x+ width;
                    selected.getLinesList().get(0).y2=mathsForEntity.calculateYLine1(y, height);

                    selected.getLinesList().get(1).x1=x;
                    selected.getLinesList().get(1).y1=mathsForEntity.calculateYLine2(y, height);
                    selected.getLinesList().get(1).x2=x+ width;
                    selected.getLinesList().get(1).y2=mathsForEntity.calculateYLine2(y, height);

                    selected.getLinesList().get(2).x1=x;
                    selected.getLinesList().get(2).y1=mathsForEntity.calculateYLine3(y, height);
                    selected.getLinesList().get(2).x2=x+ width;
                    selected.getLinesList().get(2).y2=mathsForEntity.calculateYLine3(y, height);

                    selected.getAttributesList().get(0).xTextCenterTitre=x+((width /2)-"<<Entity>>".length()*6/2);
                    selected.getAttributesList().get(0).yTextCenterTitre=y+15;
                    selected.getAttributesList().get(1).xTextCenterTitre=x+((width /2)-selected.attributesList.get(1).textTitre.length()*6/2);
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

    //Méthode pour dessiner les entités dans l'interface (ATTENTION la méthode paintComponent va repeindre tous les composants
    //Même ceux qui ne sont pas modifiés
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        //Méthode pour dessiner toutes les entités à l'exception de l'entité séléctionnées
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
        //Code pour dessiner l'entité séléctionnée
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
