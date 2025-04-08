package lai.world.blocks.units;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.io.*;
import arc.scene.ui.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;

import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

import mindustry.Vars;
import mindustry.gen.*;
import mindustry.type.UnitType;
import mindustry.content.UnitTypes;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BlockGroup;
import arc.struct.Seq;
import arc.func.Boolf;
import arc.math.geom.Vec2;
import arc.graphics.g2d.Lines;
import mindustry.ui.*;
import arc.scene.ui.layout.Table;
import arc.scene.ui.Button;
import mindustry.input.*;

import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

import lai.graphics.*;
import lai.world.*;
import lai.content.*;

import static mindustry.Vars.*;

public class Replicator extends LaiBlock{
	public float productionTime = 300f; // Время производства дрона (5 секунд)
	public float distance = 15f;
	public UnitType droneType = LaiUnits.scanningDrone;

	public Replicator(String name){
        super(name);
        update = true;
        solid = true;
        hasPower = true;
        configurable = true;
	}

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
    }

	public class ReplicatorBuild extends Building{
        private Seq<Unit> drones = new Seq<>();
        private int state = 0; // 0 - следовать за игроком, 1 - преследовать врагов
        private float productionProgress = 0f;

        @Override
        public void updateTile() {
        	
         	if (drones.size < 2) {
         	    productionProgress += edelta();
         	    if (productionProgress >= productionTime) {
         	        spawnDrone();
         	        productionProgress = 0f;
         	    }
         	}

            for (Unit drone : drones) {
            	if (tile.build != null && tile.build.health > 0) {
            	    if (drone != null && !drone.dead) {
            	        switch (state) {
            	            case 0:
            	                if (Vars.player != null && Vars.player.unit() != null && distance <8*7) {
            	                    drone.moveAt(new Vec2(Vars.player.unit().x, Vars.player.unit().y).sub(drone.x, drone.y).nor().scl(1.5f));
            	                }
            	                break;
            	            case 1:
            	                Unit enemy = Units.closestEnemy(team, x, y, 9999f, u -> true);
            	                if (enemy != null) {
            	                    drone.moveAt(new Vec2(enemy.x, enemy.y).sub(drone.x, drone.y).nor().scl(1.5f));
            	                } else {
            	                    drone.moveAt(new Vec2(x, y).sub(drone.x, drone.y).nor().scl(1.5f));
            	                }
            	                break;
            	        }
            	    }
            	}
            	else{
            		drones.each(Unit::kill); // Уничтожаем всех созданных юнитов
            		drones.clear(); // Уничтожаем всех привязанных юнитов
            		Log.info("блок уничтожин - " + this.block);
            	}
        	}
        }

        private void spawnDrone() {
            Time.run(productionTime, () -> {
                Unit drone = droneType.spawn(team, x, y);
                if (drone != null) {
                    drones.add(drone);
                }
            });
        }

        public void spaswnDrone() {
        	drones.each(Unit::kill); // Уничтожаем всех созданных юнитов
            drones.clear(); // Уничтожаем всех привязанных юнитов
        }

        @Override
        public void buildConfiguration(Table table) {
           	table.row();

           	if(droneType != null){
           		table.button(Icon.pencil, Styles.clearNoneTogglei, () -> {
           			state = 0;
           			Log.info("Следует за игроком");
           		}).size(40);
           		table.button(Icon.pencil, Styles.clearNoneTogglei, () -> {
           			state = 1;
           			Log.info("Следует за врагом");
           		}).size(40);

           		table.row();

           	}
        }
	}
}