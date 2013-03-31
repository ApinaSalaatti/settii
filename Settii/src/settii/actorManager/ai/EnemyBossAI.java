package settii.actorManager.ai;

import org.w3c.dom.NodeList;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.actorManager.components.WeaponsComponent;
import settii.eventManager.events.FireWeaponEvent;
import settii.logic.sectors.EnemyLocation;

/**
 *
 * @author Merioksan Mikko
 */
public class EnemyBossAI extends BaseAI {
//private Movement movement;
    //private Targeting targeting;
    
    private long currentSector;
    private int currentLocation;
    
    // movement stuff...
    protected float targetX, targetY;
    boolean timeForNewTarget;
    boolean moving;
    
    // targeting stuff...
    private long targetID;
    
    public EnemyBossAI(long owner) {
        super(owner);
        currentSector = -1;
        currentLocation = -1;
        targetX = -1;
        targetY = -1;
        timeForNewTarget = true;
        moving = false;
    }
    
    /*
    private void setTargeting(String t) {
        if(t.equals("EnemyBasic")) {
            targeting = new EnemyBasicTargeting(owner);
        }
        else if(t.equals("Area")) {
            targeting = new AreaTargeting(owner);
        }
    }
    public void setTargeting(Targeting t) {
        targeting = t;
    }
    public Targeting getTargeting() {
        return targeting;
    }
    
    private void setMovement(String m) {
        if(m.equals("Enemy")) {
            movement = new EnemyMovement(owner);
        }
    }
    public void setMovement(Movement m) {
        movement = m;
    }
    public Movement getMovement() {
        return movement;
    }
    */
    
    public void setSector(long id) {
        currentSector = id;
    }
    public long getSector() {
        return currentSector;
    }
    
    @Override
    public void update(long deltaMs) {
        if(targetX < 0 || targetY < 0 || timeForNewTarget) {
            getMoveTarget();
        }
        
        if(targetX >= 0 && targetY >= 0) {
            PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
            float oX = ownPC.getX();
            float oY = ownPC.getY();
            if(oX > targetX - 22 && oX < targetX + 22 && oY > targetY - 22 && oY < targetY + 22) {
                moving = false;
                stop();
            }
            else if(moving) {
                move();
            }
            else if(!moving) {
                startMove();
            }
        }
        
        if(!moving) {
            targetAndShoot();
        }
    }
    
    public void setTarget(long id) {
        targetID = id;
    }
    
    public void getMoveTarget() {
        if(Application.get().getLogic().getGame().getSector(currentSector) != null) {
            EnemyLocation loc = Application.get().getLogic().getGame().getSector(currentSector).getBossLocation();
            if(loc != null) {
                targetX = loc.x();
                targetY = loc.y();
                timeForNewTarget = false;
            }
            /*
            ArrayList<EnemyLocation> possibleLocations = Application.get().getLogic().getGame().getSector(currentSector).getEnemyLocations();
            int rn = Application.get().getRNG().nextInt(possibleLocations.size());
            if(!possibleLocations.get(rn).taken()) {
                targetX = possibleLocations.get(rn).x();
                targetY = possibleLocations.get(rn).y();
                currentLocation = rn;
                possibleLocations.get(rn).reserve(owner);
                timeForNewTarget = false;
            }
            * 
            */
        }
    }
    
    public void startMove() {
        PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
        if(ownPC != null) {
            moving = true;
            ownPC.setTarget(targetX, targetY);
        }
    }
    
    public void move() {
        PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
        if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
            ownPC.applyAcceleration(1.0f);
        }
    }
    
    public void stop() {
        PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
        ownPC.applyAcceleration(-1.0f);
    }
    
    public GameActor getTargetedActor() {
        return Application.get().getLogic().getActor(targetID);
    }
    
    public void targetAndShoot() {
        setTarget(Application.get().getLogic().getGame().getBaseID());
        target();
    }
    
    public void target() {
        GameActor t = getTargetedActor();
        if(t != null) {
            PhysicsComponent tPC = (PhysicsComponent)t.getComponent("PhysicsComponent");
            if(tPC != null) {
                PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
                if(ownPC != null) {
                    if(ownPC.getTargetX() != tPC.getX() || ownPC.getTargetY() != tPC.getY()) {
                        ownPC.setTarget(tPC.getX(), tPC.getY());
                    }
                    if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
                        WeaponsComponent wc = (WeaponsComponent)getOwningActor().getComponent("WeaponsComponent");
                        if(wc != null) {
                            if(wc.ready()) {
                                Application.get().getEventManager().queueEvent(new FireWeaponEvent(owner));
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void destroy() {
        if(currentSector > -1) {
            Application.get().getLogic().getGame().getSector(currentSector).releaseLocation(owner);
        }
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        /*
        for(int i = 0; i < attributes.getLength(); i++) {
            Node node = attributes.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Node value = node.getFirstChild();
                if(node.getNodeName().equalsIgnoreCase("Targeting")) {
                    setTargeting(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("Movement")) {
                    setMovement(value.getNodeValue());
                }
            }
        }
        */
    }
}
