package gui;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class MyJSpinner extends JSpinner {
    private int Max = 5000;
    
    public MyJSpinner(int val){
        this.setModel(new SpinnerNumberModel(
                    val, 0, Max, 1));
        
        //this.setPreferredSize(null);
    }

}
