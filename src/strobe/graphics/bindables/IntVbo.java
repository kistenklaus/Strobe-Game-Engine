package strobe.graphics.bindables;

import aspect.Aspect;
import aspect.AspectBindable;
import strobe.StrobeException;

import java.util.Arrays;

import static org.lwjgl.opengl.GL15.*;

//TODO generic VBO
public class IntVbo extends AspectBindable {

    private int ID;

    private final int allocated_size;
    private final int[] content;
    private final int usage;
    private boolean updated = false;
    private int scheduled_buffer_offset = 0;
    private boolean bound = false;

    public IntVbo(int[] content, int usage){
        this(content, content.length, usage);
    }
    public IntVbo(int allocated_size, int stride, int usage){
        this(new int[allocated_size], allocated_size, usage);
    }
    private IntVbo(int[] content, int allocated_size, int usage){
        this.content = content;
        this.allocated_size = allocated_size;
        this.usage = usage;
        if(this.allocated_size == 0) throw new StrobeException("VBO size can't be 0");
        if(usage!=GL_STATIC_DRAW&&usage!=GL_DYNAMIC_DRAW) throw new StrobeException("VBO usage is illegal");

    }

    @Override
    public final void create() {
        ID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, ID);
        glBufferData(GL_ARRAY_BUFFER, allocated_size * Integer.BYTES, usage);
        glBufferSubData(GL_ARRAY_BUFFER, 0, content);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        updated = true;
    }

    public final synchronized void buffer(int offset, int[] content){
        for(int i=offset;i<Math.max(content.length, this.content.length);i++)this.content[i] = content[i];
        for(int i=Math.max(content.length, this.content.length);i<this.content.length;i++)this.content[i] = 0;
        updated = true;
        this.scheduled_buffer_offset = Math.min(this.scheduled_buffer_offset, offset);
        if(Thread.currentThread().getName() == Aspect.getThreadName())dobuffer();
    }

    public final synchronized void buffer(int[] content){
        buffer(0, content);
    }

    @Override
    public final synchronized void bind() {
        dobuffer();
        bound = true;
        glBindBuffer(GL_ARRAY_BUFFER, ID);
    }

    public final void dobuffer(){
        if(!updated && !bound){
            glBufferSubData(GL_ARRAY_BUFFER, this.scheduled_buffer_offset * Integer.BYTES, content);
        }
    }

    @Override
    public final synchronized void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        bound = false;
    }

    @Override
    public final void dispose() {
        glDeleteBuffers(ID);
    }

    public final int getID(){
        return ID;
    }
}
