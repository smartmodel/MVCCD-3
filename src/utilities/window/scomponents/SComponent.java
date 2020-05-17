package utilities.window.scomponents;

import javax.swing.*;

public interface  SComponent {
    public static String CHANGEINAPPLICATION = "ChangeInAplication";
    public static String CHANGEINPROJECT = "ChangeInProject";

    public static final int COLORERROR = 1;
    public static final int COLORWARNING = 2;
    public static final int COLORNORMAL = 3;

    boolean checkIfUpdated();
    void restartChange();
    void reset();
    void setReadOnly(boolean readOnly);
    boolean isReadOnly();
    void setColor(int color);
    int getColor();
    void setErrorInput(boolean errorInput);
    boolean isErrorInput();
    void setCheckPreSave(boolean checkPreSave);
    boolean isCheckPreSave();
    JLabel getJLabel();

}
