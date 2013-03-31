package settii.views.humanView.renderer;

import settii.Application;
/**
 * @author Merioksan Mikko
 */
public class BitmapFont {
    private Texture tex;
    private float letterWidth;
    private float letterHeight;
    private float[] characters;
    
    private Color color;
    
    public BitmapFont(String resource) {
        tex = Application.get().getResourceManager().getTextureManager().getTexture(resource);
        letterWidth = 16.0f;
        letterHeight = 32.0f;
        
        characters = new float[1024];
        
        color = new Color(Color.BLACK); // default text is black
        
        // numbers
        characters[(int)'0'] = 0.0f;
        characters[(int)'1'] = 16.0f;
        characters[(int)'2'] = 2*16.0f;
        characters[(int)'3'] = 3*16.0f;
        characters[(int)'4'] = 4*16.0f;
        characters[(int)'5'] = 5*16.0f;
        characters[(int)'6'] = 6*16.0f;
        characters[(int)'7'] = 7*16.0f;
        characters[(int)'8'] = 8*16.0f;
        characters[(int)'9'] = 9*16.0f;
        
        // letters
        characters[(int)'a'] = 10*16.0f;
        characters[(int)'b'] = 11*16.0f;
        characters[(int)'c'] = 12*16.0f;
        characters[(int)'d'] = 13*16.0f;
        characters[(int)'e'] = 14*16.0f;
        characters[(int)'f'] = 15*16.0f;
        characters[(int)'g'] = 16*16.0f;
        characters[(int)'h'] = 17*16.0f;
        characters[(int)'i'] = 18*16.0f;
        characters[(int)'j'] = 19*16.0f;
        characters[(int)'k'] = 20*16.0f;
        characters[(int)'l'] = 21*16.0f;
        characters[(int)'m'] = 22*16.0f;
        characters[(int)'n'] = 23*16.0f;
        characters[(int)'o'] = 24*16.0f;
        characters[(int)'p'] = 25*16.0f;
        characters[(int)'q'] = 26*16.0f;
        characters[(int)'r'] = 27*16.0f;
        characters[(int)'s'] = 28*16.0f;
        characters[(int)'t'] = 29*16.0f;
        characters[(int)'u'] = 30*16.0f;
        characters[(int)'v'] = 31*16.0f;
        characters[(int)'w'] = 32*16.0f;
        characters[(int)'x'] = 33*16.0f;
        characters[(int)'y'] = 34*16.0f;
        characters[(int)'z'] = 35*16.0f;
        characters[(int)'å'] = 36*16.0f;
        characters[(int)'ä'] = 37*16.0f;
        characters[(int)'ö'] = 38*16.0f;
        
        characters[(int)'A'] = 10*16.0f;
        characters[(int)'B'] = 11*16.0f;
        characters[(int)'C'] = 12*16.0f;
        characters[(int)'D'] = 13*16.0f;
        characters[(int)'E'] = 14*16.0f;
        characters[(int)'F'] = 15*16.0f;
        characters[(int)'G'] = 16*16.0f;
        characters[(int)'H'] = 17*16.0f;
        characters[(int)'I'] = 18*16.0f;
        characters[(int)'J'] = 19*16.0f;
        characters[(int)'K'] = 20*16.0f;
        characters[(int)'L'] = 21*16.0f;
        characters[(int)'M'] = 22*16.0f;
        characters[(int)'N'] = 23*16.0f;
        characters[(int)'O'] = 24*16.0f;
        characters[(int)'P'] = 25*16.0f;
        characters[(int)'Q'] = 26*16.0f;
        characters[(int)'R'] = 27*16.0f;
        characters[(int)'S'] = 28*16.0f;
        characters[(int)'T'] = 29*16.0f;
        characters[(int)'U'] = 30*16.0f;
        characters[(int)'V'] = 31*16.0f;
        characters[(int)'W'] = 32*16.0f;
        characters[(int)'X'] = 33*16.0f;
        characters[(int)'Y'] = 34*16.0f;
        characters[(int)'Z'] = 35*16.0f;
        characters[(int)'Å'] = 36*16.0f;
        characters[(int)'Ä'] = 37*16.0f;
        characters[(int)'Ö'] = 38*16.0f;
        
        characters[(int)':'] = 39*16.0f;
        characters[(int)'-'] = 40*16.0f;
        characters[(int)' '] = 41*16.0f;
        characters[(int)'.'] = 42*16.0f;
        characters[(int)','] = 43*16.0f;
        characters[(int)'#'] = 44*16.0f;
        characters[(int)'!'] = 45*16.0f;
        characters[(int)'?'] = 46*16.0f;
        characters[(int)'\''] = 47*16.0f;
        
    }
    
    public float getLetterWidth() {
        return letterWidth;
    }
    public float getLetterHeight() {
        return letterHeight;
    }
    
    public Color getColor() {
        return color;
    }
    public void setColor(Color c) {
        color = c;
    }
    
    public void renderText(String text, float x, float y) {
        renderText(text, x, y, 1.0f);
    }
    
    /**
     * Renders the given String at the given position
     * @param text text to render
     * @param x x-coord to render to
     * @param y y-coord to render to
     * @param scale the scale with which to render 1.0 is the normal size of the font
     */
    public void renderText(String text, float x, float y, float scale) {
        float[] letter = new float[48];
        
        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int ascii = (int)c;
            float location = characters[ascii];
            
            float u = location / tex.getWidth();
            float v = tex.getV();
            float v2 = tex.getV2();
            float u2 = (location + letterWidth) / tex.getWidth();
            
            // vertices of current letter:
            // top left
            letter[0] = x + i * letterWidth * scale;
            letter[1] = y;
            letter[2] = color.r;
            letter[3] = color.g;
            letter[4] = color.b;
            letter[5] = color.a;
            letter[6] = u;
            letter[7] = v;
            
            // top right
            letter[8+0] = x + letterWidth * scale + i * letterWidth * scale;
            letter[8+1] = y;
            letter[8+2] = color.r;
            letter[8+3] = color.g;
            letter[8+4] = color.b;
            letter[8+5] = color.a;
            letter[8+6] = u2;
            letter[8+7] = v;

            // bottom left
            letter[16+0] = x + i * letterWidth * scale;
            letter[16+1] = y + letterHeight * scale;
            letter[16+2] = color.r;
            letter[16+3] = color.g;
            letter[16+4] = color.b;
            letter[16+5] = color.a;
            letter[16+6] = u;
            letter[16+7] = v2;

            // top right
            letter[24+0] = x + letterWidth * scale + i * letterWidth * scale;
            letter[24+1] = y;
            letter[24+2] = color.r;
            letter[24+3] = color.g;
            letter[24+4] = color.b;
            letter[24+5] = color.a;
            letter[24+6] = u2;
            letter[24+7] = v;

            // bottom right
            letter[32+0] = x + letterWidth * scale + i * letterWidth * scale;
            letter[32+1] = y + letterHeight * scale;
            letter[32+2] = color.r;
            letter[32+3] = color.g;
            letter[32+4] = color.b;
            letter[32+5] = color.a;
            letter[32+6] = u2;
            letter[32+7] = v2;

            // bottom left
            letter[40+0] = x + i * letterWidth * scale;
            letter[40+1] = y + letterHeight * scale;
            letter[40+2] = color.r;
            letter[40+3] = color.g;
            letter[40+4] = color.b;
            letter[40+5] = color.a;
            letter[40+6] = u;
            letter[40+7] = v2;
            
            Renderer.get().draw(tex, letter, 0); 
        }
        
    }
}
