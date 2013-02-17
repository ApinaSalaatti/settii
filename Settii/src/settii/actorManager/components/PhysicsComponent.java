package settii.actorManager.components;

import java.awt.Rectangle;
import java.util.HashMap;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import settii.actorManager.BaseComponent;
import settii.eventManager.EventManager;
import settii.eventManager.events.*;
import settii.views.humanView.RenderObject;
import settii.Application;
import settii.utils.MathUtil;
/**
 *
 * @author Merioksan Mikko
 */
public class PhysicsComponent extends BaseComponent {
    // location and size
    private float x, y;
    private float width, height;
    
    // movement stuff
    private float maxAcceleration, accelerationRate;
    private float speed, maxSpeed;
    
    private float angleRad, turnSpeed;
    private float targetX, targetY, targetAngle;
    
    private Rectangle hitbox; // our hitbox is always axis-aligned and our location is in the center of it
    private int health;
    private int damage; // possible damage for this actor, only applies to projectiles
    
    private long lifetime; // lifetime of the actor in milliseconds. 0 = infinity
    private long lived;
    
    public PhysicsComponent() {
        maxAcceleration = 0.1f;
        accelerationRate = 0.0f;
        maxSpeed = 1.0f;
        angleRad = MathUtil.ANGLE_STRAIGHT_UP; // default angle is straight up
        targetAngle = angleRad; // when we spawn, we are targeting where we are headed
        turnSpeed = 0.001f; // default turn speed
        hitbox = new Rectangle(0, 0, 0, 0);
        lifetime = 0;
        lived = 0;
    }
    
    @Override
    public String getName() {
        return "PhysicsComponent";
    }
    
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
        hitbox.x = (int)(x - width / 2);
        hitbox.y = (int)(y - height / 2);
    }
    
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public int getHealth() {
        return health;
    }
    public void setHealth(int h) {
        health = h;
    }
    public void takeDamage(int d) {
        health -= d;
        
        if(health <= 0) {
            Application.get().getEventManager().queueEvent(new ActorDestroyedEvent(owner.getID()));
        }
    }
    
    public int getDamage() {
        return damage;
    }
    public void setDamage(int d) {
        damage = d;
    }
    
    /**
     * Set the acceleration rate. 1.0 means full acceleration, -1.0 means full brakes. 0.0 means we continue at the current speed.
     * 
     * @param rate rate of acceleration from 1.0 to -1.0
     */
    public void applyAcceleration(float rate) {
        accelerationRate = rate;
    }
    public void stopAcceleration() {
        accelerationRate = 0.0f;
    }
    
    public float getAngleRad() {
        return angleRad;
    }
    public void setAngleRad(float rad) {
        angleRad = rad;
        Application.get().getEventManager().queueEvent(new ActorTurnedEvent(owner.getID()));
    }
    public void setTarget(float tx, float ty) {
        this.targetX = tx;
        this.targetY = ty;
        float dx = x - tx;
        float dy = y - ty;
        setTargetAngle((float)Math.atan2(dy, dx));
    }
    public void setTargetAngle(float angle) {
        if(angle < 0) {
            targetAngle = angle + MathUtil.PI * 2;
        }
        else {
            targetAngle = angle;
        }
    }
    
    public Rectangle getHitbox() {
        return hitbox;
    }
    
    @Override
    public void update(long deltaMs) {
        speed += maxAcceleration * accelerationRate * deltaMs;
        if(speed > maxSpeed) {
            speed = maxSpeed;
        }
        
        float speedX = -(float)(speed * Math.cos(angleRad));
        float speedY = -(float)(speed * Math.sin(angleRad));
        
        float oldX = x;
        float oldY = y;
        float newX = x + speedX * deltaMs;
        float newY = y + speedY * deltaMs;
        
        if(oldX != newX || oldY != newY) {
            setLocation(newX, newY);
            Application.get().getEventManager().queueEvent(new ActorMovedEvent(owner.getID()));
        }
        
        if(turnSpeed > 0) {
            turn(deltaMs);
        }
        
        lived += deltaMs;
        if(lifetime != 0 && lived >= lifetime) {
            Application.get().getEventManager().queueEvent(new ActorDestroyedEvent(owner.getID()));
        }
    }
    
    public void turn(long deltaMs) {
        float targetAngleDeg = MathUtil.radToDeg(targetAngle);
        float currentAngleDeg = MathUtil.radToDeg(angleRad);
        
        WeaponsComponent wc = (WeaponsComponent)owner.getComponent("WeaponsComponent");
        if(currentAngleDeg < targetAngleDeg - 1f) {
            setAngleRad(angleRad + turnSpeed * deltaMs);
            if(wc != null) {
                wc.setReady(false);
            }
        }
        else if(currentAngleDeg > targetAngleDeg + 1f) {
            setAngleRad(angleRad - turnSpeed * deltaMs);
            if(wc != null) {
                wc.setReady(false);
            }
        }
        else {
            if(wc != null) {
                wc.setReady(true);
            }
        }
        float over = angleRad - MathUtil.degToRad(360);
        if(over > 0) {
            angleRad = 0 + over;
        }
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        for(int i = 0; i < attributes.getLength(); i++) {
            Node node = attributes.item(i);
            
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Node value = node.getFirstChild();
                if(node.getNodeName().equalsIgnoreCase("y")) {
                    y = Float.parseFloat(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("x")) {
                    x = Float.parseFloat(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("width")) {
                    width = Float.parseFloat(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("height")) {
                    height = Float.parseFloat(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("health")) {
                    health = Integer.parseInt(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("damage")) {
                    damage = Integer.parseInt(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("lifetime")) {
                    lifetime = Long.parseLong(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("turnspeed")) {
                    turnSpeed = Float.parseFloat(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("rendering")) {
                    HashMap<String, String> files = new HashMap<String, String>();
                    while(value != null) {
                        files.put(value.getNodeName(), value.getFirstChild().getNodeValue());
                        value = value.getNextSibling();
                    }
                    Application.get().getEventManager().queueEvent(new RenderableActorCreatedEvent(new RenderObject(owner.getID(), x, y, files)));
                }
            }
        }
        
        // set up hitbox to surround our location
        hitbox.setRect(x - width / 2, y - height / 2, width, height);
    }
}
