package lai.maps.gen;

import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.ai.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.maps.generators.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import lai.content.*;
import mindustry.world.blocks.environment.*;
import mindustry.ai.BaseRegistry.*;
import static mindustry.Vars.*;
import mindustry.world.meta.*;
import lai.content.blocks.*;
import lai.content.*;

public class MathexisPlanetGenerator extends PlanetGenerator{
    public float heightScl = 2.2f, octaves = 6, persistence = 1.4f, heightPow = 3.6f, heightMult = 1.6f;
    float scl = 5f;

    float waterOffset = 0.10f;

    //TODO inline/remove
    public static float arkThresh = 0.28f, arkScl = 0.83f;
    public static int arkSeed = 7, arkOct = 2;
    public static float liqThresh = 1.64f, liqScl = 67f, redThresh = 3.1f, noArkThresh = 0.3f;
    public static int crystalSeed = 8, crystalOct = 2;
    public static float crystalScl = 0.9f, crystalMag = 0.3f;
    public static float airThresh = 0.13f, airScl = 10;
    

    Block[][] terrain = 
    {
        {Blocks.snow, Blocks.snow, Blocks.ice, LaiEnvironmentBlocks.darkgreenStone, LaiEnvironmentBlocks.darkgreenStone, Blocks.ice, Blocks.snow, Blocks.ice, LaiEnvironmentBlocks.darkBlueSand},
        {Blocks.ice, LaiEnvironmentBlocks.pinksand, LaiEnvironmentBlocks.pinksand, Blocks.snow, LaiEnvironmentBlocks.darkBlueSand, LaiEnvironmentBlocks.pinksand, Blocks.ice, LaiEnvironmentBlocks.pinksand, LaiEnvironmentBlocks.darkBlueSand, LaiEnvironmentBlocks.darkpinksand},
        {Blocks.ice, LaiEnvironmentBlocks.freshwater, LaiEnvironmentBlocks.redstone, LaiEnvironmentBlocks.freshwater, LaiEnvironmentBlocks.deepfreshwater, LaiEnvironmentBlocks.freshwater, Blocks.ice, Blocks.ice, LaiEnvironmentBlocks.redstone, Blocks.ice, LaiEnvironmentBlocks.pinksand, LaiEnvironmentBlocks.darkpinksand},
        {LaiEnvironmentBlocks.deepfreshwater, LaiEnvironmentBlocks.deepfreshwater, LaiEnvironmentBlocks.redstone, Blocks.snow, Blocks.ice, LaiEnvironmentBlocks.darkBlueSand, LaiEnvironmentBlocks.darkpinksand, LaiEnvironmentBlocks.darkBlueSand, LaiEnvironmentBlocks.darkpinksand, Blocks.snow},
        {Blocks.ice, LaiEnvironmentBlocks.redstone, Blocks.ice, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.ice, Blocks.snow, LaiEnvironmentBlocks.darkgreenStone, LaiEnvironmentBlocks.darkgreenStone, LaiEnvironmentBlocks.pinkcrystalspore, LaiEnvironmentBlocks.pinkcrystalspore},
        {LaiEnvironmentBlocks.deepfreshwater, LaiEnvironmentBlocks.darkpinksand, LaiEnvironmentBlocks.sporebark, Blocks.snow, LaiEnvironmentBlocks.darkBlueSand, Blocks.snow, Blocks.ice, LaiEnvironmentBlocks.pinkcrystalspore, LaiEnvironmentBlocks.pinkcrystalspore, LaiEnvironmentBlocks.sporebark},
        {LaiEnvironmentBlocks.sporebark, LaiEnvironmentBlocks.darkgreenStone, Blocks.ice, LaiEnvironmentBlocks.freshwater, Blocks.ice, Blocks.snow, LaiEnvironmentBlocks.darkpinksand, LaiEnvironmentBlocks.darkpinksand, LaiEnvironmentBlocks.sporebark, LaiEnvironmentBlocks.sporebark, LaiEnvironmentBlocks.pinkcrystalspore},
        {LaiEnvironmentBlocks.darkgreenStone, LaiEnvironmentBlocks.darkgreenStone, LaiEnvironmentBlocks.freshwater, LaiEnvironmentBlocks.deepfreshwater, LaiEnvironmentBlocks.freshwater, LaiEnvironmentBlocks.darkBlueSand, LaiEnvironmentBlocks.pinksand, LaiEnvironmentBlocks.sporebark, LaiEnvironmentBlocks.pinkcrystalspore}
    };

