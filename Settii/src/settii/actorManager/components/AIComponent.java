/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.actorManager.components;

import settii.actorManager.GameActor;
import settii.Application;
import settii.actorManager.BaseComponent;
import settii.eventManager.events.FireWeaponEvent;
import settii.actorManager.ai.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Merioksan Mikko
 */
public class AIComponent extends BaseComponent {
    private BaseAI ai;
    
    public AIComponent() {
        
    }
    
    @Override
    public String getName() {
        return "AIComponent";
    }
    
    @Override
    public void update(long deltaMs) {
        if(ai != null) {
            ai.update(deltaMs);
        }
    }
    
    /*
    public void moveDown() {
        PhysicsComponent ownPC = (PhysicsComponent)owner.getComponent("PhysicsComponent");
        if(ownPC != null) {
            float targetX = ownPC.getX();
            float targetY = ownPC.getY() + 50;
            ownPC.setTarget(targetX, targetY);
            if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
                ownPC.applyAcceleration(0.02f);
            }
        }
    }
    
    public void stop() {
        PhysicsComponent ownPC = (PhysicsComponent)owner.getComponent("PhysicsComponent");
        ownPC.applyAcceleration(-1.0f);
    }
    */
    
    public BaseAI getAI() {
        return ai;
    }
    
    @Override
    public void destroy() {
        super.destroy();
        ai.destroy();
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        for(int i = 0; i < attributes.getLength(); i++) {
            Node node = attributes.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                if(node.getNodeName().equalsIgnoreCase("EnemyBasic")) {
                    ai = new EnemyBasicAI(owner.getID());
                }
                else if(node.getNodeName().equalsIgnoreCase("HumanBasicWeapon")) {
                    ai = new HumanBasicWeaponAI(owner.getID());
                }
                else if(node.getNodeName().equalsIgnoreCase("HumanAutomaticWeapon")) {
                    ai = new HumanAutomaticWeaponAI(owner.getID());
                }
                else if(node.getNodeName().equalsIgnoreCase("EnemyBoss")) {
                    ai = new EnemyBossAI(owner.getID());
                }
                else if(node.getNodeName().equalsIgnoreCase("EnemySlave")) {
                    ai = new EnemySlaveAI(owner.getID());
                }
                
                ai.createFromXML(node.getChildNodes());
            }
        }
    }
   
}
