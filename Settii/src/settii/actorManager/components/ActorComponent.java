package settii.actorManager.components;

import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import settii.Application;
import settii.actorManager.BaseComponent;
import settii.actorManager.GameActor;
import settii.eventManager.events.ActorDestroyedEvent;

/**
 * A cool component that can hold a whole bunch of other actors! Cool!
 * Should only be used with actors that cannot move, since when the component updates it makes the actors follow the parent-actor.
 * 
 * @author Merioksan Mikko
 */
public class ActorComponent extends BaseComponent {
    private class ActorSlot {
        // location in relation to the parent actor
        public float x, y;
        public GameActor actor;
        
        public ActorSlot(GameActor a, float X, float Y) {
            actor = a;
            x = X;
            y = Y;
        }
        
        public void update(long deltaMs) {
            //PhysicsComponent pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
            PhysicsComponent ownerPC = (PhysicsComponent)owner.getComponent("PhysicsComponent");
            
            float cos = (float)Math.cos(ownerPC.getAngleRad());
            float sin = (float)Math.sin(ownerPC.getAngleRad());
            
            // TODO: figure this out properly, somthing just ain't right. >:(
            float cx = y;
            float cy = -x;
            
            float newX = cx*cos - cy*sin;
            float newY = cx*sin + cy*cos;
            
            cx = newX + ownerPC.getX();
            cy = newY + ownerPC.getY();
            
            actor.move(cx, cy);
            
            actor.update(deltaMs);
        }
    }
    
    private ArrayList<ActorSlot> actors;
    
    public ActorComponent() {
        actors = new ArrayList<ActorSlot>();
    }
    
    @Override
    public void update(long deltaMs) {
        for(ActorSlot as : actors) {
            as.update(deltaMs);
        }
    }
    
    public ArrayList<GameActor> getActors() {
        ArrayList<GameActor> list = new ArrayList<GameActor>();
        for(ActorSlot as : actors) {
            list.add(as.actor);
        }
        return list;
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        Node node = attributes.item(0);
        while(node != null) {
            GameActor a = null;
            float X = 0;
            float Y = 0;
            if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase("actor")) {
                Node n = node.getFirstChild();
                while(n != null) {
                    if(n.getNodeType() == Node.ELEMENT_NODE) {
                        if(n.getNodeName().equalsIgnoreCase("Resource")) {
                            a = Application.get().getLogic().createActor(n.getFirstChild().getNodeValue());
                        }
                        if(n.getNodeName().equalsIgnoreCase("x")) {
                            X = Float.parseFloat(n.getFirstChild().getNodeValue());
                        }
                        if(n.getNodeName().equalsIgnoreCase("y")) {
                            Y = Float.parseFloat(n.getFirstChild().getNodeValue());
                        }
                    }
                    n = n.getNextSibling();
                }
                PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
                PhysicsComponent oPC = (PhysicsComponent)owner.getComponent("PhysicsComponent");
                pc.setZ(oPC.getZ()+1);
                actors.add(new ActorSlot(a, X, Y));
            }
            node = node.getNextSibling();
        }
    }
    
    @Override
    public void destroy() {
        for(ActorSlot as : actors) {
            Application.get().getEventManager().queueEvent(new ActorDestroyedEvent(as.actor));
        }
    }
}
