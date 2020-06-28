package diagram.mcd.testDrawPanel;

public class MathsForEntity {
    public double calculateYLine1(int y, int height){
        double yLine1= y+35;
        return yLine1;
    }
    public double calculateYLine2(int y, int height){
        double yLine1= y+((height)/1.67);
        return yLine1;
    }
    public double calculateYLine3(int y, int height){
        double yLine1= y+((height)/1.25);
        return yLine1;
    }
    public double calculateXCenter(int x, int width, String nomAttribut){
        double xCenter= x+((width/2)-"<<Entity>>".length()*6/2)/1.25;
        return xCenter;
    }

}
