package lai.content.blocks;

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
import mindustry.content.*;

import lai.content.*;

import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;

public class LaiEnvironmentBlocks {
	//Environment
	public static Block
	freshwater, fueloli, 
    darkblueSandfreshwater, deepfreshwater, lava, darkGreenStoneCrater,
    darkgreenStoneWall, darkBlueSandWall, pinksandWall, iceGreenWall, redstoneWall,
    darkgreenStone, darkBlueSand, pinksand, redstone, iceGreen, darkgreenStonePlates;
    //Ore
    public static Block
    oreLithium, orePlatinum, oreIron, oreRhodium, wallOrelithium;
    //Boulder
    public static Block
    blueBoulder, redstoneBoulder;

    public static Block floor = new Floor("foo");

    public static void load() {
    	//Ore
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
        oreIron = new OreBlock("ore-iron", LaiItems.iron){{
            oreDefault = false;
            oreThreshold = 0.864f;
            oreScale = 35.904762f;
            mapColor = Color.valueOf("b7c5cc");
        }};
        oreRhodium = new OreBlock("ore-rhodium", LaiItems.rhodium){{
            oreDefault = false;
            oreThreshold = 0.864f;
            oreScale = 22.504762f;
            mapColor = Color.valueOf("a0ecbd");
        }};
    	wallOrelithium = new OreBlock("ore-wall-lithium", LaiItems.lithium){{
            wallOre = true;
        }};
        //Environment
        deepfreshwater = new Floor("deep-fresh-water"){{
            speedMultiplier = 0.2f;
            variants = 0;
            liquidDrop = LaiLiquids.freshwater;
            liquidMultiplier = 1.5f;
            isLiquid = true;
            status = StatusEffects.wet;
            statusDuration = 120f;
            drownTime = 200f;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        freshwater = new Floor("shallow-fresh-water"){{
            speedMultiplier = 0.5f;
            variants = 0;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = LaiLiquids.freshwater;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        darkblueSandfreshwater = new ShallowLiquid("dark-blue-sand-distilled-water"){{
            speedMultiplier = 0.8f;
            statusDuration = 50f;
            albedo = 0.9f;
            supportsOverlay = true;
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

        darkgreenStone = new Floor("dark-green-Stone"){{
            variants = 3;
            mapColor = Color.valueOf("16182c");
        }};
        darkgreenStonePlates = new Floor("dark-green-Stone-plates"){{
            variants = 3;
        }};

        darkGreenStoneCrater = new SteamVent("dark-green-Stone-Crater"){{
            parent = blendGroup = darkgreenStone;
            attributes.set(Attribute.steam, 1f); 
            //blendGroup = darkgreenStone;
        }};
        darkBlueSand = new Floor("dark-blue-sand") {{
            itemDrop = Items.sand;
            mapColor = Color.valueOf("1f2f37");
        }};

        pinksand = new Floor("pink-sand") {{
            itemDrop = Items.sand;
            mapColor = Color.valueOf("8664f4");
        }};

        redstone = new Floor("red-stone") {{
            mapColor = Color.valueOf("cf8634");
        }};

        iceGreen = new Floor("green-ice"){{
            mapColor = Color.valueOf("24e0ba");
        }};
        iceGreenWall = new StaticWall("green-ice-wall"){{
            floor = iceGreen;
            iceGreen.asFloor().wall = this;
            albedo = 0.6f;
        }};
        darkgreenStoneWall = new StaticWall("dark-green-Stone-Wall") {{
            floor = darkgreenStone;
            darkgreenStone.asFloor().wall = this;
            albedo = 0.6f;
            mapColor = Color.valueOf("1f2f37");
        }};
        redstoneWall = new StaticWall("red-stone-wall") {{
            floor = redstone;
            redstone.asFloor().wall = this;
            albedo = 0.6f;
        }};
        pinksandWall = new StaticWall("pink-sand-wall") {{
            floor = pinksand;
            pinksand.asFloor().wall = this;
            albedo = 0.6f;
        }};
        darkBlueSandWall = new StaticWall("dark-blue-sand-wall") {{
            floor = darkBlueSand;
            darkgreenStone.asFloor().wall = this;
            albedo = 0.6f;
        }};
        blueBoulder = new Prop("blue-Boulder"){{
            variants = 2;
            darkgreenStone.asFloor().decoration = this;
        }};
        redstoneBoulder = new Prop("blue-boulder"){{
            variants = 2;
            redstone.asFloor().decoration = this;
        }};
    }
}