package settii.resourceManager;

import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Hashtable;
import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import settii.views.humanView.renderer.Texture;
/**
 *
 * @author Merioksan Mikko
 */
public class TextureManager {
    public static final int LINEAR = GL11.GL_LINEAR;
    public static final int NEAREST = GL11.GL_NEAREST;

    public static final int CLAMP = GL11.GL_CLAMP;
    public static final int CLAMP_TO_EDGE = GL12.GL_CLAMP_TO_EDGE;
    public static final int REPEAT = GL11.GL_REPEAT;
    
    public final int target = GL11.GL_TEXTURE_2D;
    
    /** The colour model including alpha for the GL image */
    private ColorModel glAlphaColorModel;

    /** The colour model for the GL image */
    private ColorModel glColorModel;
    
    /** Our textures safely stored in a hashmap */
    private HashMap<String, Texture> textures;
    
    public TextureManager() {
        textures = new HashMap<String, Texture>();
    }
    
    public Texture getTexture(String tex) {
        if(!textures.containsKey(tex)) {
            try {
                loadTexture(tex);
            } catch(Exception e) {
                System.out.println("Couldn't create texture!");
            }
        }
        
        return textures.get(tex);
    }

    public void loadTexture(String resource) throws Exception {
            loadTexture(resource, GL11.GL_NEAREST);
    }

    public void loadTexture(String resource, int filter) throws Exception {
            loadTexture(resource, filter, GL12.GL_CLAMP_TO_EDGE);
    }

    public void loadTexture(String resource, int filter, int wrap) throws Exception {
        glAlphaColorModel = new ComponentColorModel(
            ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[] {8,8,8,8},
            true,
            false,
            ComponentColorModel.TRANSLUCENT,
            DataBuffer.TYPE_BYTE
        );

        glColorModel = new ComponentColorModel(
            ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[] {8,8,8,0},
            false,
            false,
            ComponentColorModel.OPAQUE,
            DataBuffer.TYPE_BYTE
        );

        try {
            //we are using RGBA, i.e. 4 components or "bytes per pixel"
            final int bpp = 4;
            
            Texture tex = new Texture();
            
            //create a new byte buffer which will hold our pixel data
            ByteBuffer buf = convertImageData(loadImage(resource), tex);

            //flip the buffer into "read mode" for OpenGL
            buf.flip();

            //enable textures and generate an ID
            GL11.glEnable(target);
            int id = GL11.glGenTextures();

            tex.create(target, id);
            
            //bind texture
            GL11.glBindTexture(target, id);

            //setup unpack mode
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

            //setup parameters
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, filter);
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, filter);
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, wrap);
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, wrap);

            //pass RGBA data to OpenGL
            GL11.glTexImage2D(target, 0, GL11.GL_RGBA, tex.getWidth(), tex.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
            
            textures.put(resource, tex);
        } catch(Exception e) {
            System.out.println("CANNOT READ FILE " + resource);
            e.printStackTrace();
        }
    }
    
    /**
     * Convert the buffered image to a texture
     *
     * @param bufferedImage The image to convert to a texture
     * @param texture The texture to store the data into
     * @return A buffer containing the data
     */
    private ByteBuffer convertImageData(BufferedImage bufferedImage, Texture tex) { 
        ByteBuffer imageBuffer = null; 
        WritableRaster raster;
        BufferedImage texImage;
        
        int texWidth = 2;
        int texHeight = 2;
        
        // find the closest power of 2 for the width and height

        // of the produced texture

        while (texWidth < bufferedImage.getWidth()) {
            texWidth *= 2;
        }
        while (texHeight < bufferedImage.getHeight()) {
            texHeight *= 2;
        }
        
        tex.setWidth(texWidth);
        tex.setHeight(texHeight);
        
        // create a raster that can be used by OpenGL as a source

        // for a texture

        if (bufferedImage.getColorModel().hasAlpha()) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,4,null);
            texImage = new BufferedImage(glAlphaColorModel,raster,false,new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,3,null);
            texImage = new BufferedImage(glColorModel,raster,false,new Hashtable());
        }
            
        // copy the source image into the produced image

        Graphics g = texImage.getGraphics();
        g.setColor(new java.awt.Color(0f,0f,0f,0f));
        g.fillRect(0,0,texWidth,texHeight);
        g.drawImage(bufferedImage,0,0,null);
        
        // build a byte buffer from the temporary image 

        // that be used by OpenGL to produce a texture.

        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData(); 

        imageBuffer = ByteBuffer.allocateDirect(data.length); 
        imageBuffer.order(ByteOrder.nativeOrder()); 
        imageBuffer.put(data, 0, data.length);
        
        return imageBuffer; 
    }
    
    /** 
     * Load a given resource as a buffered image
     * 
     * @param ref The location of the resource to load
     * @return The loaded buffered image
     * @throws IOException Indicates a failure to find a resource
     */
    private BufferedImage loadImage(String ref) throws IOException { 
        BufferedImage bufferedImage = ImageIO.read(new File(ref));
 
        return bufferedImage;
    }
    
    public void preload(String root) {
        File file = new File(root);
        File[] files = file.listFiles();
        for(File f : files) {
            if(f.isDirectory()) {
                preload(f.getPath());
            }
            else if(f.isFile()) {
                getTexture(f.getPath());
            }
        }
    }
}
