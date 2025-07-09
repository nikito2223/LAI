package lai.world.blocks.defense.turrets;

import arc.struct.*;
import mindustry.entities.bullet.*;
import mindustry.logic.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.graphics.*;
import lai.graphics.*;
import lai.content.*;

import static lai.content.LaiFx.*;

public class ShockTurret extends PowerTurret{

    private float cooldown = 0;

	public ShockTurret(String name) {
		super(name);
		hasPower = true;
		acceptsItems = true;
        ammoPerShot = 1;
	}

	@Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.ammo, StatValues.ammo(ObjectMap.of(this, shootType)));
    }
 
    public void limitRange(float margin){
        limitRange(shootType, margin);
    }

	public class ShockTurretBuild extends PowerTurretBuild{
        float cooldown = 0f;

        @Override
        public void updateTile() {
            super.updateTile();
            if (cooldown > 0) cooldown -= edelta();
            updatePulse();
        }

        public void updatePulse() {
            if (cooldown <= 0 && power.status > 0.99f) {
                // Выпустить импульс
                doPulse();
                cooldown = reload;
            }
        }

        void doPulse() {
            // FX
            pulseEffect.at(x, y);

            // Нанести урон врагам в радиусе
			unit.ammo(power.status * unit.type().ammoCapacity);
        }
	}
}