package lai.world.meta;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.Tooltip.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.maps.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.StatValues;

import static mindustry.Vars.*;

public class LaiValues extends StatValues {
	public static <T extends Element> T withTooltip(T element, UnlockableContent content, boolean tooltip){
        if(content != null){
            if(!mobile){
                if(tooltip){
                    element.addListener(Tooltips.getInstance().create(content.localizedName));
                }
                element.addListener(new HandCursorListener(() -> !content.isHidden(), true));
            }
            element.clicked(() -> {
                if(!content.isHidden()){
                    Vars.ui.content.show(content);
                }
            });
        }
        return element;
    }

    public static <T extends Element> T withTooltip(T element, UnlockableContent content){
        return withTooltip(element, content, false);
    }

}