package lai.maps.gen;

import arc.graphics.Color;
import arc.util.Tmp;
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
import mindustry.ai.Astar;
import mindustry.maps.generators.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import lai.content.*;
import mindustry.world.blocks.environment.*;
import mindustry.ai.BaseRegistry.*;
import static mindustry.Vars.*;
import mindustry.world.meta.*;
import lai.content.blocks.*;
import lai.content.*;

import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Sector;
import mindustry.world.*;
import mindustry.world.blocks.environment.Floor;

import static mindustry.content.Blocks.*;
import static lai.content.blocks.LaiEnvironmentBlocks.*;
import static mindustry.graphics.g3d.PlanetGrid.Ptile;

public class MathexisPlanetGenerator extends PlanetGenerator{

    BaseGenerator basegen = new BaseGenerator();


    public float heightScl = 2.2f, octaves = 6, persistence = 1.4f, heightPow = 3.6f, heightMult = 1.6f;
    float scl = 5f;

    //TODO inline/remove
    public static float arkThresh = 0.28f, arkScl = 0.83f;
    public static int arkSeed = 7, arkOct = 2;
    public static float liqThresh = 1.64f, liqScl = 67f, redThresh = 3.1f, noArkThresh = 0.3f;
    public static int crystalSeed = 8, crystalOct = 2;
    public static float crystalScl = 0.9f, crystalMag = 0.3f;
    public static float airThresh = 0.13f, airScl = 10;
    

    Block[][] arr =
    {
        {snow, natricStone, ice, darkgreenStone, darkgreenStone, natricStone, natricStone, natricStone, ice, snow, ice, darkBlueSand, darkBlueSand},
        {ice, pinksand, pinksand, snow, darkBlueSand, pinksand, ice, pinksand, darkBlueSand, darkpinksand, darkgreenStone, darkgreenStone, darkgreenStone},
        {ice, orangeDirt, orangeDirt, orangeDirt, darkBlueSand, orangeDirt, ice, ice, orangeDirt, ice, pinksand, darkpinksand, darkpinksand},
        {darkBlueSand, orangeDirt, snow, ice, darkBlueSand, darkpinksand, darkBlueSand, darkpinksand, snow, orangeDirt, orangeDirt, orangeDirt, orangeDirt},
        {ice, orangeDirt, ice, snow, snow, ice, ice, snow, darkgreenStone, natricStone, natricStone, darkgreenStone, darkgreenStone},
        {darkBlueSand, darkpinksand, sporebark, snow, darkBlueSand, snow, ice, pinkcrystalspore, pinkcrystalspore, sporebark, darkgreenStone, darkgreenStone, darkgreenStone},
        {sporebark, natricStone, darkgreenStone, ice, orangeDirt, ice, snow, darkpinksand, darkpinksand, sporebark, sporebark, pinkcrystalspore, pinkcrystalspore}
    };

    Vec3 basePos = new Vec3(0.9341721, 0.0, 0.3568221);

    @Override
    public void onSectorCaptured(Sector sector){
        sector.planet.reloadMeshAsync();
    }

    @Override
    public void onSectorLost(Sector sector){
        sector.planet.reloadMeshAsync();
    }

    @Override
    public void beforeSaveWrite(Sector sector){
        sector.planet.reloadMeshAsync();
    }

    @Override
    public boolean isEmissive(){
        return true;
    }

