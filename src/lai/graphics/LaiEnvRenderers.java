package lai.graphics;

import arc.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.type.*;
import mindustry.world.meta.*;

import mindustry.graphics.*;

import lai.world.meta.LaiEnv;

import static mindustry.Vars.*;

public class LaiEnvRenderers {
	public static void init(){
        // Основной цвет тумана — темно-сине-серый с лёгким голубым свечением
        Color mistColor = Color.valueOf("2a3a44");
        // Цвет внутреннего свечения тумана — чуть голубоватый (отражает литий)
        Color glowColor = Color.valueOf("7da8b8");

        // Загружаем текстуру и применяем фильтры
        Core.assets.load("sprites/distortAlpha.png", Texture.class);

        renderer.addEnvRenderer(LaiEnv.misty, () -> {
            Texture tex = Core.assets.get("sprites/distortAlpha.png", Texture.class);
            if(tex.getMagFilter() != TextureFilter.linear){
                tex.setFilter(TextureFilter.linear);
                tex.setWrap(TextureWrap.repeat);
            }

            // Лёгкая основа тумана
            Draw.draw(Layer.light + 0.4f, () -> {
                Draw.color(mistColor, 0.4f);
                Fill.rect(Core.camera.position.x, Core.camera.position.y, Core.camera.width, Core.camera.height);
                Draw.color();
            });

            // Основной туманный слой (с глубиной и колебаниями)
            Draw.z(state.rules.fog ? Layer.fogOfWar + 1 : Layer.weather - 1);
            Weather.drawNoiseLayers(
                tex,
                mistColor.cpy().lerp(glowColor, 0.25f), // туман с голубым оттенком
                1300f, // масштаб волн
                0.12f, // плотность (чем меньше, тем больше слоёв)
                0.25f, // прозрачность (умеренно прозрачный)
                0.9f, 1.2f, // глубина/амплитуда
                0f,
                3,
                -1.0f, 0.8f, 0.7f, 0.8f // параметры "шума" (делают мягкий дым)
            );

            // Эффект слабого свечения (металлическая пыль)
            Draw.blend(Blending.additive);
            Draw.color(glowColor, 0.1f);
            Fill.rect(Core.camera.position.x, Core.camera.position.y, Core.camera.width, Core.camera.height);
            Draw.blend();
            Draw.reset();
        });
	}
}