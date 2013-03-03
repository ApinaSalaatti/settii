package settii.views.ui.researchScreen;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import settii.Application;
import settii.views.ui.*;
import settii.logic.research.ResearchItem;
import settii.views.humanView.renderer.Renderer;
/**
 *
 * @author Merioksan Mikko
 */
public class ResearchScreen extends BaseGameScreen {
    private ArrayList<ResearchItemButton> itemSlots;
    
    private boolean updateResearch, researchDone;
    
    public ResearchScreen() {
        super();
        itemSlots = new ArrayList<ResearchItemButton>();
        updateResearch = researchDone = false;
    }
    
    public void addItemSlot(float x, float y, ResearchItem i) {
        ResearchItemButton rib = new ResearchItemButton(x, y, i);
        rib.setTooltip(i.getName() + '\n' + i.getDescription());
        itemSlots.add(rib);
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(super.onMouseDown(mX, mY, button)) {
            return true;
        }
        for(ResearchItemButton rib : itemSlots) {
            if(rib.onMouseDown(mX, mY, button)) {
                researchDone = true;
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean onKeyDown(int key) {
        if(key == Keyboard.KEY_ESCAPE) {
            Application.get().getHumanView().popScreen();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY) {
        if(super.onPointerMove(mX, mY, mDX, mDY)) {
            return true;
        }
        for(ResearchItemButton rib : itemSlots) {
            if(rib.onPointerMove(mX, mY, mDX, mDY)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void update(long deltaMs) {
        super.update(deltaMs);
        
        // cool hack! if we have bought an update, we first flag that as done and at the next update, when the research event has been triggered, we update the tree!
        if(updateResearch) {
            itemSlots.clear();
            int indx = 0;
            for(ResearchItem ri : Application.get().getLogic().getGame().getResearch().getRoots()) {
                ResearchScreenFactory.createTree(this, ri, 100, 100+indx*90);
                indx++;
            }
            updateResearch = false;
        }
        if(researchDone) {
            updateResearch = true;
            researchDone = false;
        }
        
        for(ResearchItemButton rib : itemSlots) {
            rib.update(deltaMs);
        }
    }
    
    @Override
    public void render() {
        super.render();
        
        for(ResearchItemButton rib : itemSlots) {
            rib.render();
        }
        
        Renderer.get().drawText("Hover over a research item for more info!", 80, 30);
    }
}
