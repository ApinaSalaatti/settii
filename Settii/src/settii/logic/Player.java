package settii.logic;

import org.w3c.dom.NodeList;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.*;
import settii.eventManager.events.EnemyDestroyedEvent;
import settii.logic.listeners.EnemyDestroyedListener;
/**
 *
 * @author Merioksan Mikko
 */
public class Player {
    private int exp;
    private InventoryComponent inventory;
    
    public Player() {
        Application.get().getEventManager().register(EnemyDestroyedEvent.eventType, new EnemyDestroyedListener(this));
    }
    
    public InventoryComponent getInventory() {
        return inventory;
    }
    
    public int getExp() {
        return exp;
    }
    
    public void update(long deltaMs) {
        
    }
    
    public void enemyDestroyedListener(GameActor actor) {
        StatusComponent sc = (StatusComponent)actor.getComponent("StatusComponent");
        InventoryComponent ic = (InventoryComponent)actor.getComponent("InventoryComponent");
        if(ic != null) {
            inventory.addMoney(ic.getMoney());
        }
        
        if(sc != null) {
            exp += sc.getExpValue();
        }
    }
    
    public void createFromXML(NodeList attributes) {
        inventory = new InventoryComponent();
        exp = 0;
        inventory.addMoney(100000); // starting money, for now atleast...
    }
}
