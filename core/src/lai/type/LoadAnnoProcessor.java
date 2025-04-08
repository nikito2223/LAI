package lai.type;

import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.liquid.*;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.world.blocks.power.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.meta.*;
import mindustry.entities.Units.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.entities.*;

import mindustry.world.blocks.liquid.LiquidBlock;

import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import lai.type.*;
import lai.content.*;
import static mindustry.Vars.*;
import mindustry.Vars;

import java.lang.annotation.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.*;
import javax.tools.Diagnostic.*;
import javax.tools.*;
import java.io.*;
import java.lang.annotation.*;
import java.util.*;

public class LoadAnnoProcessor {

    public static void begin(String modId) {
        for (Block block : Vars.content.blocks()) {
            if(block == null) return;
            Arrays.stream(block.getClass().getFields()).filter(e -> e.isAnnotationPresent(LoadAnno.class))
                    .forEach(ann -> {
                        LoadAnno anno =  ann.getAnnotation(LoadAnno.class);
                        String id = anno.value().replace("@",block.name);
                        String def = anno.def().replace("@",block.name);
                        if(def.isEmpty()) def = id;
                        Log.debug("[LoadAnnoProcessor]: Loading Region for "
                                +block.name.substring(modId.length()+1).toUpperCase()
                                +": val - ["+id+"] def - ["+def+"]");
                        try {
                            ann.set(block, Core.atlas.find(id,def));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }
    
    @Target(value=ElementType.FIELD)
    @Retention(value= RetentionPolicy.RUNTIME)
    public static @interface LoadAnno {
        String value();
        String def() default "";
    }
}