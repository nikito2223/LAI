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
    siliconarcburners, coalpress, graphitepress, airFiliter, vanadiaSmelter; 

	public static void load() {
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