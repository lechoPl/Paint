package gui;

import image.BRUSH;
import image.ImageElement;
import image.ImageTSer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

/*
 * Popraw ryswoanie w powiekszeniu zeby rysowalo w danym pikselu
 * 
 */
public class PaintingPanel extends JPanel {
    private JPanel canvas = new CanvasPanel();
    private ImageTSer image = new ImageTSer();
    
    //do rysowania przewidywanego rozmiaru
    ImageElement tempImage;
    
    private int CanvasWidth = 400;
    private int CanvasHeight = 300;
    
    
    private int ZoomLevel = 0; // powiekszanie o wszystkiego o 2^ZoomLevel
    private int ZoomValue = (int)Math.pow(2, ZoomLevel);
    
    private int BrushSize = 1;
    private BRUSH currentBrush = BRUSH.BR_PENCIL;
    
    private Color currentColor = Color.BLACK;
    
    private Color BackgroundColor = Color.WHITE;
    
    private boolean showGrid = true;
    
    private class CanvasPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            
            ImageElement el;
            
            if( image == null) return;
            if( image.Elements == null) return;
            
            if(showGrid && ZoomLevel > 2) {
                for(int x = 0; x < CanvasWidth; x++){
                    g.drawLine(x * ZoomValue, 0, x * ZoomValue, CanvasHeight* ZoomValue);
                }
                
                
                for(int y = 0; y < CanvasHeight; y++){
                    g.drawLine(0, y * ZoomValue, CanvasWidth * ZoomValue, y * ZoomValue);
                }
            }
            
            for (int i = 0; i < image.Elements.size(); i++) {
                el = image.Elements.elementAt(i);

                
                int join = el.size > 1 ? BasicStroke.CAP_ROUND : BasicStroke.CAP_SQUARE;
                
                BasicStroke stroke = new BasicStroke(                        
                        el.size * ZoomValue,
                        join,
                        BasicStroke.JOIN_BEVEL);
                        
                g2d.setColor(el.color);
                g2d.setStroke(stroke);
                
                switch(el.brush) {
                    case BR_PENCIL:
                        g2d.drawLine(
                                (int)( (el.x1 + 0.5) * ZoomValue),
                                (int)( (el.y1 + 0.5) * ZoomValue),
                                (int)( (el.x2 + 0.5) * ZoomValue),
                                (int)( (el.y2 + 0.5) * ZoomValue));
                        break;
                        
                        
                    case BR_OVAL:
                        g2d.drawOval(
                                (int)( (el.x1 + 0.5) * ZoomValue),
                                (int)( (el.y1 + 0.5) * ZoomValue),
                                (int)( (el.x2 + 0.5) * ZoomValue),
                                (int)( (el.y2 + 0.5) * ZoomValue));
                        break;
                        
                    default:
                        break;
                }
            }
            
