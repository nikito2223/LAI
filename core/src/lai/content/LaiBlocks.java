package lai.content;

import arc.graphics.*; 
import arc.struct.*;
import arc.math.*;
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
import mindustry.world.consumers.*;
import mindustry.content.*;
 
import lai.world.blocks.distributor.LaiDuct;
import lai.graphics.*;  
import lai.audio.*;
import lai.world.blocks.campaign.*;
import lai.world.blocks.power.*;
import lai.world.blocks.units.*;
import lai.world.blocks.power.LithiumBattery;
import lai.world.blocks.power.UraniumNuclearReactor;
import lai.world.blocks.storage.CoreBlockLiquid;
import lai.world.blocks.storage.HealingTurret;
//Import static
import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

public class LaiBlocks {
    public static Block 
    //production
    slagdrill, crusherdrill, clippers, acidDrill,
    //distribution
    projectormoto,
    //power
    powerTower, lithiumBattery, miniReactor, earthGenerator, oilPowerPlant,
    //defense
    lithiumDuct, lithiumRouter, lithiumJunction, lithiumBridgeItem, ductLiquid,
    //storage
    coreCaser, coreActor,
    healingPoint,
    //sampenat
    launchomt,
    //walls
    lithiumWall, lithiumWallLarge, lithiumWallHuge, ironWall, ironWallLarge, steelWall, steelWallLarge,
    //units
    replicator;

