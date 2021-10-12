package mcd.interfaces;


public interface IMCDParameter {

    public String getName();
    public String getNameTree();
    public int getIdProjectElement();
    public int getOrder();
    public String getClassShortNameUI();

    String getNamePath();

    //#MAJ 2021-05-30 NameTarget
    // A voir l'utilité de getName(), getNameTree(), getNamePath()
    String getNameTarget();
}
