package lai.world.blocks.storage;

import mindustry.world.blocks.storage.*;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import static mindustry.world.blocks.liquid.LiquidBlock.*;
import mindustry.gen.*;

import mindustry.core.UI;
import mindustry.ui.*;
import lai.type.LaiLiquid;

public class CoreBlockLiquid extends CoreBlock{
	public TextureRegion topRegion;
	public float dynamicRadius = 60f;

	public float liquidPadding = 5f;

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
		topRegion = Core.atlas.find(name + "-top");
	}

	@Override
    public void setBars(){
        super.setBars();
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
            Draw.rect(topRegion, x, y);

            if(liquids.currentAmount() > 0.001f){
                Draw.rect(region,x,y);
                drawTiledFrames(size, x, y, liquidPadding, liquids.current(), liquids.currentAmount() / liquidCapacity);

            }

            Draw.rect(region, x, y);
		}
	}
}