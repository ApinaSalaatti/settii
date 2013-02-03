package settii.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
/**
 *
 * @author Merioksan Mikko
 */
public class Texture {
    public final int target = GL11.GL_TEXTURE_2D;
    public int id;
    private int width;
    private int height;

    public static final int LINEAR = GL11.GL_LINEAR;
    public static final int NEAREST = GL11.GL_NEAREST;

    public static final int CLAMP = GL11.GL_CLAMP;
    public static final int CLAMP_TO_EDGE = GL12.GL_CLAMP_TO_EDGE;
    public static final int REPEAT = GL11.GL_REPEAT;

    /** The colour model including alpha for the GL image */
    private ColorModel glAlphaColorModel;

    /** The colour model for the GL image */
    private ColorModel glColorModel;

    public Texture(String resource) throws Exception {
            this(resource, GL11.GL_NEAREST);
    }

    public Texture(String resource, int filter) throws Exception {
            this(resource, filter, GL12.GL_CLAMP_TO_EDGE);
    }

    public Texture(String resource, int filter, int wrap) throws Exception {
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

            //create a new byte buffer which will hold our pixel data
            ByteBuffer buf = convertImageData(loadImage(resource));

            //flip the buffer into "read mode" for OpenGL
            buf.flip();

            //enable textures and generate an ID
            GL11.glEnable(target);
            id = GL11.glGenTextures();

            //bind texture
            bind();

            //setup unpack mode
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

            //setup parameters
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, filter);
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, filter);
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, wrap);
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, wrap);

            //pass RGBA data to OpenGL
            GL11.glTexImage2D(target, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void bind() {
        GL11.glBindTexture(target, id);
    }

    /**
     * Convert the buffered image to a texture
     *
     * @param bufferedImage The image to convert to a texture
     * @param texture The texture to store the data into
     * @return A buffer containing the data
     */
    private ByteBuffer convertImageData(BufferedImage bufferedImage) { 
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
        
        width = texWidth;
        height = texHeight;
        
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
        g.setColor(new Color(0f,0f,0f,0f));
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
    private BufferedImage loadImage(String ref) throws IOException 
    { 
        BufferedImage bufferedImage = ImageIO.read(new File(ref));
 
        return bufferedImage;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public float getU() {
        return 0f;
    }
    public float getV() {
        return 0f;
    }
    public float getU2() {
        return 1f;
    }
    public float getV2() {
        return 1f;
    }
}
