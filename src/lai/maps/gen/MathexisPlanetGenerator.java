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


    public static boolean indirectPaths = false;
    BaseGenerator basegen = new BaseGenerator();


    public float heightScl = 2.2f, octaves = 6, persistence = 1.4f, heightPow = 3.6f, heightMult = 1.6f;
    float scl = 5f;

    //TODO inline/remove
    float waterOffset = 0.04f;
    float heightYOffset = 52.2f;

    Block[][] arr =
    {
        {snow, natricStone, freshwater, darkgreenStone, darkgreenStone, natricStone, natricStone, natricStone, freshwater, snow, freshwater, mushroomBlue, mushroomBlue},
        {freshwater, crystalQuartz, crystalQuartz, snow, mushroomBlue, crystalQuartz, freshwater, crystalQuartz, mushroomBlue, darkpinksand, darkgreenStone, darkgreenStone, darkgreenStone},
        {freshwater, orangeDirt, orangeDirt, orangeDirt, mushroomBlue, orangeDirt, freshwater, freshwater, orangeDirt, freshwater, crystalQuartz, darkpinksand, darkpinksand},
        {mushroomBlue, orangeDirt, snow, freshwater, mushroomBlue, darkpinksand, mushroomBlue, darkpinksand, snow, orangeDirt, orangeDirt, orangeDirt, orangeDirt},
        {freshwater, darkgreenStone, freshwater, snow, snow, freshwater, freshwater, snow, darkgreenStone, natricStone, natricStone, darkgreenStone, darkgreenStone},
        {mushroomBlue, darkpinksand, sporebark, snow, mushroomBlue, snow, freshwater, freshwater, freshwater, sporebark, darkgreenStone, darkgreenStone, darkgreenStone},
        {sporebark, natricStone, darkgreenStone, freshwater, orangeDirt, freshwater, snow, darkpinksand, darkpinksand, sporebark, sporebark, freshwater, freshwater}
    };

    ObjectMap<Block, Block> dec = ObjectMap.of(
        biubiupool, freshwater
    );

    ObjectMap<Block, Block> tars = ObjectMap.of(
        sporebark, miceliumBlue
    );

    float water = 2f / arr[0].length;
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

    float rawHeight(Vec3 position){
        return (Mathf.pow(Simplex.noise3d(seed, 7, 0.5f, 1f/3f, position.x * scl, position.y * scl + heightYOffset, position.z * scl) * heightScl, 2.3f) + waterOffset) / (1f + waterOffset);
    }


    @Override
    public void generateSector(Sector sector){
        Ptile tile = sector.tile;
        float x = tile.v.x;
        float y = tile.v.y;
    
        boolean northPole = y > 0.85f;      // северный полюс
        boolean southPole = y < -0.85f;     // южный полюс
    
        boolean center1 = Math.abs(x) < 0.15f && Math.abs(y) < 0.15f;
        boolean center2 = Math.abs(x - 0.25f) < 0.15f && Math.abs(y) < 0.15f;
    
        if(northPole || southPole || center1 || center2){
            sector.generateEnemyBase = true;
        }
    }


    @Override
    public void getColor(Vec3 position, Color out){
        Block block = getBlock(position);

        out.set(block.mapColor).a(1f - block.albedo);
    }   
    
    // Основной метод выдачи блока
    Block getBlock(Vec3 position){
        float height = rawHeight(position);
        float px = position.x * scl, py = position.y * scl, pz = position.z * scl;

        float rad = scl;
        float temp = Mathf.clamp(Math.abs(py * 2f) / (rad));
        float tnoise = Simplex.noise3d(seed, 7, 0.56, 1f/3f, px, py + 999f - 0.1f, pz);
        temp = Mathf.lerp(temp, tnoise, 0.5f);
        height *= 1.2f;
        height = Mathf.clamp(height);

        float tar = Simplex.noise3d(seed, 4, 0.55f, 1f/2f, px, py + 999f, pz) * 0.3f + position.dst(0, 0, 1f) * 0.2f;

        Block res = arr[Mathf.clamp((int)(temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int)(height * arr[0].length), 0, arr[0].length - 1)];
        if(tar > 0.5f){
            return tars.get(res, res);
        }else{
            if(position.within(basePos, 0.65f)){

                float dst = 999f;

                Object[] sectors = Planets.serpulo.sectors.items;
                int size = Planets.serpulo.sectors.size;

                for(int i = 0; i < size; i ++){
                    var sector = (Sector)sectors[i];

                    if(sector.hasEnemyBase()){
                        dst = Math.min(dst, position.dst(sector.tile.v));
                    }
                }

                float freq = 0.05f, freq2 = 0.07f;

                if(dst*0.85f + Simplex.noise3d(seed, 3, 0.4, 5.5f, position.x, position.y + 200f, position.z)*0.015f + ((basePos.dst(position) + 0.00f) % freq < freq/2f ? 1f : 0f) * 0.07f < 0.15f){
                    return ((basePos.dst(position) + 0.01f) % freq2 < freq2*0.65f) ? Blocks.metalFloor : Blocks.darkPanel6;
                }
            }
            return res;
        }
    }

    static double metalDstScl = 0.25;

    @Override
    public void genTile(Vec3 position, TileGen tile){
        tile.floor = getBlock(position);
        tile.block = tile.floor.asFloor().wall;

        if(Ridged.noise3d(seed + 1, position.x, position.y, position.z, 2, 22) > 0.31){
            tile.block = Blocks.air;
        }
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
                float nscl = rand.random(80f, 120f) * 5.5f; // Изменено с 100f, 140f * 6f
                int stroke = rand.random(2, 7); // Изменено с 3, 9
                brush(pathfind(x1, y1, x2, y2, tile -> (tile.solid() ? 45f : 0f) + noise(tile.x, tile.y, 2, 0.35f, 1f / nscl) * 450, Astar.manhattan), stroke);
            }

            void connect(Room to){
                if(!connected.add(to) || to == this) return;

                Vec2 midpoint = Tmp.v1.set(to.x, to.y).add(x, y).scl(0.5f);
                rand.nextFloat();

                if(indirectPaths){
                    midpoint.add(Tmp.v2.set(1, 0f).setAngle(Angles.angle(to.x, to.y, x, y) + 75f * (rand.chance(0.4) ? 1f : -1f)).scl(Tmp.v1.dst(x, y) * 1.8f)); // Изменено
                }else{
                    //add randomized offset to avoid straight lines
                    midpoint.add(Tmp.v2.setToRandomDirection(rand).scl(Tmp.v1.dst(x, y) * 0.8f)); // Изменено множитель
                }

                midpoint.sub(width/2f, height/2f).limit(width / 2f / Mathf.sqrt2).add(width/2f, height/2f); // Изменено sqrt3 на sqrt2

                int mx = (int)midpoint.x, my = (int)midpoint.y;

                join(x, y, mx, my);
                join(mx, my, to.x, to.y);
            }

            void joinLiquid(int x1, int y1, int x2, int y2){
                float nscl = rand.random(90f, 130f) * 5f; // Изменено
                int rad = rand.random(5, 9); // Изменено с 7, 11
                int avoid = 1 + rad; // Изменено с 2 + rad
                var path = pathfind(x1, y1, x2, y2, tile -> (tile.solid() || !tile.floor().isLiquid ? 60f : 0f) + noise(tile.x, tile.y, 2, 0.35f, 1f / nscl) * 450, Astar.manhattan); // Изменено
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
                                    other.setFloor((Floor)(floor == darkgreenStone || floor == miceliumBlue ? darkgreenStoneWater : freshwater));
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
                midpoint.add(Tmp.v2.setToRandomDirection(rand).scl(Tmp.v1.dst(x, y) * 0.7f)); // Изменено
                midpoint.sub(width/2f, height/2f).limit(width / 2f / Mathf.sqrt2).add(width/2f, height/2f); // Изменено

                int mx = (int)midpoint.x, my = (int)midpoint.y;

                joinLiquid(x, y, mx, my);
                joinLiquid(mx, my, to.x, to.y);
            }
        }

        cells(3); // Изменено с 4
        distort(8f, 10f); // Изменено с 10f, 12f

        float constraint = 1.4f; // Изменено с 1.3f
        float radius = width / 2f / Mathf.sqrt2; // Изменено с Mathf.sqrt3
        int rooms = rand.random(3, 6); // Изменено с 2, 5
        Seq<Room> roomseq = new Seq<>();

        for(int i = 0; i < rooms; i++){
            Tmp.v1.trns(rand.random(360f), rand.random(radius / constraint));
            float rx = (width/2f + Tmp.v1.x);
            float ry = (height/2f + Tmp.v1.y);
            float maxrad = radius - Tmp.v1.len();
            float rrad = Math.min(rand.random(7f, maxrad / 2.2f), 25f); // Изменено
            roomseq.add(new Room((int)rx, (int)ry, (int)rrad));
        }

        //check positions on the map to place the player spawn. this needs to be in the corner of the map
        Room spawn = null;
        Seq<Room> enemies = new Seq<>();
        int enemySpawns = rand.random(2, Math.max((int)(sector.threat * 5), 2)); // Изменено
        int offset = rand.nextInt(360);
        float length = width/2.7f - rand.random(10, 20); // Изменено
        int angleStep = 4; // Изменено с 5
        int waterCheckRad = 4; // Изменено с 5
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

            if(waterTiles <= 3 || (i + angleStep >= 360)){ // Изменено с 4
                roomseq.add(spawn = new Room(cx, cy, rand.random(6, 12))); // Изменено

                for(int j = 0; j < enemySpawns; j++){
                    float enemyOffset = rand.range(50f); // Изменено
                    Tmp.v1.set(cx - width/2, cy - height/2).rotate(170f + enemyOffset).add(width/2, height/2); // Изменено
                    Room espawn = new Room((int)Tmp.v1.x, (int)Tmp.v1.y, rand.random(7, 14)); // Изменено
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
        int connections = rand.random(Math.max(rooms - 1, 2), rooms + 4); // Изменено
        for(int i = 0; i < connections; i++){
            roomseq.random(rand).connect(roomseq.random(rand));
        }

        for(Room room : roomseq){
            if(spawn != null) spawn.connect(room);
        }

        Room fspawn = spawn;

        cells(2); // Изменено с 1

        int tlen = tiles.width * tiles.height;
        int total = 0, waters = 0;

        for(int i = 0; i < tlen; i++){
            Tile tile = tiles.geti(i);
            if(tile.block() == Blocks.air){
                total ++;
                if(tile.floor().liquidDrop == Liquids.water){
                    waters ++;
                }
            }
        }

        boolean naval = (float)waters / total >= 0.16f; // Изменено с 0.19f

        //create water pathway if the map is flooded
        if(naval){
            for(Room room : enemies){
                if(spawn != null) room.connectLiquid(spawn);
            }
        }

        distort(8f, 5f); // Изменено

        //rivers
        pass((x, y) -> {
            if(block.solid) return;

            Vec3 v = sector.rect.project(x, y);

            float rr = Simplex.noise2d(sector.id, (float)2, 0.5f, 1f / 8f, x, y) * 0.08f; // Изменено
            float value = Ridged.noise3d(2, v.x, v.y, v.z, 1, 1f / 60f) + rr - rawHeight(v) * 0f; // Изменено
            float rrscl = rr * 40 - 1; // Изменено

            if(value > 0.15f && !Mathf.within(x, y, fspawn.x, fspawn.y, 10 + rrscl)){ // Изменено
                boolean deep = value > 0.15f + 0.08f && !Mathf.within(x, y, fspawn.x, fspawn.y, 12 + rrscl); // Изменено
                boolean spore = floor != darkpinksand && floor != miceliumBlue;
                //do not place rivers on ice, they're frozen
                //ignore pre-existing liquids
                if(!(floor == iceGreen || floor.asFloor().isLiquid)){
                    floor = spore ?
                        (deep ? sporeWater : darkgreenStoneSporeWater) :
                        (deep ? freshwater :
                            (floor == darkgreenStone || floor == miceliumBlue ? sporeWater : darkpinksand));
                }
            }
        });

        if(naval){
            int deepRadius = 1; // Изменено с 2

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

                    floor = floor == freshwater ? deepfreshwater : sporeWater;
                }
            });
        }

        Seq<Block> ores = Seq.with(oreLithium, oreIron);
        float poles = Math.abs(sector.tile.v.y);
        float nmag = 0.6f; // Изменено с 0.5f
        float scl = 0.9f; // Изменено с 1f
        float addscl = 1.2f; // Изменено с 1.3f

        if(Simplex.noise3d(seed, 2, 0.6, scl, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.22f*addscl){ // Изменено
            ores.add(orePlatinum);
        }

        if(Simplex.noise3d(seed, 2, 0.6, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.45f*addscl){ // Изменено
            ores.add(oreRhodium);
        }

        //218 doesn't have thorium generation due to proximity (TODO remove the special case and replace with hidden preset)
        if(Simplex.noise3d(seed, 2, 0.6, scl, sector.tile.v.x + 2, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.65f*addscl && sector.id != 218){ // Изменено
            ores.add(oreNatrium);
        }


        FloatSeq frequencies = new FloatSeq();
        for(int i = 0; i < ores.size; i++){
            frequencies.add(rand.random(-0.08f, 0.02f) - i * 0.008f + poles * 0.03f); // Изменено
        }

        pass((x, y) -> {
            if(!floor.asFloor().hasSurface()) return;

            int offsetX = x - 3, offsetY = y + 17; // Изменено
            for(int i = ores.size - 1; i >= 0; i--){
                Block entry = ores.get(i);
                float freq = frequencies.get(i);
                if(Math.abs(0.5f - noise(offsetX, offsetY + i*777, 2, 0.65, (35 + i * 3))) > 0.20f + i*0.008 && // Изменено
                    Math.abs(0.5f - noise(offsetX, offsetY - i*777, 1, 0.9, (25 + i * 3))) > 0.35f + freq){ // Изменено
                    ore = entry;
                    break;
                }
            }
        });

        trimDark();

        median(3); // Изменено с 2

        if(spawn != null){
            inverseFloodFill(tiles.getn(spawn.x, spawn.y));
        }

        tech();

        pass((x, y) -> {
            //random moss
            if(floor == sporebark){
                if(Math.abs(0.5f - noise(x - 90, y, 3, 0.7, 55)) > 0.018){ // Изменено
                    floor = pinkcrystalspore;
                }
            }

            //tar
            if(floor == orangeDirt){
                if(Math.abs(0.5f - noise(x - 40, y, 2, 0.65, 70)) > 0.22f && // Изменено
                Math.abs(0.5f - noise(x, y + sector.id*10, 1, 0.9, 50)) > 0.38f && !(roomseq.contains(r -> Mathf.within(x, y, r.x, r.y, 25)))){ // Изменено
                    floor = Blocks.tar;
                }
            }

            if(rand.chance(0.006)){ // Изменено с 0.0075
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
            }

            //random stuff
            dec: {
                for(int i = 0; i < 4; i++){
                    Tile near = tiles.get(x + Geometry.d4[i].x, y + Geometry.d4[i].y);
                    if(near != null && near.block() != Blocks.air){
                        break dec;
                    }
                }

                if(rand.chance(0.008) && floor.asFloor().hasSurface() && block == Blocks.air){ // Изменено
                    block = dec.get(floor, floor.asFloor().decoration);
                }
            }
        });

        float difficulty = sector.threat;
        int ruinCount = rand.random(-1, 3); // Изменено

        if(ruinCount > 0){
            IntSeq ints = new IntSeq(width * height / 5); // Изменено

            int padding = 22; // Изменено

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
            float diffRange = 0.35f; // Изменено
            //try each position
            for(int i = 0; i < ints.size && placed < ruinCount; i++){
                int val = ints.items[i];
                int x = Point2.x(val), y = Point2.y(val);

                //do not overwrite player spawn
                if(spawn != null && Mathf.within(x, y, spawn.x, spawn.y, 15f)){ // Изменено
                    continue;
                }

                float range = difficulty + rand.random(diffRange);

                Tile tile = tiles.getn(x, y);
                BasePart part = null;
                if(tile.overlay().itemDrop != null){
                    part = bases.forResource(tile.drop()).getFrac(range);
                }else if(tile.floor().liquidDrop != null && rand.chance(0.04)){ // Изменено
                    part = bases.forResource(tile.floor().liquidDrop).getFrac(range);
                }else if(rand.chance(0.04)){ // Изменено
                    part = bases.parts.getFrac(range);
                }

                //actually place the part
                if(part != null && BaseGenerator.tryPlace(part, x, y, Team.derelict, rand, (cx, cy) -> {
                    Tile other = tiles.getn(cx, cy);
                    if(other.floor().hasSurface()){
                        other.setOverlay(Blocks.oreScrap);
                        for(int j = 1; j <= 2; j++){
                            for(Point2 p : Geometry.d8){
                                Tile t = tiles.get(cx + p.x*j, cy + p.y*j);
                                if(t != null && t.floor().hasSurface() && rand.chance(j == 1 ? 0.3 : 0.15)){ // Изменено
                                    t.setOverlay(Blocks.oreScrap);
                                }
                            }
                        }
                    }
                })){
                    placed ++;

                    int debrisRadius = Math.max(part.schematic.width, part.schematic.height)/2 + 2; // Изменено
                    Geometry.circle(x, y, tiles.width, tiles.height, debrisRadius, (cx, cy) -> {
                        float dst = Mathf.dst(cx, cy, x, y);
                        float removeChance = Mathf.lerp(0.04f, 0.4f, dst / debrisRadius); // Изменено

                        Tile other = tiles.getn(cx, cy);
                        if(other.build != null && other.isCenter()){
                            if(other.team() == Team.derelict && rand.chance(removeChance)){
                                other.remove();
                            }else if(rand.chance(0.4)){ // Изменено
                                other.build.health = other.build.health - rand.random(other.build.health * 0.85f); // Изменено
                            }
                        }
                    });
                }
            }
        }

        //remove invalid ores
        for(Tile tile : tiles){
            if(tile.overlay().needsSurface && !tile.floor().hasSurface()){
                tile.setOverlay(Blocks.air);
            }
        }

        if(spawn != null){
            Schematics.placeLaunchLoadout(spawn.x, spawn.y);
        }

        for(Room espawn : enemies){
            tiles.getn(espawn.x, espawn.y).setOverlay(Blocks.spawn);
        }

        float waveTimeDec = 0.35f; // Изменено

        state.rules.waveSpacing = Mathf.lerp(60 * 60 * 2, 60f * 50f * 1f, Math.max(difficulty - waveTimeDec, 0f)); // Изменено
        state.rules.waves = true;
        state.rules.env = sector.planet.defaultEnv;
        state.rules.enemyCoreBuildRadius = 550f; // Изменено

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

