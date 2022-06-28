package window.editor.diagrammer.listeners;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import window.editor.diagrammer.elements.shapes.NoteShape;
import window.editor.diagrammer.utils.UIUtils;

public class NoteShapeListener implements KeyListener {

  private NoteShape noteShape;

  public NoteShapeListener(NoteShape noteShape) {
    this.noteShape = noteShape;
  }

  @Override
  public void keyTyped(KeyEvent e) {

    // Calcul de la taille minimale requise pour la TextArea
    int neededSize = (int) (this.noteShape.getTextArea().getPreferredSize().height + (2 * UIUtils.getNotePadding()));
    Graphics graphics = this.noteShape.getGraphics();
    FontMetrics fontMetrics = graphics.getFontMetrics(UIUtils.getShapeFont());
    int rowHeight = fontMetrics.getHeight();

    // On resize si la taille de la TextArea dépasse la taille de la note
    if (neededSize > this.noteShape.getHeight()) {
      Dimension actualSize = this.noteShape.getSize();
      Dimension newSize = new Dimension(actualSize.width, actualSize.height + rowHeight);
      this.noteShape.setSize(newSize);
      this.noteShape.repaint();
    }

  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}