package lai.content.blocks;

import arc.graphics.*;
import arc.struct.*;

import lai.content.LaiBlocks;
import lai.world.blocks.campaign.*;
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

//My Import! aaaaaaa adf
import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;
import static mindustry.content.Liquids.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

public class LaiBlocksCrafting {

	public static Block
    siliconarcburners, coalpress, graphitepress, oxygenFiliter, vanadiaSmelter, steelFactory, crusher, atmosphericExtractor;


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
            outputLiquid = new LiquidStack(oxygen, 0.8f);
            requirements(Category.crafting, with(lithium, 30, silicon, 50));
        }};

        atmosphericExtractor = new AtmosphericExtractor("atmospheric-extractor"){{
            requirements(Category.crafting, with(lithium, 30, silicon, 50));
            plans = Seq.with(
                new LiquidPlan(helium, 1.2f, 1.1f * 60f),
                new LiquidPlan(neon, 0.9f, 0.8f * 60f),
                new LiquidPlan(ozone, 0.9f, 1f * 60f)
            );
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
            craftTime = 120f;
            itemCapacity = 100;
            consumePower(4f);
            updateEffect = Fx.plasticburn;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            consumeItems(with(sand, 20, coal, 20));
            outputItems = with(silicon, 3);
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
            consumeItems(with(coal, 15));
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
	}
}