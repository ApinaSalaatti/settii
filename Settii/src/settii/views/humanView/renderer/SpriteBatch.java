package settii.views.humanView.renderer;

import settii.views.humanView.renderer.ShaderProgram;
import settii.views.humanView.renderer.Color;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.LWJGLException;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import settii.utils.MathUtil;
import settii.Application;

/**
 *
 * @author Merioksan Mikko
 */
public class SpriteBatch {
    public static final String U_TEXTURE = "u_texture";
    public static final String U_PROJ_VIEW = "u_projView";

    public static final String ATTR_COLOR = "Color";
    public static final String ATTR_POSITION = "Position";
    public static final String ATTR_TEXCOORD = "TexCoord";

    public static final String DEFAULT_VERT_SHADER = 
          "uniform mat4 " + U_PROJ_VIEW + ";\n"
        + "attribute vec4 " + ATTR_COLOR + ";\n"
        + "attribute vec2 " + ATTR_TEXCOORD + ";\n"
        + "attribute vec2 " + ATTR_POSITION + ";\n"
        + "uniform vec2 offset;\n"
        + "varying vec4 vColor;\n"
        + "varying vec2 vTexCoord; \n"
        + "void main() {\n"
        + "vColor = " + ATTR_COLOR + ";\n"
        + "vTexCoord = " + ATTR_TEXCOORD + ";\n"
        + ATTR_POSITION + " = " + ATTR_POSITION + " + offset;\n"
        + "gl_Position = " + U_PROJ_VIEW + "* vec4(" + ATTR_POSITION + ".xy, 0.0, 1.0);\n"
        + "}";

    public static final String DEFAULT_FRAG_SHADER = 
          "uniform sampler2D " + U_TEXTURE + ";\n"
        + "varying vec4 vColor;\n"
        + "varying vec2 vTexCoord;\n"
        + "void main() {\n"
        + "vec4 texColor = texture2D(" + U_TEXTURE + ", vTexCoord);\n"
        + "gl_FragColor = vColor * texColor;\n"
        + "}";

    public static final List<VertexAttrib> ATTRIBUTES = Arrays.asList(
        new VertexAttrib(0, ATTR_POSITION, 2),
        new VertexAttrib(1, ATTR_COLOR, 4),
        new VertexAttrib(2, ATTR_TEXCOORD, 2)
    );

    static ShaderProgram defaultShader;
    public static int renderCalls = 0;

    protected FloatBuffer buf16;
    protected Matrix4f projMatrix = new Matrix4f();
    protected Matrix4f viewMatrix = new Matrix4f();
    protected Matrix4f transpositionPool = new Matrix4f();
    private Matrix4f projViewMatrix = new Matrix4f(); //only for re-using Matrix4f objects

    protected Texture texture;
    protected ShaderProgram program;

    protected VertexData data;

    private int idx;
    private int maxIndex;

    private Color color;
    private boolean drawing;
    
    static ShaderProgram getDefaultShader() throws LWJGLException {
        if(defaultShader == null) {
            return new ShaderProgram(DEFAULT_VERT_SHADER, DEFAULT_FRAG_SHADER, ATTRIBUTES);
        }
        else {
            return defaultShader;
        }
    }
    
    public SpriteBatch(ShaderProgram program, int size, boolean updateUniforms) {
        drawing = false;
        color = new Color();
        
        this.program = program;

        // later we can do some abstraction to replace this with VBOs...
        this.data = new VertexArray(size * 6, ATTRIBUTES);

        // max indices before we need to flush the renderer
        maxIndex = size * 6;

        // default size
        resize(Display.getWidth(), Display.getHeight());
    }
    
    public SpriteBatch(int size) throws LWJGLException {
        this(getDefaultShader(), size, true);
    }
    
    public SpriteBatch() throws LWJGLException {
        this(1000);
    }
    
    public SpriteBatch(ShaderProgram program) {
        this(program, 1000);
    }

    public SpriteBatch(ShaderProgram program, int size) {
        this(program, 1000, true);
    }
    
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
    
    public Matrix4f getProjectionMatrix() {
        return projMatrix;
    }
    
    public Matrix4f getCombinedMatrix() {
        Matrix4f.mul(Matrix4f.transpose(projMatrix, transpositionPool), viewMatrix, projViewMatrix);
        
        return projViewMatrix;
    }
    
