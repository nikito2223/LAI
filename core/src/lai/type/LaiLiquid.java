package lai.type;

import mindustry.type.Liquid;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.gen.*; 
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import static mindustry.entities.Puddles.*;
import lai.world.meta.*;
import lai.world.draw.*;

import static mindustry.Vars.*;

public class LaiLiquid extends Liquid{

    public Color baseColor;

    public Color gradientStart = null;
    public Color gradientEnd = null;

    public boolean damageLiquid = false; //Может ли дамажить эта житкость, при условиях
	public float radioactive = 0f; //Радиактивность

    public float alpha = 1f;

    public LaiLiquid(String name, Color color, float alpha){
        super(name);
        this.baseColor = new Color(color);
        this.alpha = alpha;
    }
    public LaiLiquid(String name, Color color){
        super(name);
        this.baseColor = new Color(color);
    }


    public LaiLiquid gradient(Color start, Color end){
        this.gradientStart = start;
        this.gradientEnd = end;
        return this;
    }


    @Override
    public void setStats(){
        stats.addPercent(Stat.radioactivity, radioactive);
        stats.addPercent(Stat.explosiveness, explosiveness);
        stats.addPercent(Stat.flammability, flammability);
        stats.addPercent(Stat.temperature, temperature);
        stats.addPercent(Stat.heatCapacity, heatCapacity);
        stats.addPercent(Stat.viscosity, viscosity);
    }

    @Override
    public void load(){
        super.load();

        Color base = new Color(baseColor.r, baseColor.g, baseColor.b, alpha);
    
        // Для UI отображения
        if(gradientStart != null && gradientEnd != null){
            this.color = gradientStart.cpy().lerp(gradientEnd, 0.5f).a(alpha);
        } else {
            this.color = baseColor.cpy().a(alpha);
        }
    }

}