package lai.content.blocks;

import arc.graphics.*;
import arc.struct.*;

import lai.content.LaiBlocks;
import lai.world.blocks.campaign.*;
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
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

public class LaiBlocksCrafting {

	public static Block
    pyratitecrucible, smelterplantupdated, siliconarcburners, coalpress, graphitepress, airFiliter, vanadiaSmelter; 

	public static void load() {
		pyratitecrucible = new GenericCrafter("pyratite-crucible"){{
            scaledHealth = 40;
            size = 3;
            craftTime = 120f;
            itemCapacity = 55;
            consumePower(8f);
            updateEffect = Fx.plasticburn;
            hasLiquids = true;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));

            consumeLiquid(Liquids.water, 0.1f);
            consumeItems(with(sand, 20, lead, 15, coal, 20));
            outputItems = with(pyratite, 8);
            requirements(Category.crafting, with(lead, 360, copper, 300, silicon, 50));
        }};
		airFiliter = new GenericCrafter("air-filiter"){{
            scaledHealth = 440;
            size = 4;
            craftTime = 120f;
            itemCapacity = 16;
            consumePower(8f);
            updateEffect = Fx.plasticburn;
            hasLiquids = true;
            consumeLiquid(carbondioxide, 0.8f);
            consumeItems(with(coal, 8));
            outputLiquid = new LiquidStack(air, 0.8f);
            requirements(Category.crafting, with(lithium, 30, silicon, 50));
        }};
        smelterplantupdated = new GenericCrafter("smelter-plant-updated") {{
            health = 140;
            liquidCapacity = 10;
            size = 3;
            hasPower = true;
            itemCapacity = 10;
            hasLiquids = true;
            hasItems = true;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(), new DrawDefault());
            craftTime = 80;
            updateEffect = Fx.plasticburn;
            consumePower(3.2f);
            consumeItems(ItemStack.with(Items.scrap, 5));
            requirements(Category.crafting, ItemStack.with(Items.silicon, 15, Items.lead, 15, Items.graphite, 15));
            outputLiquid = new LiquidStack(Liquids.slag, 18f / 60f);
        }};
        siliconarcburners = new GenericCrafter("silicon-arc-burners"){{
            scaledHealth = 40;
            size = 3;
            craftTime = 120f;
            itemCapacity = 100;
            consumePower(8f);
            updateEffect = Fx.plasticburn;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            consumeItems(with(sand, 20, coal, 20));
            outputItems = with(silicon, 3);
            requirements(Category.crafting, with(lithium, 50, graphite, 50));
        }};
        coalpress = new GenericCrafter("coal-press"){{
            requirements(Category.crafting, with(lithium, 20));
            craftEffect = Fx.coalSmeltsmoke;
            outputItem = new ItemStack(Items.coal, 4);
            craftTime = 20f;
            size = 2;
            hasPower = hasItems = hasLiquids = true;
            rotateDraw = false;
            consumeLiquid(fueloli, 0.1f);
            consumePower(0.7f);
        }};
        graphitepress = new GenericCrafter("graphite-press"){{
            scaledHealth = 40;
            size = 2;
            craftTime = 30f;
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
            itemCapacity = 40;
            updateEffect = Fx.plasticburn;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            consumeItems(with(lithium, 10, coal, 20));
            outputItems = with(vanadium, 1);
            requirements(Category.crafting, with(lithium, 30, graphite, 30));
        }};
	}
}