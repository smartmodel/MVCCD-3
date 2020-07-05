package diagram.mcd.testDrawPanel;

public class Attributes {
    String textTitre;
    float xTextCenterTitre, yTextCenterTitre;

    public Attributes() {
    }

    public Attributes(String textTitre) {
        this.textTitre = textTitre;
    }

    public Attributes(String textTitre, float xTextCenterTitre, float yTextCenterTitre) {
        this.textTitre = textTitre;
        this.xTextCenterTitre = xTextCenterTitre;
        this.yTextCenterTitre = yTextCenterTitre;
    }




    public String getTextTitre() {
        return textTitre;
    }


}
