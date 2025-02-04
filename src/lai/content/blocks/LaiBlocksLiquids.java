package lai.content.blocks;

import arc.graphics.*;
import arc.struct.*;

import lai.content.LaiBlocks;
import lai.world.blocks.campaign.*;
import lai.content.*;
import lai.graphics.*; 

import mindustry.content.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.liquid.*;

import mindustry.type.*;
import mindustry.world.*;
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

public class LaiBlocksLiquids {

	public static Block
    lithiumPump, lithiumconduit, lithiumconduitrouter, lithiumBridgeLiquid;

	public static void load() {
        lithiumPump = new Pump("lithium-pump"){{
            requirements(Category.liquid, with(lithium, 10));
            pumpAmount = 0.40f;
            liquidCapacity = 40f;
            size = 2;
        }};
            lithiumconduit = new Conduit("lithium-conduit"){{
            requirements(Category.liquid, with(lithium, 1));
            health = 60;
            liquidCapacity = 17f;
            liquidPressure = 1.123f;
        }};
            lithiumconduitrouter = new LiquidRouter("lithium-conduit-router"){{
            requirements(Category.liquid, with(lithium, 4));
            liquidCapacity = 20f;
            underBullets = true;
            size = 1;
            solid = false;
        }};
            lithiumBridgeLiquid = new LiquidBridge("lithium-bridge-liquids"){{
            requirements(Category.liquid, with(lithium, 4, Items.graphite, 8));
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 6;
            hasPower = false;
        }};
	}
}