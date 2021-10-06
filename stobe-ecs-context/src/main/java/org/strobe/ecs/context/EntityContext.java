package org.strobe.ecs.context;

import org.strobe.ecs.EntityComponentSystem;
import org.strobe.ecs.context.renderer.EntityRenderer;
import org.strobe.engine.StrobeContext;
import org.strobe.gfx.Graphics;
import org.strobe.gfx.opengl.OpenGlContext;
import org.strobe.gfx.opengl.bindables.framebuffer.Framebuffer;
import org.strobe.window.WindowKeyboard;
import org.strobe.window.WindowMouse;
import org.strobe.ecs.context.scripts.ScriptSystem;

public abstract class EntityContext extends OpenGlContext {

    private EntityRenderer renderer = null;
    private WindowMouse mouse = null;
    private WindowKeyboard keyboard = null;
    protected final EntityComponentSystem ecs = new EntityComponentSystem();

    public EntityContext(String title, int width, int height){
        super(title, width, height, Framebuffer.Attachment.COLOR_RGB_ATTACHMENT_0, Framebuffer.Attachment.DEPTH_RBO_ATTACHMENT);
    }


    @Override
    public void init(Graphics gfx) {
        super.init(gfx);
        renderer = new EntityRenderer(gfx, ecs, target);
        gfx.addRenderer(0, renderer);

        mouse = gfx.getWindow().getMouse();
        keyboard = gfx.getWindow().getKeyboard();

        setup(gfx);

        ecs.addEntitySystem(new ScriptSystem(ecs));
    }


    public abstract void setup(Graphics gfx);



    @Override
    public void render(Graphics gfx) {
    }

    @Override
    public void update(float dt) {
        ecs.update(dt);
    }

    public final WindowMouse getMouse(){
        return mouse;
    }

    public final WindowKeyboard getKeyboard(){
        return keyboard;
    }

}
