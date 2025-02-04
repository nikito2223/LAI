package lai.world.blocks.power;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.power.*;
import mindustry.game.EventType.*;
import static mindustry.game.EventType.*;
import mindustry.graphics.Pal;
import mindustry.ui.*;
import mindustry.world.consumers.*;

import lai.graphics.*;

import static mindustry.world.blocks.power.Battery.*;

public class LithiumBattery extends PowerDistributor{
	public @Nullable DrawBlock drawer;

	public Color emptyLightColor = Color.valueOf("f8c266");
    public Color fullLightColor = Color.valueOf("fb9567");
 
	public LithiumBattery(String name) {
		super(name);
		outputsPower = true;
        consumesPower = true;
        canOverdrive = false;
        //типо должно заменить обычный вид
        flags = EnumSet.of(BlockFlag.battery);
        //TODO could be supported everywhere...
        destructible = true;
        //batteries don't need to update
        update = true;

	}

	@Override
    public void init(){
        super.init();

        checkDrawDefault();
    }

    @Override
    public void setBars(){
    	super.setBars();
    	addBar("powerHealth", (BatteryBuild entity) -> new Bar("bar.lai-powerHealth.name", LaiPal.lithiumStrength, () -> entity.powerHealth));
    }

    void checkDrawDefault(){
        if(drawer == null){
            drawer = new DrawMulti(new DrawDefault(), new DrawPower(){{
                emptyLightColor = LithiumBattery.this.emptyLightColor;
                fullLightColor = LithiumBattery.this.fullLightColor;
            }}, new DrawRegion("-top"));
        }
    }


    @Override
    public void load(){
        checkDrawDefault();

        super.load();
        drawer.load(this);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public TextureRegion[] icons(){
        return drawer.finalIcons(this);
    }

    @Override
    public void getRegionsToOutline(Seq<TextureRegion> out){
        drawer.getRegionsToOutline(this, out);
    }

    public class BatteryBuild extends Building{
    	public float powerHealth = 1f;
    	public boolean isPowerDamage;
 	
        @Override
        public void draw(){
            drawer.draw(this);
        }

        @Override
        public void drawLight(){
            super.drawLight();
            drawer.drawLight(this);
        }

        @Override
        public float warmup(){
            return power.status;
        }

        @Override
        public void overwrote(Seq<Building> previous){
            for(Building other : previous){
                if(other.power != null && other.block.consPower != null && other.block.consPower.buffered){
                    float amount = other.block.consPower.capacity * other.power.status;
                    power.status = Mathf.clamp(power.status + amount / consPower.capacity);
                }
            }
        }

        @Override
        public void updateTile() {
        	float currentEnergy = power.graph.getBatteryStored();
        	float newEnergy = currentEnergy / 2; 
      

        	if(power.status == 1){
    			isPowerDamage = true;
    		}

        	if(isPowerDamage == true) {
        		powerHealth -= Time.delta * 2/390;
        	}

        	if(powerHealth == 0) {
        		
        	}

        }

        @Override
        public BlockStatus status(){
            if(Mathf.equal(power.status, 0f, 0.001f)) return BlockStatus.noInput;
            if(Mathf.equal(power.status, 1f, 0.001f)) return BlockStatus.active;
            return BlockStatus.noOutput;
        }
    }
}