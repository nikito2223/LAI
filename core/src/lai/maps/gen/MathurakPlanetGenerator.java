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

public class MathurakPlanetGenerator extends PlanetGenerator
{
    //alternate, less direct generation (wip)
    public static boolean alt = false;

    BaseGenerator basegen = new BaseGenerator();
    float scl = 5f;
    float waterOffset = 0.17f;
    boolean genLakes = false;

    public Block[][] arr = {
        {LaiBlocks.darkgreenStone, LaiBlocks.darkgreenStone, LaiBlocks.pinksand, LaiBlocks.darkBlueSand, LaiBlocks.darkgreenStone, LaiBlocks.pinksand},
        {LaiBlocks.darkgreenStone, LaiBlocks.pinksand, LaiBlocks.darkgreenStone, LaiBlocks.darkBlueSand, LaiBlocks.darkgreenStone, LaiBlocks.pinksand},
        {LaiBlocks.darkBlueSand, LaiBlocks.darkgreenStone, LaiBlocks.darkgreenStone, LaiBlocks.darkgreenStone, LaiBlocks.darkgreenStone, LaiBlocks.darkBlueSand},
        {LaiBlocks.darkBlueSand, LaiBlocks.darkBlueSand, LaiBlocks.pinksand, LaiBlocks.pinksand, LaiBlocks.darkgreenStone, LaiBlocks.pinksand},
        {LaiBlocks.darkBlueSand, LaiBlocks.darkBlueSand, LaiBlocks.darkgreenStone, LaiBlocks.darkBlueSand, LaiBlocks.darkgreenStone, LaiBlocks.pinksand},
        {LaiBlocks.darkBlueSand, LaiBlocks.darkgreenStone, LaiBlocks.darkgreenStone, LaiBlocks.darkgreenStone, LaiBlocks.pinksand, LaiBlocks.darkBlueSand},
        {LaiBlocks.darkgreenStone, LaiBlocks.darkgreenStone, LaiBlocks.darkgreenStone, LaiBlocks.darkBlueSand, LaiBlocks.darkBlueSand, LaiBlocks.pinksand},
        {LaiBlocks.darkgreenStone, LaiBlocks.pinksand, LaiBlocks.darkBlueSand, LaiBlocks.darkBlueSand, LaiBlocks.darkgreenStone, LaiBlocks.pinksand}
};



    ObjectMap<Block, Block> dec = ObjectMap.of(
            LaiBlocks.distilledwater, LaiBlocks.distilledwater
    );

    ObjectMap<Block, Block> tars = ObjectMap.of(
            LaiBlocks.pinksand, LaiBlocks.darkgreenStone,
            LaiBlocks.darkBlueSand, LaiBlocks.darkgreenStone
    );

    float water = 8f / arr[0].length;

    float rawHeight(Vec3 position){
        position = Tmp.v33.set(position).scl(scl);
        return (Mathf.pow(Simplex.noise3d(seed, 7, 0.5f, 1f/3f, position.x, position.y, position.z), 2.3f) + waterOffset) / (1f + waterOffset);
    }

    @Override
    public void generateSector(Sector sector)
    {

        //these always have bases
        if(sector.id == 128 || sector.id == 0){
            sector.generateEnemyBase = true;
            return;
        }

        Ptile tile = sector.tile;

        boolean any = false;
        float poles = Math.abs(tile.v.y);
        float noise = Noise.snoise3(tile.v.x, tile.v.y, tile.v.z, 0.001f, 0.58f);

        if(noise + poles/7.1 > 0.12 && poles > 0.23){
            any = true;
        }

        if(noise < 0.16){
            for(Ptile other : tile.tiles){
                var osec = sector.planet.getSector(other);

                //no sectors near start sector!
                if(
                        osec.id == sector.planet.startSector || //near starting sector
                                osec.generateEnemyBase && poles < 0.85 || //near other base
                                (sector.preset != null && noise < 0.11) //near preset
                        ){
                    return;
                }
            }
        }

        sector.generateEnemyBase = any;
    }

