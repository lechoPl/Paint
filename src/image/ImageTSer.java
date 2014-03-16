package image;

import java.awt.Color;
import java.io.Serializable;
import java.util.Vector;

public class ImageTSer implements Serializable{
    public Color bgColor;
    
    public int Width;
    public int Height;
    
    public Vector<ImageElement> Elements;
    
    /*public void setSize(int width, int height) {
        Width = width;
        Height = height;
    }*/
    
    public ImageTSer() {
        Elements = new Vector<ImageElement>();
        bgColor = Color.white;
        
        Width = 400;
        Height = 300;
    }
}
