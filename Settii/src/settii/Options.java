package settii;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import settii.eventManager.events.OptionsChangedEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class Options {
    private float soundVolume;
    private float musicVolume;
    
    public Options() {
        soundVolume = 1.0f;
        musicVolume = 1.0f;
    }
    
    public void createFromXML(NodeList options) {
        Node n = options.item(0);
        while(n != null) {
            if(n.getNodeType() == Node.ELEMENT_NODE) {
                if(n.getNodeName().equalsIgnoreCase("soundVolume")) {
                    soundVolume = Float.parseFloat(n.getFirstChild().getNodeValue());
                }
                else if(n.getNodeName().equalsIgnoreCase("musicVolume")) {
                    musicVolume = Float.parseFloat(n.getFirstChild().getNodeValue());
                }
            }
            n = n.getNextSibling();
        }
    }
    
    public boolean soundOn() {
        return soundVolume >0;
    }
    public float soundVolume() {
        return soundVolume;
    }
    public void setVolume(float vol) {
        soundVolume = vol;
    }
    public void enableSound() {
        soundVolume = 1.0f;
    }
    public void disableSound() {
        soundVolume = 0.0f;
    }
    
    public float musicVolume() {
        return musicVolume;
    }
    public void setMusicVolume(float vol) {
        musicVolume = vol;
    }
    
    public void save() {
        Application.get().getEventManager().queueEvent(new OptionsChangedEvent());
    }
}