    @Override
    public void getEmissiveColor(Vec3 position, Color out){
        float dst = 999f, captureDst = 999f, lightScl = 0f;

        Object[] sectors = LaiPlanets.mathexis.sectors.items;
        int size = LaiPlanets.mathexis.sectors.size;

        for(int i = 0; i < size; i ++){
            var sector = (Sector)sectors[i];

            if(sector.hasEnemyBase() && !sector.isCaptured()){
                dst = Math.min(dst, position.dst(sector.tile.v) - (sector.preset != null ? sector.preset.difficulty/10f * 0.03f - 0.03f : 0f));
            }else if(sector.hasBase()){
                float cdst = position.dst(sector.tile.v);
                if(cdst < captureDst){
                    captureDst = cdst;
                    lightScl = sector.info.lightCoverage;
                }
            }
        }

        lightScl = Math.min(lightScl / 50000f, 1.3f);
        if(lightScl < 1f) lightScl = Interp.pow5Out.apply(lightScl);

        float freq = 0.05f;
        if(position.dst(basePos) < 0.55f ?

            dst*metalDstScl + Simplex.noise3d(seed + 1, 3, 0.4, 5.5f, position.x, position.y + 200f, position.z)*0.08f + ((basePos.dst(position) + 0.00f) % freq < freq/2f ? 1f : 0f) * 0.07f < 0.08f/* || dst <= 0.0001f*/ :
            dst*metalDstScl + Simplex.noise3d(seed, 3, 0.4, 9f, position.x, position.y + 370f, position.z)*0.06f < 0.045){

            out.set(LaiTeams.xenoSyndicate.color)
                .mul(0.8f + Simplex.noise3d(seed, 1, 1, 9f, position.x, position.y + 99f, position.z) * 0.4f)
                .lerp(Team.sharded.color, 0.2f*Simplex.noise3d(seed, 1, 1, 9f, position.x, position.y + 999f, position.z)).toFloatBits();
        }else if(captureDst*metalDstScl + Simplex.noise3d(seed, 3, 0.4, 9f, position.x, position.y + 600f, position.z)*0.07f < 0.05 * lightScl){
            out.set(Team.sharded.color).mul(0.7f + Simplex.noise3d(seed, 1, 1, 9f, position.x, position.y + 99f, position.z) * 0.4f)
                .lerp(LaiTeams.xenoSyndicate.color, 0.3f*Simplex.noise3d(seed, 1, 1, 9f, position.x, position.y + 999f, position.z)).toFloatBits();

        }
    }


    @Override
    public float getHeight(Vec3 position) {
        position = Tmp.v33.set(position).scl(scl);
        return Mathf.pow(Simplex.noise3d(seed, 5, 0.5f, 1f/3f, position.x, position.y, position.z), 2f);
    }

    @Override
    public void generateSector(Sector sector) {
        Ptile tile = sector.tile;
        float meridian = tile.v.x, poles = tile.v.y;

        //an arc of enemy bases
        if ((meridian < -0.32f && poles < -0.27f && poles > -0.9f) || (meridian > -0.29f && meridian < 0.21f && poles > -0.12f && poles < 0.68f)){
            sector.generateEnemyBase = true;
        }
    }


    @Override
    public void getColor(Vec3 position, Color out){
        Block block = getBlock(position);

        out.set(block.mapColor).a(1f - block.albedo);
    }

    float rawTemp(Vec3 position){
        return position.dst(0, 0, 1)*2.2f - Simplex.noise3d(seed, 9, 0.54f, 1.4f, 10f + position.x, 10f + position.y, 10f + position.z) * 2.9f;
    }

    // Основной шум жидкостей — теперь реже и мягче
    protected float fluidField(Vec3 p, double oct, double fall, double scale, float mul){
        return fluidField(p, oct, fall, scale, mul, 0f);
    }
    
    protected float fluidField(Vec3 p, double oct, double fall, double scale, float mul, float shift){
        return Simplex.noise3d(seed, oct, fall, scale,
                p.x + shift, p.y + shift, p.z + shift) * mul;
    }
    
    // Определение воды/жидкости в точке — теперь более редкое
    protected boolean detectFluid(Vec3 p, float threshold){
        float f1 = fluidField(p, 6, 0.48, 1f,     0.42f);
        float f2 = fluidField(p, 8, 0.31, 1f/3f,  0.47f, 40f);
        float f3 = fluidField(p, 7, 0.24, 1f/6f,  0.39f, 300f);
    
        // Жидкость появляется реже
        return f1 < threshold || f2 < threshold || f3 < threshold;
    }