            if(tempImage != null){
                el = tempImage;

                
                int join = el.size > 1 ? BasicStroke.CAP_ROUND : BasicStroke.CAP_SQUARE;
                
                BasicStroke stroke = new BasicStroke(                        
                        el.size * ZoomValue,
                        join,
                        BasicStroke.JOIN_BEVEL);
                        
                g2d.setColor(el.color);
                g2d.setStroke(stroke);
                
                switch(el.brush) {
                    case BR_PENCIL:
                        g2d.drawLine(
                                (int)( (el.x1 + 0.5) * ZoomValue),
                                (int)( (el.y1 + 0.5) * ZoomValue),
                                (int)( (el.x2 + 0.5) * ZoomValue),
                                (int)( (el.y2 + 0.5) * ZoomValue));
                        break;
                        
                        
                    case BR_OVAL:
                        g2d.drawOval(
                                (int)( (el.x1 + 0.5) * ZoomValue),
                                (int)( (el.y1 + 0.5) * ZoomValue),
                                (int)( (el.x2 + 0.5) * ZoomValue),
                                (int)( (el.y2 + 0.5) * ZoomValue));
                        break;
                        
                    default:
                        break;
                }
            }
        }
    }
    
    //---------------------------------------------
    //---------------------------------------------
    
    //  Rozmiar płótna
    public void setCanvansSize(int weigth, int height){
        CanvasWidth = weigth;
        CanvasHeight = height;
        
        image.Height = CanvasHeight;
        image.Width = CanvasWidth;
        //ucinanie zaduzych rzeczy
        
        canvas.setPreferredSize(new Dimension(CanvasWidth, CanvasHeight));
        canvas.revalidate();
    }
    
    public Dimension getCanvansSize(){
        return new Dimension(CanvasWidth, CanvasHeight);
    }
    
    //  Powiekszenie
    public void setZoomLevel(int zoom){
        ZoomLevel = zoom;
        
        ZoomValue = (int)Math.pow(2, ZoomLevel);
        
        canvas.setPreferredSize(new Dimension(
                (int)(CanvasWidth * ZoomValue), 
                (int)(CanvasHeight * ZoomValue)));
        canvas.revalidate();
        
        canvas.repaint();
    }
    
    public int getZoomLevel() {
        return ZoomLevel;
    }
    
    
    //  ROZMIAR PRZEDZLA
    public void setBrushSize(int val) {
        BrushSize = val;
    }
    
    public int getBrushSize() {
        return BrushSize;
    }
    
    //  Pedzel
    public void setBrush(BRUSH br){
        currentBrush = br;
    }
    
    public BRUSH getBrush() {
        return currentBrush;
    }
    
    //  Color
    public void setColor(Color c) {
        currentColor = c;
    }
    
    public Color getColor() {
        return currentColor;
    }
    
    //  BackgroundColor
    public void setBackgroundColor(Color c) {
        BackgroundColor = c;
        
        if(canvas != null)
            canvas.setBackground(c);
        
        image.bgColor = BackgroundColor;
        
        canvas.repaint();
    }
    
    public Color getBackgroundColor() {
        return BackgroundColor;
    }
    
    //  Wczytywanie obrazka
    public void LoadImage(File file){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
          fis = new FileInputStream(file);
          ois = new ObjectInputStream(fis);
          
          image = (ImageTSer) ois.readObject();
          
          this.setCanvansSize(image.Width, image.Height);
          canvas.setBackground(image.bgColor);
          
          canvas.revalidate();
          canvas.repaint();
          //Dopisz zmiane tla plotna

        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } finally {
          // zasoby zwalniamy w finally
          try {
            if (ois != null) ois.close();
          } catch (IOException e) {}
          try {
            if (fis != null) fis.close();
          } catch (IOException e) {}
        }   
    }
    
    //  Zapisywanie obrazka
    public void SaveImage(File file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        
        try {
          fos= new FileOutputStream(file);
          oos = new ObjectOutputStream(fos);

          oos.writeObject(image);

        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          // zamykamy strumienie w finally
          try {
            if (oos != null) oos.close();
          } catch (IOException e) {}
          try {
            if (fos != null) fos.close();
          } catch (IOException e) {}
        }
    }
    
    
    //  Czyszczenie calego płótna
    public void ClrCanvas() {
        image.Elements.removeAllElements();
        
        BackgroundColor = Color.white;
        canvas.setBackground(BackgroundColor);
        
        this.setCanvansSize(400,300);
        
        canvas.repaint();
    }
    
    public void ToUp(){
        Rectangle visiableRect = canvas.getVisibleRect();
        visiableRect.setLocation(visiableRect.x, 0);
        
        canvas.scrollRectToVisible(visiableRect);
    }
    
    public void ToDown(){
        Rectangle visiableRect = canvas.getVisibleRect();
        visiableRect.setLocation(visiableRect.x, canvas.getHeight() - visiableRect.height);
        
        canvas.scrollRectToVisible(visiableRect);
    }
    
    public void ToLeft(){
        Rectangle visiableRect = canvas.getVisibleRect();
        visiableRect.setLocation(0, visiableRect.y);
        
        canvas.scrollRectToVisible(visiableRect);
    }
    
    public void ToRight(){
        Rectangle visiableRect = canvas.getVisibleRect();
        visiableRect.setLocation(canvas.getWidth() - visiableRect.width, visiableRect.y);
        
        canvas.scrollRectToVisible(visiableRect);
    }
    
    //---------------------------------------------
    //---------------------------------------------
    
    //  Obsluga malowania
    private MouseInputListener MousList = new MouseInputAdapter(){
        private int preX;
        private int preY;
        
        
        @Override
        public void mousePressed(MouseEvent e) {
            /*dopiswyanie punktu poczatka lini*/
            preX = e.getX() / ZoomValue;
            preY = e.getY() / ZoomValue;
            /*
            if( currentBrush != BRUSH.BR_PENCIL) {
                    preX = e.getX() / ZoomValue;
                    preY = e.getY() / ZoomValue;
            }*/
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            tempImage = null;
                    
            int W = BrushSize * ZoomValue;
            int H = BrushSize * ZoomValue;
            
            if (SwingUtilities.isLeftMouseButton(e)) {
                int x = e.getX();// - W/2;
                int y = e.getY();// - H/2;
                
                if (x < 0) x = 0;
                if (y < 0) y = 0;
                
                if (x > CanvasWidth * ZoomValue)
                    x = CanvasWidth * ZoomValue;
                
                if (y > CanvasHeight * ZoomValue)
                    y = CanvasHeight * ZoomValue;
                
                canvas.scrollRectToVisible(new Rectangle(x, y, W, H));
                
                x = x / ZoomValue;
                y = y / ZoomValue;
                H = H / ZoomValue;
                W = W / ZoomValue;
                
                
                //Rectangle rect = new Rectangle(x, y, W, H);
                //circles.addElement(rect);
                
                ImageElement el;
                
                switch(currentBrush) {
                    case BR_PENCIL:
                        el = new ImageElement(currentBrush,
                            BrushSize,
                            currentColor,
                            x, y, x, y);
                        break;
                    
                    // w tym miejscu ewentualne rozszerzenie pedzili
                    case BR_OVAL:
                        el = new ImageElement(currentBrush,
                                BrushSize,
                                currentColor,
                                preX,
                                preY,
                                Math.abs(preX - x),
                                Math.abs(preY - y));
                    
                    default:
                        el = new ImageElement(currentBrush,
                                BrushSize,
                                currentColor,
                                preX,
                                preY,
                                Math.abs(preX - x),
                                Math.abs(preY - y));;
                        break;
                }
                
                image.Elements.add(el);
                
                canvas.repaint();
                
            }
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            int W = BrushSize * ZoomValue;
            int H = BrushSize * ZoomValue;
            
            if (SwingUtilities.isLeftMouseButton(e)) {
                int x = e.getX();
                int y = e.getY();
                
                /*if( currentBrush != BRUSH.BR_PENCIL) {
                    x = e.getX() - W/2;
                    y = e.getY() - H/2;
                }*/
                
                //int x = e.getX() - W/2;
                //int y = e.getY() - H/2;
                
                if (x < 0) x = 0;
                if (y < 0) y = 0;
                
                if (x > CanvasWidth * ZoomValue)
                    x = CanvasWidth * ZoomValue;
                
                if (y > CanvasHeight * ZoomValue)
                    y = CanvasHeight * ZoomValue;
                
                canvas.scrollRectToVisible(new Rectangle(x, y, W, H));
                
                x = x / ZoomValue;
                y = y / ZoomValue;
                H = H / ZoomValue;
                W = W / ZoomValue;
                
                //Rectangle rect = new Rectangle(x, y, W, H);
                
                ImageElement el;
                
                switch(currentBrush) {
                    case BR_PENCIL:
                        el = new ImageElement(currentBrush,
                            BrushSize,
                            currentColor,
                            preX, preY, x, y);
                        
                        image.Elements.add(el);
                        
                        preX = x;
                        preY = y;
                        
                        break;
                        
                    case BR_OVAL:
                        //preX - x to jest obecna szerokosc
                        //preY - y to jest obecna wysokosc
                        
                        tempImage = new ImageElement(currentBrush,
                                BrushSize,
                                currentColor,
                                preX,
                                preY,
                                Math.abs(preX - x),
                                Math.abs(preY - y));
                    
                    default:
                        tempImage = new ImageElement(currentBrush,
                                BrushSize,
                                currentColor,
                                preX,
                                preY,
                                Math.abs(preX - x),
                                Math.abs(preY - y));
                        break;
                }
                
                /*
                MyLine line = new MyLine(preX, preY, W, H);
                circles.addElement(line);
                */
                canvas.repaint(); 
            }
        }
    }; 
    
    public PaintingPanel() {
        image = new ImageTSer();
                
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(Color.GRAY);
        
        canvas.setBackground(BackgroundColor);
        canvas.setPreferredSize(new Dimension
                (CanvasWidth * ZoomValue,
                CanvasHeight * ZoomValue)
                );
        
        //odczytywanie klikniecia
        canvas.addMouseListener(MousList);
        
        //odczytywanie przeciagniecia
        canvas.addMouseMotionListener(MousList);
                
        this.add(canvas);
    }
}