    @Override
    public void generateSector(Sector sector){
        //no bases right now
    }

    float water = 2f / terrain[0].length;

    @Override
    public float getHeight(Vec3 position){
        float height = rawHeight(position);
        return Math.max(height, water);
    }

    @Override
    public Color getColor(Vec3 position){
        Block block = getBlock(position);

        //more obvious color
        if(block == LaiEnvironmentBlocks.darkgreenStone) block = LaiEnvironmentBlocks.darkBlueSand;
        //TODO this might be too green
        //if(block == Blocks.beryllicStone) block = Blocks.arkyicStone;

        return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
    }

    @Override
    public float getSizeScl(){
        //TODO should sectors be 600, or 500 blocks?
        return 2000 * 1.07f * 6f / 5f;
    }

    float rawHeight(Vec3 position){
        position = Tmp.v33.set(position).scl(scl);
        return (Mathf.pow(Simplex.noise3d(seed, 7, 0.5f, 1f/3f, position.x, position.y, position.z), 2.3f) + waterOffset) / (1f + waterOffset);
    }

        float rawTemp(Vec3 position){
        return position.dst(0, 0, 1)*2.2f - Simplex.noise3d(seed, 9, 0.54f, 1.4f, 10f + position.x, 10f + position.y, 10f + position.z) * 2.9f;
    }

    Block getBlock(Vec3 position){
        float ice = rawTemp(position);
        Tmp.v32.set(position);

        float height = rawHeight(position);
        Tmp.v31.set(position);
        height *= 1.2f;
        height = Mathf.clamp(height);

        Block result = terrain[Mathf.clamp((int)(height * terrain.length), 0, terrain.length - 1)][Mathf.clamp((int)(height * terrain.length), 0, terrain.length - 1)];

        if(ice < 0.3 + Math.abs(Ridged.noise3d(seed + crystalSeed, position.x + 4f, position.y + 8f, position.z + 1f, crystalOct, crystalScl)) * crystalMag){
            return LaiEnvironmentBlocks.darkgreenStone;
        }

        if(ice < 0.6){
            if(result == LaiEnvironmentBlocks.darkgreenStone || result == LaiEnvironmentBlocks.sporebark || result == LaiEnvironmentBlocks.pinkcrystalspore){
                //TODO bio(?) luminescent stuff? ice?
                return LaiEnvironmentBlocks.darkBlueSand; //TODO perhaps something else.
            }
        }

        position = Tmp.v32;

        //TODO tweak this to make it more natural
        //TODO edge distortion?
        if(ice < redThresh - noArkThresh && Ridged.noise3d(seed + arkSeed, position.x + 2f, position.y + 8f, position.z + 1f, arkOct, arkScl) > arkThresh){
            //TODO arkyic in middle
            result = LaiEnvironmentBlocks.bluedirt;
        }

        if(ice > redThresh){
            result = LaiEnvironmentBlocks.redstone;
        }else if(ice > redThresh - 0.4f){
            //TODO this may increase the amount of pinkcrystalspore, but it's too obvious a transition.
            result = LaiEnvironmentBlocks.pinkcrystalspore;
        }

        return result;
    }

    @Override
    public void genTile(Vec3 position, TileGen tile){
        tile.floor = getBlock(position);

        if(tile.floor == LaiEnvironmentBlocks.darkgreenStone && rand.chance(0.01)){
            tile.floor = LaiEnvironmentBlocks.darkGreenStoneCrater;
        }

        tile.block = tile.floor.asFloor().wall;

        if(Ridged.noise3d(seed + 1, position.x, position.y, position.z, 2, airScl) > airThresh){
            tile.block = Blocks.air;
        }

        //TODO only certain places should have carbon stone...
        if(Ridged.noise3d(seed + 2, position.x, position.y + 4f, position.z, 3, 6f) > 0.6){
            tile.floor = LaiEnvironmentBlocks.darkpinksand;
        }
    }

