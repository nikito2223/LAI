package lai.maps.gen;

import arc.math.*;
import arc.util.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.maps.generators.*;
import lai.content.*;
import lai.content.blocks.*;

import static mindustry.Vars.*;

public class TemaPlanetGenerator extends PlanetGenerator
{
    Color c1 = Color.valueOf("141e8b"), c2 = Color.valueOf("272766"), out = new Color();

    Block[][] arr = {
    {LaiEnvironmentBlocks.darkgreenStone, LaiEnvironmentBlocks.redstone, LaiEnvironmentBlocks.darkgreenStone, LaiEnvironmentBlocks.darkgreenStone, LaiEnvironmentBlocks.darkgreenStone}

    };

    {
        baseSeed = 1;
    }

    @Override
    public void generateSector(Sector sector){
        //no bases
    }

    @Override
    public float getHeight(Vec3 position){
        return 0;
    }

    @Override
    public Color getColor(Vec3 position){
        float depth = Simplex.noise3d(seed, 2, 0.56, 1.7f, position.x, position.y, position.z) / 2f;
        return c1.write(out).lerp(c2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(0.2f);
    }

    @Override
    public float getSizeScl(){
        return 2000;
    }

    @Override
    public void addWeather(Sector sector, Rules rules){
        //no weather... yet
    }

    @Override
    public void genTile(Vec3 position, TileGen tile){
        tile.floor = getBlock(position);

        if(tile.floor == Blocks.redmat && rand.chance(0.1)){
            tile.block = Blocks.redweed;
        }

        if(tile.floor == Blocks.bluemat && rand.chance(0.03)){
            tile.block = Blocks.purbush;
        }

        if(tile.floor == Blocks.bluemat && rand.chance(0.002)){
            tile.block = Blocks.yellowCoral;
        }
    }

    @Override
    protected void generate(){
        pass((x, y) -> {
            float max = 0;
            for(Point2 p : Geometry.d8){
                max = Math.max(max, world.getDarkness(x + p.x, y + p.y));
            }
            if(max > 0){
                block = floor.asFloor().wall;
            }

            if(noise(x, y, 40f, 1f) > 0.9){
                //block = Blocks.coralChunk;
            }
        });

        Schematics.placeLaunchLoadout(width / 2, height / 2);
    }

    float rawHeight(Vec3 position){
        return Simplex.noise3d(seed, 8, 0.7f, 1f, position.x, position.y, position.z);
    }

    Block getBlock(Vec3 position){
        float height = rawHeight(position);
        Tmp.v31.set(position);
        position = Tmp.v33.set(position).scl(2f);
        float temp = Simplex.noise3d(seed, 8, 0.6, 1f/2f, position.x, position.y + 99f, position.z);
        height *= 1.2f;
        height = Mathf.clamp(height);

        return arr[Mathf.clamp((int)(temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int)(height * arr[0].length), 0, arr[0].length - 1)];
    }
}
