package image;

import java.awt.Color;
import java.io.Serializable;

public class ImageElement implements Serializable{
    public final BRUSH brush;
    
    public final int size;
    public final Color color;
    
    public final int x1, x2, y1, y2;
    
    public ImageElement(BRUSH br, int size, Color c, int x1, int y1, int x2, int y2) {
        this.brush = br;
        this.size = size;
        
        this.color = c;
        
        this.x1 = x1;
        this.y1 = y1;
        
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public ImageElement(BRUSH br, int size, Color c, int x1, int y1) {
        this.brush = br;
        this.size = size;
        
        this.color = c;
        
        this.x1 = x1;
        this.y1 = y1;
        
        this.x2 = 0;
        this.y2 = 0;
    }
}
