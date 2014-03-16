package gui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Michal Lech
 */


public class MainFrame extends JFrame {
    private JPanel canvas = new PaintingPanel();
    private MyToolBar tools = new MyToolBar( (PaintingPanel) canvas );
    private ColorPanel colorPanel = new ColorPanel((PaintingPanel) canvas);
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    
    
    
    public MainFrame(){
        super("Edytor obrazów");
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //------- SIZE ------------
        Dimension dScreanSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        double min = 0.25;
        Dimension dMinScreanSize = new Dimension((int)(dScreanSize.width*min),
                (int)(dScreanSize.height*min));
        
        int width = (2*dScreanSize.width)/3;
        int height = (2*dScreanSize.height)/3;
        
        this.setSize(width, height);
        
        this.setLocation((dScreanSize.width - width)/2,
                (dScreanSize.height - height)/2);
        
        this.setMinimumSize(dMinScreanSize);
        //--------------------------
        
        this.setLayout( new   BorderLayout() );
        
        this.add(tools,  BorderLayout.PAGE_START);
        
        JScrollPane PaintingPanelScrolPane = new JScrollPane(canvas);
        JScrollPane ColorPanelScrolPane = new JScrollPane(colorPanel);
        
        
        splitPane.add(ColorPanelScrolPane);
        splitPane.add(PaintingPanelScrolPane);
        
        
        this.add(splitPane,  BorderLayout.CENTER);
        
        this.setVisible(true);
    }

}