    /** A convenience method to resize the projection matrix to the given
    * dimensions, using y-down ortho 2D. This will invoke a call to
    * updateMatrices.
    *
    * @param width
    * @param height */
    public void resize(int width, int height) {
        projMatrix = MathUtil.toOrtho2D(projMatrix, 0, 0, width, height);
        updateUniforms();
    }

    /** Sets this SpriteBatch's color to the RGBA values of the given color
    * object.
    *
    * @param color the RGBA values to use */
    public void setColor(Color color) {
        setColor(color.r, color.g, color.b, color.a);
    }
    
    public void setColor(float r, float g, float b, float a) {
        color.r = r;
        color.g = g;
        color.b = b;
        color.a = a;
    }
    
    public void updateUniforms() {
        updateUniforms(program);
    }
    
    /** Call to multiply the the projection with the view matrix and save the
    * result in the uniform mat4 {@value #U_PROJ_VIEW}, as well as update the
    * U_TEXTURE uniform. */
    public void updateUniforms(ShaderProgram program) {
        projViewMatrix = getCombinedMatrix();
        
        // bind program before sending uniforms
        program.use();
        
        boolean oldStrict = ShaderProgram.isStrictMode();
        
        // disable strict mode
        program.setStrictMode(false);
        
        // we can now utilize ShaderProgram's hash map which may be better than
        // glGetUniformLocation
        
        // Store the the multiplied matrix in the "projViewMatrix"-uniform:
        program.setUniformMatrix(U_PROJ_VIEW, false, projViewMatrix);

        // upload texcoord 0
        program.setUniformi(U_TEXTURE, 0);

        //reset strict mode
        ShaderProgram.setStrictMode(oldStrict);
    }
    
    /** An advanced call that allows you to change the shader without uploading
    * shader uniforms. This will flush the batch if we are within begin().
    *
    * @param program
    * @param updateUniforms whether to call updateUniforms after changing the
    * programs */
    public void setShader(ShaderProgram program, boolean updateUniforms) {
        if (program==null) {
            throw new NullPointerException("shader cannot be null; use getDefaultShader instead");
        }
        
        if (drawing) {
            flush(); //if we are already drawing, flush the batch before switching shaders
        }
        
        this.program = program; //now switch the shader
        
        if (updateUniforms) {
            updateUniforms(); // send uniform data to shader
        }
        else if (drawing) {
            program.use(); //if we don't want to update, then just start the program if we are drawing
        }
    }
    
    /** Changes the shader and updates it with the current texture and projView
    * uniforms. This will flush the batch if we are within begin().
    *
    * @param program the new program to use */
    public void setShader(ShaderProgram program) {
        setShader(program, true);
    }
    
    public ShaderProgram getShader() {
        return program;
    }
    
    public void begin() {
        if(drawing) {
            throw new IllegalStateException("You are already drawing!");
        }
        
        drawing = true;
        
        program.use();
        
        idx = 0;
        renderCalls = 0;
        texture = null;
    }
    
    public void end() {
        if(!drawing) {
            throw new IllegalStateException("You aren't drawing!");
        }
        
        drawing = false;
        flush();
    }
    
    public void flush() {
        if(idx > 0) {
            data.flip();
            render();
            idx = 0;
            data.clear();
        }
    }
    
    public void drawRegion(Texture tex, float srcX, float srcY, float srcWidth, float srcHeight, float dstX, float dstY) {
        drawRegion(tex, srcX, srcY, srcWidth, srcHeight, dstX, dstY, srcWidth, srcHeight);
    }
    
    public void drawRegion(Texture tex, float srcX, float srcY, float srcWidth, float srcHeight, float dstX, float dstY, float dstWidth, float dstHeight) {
        float u = srcX / tex.getWidth();
        float v = srcY / tex.getHeight();
        float u2 = (srcX + srcWidth) / tex.getWidth();
        float v2 = (srcY + srcHeight) / tex.getHeight();
        
        draw(tex, dstX, dstY, dstWidth, dstHeight, u, v, u2, v2);
    }
    
    public void draw(Texture tex, float x, float y) {
        draw(tex, x, y, tex.getWidth(), tex.getHeight());
    }

    public void draw(Texture tex, float x, float y, float width, float height) {
        draw(tex, x, y, width, height, tex.getU(), tex.getV(), tex.getU2(), tex.getV2());
    }


