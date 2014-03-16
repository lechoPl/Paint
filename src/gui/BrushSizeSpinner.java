package gui;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Michal Lech
 */
public class BrushSizeSpinner extends JSpinner {
    private int Max = 100;
    
    public BrushSizeSpinner(int val){
        this.setModel(new SpinnerNumberModel(
                    val, 1, Max, 1));
        
        //this.setPreferredSize(null);
    }

}
