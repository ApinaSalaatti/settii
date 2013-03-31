package settii.views.humanView;

import java.util.ArrayList;
import java.util.Collections;
import settii.views.humanView.listeners.*;
import settii.views.humanView.renderer.Renderer;
import settii.actorManager.GameActor;
import settii.eventManager.events.*;
import settii.Application;
import java.util.Iterator;
import java.util.PriorityQueue;
import org.lwjgl.opengl.Display;
import settii.utils.MathUtil;
import settii.views.humanView.animation.Animation;
/**
 *
 * @author Merioksan Mikko
 */
public class GameScene {
    private ArrayList<RenderObject> renderableActors;
    private boolean initialized;
    
    //private Texture background;
    private RenderObject background;
    
    public GameScene() {
        initialized = false;
        renderableActors = new ArrayList<RenderObject>();
        
        // register for interesting events
        //Application.get().getEventManager().register(ActorTextureChangeEvent.eventType, new ActorTextureChangeListener(this));
        Application.get().getEventManager().register(RenderableActorCreatedEvent.eventType, new RenderableActorCreatedListener(this));
        //Application.get().getEventManager().register(ActorMovedEvent.eventType, new ActorMovedListener(this));
        //Application.get().getEventManager().register(ActorTurnedEvent.eventType, new ActorTurnedListener(this));
        Application.get().getEventManager().register(ActorDestroyedEvent.eventType, new ActorDestroyedListener(this));
        
        // TODO figure this out properly you moron...
        background = new RenderObject(-1, 0,0, Display.getWidth() * 5, Display.getHeight() * 5, MathUtil.ANGLE_STRAIGHT_UP, "assets/graphics/background.png");
    }
    
    // TODO: why is this so useless? :--(
    public boolean init() {
        initialized = true;
        
        return true;
    }
    
    public boolean initialized() {
        return initialized;
    }
    
    public void update(long deltaMs) {
        Iterator<RenderObject> it = renderableActors.iterator();
        while(it.hasNext()) {
            RenderObject ro = it.next();
            ro.update(deltaMs);
        }
        
        Collections.sort(renderableActors);
    }
    
    public void render() {
        Renderer.get().draw(background);
        
        Iterator<RenderObject> it = renderableActors.iterator();
        while(it.hasNext()) {
            RenderObject ro = it.next();
            Renderer.get().draw(ro);
        }
    }
    
    public void RenderableActorCreatedListener(RenderableActorCreatedEvent race) {
        renderableActors.add(race.getRenderObject());
    }
    
    public void ActorMovedListener(ActorMovedEvent ame) {
        /*
        RenderObject ro = renderableActors.get(ame.getActor());
        PhysicsComponent pc = null;
        
        GameActor actor = Application.get().getLogic().getActor(ame.getActor());
        if(actor != null){
            pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
        }
        if(ro != null && pc != null) {
            ro.move(pc.getX(), pc.getY());
            ro.setZ(pc.getZ());
        }
        * 
        */
    }
    
    public void ActorTextureChangeListener(ActorTextureChangeEvent atce) {
        /*
        RenderObject ro = renderableActors.get(atce.getActor());
        if(ro != null) {
            ro.setFile(atce.getTexture());
        }
        * 
        */
    }
    
    public void ActorTurnedListener(long id) {
        /*
        RenderObject ro = renderableActors.get(id);
        GameActor a = Application.get().getLogic().getActor(id);
        if(a != null) {
            PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        
            if(ro != null && pc != null) {
                ro.setRotation(pc.getAngleRad());
            }
        }
        * 
        */
    }
    
    public void actorDestroyedListener(GameActor actor) {
        //renderableActors.remove(actor.getID());
        Iterator<RenderObject> it = renderableActors.iterator();
        while(it.hasNext()) {
            RenderObject ro = it.next();
            if(ro.getActor() == actor.getID()) {
                Application.get().getHumanView().playAnimation(ro.getTexture(), ro.getAnimation().getAnimationData(Animation.ANIMATION_DEATH), 400, ro.getX(), ro.getY());
                it.remove();
            }
        }
    }
}