    @Override
    public float getHeight(Vec3 position){
        float height = rawHeight(position);
        return Math.max(height, water);
    }

    @Override
    public Color getColor(Vec3 position){
        Block block = getBlock(position);
        //replace salt with sand color
        if(block == LaiBlocks.darkgreenStone) return LaiBlocks.darkBlueSand.mapColor;
        return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
    }

    @Override
    public void genTile(Vec3 position, TileGen tile){
        tile.floor = getBlock(position);
        tile.block = tile.floor.asFloor().wall;

        if(Ridged.noise3d(seed + 1, position.x, position.y, position.z, 2, 22) > 0.31){
            tile.block = Blocks.air;
        }
    }

    Block getBlock(Vec3 position){
        float height = rawHeight(position);
        Tmp.v31.set(position);
        position = Tmp.v33.set(position).scl(scl);
        float rad = scl;
        float temp = Mathf.clamp(Math.abs(position.y * 2f) / (rad));
        float tnoise = Simplex.noise3d(seed, 7, 0.56, 1f/3f, position.x, position.y + 999f, position.z);
        temp = Mathf.lerp(temp, tnoise, 0.5f);
        height *= 1.2f;
        height = Mathf.clamp(height);

        float tar = Simplex.noise3d(seed, 4, 0.55f, 1f/2f, position.x, position.y + 999f, position.z) * 0.3f + Tmp.v31.dst(0, 0, 1f) * 0.2f;

        Block res = arr[Mathf.clamp((int)(temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int)(height * arr[0].length), 0, arr[0].length - 1)];
        if(tar > 0.5f){
            return tars.get(res, res);
        }else{
            return res;
        }
    }

    @Override
    protected float noise(float x, float y, double octaves, double falloff, double scl, double mag){
        Vec3 v = sector.rect.project(x, y).scl(5f);
        return Simplex.noise3d(seed, octaves, falloff, 1f / scl, v.x, v.y, v.z) * (float)mag;
    }

