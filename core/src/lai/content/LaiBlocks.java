package lai.content;

import arc.graphics.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.meta.*;
import mindustry.content.*;

import lai.world.LaiBlock;

import lai.world.blocks.power.*;
import lai.world.blocks.power.LithiumBattery;
import lai.world.blocks.power.UpgradeRector;
import lai.world.blocks.storage.CoreBlockLiquid;
import lai.world.blocks.explosives.*;
import lai.world.blocks.production.*;
//Import static
import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

import lai.world.blocks.defense.*;


public class LaiBlocks {
    public static Block 
    //production
    slagdrill, crusherdrill, clippers, acidDrill, omniDrill,
    //distribution
    projectormoto,
    //power
    powerTower, lithiumBattery, miniReactor, earthGenerator, oilPowerPlant,
    //storage
    coreCaser, coreActor, tnt,

    lithiumWall, lithiumWallLarge, lithiumWallHuge, ironWall, ironWallLarge, steelWall, steelWallLarge;
    //units

    public static void load() {
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

        omniDrill = new BeamDrill("omni-drill"){{ 
            requirements(Category.production, with(LaiItems.lithium, 40));
            consumePower(0.15f);
            drillTime = 350f;
            tier = 3;
            size = 3;
            range = 5;
            researchCost = with(LaiItems.lithium, 10);
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

            powerConsume = 1300f;
        }};

        miniReactor = new UpgradeRector("mini-nuclear-reactor"){{
            requirements(Category.power, with(LaiItems.platinum, 20, LaiItems.lithium, 50, Items.silicon, 30));
            size = 3;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.24f;

            outLiquid = waterRadioction;
            liquidTick = 0.05f;
            radius = 40f;
            radiactionDamage = 1f;

            health = 700;
            itemDuration = 360f;
            powerProduction = 15f;
            heating = 0.02f;
            explosionRadius = 25;
            squareSprite = false;
            consumeItem(LaiItems.uranium, 2);
            consumeLiquid(LaiLiquids.freshwater, heating / coolantPower).update(false);
        }};
 
/*        earthGenerator = new ThermalGenerator("earth-generator"){{
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
        }};*/


		//endPower
		//defense
		//endDefens	
		//storage
		coreCaser = new CoreBlock("core-caser"){{
            requirements(Category.effect, with(LaiItems.lithium, 1200, silicon, 500, graphite, 1000, iron, 300));
            unitType = LaiUnits.stars;
            health = 6000;
            itemCapacity = 4000;
            size = 4;
            thrusterLength = 34/4f;
            armor = 5f;
            alwaysUnlocked = true;
            incinerateNonBuildable = true;
            squareSprite = false;
            isFirstTier = true;
            //TODO should this be higher?
            buildCostMultiplier = 0.7f;

            unitCapModifier = 10;
            researchCostMultiplier = 0.07f;
        }};
        coreActor = new CoreBlockLiquid("core-actors"){{
            requirements(Category.effect, with(LaiItems.lithium, 1200, LaiItems.platinum, 200));
            unitType = LaiUnits.arom;
            health = 12000;
            itemCapacity = 15000;
            size = 5;
            thrusterLength = 40/4f;
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

        tnt = new Dynamite("tnt"){{
            explosionDelay = 180f; // 3 секунды
            explosionRadius = 60f;
            explosionDamage = 150f;

            requirements(Category.effect, with(Items.lead, 10));
            size = 1;
            consumePower(0.1f); // нужна минимальная энергия
        }};

		//endStorage

		//endSampenat

		int wallHealthMultiplier = 4;
		//walls
		lithiumWall = new PhotonShieldWall("lithium-wall"){{
            scaledHealth = 150 * wallHealthMultiplier;
            size = 1;
            requirements(Category.defense, with(lithium, 6));
        }};
		lithiumWallLarge = new PhotonShieldWall("lithium-wall-large"){{
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
