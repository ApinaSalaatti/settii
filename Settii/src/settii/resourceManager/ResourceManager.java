package settii.resourceManager;

/**
 *
 * @author Merioksan Mikko
 */
public class ResourceManager {
    private TextureManager texManager;
    private AudioManager audioManager;
    private DataManager dataManager;
    
    public ResourceManager() {
        texManager = new TextureManager();
        audioManager = new AudioManager();
        dataManager = new DataManager();
    }
    
    public TextureManager getTextureManager() {
        return texManager;
    }
    
    public AudioManager getAudioManager() {
        return audioManager;
    }
    
    public DataManager getDataManager() {
        return dataManager;
    }
}
