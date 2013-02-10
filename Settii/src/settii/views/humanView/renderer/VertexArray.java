package settii.views.humanView.renderer;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL11;
/**
 *
 * @author Merioksan Mikko
 */
public class VertexArray implements VertexData {
    protected VertexAttrib[] attributes;
    
    private int totalNumComponents;
    private int stride;
    private FloatBuffer buffer;
    private int vertCount;
    
    /**
    *
    * @param vertCount the number of VERTICES; e.g. 3 verts to make a triangle, regardless of number of attributes
    * @param attributes a list of attributes per vertex
    */
    public VertexArray(int vertCount, VertexAttrib ... attributes) {
        this.attributes = attributes;
        
        for(VertexAttrib a : attributes) {
            totalNumComponents += a.numComponents;
        }
        
        this.vertCount = vertCount;
        
        this.buffer = BufferUtils.createFloatBuffer(vertCount * totalNumComponents);
    }
    
    public VertexArray(int vertCount, List<VertexAttrib> attributes) {
        this(vertCount, attributes.toArray(new VertexAttrib[attributes.size()]));
    }
    
    @Override
    public VertexArray flip() {
        buffer.flip();
        return this;
    }
    
    @Override
    public VertexArray clear() {
        buffer.clear();
        return this;
    }
    
    @Override
    public VertexArray put(float[] verts, int offset, int length) {
        buffer.put(verts, offset, length);
        return this;
    }
    
    @Override
    public VertexArray put(float f) {
        buffer.put(f);
        return this;
    }
    
    @Override
    public FloatBuffer buffer() {
        return buffer;
    }
    
    @Override
    public int getTotalNumComponents() {
        return totalNumComponents;
    }
    
    @Override
    public int getVertexCount() {
        return vertCount;
    }
    
    @Override
    public void bind() {
        int offset = 0;
        
        // 4 bytes per float
        int stride = totalNumComponents * 4;
        
        for(int i = 0; i < attributes.length; i++) {
            VertexAttrib a = attributes[i];
            buffer.position(offset);
            GL20.glEnableVertexAttribArray(a.location);
            GL20.glVertexAttribPointer(a.location, a.numComponents, false, stride, buffer);
            offset += a.numComponents;
        }
    }
    
    @Override
    public void draw(int geom, int first, int count) {
        GL11.glDrawArrays(geom, first, count);
    }
    
    @Override
    public void unbind() {
        for(int i = 0; i < attributes.length; i++) {
            VertexAttrib a = attributes[i];
            GL20.glDisableVertexAttribArray(a.location);
        }
    }
}
