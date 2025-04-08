package lai.world.meta;

import arc.*;
import arc.struct.*;
import mindustry.world.meta.*;
import mindustry.world.meta.Stat;

public class LaiStat{

    public static final Stat
    coldCapacity = new Stat("lai-cold-capacity"),
    radioactive = new Stat("lai-radioactive"),
    radiactionDamage = new Stat("lai-radiaction-damage", StatCat.function);
    
}
