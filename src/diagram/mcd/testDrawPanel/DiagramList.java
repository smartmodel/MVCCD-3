package diagram.mcd.testDrawPanel;

import java.util.ArrayList;

public class DiagramList {
    String name;
    ArrayList<MCDEntityDraw> MCDEntityDraws = new ArrayList<>();

    public DiagramList(String name, ArrayList<MCDEntityDraw> MCDEntityDraws) {
        this.name = name;
        this.MCDEntityDraws = MCDEntityDraws;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MCDEntityDraw> getMCDEntityDraws() {
        return MCDEntityDraws;
    }

    public void setMCDEntityDraws(ArrayList<MCDEntityDraw> MCDEntityDraws) {
        this.MCDEntityDraws = MCDEntityDraws;
    }

    public void addEntityToList(){

    }
    public void getListSize(ArrayList<MCDEntityDraw> arrayListEntite){

    }
}
