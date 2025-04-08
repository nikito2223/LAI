package lai.world.blocks.power;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.world.*;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.power.NuclearReactor;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*; 
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.meta.*;
import mindustry.entities.Units.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.entities.*;

import mindustry.world.blocks.power.*;

public class OxygenGenerator extends ConsumeGenerator {
	
    public Liquid boostLiquid; // Жидкость для буста (кислород)
    public float boostAmount;
    public boolean hasBoost = true;

	public OxygenGenerator(String name){
		super(name);
	}


	@Override
	public void setBars(){
		super.setBars();
		//addBar("oxygen", (OxygenGeneratorBuild entity) -> new Bar("bar.liquid-oxygen", entity.liquids.current().color, () -> entity.productionEfficiency));
	}

	public OxygenGenerator consumerOxygen(Liquid liquid, float amount){
		this.boostAmount = amount;
		this.boostLiquid = liquid;
		return this;
	}


	public class OxygenGeneratorBuild extends ConsumeGeneratorBuild {
		public float boost = Mathf.lerp(1f, 1.5f, optionalEfficiency) * efficiency;
		@Override
    	public void updateTile() {
        	super.updateTile();

        	if(boostLiquid != null){
        		hasBoost = true;
        	}
        	else {
        		hasBoost = false;
        	}

        	if(enabled){
        		if(hasBoost){
        			productionEfficiency += boost;
        		}
        	}
        	else{
        		productionEfficiency = 0f;
        	}
    	}
    	public float realBoost(){
    		return boost;
    	}
	}
}