    @Override
    protected void generate(){
        float temp = rawTemp(sector.tile.v);

        if(temp > 0.7){

            pass((x, y) -> {
                if(floor != Blocks.ice){
                    float noise = noise(x + 782, y, 8, 0.8f, 150f, 0.5f);
                    if(noise > 0.62f){
                        if(noise > 0.635f){
                            floor = LaiEnvironmentBlocks.deepfreshwater;
                        }else{
                            floor = LaiEnvironmentBlocks.iceGreen;
                        }
                        ore = Blocks.air;
                    }

                    //TODO this needs to be tweaked
                    if(noise > 0.55f && floor == LaiEnvironmentBlocks.pinksand){
                        floor = LaiEnvironmentBlocks.pinkcrystalspore;
                    }
                }
            });
        }

        cells(4);

        //regolith walls for more dense terrain
        pass((x, y) -> {
            if(floor == LaiEnvironmentBlocks.darkgreenStone && noise(x, y, 3, 0.4f, 13f, 1f) > 0.59f){
                block = LaiEnvironmentBlocks.darkgreenStoneWall;
            }
        });

        float length = width/2.8f;
        Vec2 trns = Tmp.v1.trns(rand.random(360f), length);
        int
        spawnX = (int)(trns.x + width/2f), spawnY = (int)(trns.y + height/2f),
        endX = (int)(-trns.x + width/2f), endY = (int)(-trns.y + height/2f);
        float maxd = Mathf.dst(width/2f, height/2f);

        erase(spawnX, spawnY, 15);
        brush(pathfind(spawnX, spawnY, endX, endY, tile -> (tile.solid() ? 300f : 0f) + maxd - tile.dst(width/2f, height/2f)/10f, Astar.manhattan), 9);
        erase(endX, endY, 15);

        //arkycite
        pass((x, y) -> {
            if(floor != LaiEnvironmentBlocks.pinksand) return;

            //TODO bad
            if(Math.abs(noise(x, y + 500f, 5, 0.6f, 40f, 1f) - 0.5f) < 0.09f){
                floor = Blocks.snow;
            }

            if(nearWall(x, y)) return;

            float noise = noise(x + 300, y - x*1.6f + 100, 4, 0.8f, liqScl, 1f);

            if(noise > liqThresh){
                floor = Blocks.tar;
            }
        });

        median(2, 0.6, Blocks.tar);

        blend(Blocks.tar, Blocks.snow, 4);

        //TODO may overwrite floor blocks under walls and look bad
        blend(LaiEnvironmentBlocks.deepfreshwater, Blocks.yellowStonePlates, 4);

        distort(10f, 12f);
        distort(5f, 7f);

        //does arkycite need smoothing?
        median(2, 0.6, Blocks.tar);

        //smooth out slag to prevent random 1-tile patches
        median(3, 0.6, LaiEnvironmentBlocks.deepfreshwater);

        pass((x, y) -> {
            //rough rhyolite
            if(noise(x, y + 600 + x, 5, 0.86f, 60f, 1f) < 0.41f && floor == Blocks.rhyolite){
                floor = Blocks.roughRhyolite;
            }

            if(floor == LaiEnvironmentBlocks.deepfreshwater && Mathf.within(x, y, spawnX, spawnY, 70f + noise(x, y, 6, 0.8f, 9f, 15f))){
                floor = Blocks.yellowStonePlates;
            }

            if((floor == Blocks.tar || floor == Blocks.snow) && block.isStatic()){
                block = Blocks.snowWall;
            }

            float max = 0;
            for(Point2 p : Geometry.d8){
                //TODO I think this is the cause of lag
                max = Math.max(max, world.getDarkness(x + p.x, y + p.y));
            }
            if(max > 0){
                block = floor.asFloor().wall;
                if(block == Blocks.air) block = LaiEnvironmentBlocks.sporebarkWall;
            }

            if(floor == Blocks.yellowStonePlates && noise(x + 78 + y, y, 3, 0.8f, 6f, 1f) > 0.44f){
                floor = LaiEnvironmentBlocks.sporebark;
            }

            if(floor == Blocks.redStone && noise(x + 78 - y, y, 4, 0.73f, 19f, 1f) > 0.63f){
                floor = Blocks.denseRedStone;
            }
        });

        inverseFloodFill(tiles.getn(spawnX, spawnY));

        //TODO veins, blend after inverse flood fill?
        blend(Blocks.redStoneWall, Blocks.denseRedStone, 4);

        //make sure enemies have room
        erase(endX, endY, 6);

        //TODO enemies get stuck on 1x1 passages.

        tiles.getn(endX, endY).setOverlay(Blocks.spawn);

        //ores
        pass((x, y) -> {

            if(block != Blocks.air){
                if(nearAir(x, y)){
                    if(block != LaiEnvironmentBlocks.darkgreenStone && noise(x + 782, y, 4, 0.8f, 38f, 1f) > 0.665f){
                        ore = LaiEnvironmentBlocks.wallOrelithium;
                    }

                }
            }else if(!nearWall(x, y)){

                if(noise(x + 150, y + x*2 + 100, 4, 0.8f, 55f, 1f) > 0.76f){
                    ore = LaiEnvironmentBlocks.oreIron;
                }

                //TODO design ore generation so it doesn't overlap
                if(noise(x + 190, y + 600 - x, 4, 0.90f, 45f, 1f) < 0.27f){
                    ore = LaiEnvironmentBlocks.oreLithium;
                }

                if(noise(x + 999, y + 600 - x + 30, 4, 0.4f, 45f, 1f) > 0.27f && floor == LaiEnvironmentBlocks.sporebarkWall){
                    ore = LaiEnvironmentBlocks.orePlatinum;
                }
                /*if(noise(x + 999, y + 600 - x, 4, 0.63f, 45f, 1f) < 0.27f && floor == LaiEnvironmentBlocks.darkgreenStone){
                    ore = LaiEnvironmentBlocks.oreLithium;
                }*/

            }

            if(noise(x + 999, y + 600 - x, 5, 0.8f, 45f, 1f) < 0.44f && floor == Blocks.crystallineStone){
                floor = Blocks.crystalFloor;
            }

            if(block == Blocks.air && (floor == Blocks.crystallineStone || floor == Blocks.crystalFloor) && rand.chance(0.09) && nearWall(x, y)
                && !near(x, y, 4, Blocks.crystalCluster) && !near(x, y, 4, Blocks.vibrantCrystalCluster)){
                block = floor == Blocks.crystalFloor ? Blocks.vibrantCrystalCluster : Blocks.crystalCluster;
                ore = Blocks.air;
            }

            if(block == Blocks.snowWall && rand.chance(0.23) && nearAir(x, y) && !near(x, y, 3, Blocks.crystalOrbs)){
                block = Blocks.crystalOrbs;
                ore = Blocks.air;
            }

            //TODO test, different placement
            //TODO this biome should have more blocks in general
            if(block == LaiEnvironmentBlocks.pinkcrystalsporeWall && rand.chance(0.3) && nearAir(x, y) && !near(x, y, 3, LaiEnvironmentBlocks.pinkcrystal)){
                block = LaiEnvironmentBlocks.pinkcrystal;
                ore = Blocks.air;
            }
        });

        //remove props near ores, they're too annoying
        pass((x, y) -> {
            if(ore.asFloor().wallOre || block.itemDrop != null || (block == Blocks.air && ore != Blocks.air)){
                removeWall(x, y, 3, b -> b instanceof TallBlock);
            }
        });

        trimDark();

        int minVents = rand.random(6, 9);
        int ventCount = 0;

        //vents
        outer:
        for(Tile tile : tiles){
            var floor = tile.floor();
            if((floor == Blocks.rhyolite || floor == Blocks.roughRhyolite) && rand.chance(0.002)){
                int radius = 2;
                for(int x = -radius; x <= radius; x++){
                    for(int y = -radius; y <= radius; y++){
                        Tile other = tiles.get(x + tile.x, y + tile.y);
                        if(other == null || (other.floor() != Blocks.rhyolite && other.floor() != Blocks.roughRhyolite) || other.block().solid){
                            continue outer;
                        }
                    }
                }

                ventCount ++;
                for(var pos : SteamVent.offsets){
                    Tile other = tiles.get(pos.x + tile.x + 1, pos.y + tile.y + 1);
                    other.setFloor(Blocks.rhyoliteVent.asFloor());
                }
            }
        }

        int iterations = 0;
        int maxIterations = 5;

        //try to add additional vents, but only several times to prevent infinite loops in bad maps
        while(ventCount < minVents && iterations++ < maxIterations){
            outer:
            for(Tile tile : tiles){
                if(rand.chance(0.00018 * (1 + iterations)) && !Mathf.within(tile.x, tile.y, spawnX, spawnY, 5f)){
                    //skip crystals, but only when directly on them
                    if(tile.floor() == Blocks.crystallineStone || tile.floor() == Blocks.crystalFloor){
                        continue;
                    }

                    int radius = 1;
                    for(int x = -radius; x <= radius; x++){
                        for(int y = -radius; y <= radius; y++){
                            Tile other = tiles.get(x + tile.x, y + tile.y);
                            //skip solids / other vents / arkycite / slag
                            if(other == null || other.block().solid || other.floor().attributes.get(Attribute.steam) != 0 || other.floor() == LaiEnvironmentBlocks.deepfreshwater || other.floor() == Blocks.tar){
                                continue outer;
                            }
                        }
                    }

                    Block
                    floor = LaiEnvironmentBlocks.darkgreenStone,
                    secondFloor = LaiEnvironmentBlocks.darkgreenStonePlates,
                    vent = LaiEnvironmentBlocks.darkGreenStoneCrater;

                    int xDir = 1;
                    //set target material depending on what's encountered
                    if(tile.floor() == LaiEnvironmentBlocks.bluedirt || tile.floor() == Blocks.snow){
                        floor = secondFloor = Blocks.snow;
                        vent = Blocks.arkyicVent;
                    }else if(tile.floor() == LaiEnvironmentBlocks.sporebark || tile.floor() == Blocks.yellowStonePlates || tile.floor() == LaiEnvironmentBlocks.pinkcrystalspore){
                        floor = LaiEnvironmentBlocks.sporebark;
                        secondFloor = Blocks.yellowStonePlates;
                        vent = Blocks.yellowStoneVent;
                    }else if(tile.floor() == Blocks.redStone || tile.floor() == Blocks.denseRedStone){
                        floor = Blocks.denseRedStone;
                        secondFloor = Blocks.redStone;
                        vent = Blocks.redStoneVent;
                        xDir = -1;
                    }else if(tile.floor() == Blocks.ice){
                        floor = secondFloor = Blocks.ice;
                        vent = Blocks.carbonVent;
                    }


                    ventCount ++;
                    for(var pos : SteamVent.offsets){
                        Tile other = tiles.get(pos.x + tile.x + 1, pos.y + tile.y + 1);
                        other.setFloor(vent.asFloor());
                    }

                    //"circle" for blending
                    //TODO should it replace akrycite? slag?
                    int crad = rand.random(6, 14), crad2 = crad * crad;
                    for(int cx = -crad; cx <= crad; cx++){
                        for(int cy = -crad; cy <= crad; cy++){
                            int rx = cx + tile.x, ry = cy + tile.y;
                            //skew circle Y
                            float rcy = cy + cx*0.9f;
                            if(cx*cx + rcy*rcy <= crad2 - noise(rx, ry + rx * 2f * xDir, 2, 0.7f, 8f, crad2 * 1.1f)){
                                Tile dest = tiles.get(rx, ry);
                                if(dest != null && dest.floor().attributes.get(Attribute.steam) == 0 && dest.floor() != Blocks.roughRhyolite && dest.floor() != Blocks.tar && dest.floor() != LaiEnvironmentBlocks.deepfreshwater){

                                    dest.setFloor(rand.chance(0.08) ? secondFloor.asFloor() : floor.asFloor());

                                    if(dest.block().isStatic()){
                                        dest.setBlock(floor.asFloor().wall);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        for(Tile tile : tiles){
            if(tile.overlay().needsSurface && !tile.floor().hasSurface()){
                tile.setOverlay(Blocks.air);
            }
        }

        decoration(0.017f);

        //it is very hot
        state.rules.env = sector.planet.defaultEnv;
        state.rules.placeRangeCheck = true;

        //TODO remove slag and arkycite around core.
        Schematics.placeLaunchLoadout(spawnX, spawnY);

        //all sectors are wave sectors
        state.rules.waves = false;
        state.rules.showSpawns = true;
    }
}

