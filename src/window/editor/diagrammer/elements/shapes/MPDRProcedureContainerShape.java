package window.editor.diagrammer.elements.shapes;

import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.UMLPackageIntegrableShapes;

import java.awt.*;

public class MPDRProcedureContainerShape extends SquaredShape implements UMLPackageIntegrableShapes {

    private Color COLOR = Color.decode("#F0E29F");

    public MPDRProcedureContainerShape() {
        super();
    }

    @Override
    public void initUI(){
        this.setMinimumSize(new Dimension((int) (Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH / 1.5), Preferences.DIAGRAMMER_DEFAULT_CLASS_HEIGHT));
        this.setSize(this.getMinimumSize());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(COLOR);

        graphics.fillRect(0, (int) (width * 0.10), width, height);
        graphics.fillRoundRect(0, 0, width, height, 40, 40);

        this.setBackgroundColor();
    }

    private void setBackgroundColor() {
        this.setBackground(new Color(255, 255, 255));
    }

    @Override
    public void setLocationDifference(int differenceX, int differenceY) {
        super.setLocationDifference(differenceX, differenceY);
    }
}
