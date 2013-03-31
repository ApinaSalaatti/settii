package settii.actorManager.ai;

import org.w3c.dom.NodeList;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.actorManager.components.StatusComponent;
import settii.actorManager.components.WeaponsComponent;
import settii.eventManager.events.FireWeaponEvent;
import settii.utils.MathUtil;

/**
 *
 * @author Merioksan Mikko
 */
public class HumanAutomaticWeaponAI extends BaseAI {
    //private Targeting targeting;
    private float guardAngle;
    
    // targeting stuff...
    private long targetID;
    
    public HumanAutomaticWeaponAI(long owner) {
        super(owner);
        guardAngle = -1;
        targetID = -1;
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
    
    @Override
    public void update(long deltaMs) {
        if(targeting != null) {
            targeting.update(deltaMs);
        }
    }
    */
    
    public void setTarget(long id) {
        targetID = id;
    }
    
    public GameActor getTargetedActor() {
        return Application.get().getLogic().getActor(targetID);
    }
    
    public void setGuardAngle(float angle) {
        if(angle < 0) {
            guardAngle = angle + MathUtil.PI * 2;
        }
        else {
            guardAngle = angle;
        }
    }
    
    @Override
    public void update(long deltaMs) {
        if(getTargetedActor() == null)
            checkEnemies();
        
        target();
    }
    
    // TODO: this whole thing is total shit
    private void checkEnemies() {
        /*
        GameActor a = getOwningActor();
        PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        WeaponsComponent wc = (WeaponsComponent)a.getComponent("WeaponsComponent");
        
        float cx = pc.getX();
        float cy = pc.getY();
        
        float attackAngle = wc.getAttackAngle();
        float range = wc.getRange();
        
        float angleToFirstEdge = -attackAngle - guardAngle;
        float angleToSecondEdge = attackAngle - guardAngle;
        float firstEdgeX = range * (float)Math.cos(angleToFirstEdge);
        float firstEdgeY = range * (float)Math.sin(angleToFirstEdge);
        float secondEdgeX = range * (float)Math.cos(angleToSecondEdge);
        float secondEdgeY = range * (float)Math.sin(angleToSecondEdge);
        
        for(GameActor enemy : Application.get().getLogic().getActors()) {
            StatusComponent enemySC = (StatusComponent)enemy.getComponent("StatusComponent");
            StatusComponent ownSC = (StatusComponent)getOwningActor().getComponent("StatusComponent");
            if(!enemySC.getAllegiance().equals(ownSC.getAllegiance()) && !enemySC.getType().equalsIgnoreCase("Projectile")) { // only shoot enemies. :)
                PhysicsComponent enemyPC = (PhysicsComponent)enemy.getComponent("PhysicsComponent");
                float relativeX = Math.abs(enemyPC.getX() - cx);
                float relativeY = Math.abs(enemyPC.getY() - cy);

                if(withinRadius(relativeX, relativeY, range) && isClockwise(firstEdgeX, firstEdgeY, relativeX, relativeY) && !isClockwise(secondEdgeX, secondEdgeY, relativeX, relativeY)) {
                    setTarget(enemy.getID());
                    return;
                }
            }
        }
        * 
        */
        
        GameActor a = getOwningActor();
        PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        WeaponsComponent wc = (WeaponsComponent)a.getComponent("WeaponsComponent");
        
        float cx = pc.getX();
        float cy = pc.getY();
        float range = wc.getRange();
        
        for(GameActor enemy : Application.get().getLogic().getActors()) {
            StatusComponent enemySC = (StatusComponent)enemy.getComponent("StatusComponent");
            StatusComponent ownSC = (StatusComponent)getOwningActor().getComponent("StatusComponent");
            if(!enemySC.getAllegiance().equals(ownSC.getAllegiance()) && !enemySC.getType().equalsIgnoreCase("Projectile")) { // only shoot enemies. :)
                PhysicsComponent enemyPC = (PhysicsComponent)enemy.getComponent("PhysicsComponent");
                float relativeX = Math.abs(enemyPC.getX() - cx);
                float relativeY = Math.abs(enemyPC.getY() - cy);

                if(withinRadius(relativeX, relativeY, range)) {
                    setTarget(enemy.getID());
                    return;
                }
            }
        }
        
        // no target within radius
        setTarget(-1);
    }
    
    private boolean withinRadius(GameActor enemy) {
        GameActor a = getOwningActor();
        PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        WeaponsComponent wc = (WeaponsComponent)a.getComponent("WeaponsComponent");
        PhysicsComponent enemyPC = (PhysicsComponent)enemy.getComponent("PhysicsComponent");
        
        float cx = pc.getX();
        float cy = pc.getY();
        float range = wc.getRange();
        
        float relativeX = Math.abs(enemyPC.getX() - cx);
        float relativeY = Math.abs(enemyPC.getY() - cy);
        
        return withinRadius(relativeX, relativeY, range);
    }
    private boolean withinRadius(float distX, float distY, float radius) {
        return (distX * distX + distY * distY) <= (radius * radius);
    }
    
    /*
    private boolean isClockwise(float edgeX, float edgeY, float pointX, float pointY) {
        return -edgeX * pointY + pointX * edgeY > 0;
    }
    */
    
    public void target() {
        GameActor t = getTargetedActor();
        if(t != null && withinRadius(t)) {
            PhysicsComponent tPC = (PhysicsComponent)t.getComponent("PhysicsComponent");
            if(tPC != null) {
                PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
                if(ownPC != null) {
                    ownPC.setTarget(tPC.getX(), tPC.getY());
                    if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
                        WeaponsComponent wc = (WeaponsComponent)getOwningActor().getComponent("WeaponsComponent");
                        if(wc != null  && wc.ready()) {
                            Application.get().getEventManager().queueEvent(new FireWeaponEvent(owner));
                        }
                    }
                }
            }
        }
        else if(guardAngle >= 0) {
            PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
            ownPC.setTargetAngle(guardAngle);
        }
    }
    /*
    // TODO this is a piece of shit too...
    private boolean withinGuardArea(GameActor enemy) {
        if(enemy != null) {
            GameActor a = getOwningActor();
            PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
            WeaponsComponent wc = (WeaponsComponent)a.getComponent("WeaponsComponent");

            float cx = pc.getX();
            float cy = pc.getY();

            float attackAngle = wc.getAttackAngle();
            float range = wc.getRange();
            float angleToFirstEdge = -attackAngle - guardAngle;
            float angleToSecondEdge = attackAngle - guardAngle;
            float firstEdgeX = range * (float)Math.cos(angleToFirstEdge);
            float firstEdgeY = range * (float)Math.sin(angleToFirstEdge);
            float secondEdgeX = range * (float)Math.cos(angleToSecondEdge);
            float secondEdgeY = range * (float)Math.sin(angleToSecondEdge);
            StatusComponent enemySC = (StatusComponent)enemy.getComponent("StatusComponent");
            StatusComponent ownSC = (StatusComponent)getOwningActor().getComponent("StatusComponent");
            if(!enemySC.getAllegiance().equals(ownSC.getAllegiance()) && !enemySC.getType().equalsIgnoreCase("Projectile")) { // only shoot enemies. :)
                PhysicsComponent enemyPC = (PhysicsComponent)enemy.getComponent("PhysicsComponent");
                float relativeX = Math.abs(enemyPC.getX() - cx);
                float relativeY = Math.abs(enemyPC.getY() - cy);

                if(withinRadius(relativeX, relativeY, range) && isClockwise(firstEdgeX, firstEdgeY, relativeX, relativeY) && !isClockwise(secondEdgeX, secondEdgeY, relativeX, relativeY)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    * 
    */
    
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
            }
        }
        */
    }
}
