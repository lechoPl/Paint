package gui;

import image.BRUSH;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

public class MyToolBar extends JToolBar {
    private JButton NewImage;
    private JButton LoadImage;
    private JButton SaveImgae;
    
    //spinnery do zmiany wielkosci
    private JSpinner ImageHeight;
    private JSpinner ImageWidth;
    
    private JSpinner Dupa;
    
    
    
    //przyciski do przyblizania i oddalania
    private JButton ZoomIn;
    private JButton ZoomOut;
    
    //przyciski do przesuwanie kierunku
    private JButton Up;
    private JButton Down;
    private JButton Right;
    private JButton Left;
    
    private ActionListener NewList = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.ClrCanvas();
        }
    };
    
    private ActionListener LoadList = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            
            fileChooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".ser");
                }

                @Override
                public String getDescription() {
                    return "SER Images";
                }
            });
            
            int returnVal = fileChooser.showOpenDialog(canvas.getRootPane());
            
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                
                canvas.LoadImage(file);
            }
        }
    };
    
    private ActionListener SaveList = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            JFileChooser fileChooser = new JFileChooser();
            
            fileChooser.setCurrentDirectory(new File("."));
            
            fileChooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".ser");
                }

                @Override
                public String getDescription() {
                    return "SER Images";
                }
            });
            
            int returnVal = fileChooser.showSaveDialog(canvas.getRootPane());
            
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                
                if(!file.getName().toLowerCase().endsWith(".ser"))
                    file = new File(file.getName()+".ser");
                
                canvas.SaveImage(file);
            }
            
            
        }
    };
    
    private ChangeListener SpinWidthList = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            MyJSpinner temp = ((MyJSpinner)e.getSource());
            int val = (int)temp.getValue();
            
            canvas.setCanvansSize(val, canvas.getCanvansSize().height);
        }
    };
    
    private ChangeListener SpinHeightList = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            MyJSpinner temp = ((MyJSpinner)e.getSource());
            int val = (int)temp.getValue();
            
            canvas.setCanvansSize(canvas.getCanvansSize().width, val);
        }
    };
    
    private ActionListener ZoomInList = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.setZoomLevel(canvas.getZoomLevel()+1);
            
            if(canvas.getZoomLevel() == 8)
                ((JButton)e.getSource()).setEnabled(false);
            
            ZoomOut.setEnabled(true);
        }
    };
    
    private ActionListener ZoomOutList = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.setZoomLevel(canvas.getZoomLevel()-1);
            
            if(canvas.getZoomLevel() == 0)
                ((JButton)e.getSource()).setEnabled(false);
            
            ZoomIn.setEnabled(true);
        }
    };
    
    private ActionListener LeftList = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.ToLeft();
        }
    };
    
    private ActionListener UpList = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.ToUp();        
        }
    };
    
    private ActionListener DownList = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.ToDown();
        }
    };
    
    private ActionListener RightList = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.ToRight();
        }
    };
    
    private ChangeListener SetBrush = new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
            JRadioButton temp = (JRadioButton)e.getSource();
            if( temp.isSelected() == true){
                if(temp == Pencil)
                    canvas.setBrush(BRUSH.BR_PENCIL);
                
                if(temp == Oval)
                    canvas.setBrush(BRUSH.BR_OVAL);
            }
        }
    };
    
    
            
    private ChangeListener SetBrushSize = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            BrushSizeSpinner temp = ((BrushSizeSpinner)e.getSource());
            int val = (int)temp.getValue();
            
            canvas.setBrushSize(val);
        }
    };
            
    private final PaintingPanel canvas;
    
    private JRadioButton Pencil;
    private JRadioButton Oval;
    
    public MyToolBar(PaintingPanel canvas){
        super("Pasek narzedzi");
        
        this.canvas = canvas;
        
        NewImage = new JButton("Nowy");
        LoadImage = new JButton("Wczytaj");
        SaveImgae = new JButton("Zapisz");
        
        NewImage.addActionListener(NewList);
        LoadImage.addActionListener(LoadList);
        SaveImgae.addActionListener(SaveList);
        
        this.add(NewImage);
        this.addSeparator();
        
        this.add(LoadImage);
        this.add(SaveImgae);
        this.addSeparator();
        
        ImageWidth = new MyJSpinner(canvas.getCanvansSize().width);
        ImageWidth.addChangeListener(SpinWidthList);
        
        ImageHeight = new MyJSpinner(canvas.getCanvansSize().height);
        ImageHeight.addChangeListener(SpinHeightList);
        
        add(new JLabel("Szerokosc: "));
        add(ImageWidth);
        this.addSeparator();
        
        add(new JLabel("Wysokosc: "));
        add(ImageHeight);
        this.addSeparator();
        
        ZoomIn =  new JButton(createImageIcon("img\\ZoomIn.png"));
        //ZoomIn.setPreferredSize(new Dimension(30,30));
        ZoomIn.addActionListener(ZoomInList);
        
        ZoomOut =  new JButton(createImageIcon("img\\ZoomOut.png"));
        //ZoomOut.setPreferredSize(new Dimension(30,30));
        ZoomOut.setEnabled(false);
        ZoomOut.addActionListener(ZoomOutList);
        
        this.add(ZoomIn);
        this.add(ZoomOut);
        this.addSeparator();
        
        Up =  new JButton(createImageIcon("img\\up.png"));
        //Up.setPreferredSize(new Dimension(30,15));
        Up.addActionListener(UpList);
        
        Down =  new JButton(createImageIcon("img\\down.png"));
        //Down.setPreferredSize(new Dimension(30,15));
        Down.addActionListener(DownList);
        
        Left =  new JButton(createImageIcon("img\\left.png"));
        //Left.setPreferredSize(new Dimension(15,30));
        Left.addActionListener(LeftList);
        
        Right =  new JButton(createImageIcon("img\\right.png"));
        //Right.setPreferredSize(new Dimension(15,30));
        Right.addActionListener(RightList);
        
        this.add(Left);
        this.add(Up);
        this.add(Down);
        this.add(Right);
        this.addSeparator();
        this.addSeparator();
        this.addSeparator();
        
        JPanel BrushPanel;
        BrushPanel = new JPanel(new GridLayout(2,0));
        
        ButtonGroup group = new ButtonGroup();
        
        Pencil = new JRadioButton("Ołówek");
        Pencil.addChangeListener(SetBrush);
        group.add(Pencil);
        BrushPanel.add(Pencil);
        
        Oval = new JRadioButton("Okrąg");
        Oval.addChangeListener(SetBrush);
        group.add(Oval);
        BrushPanel.add(Oval);
        
        group.setSelected(Pencil.getModel(), true);
        
        
        this.add(BrushPanel);
        this.addSeparator();
        
        
        BrushSizeSpinner BSsetSpin = new BrushSizeSpinner(canvas.getBrushSize());
        BSsetSpin.addChangeListener(SetBrushSize);
        this.add(BSsetSpin);
    }
    
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MyToolBar.class.getResource(path);
        if (imgURL != null) {
            ImageIcon temp = new ImageIcon(imgURL);
            
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Nie moge znajelsc pliku: " + path);
            return null;
        }
    }

}
