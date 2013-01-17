package settii.resourceManager;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
/**
 *
 * @author Merioksan Mikko
 */
public class ResourceManager {
    private static ResourceManager instance = new ResourceManager();
    
    private HashMap<String, BufferedImage> graphics;
    
    public ResourceManager() {
        graphics = new HashMap<String, BufferedImage>();
    }
    
    public ResourceManager get() {
        return instance;
    }
    
    public BufferedImage getImage(String resource) {
        if(!graphics.containsKey(resource)) {
            loadImage(resource, true);
        }
        
        return graphics.get(resource);
    }
    
    public void loadImage(String resource, boolean transparentBg) {
        if(!graphics.containsKey(resource)) {
            BufferedImage bufferedImage = null;
            
            try {
                bufferedImage = ImageIO.read(new File(resource));

                if(transparentBg) {
                    bufferedImage = createTransparency(bufferedImage);
                }
            }
            catch(Exception e) {
                try {
                    bufferedImage = ImageIO.read(new File("assets/graphics/error.png"));
                }
                catch(Exception ex) {
                    System.out.println("Couldn't even find the error image! :(");
                    System.exit(-1);
                }
            }
            
            graphics.put(resource, bufferedImage);
        }
    }
    
    private BufferedImage createTransparency(BufferedImage img) {
        final int transColor = img.getRGB(0, 0);

        ImageFilter filter = new RGBImageFilter() {
            public int markerRGB = transColor;

            @Override
            public int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {  
                    // Mark the alpha bits as zero - transparent  
                    return 0x00FFFFFF & rgb;  
                }  
                else {  
                    // nothing to do  
                    return rgb;  
                }
            }
        };

        final ImageProducer ip = new FilteredImageSource(img.getSource(), filter);  
        Image transImg = Toolkit.getDefaultToolkit().createImage(ip);
        final BufferedImage bufferedImage = new BufferedImage(transImg.getWidth(null), transImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);  
        final Graphics2D g2 = bufferedImage.createGraphics();  
        g2.drawImage(transImg, 0, 0, null);  
        g2.dispose(); 

        return bufferedImage;
    }
}
