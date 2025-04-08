package lai.world.blocks.storage;

import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.liquid.*;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.world.blocks.power.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.meta.*;
import mindustry.entities.Units.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.entities.*;
import mindustry.world.draw.*;
import static mindustry.world.blocks.liquid.LiquidBlock.*;
import mindustry.world.blocks.liquid.LiquidRouter;
import static lai.content.LaiLiquids.*;
import mindustry.gen.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import static lai.type.LoadAnnoProcessor.*;
import lai.content.*;
import lai.graphics.*;
import lai.type.LaiLiquid;

import static mindustry.Vars.*;
import mindustry.Vars;

public class CoreBlockLiquid extends CoreBlock{

	public TextureRegion bottomRegion; 
	public TextureRegion topRegion;
	public float dynamicRadius = 60f;

	public float liquidPadding = 0f;

	public CoreBlockLiquid(String name){
		super(name);
		update = true;
        solid = true;
		liquidCapacity = 5000;
		hasLiquids = true;
		envEnabled = Env.any;
		outputsLiquid = true;
		sync = true;
		envEnabled |= Env.space | Env.underwater;
	}

	@Override
	public void load(){
		super.load();
		bottomRegion = Core.atlas.find(name + "-bottom");
		topRegion = Core.atlas.find(name + "-top");
	}

	@Override
    public void setBars(){
        super.setBars();
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, bottomRegion};
    }


	public class CoreBlockLiquidBuild extends CoreBuild{
		public boolean liquidDamage = false;
		public @Nullable Liquid liquid = null;

		public float range(){
			return dynamicRadius;
		}

		@Override
        public void updateTile(){
        	if(liquids.currentAmount() > 0.01f){
                dumpLiquid(liquids.current());
                liquid = liquids.current();
            	if(liquid instanceof LaiLiquid){
            		LaiLiquid laiLiquid = (LaiLiquid) liquid;
            		if(laiLiquid.damageLiquid){
            			liquidDamage = true;
            		}
            		else{
            			liquidDamage = false;
           			}
            	}
            	else if (liquids.currentAmount() < 0.001f) {
            		liquidDamage = false;
            	}
            	else{
            		liquidDamage = false;
           		}
           }
        }
        

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return (liquids.current() == liquid || liquids.currentAmount() < 0.01f);
        }

        @Override
        public void drawSelect(){
            float dynamicRadius = range();
            if (liquidDamage == true){
            	Drawf.dashCircle(x,y,dynamicRadius, liquids.current().color);
            }
        }

		@Override
		public void draw() {
            float rotation = rotate ? rotdeg() : 0;
            Draw.rect(topRegion, x, y);

            if(liquids.currentAmount() > 0.001f){
                drawTiledFrames(size, x, y, liquidPadding, liquids.current(), liquids.currentAmount() / liquidCapacity);
            }

            Draw.rect(region, x, y);
		}
	}
}