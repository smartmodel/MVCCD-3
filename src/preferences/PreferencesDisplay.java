package preferences;

public enum PreferencesDisplay {

    REPOSITORY("Edition depuis le référentiel"),
    MENU("Edition depuis la barre de menu"),
    BOTH("Edition depuis le réf. et le menu");

    private final String value;

    PreferencesDisplay(String value) {
        this.value = value;
    }

    public String getUMLAssociationDegreeValue() {
        return value;
    }

}
