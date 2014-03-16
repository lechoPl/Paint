package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColorPanel extends JPanel {
    
    private ColorLabel LabelBLACK = new ColorLabel(Color.BLACK);
    private ColorLabel LabelWHITE = new ColorLabel(Color.WHITE);
    
    private ColorLabel LabelDARK_GRAY = new ColorLabel(Color.DARK_GRAY);
    private ColorLabel LabelGRAY = new ColorLabel(Color.GRAY);
    
    private ColorLabel LabelDARK_BROWN = new ColorLabel(new Color(47,23,0));
    private ColorLabel LabelBROWN = new ColorLabel(new Color(128,64,0));
    
    private ColorLabel LabelRED = new ColorLabel(Color.RED);
    private ColorLabel LabelPINK = new ColorLabel(Color.PINK);
    
    private ColorLabel LabelORANGE = new ColorLabel(Color.ORANGE);
    private ColorLabel LabelYELLOW = new ColorLabel(Color.YELLOW);
    
    private ColorLabel LabelDARK_GREEN = new ColorLabel(new Color(34,177,76));
    private ColorLabel LabelGREEN = new ColorLabel(Color.GREEN);
    
    private ColorLabel LabelBLUE = new ColorLabel(Color.BLUE);
    private ColorLabel LabelCYAN = new ColorLabel(Color.CYAN);
    
    private ColorLabel LabelPURPLE = new ColorLabel(new Color(96,43,96));
    private ColorLabel LabelLIGHT_PURPLE = new ColorLabel(new Color(190,109,190));
    
    private JButton MoreColors = new JButton("Wiecej");
    
    private JPanel PanelWithColors;
    private JLabel ObecnyKolor;
    
    private void addCL() {
        GridLayout myLayout = new GridLayout(8,2);
        myLayout.setHgap(3);
        myLayout.setVgap(3);
        
        PanelWithColors = new JPanel(myLayout);
        
        PanelWithColors.add(LabelBLACK);
        PanelWithColors.add(LabelWHITE);
    
        PanelWithColors.add(LabelDARK_GRAY);
        PanelWithColors.add(LabelGRAY);
    
        PanelWithColors.add(LabelDARK_BROWN);
        PanelWithColors.add(LabelBROWN);
    
        PanelWithColors.add(LabelRED);
        PanelWithColors.add(LabelPINK);
    
        PanelWithColors.add(LabelORANGE);
        PanelWithColors.add(LabelYELLOW);
    
        PanelWithColors.add(LabelDARK_GREEN);
        PanelWithColors.add(LabelGREEN);
    
        PanelWithColors.add(LabelBLUE);
        PanelWithColors.add(LabelCYAN);
    
        PanelWithColors.add(LabelPURPLE);
        PanelWithColors.add(LabelLIGHT_PURPLE);
                
                
        LabelBLACK.addMouseListener(CLList);
        LabelWHITE.addMouseListener(CLList);
    
        LabelDARK_GRAY.addMouseListener(CLList);
        LabelGRAY.addMouseListener(CLList);
    
        LabelDARK_BROWN.addMouseListener(CLList);
        LabelBROWN.addMouseListener(CLList);
    
        LabelRED.addMouseListener(CLList);
        LabelPINK.addMouseListener(CLList);
    
        LabelORANGE.addMouseListener(CLList);
        LabelYELLOW.addMouseListener(CLList);
    
        LabelDARK_GREEN.addMouseListener(CLList);
        LabelGREEN.addMouseListener(CLList);
    
        LabelBLUE.addMouseListener(CLList);
        LabelCYAN.addMouseListener(CLList);
    
        LabelPURPLE.addMouseListener(CLList);
        LabelLIGHT_PURPLE.addMouseListener(CLList);
        
    }
    
    private MouseListener CLList = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            Color temp = ((ColorLabel)e.getSource()).color;
            
           canvas.setColor(temp);
           ObecnyKolor.setBackground(temp);
        }
    };
    
    private ActionListener MCList = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color newColor = JColorChooser.showDialog(canvas.getRootPane(), 
                            "Wybieranie Koloru",
                            canvas.getColor());
            
            if(newColor != null) {
                canvas.setColor(newColor);
                ObecnyKolor.setBackground(newColor);
            }
        }
    }; 
        
    private final PaintingPanel canvas;
    
    public ColorPanel(PaintingPanel canvas) {
        this.canvas = canvas;
        
        this.setLayout(new FlowLayout());
        
        JPanel temp;
        
        JPanel PackagePanel = new JPanel();
        PackagePanel.setLayout(new BoxLayout(PackagePanel, BoxLayout.Y_AXIS));
        
        add(PackagePanel);
        
        temp = new JPanel(new FlowLayout());
        temp.add(new JLabel("Obecny"));
        PackagePanel.add(temp);
        
        temp = new JPanel(new FlowLayout());
        ObecnyKolor = new JLabel("");
        ObecnyKolor.setBackground(canvas.getColor());
        ObecnyKolor.setPreferredSize(new Dimension(40,40));
        ObecnyKolor.setOpaque(true);
        temp.add(ObecnyKolor);
        PackagePanel.add(temp);
        
        temp = new JPanel(new FlowLayout());
        temp.add(new JLabel("Kolor"));
        PackagePanel.add(temp);
        
        
        addCL();
        temp = new JPanel(new FlowLayout());
        temp.add(PanelWithColors);
        
        PackagePanel.add(temp);
        
        temp = new JPanel(new FlowLayout());
        temp.add(MoreColors);
        MoreColors.addActionListener(MCList);
        PackagePanel.add(temp);
        
    }

}
