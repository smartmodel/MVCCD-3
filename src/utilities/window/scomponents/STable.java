package utilities.window.scomponents;

import main.MVCCDElement;
import utilities.window.scomponents.services.SComponentService;

import javax.swing.*;

public class STable extends JTable implements SComponent {

    private boolean updated = false;
    private boolean checkPreSave = false;
    private boolean readOnly = false;
    private IPanelInputContent panel;
    private int color;
    private boolean errorInput = false;
    private JLabel label;

    public STable(IPanelInputContent panel, JLabel label) {
        this.panel = panel;
        this.label = label;
        this.setColor(SComponent.COLORNORMAL);
    }

    public STable(IPanelInputContent panel) {
        this.panel = panel;
        this.setColor(SComponent.COLORNORMAL);
    }




    @Override
    public boolean checkIfUpdated() {
        return updated;
    }

    public void actionChangeActivated(){
        updated = true;
    }

    @Override
    public void restartChange() {
        updated = false;
    }

    @Override
    public void reset() {
        //TODO-2 A voir
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        if (readOnly) {
            super.setEnabled(false);
        }
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }


    public void setEnabled(boolean enabled){
        if (! isReadOnly()){
            super.setEnabled(enabled);
            if (label != null) label.setEnabled(enabled);
        } else{
            super.setEnabled(false);
            if (label != null) label.setEnabled(false);
        }
    }


    public void setVisible(boolean visible){
        super.setVisible(visible);
        if (label != null) label.setVisible(visible);
    }


    @Override
    public void setColor(int color) {
        this.color = color;
        if (color == SComponent.COLORNORMAL){
            SComponentService.colorNormal(this);
        }
        if (color == SComponent.COLORWARNING){
            SComponentService.colorWarning(this);
        }
        if (color == SComponent.COLORERROR){
            SComponentService.colorError(this);
        }
    }


    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setErrorInput(boolean errorInput) {

        this.errorInput = errorInput;
    }

    @Override
    public boolean isErrorInput() {

        return errorInput;
    }


    @Override
    public void setCheckPreSave(boolean checkPreSave) {
    }

    @Override
    public boolean isCheckPreSave() {
        return checkPreSave;
    }

    @Override
    public JLabel getJLabel() {
        return label;
    }

    public static int getOrderByLine(int i) {
        if ( i== 0){
            return MVCCDElement.FIRSTVALUEORDER;
        } else {
            return MVCCDElement.FIRSTVALUEORDER + (i * MVCCDElement.INTERVALORDER);
        }
    }
}
