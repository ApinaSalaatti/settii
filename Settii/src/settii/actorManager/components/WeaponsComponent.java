package settii.actorManager.components;

import org.w3c.dom.NodeList;
import settii.actorManager.BaseComponent;
import settii.actorManager.components.PhysicsComponent;
import settii.Application;
import settii.actorManager.GameActor;
/**
 *
 * @author Merioksan Mikko
 */
public class WeaponsComponent extends BaseComponent {
    private int damage;
    private String bullet;
    
    public WeaponsComponent() {
        damage = 5;
        bullet = "assets/data/actors/bullet.xml";
    }
    
    @Override
    public String getName() {
        return "WeaponsComponent";
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        
    }
    
    public void fire() {
        long id = super.getOwner();
        PhysicsComponent pc = (PhysicsComponent)Application.get().getLogic().getActor(id).getComponent("PhysicsComponent");
        
        long bul = Application.get().getLogic().createActor(bullet);
        GameActor act = Application.get().getLogic().getActor(bul);
        act.move(pc.getX(), pc.getY() - 40);
    }
}
