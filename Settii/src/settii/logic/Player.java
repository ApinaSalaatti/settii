package settii.logic;

import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.*;
/**
 *
 * @author Merioksan Mikko
 */
public class Player {
    private int exp;
    private InventoryComponent inventory;
    
    public Player() {
        inventory = new InventoryComponent();
        exp = 0;
        inventory.addMoney(1000); // starting money, for now atleast...
    }
    
    public InventoryComponent getInventory() {
        return inventory;
    }
    
    public int getExp() {
        return exp;
    }
    
    public void update(long deltaMs) {
        
    }
    
    public void actorDestroyedListener(GameActor actor) {
        StatusComponent sc = (StatusComponent)actor.getComponent("StatusComponent");
        if(sc != null && sc.getAllegiance().equals(StatusComponent.ALLEGIANCE_ENEMY)) {
            InventoryComponent ic = (InventoryComponent)actor.getComponent("InventoryComponent");
            if(ic != null) {
                inventory.addMoney(ic.getMoney());
            }
            
            exp += sc.getExpValue();
        }
    }
}