    // Получение твёрдого блока с учётом высоты
    Block resolveSolid(Vec3 p){
        float h = getHeight(p);
    
        Tmp.v31.set(p);
        p = Tmp.v33.set(p).scl(scl);
    
        float radius = scl;
        float temperature = Mathf.clamp(Math.abs(p.y * 2f) / radius);
        float tempNoise = Simplex.noise3d(seed, 6, 0.52, 1f/3f, p.x, p.y + 500, p.z);
    
        temperature = Mathf.lerp(temperature, tempNoise, 0.45f);
    
        h *= 1.15f;
        h = Mathf.clamp(h);
    
        int tIndex = Mathf.clamp((int)(temperature * arr.length), 0, arr.length - 1);
        int hIndex = Mathf.clamp((int)(h * arr[0].length), 0, arr[0].length - 1);
    
        Block base = arr[tIndex][hIndex];
    
        // Альтернативы — реже
        if(Simplex.noise3d(seed, 4, 0.55f, 1f / scl, p.x, p.y, p.z) > 0.5f){
            base = pickAlternate(base);
        }
    
        // Затопление ещё реже
        if(detectFluid(p, 0.12f) || (base == pinksand && detectFluid(p, 0.10f))){
            base = applyFlood(base);
        }
    
        return base;
    }
    
    
    // Замена блоков на "затопленные" версии
    Block applyFlood(Block b){
        return b == darkgreenStone ? freshwater :
               b;
    }
    
    
    // Поиск альтернативного блока
    Block pickAlternate(Block b){
        Block alt = content.block(b.name + "-alt");
        return alt != null ? alt : b;
    }
    
    
    // Основной метод выдачи блока
    Block getBlock(Vec3 pos){
        float height = getHeight(pos);
        if(height < 0.6f && detectFluid(pos, 0.08f)) return freshwater; // только низкие места

    
        // плотные минералы
        if(Simplex.noise3d(seed, 4, 0.8f, 1f, pos.x, pos.y, pos.z) > 0.82f)
            return lithiumOxideLiquid;
    
        return resolveSolid(pos);
    }

    static double metalDstScl = 0.25;

    @Override
    public void genTile(Vec3 position, TileGen tile){
        tile.floor = getBlock(position);

        if(tile.floor == darkgreenStone && rand.chance(0.01)){
            tile.floor = darkGreenStoneCrater;
        }

        tile.block = tile.floor.asFloor().wall;

        if(Ridged.noise3d(seed + 1, position.x, position.y, position.z, 2, airScl) > airThresh){
            tile.block = Blocks.air;
        }

        //TODO only certain places should have carbon stone...
        if(Ridged.noise3d(seed + 2, position.x, position.y + 4f, position.z, 3, 6f) > 0.6){
            tile.floor = darkpinksand;
        }
    }