    public void draw(Texture tex, float x, float y, float originX, float originY, float rotationRadians) {
        draw(tex, x, y, tex.getWidth(), tex.getHeight(), originX, originY, rotationRadians);
    }

    public void draw(Texture tex, float x, float y, float width, float height, float originX, float originY, float rotationRadians) {
        draw(tex, x, y, width, height, originX, originY, rotationRadians, tex.getU(), tex.getV(), tex.getU2(), tex.getV2());
    }
    
    public void draw(Texture tex, float x, float y, float width, float height, float originX, float originY, float rotationRadians, float u, float v, float u2, float v2) {
        checkFlush(tex);
        
        final float r = color.r;
        final float g = color.g;
        final float b = color.b;
        final float a = color.a;
        
        float x1, x2, x3, x4, y1, y2, y3, y4;
        
        if(rotationRadians != 0) {
            float scaleX = 1f;
            float scaleY = 1f;
            
            float cx = originX * scaleX;
            float cy = originY * scaleY;
            
            float p1x = -cx;
            float p1y = -cy;
            float p2x = width - cx;
            float p2y = -cy;
            float p3x = width - cx;
            float p3y = height - cy;
            float p4x = -cx;
            float p4y = height - cy;
            
            final float cos = (float) Math.cos(rotationRadians);
            final float sin = (float) Math.sin(rotationRadians);
            
            x1 = x + (cos * p1x - sin * p1y) + cx; // TOP LEFT
            y1 = y + (sin * p1x + cos * p1y) + cy;
            x2 = x + (cos * p2x - sin * p2y) + cx; // TOP RIGHT
            y2 = y + (sin * p2x + cos * p2y) + cy;
            x3 = x + (cos * p3x - sin * p3y) + cx; // BOTTOM RIGHT
            y3 = y + (sin * p3x + cos * p3y) + cy;
            x4 = x + (cos * p4x - sin * p4y) + cx; // BOTTOM LEFT
            y4 = y + (sin * p4x + cos * p4y) + cy;
        }
        else {
            x1 = x;
            y1 = y;

            x2 = x+width;
            y2 = y;

            x3 = x+width;
            y3 = y+height;

            x4 = x;
            y4 = y+height;
        }

        // top left, top right, bottom left
        vertex(x1, y1, r, g, b, a, u, v);
        vertex(x2, y2, r, g, b, a, u2, v);
        vertex(x4, y4, r, g, b, a, u, v2);

        // top right, bottom right, bottom left
        vertex(x2, y2, r, g, b, a, u2, v);
        vertex(x3, y3, r, g, b, a, u2, v2);
        vertex(x4, y4, r, g, b, a, u, v2);
    }
    
    public void draw(Texture tex, float x, float y, float width, float height, float u, float v, float u2, float v2) {
        draw(tex, x, y, width, height, x, y, 0f, u, v, u2, v2);
    }
    
    /** Renders a texture using custom vertex attributes; e.g. for different
    * vertex colours. This will ignore the current batch color and "x/y translation",
    * as well as the U/V coordinates of the given ITexture.
    *
    * @param tex the texture to use
    * @param vertices an array of 6 vertices, each holding 8 attributes (total
    * = 48 elements)
    * @param offset the offset from the vertices array to start from */
    public void draw(Texture tex, float[] vertices, int offset) {
        checkFlush(tex);
        data.put(vertices, offset, data.getTotalNumComponents() * 6);
        idx += 6;
    }
    
    private VertexData vertex(float x, float y, float r, float g, float b, float a, float u, float v) {
        data.put(x).put(y).put(r).put(g).put(b).put(a).put(u).put(v);
        idx++;
        return data;
    }
    
    protected void checkFlush(Texture sprite) {
        if (sprite == null)
            throw new NullPointerException("null texture");

        // we need to bind a different texture/type. this is
        // for convenience; ideally the user should order
        // their rendering wisely to minimize texture binds
        if (sprite != this.texture || idx >= maxIndex) {
            // apply the last texture
            flush();
            this.texture = sprite;
        }
    }

    private void render() {
        if (texture != null) {
            texture.bind();
        }
        data.bind();
        data.draw(GL11.GL_TRIANGLES, 0, idx);
        data.unbind();
        renderCalls++;
    }
}
