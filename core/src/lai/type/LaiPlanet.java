package lai.type;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.gen.Icon;
import mindustry.type.*;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g3d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.content.*;
import mindustry.content.TechTree.*;
import mindustry.ctype.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.io.*;
import mindustry.maps.generators.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;
import static mindustry.graphics.g3d.PlanetRenderer.*;

public class LaiPlanet extends Planet {

    public boolean hasAtmosphereGas = true;

    public LaiPlanet(String name, Planet parent, float radius){
        super(name, parent, radius);
    }

    public LaiPlanet(String name, Planet parent, float radius, int sectorSize){
        this(name, parent, radius);
    }

    public boolean isAtmosphereGas(){
        return hasAtmosphereGas;
    }

    public void atmosphereGas(LiquidStack liquid){

    }

} 
