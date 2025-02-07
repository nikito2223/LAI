package lai.content;

import arc.graphics.*; 
import arc.struct.*;

import mindustry.entities.pattern.ShootSpread;
import mindustry.entities.bullet.*;
import mindustry.entities.part.RegionPart;
import mindustry.gen.*;
import mindustry.graphics.*; 
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*; 
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.blocks.units.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.content.*;

import lai.graphics.*;  
import lai.audio.*;
import lai.world.blocks.campaign.*;
import lai.world.blocks.power.LithiumBattery;

//Import static
import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

import lai.world.blocks.defense.turrets.powerTurrets.*;

public class LaiBlocks {
    public static Block 
    //production
    slagdrill, crusherdrill, clippers, acidDrill,
    //distribution
    projectormoto,
    //power
    gannerSolarPanel, powerGerm, battaryLithium, miniReactor,
    //defense
    lithiumDuct, lithiumRouter, lithiumJunction, lithiumBridgeItem,
    //environment & ores
    oreLithium, darkgreenStone, darkgreenStoneWall, darkBlueSand, darkBlueSandWall, distilledwater, fueloli, wallOrelithium, orePlatinum, blueBoulder, orerhodium, pinksand, redstone, darkblueSandDistilledWater, deepDistilledwater, lava, pinksandWall,
    //units
    spiderFactory, spiderReconstructor,
    //storage
    coreCaser,
    //sampenat
    launchomt, interplanetary,
    //walls
    lithiumWall, lithiumWallLarge, lithiumWallHuge;

