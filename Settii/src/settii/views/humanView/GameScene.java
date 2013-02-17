package settii.views.humanView;

import settii.views.humanView.listeners.*;
import settii.actorManager.components.*;
import settii.views.humanView.RenderObject;
import settii.views.humanView.renderer.Renderer;
import settii.actorManager.GameActor;
import settii.eventManager.*;
import settii.eventManager.events.*;
import java.awt.Canvas;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import settii.Application;
import java.util.HashMap;
import java.util.Collection;
import org.lwjgl.input.Mouse;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
/**
 *
 * @author Merioksan Mikko
 */
public class GameScene {
    private HashMap<Long, RenderObject> renderableActors;
    private boolean initialized;
    
    public GameScene() {
        initialized = false;
        renderableActors = new HashMap<Long, RenderObject>();
        
        // register for interesting events
        Application.get().getEventManager().register(ActorTextureChangeEvent.eventType, new ActorTextureChangeListener(this));
        Application.get().getEventManager().register(RenderableActorCreatedEvent.eventType, new RenderableActorCreatedListener(this));
        Application.get().getEventManager().register(ActorMovedEvent.eventType, new ActorMovedListener(this));
        Application.get().getEventManager().register(ActorTurnedEvent.eventType, new ActorTurnedListener(this));
        Application.get().getEventManager().register(ActorDestroyedEvent.eventType, new ActorDestroyedListener(this));
    }
    
    public boolean init() {
        initialized = true;
        
        return true;
    }
    
    public boolean initialized() {
        return initialized;
    }
    
    public void render() {
        Collection<RenderObject> objects = renderableActors.values();
        for(RenderObject ro : objects) {
            Renderer.get().draw(ro);
        }
    }
    
    public void RenderableActorCreatedListener(RenderableActorCreatedEvent race) {
        renderableActors.put(race.getRenderObject().getActor(), race.getRenderObject());
    }
    
    public void ActorMovedListener(ActorMovedEvent ame) {
        RenderObject ro = renderableActors.get(ame.getActor());
        PhysicsComponent pc = null;
        
        GameActor actor = Application.get().getLogic().getActor(ame.getActor());
        if(actor != null){
            pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
        }
        if(ro != null && pc != null) {
            ro.move(pc.getX(), pc.getY());
        }
    }
    
    public void ActorTextureChangeListener(ActorTextureChangeEvent atce) {
        RenderObject ro = renderableActors.get(atce.getActor());
        if(ro != null) {
            ro.setFile(atce.getTexture());
        }
    }
    
    public void ActorTurnedListener(long id) {
        RenderObject ro = renderableActors.get(id);
        GameActor a = Application.get().getLogic().getActor(id);
        if(a != null) {
            PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        
            if(ro != null && pc != null) {
                ro.setRotation(pc.getAngleRad());
            }
        }
    }
    
    public void actorDestroyedListener(long id) {
        renderableActors.remove(id);
    }
}
