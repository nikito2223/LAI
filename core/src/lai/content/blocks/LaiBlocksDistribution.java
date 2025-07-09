package lai.content.blocks;

import arc.graphics.*; 
import arc.struct.*;
import arc.math.*;

import mindustry.gen.*;
import mindustry.graphics.*; 
import mindustry.type.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.*;
import mindustry.content.*;

import lai.content.*;

import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

import lai.world.blocks.distributor.*;

public class LaiBlocksDistribution {
	public static Block 
	lithiumDuct, lithiumRouter, lithiumJunction, lithiumBridgeItem, ductLiquid, magneticDuct;

	public static void load(){
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
        magneticDuct = new Conveyor("magnetic-duct"){{
            requirements(Category.distribution, with(Items.surgeAlloy, 1, Items.tungsten, 1));
            health = 230;
            //TODO different base speed/item capacity?
            speed = 10f / 60f;
            itemCapacity = 5;


            hasPower = true;
            consumesPower = true;
            conductivePower = true;

            underBullets = true;

            consumePower(2f / 60f);
        }};
	}
}