    public static void load() {
        //endregion
        //region crafting
        

		//endCrafting
		
		//production
		crusherdrill = new Drill("crusher-drill"){{
            requirements(Category.production, with(LaiItems.lithium, 20));
            tier = 8;
            drillTime = 180f;
            size = 2;
			itemCapacity = 20;
            researchCost = with(LaiItems.lithium, 20);
            consumeLiquid(LaiLiquids.distilledwater, 0.25f / 180f).boost();
        }};
        acidDrill = new Drill("acid-drill"){{
            requirements(Category.production, with(LaiItems.lithium, 90, LaiItems.platinum, 40, Items.silicon, 120));
            tier = 9;
            drillTime = 180f;
            size = 2;
            itemCapacity = 20;
            researchCost = with(LaiItems.lithium, 90, LaiItems.platinum, 40, Items.silicon, 120);
            consumeLiquid(LaiLiquids.acid, 0.20f / 60f);
        }};
			clippers = new BeamDrill("clippers"){{
            requirements(Category.production, with(LaiItems.lithium, 40));
            consumePower(0.15f);

            drillTime = 350f;
            tier = 3;
            size = 2;
            range = 5;
            fogRadius = 5;
            researchCost = with(LaiItems.lithium, 10);

            consumeLiquid(LaiLiquids.distilledwater, 0.25f / 60f).boost();
        }};
		//endProduction
		
		//distribution
		projectormoto = new ForceProjector("projector-moto")
		{{
            requirements(Category.effect, with(LaiItems.platinum, 175, LaiItems.lithium, 15));
            size = 3;
            sides = 15;
            phaseRadiusBoost = 30f;
            radius = 153.3f;
            shieldHealth = 1375f;
            cooldownNormal = 1.5f;
            cooldownLiquid = 1.2f;
            cooldownBrokenBase = 0.35f;

            itemConsumer = consumeItem(LaiItems.lithium).boost();
            consumePower(4f);
        }};
		//endDistribution
		
		//endLiquids
		
		//power
		gannerSolarPanel = new SolarGenerator("genner-panel-large"){{
            requirements(Category.power, with(LaiItems.lithium, 80));
            size = 4;
            powerProduction = 3.3f;
        }};
		powerGerm = new BeamNode("power-germ"){{
            requirements(Category.power, with(LaiItems.lithium, 10));
            consumesPower = outputsPower = true;
            health = 180;
            range = 15;
            fogRadius = 1;
            researchCost = with(LaiItems.lithium, 7);

            consumePowerBuffered(2500f);
        }};
        battaryLithium = new LithiumBattery("battary-lithium"){{
            requirements(Category.power, with(LaiItems.platinum, 20, LaiItems.lithium, 50, Items.silicon, 30));
            size = 2;
            emptyLightColor = Color.valueOf("6784ff");
            fullLightColor = Color.valueOf("67fbd2");
            baseExplosiveness = 5f;
            consumePowerBuffered(13000f);
        }};

        miniReactor = new UraniumNuclearReactor("mini-nuclear-reactor"){{
            requirements(Category.power, with(LaiItems.platinum, 20, LaiItems.lithium, 50, Items.silicon, 30));
            size = 3;
            setOutputLiquid(LaiLiquids.waterRadioction, 0.05f, "c1e8a2");

            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.24f;

            health = 700;
            itemDuration = 360f;
            powerProduction = 15f;
            heating = 0.02f;
            explosionRadius = 25;

            consumeItem(LaiItems.uranium, 1);
            consumeLiquid(LaiLiquids.distilledwater, heating / coolantPower).update(false);
        }};

		//endPower

		//defense
		lithiumDuct = new Duct("lithium-duct"){{
            requirements(Category.distribution, with(LaiItems.lithium, 1));
            health = 65;
            speed = 4f; 
            //bridgeReplacement = lithiumBridgeItem;
            
            envEnabled |= Env.terrestrial | Env.underwater;
            envDisabled = Env.none;
            researchCost = with(LaiItems.lithium, 5);
        }};
		lithiumRouter = new DuctRouter("lithium-router"){{
            requirements(Category.distribution, with(LaiItems.lithium, 3));
            health = 90;
            speed = 5f;
            regionRotated1 = 1;
            solid = true;
            researchCost = with(LaiItems.lithium, 9);
        }};
 
		lithiumBridgeItem = new DuctBridge("lithium-bridge"){{
            requirements(Category.distribution, with(LaiItems.lithium, 6));
            health = 90;
            range = 8; // Дальность моста
            speed = 5f;
            buildCostMultiplier = 2f;
            researchCostMultiplier = 0.3f;
            ((Duct)lithiumDuct).bridgeReplacement = this;
            researchCost = with(LaiItems.lithium, 12);
		}};

		lithiumJunction = new Junction("lithium-Junction"){{
            requirements(Category.distribution, with(LaiItems.lithium, 2));
            speed = 26;
            capacity = 6;
            health = 30;
            buildCostMultiplier = 6f;


        }};
		//endDefense
		
		//environment & ores
		darkBlueSand = new Floor("dark-blue-sand") {{
            itemDrop = Items.sand;
            mapColor = Color.valueOf("5c75f2");
        }};
        pinksand = new Floor("pink-sand") {{
            itemDrop = Items.sand;
            mapColor = Color.valueOf("8664f4");
        }};
        redstone = new Floor("red-stone") {{
            mapColor = Color.valueOf("cf8634");
        }};
		oreLithium = new OreBlock("ore-lithium", LaiItems.lithium){{
            oreDefault = true;
            oreThreshold = 0.864f;
            oreScale = 24.904762f;
            mapColor = Color.valueOf("4d59a1");
        }};
		orePlatinum = new OreBlock("ore-platinum", LaiItems.platinum){{
            oreDefault = true;
            oreThreshold = 0.864f;
            oreScale = 24.904762f;
            mapColor = Color.valueOf("d76dd1");
		}};
        orerhodium = new OreBlock("ore-rhodium", LaiItems.rhodium){{
            oreDefault = true;
            oreThreshold = 0.864f;
            oreScale = 24.904762f;
            mapColor = Color.valueOf("a0ecbd");
        }};
		darkgreenStone = new Floor("dark-green-Stone"){{
            variants = 3;
            attributes.set(Attribute.water, 0.40f);
            attributes.set(Attribute.heat, -0.50f);
            mapColor = Color.valueOf("16182c");
        }};
        
        //valid code since Floor extends Block
        

        darkgreenStoneWall = new StaticWall("dark-blue-Stone-Wall") {{
            Block floor = new Floor("foo");
            floor = darkgreenStone;
            darkgreenStone.asFloor().wall = this;
            albedo = 0.6f;
        }};

        darkBlueSandWall = new StaticWall("dark-blue-sand-wall") {{
            Block floor = new Floor("one");
            floor = darkBlueSand;
            darkgreenStone.asFloor().wall = this;
            albedo = 0.6f;
        }};

        pinksandWall = new StaticWall("pink-sand-wall") {{
            Block floor = new Floor("two-two");
            floor = pinksand;
            pinksand.asFloor().wall = this;
            albedo = 0.6f;
        }};
		fueloli = new Floor("pooled-fueloli"){{
            drownTime = 230f;
            status = StatusEffects.tarred;
            statusDuration = 240f;
            speedMultiplier = 0.19f;
            variants = 0;
            liquidDrop = LaiLiquids.fueloli;
            isLiquid = true;
            cacheLayer = CacheLayer.tar;
        }};

        lava = new Floor("deep-lava"){{
            drownTime = 230f;
            status = StatusEffects.tarred;
            statusDuration = 240f;
            speedMultiplier = 0.19f;
            variants = 0;
            liquidDrop = LaiLiquids.lava;
            isLiquid = true;
            cacheLayer = CacheLayer.slag;
        }};
		wallOrelithium = new OreBlock("ore-wall-lithium", LaiItems.lithium){{
            wallOre = true;
        }};
		blueBoulder = new Prop("blue-Boulder"){{
            variants = 2;
            darkgreenStone.asFloor().decoration = darkBlueSand.asFloor().decoration = this;
        }};

        deepDistilledwater = new Floor("deep-distilledwater"){{
            speedMultiplier = 0.2f;
            variants = 0;
            liquidDrop = LaiLiquids.distilledwater;
            liquidMultiplier = 1.5f;
            isLiquid = true;
            status = StatusEffects.wet;
            statusDuration = 120f;
            drownTime = 200f;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        distilledwater = new Floor("shallow-distilledwater"){{
            speedMultiplier = 0.5f;
            variants = 0;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = LaiLiquids.distilledwater;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        darkblueSandDistilledWater = new ShallowLiquid("dark-blue-sand-distilled-water"){{
            speedMultiplier = 0.8f;
            statusDuration = 50f;
            albedo = 0.9f;
            supportsOverlay = true;
        }};
		//Endenvironment
		
		//units
		//spiderFactory = new UnitFactory("spider-factory"){{
        //    scaledHealth = 120;
        //    size = 3;
        //    consumePower(3f);
        //    requirements(Category.units, with(LaiItems.lithium, 100, silicon, 75));
        //    plans.add(
        //        new UnitPlan(FormUnits.genrtor, 60f * 15, with(LaiItems.lithium, 10, silicon, 10)),
		//		new UnitPlan(FormUnits.herma, 60f * 15, with(LaiItems.lithium, 10, silicon, 10))
        //    );
        //}};

        //spiderReconstructor = new Reconstructor("spider-reconstructor"){{
        //    requirements(Category.units, with(LaiItems.lithium, 100, silicon, 75));
//
        //    size = 3;
        //    consumePower(3f);
        //    consumeItems(with(LaiItems.lithium, 140, Items.graphite, 40, Items.silicon, 160));
//
        //    constructTime = 60f * 10f;
//
        //    upgrades.addAll(
        //        new UnitType[]{FormUnits.genrtor, FormUnits.mover}
        //    );
        //}};
        
		//endUnits
		
		//storage
		coreCaser = new CoreBlock("core-caser"){{
            requirements(Category.effect, with(LaiItems.lithium, 1200, LaiItems.platinum, 200));
            unitType = LaiUnits.arom;
            health = 18000;
            itemCapacity = 8000;
            size = 4;
            thrusterLength = 34/4f;
            armor = 5f;
            alwaysUnlocked = true;
            incinerateNonBuildable = true;

            isFirstTier = true;
            //TODO should this be higher?
            buildCostMultiplier = 0.7f;

            unitCapModifier = 15;
            researchCostMultiplier = 0.07f;
        }};
		//endStorage
		
		//sampenat
		launchomt = new LaiLaunch("launch-otm"){{
            requirements(Category.effect, BuildVisibility.campaignOnly, with(LaiItems.lithium, 40, Items.silicon, 140));
            size = 3;
            itemCapacity = 200;
            launchTime = 60f * 20;
            hasPower = true;
            
            consumePower(4f);
        }};
		interplanetary = new LaiAccelerator("interplanetary"){{
            requirements(Category.effect, BuildVisibility.campaignOnly, with(LaiItems.lithium, 111, Items.silicon, 111, LaiItems.platinum, 111));
            researchCostMultiplier = 0.1f;
            size = 7;
            hasPower = true;
            consumePower(100f);
            buildCostMultiplier = 0.5f;
            scaledHealth = 80;
        }};
		//endSampenat
		
		//walls
			lithiumWall = new Wall("lithium-wall"){{
            scaledHealth = 800;
            size = 1;
            requirements(Category.defense, with(lithium, 6));
        }};
			lithiumWallLarge = new Wall("lithium-wall-large"){{
            scaledHealth = 1600;
            size = 2;
            requirements(Category.defense, with(lithium, 24));
        }};
			lithiumWallHuge = new Wall("lithium-wall-huge"){{
            scaledHealth = 2200;
            size = 3;
            requirements(Category.defense, with(lithium, 48));
        }};
		//endWalls
    }
}
