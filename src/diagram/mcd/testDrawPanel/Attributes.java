package diagram.mcd.testDrawPanel;

public class Attributes {
    String textTitre;
    String textType;
    float xTextCenterType, yTextCenterType;
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

    public void setTextTitre(String textTitre) {
        this.textTitre = textTitre;
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public float getxTextCenterTitre() {
        return xTextCenterTitre;
    }

    public void setxTextCenterTitre(int xTextCenterTitre) {
        this.xTextCenterTitre = xTextCenterTitre;
    }

    public float getyTextCenterTitre() {
        return yTextCenterTitre;
    }

    public void setyTextCenterTitre(int yTextCenterTitre) {
        this.yTextCenterTitre = yTextCenterTitre;
    }

    public float getxTextCenterType() {
        return xTextCenterType;
    }

    public void setxTextCenterType(int xTextCenterType) {
        this.xTextCenterType = xTextCenterType;
    }

    public float getyTextCenterType() {
        return yTextCenterType;
    }

    public void setyTextCenterType(int yTextCenterType) {
        this.yTextCenterType = yTextCenterType;
    }
}
