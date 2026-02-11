package lai.graphics;

import arc.*;
import arc.files.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.g2d.*;
import arc.graphics.g3d.*;
import arc.graphics.gl.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.game.EventType.*;
import mindustry.type.*;

import lai.core.LaiVars;

import mindustry.Vars;

import static mindustry.Vars.*;

public class LAIShaders extends Shaders {
	
	public static ModSurfaceShader plazme;

    public static Shader g3d = new Shader(
        LaiVars.internalFileTree.child("shaders/3d.vert"),
        LaiVars.internalFileTree.child("shaders/3d.frag")
    );

	public static void init(){
		plazme = new ModSurfaceShader("plazme");
	}

    public static class ModSurfaceShader extends Shader {
        Texture noiseTex;

        public ModSurfaceShader(String frag) {
            super(Shaders.getShaderFi("screenspace.vert"), Vars.tree.get("shaders/" + frag + ".frag"));
            loadNoise();
        }

        public String textureName(){
            return "noise";
        }

        public void loadNoise(){
            Core.assets.load("sprites/" + textureName() + ".png", Texture.class).loaded = t -> {
                t.setFilter(Texture.TextureFilter.linear);
                t.setWrap(Texture.TextureWrap.repeat);
            };
        }

        @Override
        public void apply() {
            setUniformf("u_campos", Core.camera.position.x - Core.camera.width / 2, Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_resolution", Core.camera.width, Core.camera.height);
            setUniformf("u_time", Time.time);

            if(hasUniform("u_noise")){
                if(noiseTex == null) {
                    noiseTex = Core.assets.get("sprites/" + textureName() + ".png", Texture.class);
                }

                noiseTex.bind(1);
                renderer.effectBuffer.getTexture().bind(0);

                setUniformi("u_noise", 1);
            }
        }
    }



}