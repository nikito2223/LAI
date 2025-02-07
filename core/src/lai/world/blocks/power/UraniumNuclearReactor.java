package mindustry.world.blocks.power;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.world.blocks.power.NuclearReactor;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.meta.*;

import lai.content.LaiFx;
import lai.content.LaiItems;
import lai.content.LaiLiquids;

import static mindustry.Vars.*;

public class UraniumNuclearReactor extends PowerGenerator{
    public final int timerFuel = timers++;

    public Color lightColor = Color.valueOf("7f19ea");
    public Color coolColor = new Color(1, 1, 1, 0f);
    public Color hotColor = Color.valueOf("ff9575a3");
    public Color UraniumLight = Color.valueOf("94f644");
    /** ticks to consume 1 fuel */
    public float itemDuration = 120;
    /** heating per frame * fullness */
    public float heating = 0.01f; 
    /** threshold at which block starts smoking */
    public float smokeThreshold = 0.3f;
    /** heat threshold at which lights start flashing */
    public float flashThreshold = 0.46f;

    //custom
    public Liquid outLiquid = LaiLiquids.waterRadioction;
    public float liquidTick;

    /** heat removed per unit of coolant */
    public float coolantPower = 0.5f;

    public Item fuelItem = LaiItems.uranium;

    public TextureRegion radioctiontopRegion;
    public TextureRegion topRegion;
    public TextureRegion lightsRegion;

    public UraniumNuclearReactor(String name){
        super(name);
        itemCapacity = 30;
        liquidCapacity = 60;
        hasItems = true;
        hasLiquids = true;
        rebuildable = false;
        flags = EnumSet.of(BlockFlag.reactor, BlockFlag.generator);
        schematicPriority = -5;
        envEnabled = Env.any;
        outputsLiquid = true;
        explosionShake = 6f;
        explosionShakeDuration = 16f;

        explosionRadius = 19;
        explosionDamage = 1250 * 4;

        explodeEffect = LaiFx.uraniumReactorExplosion;
        explodeSound = Sounds.explosionbig;
    }

    public UraniumNuclearReactor setOutputLiquid(Liquid liquid, float petTick, String UraniumHex) {
    	this.outLiquid = liquid;
    	this.liquidTick = petTick;
    	this.UraniumLight = Color.valueOf(UraniumHex);
    	return this;
    }

    @Override
    public void init() {
    	topRegion = Core.atlas.find(name + "-top");
    	lightsRegion = Core.atlas.find(name + "-lights");
        radioctiontopRegion = Core.atlas.find(name + "-radioction");
        super.init();
    }

    @Override
    public void setStats(){
        super.setStats();

        if(hasItems){
            stats.add(Stat.productionTime, itemDuration / 60f, StatUnit.seconds);
            stats.add(Stat.output, outLiquid.localizedName);
        }
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("heat", (NuclearReactorBuild entity) -> new Bar("bar.heat", Pal.lightOrange, () -> entity.heat));
        addBar("radioactive", (NuclearReactorBuild entity) -> 
        new Bar(() -> Core.bundle.format("bar.radioactive", outLiquid.localizedName),
				() -> outLiquid.color,
				() -> entity.liquids.get(outLiquid) / this.liquidCapacity)
    	);
    }

    public class NuclearReactorBuild extends GeneratorBuild{
        public float heat;
        public float flash;
        public float smoothLight;
        //Custom Configuration
        public float liquidWater;

        @Override
        public void updateTile(){
            int fuel = items.get(fuelItem);
            float fullness = (float)fuel / itemCapacity;
            float liquidPut = liquidTick * edelta();
            liquidWater += liquidPut;
            productionEfficiency = fullness;

            if(fuel > 0 && enabled){
                heat += fullness * heating * Math.min(delta(), 4f);          

                handleLiquid(this, outLiquid, liquidPut);

                if(timer(timerFuel, itemDuration / timeScale)){
                    consume();
                }
            }else{
                productionEfficiency = 0f;
            }

            if(heat > 0){
                float maxUsed = Math.min(liquids.get(LaiLiquids.distilledwater), heat / coolantPower);
                heat -= maxUsed * coolantPower;
                liquids.remove(LaiLiquids.distilledwater, maxUsed);
            }

            if(heat > smokeThreshold){
                float smoke = 1.0f + (heat - smokeThreshold) / (1f - smokeThreshold); //ranges from 1.0 to 2.0
                if(Mathf.chance(smoke / 20.0 * delta())){
                    Fx.reactorsmoke.at(x + Mathf.range(size * tilesize / 2f),
                    y + Mathf.range(size * tilesize / 2f));
                }
            }

            heat = Mathf.clamp(heat);

            if(heat >= 0.999f){
                Events.fire(Trigger.thoriumReactorOverheat);
                kill();
            }

            dumpLiquid(outLiquid);
        }

        public void handleLiquid(Building build, Liquid liquid, float amount) {
        	float accepted = Math.min(amount, block.liquidCapacity - liquids.get(liquid));
        	if(accepted > 0) {
        		liquids.add(liquid, accepted);
        	}
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.heat) return heat;
            return super.sense(sensor);
        }

        @Override
        public boolean shouldExplode(){
            return super.shouldExplode() && (items.get(fuelItem) >= 5 || heat >= 0.5f);
        }

        @Override
        public void drawLight(){
            float fract = productionEfficiency;
            smoothLight = Mathf.lerpDelta(smoothLight, fract, 0.08f);
            Drawf.light(x, y, (90f + Mathf.absin(5, 5f)) * smoothLight, Tmp.c1.set(lightColor).lerp(Color.scarlet, heat), 0.6f * smoothLight);
        }

        @Override
        public void draw(){
            super.draw();


            Draw.color(coolColor, hotColor, heat);
            Fill.rect(x, y, size * tilesize, size * tilesize);

            Draw.color(LaiLiquids.distilledwater.color);
            Draw.alpha(liquids.get(LaiLiquids.distilledwater) / liquidCapacity);
            Draw.rect(topRegion, x, y);

            if(heat > flashThreshold){
                flash += (1f + ((heat - flashThreshold) / (1f - flashThreshold)) * 5.4f) * Time.delta;
                Draw.color(Color.red, Color.yellow, Mathf.absin(flash, 9f, 1f));
                Draw.alpha(0.3f);
                Draw.rect(lightsRegion, x, y);
            }

            if(liquidWater > 0)
            {
            	Draw.color(LaiLiquids.waterRadioction.color);
            	Draw.alpha(liquids.get(LaiLiquids.waterRadioction) / liquidCapacity);
            	Draw.rect(radioctiontopRegion, x, y);
            }

            Draw.reset();
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(heat);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            heat = read.f();
        }
    }
}
