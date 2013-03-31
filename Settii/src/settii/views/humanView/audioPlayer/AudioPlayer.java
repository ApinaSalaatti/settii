package settii.views.humanView.audioPlayer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.Display;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.actorManager.components.WeaponsComponent;
import settii.eventManager.events.ChangeSectorEvent;
import settii.eventManager.events.OptionsChangedEvent;
import settii.eventManager.events.WeaponFiredEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class AudioPlayer {
    private float volume;
    private float musicVolume;
    private IntBuffer currentMusic;
    
    private ArrayList<IntBuffer> sources;
    
    /*
    IntBuffer buffer;
    IntBuffer source;
    
    FloatBuffer sourcePos;
    FloatBuffer sourceVel;
    * 
    */
    
    FloatBuffer listenerPos;
    FloatBuffer listenerVel;
    FloatBuffer listenerOri;
    
    public AudioPlayer() {
        volume = 1.0f;
        musicVolume = 1.0f;
        
        sources = new ArrayList<IntBuffer>();
        
        /*
        buffer = BufferUtils.createIntBuffer(1);
        source = BufferUtils.createIntBuffer(1);

        sourcePos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        sourcePos.flip();
        sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        sourceVel.flip();
        * 
        */
        listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        listenerPos.flip();
        listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        listenerVel.flip();
        listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f});
        listenerOri.flip();
        
        AL10.alListener(AL10.AL_POSITION, listenerPos);
        AL10.alListener(AL10.AL_VELOCITY, listenerVel);
        AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
        
        Application.get().getEventManager().register(ChangeSectorEvent.eventType, new ChangeSectorListener(this));
        Application.get().getEventManager().register(WeaponFiredEvent.eventType, new WeaponFiredListener(this));
        Application.get().getEventManager().register(OptionsChangedEvent.eventType, new OptionsChangedListener(this));
        //Application.get().getEventManager().register(ActorDestroyedEvent.eventType, new ActorDestroyedListener(this));
    }
    
    public void moveListener(float x, float y) {
        float relX = x / (Display.getWidth() * 3); // piece of shit way of normalizing the coordinates
        float relY = y / (Display.getHeight() * 3);
        
        listenerPos.put(relX).put(relY).put(0.0f);
        listenerPos.flip();
        
        AL10.alListener(AL10.AL_POSITION, listenerPos);
    }
    
    public IntBuffer addSource() {
        IntBuffer sourceBuf = BufferUtils.createIntBuffer(1);
        AL10.alGenSources(sourceBuf);
        sources.add(sourceBuf);
        return sourceBuf;
    }
    
    public IntBuffer playSound(String sound, float x, float y, float volume, boolean loop) {
        IntBuffer source = addSource();
        
        IntBuffer buf = Application.get().getResourceManager().getAudioManager().getSound(sound);
        float X = x / (Display.getWidth() * 3);
        float Y = y / (Display.getHeight() * 3);
        float Z = 0.0f;
        
        FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3).put(X).put(Y).put(Z);
        FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });

        sourcePos.flip();
        sourceVel.flip();
        
        AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buf.get(0));
        AL10.alSourcef(source.get(0), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(0), AL10.AL_GAIN, volume);
        AL10.alSource(source.get(0), AL10.AL_POSITION, sourcePos);
        AL10.alSource(source.get(0), AL10.AL_VELOCITY, sourceVel);
        
        if(loop) {
            AL10.alSourcei(source.get(0), AL10.AL_LOOPING, AL10.AL_TRUE);
        }
        else {
            AL10.alSourcei(source.get(0), AL10.AL_LOOPING, AL10.AL_FALSE);
        }
        
        AL10.alSourcePlay(source.get(0));
        
        return source;
    }
    
    public IntBuffer playMusic(String scene, boolean loop) {
        if(currentMusic != null) {
            AL10.alSourceStop(currentMusic.get(0));
        }
        IntBuffer music = playSound(getMusic(scene), 0, 0, musicVolume, loop);
        currentMusic = music;
        return music;
    }
    public IntBuffer playEffect(String effect, float x, float y,  boolean loop) {
        return playSound(getEffect(effect), x, y, volume, loop);
    }
    
    public void stop(IntBuffer source) {
        if(source != null)
            if(AL10.alIsSource(source.get(0)))
                AL10.alSourceStop(source);
    }
    
    public void update(long deltaMs) {
        Iterator<IntBuffer> it = sources.iterator();
        while(it.hasNext()) {
            IntBuffer source = it.next();
            if(AL10.alGetSourcei(source.get(0), AL10.AL_SOURCE_STATE) == AL10.AL_STOPPED) {
                AL10.alDeleteSources(source.get(0));
                it.remove();
            }
        }
    }
    
    public void weaponFiredListener(long a) {
        GameActor actor = Application.get().getLogic().getActor(a);
        WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
        PhysicsComponent pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
        playEffect(getWeaponSound(wc.getBullet()), pc.getX(), pc.getY(), false);
        //playSound(getWeaponSound(wc.getBullet()), pc.getX(), pc.getY(), false);
    }
    
    public void changeSectorListener(long id) {
        float x = Application.get().getLogic().getGame().getSector(id).getX();
        float y = Application.get().getLogic().getGame().getSector(id).getY();
        moveListener(x, y);
    }
    
    public void optionsChangedListener() {
        volume = Application.get().getOptions().soundVolume();
        musicVolume = Application.get().getOptions().musicVolume();
        for(IntBuffer source : sources) {
            AL10.alSourcef(source.get(0), AL10.AL_GAIN, volume);
        }
        
        if(currentMusic != null) {
            AL10.alSourcef(currentMusic.get(0), AL10.AL_GAIN, musicVolume);
        }
    }
    
    public void actorDestroyedListener(long id) {
        //AL10.alDeleteSources(sources.get(id));
        //sources.remove(id);
    }
    
    public String getWeaponSound(String weapon) {
        if(weapon.equals("assets/data/actors/bullet.xml")) {
            return "bullet";
        }
        
        return null;
    }
    
    public String getMusic(String scene) {
        if(scene.equalsIgnoreCase("main menu")) {
            return "assets/audio/testi.wav";
        }
        if(scene.equalsIgnoreCase("gameplay")) {
            return "assets/audio/background.wav";
        }
        
        return null;
    }
    
    public String getEffect(String effect) {
        if(effect.equals("bullet")) {
            return "assets/audio/pprooo.wav";
        }
        
        return null;
    }
}
