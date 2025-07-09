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
import lai.graphics.*;

import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;

public class LaiEnvironmentBlocks {
	//Environment
	public static Block
	freshwater, fueloli, plazme,
    darkblueSandfreshwater, deepfreshwater, lava, darkGreenStoneCrater,
    darkgreenStoneWall, darkBlueSandWall, pinksandWall, iceGreenWall, redstoneWall, darkpinksand, bluedirt, sporebark, pinkcrystalspore,
    darkgreenStone, darkBlueSand, pinksand, redstone, iceGreen, darkgreenStonePlates, darkpinksandWall, bluedirtWall, sporebarkWall, pinkcrystalsporeWall;
    //Ore
    public static Block
    oreLithium, orePlatinum, oreIron, oreRhodium, wallOrelithium, wallOreSurgeAllow;
    //Boulder
    public static Block
    blueBoulder, redstoneBoulder, pinkcrystal;

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
        wallOreSurgeAllow = new OreBlock("ore-wall-surge-allow", LaiItems.rawKinetic){{
            wallOre = true;
            oreThreshold = 0.882f;
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
            variants = 0;
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

        plazme = new Floor("deep-plazme"){{
            status = StatusEffects.tarred;
            statusDuration = 240f;
            speedMultiplier = 0.19f;
            variants = 0;
            liquidDrop = LaiLiquids.plazme;
            isLiquid = true;
            cacheLayer = CLayer.plazme;
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


        darkpinksand = new Floor("dark-pink-sand"){{
            mapColor = Color.valueOf("4c3254");
            variants = 3;
        }};

        bluedirt = new Floor("blue-dirt"){{
            mapColor = Color.valueOf("165289");
            variants = 3;
        }};
        sporebark = new Floor("spore-bark"){{
            mapColor = Color.valueOf("313a1a");
            variants = 3;
        }};

        pinkcrystalspore = new Floor("pink-crystal-spore"){{
            mapColor = Color.valueOf("ff5288");
            variants = 3;
        }};

        sporebarkWall = new StaticWall("spore-bark-wall"){{
            floor = sporebark;
            sporebark.asFloor().wall = this;
            albedo = 0.6f;
        }};

        pinkcrystalsporeWall = new StaticWall("pink-crystal-spore-wall"){{
            floor = pinkcrystalspore;
            pinkcrystalspore.asFloor().wall = this;
            albedo = 0.9f;
        }};

        bluedirtWall = new StaticWall("blue-dirt-wall"){{
            floor = bluedirt;
            bluedirt.asFloor().wall = this;
            albedo = 0.6f;
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
        darkpinksandWall = new StaticWall("dark-pink-sand-wall"){{
            floor = darkpinksand;
            darkpinksand.asFloor().wall = this;
            albedo = 0.6f;
        }};

        blueBoulder = new Prop("blue-Boulder"){{
            variants = 2;
            darkgreenStone.asFloor().decoration = this;
        }};
        redstoneBoulder = new Prop("red-boulder"){{
            variants = 2;
            redstone.asFloor().decoration = this;
        }};
        pinkcrystal = new TallBlock("pink-crystal"){{
            variants = 2;
            clipSize = 128f;
        }};
    }
}