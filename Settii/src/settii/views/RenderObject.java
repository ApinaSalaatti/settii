package settii.views;

/**
 *
 * @author Merioksan Mikko
 */
public class RenderObject {
    private long actor;
    private double x, y;
    private double width, height;
    private String filename;
    
    public RenderObject(long a, double x, double y, String file) {
        actor = a;
        this.x = x;
        this.y = y;
        width = 50;
        height = 50;
        filename = file;
    }
    
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public long getActor() {
        return actor;
    }
    public String getFilename() {
        return filename;
    }
    
    public void move(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void changeFile(String file) {
        filename = file;
    }
}
