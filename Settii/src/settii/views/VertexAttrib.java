package settii.views;

/**
 *
 * @author Merioksan Mikko
 */
public class VertexAttrib {
    public String name;
    public int numComponents;
    public int location;
    
    public VertexAttrib(int loc, String name, int numComp) {
        this.name = name;
        numComponents = numComp;
        location = loc;
    }
    
    @Override
    public String toString() {
        return name + " (" + numComponents + ")";
    }
}
