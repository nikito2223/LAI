package lai.content.blocks;

import arc.graphics.*;
import arc.struct.*;

import lai.content.LaiBlocks;
import lai.world.blocks.production.*;
import lai.content.*;
import lai.graphics.*; 

import mindustry.content.*;
import mindustry.world.blocks.production.*;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.gen.*;
import mindustry.graphics.*; 
import mindustry.world.consumers.ConsumeLiquid;

import arc.math.*;
import mindustry.*;
import mindustry.entities.*; 
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.DrawPart.*;
import mindustry.entities.part.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
//My Import! aaaaaaa adf
import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;
import static mindustry.content.Liquids.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

public class LaiBlocksCrafting {
    //Block - GenericCrafter 
    public static Block graphitepress, coalpress, siliconarcburners, steelFactory, vanadiaSmelter, surgeChamber;
    //Block - liquid
    public static Block oxygenFiliter, crusher, atmosphericExtractor, heliophanusMixer, fabrimat;

	public static void load() {

		oxygenFiliter = new GenericCrafter("oxygen-filiter"){{
            scaledHealth = 440;
            size = 4;
            craftTime = 120f;
            itemCapacity = 16;
            consumePower(8f);
            updateEffect = Fx.plasticburn;
            hasLiquids = true;
            squareSprite = false;
            consumeLiquid(carbondioxide, 0.8f);
            drawer = new DrawMulti(new DrawRegion("-bottom"), 
                new DrawLiquidTile(oxygen), 
                new DrawLiquidTile(carbondioxide){{
                    drawLiquidLight = true;
                }}, 
                new DrawDefault()
            );
            outputLiquid = new LiquidStack(oxygen, 0.2f);
            requirements(Category.crafting, with(lithium, 30, silicon, 50));
        }};

        atmosphericExtractor = new AtmosphericExtractor("atmospheric-extractor"){{
            requirements(Category.crafting, with(lithium, 30, silicon, 50));
            plans = Seq.with(
                new LiquidPlan(helium, 1.2f, 1.1f * 60f),
                new LiquidPlan(neon, 0.9f, 0.8f * 60f),
                new LiquidPlan(ozone, 0.9f, 1f * 60f)
            );
            squareSprite = false;
            drawer = new DrawMulti(new DrawRegion("-bottom"),
                new DrawLiquidTile(helium), 
                new DrawLiquidTile(neon),
                new DrawLiquidTile(ozone), 
                new DrawBlurSpin("-rotate", 0.6f * 9f){{
                    blurThresh = 0.01f;
                }}, new DrawDefault());
            size = 3;
            consumePower(1.5f);
        }};

        heliophanusMixer = new GenericCrafter("heliophanus-mixer"){{
            requirements(Category.crafting, with(Items.lead, 35, Items.silicon, 40, lithium, 80));
            outputLiquid = new LiquidStack(LaiLiquids.heliophanus, 12f / 60f);
            size = 2;
            hasPower = true;
            hasItems = false;
            hasLiquids = true;
            rotate = false;
            solid = true;
            outputsLiquid = true;
            envEnabled = Env.any;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(LaiLiquids.helium), new DrawLiquidTile(Liquids.cryofluid){{drawLiquidLight = false;}}, new DrawLiquidTile(LaiLiquids.heliophanus){{drawLiquidLight = true;}}, new DrawDefault());
            liquidCapacity = 24f;
            craftTime = 120f;
            lightLiquid = LaiLiquids.heliophanus;
            squareSprite = false;
            consumePower(1f);
            consumeLiquid(LaiLiquids.helium, 12f / 60f);
            consumeLiquid(Liquids.cryofluid, 12f / 60f);
        }};
        fabrimat = new GenericCrafter("fabrimat"){{
            requirements(Category.crafting, with(Items.lead, 35, Items.silicon, 40, lithium, 80));
            craftTime = 80f;
            size = 3;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(), new DrawDefault());
            consumeItems(with(lead, 2, silicon, 5, surgeAlloy, 3, lithium, 5, steel, 3));
            outputLiquid = new LiquidStack(LaiLiquids.mattery, 12f / 60f);
            consumePower(1f);
        }};

        crusher = new GenericCrafter("crusher"){{
            requirements(Category.crafting, with(Items.silicon, 50, Items.graphite, 40));
            size = 3;

            researchCostMultiplier = 1.2f;
            craftTime = 10f;
            rotate = true;
            invertFlip = true;
            group = BlockGroup.liquids;
            itemCapacity = 0;

            liquidCapacity = 50f;
            squareSprite = false;
            consumeLiquid(LaiLiquids.freshwater, 10f / 60f);
            consumePower(1f); 

            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(LaiLiquids.freshwater, 2f),
                new DrawBubbles(Color.valueOf("7693e3")){{
                    sides = 10;
                    recurrence = 3f;
                    spread = 6;
                    radius = 1.5f;
                    amount = 20;
                }},
                new DrawRegion(),
                new DrawLiquidOutputs(),
                new DrawGlowRegion(){{
                    alpha = 0.7f;
                    color = Color.valueOf("c4bdf3");
                    glowIntensity = 0.3f;
                    glowScale = 6f;
                }}
            );

            ambientSound = Sounds.electricHum;
            ambientSoundVolume = 0.08f;

            regionRotated1 = 3;
            outputLiquids = LiquidStack.with(LaiLiquids.oxygen, 3f / 60, Liquids.hydrogen, 2f / 60);
            liquidOutputDirections = new int[]{1, 3};
        }};

        siliconarcburners = new GenericCrafter("silicon-arc-burners"){{
            scaledHealth = 40;
            squareSprite = false;
            size = 3;
            craftTime = 60f;
            itemCapacity = 100;
            consumePower(1.4f);
            updateEffect = Fx.plasticburn;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            consumeItems(with(sand, 3, graphite, 6));
            outputItems = with(silicon, 2);
            requirements(Category.crafting, with(lithium, 50, graphite, 50));
        }};
        coalpress = new GenericCrafter("coal-press"){{
            requirements(Category.crafting, with(lithium, 20));
            craftEffect = Fx.coalSmeltsmoke;
            squareSprite = false;
            outputItem = new ItemStack(Items.coal, 4);
            craftTime = 20f;
            size = 2;
            hasPower = hasItems = hasLiquids = true;
            rotateDraw = false;
            consumeLiquid(oil, 0.1f);
            consumePower(0.4f);
        }};
        graphitepress = new GenericCrafter("graphite-press"){{
            scaledHealth = 40;
            size = 2;
            craftTime = 30f;
            squareSprite = false;
            itemCapacity = 40;
            updateEffect = Fx.plasticburn;
            consumeItems(with(coal, 3));
            outputItems = with(graphite, 2);
            requirements(Category.crafting, with(lithium, 70));
        }};
        vanadiaSmelter = new GenericCrafter("vanadia-smelter"){{
            scaledHealth = 60;
            size = 2;
            craftTime = 20f;
            squareSprite = false;
            itemCapacity = 40;
            updateEffect = Fx.plasticburn;
            consumeItems(with(iron, 2, silicon, 1, coal, 1));
            outputItems = with(vanadium, 1);
            requirements(Category.crafting, with(lithium, 30, graphite, 30));
        }};
        steelFactory = new GenericCrafter("steel-fabric"){{
            requirements(Category.crafting, with(iron, 100, lead, 120, graphite, 80));
            size = 2;
            squareSprite = false;
            scaledHealth = 60;
            craftEffect = Fx.turbinegenerate;
            craftTime = 120f;
            consumeItems(with(
                iron, 2,
                coal, 1
            ));
            itemCapacity = 30;
            outputItems = with(steel, 1);
            consumePower(7f);
        }};
        surgeChamber = new GenericCrafter("surge-chamber"){{
            requirements(Category.crafting, with(Items.silicon, 100, Items.graphite, 80));

            size = 3;   
            squareSprite = false;

            hasLiquids = true;
            itemCapacity = 20;
            craftTime = 60f * 1.5f;
            liquidCapacity = 80f * 5;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.9f;
            outputItems = with(surgeAlloy, 1, lead, 1);

            craftEffect = new RadialEffect(LaiFx.kineticChargeFx, 4, 90f, 5f);

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawCircles(){{
                color = Color.valueOf("ffc073").a(0.24f);
                strokeMax = 2.5f;
                radius = 10f;
                amount = 3;
            }}, new DrawLiquidRegion(Liquids.cryofluid), new DrawDefault(), new DrawHeatInput(),
            new DrawHeatRegion(){{
                color = Color.valueOf("ff6060ff");
            }},
            new DrawHeatRegion("-vents"){{
                color.a = 1f;
            }});
            consumeItems(with(rawKinetic, 1));
            consumeLiquid(Liquids.cryofluid, 40f / 60f);
            consumePower(4f);
        }};

	}
    
}