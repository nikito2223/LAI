package lai.world.draw;

import arc.Core;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.type.Liquid;
import mindustry.world.draw.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.gen.*; 
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.liquid.LiquidBlock;
import mindustry.gen.Building;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Time;

import mindustry.Vars;
import mindustry.type.Liquid;
import mindustry.graphics.Drawf;


import static mindustry.Vars.*;

public class PulsingLiquidRenderer extends DrawBlock {

	public Color start;
    public Color end;
    public float alpha;
    public boolean gradient;

    public PulsingLiquidRenderer(Color start, Color end, float alpha){
        this.start = start;
        this.end = end;
        this.alpha = alpha;
        this.gradient = !start.equals(end);
    }

    public static void render(Liquid liquid, float amount, float capacity, float x, float y, float w, float h){
        float fill = amount / capacity;
        float pulse = Mathf.absin(Time.time, 6f, 0.1f); // частота и амплитуда

        Color base = liquid.color.cpy();
        base.a += pulse;

        Draw.color(base);
        Draw.rect(Core.atlas.find("liquid"), x, y, w + pulse * 4f, h * fill + pulse * 4f);
        Draw.color();
    }

}