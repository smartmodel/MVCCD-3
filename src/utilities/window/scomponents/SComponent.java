package utilities.window.scomponents;

public interface  SComponent {
    public static String CHANGEINAPPLICATION = "ChangeInAplication";
    public static String CHANGEINPROJECT = "ChangeInProject";

    boolean checkIfUpdated();
    void restartChange();
    void reset();
    void setReadOnly(boolean readOnly);
    boolean isReadOnly();
    void setColorError();
    void setColorWarning();
    void setColorNormal();

}