    @Override
    protected void generate() {
        float temp = rawTemp(sector.tile.v);
    
        // --- Step 1: Base terrain generation ---
        if(temp > 0.7f){
            pass((x, y) -> {
                if(floor != ice){
                    float n = noise(x + 782, y, 8, 0.8f, 150f, 0.5f);
                    if(n > 0.62f){
                        floor = n > 0.635f ? deepfreshwater : iceGreen;
                        ore = Blocks.air;
                    }
                    if(n > 0.55f && floor == pinksand) floor = pinkcrystalspore;
                }
            });
        }
    
        cells(4);
        pass((x, y) -> {
            if(floor == darkgreenStone && noise(x, y, 3, 0.4f, 13f, 1f) > 0.59f){
                block = darkgreenStoneWall;
            }
        });

        pass((x, y) -> {
            if(floor != pinksand) return;

            //TODO bad
            if(Math.abs(noise(x, y + 500f, 5, 0.6f, 40f, 1f) - 0.5f) < 0.09f){
                floor = snow;
            }

            if(nearWall(x, y)) return;

            float noise = noise(x + 300, y - x*1.6f + 100, 4, 0.8f, liqScl, 1f);

            if(noise > liqThresh){
                floor = Blocks.tar;
            }
        });


    
        // --- Step 2: Spawn positions and paths ---
        float length = width/2.6f;
        Vec2 trns = Tmp.v1.trns(rand.random(360f), length);
        int
        spawnX = (int)(trns.x + width/2f), spawnY = (int)(trns.y + height/2f),
        endX = (int)(-trns.x + width/2f), endY = (int)(-trns.y + height/2f);
        float maxd = Mathf.dst(width/2f, height/2f);

        erase(spawnX, spawnY, 15);
        brush(pathfind(spawnX, spawnY, endX, endY, tile -> (tile.solid() ? 300f : 0f) + maxd - tile.dst(width/2f, height/2f)/10f, Astar.manhattan), 9);
        erase(endX, endY, 15);
    
        // --- Step 3: Arkycite / mini-biomes ---
        pass((x, y) -> {
            if(floor != pinksand) return;

            //TODO bad
            if(Math.abs(noise(x, y + 500f, 5, 0.6f, 40f, 1f) - 0.5f) < 0.09f){
                floor = snow;
            }

            if(nearWall(x, y)) return;

            float noise = noise(x + 300, y - x*1.6f + 100, 4, 0.8f, liqScl, 1f);

            if(noise > liqThresh){
                floor = Blocks.tar;
            }
        });
    
        median(2, 0.6, Blocks.tar);
        blend(Blocks.tar, snow, 4);
        distort(10f, 12f);
        distort(5f, 7f);

        median(3, 0.5, deepfreshwater);
        median(2, 0.6, Blocks.tar);
        
        pass((x, y) -> {
            //rough rhyolite
            if(noise(x, y + 600 + x, 5, 0.86f, 60f, 1f) < 0.41f && floor == Blocks.rhyolite){
                floor = Blocks.roughRhyolite;
            }

            if(floor == deepfreshwater){
                float noise = noise(x + 782, y, 8, 0.8f, 150f, 0.5f);
                if(noise > 0.62f){
                    floor = freshwater; // только здесь
                    ore = Blocks.air;
                }
            }

            if((floor == Blocks.tar || floor == snow) && block.isStatic()){
                block = snowWall;
            }

            float max = 0;
            for(Point2 p : Geometry.d8){
                //TODO I think this is the cause of lag
                max = Math.max(max, world.getDarkness(x + p.x, y + p.y));
            }
            if(max > 0){
                block = floor.asFloor().wall;
                if(block == Blocks.air) block = sporebarkWall;
            }

            if(floor == Blocks.yellowStonePlates && noise(x + 78 + y, y, 3, 0.8f, 6f, 1f) > 0.44f){
                floor = sporebark;
            }

            if(floor == Blocks.redStone && noise(x + 78 - y, y, 4, 0.73f, 19f, 1f) > 0.63f){
                floor = Blocks.denseRedStone;
            }
        });

    
        inverseFloodFill(tiles.getn(spawnX, spawnY));
        blend(Blocks.redStoneWall, Blocks.denseRedStone, 4);
        erase(endX, endY, 6);
        tiles.getn(endX, endY).setOverlay(Blocks.spawn);
        
        // --- Step 5: Room generation ---
        class Room {
            final int x, y, radius;
            final ObjectSet<Room> connected = new ObjectSet<>();
            Room(int x, int y, int radius){ this.x = x; this.y = y; this.radius = radius; connected.add(this); }
            void join(int x1,int y1,int x2,int y2){
                float nscl = rand.random(20,60);
                int stroke = rand.random(4,12);
                brush(pathfind(x1, y1, x2, y2, tile -> (tile.solid()?27f:0f)+noise(tile.x,tile.y,2,2f,1f/nscl)*60, Astar.manhattan), stroke);
            }
            void connect(Room to){
                if(!connected.add(to)||to==this) return;
                Vec2 midpoint = Tmp.v1.set(to.x,to.y).add(x,y).scl(0.5f);
                midpoint.add(Tmp.v2.setToRandomDirection(rand).scl(Tmp.v1.dst(x,y)));
                midpoint.sub(width/2f,height/2f).limit(width/2f/Mathf.sqrt3).add(width/2f,height/2f);
                int mx=(int)midpoint.x,my=(int)midpoint.y;
                join(x,y,mx,my); join(mx,my,to.x,to.y);
            }
        }
    
        int rooms = rand.random(2,4);
        Seq<Room> roomseq = new Seq<>();
        float constraint = 1.3f;
        float radiusRoom = width / 2f / Mathf.sqrt3;
        for(int i=0;i<rooms;i++){
            Tmp.v1.trns(rand.random(360),rand.random(radiusRoom/constraint));
            int rx = (int)(width/2f+Tmp.v1.x);
            int ry = (int)(height/2f+Tmp.v1.y);
            int rrad = (int)Math.min(rand.random(9,radiusRoom-Tmp.v1.len()/2),30);
            roomseq.add(new Room(rx,ry,rrad));
        }
    
        Room spawn = roomseq.first();
        roomseq.each(room -> erase(room.x, room.y, room.radius));
        int connections = rand.random(Math.max(rooms-1,1), rooms+3);
        for(int i=0;i<connections;i++) roomseq.random(rand).connect(roomseq.random(rand));
        for(Room room : roomseq) spawn.connect(room);
    
        cells(1);
        distort(10,6);
        inverseFloodFill(tiles.getn(spawn.x, spawn.y));
    
        // --- Step 6: Ores & decoration ---
        Seq<Block> ores = Seq.with(oreLithium, oreIron, oreRhodium, oreNatrium);
        ores(ores);
        oresSquare(Seq.with(oreLithium, oreIron), spawn.x, spawn.y, 20);
        trimDark();
        median(2);
        decoration(0.017f);

        pass((x, y) -> {
            if(ore.asFloor().wallOre || block.itemDrop != null || (block == Blocks.air && ore != Blocks.air)){
                removeWall(x, y, 3, b -> b instanceof TallBlock);
            }
        });

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
                            if(other == null || other.block().solid || other.floor().attributes.get(Attribute.steam) != 0 || other.floor() == deepfreshwater || other.floor() == Blocks.tar){
                                continue outer;
                            }
                        }
                    }

                    Block
                    floor = darkgreenStone,
                    secondFloor = darkgreenStonePlates,
                    vent = darkGreenStoneCrater;

                    int xDir = 1;
                    //set target material depending on what's encountered
                    if(tile.floor() == bluedirt || tile.floor() == snow){
                        floor = secondFloor = snow;
                        vent = Blocks.arkyicVent;
                    }else if(tile.floor() == sporebark || tile.floor() == Blocks.yellowStonePlates || tile.floor() == pinkcrystalspore){
                        floor = sporebark;
                        secondFloor = Blocks.yellowStonePlates;
                        vent = Blocks.yellowStoneVent;
                    }else if(tile.floor() == Blocks.redStone || tile.floor() == Blocks.denseRedStone){
                        floor = Blocks.denseRedStone;
                        secondFloor = Blocks.redStone;
                        vent = Blocks.redStoneVent;
                        xDir = -1;
                    }else if(tile.floor() == ice){
                        floor = secondFloor = ice;
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
                                if(dest != null && dest.floor().attributes.get(Attribute.steam) == 0 && dest.floor() != Blocks.roughRhyolite && dest.floor() != Blocks.tar && dest.floor() != deepfreshwater){

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
    
        // --- Step 7: Launch loadout & environment ---
        Schematics.placeLaunchLoadout(spawn.x, spawn.y);
        state.rules.env = sector.planet.defaultEnv;
        state.rules.placeRangeCheck = true;
        state.rules.waves = false;
        state.rules.showSpawns = true;
    }

    public void oresSquare(Seq<Block> ores, int cx, int cy, int rad, float scl, Floor floorOn) {
        for (Block cur : ores) {
            boolean generated = false;
            int offset = 0;

            while (!generated) {
                for (int x = -rad; x <= rad; x++) {
                    for (int y = -rad; y <= rad; y++) {
                        Tile tile = tiles.get(x + cx, y + cy);
                        if (tile == null || (!tile.floor().hasSurface() && !tile.floor().supportsOverlay) || tile.overlay() != air || tile.floor() == deepwater || (floorOn != empty && tile.floor() != floorOn)) continue;

                        int i = ores.indexOf(cur);
                        int offsetX = Math.round((x - 4) * scl), offsetY = Math.round((y + 23) * scl);
                        if (Math.abs(0.5f - noise(offsetX + offset, offsetY + i * 999, 2, 0.7, (40 + i * 2))) > 0.24f &&
                            Math.abs(0.5f - noise(offsetX + offset, offsetY - i * 999, 1, 1, (30 + i * 4))) > 0.33f) {
                            tile.setOverlay(cur);
                            if (tile.block() == air) generated = true;
                        }
                    }
                }

                if (!generated) {
                    offset += 1000;
                }
            }
        }
    }

    public void oresSquare(Seq<Block> ores, int cx, int cy, int rad) {
        oresSquare(ores, cx, cy, rad, 1f, empty.asFloor());
    }

    @Override
    public void ores(Seq<Block> ores) {
        pass((x, y) -> {
            if ((!floor.asFloor().hasSurface() && !floor.asFloor().supportsOverlay) || floor == deepwater) return;

            int offsetX = x - 4, offsetY = y + 23;
            for (int i = ores.size - 1; i >= 0; i--) {
                Block entry = ores.get(i);
                if (Math.abs(0.5f - noise(offsetX, offsetY + i*999, 2, 0.7, (40 + i * 2))) > 0.24f &&
                    Math.abs(0.5f - noise(offsetX, offsetY - i*999, 1, 1, (30 + i * 4))) > 0.33f) {
                    ore = entry;
                    break;
                }
            }
        });
    }


    @Override
    public void postGenerate(Tiles tiles){
        if(sector.hasEnemyBase()){
            basegen.postGenerate();

            //spawn air enemies
            if(spawner.countGroundSpawns() == 0){
                state.rules.spawns = Waves.generate(sector.threat, new Rand(sector.id), state.rules.attackMode, true, false);
            }
        }
    }
}

