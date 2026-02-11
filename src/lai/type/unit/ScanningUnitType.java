package lai.type.unit;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.world.meta.*;
import mindustry.gen.*;

public class ScanningUnitType extends UnitType {

	public ScanningUnitType(String name){
		super(name);
		health = 50f;
		mineWalls = false;
        mineFloor = false;
		flying = true;
		rotateSpeed = 7f;
		itemCapacity = 0;
		speed = 3f;
		fogRadius = 15f;
		targetable = false;
	}

    @Override
    public boolean isHidden() {
        return true; // Полностью скрыт
    }

}