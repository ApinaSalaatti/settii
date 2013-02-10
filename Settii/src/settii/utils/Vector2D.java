package settii.utils;

/**
 *
 * @author Merioksan Mikko
 */
public class Vector2D {
    private float y;
    private float x;
    
    public Vector2D(float x, float y) {
        this.y = y;
        this.x = x;
    }
    
    public float getX() {
        return x;
    }
    public void setX(float newX) {
        this.x = newX;
    }
    public float getY() {
        return y;
    }
    public void setY(float newY) {
        this.y = newY;
    }
    public void set(Vector2D vector) {
        this.y = vector.getY();
        this.x = vector.getX();
    }
    
    public Vector2D add(Vector2D other) {
        float newY = this.y + other.getY();
        float newX = this.x + other.getX();
        
        return new Vector2D(newY, newX);
    }

    public Vector2D substract(Vector2D other) {
        float newY = this.y - other.getY();
        float newX = this.x - other.getX();
        
        return new Vector2D(newY, newX);
    }
    
    public Vector2D multiply(Vector2D other) {
        float newY = this.y * other.getY();
        float newX = this.x * other.getX();
        
        return new Vector2D(newY, newX);
    }
    public Vector2D multiply(float d) {
        float newY = this.y * d;
        float newX = this.x * d;
        
        return new Vector2D(newY, newX);
    }
    
    public Vector2D divide(Vector2D other) {
        float newY = this.y / other.getY();
        float newX = this.x / other.getX();
        
        return new Vector2D(newY, newX);
    }
    public Vector2D divide(float d) {
        float newY = this.y / d;
        float newX = this.x / d;
        
        return new Vector2D(newY, newX);
    }
    
    public Vector2D getUnit() {
        float length = (float)Math.sqrt(this.dot(this));
        
        Vector2D normalized = this.divide(length);
        
        return normalized;
    }
    
    public double dot(Vector2D other) {
        return this.y * other.getY() + this.x * other.getX();
    }
    
    public float getAngleDeg() {
        return (float)Math.atan2(x, y);
    }
    
    public float getAngleRad() {
        return (float)getAngleDeg() * (MathUtil.PI / 180.0f);
    }
    
    // static method for creating a heading vector between two points
    public static Vector2D createHeading(float y, float x, float yTarget, float xTarget) {
        return new Vector2D(yTarget - y, xTarget - x);
    }
}
