package lai.world.blocks.explosives;

import arc.*;
import arc.graphics.g2d.*;
import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.blocks.*;
import mindustry.content.Fx;
import mindustry.gen.Call;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.Env;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.Env;
import mindustry.ctype.*;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.payloads.*;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.world.meta.*;
import mindustry.world.Block;
import mindustry.entities.Units.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.entities.*;

import lai.world.*;
import lai.graphics.*;

import static mindustry.Vars.*;

public class Dynamite extends LaiBlock {
	
	public float explosionDelay = 180f;
    public float explosionRadius = 60f;
    public float explosionDamage = 150f;

	public Dynamite(String name){
		super(name);
		hasPower = true;
        update = true;
        solid = true;
        destructible = true;
        hasPower = true;
        group = BlockGroup.projectors;
        envEnabled |= Env.space;

	}

	@Override
    public void setBars(){
        super.setBars();
    }


    public class DynamiteBuild extends Building {
        float timer = 0;
        boolean activated = false;
        public float[] points = new float[]{
        
        };


        @Override
        public void updateTile() {

            if (!activated && this.power.graph.getPowerProduced() > 0.01f) {
                activated = true;
            }

            if (activated) {
                timer += Time.delta;
                if (timer >= explosionDelay) {
                    explode();
                }
            }

            indexer.eachBlock(this, explosionRadius, b -> true, other -> {
                Fx.healBlockFull.at(other.x, other.y, other.block.size, Color.yellow, other.block);
            });

        }

        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, explosionRadius, LaiPal.radiction);
        }

        public void explode() {
            // Эффекты
            Fx.blastExplosion.at(x, y);
            Sounds.explosion.at(x, y);
			Damage.dynamicExplosion(x, y, 1, 10, explosionDamage, explosionRadius * tilesize, true);



            Units.nearbyEnemies(team, x, y, explosionRadius, u -> {
        		u.damage(explosionDamage);
    		});
            // Удалить блок
            tile.setAir();
        }
    }
}