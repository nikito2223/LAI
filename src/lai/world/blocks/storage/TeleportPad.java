package lai.world.blocks.storage;

import arc.scene.ui.layout.Table;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.mod.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.liquid.Conduit.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;

import arc.*;
import arc.util.io.*;

import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;

import arc.audio.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import arc.util.pooling.Pool.*;
import arc.util.pooling.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

import lai.world.meta.*;
import lai.world.*;

import mindustry.ui.Bar;
import mindustry.world.blocks.power.PowerBlock;

public class TeleportPad extends LaiBlock {

    public float teleportRange = 200f;
    public float warmupTime = 10f * 60f; // 10 сек
    public TextureRegion top;

    @Override
    public void load(){
        super.load();
        top = Core.atlas.find(name + "-top");
    }


    public TeleportPad(String name) {
        super(name);

        update = true;
        solid = true;
        hasPower = true;
        consumesPower = true;
        quickRotate = false;
        configurable = true;
        saveConfig = true;

        config(Point2.class, (TeleportPadBuild tile, Point2 p) ->
            tile.targetPos = Point2.pack(p.x + tile.tileX(), p.y + tile.tileY())
        );
        
        config(Integer.class, (TeleportPadBuild tile, Integer pos) ->
            tile.targetPos = pos
        );


    }

    @Override
    public void setStats(){
        super.setStats();
    
        stats.add(Stat.powerUse, 300f, StatUnit.perSecond);
    
        stats.add(LaiStat.teleportPower, warmupTime / 60f, StatUnit.seconds);
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("charge", (TeleportPadBuild e) ->
            new Bar("bar.lai-energy-teleport", Pal.accent, () -> e.charge / warmupTime)
        );
    }

    public class TeleportPadBuild extends Building {
        public Integer targetPos = null;
        public float charge = 0f;           
        public float detectRange = 6f;      
        public float noTeleportTime = 0f;

        @Override
        public void updateTile() {
            if(noTeleportTime > 0f){
                noTeleportTime -= Time.delta;
                return;
            }
            if(targetPos != null){
                Building t = world.build(targetPos);
                if(t == null){
                    targetPos = null;
                }
            }

        
            if(efficiency <= 0f){
                return;
            }
        
            if(targetPos == null){
                return;
            }

            if(charge < warmupTime){
                charge += Time.delta;
                if(charge > warmupTime) charge = warmupTime;
                return;
            }

        
            Unit u = Units.closest(team, x, y, detectRange,
                uu -> !uu.isFlying() && uu.within(this, detectRange)
            );
        
            if(u == null) return;

            Building target = world.build(targetPos);
        
            if(target != null){
                u.set(target.x, target.y);
            }

    
            noTeleportTime = 10f;
        
            charge = 0f;
        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
        
            if(this == other){
                if(targetPos == null) deselect();
                configure(-1);
                return false;
            }
        
            if(other == null) return true;
        
            
            if(targetPos != null && other.pos() == targetPos){
                configure(-1);
                return false;
            }
        
            if(other.block == block && other.team == team){
                TeleportPadBuild o = (TeleportPadBuild) other;
        
                if(o.targetPos != null && o.targetPos == this.pos()){
                    return false;
                }
            }
        
            if(other.block == block && other.team == team && other.within(this, teleportRange)){
                configure(other.pos());
                return false;
            }
        
            return true;
        }

        @Override
        public Integer config() {
            return targetPos;
        }

        @Override
        public void drawConfigure() {
            float sin = Mathf.absin(Time.time, 6f, 1f);

            Draw.color(Pal.accent);
            Lines.stroke(1f);
            Drawf.circles(x, y, (block.size * tilesize / 2f) + sin, Pal.accent);

            if(targetPos != null){
                Building target = world.build(targetPos);

                if(target != null){
                    Drawf.circles(target.x, target.y, (block.size * tilesize / 2f) + sin, Pal.place);
                    Drawf.arrow(x, y, target.x, target.y, block.size * tilesize + sin, 4f + sin);
                }
            }

            Drawf.dashCircle(x, y, teleportRange, Pal.accent);
        }

        @Override
        public void draw(){
            super.draw();
            
            
            float heat = charge / warmupTime;
            float time = Time.time;
            
            
            // ===== DRAW ENERGY-FILLED TOP =====
            if(top != null){
                Draw.z(Layer.blockOver);
                Color energy = team.color.cpy();
                energy.a = heat * 0.9f + Mathf.absin(time, 6f, 0.1f);
                Draw.color(energy);
                Draw.rect(top, x, y, rotdeg());
                Draw.color();
            }
            
            
            // ===== ENERGY CIRCLES INSTEAD OF SQUARES =====
            Color col = (efficiency <= 0f ? Color.scarlet : team.color);
            float energy = heat;
            float baseRad = (block.size * tilesize / 2f) * 1.25f;
            int rings = 3;
            
            
            Draw.z(Layer.effect);
            
            
            for(int i = 0; i < rings; i++){
            float prog = (float)i / (rings - 1);
            float phase = Mathf.absin(time + i * 25f, 8f, 0.15f);
            float radius = baseRad * (energy * (0.3f + prog)) + phase * 3f;
            
            
            float alpha;
            if(energy >= 0.999f){
                float blink = Mathf.absin(time - i * 3f, 6f, 0.8f);
                alpha = blink;
            }else{
                alpha = (energy * 0.7f + prog * 0.3f) * (0.6f + phase);
            }
            
            
            Draw.color(col, alpha);
            Lines.stroke(1.5f + prog * 2f);
            Lines.circle(x, y, radius);
            }
            
            
            Draw.color();
        }


        @Override
        public void write(Writes w){
            super.write(w);
            w.i(targetPos == null ? -1 : targetPos);
        }
    
        @Override
        public void read(Reads r, byte rev){
            super.read(r, rev);
            int pos = r.i();
            targetPos = pos == -1 ? null : pos;
        }
    }
}