    public static void load() {
		//endCrafting
		
		//production
		crusherdrill = new Drill("crusher-drill"){{
            requirements(Category.production, with(LaiItems.lithium, 20));
            tier = 8;
            drillTime = 180f;
            size = 2;
			itemCapacity = 20;
            researchCost = with(LaiItems.lithium, 20);
            consumeLiquid(LaiLiquids.freshwater, 0.25f / 180f).boost();
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

            consumeLiquid(LaiLiquids.freshwater, 0.25f / 60f).boost();
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
		powerTower = new PowerTower("power-tower"){{
            requirements(Category.power, with(LaiItems.lithium, 10));
            consumesPower = outputsPower = true;
            health = 180;
            range = 10;
            fogRadius = 1;
            researchCost = with(LaiItems.lithium, 7);
            consumePowerBuffered(2500f);
        }};

        oilPowerPlant = new OxygenGenerator("oil-power-plant"){{
            requirements(Category.power, with(lithium, 120, Items.graphite, 80, iron, 100));
            powerProduction = 8.33f;
            itemDuration = 90f;
            consumeLiquid(Liquids.oil, 0.2f);
            hasLiquids = true;
            size = 2;
            generateEffect = Fx.generatespark;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.06f;
            consumerOxygen(oxygen, 0.5f);

        }};

        lithiumBattery = new LithiumBattery("lithium-battery"){{
            requirements(Category.power, with(LaiItems.platinum, 20, LaiItems.lithium, 50, Items.silicon, 30));
            size = 2;
            emptyLightColor = Color.valueOf("6784ff");
            fullLightColor = Color.valueOf("67fbd2");
            baseExplosiveness = 5f;
            consumePowerBuffered(1300f);
        }};

        miniReactor = new UraniumNuclearReactor("mini-nuclear-reactor"){{
            requirements(Category.power, with(LaiItems.platinum, 20, LaiItems.lithium, 50, Items.silicon, 30));
            size = 3;
            newReactor(LaiLiquids.waterRadioction, 0.05f, 60f, 1f);
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.24f;

            health = 700;
            itemDuration = 360f;
            powerProduction = 15f;
            heating = 0.02f;
            explosionRadius = 25;
            squareSprite = false;
            consumeItem(LaiItems.uranium, 4);
            consumeLiquid(LaiLiquids.freshwater, heating / coolantPower).update(false);
        }};

        earthGenerator = new ThermalGenerator("earth-generator"){{
            requirements(Category.power, with(LaiItems.lithium, 60));
            attribute = Attribute.steam;
            group = BlockGroup.liquids;
            displayEfficiencyScale = 1f / 9f;
            minEfficiency = 9f - 0.0001f;
            powerProduction = 3f / 9f;
            displayEfficiency = false;
            generateEffect = Fx.turbinegenerate;
            effectChance = 0.04f;
            size = 3;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;

            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.6f * 9f){{
                blurThresh = 0.01f;
            }});

            hasLiquids = true;
            liquidCapacity = 20f;
            fogRadius = 3;
            researchCost = with(LaiItems.lithium, 15);
        }};


		//endPower
		//defense
		lithiumDuct = new LaiDuct("lithium-duct"){{
            requirements(Category.distribution, with(LaiItems.lithium, 1));
            health = 90;
            speed = 4f; 
            //bridgeReplacement = lithiumBridgeItem;
            antiRadiaction = true;
            researchCost = with(LaiItems.lithium, 5);
        }};
		lithiumRouter = new DuctRouter("lithium-router"){{
            requirements(Category.distribution, with(LaiItems.lithium, 3));
            health = 90;
            speed = 5f;
            regionRotated1 = 1;
            solid = true;
            squareSprite = false;
            researchCost = with(LaiItems.lithium, 9);
        }};
 
		lithiumBridgeItem = new DuctBridge("lithium-bridge"){{
            requirements(Category.distribution, with(LaiItems.lithium, 6));
            health = 90;
            range = 6; // Дальность моста
            speed = 5f;
            buildCostMultiplier = 2f;
            researchCostMultiplier = 0.3f;
            ((LaiDuct)lithiumDuct).bridgeReplacement = this;
            researchCost = with(LaiItems.lithium, 12);
		}};

		lithiumJunction = new Junction("lithium-Junction"){{
            requirements(Category.distribution, with(LaiItems.lithium, 2));
            speed = 26;
            capacity = 6;
            health = 30;
            buildCostMultiplier = 6f;


        }};
		//endDefens	
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
            squareSprite = false;
            isFirstTier = true;
            //TODO should this be higher?
            buildCostMultiplier = 0.7f;

            unitCapModifier = 15;
            researchCostMultiplier = 0.07f;
        }};
        coreActor = new CoreBlockLiquid("core-actors"){{
            requirements(Category.effect, with(LaiItems.lithium, 1200, LaiItems.platinum, 200));
            unitType = LaiUnits.arom;
            health = 28000;
            itemCapacity = 15000;
            size = 5;
            thrusterLength = 34/4f;
            armor = 5f;
            squareSprite = false;
            alwaysUnlocked = true;
            incinerateNonBuildable = true;
            isFirstTier = true;
            group = BlockGroup.liquids;
            //TODO should this be higher?
            buildCostMultiplier = 0.7f;
            unitCapModifier = 15;
            researchCostMultiplier = 0.07f;
        }};
        healingPoint = new HealingTurret("healing-point"){{
            requirements(Category.effect, with(LaiItems.lithium, 100, LaiItems.platinum, 200));
            size = 2;
            repairSpeed = 0.45f;
            repairRadius = 60f;
            beamWidth = 0.73f;
            powerUse = 1f;
            pulseRadius = 5f;
        }};
		//endStorage
		
		//sampenat
		launchomt = new LaiLaunch("launch-otm"){{
            requirements(Category.effect, BuildVisibility.campaignOnly, with(LaiItems.lithium, 40, Items.silicon, 140));
            size = 3;
            itemCapacity = 200;
            launchTime = 60f * 20;
            hasPower = true;
            squareSprite = false;
            consumePower(4f);
        }};

		//endSampenat
        //units
        replicator = new Replicator("replicator"){{
            requirements(Category.units, with(LaiItems.lithium, 320, Items.silicon, 200));
            consumePower(1.5f);
            size = 2;
            squareSprite = false;
        }};

		int wallHealthMultiplier = 4;
		//walls
		lithiumWall = new Wall("lithium-wall"){{
            scaledHealth = 150 * wallHealthMultiplier;
            size = 1;
            requirements(Category.defense, with(lithium, 6));
        }};
		lithiumWallLarge = new Wall("lithium-wall-large"){{
            scaledHealth = 150 * wallHealthMultiplier * 4;
            size = 2;
            requirements(Category.defense, ItemStack.mult(lithiumWall.requirements, 4));
        }};
		lithiumWallHuge = new Wall("lithium-wall-huge"){{
            scaledHealth = 150 * wallHealthMultiplier * 8;
            size = 3;
            requirements(Category.defense, ItemStack.mult(lithiumWallLarge.requirements, 4));
        }};
        ironWall = new Wall("iron-wall"){{
            requirements(Category.defense, with(iron, 6));
            scaledHealth = 110 * wallHealthMultiplier;
            size = 1;
        }};
        ironWallLarge = new Wall("iron-wall-large"){{
            requirements(Category.defense, ItemStack.mult(ironWall.requirements, 4));
            scaledHealth = 110 * wallHealthMultiplier * 4;
            size = 2;
        }};
        steelWall = new Wall("steel-wall"){{
            requirements(Category.defense, with(steel, 6));
            scaledHealth = 190 * wallHealthMultiplier;
            size = 1;
        }};
        steelWallLarge = new Wall("steel-wall-large"){{
            requirements(Category.defense, ItemStack.mult(steelWall.requirements, 4));
            scaledHealth = 190 * wallHealthMultiplier * 4;
            size = 2;
        }};
		//endWalls
    }
}
