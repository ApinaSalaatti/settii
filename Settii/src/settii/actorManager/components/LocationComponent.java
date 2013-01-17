package settii.actorManager.components;

import settii.actorManager.BaseComponent;
/**
 *
 * @author Merioksan Mikko
 */
public class LocationComponent extends BaseComponent {
    private double x, y;
    
    public LocationComponent() {
        x = 0;
        y = 0;
    }
    public LocationComponent(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String getName() {
        return "LocationComponent";
    }
    
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
