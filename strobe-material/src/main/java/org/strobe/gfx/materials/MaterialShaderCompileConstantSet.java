package org.strobe.gfx.materials;

import org.strobe.gfx.lights.LightConstants;
import org.strobe.gfx.materials.shaders.MaterialShader;
import org.strobe.gfx.opengl.bindables.shader.ShaderCompileConstantSet;

public final class MaterialShaderCompileConstantSet extends ShaderCompileConstantSet {

    private static MaterialShaderCompileConstantSet instance = null;

    public static MaterialShaderCompileConstantSet getInstance(){
        if(instance == null)return new MaterialShaderCompileConstantSet();
        return instance;
    }

    private MaterialShaderCompileConstantSet(){
        super(
                "DIRECTIONAL_LIGHT_COUNT = " + LightConstants.DIRECTIONAL_LIGHT_COUNT
        );
    }


}
