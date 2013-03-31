package settii.resourceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

/**
 *
 * @author Merioksan Mikko
 */
public class AudioManager {
    private HashMap<String, IntBuffer> buffers;
    
    IntBuffer buffer;
    IntBuffer source;
    
    FloatBuffer sourcePos;
    FloatBuffer sourceVel;
    FloatBuffer listenerPos;
    FloatBuffer listenerVel;
    FloatBuffer listenerOri;
    
    public AudioManager() {
        buffers = new HashMap<String, IntBuffer>();
        
        buffer = BufferUtils.createIntBuffer(1);
        source = BufferUtils.createIntBuffer(1);

        sourcePos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        sourcePos.flip();
        sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        sourceVel.flip();
        listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        listenerPos.flip();
        listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        listenerVel.flip();
        listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f});
        listenerOri.flip();
        try {
            AL.create();
        } catch(LWJGLException le) {
            return;
        }
        
        AL10.alGetError();
        
        if(loadALData() == AL10.AL_FALSE) {
            System.out.println("Error loading audio data!");
            return;
        }
        
        setListenerValues();
    }
    
    public int loadALData() {
        AL10.alGenBuffers(buffer);
        if(AL10.alGetError() != AL10.AL_NO_ERROR) {
            return AL10.AL_FALSE;
        }
        /*
        WaveData waveFile = WaveData.create("testi.wav");
        AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        * 
        */
        AL10.alGenSources(source);
        
        if(AL10.alGetError() != AL10.AL_NO_ERROR) {
            return AL10.AL_FALSE;
        }
        
        AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
        AL10.alSourcei(source.get(0), AL10.AL_LOOPING, AL10.AL_TRUE);
        AL10.alSourcef(source.get(0), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(0), AL10.AL_GAIN, 1.0f);
        AL10.alSource(source.get(0), AL10.AL_POSITION, sourcePos);
        AL10.alSource(source.get(0), AL10.AL_VELOCITY, sourceVel);
        
        if(AL10.alGetError() == AL10.AL_NO_ERROR) {
            return AL10.AL_TRUE;
        }
        
        return AL10.AL_FALSE;
    }
    
    public void setListenerValues() {
        AL10.alListener(AL10.AL_POSITION, listenerPos);
        AL10.alListener(AL10.AL_VELOCITY, listenerVel);
        AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
    }
    
    public void killALData() {
        AL10.alDeleteSources(source);
        AL10.alDeleteBuffers(buffer);
    }
    
    public void play() {
        AL10.alSourcePlay(source.get(0));
    }
    
    private void loadSound(String res) {
        IntBuffer b = BufferUtils.createIntBuffer(1);
        AL10.alGenBuffers(b);
        
        WaveData waveFile = WaveData.create(res);
        AL10.alBufferData(b.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        buffers.put(res, b);
    }
    
    public IntBuffer getSound(String res) {
        if(!buffers.containsKey(res)) {
            loadSound(res);
        }
        
        return buffers.get(res);
    }
    
    public void preload(String root) {
        File file = new File(root);
        File[] files = file.listFiles();
        for(File f : files) {
            if(f.isDirectory()) {
                preload(f.getPath());
            }
            else if(f.isFile()) {
                getSound(f.getPath());
            }
        }
    }
}
