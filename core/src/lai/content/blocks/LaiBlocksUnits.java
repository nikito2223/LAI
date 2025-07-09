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
import mindustry.world.blocks.units.*;
import lai.world.blocks.units.*;
import lai.content.*;

import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

public class LaiBlocksUnits {
	public static Block 
	/*replicator*/ sparkFactory, sparkReconstructor; 

	public static void load(){
/*		replicator = new Replicator("replicator"){{
            requirements(Category.units, with(LaiItems.lithium, 320, Items.silicon, 200));
            consumePower(1.5f);
            size = 2;
            squareSprite = false;
        }};*/
        //factory
        sparkFactory = new UnitFactory("spark-factory"){{
            requirements(Category.units, with(Items.silicon, 80, lithium, 60));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(LaiUnits.exarch, 60f * 35f, with(lithium, 30, Items.silicon, 20)));
            researchCost = with(lithium, 320, graphite, 80, silicon, 80);
            regionSuffix = "-dark";
            fogRadius = 3;
            consumePower(2f);
        }};
        sparkReconstructor = new Reconstructor("spark-reconstructor"){{
            requirements(Category.units, with(Items.silicon, 180, lithium, 60, platinum, 70));
            size = 3;

            constructTime = 60f * 10f;
            upgrades.addAll(
                new UnitType[]{LaiUnits.exarch, LaiUnits.archon}
            );
            consumeItems(with(Items.silicon, 70, platinum, 40));
            researchCost = with(lithium, 320, graphite, 80, silicon, 80);
            consumePower(2.4f);
        }};
	}
}