    @Override
    protected void generate(){

        class Room{
            int x, y, radius;
            ObjectSet<Room> connected = new ObjectSet<>();

            Room(int x, int y, int radius){
                this.x = x;
                this.y = y;
                this.radius = radius;
                connected.add(this);
            }

            void join(int x1, int y1, int x2, int y2){
                float nscl = rand.random(100f, 140f) * 6f;
                int stroke = rand.random(3, 9);
                brush(pathfind(x1, y1, x2, y2, tile -> (tile.solid() ? 50f : 0f) + noise(tile.x, tile.y, 2, 0.4f, 1f / nscl) * 500, Astar.manhattan), stroke);
            }

            void connect(Room to){
                if(!connected.add(to) || to == this) return;

                Vec2 midpoint = Tmp.v1.set(to.x, to.y).add(x, y).scl(0.5f);
                rand.nextFloat();

                if(alt){
                    midpoint.add(Tmp.v2.set(1, 0f).setAngle(Angles.angle(to.x, to.y, x, y) + 90f * (rand.chance(0.5) ? 1f : -1f)).scl(Tmp.v1.dst(x, y) * 2f));
                }else{
                    //add randomized offset to avoid straight lines
                    midpoint.add(Tmp.v2.setToRandomDirection(rand).scl(Tmp.v1.dst(x, y)));
                }

                midpoint.sub(width/2f, height/2f).limit(width / 2f / Mathf.sqrt3).add(width/2f, height/2f);

                int mx = (int)midpoint.x, my = (int)midpoint.y;

                join(x, y, mx, my);
                join(mx, my, to.x, to.y);
            }

            void joinLiquid(int x1, int y1, int x2, int y2){
                float nscl = rand.random(100f, 140f) * 6f;
                int rad = rand.random(3, 11);
                int avoid = 2 + rad;
                var path = pathfind(x1, y1, x2, y2, tile -> (tile.solid() || !tile.floor().isLiquid ? 70f : 0f) + noise(tile.x, tile.y, 2, 0.4f, 1f / nscl) * 500, Astar.manhattan);
                path.each(t -> {
                    //don't place liquid paths near the core
                    if(Mathf.dst2(t.x, t.y, x2, y2) <= avoid * avoid){
                        return;
                    }

                    for(int x = -rad; x <= rad; x++){
                        for(int y = -rad; y <= rad; y++){
                            int wx = t.x + x, wy = t.y + y;
                            if(Structs.inBounds(wx, wy, width, height) && Mathf.within(x, y, rad)){
                                Tile other = tiles.getn(wx, wy);
                                other.setBlock(Blocks.air);
                                if(Mathf.within(x, y, rad - 1) && !other.floor().isLiquid){
                                    Floor floor = other.floor();
                                    //TODO does not respect tainted floors
                                    other.setFloor((Floor)(floor == LaiBlocks.darkBlueSand || floor == LaiBlocks.darkgreenStone ? LaiBlocks.distilledwater : LaiBlocks.deepDistilledwater));
                                }
                            }
                        }
                    }
                });
            }

            void connectLiquid(Room to){
                if(to == this) return;

                Vec2 midpoint = Tmp.v1.set(to.x, to.y).add(x, y).scl(0.5f);
                rand.nextFloat();

                //add randomized offset to avoid straight lines
                midpoint.add(Tmp.v2.setToRandomDirection(rand).scl(Tmp.v1.dst(x, y)));
                midpoint.sub(width/2f, height/2f).limit(width / 2f / Mathf.sqrt3).add(width/2f, height/2f);

                int mx = (int)midpoint.x, my = (int)midpoint.y;

                joinLiquid(x, y, mx, my);
                joinLiquid(mx, my, to.x, to.y);
            }
        }

        cells(4);
        distort(10f, 12f);

        float constraint = 1.3f;
        float radius = width / 2f / Mathf.sqrt3;
        int rooms = rand.random(2, 5);
        Seq<Room> roomseq = new Seq<>();

        for(int i = 0; i < rooms; i++){
            Tmp.v1.trns(rand.random(360f), rand.random(radius / constraint));
            float rx = (width/2f + Tmp.v1.x);
            float ry = (height/2f + Tmp.v1.y);
            float maxrad = radius - Tmp.v1.len();
            float rrad = Math.min(rand.random(9f, maxrad / 2f), 30f);
            roomseq.add(new Room((int)rx, (int)ry, (int)rrad));
        }

        //check positions on the map to place the player spawn. this needs to be in the corner of the map
        Room spawn = null;
        Seq<Room> enemies = new Seq<>();
        int enemySpawns = rand.random(1, Math.max((int)(sector.threat * 4), 1));
        int offset = rand.nextInt(360);
        float length = width/2.55f - rand.random(13, 23);
        int angleStep = 5;
        int waterCheckRad = 7;
        for(int i = 0; i < 360; i+= angleStep){
            int angle = offset + i;
            int cx = (int)(width/2 + Angles.trnsx(angle, length));
            int cy = (int)(height/2 + Angles.trnsy(angle, length));

            int waterTiles = 0;

            //check for water presence
            for(int rx = -waterCheckRad; rx <= waterCheckRad; rx++){
                for(int ry = -waterCheckRad; ry <= waterCheckRad; ry++){
                    Tile tile = tiles.get(cx + rx, cy + ry);
                    if(tile == null || tile.floor().liquidDrop != null){
                        waterTiles ++;
                    }
                }
            }

            if(waterTiles <= 4 || (i + angleStep >= 360)){
                roomseq.add(spawn = new Room(cx, cy, rand.random(8, 15)));

                for(int j = 0; j < enemySpawns; j++){
                    float enemyOffset = rand.range(60f);
                    Tmp.v1.set(cx - width/2, cy - height/2).rotate(180f + enemyOffset).add(width/2, height/2);
                    Room espawn = new Room((int)Tmp.v1.x, (int)Tmp.v1.y, rand.random(8, 16));
                    roomseq.add(espawn);
                    enemies.add(espawn);
                }

                break;
            }
        }

        //clear radius around each room
        for(Room room : roomseq){
            erase(room.x, room.y, room.radius);
        }

        //randomly connect rooms together
        int connections = rand.random(Math.max(rooms - 1, 1), rooms + 3);
        for(int i = 0; i < connections; i++){
            roomseq.random(rand).connect(roomseq.random(rand));
        }

        for(Room room : roomseq){
            spawn.connect(room);
        }

        Room fspawn = spawn;

        cells(1);

        int tlen = tiles.width * tiles.height;
        int total = 0, waters = 0;

        for(int i = 0; i < tlen; i++){
            Tile tile = tiles.geti(i);
            if(tile.block() == Blocks.air){
                total ++;
                if(tile.floor().liquidDrop == LaiLiquids.distilledwater){
                    waters ++;
                }
            }
        }

        boolean naval = (float)waters / total >= 0.29f;

        //create water pathway if the map is flooded
        if(naval){
            for(Room room : enemies){
                room.connectLiquid(spawn);
            }
        }

        distort(10f, 6f);

        //rivers
        pass((x, y) -> {
            if(block.solid) return;

            Vec3 v = sector.rect.project(x, y);

            float rr = Simplex.noise2d(sector.id, (float)2, 0.6f, 1f / 7f, x, y) * 0.1f;
            float value = Ridged.noise3d(2, v.x, v.y, v.z, 1, 1f / 55f) + rr - rawHeight(v) * 0f;
            float rrscl = rr * 44 - 2; 

            if(value > 0.17f && !Mathf.within(x, y, fspawn.x, fspawn.y, 12 + rrscl)){
                boolean deep = value > 0.17f + 0.1f && !Mathf.within(x, y, fspawn.x, fspawn.y, 15 + rrscl);
                boolean spore = floor != LaiBlocks.darkBlueSand && floor != LaiBlocks.darkgreenStone;
                //do not place rivers on ice, they're frozen
                //ignore pre-existing liquids
                if(!(floor == Blocks.ice || floor == Blocks.iceSnow || floor == Blocks.snow || floor.asFloor().isLiquid)){
                    floor = spore ?
                        (deep ? LaiBlocks.deepDistilledwater : LaiBlocks.distilledwater) :
                        (deep ? LaiBlocks.deepDistilledwater :
                            (floor == LaiBlocks.darkBlueSand || floor == LaiBlocks.darkgreenStone ? LaiBlocks.distilledwater : LaiBlocks.distilledwater));
                }
            }
        });

        //shoreline setup
        pass((x, y) -> {
            int deepRadius = 3;

            if(floor.asFloor().isLiquid && floor.asFloor().shallow){

                for(int cx = -deepRadius; cx <= deepRadius; cx++){
                    for(int cy = -deepRadius; cy <= deepRadius; cy++){
                        if((cx) * (cx) + (cy) * (cy) <= deepRadius * deepRadius){
                            int wx = cx + x, wy = cy + y;

                            Tile tile = tiles.get(wx, wy);
                            if(tile != null && (!tile.floor().isLiquid || tile.block() != Blocks.air)){
                                //found something solid, skip replacing anything
                                return;
                            }
                        }
                    }
                }

                floor = floor == LaiBlocks.distilledwater ? LaiBlocks.deepDistilledwater : LaiBlocks.distilledwater;
            }
        });

        if(naval){
            int deepRadius = 2;

            //TODO code is very similar, but annoying to extract into a separate function
            pass((x, y) -> {
                if(floor.asFloor().isLiquid && !floor.asFloor().isDeep() && !floor.asFloor().shallow){

                    for(int cx = -deepRadius; cx <= deepRadius; cx++){
                        for(int cy = -deepRadius; cy <= deepRadius; cy++){
                            if((cx) * (cx) + (cy) * (cy) <= deepRadius * deepRadius){
                                int wx = cx + x, wy = cy + y;

                                Tile tile = tiles.get(wx, wy);
                                if(tile != null && (tile.floor().shallow || !tile.floor().isLiquid)){
                                    //found something shallow, skip replacing anything
                                    return;
                                }
                            }
                        }
                    }

                    floor = floor == LaiBlocks.distilledwater ? LaiBlocks.deepDistilledwater : LaiBlocks.distilledwater;
                }
            });
        }
        distort(10f, 6f);

        Seq<Block> ores = Seq.with(LaiBlocks.oreLithium, LaiBlocks.orePlatinum);
        float poles = Math.abs(sector.tile.v.y);
        float nmag = 0.5f;
        float scl = 1f;
        float addscl = 1.3f;

        if(Simplex.noise3d(seed, 3, 0.5, scl, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.25f*addscl){
            ores.add(LaiBlocks.oreLithium);
        }

        if(Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.5f*addscl){
            ores.add(LaiBlocks.orerhodium);
        }

        if(Simplex.noise3d(seed, 4, 0.5, scl, sector.tile.v.x + 2, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.7f*addscl){
            ores.add(LaiBlocks.orePlatinum);
        }

        FloatSeq frequencies = new FloatSeq();
        for(int i = 0; i < ores.size; i++){
            frequencies.add(rand.random(-0.1f, 0.01f) - i * 0.01f + poles * 0.04f);
        }

        pass((x, y) -> {
            if(!floor.asFloor().hasSurface()) return;

            int offsetX = x - 4, offsetY = y + 23;
            for(int i = ores.size - 1; i >= 0; i--){
                Block entry = ores.get(i);
                float freq = frequencies.get(i);
                if(Math.abs(0.5f - noise(offsetX, offsetY + i*999, 2, 0.7, (40 + i * 2))) > 0.22f + i*0.01 &&
                        Math.abs(0.5f - noise(offsetX, offsetY - i*999, 1, 1, (30 + i * 4))) > 0.37f + freq){
                    ore = entry;
                    break;
                }
            }

            if(ore == LaiBlocks.oreLithium && rand.chance(0.33)){
                floor = LaiBlocks.darkBlueSand;
            }
        });

        trimDark();

        median(2);

        inverseFloodFill(tiles.getn(spawn.x, spawn.y));

        tech();

        pass((x, y) -> {
            //random moss
            // if(floor == LaiBlocks.darkgreenStone){
            //      if(Math.abs(0.5f - noise(x - 90, y, 4, 0.8, 65)) > 0.02){
            //          floor = LaiBlocks.darkBlueSand;
            //     }
            // }

            //tar
            if(floor == LaiBlocks.darkgreenStone){
                if(Math.abs(0.5f - noise(x - 40, y, 2, 0.7, 80)) > 0.25f &&
                    Math.abs(0.5f - noise(x, y + sector.id*10, 1, 1, 60)) > 0.41f && !(roomseq.contains(r -> Mathf.within(x, y, r.x, r.y, 15)))){
                    floor = LaiBlocks.darkgreenStone;
                }
            }

            //hotrock tweaks
            if(floor == LaiBlocks.lava){
                if(Math.abs(0.5f - noise(x - 90, y, 4, 0.8, 80)) > 0.035){
                    floor = LaiBlocks.pinksand;
                }else{
                    ore = Blocks.air;
                    boolean all = true;
                    for(Point2 p : Geometry.d4){
                        Tile other = tiles.get(x + p.x, y + p.y);
                        if(other == null || (other.floor() != LaiBlocks.lava && other.floor() != LaiBlocks.lava)){
                            all = false;
                        }
                    }
                    if(all){
                        floor = LaiBlocks.lava;
                    }
                }
            }else if(genLakes && floor != LaiBlocks.pinksand && floor != LaiBlocks.darkgreenStone && floor.asFloor().hasSurface()){
                float noise = noise(x + 782, y, 5, 0.75f, 260f, 1f);
                if(noise > 0.67f && !roomseq.contains(e -> Mathf.within(x, y, e.x, e.y, 14))){
                    if(noise > 0.72f){
                        floor = noise > 0.78f ? LaiBlocks.distilledwater : (floor == LaiBlocks.darkBlueSand ? LaiBlocks.distilledwater : LaiBlocks.distilledwater);
                    }else{
                        floor = (floor == LaiBlocks.darkBlueSand ? floor : LaiBlocks.pinksand);
                    }
                }
            }

            if(rand.chance(0.0075)){
                //random spore trees
                boolean any = false;
                boolean all = true;
                for(Point2 p : Geometry.d4){
                    Tile other = tiles.get(x + p.x, y + p.y);
                    if(other != null && other.block() == Blocks.air){
                        any = true;
                    }else{
                        all = false;
                    }
                }
                if(any && ((block == LaiBlocks.pinksandWall || block == LaiBlocks.darkgreenStoneWall) || (all && block == Blocks.air && floor == LaiBlocks.pinksand && rand.chance(0.03)))){
                    block = rand.chance(0.5) ? Blocks.whiteTree : Blocks.whiteTreeDead;
                }
            }

            //random stuff
            dec: {
                for(int i = 0; i < 4; i++){
                    Tile near = world.tile(x + Geometry.d4[i].x, y + Geometry.d4[i].y);
                    if(near != null && near.block() != Blocks.air){
                        break dec;
                    }
                }

                if(rand.chance(0.01) && floor.asFloor().hasSurface() && block == Blocks.air){
                    block = dec.get(floor, floor.asFloor().decoration);
                }
            }
        });

        float difficulty = sector.threat;
        
        int ruinCount = rand.random(-2, 4);

                if(ruinCount > 0){
            IntSeq ints = new IntSeq(width * height / 4);

            int padding = 25;

            //create list of potential positions
            for(int x = padding; x < width - padding; x++){
                for(int y = padding; y < height - padding; y++){
                    Tile tile = tiles.getn(x, y);
                    if(!tile.solid() && (tile.drop() != null || tile.floor().liquidDrop != null)){
                        ints.add(tile.pos());
                    }
                }
            }

            ints.shuffle(rand);

            int placed = 0;
            float diffRange = 0.4f;
            //try each position
            for(int i = 0; i < ints.size && placed < ruinCount; i++){
                int val = ints.items[i];
                int x = Point2.x(val), y = Point2.y(val);

                //do not overwrite player spawn
                if(Mathf.within(x, y, spawn.x, spawn.y, 18f)){
                    continue;
                }

                float range = difficulty + rand.random(diffRange);

                Tile tile = tiles.getn(x, y);
                BasePart part = null;
                if(tile.overlay().itemDrop != null){
                    part = bases.forResource(tile.drop()).getFrac(range);
                }else if(tile.floor().liquidDrop != null && rand.chance(0.05)){
                    part = bases.forResource(tile.floor().liquidDrop).getFrac(range);
                }else if(rand.chance(0.05)){ //ore-less parts are less likely to occur.
                    part = bases.parts.getFrac(range);
                }
            }
        }


        //remove invalid ores
        for(Tile tile : tiles){
            if(tile.overlay().needsSurface && !tile.floor().hasSurface()){
                tile.setOverlay(Blocks.air);
            }
        }

       Schematics.placeLaunchLoadout(spawn.x, spawn.y);

        for(Room espawn : enemies){
            tiles.getn(espawn.x, espawn.y).setOverlay(Blocks.spawn);
        }

        
        if(sector.hasEnemyBase()){
            basegen.generate(tiles, enemies.map(r -> tiles.getn(r.x, r.y)), tiles.get(spawn.x, spawn.y), state.rules.waveTeam, sector, difficulty);

            state.rules.attackMode = sector.info.attack = true;
        }else{
            state.rules.winWave = sector.info.winWave = 10 + 5 * (int)Math.max(difficulty * 10, 1);
        }

        float waveTimeDec = 0.4f;

        state.rules.waveSpacing = Mathf.lerp(60 * 65 * 2, 60f * 60f * 1f, Math.max(difficulty - waveTimeDec, 0f));
        state.rules.waves = true;
        state.rules.env = sector.planet.defaultEnv;
        state.rules.enemyCoreBuildRadius = 600f;

        //spawn air only when spawn is blocked
        state.rules.spawns = Waves.generate(difficulty, new Rand(sector.id), state.rules.attackMode, state.rules.attackMode && spawner.countGroundSpawns() == 0, naval);
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

