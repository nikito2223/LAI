package lai.type.unit;

import lai.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.world.meta.*;

/** Config class for special Erekir unit properties. */
public class MathexisUnitType extends UnitType{

    public MathexisUnitType(String name){
        super(name);
        outlineColor = Pal.darkOutline;
        envDisabled = Env.space;
        ammoType = new ItemAmmoType(LaiItems.lithium);
        researchCostMultiplier = 10f;
    }
}
