package gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;

public class ColorLabel extends JLabel{
    public final Color color;
    
    public ColorLabel(Color c){
        color = c;
        
        setPreferredSize(new Dimension(20,20));
        setBackground(c);
        
        setOpaque(true);
    }

}
